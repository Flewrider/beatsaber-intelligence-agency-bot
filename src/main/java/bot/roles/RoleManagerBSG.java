package bot.roles;

import bot.main.BotConstants;
import bot.utils.ListValueUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;
import java.util.stream.Collectors;

public class RoleManagerBSG {

    private static final String DE_SUFFIX = " DE";

    /**
     * Ensures the member holds exactly the milestone role that matches their current country rank.
     * <p>
     * This is idempotent: if the member already has the correct role (and no other milestone roles),
     * nothing is touched. This is the fix for the endless "removed Top X DE / added Top X DE" role
     * flapping that happened every refresh cycle.
     *
     * The previous implementation relied on a case-sensitive {@code equals} check to decide whether a
     * change was needed, while the actual add/remove used case-insensitive matching. Whenever the Discord
     * role name did not byte-for-byte match {@code "Top <n> DE"} (different casing, a stray space, or an
     * extra unrelated "Top ..." role the bot could not remove), the check kept reporting "new milestone"
     * forever and the bot removed and re-added the same role on every run.
     *
     * @param member      the guild member to update
     * @param countryRank the player's current ScoreSaber country rank (0 = inactive)
     * @param isInactive  whether the player is currently inactive
     * @return {@code true} only if the target milestone role was newly added (i.e. the member actually
     * reached a milestone they did not have before) — used to decide logging / congrats messages
     */
    public static boolean syncMilestoneRole(Member member, int countryRank, boolean isInactive) {
        if (member == null) {
            return false;
        }
        String targetRole = isInactive ? null : getMilestoneRoleName(countryRank);

        // Active player whose rank is below the lowest tracked milestone: leave their roles untouched
        // (matches the original behaviour of doing nothing once a player drops out of the milestone range).
        if (!isInactive && targetRole == null) {
            return false;
        }

        List<Role> currentMilestoneRoles = getCurrentMilestoneRoles(member);
        boolean hasTarget = targetRole != null
                && currentMilestoneRoles.stream().anyMatch(role -> namesMatch(role.getName(), targetRole));

        // Strip any milestone role that is not the target (stale milestones, wrong casing, duplicates).
        List<Role> staleRoles = currentMilestoneRoles.stream()
                .filter(role -> targetRole == null || !namesMatch(role.getName(), targetRole))
                .collect(Collectors.toList());
        if (!staleRoles.isEmpty()) {
            RoleManager.removeMemberRoles(member, staleRoles);
        }

        // Only add the target role if it is genuinely missing -> no remove-then-re-add churn.
        if (targetRole != null && !hasTarget) {
            RoleManager.assignRole(member, targetRole);
            return true;
        }
        return false;
    }

    /**
     * @return the "Top &lt;n&gt; DE" role name the given country rank maps to, or {@code null} if the rank
     * is outside the tracked milestone range.
     */
    public static String getMilestoneRoleName(int countryRank) {
        int lowestMilestone = BotConstants.bsgCountryRankMilestones[BotConstants.bsgCountryRankMilestones.length - 1];
        if (countryRank <= 0 || countryRank > lowestMilestone) {
            return null;
        }
        int milestone = ListValueUtils.findBsgMilestoneForRank(countryRank);
        if (milestone < 0) {
            return null;
        }
        return BotConstants.topRolePrefix + milestone + DE_SUFFIX;
    }

    /**
     * @return all "Top &lt;n&gt; DE" milestone roles the member currently holds. Unlike a broad
     * {@code contains("top ")} match, this only matches the actual BSG milestone roles, so unrelated
     * roles that merely contain "top" are never removed or counted.
     */
    public static List<Role> getCurrentMilestoneRoles(Member member) {
        return member.getRoles().stream()
                .filter(role -> isMilestoneRoleName(role.getName()))
                .collect(Collectors.toList());
    }

    private static boolean isMilestoneRoleName(String roleName) {
        if (roleName == null) {
            return false;
        }
        for (Integer milestone : BotConstants.bsgCountryRankMilestones) {
            if (namesMatch(roleName, BotConstants.topRolePrefix + milestone + DE_SUFFIX)) {
                return true;
            }
        }
        return false;
    }

    private static boolean namesMatch(String a, String b) {
        return a != null && b != null && a.trim().equalsIgnoreCase(b.trim());
    }
}

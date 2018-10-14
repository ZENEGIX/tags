package ru.zenegix.tags.team;

import org.bukkit.entity.Player;
import ru.zenegix.tags.render.Renderable;

public interface TagTeam {

    /**
     * Get a prefix for this team
     *
     * @return prefix
     */
    Renderable getPrefix();

    /**
     * Get a suffix for this team
     *
     * @return suffix
     */
    Renderable getSuffix();

    /**
     * Get a update period in ticks
     * If period < 1, then update doesn't work
     *
     * @return the update period in ticks
     */
    int getUpdatePeriod();

    /**
     * Set update period and start new update task
     * If last update period < 1, then in first - stop the last update task
     *
     * @param updatePeriod the update period in ticks
     */
    void setUpdatePeriod(int updatePeriod);

    /**
     * Add member in this team
     *
     * @param player member, not case sensitive
     *
     * @return {@code true} if member successfully added to this team
     *          {@code false} otherwise
     */
    boolean addMember(String player);

    /**
     * Remove member from this team
     *
     * @param player member, not case sensitive
     *
     * @return {@code true} if member successfully removed from this team
     *          {@code false} otherwise
     */
    boolean removeMember(String player);

    /**
     *
     * @param player given name, not case sensitive
     *
     * @return {@code true} if player is a member for this team,
     *          {@code false} otherwise
     */
    boolean isMember(String player);

    /**
     * Force update the prefix and suffix.
     *
     * Use it, if you update prefix or suffix
     */
    void update();

    /**
     * Showing a team for a player.
     * Using, when player are join to server.
     *
     * @param player player, which need show this team
     */
    void show(Player player);

}

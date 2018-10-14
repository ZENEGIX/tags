package ru.zenegix.tags.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.IntEnum;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.ChatColor;
import ru.zenegix.tags.packet.v1_13.CraftScoreboardTeamPacket;

import java.util.Collection;
import java.util.List;

public abstract class ScoreboardTeamPacket extends AbstractPacket {

    /**
     * Constructs a new strongly typed wrapper for the given packet.
     *
     * @param handle - handle to the raw packet data.
     * @param type   - the packet type.
     */
    protected ScoreboardTeamPacket(PacketContainer handle, PacketType type) {
        super(handle, type);
    }

    /**
     * Enum containing all known modes.
     *
     * @author dmulloy2
     */
    public static class Mode extends IntEnum {
        public static final int TEAM_CREATED = 0;
        public static final int TEAM_REMOVED = 1;
        public static final int TEAM_UPDATED = 2;
        public static final int PLAYERS_ADDED = 3;
        public static final int PLAYERS_REMOVED = 4;

        private static final CraftScoreboardTeamPacket.Mode INSTANCE = new CraftScoreboardTeamPacket.Mode();

        public static CraftScoreboardTeamPacket.Mode getInstance() {
            return INSTANCE;
        }
    }

    /**
     * Retrieve Team Name.
     * <p>
     * Notes: a unique name for the team. (Shared with scoreboard).
     *
     * @return The current Team Name
     */
    public abstract String getName();

    /**
     * Set Team Name.
     *
     * @param value - new value.
     */
    public abstract void setName(String value);

    /**
     * Retrieve Team Display Name.
     * <p>
     * Notes: only if Mode = 0 or 2.
     *
     * @return The current Team Display Name
     */
    public abstract String getDisplayName();

    /**
     * Set Team Display Name.
     *
     * @param value - new value.
     */
    public abstract void setDisplayName(String value);

    /**
     * Retrieve Team Prefix.
     * <p>
     * Notes: only if Mode = 0 or 2. Displayed before the players' name that are
     * part of this team.
     *
     * @return The current Team Prefix
     */
    public abstract String getPrefix();

    /**
     * Set Team Prefix.
     *
     * @param value - new value.
     */
    public abstract void setPrefix(String value);

    /**
     * Retrieve Team Suffix.
     * <p>
     * Notes: only if Mode = 0 or 2. Displayed after the players' name that are
     * part of this team.
     *
     * @return The current Team Suffix
     */
    public abstract String getSuffix();

    /**
     * Set Team Suffix.
     *
     * @param value - new value.
     */
    public abstract void setSuffix(String value);

    /**
     * Retrieve Name Tag Visibility.
     * <p>
     * Notes: only if Mode = 0 or 2. always, hideForOtherTeams, hideForOwnTeam,
     * never.
     *
     * @return The current Name Tag Visibility
     */
    public abstract String getNameTagVisibility();

    /**
     * Set Name Tag Visibility.
     *
     * @param value - new value.
     */
    public abstract void setNameTagVisibility(String value);

    /**
     * Retrieve Color.
     * <p>
     * Notes: only if Mode = 0 or 2. Same as Chat colors.
     *
     * @return The current Color
     */
    public abstract ChatColor getColor();

    /**
     * Set Color.
     *
     * @param value - new value.
     */
    public abstract void setColor(ChatColor value);

    /**
     * Get the collision rule.
     * Notes: only if Mode = 0 or 2. always, pushOtherTeams, pushOwnTeam, never.
     * @return The current collision rule
     */
    public abstract String getCollisionRule();

    /**
     * Sets the collision rule.
     * @param value - new value.
     */
    public abstract void setCollisionRule(String value);

    /**
     * Retrieve Players.
     * <p>
     * Notes: only if Mode = 0 or 3 or 4. Players to be added/remove from the
     * team. Max 40 characters so may be uuid's later
     *
     * @return The current Players
     */
    public abstract List<String> getPlayers();

    /**
     * Set Players.
     *
     * @param value - new value.
     */
    public abstract void setPlayers(List<String> value);

    public void addPlayer(String player) {
        List<String> players = this.getPlayers();
        players.add(player);

        this.setPlayers(players);
    }

    public void removePlayer(String player) {
        List<String> players = this.getPlayers();
        players.add(player);

        this.setPlayers(players);
    }

    /**
     * Retrieve Mode.
     * <p>
     * Notes: if 0 then the team is created. If 1 then the team is removed. If 2
     * the team team information is updated. If 3 then new players are added to
     * the team. If 4 then players are removed from the team.
     *
     * @return The current Mode
     */
    public abstract int getMode();

    /**
     * Set Mode.
     *
     * @param value - new value.
     */
    public abstract void setMode(int value);

    /**
     * Retrieve pack option data. Pack data is calculated as follows:
     *
     * <pre>
     * <code>
     * int data = 0;
     * if (team.allowFriendlyFire()) {
     *     data |= 1;
     * }
     * if (team.canSeeFriendlyInvisibles()) {
     *     data |= 2;
     * }
     * </code>
     * </pre>
     *
     * @return The current pack option data
     */
    public abstract int getPackOptionData();

    /**
     * Set pack option data.
     *
     * @param value - new value
     * @see #getPackOptionData()
     */
    public abstract void setPackOptionData(int value);

}

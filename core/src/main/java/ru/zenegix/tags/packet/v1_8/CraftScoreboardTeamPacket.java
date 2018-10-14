package ru.zenegix.tags.packet.v1_8;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.ChatColor;
import ru.zenegix.tags.Utils;
import ru.zenegix.tags.packet.ScoreboardTeamPacket;

import java.util.Collection;
import java.util.List;

public class CraftScoreboardTeamPacket extends ScoreboardTeamPacket {

    public static final PacketType TYPE =
            PacketType.Play.Server.SCOREBOARD_TEAM;

    public CraftScoreboardTeamPacket() {
        super(new PacketContainer(TYPE), TYPE);

        this.handle.getModifier().writeDefaults();
    }

    public CraftScoreboardTeamPacket(PacketContainer packet) {
        super(packet, TYPE);
    }

    @Override
    public String getName() {
        return this.handle.getStrings().read(0);
    }

    @Override
    public void setName(String value) {
        this.handle.getStrings().write(0, value);
    }

    @Override
    public String getDisplayName() {
        return this.handle.getStrings().read(1);
    }

    @Override
    public void setDisplayName(String value) {
        this.handle.getStrings().write(1, value);
    }

    @Override
    public String getPrefix() {
        return this.handle.getStrings().read(2);
    }

    @Override
    public void setPrefix(String value) {
        this.handle.getStrings().write(2, Utils.substring(value, 16));
    }

    @Override
    public String getSuffix() {
        return this.handle.getStrings().read(3);
    }

    @Override
    public void setSuffix(String value) {
        this.handle.getStrings().write(3, Utils.substring(value, 16));
    }

    @Override
    public String getNameTagVisibility() {
        return this.handle.getStrings().read(4);
    }

    @Override
    public void setNameTagVisibility(String value) {
        this.handle.getStrings().write(4, value);
    }

    @Override
    public ChatColor getColor() {
        int id = this.handle.getIntegers().read(0);

        return ChatColor.getByChar((char) (id < 10 ? ('0' + id) : ('a' + id - 10)));
    }

    @Override
    public void setColor(ChatColor value) {
        char c = value.getChar();

        this.handle.getIntegers().write(0, (c >= 'a' ? c - 'a': c - '9' - 1) + 10);
    }

    @Override
    public String getCollisionRule() {
        return this.handle.getStrings().read(5);
    }

    @Override
    public void setCollisionRule(String value) {
        this.handle.getStrings().write(5, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getPlayers() {
        return (List<String>) this.handle.getSpecificModifier(Collection.class)
                .read(0);
    }

    @Override
    public void setPlayers(List<String> value) {
        this.handle.getSpecificModifier(Collection.class).write(0, value);
    }

    @Override
    public int getMode() {
        return this.handle.getIntegers().read(1);
    }

    @Override
    public void setMode(int value) {
        this.handle.getIntegers().write(1, value);
    }

    @Override
    public int getPackOptionData() {
        return this.handle.getIntegers().read(2);
    }

    @Override
    public void setPackOptionData(int value) {
        this.handle.getIntegers().write(2, value);
    }

}

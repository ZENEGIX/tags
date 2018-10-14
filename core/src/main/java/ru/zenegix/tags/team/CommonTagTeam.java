package ru.zenegix.tags.team;

import org.bukkit.entity.Player;
import ru.zenegix.tags.TagManager;
import ru.zenegix.tags.Utils;
import ru.zenegix.tags.packet.ScoreboardTeamPacket;
import ru.zenegix.tags.render.Renderable;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class CommonTagTeam extends AbstractTagTeam {

    private final String name;

    private final ScoreboardTeamPacket createPacket;

    private static final AtomicInteger NEXT_ID = new AtomicInteger(0);

    public CommonTagTeam(TagManager ownerManager, Renderable prefix, Renderable suffix, List<String> players, int priority) {
        super(ownerManager, prefix, suffix, players, priority);

        this.name = this.getPriority() + "Tag_" + NEXT_ID.getAndIncrement();
        this.createPacket = this.createPacket();
        this.createPacket.setMode(ScoreboardTeamPacket.Mode.TEAM_CREATED);
        this.createPacket.setNameTagVisibility("ALWAYS");

        this.update();
    }

    @Override
    public boolean addMember(String player) {
        return this.modifyMembers(player, super::addMember, ScoreboardTeamPacket.Mode.PLAYERS_ADDED);
    }

    @Override
    public boolean removeMember(String player) {
        return this.modifyMembers(player, super::removeMember, ScoreboardTeamPacket.Mode.PLAYERS_REMOVED);
    }

    @Override
    public void update() {
        String prefix = Utils.render(this.getPrefix());
        String suffix = Utils.render(this.getSuffix());
        ScoreboardTeamPacket packet = this.createPacket();
        boolean needReceive = false;

        if (prefix != null) {
            packet.setPrefix(prefix);
            this.createPacket.setPrefix(prefix);

            needReceive = true;
        }

        if (suffix != null) {
            packet.setSuffix(suffix);
            this.createPacket.setSuffix(suffix);

            needReceive = true;
        }

        if (needReceive) {
            packet.setMode(ScoreboardTeamPacket.Mode.TEAM_UPDATED);
            packet.broadcastPacket();
        }
    }

    @Override
    public void show(Player player) {
        this.createPacket.sendPacket(player);
    }

    @Override
    protected ScoreboardTeamPacket createPacket() {
        ScoreboardTeamPacket packet = super.createPacket();
        packet.setName(this.name);
        packet.setDisplayName(this.name);

        return packet;
    }

    private boolean modifyMembers(String player, Predicate<String> predicate, int mode) {
        if (!predicate.test(player)) {
            return false;
        }

        ScoreboardTeamPacket packet = this.createPacket();
        packet.setMode(mode);
        packet.setPlayers(Collections.singletonList(player));
        packet.broadcastPacket();

        this.createPacket.setPlayers(this.members);

        return true;
    }

}

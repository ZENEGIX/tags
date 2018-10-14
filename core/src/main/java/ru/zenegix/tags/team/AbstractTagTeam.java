package ru.zenegix.tags.team;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import ru.zenegix.tags.TagManager;
import ru.zenegix.tags.Utils;
import ru.zenegix.tags.packet.ScoreboardTeamPacket;
import ru.zenegix.tags.render.Renderable;

import java.util.List;

public abstract class AbstractTagTeam implements TagTeam, Runnable {

    protected final TagManager ownerManager;

    private final Renderable prefix, suffix;

    private int updatePeriod;

    private BukkitTask updateTask;

    protected List<String> members;

    private final char priority;

    public AbstractTagTeam(TagManager ownerManager, Renderable prefix, Renderable suffix, List<String> members, int priority) {
        this.ownerManager = ownerManager;
        this.prefix = prefix;
        this.suffix = suffix;
        this.members = members;
        this.priority = Utils.parsePriority(priority);

        this.ownerManager.registerTeam(this);
    }

    @Override
    public Renderable getPrefix() {
        return this.prefix;
    }

    @Override
    public Renderable getSuffix() {
        return this.suffix;
    }

    @Override
    public int getUpdatePeriod() {
        return this.updatePeriod;
    }

    @Override
    public void setUpdatePeriod(int updatePeriod) {
        if (this.updateTask != null) {
            this.updateTask.cancel();
        }

        if ((this.updatePeriod = updatePeriod) > 0) {
            this.updateTask = Bukkit.getScheduler().runTaskTimer(
                    this.ownerManager.getPlugin(),
                    this,
                    0,
                    this.updatePeriod
            );
        }
    }

    @Override
    public boolean addMember(String player) {
        this.ownerManager.removeMemberFromAllTeams(player);

        return this.members.add(player);
    }

    @Override
    public boolean removeMember(String player) {
        return this.members.remove(player);
    }

    @Override
    public boolean isMember(String player) {
        return this.members.contains(player);
    }

    @Override
    public void run() {
        this.update();
    }

    protected ScoreboardTeamPacket createPacket() {
        return this.ownerManager.getScoreboardTeamPacketFactory().create();
    }

    protected char getPriority() {
        return this.priority;
    }

}

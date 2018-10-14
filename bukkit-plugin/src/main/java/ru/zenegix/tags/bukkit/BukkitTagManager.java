package ru.zenegix.tags.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import ru.zenegix.tags.TagManager;
import ru.zenegix.tags.Utils;
import ru.zenegix.tags.packet.ScoreboardTeamPacketFactory;
import ru.zenegix.tags.team.TagTeam;
import ru.zenegix.tags.team.TagTeamBuilder;

import java.util.Collection;
import java.util.HashSet;

public class BukkitTagManager implements TagManager, Listener {

    private final Plugin plugin;

    private final ScoreboardTeamPacketFactory scoreboardTeamPacketFactory;

    private final Collection<TagTeam> registeredTeams;

    private final TagTeamBuilder builder;

    public BukkitTagManager(Plugin plugin) {
        this(plugin, Utils.createCurrentFactory());
    }

    public BukkitTagManager(Plugin plugin, ScoreboardTeamPacketFactory scoreboardTeamPacketFactory) {
        this.scoreboardTeamPacketFactory = scoreboardTeamPacketFactory;
        this.registeredTeams = new HashSet<>();
        this.builder = new TagTeamBuilder(this);

        Bukkit.getPluginManager().registerEvents(this, this.plugin = plugin);
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public ScoreboardTeamPacketFactory getScoreboardTeamPacketFactory() {
        return this.scoreboardTeamPacketFactory;
    }

    public void registerTeam(TagTeam tagTeam) {
        this.registeredTeams.add(tagTeam);
    }

    public void unregisterTeam(TagTeam tagTeam) {
        this.registeredTeams.remove(tagTeam);
    }

    public TagTeamBuilder.CommonTagTeamBuilder commonTagTeamBuilder() {
        return this.builder.commonTagTeamBuilder();
    }

    public TagTeamBuilder.DynamicTagTeamBuilder dynamicTagTeamBuilder() {
        return this.builder.dynamicTagTeamBuilder();
    }

    public void removeMemberFromAllTeams(String player) {
        this.registeredTeams.forEach(team -> team.removeMember(player));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        this.registeredTeams.forEach(team -> {
            team.show(event.getPlayer());
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.removeMemberFromAllTeams(event.getPlayer().getName());
    }

}

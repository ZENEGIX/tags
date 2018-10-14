package ru.zenegix.tags.example;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.zenegix.tags.TagManager;
import ru.zenegix.tags.bukkit.LoneyTags;
import ru.zenegix.tags.render.CachedRender;
import ru.zenegix.tags.render.DynamicRender;
import ru.zenegix.tags.team.TagTeam;

public class TagExamplePlugin extends JavaPlugin implements Listener {

    private TagTeam tagTeam;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        TagManager tagManager = LoneyTags.getTagManager();

        boolean dynamic = false; // Change it, if you want test a dynamic c:
        boolean changeText = false; // Change it, if you want a test a change prefix and suffix c:

        if (dynamic) {
            this.tagTeam = tagManager.dynamicTagTeamBuilder().setUpdatePeriod(1).setPriority(1).setPrefix(data -> {
                Player player = data.getPlayer();
                Player receiver = data.getReceiver();

                return player == receiver
                        ? "Me "
                        : "Not me ";
            }).setSuffix(data -> {
                Player player = data.getPlayer();
                Player receiver = data.getReceiver();

                return player == receiver
                        ? null
                        : " D: " + Math.floor(player.getLocation().distance(receiver.getLocation()));
            }).build();
        } else {
            this.tagTeam = tagManager.commonTagTeamBuilder()
                    .setPriority(1)
                    .setPrefix("Player ")
                    .setSuffix(" &6*")
                    .build();
        }

        // We add a color to prefix
        if (changeText) {
            if (dynamic) {
                ((DynamicRender) this.tagTeam.getPrefix()).setTextFunction(data -> {
                    Player player = data.getPlayer();
                    Player receiver = data.getReceiver();

                    return player == receiver
                            ? "&fMe &a"
                            : "&fNot me &c";
                });
            } else {
                ((CachedRender) this.tagTeam.getPrefix()).setText("&fPlayer &7");
            }

            // Forced update, because there is no guarantee that the task is to update
            this.tagTeam.update();
        }

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.tagTeam.addMember(event.getPlayer().getName());
    }

}

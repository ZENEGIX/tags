package ru.zenegix.tags.render;

import org.bukkit.entity.Player;

public class RenderData {

    private Player player, receiver;

    public RenderData(Player player, Player receiver) {
        this.player = player;
        this.receiver = receiver;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getReceiver() {
        return this.receiver;
    }

    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }

}

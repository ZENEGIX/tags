package ru.zenegix.tags.team;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.zenegix.tags.TagManager;
import ru.zenegix.tags.Utils;
import ru.zenegix.tags.packet.ScoreboardTeamPacket;
import ru.zenegix.tags.render.RenderData;
import ru.zenegix.tags.render.Renderable;

import java.util.Collections;
import java.util.List;

/**
 * Тима, которая для каждого каждой пары игрока и получателя создает свою тиму
 * Сделано для того, чтобы можно было более гибко настроить префиксы/суффиксы игроку
 *
 * Название такого пакета создается так: '{priority}{player_name_hash}', где
 *     {priority} - символ приоритета
 *     {player_name_hash} - хеш ника игрока
 *
 * Написано на русском, потому что лень переводить на английский :c
 */
public class DynamicTagTeam extends AbstractTagTeam {

    public DynamicTagTeam(TagManager ownerManager, Renderable prefix, Renderable suffix, List<String> members, int priority) {
        super(ownerManager, prefix, suffix, members, priority);
    }

    @Override
    public boolean addMember(String member) {
        if (!super.addMember(member)) {
            return false;
        }

        Player player = Bukkit.getPlayer(member);

        for (Player receiver : Bukkit.getOnlinePlayers()) {
            this.show(player, receiver);
        }

        return true;
    }

    @Override
    public boolean removeMember(String member) {
        if (!super.removeMember(member)) {
            return false;
        }

        Player player = Bukkit.getPlayer(member);
        ScoreboardTeamPacket packet = this.createPacket(player);
        packet.setMode(ScoreboardTeamPacket.Mode.TEAM_REMOVED);
        packet.broadcastPacket();

        return true;
    }

    @Override
    public void show(Player receiver) {
        for (String member : this.members) {
            Player player = Bukkit.getPlayer(member);

            this.show(player, receiver);
        }
    }

    @Override
    public void update() {
        for (String member : this.members) {
            Player player = Bukkit.getPlayer(member);

            for (Player receiver : Bukkit.getOnlinePlayers()) {
                this.update(player, receiver);
            }
        }
    }

    private void show(Player player, Player receiver) {
        ScoreboardTeamPacket packet = this.prepareSend(player, receiver, ScoreboardTeamPacket.Mode.TEAM_CREATED);

        if (packet != null) {
            packet.setPlayers(Collections.singletonList(player.getName()));
            packet.setNameTagVisibility("ALWAYS");
            packet.sendPacket(receiver);
        }
    }

    private void update(Player player, Player receiver) {
        ScoreboardTeamPacket packet = this.prepareSend(player, receiver, ScoreboardTeamPacket.Mode.TEAM_UPDATED);

        if (packet != null) {
            packet.sendPacket(receiver);
        }
    }

    private ScoreboardTeamPacket prepareSend(Player player, Player receiver, int mode) {
        ScoreboardTeamPacket packet = this.createPacket(player);

        if (!this.setPrefixAndSuffix(packet, player, receiver)) {
            return null;
        }

        packet.setMode(mode);

        return packet;
    }

    private boolean setPrefixAndSuffix(ScoreboardTeamPacket packet, Player player, Player receiver) {
        RenderData data = new RenderData(player, receiver);
        String prefix = Utils.render(this.getPrefix(), data);
        String suffix = Utils.render(this.getSuffix(), data);
        boolean result = false;

        if (prefix != null) {
            packet.setPrefix(prefix);
            result = true;
        }

        if (suffix != null) {
            packet.setSuffix(suffix);
            result = true;
        }

        return result;
    }

    private ScoreboardTeamPacket createPacket(Player player) {
        ScoreboardTeamPacket packet = super.createPacket();
        packet.setName(this.getPriority() + "" + player.getName().hashCode());

        return packet;
    }

}

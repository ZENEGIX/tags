package ru.zenegix.tags.packet.v1_8;

import ru.zenegix.tags.packet.ScoreboardTeamPacket;
import ru.zenegix.tags.packet.ScoreboardTeamPacketFactory;

public class CraftScoreboardTeamPacketFactory implements ScoreboardTeamPacketFactory {

    @Override
    public ScoreboardTeamPacket create() {
        return new CraftScoreboardTeamPacket();
    }

}

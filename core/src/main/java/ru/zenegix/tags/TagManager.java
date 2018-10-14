package ru.zenegix.tags;

import org.bukkit.plugin.Plugin;
import ru.zenegix.tags.packet.ScoreboardTeamPacketFactory;
import ru.zenegix.tags.team.TagTeam;
import ru.zenegix.tags.team.TagTeamBuilder;

public interface TagManager {

    Plugin getPlugin();

    ScoreboardTeamPacketFactory getScoreboardTeamPacketFactory();

    void registerTeam(TagTeam tagTeam);

    void unregisterTeam(TagTeam tagTeam);

    TagTeamBuilder.CommonTagTeamBuilder commonTagTeamBuilder();

    TagTeamBuilder.DynamicTagTeamBuilder dynamicTagTeamBuilder();

    void removeMemberFromAllTeams(String player);

}

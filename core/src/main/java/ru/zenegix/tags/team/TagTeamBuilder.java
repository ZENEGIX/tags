package ru.zenegix.tags.team;

import org.bukkit.entity.Player;
import ru.zenegix.tags.TagManager;
import ru.zenegix.tags.render.CachedRender;
import ru.zenegix.tags.render.DynamicRender;
import ru.zenegix.tags.render.RenderData;
import ru.zenegix.tags.render.Renderable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TagTeamBuilder {

    private final TagManager tagManager;

    public TagTeamBuilder(TagManager tagManager) {
        this.tagManager = tagManager;
    }

    public CommonTagTeamBuilder commonTagTeamBuilder() {
        return new CommonTagTeamBuilder(this.tagManager);
    }

    public DynamicTagTeamBuilder dynamicTagTeamBuilder() {
        return new DynamicTagTeamBuilder(this.tagManager);
    }

    public static class DynamicTagTeamBuilder extends AbstractTagTeamBuilder<DynamicTagTeamBuilder, DynamicTagTeam> {

        private final TagManager tagManager;

        private Renderable prefix, suffix;

        public DynamicTagTeamBuilder(TagManager tagManager) {
            this.tagManager = tagManager;
        }

        public DynamicTagTeamBuilder setPrefix(Function<RenderData, Object> prefix) {
            this.prefix = new DynamicRender(prefix);

            return this;
        }

        public DynamicTagTeamBuilder setSuffix(Function<RenderData, Object> suffix) {
            this.suffix = new DynamicRender(suffix);

            return this;
        }

        public DynamicTagTeam build() {
            return this.finish(new DynamicTagTeam(
                    this.tagManager,
                    this.prefix,
                    this.suffix,
                    this.players,
                    this.priority
            ));
        }
    }

    public static class CommonTagTeamBuilder extends AbstractTagTeamBuilder<CommonTagTeamBuilder, CommonTagTeam> {

        private final TagManager tagManager;

        private String prefix, suffix;

        public CommonTagTeamBuilder(TagManager tagManager) {
            this.tagManager = tagManager;
        }

        public CommonTagTeamBuilder setPrefix(String prefix) {
            this.prefix = prefix;

            return this;
        }

        public CommonTagTeamBuilder setSuffix(String suffix) {
            this.suffix = suffix;

            return this;
        }

        public CommonTagTeam build() {
            return this.finish(new CommonTagTeam(
                    this.tagManager,
                    this.prefix == null ? null : new CachedRender(this.prefix),
                    this.suffix == null ? null : new CachedRender(this.suffix),
                    this.players,
                    this.priority
            ));
        }

    }

    public static class AbstractTagTeamBuilder<B extends AbstractTagTeamBuilder<B, T>, T extends AbstractTagTeam> {

        List<String> players = new ArrayList<>();

        int updatePeriod = 0, priority = 26;

        public B setPriority(int priority) {
            this.priority = priority;

            return (B) this;
        }

        public B addPlayers(String... players) {
            this.players.addAll(Arrays.asList(players));

            return (B) this;
        }

        public B addPlayers(Player... players) {
            for (Player player : players) {
                this.players.add(player.getName());
            }

            return (B) this;
        }

        public B setUpdatePeriod(int updatePeriod) {
            this.updatePeriod = updatePeriod;

            return (B) this;
        }

        public T finish(T instance) {
            instance.setUpdatePeriod(this.updatePeriod);

            return instance;
        }

    }

}

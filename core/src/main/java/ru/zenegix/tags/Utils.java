package ru.zenegix.tags;

import org.bukkit.Bukkit;
import ru.zenegix.tags.packet.ScoreboardTeamPacketFactory;
import ru.zenegix.tags.render.RenderData;
import ru.zenegix.tags.render.Renderable;

import java.lang.reflect.Constructor;

public class Utils {

    private static Constructor<?> constructor;

    public static ScoreboardTeamPacketFactory createCurrentFactory() {
        try {
            if (constructor == null) {
                initConstructor();
            }

            return (ScoreboardTeamPacketFactory) constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void initConstructor() {
        try {
            String name = Bukkit.getServer().getClass().getPackage().getName();

            constructor = Integer.parseInt((name.substring(name.lastIndexOf('.') + 1) + ".").split("_")[1]) == 13
                    ? ru.zenegix.tags.packet.v1_13.CraftScoreboardTeamPacketFactory.class.getConstructor()
                    : ru.zenegix.tags.packet.v1_8.CraftScoreboardTeamPacketFactory.class.getConstructor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String substring(String target, int maxLength) {
        return target.length() > maxLength
                ? target.substring(0, maxLength)
                : target;
    }

    public static String render(Renderable renderable) {
        return render(renderable, null);
    }

    public static String render(Renderable renderable, RenderData data) {
        return renderable == null
                ? null
                : renderable.render(data);
    }

    public static char parsePriority(int priority) {
        if (priority < 1) {
            priority = 1;
        }

        if (priority > 26) {
            priority = 26;
        }

        return (char) ('A' + priority - 1);
    }

}

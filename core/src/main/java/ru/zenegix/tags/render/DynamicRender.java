package ru.zenegix.tags.render;

import org.bukkit.ChatColor;

import java.util.function.Function;

public class DynamicRender implements Renderable {

    // Object, because I am want, that user can return number value without casting to String
    private Function<RenderData, Object> textFunction;

    public DynamicRender(Function<RenderData, Object> textFunction) {
        this.textFunction = textFunction;
    }

    public void setTextFunction(Function<RenderData, Object> textFunction) {
        this.textFunction = textFunction;
    }

    @Override
    public String render(RenderData data) {
        Object result = this.textFunction.apply(data);

        return result == null
                ? null
                : ChatColor.translateAlternateColorCodes('&', result.toString());
    }

}

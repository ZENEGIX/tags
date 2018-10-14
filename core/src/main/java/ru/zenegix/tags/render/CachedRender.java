package ru.zenegix.tags.render;

import org.bukkit.ChatColor;

public class CachedRender implements Renderable {

    private String text;

    public CachedRender(String text) {
        this.setText(text);
    }

    public void setText(String text) {
        this.text = ChatColor.translateAlternateColorCodes('&', text);
    }

    @Override
    public String render(RenderData data) {
        return this.text;
    }

}

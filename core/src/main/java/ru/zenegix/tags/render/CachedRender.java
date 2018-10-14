package ru.zenegix.tags.render;

public class CachedRender implements Renderable {

    private String text;

    public CachedRender(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String render(RenderData data) {
        return this.text;
    }

}

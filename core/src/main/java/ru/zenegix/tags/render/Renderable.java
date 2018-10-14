package ru.zenegix.tags.render;

public interface Renderable {

    /**
     * Render a some text for given render data
     *
     * @param data the render data
     *
     * @return rendered text
     */
    String render(RenderData data);

}

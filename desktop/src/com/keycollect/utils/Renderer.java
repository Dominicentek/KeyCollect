package com.keycollect.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.keycollect.Main;

import java.awt.*;

public class Renderer extends SpriteBatch {
    private static Texture pixel;
    static {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.drawPixel(0, 0, 0xFFFFFFFF);
        pixel = new Texture(pixmap);
    }
    public void setColor(int rgba) {
        setColor(((rgba >> 24) & 0xFF) / 255f, ((rgba >> 16) & 0xFF) / 255f, ((rgba >> 8) & 0xFF) / 255f, (rgba & 0xFF) / 255f);
    }
    public void draw(Texture texture, int x, int y) {
        draw(texture, x, y, texture.getWidth(), texture.getHeight(), false, false);
    }
    public void draw(Texture texture, int x, int y, int width, int height) {
        draw(texture, x, y, width, height, false, false);
    }
    public void draw(Texture texture, int x, int y, boolean flipX, boolean flipY) {
        draw(texture, x, y, texture.getWidth(), texture.getHeight(), flipX, flipY);
    }
    public void draw(Texture texture, int x, int y, int width, int height, boolean flipX, boolean flipY) {
        draw(texture, new Rectangle(0, 0, texture.getWidth(), texture.getHeight()), x, y, width, height, flipX, flipY);
    }
    public void draw(Texture texture, Rectangle bounds, int x, int y) {
        draw(texture, bounds, x, y, bounds.width, bounds.height, false, false);
    }
    public void draw(Texture texture, Rectangle bounds, int x, int y, int width, int height) {
        draw(texture, bounds, x, y, width, height, false, false);
    }
    public void draw(Texture texture, Rectangle bounds, int x, int y, boolean flipX, boolean flipY) {
        draw(texture, bounds, x, y, bounds.width, bounds.height, flipX, flipY);
    }
    public void draw(Texture texture, Rectangle bounds, int x, int y, int width, int height, boolean flipX, boolean flipY) {
        y = Main.height - y - height;
        draw(texture, x, y, width, height, bounds.x, bounds.y, bounds.width, bounds.height, flipX, flipY);
    }
    public void fillrect(int x, int y, int width, int height) {
        draw(pixel, x, y, width, height);
    }
    public void fillrect(int x, int y, int width, int height, int color) {
        Color prevColor = getColor();
        setColor(color);
        fillrect(x, y, width, height);
        setColor(prevColor);
    }
    public void drawrect(int x, int y, int width, int height) {
        draw(pixel, x, y, width, 1);
        draw(pixel, x, y, 1, height);
        draw(pixel, x + width - 1, y, 1, height);
        draw(pixel, x, y + height - 1, width, 1);
    }
    public void drawrect(int x, int y, int width, int height, int color) {
        Color prevColor = getColor();
        setColor(color);
        drawrect(x, y, width, height);
        setColor(prevColor);
    }
}

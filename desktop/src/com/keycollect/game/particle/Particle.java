package com.keycollect.game.particle;

import com.badlogic.gdx.graphics.Texture;
import com.keycollect.game.Game;

import java.awt.*;

public abstract class Particle {
    public int x;
    public int y;
    public Texture texture;
    public Rectangle textureRegion;
    public Particle(Texture texture) {
        this.texture = texture;
        textureRegion = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
    }
    public abstract void init();
    public abstract void update();
    public final void remove() {
        Game.particles.remove(this);
    }
}

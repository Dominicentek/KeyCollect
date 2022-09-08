package com.keycollect.game.particle;

import com.keycollect.loader.Loader;

import java.awt.*;

public class KeyCollectParticle extends Particle {
    public int state = 0;
    public int timeout = 2;
    public KeyCollectParticle() {
        super(Loader.get("keycollect"));
        textureRegion = new Rectangle(0, 0, 16, 16);
    }
    public void init() {}
    public void update() {
        timeout--;
        if (timeout == 0) {
            timeout = 2;
            state++;
            if (state == 13) remove();
            textureRegion = new Rectangle(state % 7 * 16, state / 7 * 16, 16, 16);
        }
    }
}

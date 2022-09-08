package com.keycollect.game.particle;

import com.badlogic.gdx.graphics.Texture;
import com.keycollect.game.Game;
import com.keycollect.loader.Loader;

import java.util.Random;

public class DeathParticleSpawner extends Particle {
    public boolean moveToSpawn;
    public DeathParticleSpawner(boolean moveToSpawn) {
        super(Loader.get("nothing"));
        this.moveToSpawn = moveToSpawn;
    }
    public void init() {
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            Game.addParticle(new DeathParticle(random.nextDouble() * 3 - 1.5, random.nextDouble() * -2 - 4, moveToSpawn), x, y);
        }
        remove();
    }
    public void update() {}
}

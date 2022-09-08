package com.keycollect.game.particle;

import com.badlogic.gdx.graphics.Texture;
import com.keycollect.game.Game;
import com.keycollect.game.Level;
import com.keycollect.game.player.Physics;
import com.keycollect.loader.Loader;

import java.awt.*;

public class DeathParticle extends Particle {
    public double speedX;
    public double speedY;
    public double X;
    public double Y;
    public int assembleTimeout = 60;
    public int dstX;
    public int dstY;
    public int srcX;
    public int srcY;
    public boolean moveToSpawn = false;
    public DeathParticle(double speedX, double speedY, boolean moveToSpawn) {
        super(Loader.get("keycollect"));
        textureRegion = new Rectangle(32, 0, 16, 16);
        this.speedX = speedX;
        this.speedY = speedY;
        dstX = Game.LEVELS[Game.currentLevel].spawnPosX * 16;
        dstY = Game.LEVELS[Game.currentLevel].spawnPosY * 16;
        this.moveToSpawn = moveToSpawn;
    }
    public void init() {
        X = x;
        Y = y;
    }
    public void update() {
        if (assembleTimeout == 0) {
            double speedX = (dstX - srcX) / 60.0;
            double speedY = (dstY - srcY) / 60.0;
            X += speedX;
            Y += speedY;
        }
        else {
            if (moveToSpawn) assembleTimeout--;
            X += speedX;
            Y += speedY;
            speedY += Physics.gravity / 100.0;
            srcX = (int)X;
            srcY = (int)Y;
        }
        x = (int)X;
        y = (int)Y;
    }
}

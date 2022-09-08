package com.keycollect.game.player;

import com.keycollect.game.Game;

import java.awt.*;

public class Player {
    public static final int hitboxWidth = 1200;
    public static final int hitboxHeight = 1400;
    public static int x;
    public static int y;
    public static boolean dead = false;
    public static void reset() {
        x = Game.LEVELS[Game.currentLevel].spawnPosX * 1600 +  800;
        y = Game.LEVELS[Game.currentLevel].spawnPosY * 1600 + 1600;
        Physics.speedX = 0;
        Physics.speedY = 0;
        dead = false;
    }
    public static Rectangle hitbox() {
        return new Rectangle(x - hitboxWidth / 2, y - hitboxHeight, hitboxWidth, hitboxHeight);
    }
}

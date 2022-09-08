package com.keycollect.game.player;

import com.keycollect.game.Game;
import com.keycollect.game.Level;

import java.awt.*;

public class Physics {
    public static int speedX = 0;
    public static int speedY = 0;
    public static boolean inAir = false;
    public static boolean moveLeft = false;
    public static boolean moveRight = false;
    public static final int gravity = 20;
    public static final int acceleration = 10;
    public static final int maxSpeed = 150;
    public static boolean[][] collisionMap = new boolean[Level.WIDTH][Level.HEIGHT];
    public static void update() {
        if (!Game.move) return;
        if (moveLeft && !moveRight) {
            speedX -= acceleration;
            if (speedX < -maxSpeed) speedX = -maxSpeed;
        }
        else if (moveRight && !moveLeft) {
            speedX += acceleration;
            if (speedX > maxSpeed) speedX = maxSpeed;
        }
        else {
            if (speedX > 0) {
                speedX -= acceleration;
                if (speedX < 0) speedX = 0;
            }
            if (speedX < 0) {
                speedX += acceleration;
                if (speedX > 0) speedX = 0;
            }
        }
        Player.x += speedX;
        for (int x = 0; x < Level.WIDTH; x++) {
            for (int y = 0; y < Level.HEIGHT; y++) {
                if (!collisionMap[x][y]) continue;
                Rectangle tileHitbox = new Rectangle(x * 1600, y * 1600, 1600, 1600);
                Rectangle hitbox = Player.hitbox();
                if (tileHitbox.intersects(hitbox)) {
                    Rectangle leftHitbox = new Rectangle(hitbox.x, hitbox.y, 1, hitbox.height);
                    Player.x = (leftHitbox.intersects(tileHitbox) ? tileHitbox.x + tileHitbox.width : tileHitbox.x - hitbox.width) + Player.hitboxWidth / 2;
                    speedX = 0;
                }
            }
        }
        speedY += gravity;
        Player.y += speedY;
        inAir = true;
        for (int x = 0; x < Level.WIDTH; x++) {
            for (int y = 0; y < Level.HEIGHT; y++) {
                if (!collisionMap[x][y]) continue;
                Rectangle tileHitbox = new Rectangle(x * 1600, y * 1600, 1600, 1600);
                Rectangle hitbox = Player.hitbox();
                if (tileHitbox.intersects(hitbox)) {
                    Rectangle upHitbox = new Rectangle(hitbox.x, hitbox.y, hitbox.width, 1);
                    Player.y = (upHitbox.intersects(tileHitbox) ? tileHitbox.y + tileHitbox.height : tileHitbox.y - hitbox.height) + Player.hitboxHeight;
                    speedY = 0;
                    inAir = false;
                }
            }
        }
        for (int x = 0; x < Level.WIDTH; x++) {
            for (int y = 0; y < Level.HEIGHT; y++) {
                Rectangle tileHitbox = new Rectangle(x * 1600, y * 1600, 1600, 1600);
                Rectangle hitbox = Player.hitbox();
                if (tileHitbox.intersects(hitbox)) {
                    Game.LEVELS[Game.currentLevel].tiles[x][y].touchEvent.touched(x, y);
                }
            }
        }
        if (Player.x - Player.hitboxWidth / 2 < 0) {
            Player.x = Player.hitboxWidth / 2;
            speedX = 0;
        }
        if (Player.x + Player.hitboxWidth / 2 > Level.WIDTH * 1600) {
            Player.x = Level.WIDTH * 1600 - Player.hitboxWidth / 2;
            speedX = 0;
        }
    }
    public static void jump() {
        if (inAir) return;
        Game.playSound("jump");
        speedY = -400;
    }
    public static void updateCollisionMap() {
        for (int x = 0; x < Level.WIDTH; x++) {
            for (int y = 0; y < Level.HEIGHT; y++) {
                collisionMap[x][y] = Game.LEVELS[Game.currentLevel].tiles[x][y].solid;
            }
        }
    }
}

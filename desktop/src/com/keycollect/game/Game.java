package com.keycollect.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.keycollect.Main;
import com.keycollect.game.particle.DeathParticleSpawner;
import com.keycollect.game.particle.KeyCollectParticle;
import com.keycollect.game.particle.Particle;
import com.keycollect.game.player.Physics;
import com.keycollect.game.player.Player;
import com.keycollect.loader.Loader;
import com.keycollect.utils.Renderer;

import java.util.ArrayList;

public class Game {
    public static final Level[] LEVELS = new Level[5];
    public static int currentLevel = 0;
    public static boolean[][] spotlightFreePixels = new boolean[Main.width][Main.height];
    public static boolean collectedKey = false;
    public static ArrayList<Particle> particles = new ArrayList<>();
    public static int deathTimeout = -1;
    public static int winTimeout = -1;
    public static boolean menu = true;
    public static boolean move = true;
    public static void render(Renderer renderer) {
        if (menu) {
            int buttonSize = 48;
            int buttonSpacing = 10;
            int logoOffset = (int)Math.round(Math.sin(System.currentTimeMillis() / 500.0) * 10);
            Texture texture = Loader.get("logo");
            renderer.draw(texture, Main.width / 2 - texture.getWidth() / 2, 35 + logoOffset);
            button(renderer, Main.width / 2 - buttonSize / 2 - buttonSpacing * 2 - buttonSize * 2, Main.height / 2, buttonSize, buttonSize, "num1", "negatedNum1", () -> Game.loadLevel(0));
            button(renderer, Main.width / 2 - buttonSize / 2 - buttonSpacing - buttonSize, Main.height / 2, buttonSize, buttonSize, "num2", "negatedNum2", () -> Game.loadLevel(1));
            button(renderer, Main.width / 2 - buttonSize / 2, Main.height / 2, buttonSize, buttonSize, "num3", "negatedNum3", () -> Game.loadLevel(2));
            button(renderer, Main.width / 2 - buttonSize / 2 - buttonSpacing * -1 - buttonSize * -1, Main.height / 2, buttonSize, buttonSize, "num4", "negatedNum4", () -> Game.loadLevel(3));
            button(renderer, Main.width / 2 - buttonSize / 2 - buttonSpacing * -2 - buttonSize * -2, Main.height / 2, buttonSize, buttonSize, "num5", "negatedNum5", () -> Game.loadLevel(4));
            return;
        }
        int floatingTileOffset = (int)Math.round(Math.sin(System.currentTimeMillis() / 250.0) * 2);
        for (int x = 0; x < Level.WIDTH; x++) {
            for (int y = 0; y < Level.HEIGHT; y++) {
                TileType tile = LEVELS[currentLevel].tiles[x][y];
                renderer.draw(tile.texture, x * 16, y * 16 + (tile.floats ? floatingTileOffset : 0));
            }
        }
        for (Particle particle : particles) {
            renderer.draw(particle.texture, particle.textureRegion, particle.x, particle.y);
        }
        renderer.setColor(0xFFFFFFFF);
        if (!Player.dead) renderer.fillrect((Player.x - Player.hitboxWidth / 2) / 100, (Player.y - Player.hitboxHeight) / 100, Player.hitboxWidth / 100, Player.hitboxHeight / 100);
        renderer.setColor(0x000000FF);
        for (int x = 0; x < Main.width; x++) {
            for (int y = 0; y < Main.height; y++) {
                if (!spotlightFreePixels[x][y]) renderer.fillrect(x, y, 1, 1);
            }
        }
        renderer.setColor(0xFF0000FF);
    }
    public static void update() {
        if (menu) return;
        ArrayList<Particle> particles = new ArrayList<>(Game.particles);
        for (Particle particle : particles) {
            particle.update();
        }
        Physics.moveLeft = Gdx.input.isKeyPressed(Input.Keys.A);
        Physics.moveRight = Gdx.input.isKeyPressed(Input.Keys.D);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) Physics.jump();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) menu = true;
        Physics.update();
        int spotlightRadius = 25;
        for (int x = -spotlightRadius; x < spotlightRadius; x++) {
            for (int y = -spotlightRadius; y < spotlightRadius; y++) {
                int X = Player.x / 100 + x;
                int Y = (Player.y - Player.hitboxHeight / 2) / 100 + y;
                if (X < 0 || X >= Main.width || Y < 0 || Y >= Main.height) continue;
                if (Math.sqrt(x * x + y * y) <= spotlightRadius) spotlightFreePixels[X][Y] = true;
            }
        }
        if (Player.dead) {
            deathTimeout--;
            if (deathTimeout == 0) reset();
        }
        if (winTimeout >= 0) {
            winTimeout--;
            if (winTimeout == 0) {
                menu = true;
            }
        }
    }
    public static void loadLevel(int levelID) {
        currentLevel = levelID;
        menu = false;
        reset();
        Physics.updateCollisionMap();
    }
    public static void die() {
        Player.dead = true;
        move = false;
        addParticle(new DeathParticleSpawner(true), Player.x / 100, Player.y / 100);
        deathTimeout = 120;
        playSound("death");
    }
    public static void reset() {
        for (int x = 0; x < Level.WIDTH; x++) {
            for (int y = 0; y < Level.HEIGHT; y++) {
                Game.LEVELS[Game.currentLevel].tiles[x][y] = Game.LEVELS[Game.currentLevel].origTiles[x][y];
            }
        }
        collectedKey = false;
        move = true;
        for (int x = 0; x < Main.width; x++) {
            for (int y = 0; y < Main.height; y++) {
                spotlightFreePixels[x][y] = false;
            }
        }
        particles.clear();
        Player.reset();
    }
    public static void addParticle(Particle particle, int x, int y) {
        particle.x = x;
        particle.y = y;
        particle.init();
        particles.add(particle);
    }
    public static void button(Renderer renderer, int x, int y, int width, int height, String iconName, String highlightIconName, Runnable action) {
        Texture icon;
        if (Main.mouseX >= x && Main.mouseY >= y && Main.mouseX < x + width && Main.mouseY < y + height) {
            renderer.fillrect(x, y, width, height);
            icon = Loader.get(highlightIconName);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) action.run();
        }
        else {
            renderer.drawrect(x, y, width, height);
            icon = Loader.get(iconName);
        }
        renderer.draw(icon, x + width / 2 - icon.getWidth() / 2, y + height / 2 - icon.getHeight() / 2);
    }
    public static void finish() {
        winTimeout = 60;
        addParticle(new DeathParticleSpawner(false), Player.x / 100, Player.y / 100);
        playSound("finish");
    }
    public static void playSound(String soundAssetName) {
        Loader.<Sound>get(soundAssetName).play();
    }
}
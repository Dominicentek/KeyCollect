package com.keycollect.game;

import com.badlogic.gdx.graphics.Texture;
import com.keycollect.game.particle.KeyCollectParticle;
import com.keycollect.loader.Loader;

public enum TileType {
    AIR("air", false, false, TileTouchEvent.NONE),
    GROUND("ground", false, true, TileTouchEvent.NONE),
    SPIKE("spike", false, false, (x, y) -> Game.die()),
    KEY("key", true, false, (x, y) -> {
        Game.collectedKey = true;
        Game.LEVELS[Game.currentLevel].tiles[x][y] = TileType.AIR;
        Game.addParticle(new KeyCollectParticle(), x * 16, y * 16);
        Game.playSound("collect");
    }),
    EXIT("exit", true, false, (x, y) -> {
        if (Game.collectedKey) {
            Game.LEVELS[Game.currentLevel].tiles[x][y] = TileType.AIR;
            Game.finish();
            Game.addParticle(new KeyCollectParticle(), x * 16, y * 16);
        }
    });
    public Texture texture;
    public final String textureAssetName;
    public final boolean floats;
    public final boolean solid;
    public final TileTouchEvent touchEvent;
    TileType(String textureAssetName, boolean floats, boolean solid, TileTouchEvent touchEvent) {
        this.textureAssetName = textureAssetName;
        this.floats = floats;
        this.solid = solid;
        this.touchEvent = touchEvent;
    }
    private void getTexture() {
        texture = Loader.<Texture>get(textureAssetName);
    }
    public static void getAllTextures() {
        for (TileType type : values()) {
            type.getTexture();
        }
    }
}

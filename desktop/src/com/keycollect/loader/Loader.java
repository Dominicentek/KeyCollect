package com.keycollect.loader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.keycollect.game.Game;

import java.util.HashMap;

public class Loader {
    private static final HashMap<String, Asset<?>> assets = new HashMap<>();
    public static void load() {
        for (String name : assets.keySet()) {
            assets.get(name).load();
        }
    }
    public static <T> T get(String name) {
        Asset<?> asset = assets.get(name);
        if (asset == null) return null;
        return (T)asset.asset;
    }
    static {
        try {
            assets.put("nothing", new ImageAsset("") {
                public Texture load(FileHandle file) {
                    return new Texture(1, 1, Pixmap.Format.RGBA8888);
                }
            });
            for (int i = 1; i <= Game.LEVELS.length; i++) {
                assets.put("level" + i, new LevelAsset("assets/levels/level" + i + ".lvl", i - 1));
            }
            assets.put("air", new ImageAsset("assets/images/air.png"));
            assets.put("ground", new ImageAsset("assets/images/ground.png"));
            assets.put("spike", new ImageAsset("assets/images/spike.png"));
            assets.put("key", new ImageAsset("assets/images/key.png"));
            assets.put("exit", new ImageAsset("assets/images/exit.png"));
            assets.put("keycollect", new ImageAsset("assets/images/keycollect.png"));
            assets.put("logo", new ImageAsset("assets/images/menu/logo.png"));
            assets.put("num1", new ImageAsset("assets/images/menu/num1.png"));
            assets.put("num2", new ImageAsset("assets/images/menu/num2.png"));
            assets.put("num3", new ImageAsset("assets/images/menu/num3.png"));
            assets.put("num4", new ImageAsset("assets/images/menu/num4.png"));
            assets.put("num5", new ImageAsset("assets/images/menu/num5.png"));
            assets.put("negatedNum1", new ImageAsset("assets/images/menu/negatedNum1.png"));
            assets.put("negatedNum2", new ImageAsset("assets/images/menu/negatedNum2.png"));
            assets.put("negatedNum3", new ImageAsset("assets/images/menu/negatedNum3.png"));
            assets.put("negatedNum4", new ImageAsset("assets/images/menu/negatedNum4.png"));
            assets.put("negatedNum5", new ImageAsset("assets/images/menu/negatedNum5.png"));
            assets.put("collect", new SoundAsset("assets/sounds/collect.wav"));
            assets.put("death", new SoundAsset("assets/sounds/death.wav"));
            assets.put("finish", new SoundAsset("assets/sounds/finish.wav"));
            assets.put("jump", new SoundAsset("assets/sounds/jump.wav"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

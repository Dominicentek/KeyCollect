package com.keycollect.loader;

import com.badlogic.gdx.files.FileHandle;
import com.keycollect.game.Game;
import com.keycollect.game.Level;

public class LevelAsset extends Asset<Level> {
    public int index;
    public LevelAsset(String path, int index) {
        super(path);
        this.index = index;
    }
    public Level load(FileHandle file) {
        Level level = Level.parse(file.readBytes());
        Game.LEVELS[index] = level;
        return level;
    }
}

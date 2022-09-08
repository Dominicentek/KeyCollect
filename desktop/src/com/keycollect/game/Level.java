package com.keycollect.game;

import com.keycollect.utils.ByteArray;

public class Level {
    public static final int WIDTH = 24;
    public static final int HEIGHT = 16;
    public TileType[][] tiles = new TileType[24][16];
    public TileType[][] origTiles = new TileType[24][16];
    public int spawnPosX = 0;
    public int spawnPosY = 0;
    public static Level parse(byte[] data) {
        ByteArray array = new ByteArray(data);
        Level level = new Level();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                level.origTiles[x][y] = array.readEnum(TileType.class);
            }
        }
        level.spawnPosX = array.readUnsignedByte();
        level.spawnPosY = array.readUnsignedByte();
        return level;
    }
}

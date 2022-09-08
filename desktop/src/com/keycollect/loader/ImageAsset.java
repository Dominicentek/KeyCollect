package com.keycollect.loader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class ImageAsset extends Asset<Texture> {
    public ImageAsset(String path) {
        super(path);
    }
    public Texture load(FileHandle file) {
        return new Texture(file);
    }
}

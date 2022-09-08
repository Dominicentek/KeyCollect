package com.keycollect.loader;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public abstract class Asset<T> {
    public String path;
    public T asset;
    public Asset(String path) {
        this.path = path;
    }
    public void load() {
        asset = load(Gdx.files.internal(path));
    }
    public abstract T load(FileHandle file);
}

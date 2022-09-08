package com.keycollect.game;

public interface TileTouchEvent {
    TileTouchEvent NONE = (x, y) -> {};
    void touched(int x, int y);
}

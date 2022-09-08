package com.keycollect;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import com.keycollect.game.Game;
import com.keycollect.game.TileType;
import com.keycollect.game.player.Physics;
import com.keycollect.loader.Loader;
import com.keycollect.utils.Renderer;

public class Main extends ApplicationAdapter {
	public Renderer renderer;
	public static final int width = 384;
	public static final int height = 256;
	public static int mouseX = 0;
	public static int mouseY = 0;
	public void create() {
		renderer = new Renderer();
		Loader.load();
		TileType.getAllTextures();
	}
	public void render() {
		mouseX = Gdx.input.getX() / 2;
		mouseY = Gdx.input.getY() / 2;
		Game.update();
		ScreenUtils.clear(0, 0, 0, 1);
		renderer.setColor(0xFFFFFFFF);
		renderer.begin();
		renderer.setTransformMatrix(new Matrix4().scale(2, 2, 1));
		Game.render(renderer);
		renderer.end();
	}
}
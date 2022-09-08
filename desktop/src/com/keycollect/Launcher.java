package com.keycollect;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class Launcher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(Main.width * 2, Main.height * 2);
		config.setResizable(false);
		config.setForegroundFPS(60);
		config.setTitle("Key Collect");
		new Lwjgl3Application(new Main(), config);
	}
}

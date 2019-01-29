package com.tcg.mazegenerator.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.mazegenerator.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.foregroundFPS = 10;
		config.width = Game.WORLD_WIDTH;
		config.height = Game.WORLD_HEIGHT;
		new LwjglApplication(new Game(), config);
	}
}

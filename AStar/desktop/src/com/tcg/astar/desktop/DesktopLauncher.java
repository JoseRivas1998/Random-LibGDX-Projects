package com.tcg.astar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.astar.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Game.WORLD_WIDTH;
		config.height = Game.WORLD_HEIGHT;
//		config.foregroundFPS = 15;
		new LwjglApplication(new Game(), config);
	}
}

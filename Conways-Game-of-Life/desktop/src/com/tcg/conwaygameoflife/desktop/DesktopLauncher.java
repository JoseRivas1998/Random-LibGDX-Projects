package com.tcg.conwaygameoflife.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.conwaygameoflife.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Game.WORLD_WIDTH;
		config.height = Game.WORLD_HEIGHT;
		new LwjglApplication(new Game(), config);
	}
}

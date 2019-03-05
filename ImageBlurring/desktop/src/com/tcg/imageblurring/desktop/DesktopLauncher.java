package com.tcg.imageblurring.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.imageblurring.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Game.filePath = arg[0];
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 544;
		config.height = 416;
		config.foregroundFPS = 0;
		new LwjglApplication(new Game(), config);
	}
}

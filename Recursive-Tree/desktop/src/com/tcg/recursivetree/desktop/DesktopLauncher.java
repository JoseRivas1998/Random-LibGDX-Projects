package com.tcg.recursivetree.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.recursivetree.Game;

import static com.tcg.recursivetree.Game.VIEWPORT_HEIGHT;
import static com.tcg.recursivetree.Game.VIEWPORT_WIDTH;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = VIEWPORT_WIDTH;
		config.height = VIEWPORT_HEIGHT;
		config.foregroundFPS = 0;
		new LwjglApplication(new Game(), config);
	}
}

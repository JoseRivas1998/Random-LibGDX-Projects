package com.tcg.complexnumbers.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.complexnumbers.ComplexNumber;
import com.tcg.complexnumbers.Game;

public class DesktopLauncher {
    public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = config.height = 800;
		config.vSyncEnabled = false;
		config.foregroundFPS = 0;
		new LwjglApplication(new Game(), config);;
    }
}

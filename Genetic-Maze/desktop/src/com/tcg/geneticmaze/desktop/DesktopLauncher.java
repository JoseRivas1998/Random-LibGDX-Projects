package com.tcg.geneticmaze.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.tcg.geneticmaze.Game;
import com.tcg.lichengine.TCGHelpers;
import com.tcg.lichengine.managers.TCGValues;

import static com.tcg.lichengine.TCGHelpers.worldHeight;
import static com.tcg.lichengine.TCGHelpers.worldWidth;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Files files = new LwjglFiles();
		TCGValues.load(files);
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = worldWidth();
		config.height = worldHeight();
		config.foregroundFPS = 75;
		new LwjglApplication(new Game(), config);
	}
}

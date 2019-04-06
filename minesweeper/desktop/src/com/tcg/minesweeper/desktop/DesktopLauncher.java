package com.tcg.minesweeper.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.minesweeper.Minesweeper;

import static com.tcg.minesweeper.Minesweeper.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) (CELL_SIZE * COLS);
		config.height = (int) (CELL_SIZE * ROWS);
		new LwjglApplication(new Minesweeper(), config);
	}
}

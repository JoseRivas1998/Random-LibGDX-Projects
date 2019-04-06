package com.tcg.minesweeper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Minesweeper extends ApplicationAdapter {

	public static final int COLS = 8;
	public static final int ROWS = 8;
	private static final float BOMB_CHANCE = 0.25f;

	public static final float CELL_SIZE = 32f;

	private Cell[][] grid;
	private Cell cursorCell;

	private Viewport viewport;

	private ShapeRenderer sr;

	public static Content content;

	private SpriteBatch sb;

	@Override
	public void create () {
		content = new Content();
		viewport = new ExtendViewport(CELL_SIZE * COLS, CELL_SIZE * ROWS);
		sr = new ShapeRenderer();
		sb = new SpriteBatch();
		grid = new Cell[COLS][ROWS];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new Cell(i, j, BOMB_CHANCE);
			}
		}

		for (Cell[] cells : grid) {
			for (Cell cell : cells) {
				cell.countBombNeighbors(grid);
			}
		}
		cursorCell = null;

	}

	private void handleMouse() {
		Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
		if(mousePosition.x > 0 && mousePosition.x < COLS * CELL_SIZE && mousePosition.y > 0 && mousePosition.y < ROWS * CELL_SIZE) {
			int row = (int) (mousePosition.y / CELL_SIZE);
			int col = (int) (mousePosition.x / CELL_SIZE);
			cursorCell = grid[row][col];
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				revealCells(cursorCell);
			}
		}
	}

	private void revealCells(Cell cell) {
		if(cell.isBomb()) {
			cell.reveal();
			return;
		}
		if(cell.isRevealed()) {
			return;
		}
		if(cell.getBombNeighbors() != 0) {
			cell.reveal();
			return;
		}
		if(cell.isVisited()) {
			return;
		}
		cell.reveal();
		cell.visit();
		for (Cell neighbor : cell.getNeighbors(grid)) {
			revealCells(neighbor);
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		handleMouse();
		viewport.apply(true);
		sr.begin(ShapeRenderer.ShapeType.Filled);
		for (Cell[] cells : grid) {
			for (Cell cell : cells) {
				sr.setColor(new Color(0xd2d2d2ff));
				if(cursorCell != null && cursorCell == cell) {
					sr.setColor(new Color(0xFF0000FF));
				} else if(cell.isRevealed()) {
					sr.setColor(new Color(0x00FF00FF));
				} else if(cell.isBomb()) {
					sr.setColor(new Color(0xFFFF00FF));
				}
				cell.draw(sr);
				sr.setColor(Color.WHITE);
			}
		}
		sr.end();
		sr.begin(ShapeRenderer.ShapeType.Line);
		for (Cell[] cells : grid) {
			for (Cell cell : cells) {
				sr.setColor(Color.BLACK);
				cell.draw(sr);
				sr.setColor(Color.WHITE);
			}
		}
		sr.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		viewport.apply(true);
	}

	@Override
	public void dispose () {
		content.dispose();
		sr.end();
	}
}

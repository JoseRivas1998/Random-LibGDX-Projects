package com.tcg.conwaygameoflife;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.conwaygameoflife.cells.Cell;
import com.tcg.conwaygameoflife.cells.ConwayCell;

public class Game extends ApplicationAdapter {

	private static final int CELL_WIDTH = 5;
	public static final int WORLD_WIDTH = 1280;
	public static final int WORLD_HEIGHT = 720;

	private static final int ROWS = WORLD_HEIGHT / CELL_WIDTH;
	private static final int COLS = WORLD_WIDTH / CELL_WIDTH;

	private ShapeRenderer sr;
	private Viewport viewport;

	private Cell[][] grid;

	private boolean runAlgorithm;
	private Vector2 cursorCell;
	private Vector2 pCursorCell;

	private float algorithmTimer;
	private static final float ALGORITHM_TIME = 0.05f;

	@Override
	public void create () {
		sr = new ShapeRenderer();
		viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT);
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cursorCell = new Vector2(0, 0);
		pCursorCell = new Vector2(-1, -1);
		reset();
	}

	private void reset() {
		grid = new Cell[ROWS][COLS];
		runAlgorithm = false;
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				grid[row][col] = new ConwayCell(row, col);
			}
		}
		Cell.forEachCell(grid, cell -> {
			cell.addNeighbors(grid);
		});
		algorithmTimer = 0;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			int x = (int) getMousePosInGameWorld().x;
			int y = (int) getMousePosInGameWorld().y;
			int row = y / CELL_WIDTH;
			int col = x / CELL_WIDTH;
			cursorCell.set(row, col);
			if(!cursorCell.equals(pCursorCell) &&
			row >= 0 && row < grid.length && col >= 0 && col < grid[row].length) {
				grid[row][col].setState(grid[row][col].getState() ==
						Cell.CellState.Alive ? Cell.CellState.Dead : Cell.CellState.Alive);
			}
			pCursorCell.set(cursorCell);
		} else {
			cursorCell = new Vector2(0, 0);
			pCursorCell = new Vector2(-1, -1);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			runAlgorithm = !runAlgorithm;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.S)) {
			doAlgorithm();
		}
		if(runAlgorithm){
			algorithmTimer += Gdx.graphics.getDeltaTime();
			if(algorithmTimer >= ALGORITHM_TIME) {
				algorithmTimer = 0;
				doAlgorithm();
			}
		}
		drawCells();
	}

	private void doAlgorithm() {
		Cell.forEachCell(grid, Cell::updateNeighborStateCount);
		Cell.forEachCell(grid, cell -> {
			cell.setState(cell.getTargetState());
		});
	}

	private Vector3 getMousePosInGameWorld() {
		return viewport.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
	}

	private void drawCells() {
		sr.setProjectionMatrix(viewport.getCamera().combined);
		sr.begin(ShapeRenderer.ShapeType.Filled);
		Cell.forEachCell(grid, cell -> {
			cell.draw(sr, CELL_WIDTH);
		});
		sr.end();
		sr.begin(ShapeRenderer.ShapeType.Line);
		sr.setColor(Color.GRAY);
		Cell.forEachCell(grid, cell -> {
			cell.draw(sr, CELL_WIDTH, false);
		});
		sr.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void dispose () {
		sr.dispose();
	}
}

package com.tcg.mazegenerator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Stack;

public class Game extends ApplicationAdapter {

	public static final int WORLD_WIDTH = 1280;
	public static final int WORLD_HEIGHT = 720;

	public static final int ROWS = 72;
	public static final int COLS = 128;

	private Cell[][] grid;
	private Stack<Cell> cellStack;

	private Cell current;

	private float cellWidth;
	private float cellHeight;

	private ShapeRenderer sr;
	private Viewport viewport;

	@Override
	public void create () {
		sr = new ShapeRenderer();
		viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT);
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cellWidth = (float) WORLD_WIDTH / COLS;
		cellHeight = (float) WORLD_HEIGHT / ROWS;
		cellStack = new Stack<>();
		reset();
	}

	private void reset() {
		cellStack.clear();
		grid = new Cell[ROWS][COLS];
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				grid[row][col] = new Cell(row, col);
			}
		}
		for (Cell[] cells : grid) {
			for (Cell cell : cells) {
				cell.addNeighbors(grid);
			}
		}
		int startRow = MathUtils.random(grid.length - 1);
		int startCol = MathUtils.random(grid[startRow].length - 1);
//		int startRow = 0;
//		int startCol = 0;
		current = grid[startRow][startCol];
		current.setVisited(true);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			reset();
		}
		doAlgorithm();
		drawAll();

	}

	private void doAlgorithm() {
		if(current != null && current.hasUnvisitedNeighbors()) {
			Cell neighbor = current.getUnvisitedNeighbor();
			cellStack.push(current);
			Cell.removeWall(current, neighbor);
			neighbor.setVisited(true);
			current = neighbor;
		} else if(!cellStack.empty()) {
			current = cellStack.pop();
		}
	}

	private void drawAll() {
		sr.setProjectionMatrix(viewport.getCamera().combined);
		sr.begin(ShapeRenderer.ShapeType.Filled);
		drawVisited();
		drawHighlight();
		sr.end();
		sr.begin(ShapeRenderer.ShapeType.Line);
		drawWalls();
		sr.end();
	}

	private void drawVisited() {
		for (Cell[] cells : grid) {
			for (Cell cell : cells) {
				cell.drawVisited(sr, cellWidth, cellHeight);
			}
		}
	}

	private void drawHighlight() {
		current.drawHighlight(sr, cellWidth, cellHeight);
	}

	private void drawWalls() {
		for (Cell[] cells : grid) {
			for (Cell cell : cells) {
				cell.drawWalls(sr, cellWidth, cellHeight);
			}
		}
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

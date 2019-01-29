package com.tcg.mazegenerator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Stack;
import java.util.logging.FileHandler;

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

    private static int screenshotCounter = 0;

    private static final int STEPS_PER_SECOND = 60;

    @Override
    public void create() {
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
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            reset();
        }
        int steps = (int) Math.ceil((float) STEPS_PER_SECOND * Gdx.graphics.getDeltaTime());
        for (int i = 0; i < steps; i++) {
            doAlgorithm();
        }
        drawAll();
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            saveScreenshot();
        }

    }

    private void saveScreenshot() {
        FileHandle file;
        do {
            Game.screenshotCounter++;
            file = Gdx.files.local(String.format("screenshots/screenshot%03d.png", Game.screenshotCounter));
        } while(file.exists());
        byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);

        // this loop makes sure the whole screenshot is opaque and looks exactly like what the user is seeing
        for (int i = 4; i < pixels.length; i += 4) {
            pixels[i - 1] = (byte) 255;
        }

        Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
        BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
        PixmapIO.writePNG(file, pixmap);
        pixmap.dispose();
    }

    private void doAlgorithm() {
        if (current != null && current.hasUnvisitedNeighbors()) {
            Cell neighbor = current.getUnvisitedNeighbor();
            cellStack.push(current);
            Cell.removeWall(current, neighbor);
            neighbor.setVisited(true);
            current = neighbor;
        } else if (!cellStack.empty()) {
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
    public void dispose() {
        sr.dispose();
    }
}

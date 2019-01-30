package com.tcg.imagedrawing;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Stack;

public class Game extends ApplicationAdapter {

    private String imagePath;

    private int worldWidth;
    private int worldHeight;

    private Texture img;

    private int rows;
    private int cols;

    private static int cellSideLength = 1;

    private ShapeRenderer sr;
    private Viewport viewport;

    private ColorCell[][] grid;
    private Stack<ColorCell> colorCellStack;

    private ColorCell current;

    private static final int stepsPerLoop = 50;

    private SpriteBatch sb;
    private TiledBackground tb;

    public Game(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public void create() {
        sr = new ShapeRenderer();
        tb = new TiledBackground("checkboard.png");
        sb = new SpriteBatch();
        img = new Texture(Gdx.files.absolute(this.imagePath));
        rows = img.getHeight();
        cols = img.getWidth();
        worldWidth = cols * cellSideLength;
        worldHeight = rows * cellSideLength;
        viewport = new StretchViewport(worldWidth, worldHeight);
        Gdx.graphics.setWindowedMode(worldWidth, worldHeight);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        colorCellStack = new Stack<>();
        reset();
    }

    private void reset() {
        colorCellStack.clear();
        int[][] colors = Util.absoluteFilePathToColorMatrix(this.imagePath);
        grid = new ColorCell[rows][cols];
        for (int row = 0; row < colors.length; row++) {
            for (int col = 0; col < colors[row].length; col++) {
                grid[row][col] = new ColorCell(row, col, colors[row][col]);
            }
        }
        for (ColorCell[] colorCells : grid) {
            for (ColorCell colorCell : colorCells) {
                colorCell.addNeighbors(grid);
            }
        }
        int startRow = MathUtils.random(grid.length - 1);
        int startCol = MathUtils.random(grid[startRow].length - 1);
        current = grid[startRow][startCol];
        current.setVisited(true);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (int i = 0; i < stepsPerLoop; i++) {
            doAlgorithm();
        }
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        tb.draw(sb);
        sb.end();
        renderGrid();
    }

    private void doAlgorithm() {
        if (current != null && current.hasUnvisitedNeighbors()) {
            ColorCell neighbor = current.getUnvisitedNeighbor();
            neighbor.setVisited(true);
            colorCellStack.push(current);
            current = neighbor;
        } else if (!colorCellStack.empty()) {
            current = colorCellStack.pop();
        }
    }

    private void renderGrid() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setProjectionMatrix(viewport.getCamera().combined);
        for (ColorCell[] colorCells : grid) {
            for (ColorCell colorCell : colorCells) {
                colorCell.draw(sr, cellSideLength);
            }
        }
        if (current != null) {
            current.draw(sr, cellSideLength, !colorCellStack.empty());
        }
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        sb.dispose();
        tb.dispose();
        sr.dispose();
        img.dispose();
    }
}

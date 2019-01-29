package com.tcg.astar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.astar.entities.Cell;

public class Game extends ApplicationAdapter {

    private int rows;
    private int cols;

    public static final int WORLD_WIDTH = 1280;
    public static final int WORLD_HEIGHT = 736;

    private Cell[][] grid;

    private float cellWidth;
    private float cellHeight;

    private Array<Cell> openSet;
    private Array<Cell> closedSet;
    private Cell start;
    private Cell end;
    private Array<Cell> path;

    private ShapeRenderer sr;
    private Viewport viewport;
    private SpriteBatch spriteBatch;

    private boolean foundSolution;
    private boolean noSolution;

    private TiledMap tileMap;
    private TiledMapRenderer renderer;

    private boolean USE_MAP;

    @Override
    public void create() {

        USE_MAP = MathUtils.randomBoolean(.75f);
        reset();

        sr = new ShapeRenderer();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch = new SpriteBatch();

    }

    private void reset() {
        openSet = new Array<Cell>();
        closedSet = new Array<Cell>();
        path = new Array<Cell>();
        createGrid(!USE_MAP);

        openSet.add(start);

        foundSolution = false;
        noSolution = false;
    }

    private void createGrid(boolean randomGrid) {
        if (randomGrid) {
            randomGrid();
        } else {
            mapGrid(choose(
                    "maps/map01.tmx",
                    "maps/map02.tmx"
            ));
        }

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                grid[row][col].addNeighbors(grid);
            }
        }
        start.setWall(false);
        end.setWall(false);
    }

    private void randomGrid() {
        rows = 23;
        cols = 40;
        grid = new Cell[rows][cols];
        float wallChance = MathUtils.random();
        Gdx.app.log(getClass().getName() + ".reset()", String.format("Wall chance: %f", wallChance));
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                grid[row][col] = new Cell(row, col, wallChance);
            }
        }

        start = grid[0][0];
        end = grid[rows - 1][cols - 1];

        cellWidth = (float) WORLD_WIDTH / cols;
        cellHeight = (float) WORLD_HEIGHT / rows;
    }

    private void mapGrid(String map) {
        if (tileMap != null) {
            tileMap.dispose();
        }
        tileMap = new TmxMapLoader().load(map);
        renderer = new OrthogonalTiledMapRenderer(tileMap);
        cellWidth = (int) tileMap.getProperties().get("tilewidth");
        cellHeight = (int) tileMap.getProperties().get("tileheight");
        TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().get("obstacles");
        cols = layer.getWidth();
        rows = layer.getHeight();
        grid = new Cell[rows][cols];
        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);

                grid[row][col] = new Cell(row, col, !(cell == null || cell.getTile() == null));

            }
        }
        int startRow = 0;
        int startCol = 0;
        int endRow = rows - 1;
        int endCol = cols - 1;
        MapLayer startLayer = tileMap.getLayers().get("start");
        for (MapObject object : startLayer.getObjects()) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            startRow = (int) (ellipse.y / cellHeight);
            startCol = (int) (ellipse.x / cellWidth);
        }
        MapLayer endLayer = tileMap.getLayers().get("end");
        for (MapObject object : endLayer.getObjects()) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            endRow = (int) (ellipse.y / cellHeight);
            endCol = (int) (ellipse.x / cellWidth);
        }
        start = grid[startRow][startCol];
        end = grid[endRow][endCol];
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            reset();
        }

        doAlgorithm();


        sr.setProjectionMatrix(viewport.getCamera().combined);

        if (USE_MAP && renderer != null) {
            renderer.setView((OrthographicCamera) viewport.getCamera());
            renderer.render();
        } else {
            renderGrid();
        }
//        renderOpenSet();
//        renderClosedSet();
        renderPath();
        renderPoints();

    }

    private void doAlgorithm() {
        if (openSet.size > 0 && !foundSolution && !noSolution) {
            int lowestIndex = 0;
            float lowestF = openSet.get(0).getF();
            for (int i = 1; i < openSet.size; i++) {
                if (openSet.get(i).getF() < lowestF) {
                    lowestF = openSet.get(i).getF();
                    lowestIndex = i;
                }
            }

            Cell current = openSet.get(lowestIndex);

            if (current.equals(end)) {
                Gdx.app.log(getClass().getName() + ".doAlgorithm()", "DONE");
                foundSolution = true;
                buildPath(current);
                return;
            }

            openSet.removeValue(current, false);
            closedSet.add(current);
            for (Cell neighbor : current.getNeighbors()) {
                if (!closedSet.contains(neighbor, false) && !neighbor.isWall()) {
                    float tempG = current.getG() + 1;

                    boolean newPath = false;
                    if (openSet.contains(neighbor, false)) {
                        if (tempG < neighbor.getG()) {
                            neighbor.setG(tempG);
                            newPath = true;
                        }
                    } else {
                        neighbor.setG(tempG);
                        newPath = true;
                        openSet.add(neighbor);
                    }
                    if (newPath) {
                        neighbor.setH(Cell.heuristic(neighbor, end));
                        neighbor.setF(neighbor.getG() + neighbor.getH());
                        neighbor.setPrevious(current);
                    }
                }
            }
            buildPath(current);

        } else {
            noSolution = true;
        }
    }

    private void buildPath(Cell current) {
        path.clear();
        Cell temp = current;
        path.add(temp);
        while (temp.getPrevious() != null) {
            path.add(temp.getPrevious());
            temp = temp.getPrevious();
        }
    }

    private void renderGrid() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                grid[row][col].draw(sr, cellWidth, cellHeight, Color.WHITE);
            }
        }
    }

    private void renderOpenSet() {
        for (Cell cell : openSet) {
            cell.draw(sr, cellWidth, cellHeight, Color.LIME);
        }
    }

    private void renderClosedSet() {
        for (Cell cell : closedSet) {
            cell.draw(sr, cellWidth, cellHeight, Color.RED);
        }
    }

    private void renderPoints() {
        start.draw(sr, cellWidth, cellHeight, Color.NAVY);
        end.draw(sr, cellWidth, cellHeight, Color.YELLOW);
    }

    private void renderPath() {
        Color c;
        if (foundSolution) {
            c = new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1f);
        } else if (noSolution) {
            c = Color.RED;
        } else {
            c = Color.BLUE;
        }
        for (Cell cell : path) {
            cell.draw(sr, cellWidth, cellHeight, c);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        sr.dispose();
        if (tileMap != null) {
            tileMap.dispose();
        }
    }

    private static <T> T choose(T... choices) {
        return choices[MathUtils.random(choices.length - 1)];
    }

}

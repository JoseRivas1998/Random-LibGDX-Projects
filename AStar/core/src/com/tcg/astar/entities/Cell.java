package com.tcg.astar.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/28/19
 */
public class Cell {

    private int row;
    private int col;
    private float f;
    private float g;
    private float h;
    private Array<Cell> neighbors;
    private Cell previous;
    private boolean wall;

    public Cell(int row, int col, float wallChance) {
        this(row, col, MathUtils.randomBoolean(wallChance));
    }

    public Cell(int row, int col, boolean isWall) {
        this.row = row;
        this.col = col;
        this.f = 0f;
        this.g = 0f;
        this.h = 0f;
        this.neighbors = new Array<Cell>();
        this.previous = null;
        this.wall = isWall;
    }

    public void draw(ShapeRenderer shapeRenderer, float width, float height, Color color) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(this.wall ? Color.BLACK : color);
        shapeRenderer.rect(col * width, row * height, width, height);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(col * width, row * height, width, height);
        shapeRenderer.end();
        shapeRenderer.setColor(Color.WHITE);
    }

    public void addNeighbors(Cell[][] grid) {
        neighbors.clear();
        boolean hasNorth = this.row < grid.length - 1;
        boolean hasSouth = this.row > 0;
        boolean hasEast = this.col < grid[row].length - 1;
        boolean hasWest = this.col > 0;

        int north = row + 1;
        int south = row - 1;
        int east = col + 1;
        int west = col - 1;

        boolean isNorthWall = false;
        boolean isSouthWall = false;
        boolean isEastWall = false;
        boolean isWestWall = false;

        // North Neighbor
        if (hasNorth) {
            neighbors.add(grid[north][col]);
            isNorthWall = grid[north][col].isWall();
        }

        // South Neighbor
        if (hasSouth) {
            neighbors.add(grid[south][col]);
            isSouthWall = grid[south][col].isWall();
        }

        // East Neighbor
        if (hasEast) {
            neighbors.add(grid[row][east]);
            isEastWall = grid[row][east].isWall();
        }

        // West Neighbor
        if (hasWest) {
            neighbors.add(grid[row][west]);
            isWestWall = grid[row][west].isWall();
        }

        boolean hasNorthEast = hasNorth && hasEast && !(isNorthWall && isEastWall);
        boolean hasNorthWest = hasNorth && hasWest && !(isNorthWall && isWestWall);
        boolean hasSouthEast = hasSouth && hasEast && !(isSouthWall && isEastWall);
        boolean hasSouthWest = hasSouth && hasWest && !(isSouthWall && isWestWall);

//        // NorthEast Neighbor
//        if (hasNorthEast) {
//            neighbors.add(grid[north][east]);
//        }
//
//        // NorthWest Neighbor
//        if (hasNorthWest) {
//            neighbors.add(grid[north][west]);
//        }
//
//        // SouthEast Neighbor
//        if (hasSouthEast) {
//            neighbors.add(grid[south][east]);
//        }
//
//        // SouthWest Neighbor
//        if (hasSouthWest) {
//            neighbors.add(grid[south][west]);
//        }

    }

    public static float heuristic(Cell c1, Cell c2) {
//        return (float) Math.sqrt(Math.pow(c1.row - c2.row, 2) + Math.pow(c1.col - c1.row, 2));
        return Math.abs(c1.row - c2.row) + Math.abs(c1.col - c2.col);
    }

    @Override
    public boolean equals(Object o) {
        boolean result;
        if (o == null || o.getClass() != this.getClass()) {
            result = false;
        } else {
            Cell other = (Cell) o;
            result = other.row == this.row && other.col == this.col;
        }
        return result;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public Array<Cell> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Array<Cell> neighbors) {
        this.neighbors = neighbors;
    }

    public Cell getPrevious() {
        return previous;
    }

    public void setPrevious(Cell previous) {
        this.previous = previous;
    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }
}

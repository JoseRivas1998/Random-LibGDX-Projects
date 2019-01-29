package com.tcg.mazegenerator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Objects;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/29/19
 */
public class Cell {

    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    private int row;
    private int col;
    private boolean visited;
    private Array<Cell> neighbors;
    private boolean[] walls;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.visited = false;
        walls = new boolean[]{true, true, true, true};
        this.neighbors = new Array<>();
    }

    public void addNeighbors(Cell[][] grid) {

        boolean hasNorth = this.row < grid.length - 1;
        boolean hasEast = this.col < grid[this.row].length - 1;
        boolean hasSouth = this.row > 0;
        boolean hasWest = this.col > 0;

        if (hasNorth) {
            neighbors.add(grid[this.row + 1][this.col]);
        }

        if (hasEast) {
            neighbors.add(grid[this.row][this.col + 1]);
        }

        if (hasSouth) {
            neighbors.add(grid[this.row - 1][this.col]);
        }

        if (hasWest) {
            neighbors.add(grid[this.row][this.col - 1]);
        }

    }

    public boolean hasUnvisitedNeighbors() {
        boolean result = false;
        for (Cell neighbor : neighbors) {
            if (!neighbor.isVisited()) {
                result = true;
                break;
            }
        }
        return result;
    }

    public Cell getUnvisitedNeighbor() {
        if (!hasUnvisitedNeighbors()) {
            return null; // Shouldn't get here
        }
        int i;
        do {
            i = MathUtils.random(neighbors.size - 1);
        } while (neighbors.get(i).isVisited());
        return neighbors.get(i);
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

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isWall(int side) {
        return this.walls[side];
    }

    public void setWall(int side, boolean wall) {
        this.walls[side] = wall;
    }

    public void drawVisited(ShapeRenderer sr, float width, float height) {
        if (this.visited) {
            sr.setColor(Color.BLACK);
            sr.rect(
                    this.getCol() * width, this.getRow() * height,
                    width, height
            );
        }
        sr.setColor(Color.WHITE);
    }

    public void drawHighlight(ShapeRenderer sr, float width, float height) {
        sr.setColor(Color.LIME);
        sr.rect(
                this.getCol() * width, this.getRow() * height,
                width, height
        );
        sr.setColor(Color.WHITE);
    }

    public void drawWalls(ShapeRenderer sr, float width, float height) {
        Vector2 bottomLeft = new Vector2(this.getCol() * width, this.getRow() * height);
        Vector2 bottomRight = new Vector2(bottomLeft).add(width, 0);
        Vector2 topLeft = new Vector2(bottomLeft).add(0, height);
        Vector2 topRight = new Vector2(bottomLeft).add(width, height);

        if (this.walls[Cell.NORTH]) {
            sr.line(topLeft, topRight);
        }

        if (this.walls[Cell.EAST]) {
            sr.line(topRight, bottomRight);
        }

        if (this.walls[Cell.SOUTH]) {
            sr.line(bottomLeft, bottomRight);
        }

        if (this.walls[Cell.WEST]) {
            sr.line(bottomLeft, topLeft);
        }

    }

    public static void removeWall(Cell a, Cell b) {
        /*
         * COMPARISON CHART:
         * x == 0, a and b are vertically aligned
         * x == 1, a is east of b
         * x == -1, a is west of b
         *
         * y == 0, a and b are horizontally aligned
         * y == 1, a is north of b
         * y == -1, a is south of b
         *
         */
        int x = a.getCol() - b.getCol();
        int y = a.getRow() - b.getRow();

        if (x == 1) { // a is east of b
            a.setWall(Cell.WEST, false);
            b.setWall(Cell.EAST, false);
        } else if (x == -1) { // a is west of b
            a.setWall(Cell.EAST, false);
            b.setWall(Cell.WEST, false);
        } // Otherwise, they are either vertically aligned or not adjacent

        if (y == 1) { // a is north of b
            a.setWall(Cell.SOUTH, false);
            b.setWall(Cell.NORTH, false);
        } else if (y == -1) { // a is south of b
            a.setWall(Cell.NORTH, false);
            b.setWall(Cell.SOUTH, false);
        } // Otherwise, they are either horizontally aligned or not adjacent

    }

    @Override
    public boolean equals(Object o) {
        boolean result;
        if (o == null || o.getClass() != this.getClass()) {
            result = false;
        } else {
            Cell cell = (Cell) o;
            result = cell.getRow() == this.getRow() && cell.getCol() == this.getCol();
        }
        return result;
    }

    @Override
    public int hashCode() {
        return this.row * 31 + this.col;
    }
}

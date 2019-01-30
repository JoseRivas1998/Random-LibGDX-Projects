package com.tcg.imagedrawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/29/19
 */
public class ColorCell {

    private int row;
    private int col;
    private Color color;
    private boolean visited;
    private Array<ColorCell> neighbors;

    public ColorCell(int row, int col, int color) {
        this.row = row;
        this.col = col;
        this.color = new Color(color);
        neighbors = new Array<>();
        visited = false;
    }

    public void addNeighbors(ColorCell[][] grid) {
        neighbors.clear();
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

    public void draw(ShapeRenderer sr, float sideLength) {
        this.draw(sr, sideLength, false);
    }

    public void draw(ShapeRenderer sr, float sideLength, boolean highlight) {
        if(highlight) {
            sr.setColor(Color.LIME);
        } else if(visited) {
            sr.setColor(this.color);
        } else {
            sr.setColor(Color.BLACK);
        }
        sr.rect(this.col * sideLength, this.row * sideLength, sideLength, sideLength);
        sr.setColor(Color.WHITE);
    }

    public boolean hasUnvisitedNeighbors() {
        boolean result = false;
        for (ColorCell neighbor : neighbors) {
            if (!neighbor.isVisited()) {
                result = true;
                break;
            }
        }
        return result;
    }

    public ColorCell getUnvisitedNeighbor() {
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}

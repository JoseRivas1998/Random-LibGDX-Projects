package com.tcg.minesweeper;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import static com.tcg.minesweeper.Minesweeper.CELL_SIZE;

public class Cell {

    private final int row;
    private final int col;
    private final boolean isBomb;
    private boolean isVisited;
    private boolean isRevealed;
    private int bombNeighbors;

    public Cell(int row, int col, float chance) {
        this.row = row;
        this.col = col;
        isBomb = MathUtils.randomBoolean(chance);
        isVisited = false;
        isRevealed = false;
        bombNeighbors = -1;
    }

    public void visit() {
        isVisited = true;
    }

    public void reveal() {
        isRevealed = true;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public int getBombNeighbors() {
        return bombNeighbors;
    }

    public Array<Cell> getNeighbors(Cell[][] grid) {
        Array<Cell> neighbors = new Array<Cell>();
        for(int i = row - 1; i <= row + 1; i++) {
            if(i < 0 || i >= grid.length) continue;
            for(int j = col - 1; j <= col + 1; j++) {
                if(j < 0 || j >= grid[i].length) continue;
                if(i == row && j == col) continue;
                neighbors.add(grid[i][j]);
            }
        }
        return neighbors;
    }

    public void countBombNeighbors(Cell[][] grid) {
        Array<Cell> neighbors = getNeighbors(grid);
        bombNeighbors = 0;
        for (Cell neighbor : neighbors) {
            if(neighbor.isBomb) bombNeighbors++;
        }
    }

    public void draw(ShapeRenderer sr) {
        sr.rect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    public void drawNumber(ShapeRenderer sr) {
        // todo
    }

}

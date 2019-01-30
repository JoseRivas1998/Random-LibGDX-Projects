package com.tcg.conwaygameoflife.cells;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/30/19
 */
public class ConwayCell extends Cell {

    public ConwayCell(int row, int col) {
        super(row, col);
        setState(MathUtils.randomBoolean(0.1f) ? CellState.Alive : CellState.Dead);
    }

    @Override
    public void setStateColors() {
        stateColor.put(CellState.Alive, Color.BLACK);
        stateColor.put(CellState.Dead, Color.WHITE);
    }

    @Override
    public void addNeighbors(Cell[][] grid) {
        clearNeighbors();
        int row = this.getRow();
        int col = this.getCol();

        int north = row + 1;
        int east = col + 1;
        int south = row - 1;
        int west = col - 1;

        boolean hasNorth = north < grid.length;
        boolean hasEast = east < grid[row].length;
        boolean hasSouth = south >= 0;
        boolean hasWest = west >= 0;

        if (hasNorth) {
            addNeighbor(grid[north][col]);
        }

        if (hasEast) {
            addNeighbor(grid[row][east]);
        }

        if (hasSouth) {
            addNeighbor(grid[south][col]);
        }

        if (hasWest) {
            addNeighbor(grid[row][west]);
        }

        if (hasNorth && hasEast) {
            addNeighbor(grid[north][east]);
        }

        if (hasNorth && hasWest) {
            addNeighbor(grid[north][west]);
        }

        if (hasSouth && hasEast) {
            addNeighbor(grid[south][east]);
        }

        if (hasSouth && hasWest) {
            addNeighbor(grid[south][west]);
        }

    }

    @Override
    public CellState getTargetState() {
        int liveNeighbors = neighborsInState(CellState.Alive);
        CellState target = getState();
        switch (getState()) {
            case Alive:
                if (liveNeighbors < 2 || liveNeighbors > 3) {
                    target = CellState.Dead;
                }
                break;
            case Dead:
                if (liveNeighbors == 3) {
                    target = CellState.Alive;
                }
                break;
        }
        return target;
    }
}

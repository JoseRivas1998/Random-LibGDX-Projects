package com.tcg.conwaygameoflife.cells;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/30/19
 */
public abstract class Cell {

    public enum CellState {
        Alive, Dead
    }

    private int row;
    private int col;

    private Array<Cell> neighbors;

    private CellState state;
    private Map<CellState, Integer> neighborStateOccurrences;
    protected Map<CellState, Color> stateColor;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        neighbors = new Array<>();
        neighborStateOccurrences = new HashMap<>();
        resetNeighborStateCount();
        stateColor = new HashMap<>();
        setStateColors();
    }

    public abstract void setStateColors();

    public abstract void addNeighbors(Cell[][] grid);

    public void draw(ShapeRenderer sr, float cellWidth) {
        draw(sr, cellWidth, true);
    }

    public void draw(ShapeRenderer sr, float cellWidth, boolean useColor) {
        if(useColor) sr.setColor(stateColor.get(state));
        sr.rect(col * cellWidth, row * cellWidth, cellWidth, cellWidth);
    }

    public abstract CellState getTargetState();

    protected void clearNeighbors() {
        this.neighbors.clear();
        resetNeighborStateCount();
    }

    protected void addNeighbor(Cell cell) {
        neighbors.add(cell);
    }

    private void resetNeighborStateCount() {
        for (CellState state : CellState.values()) {
            neighborStateOccurrences.put(state, 0);
        }
    }

    public void updateNeighborStateCount() {
        resetNeighborStateCount();
        for (Cell neighbor : neighbors) {
            neighborStateOccurrences.put(neighbor.state, neighborStateOccurrences.get(neighbor.state) + 1);
        }
    }

    public int neighborsInState(CellState cellState) {
        return neighborStateOccurrences.get(cellState);
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

    public Array<Cell> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Array<Cell> neighbors) {
        this.neighbors = neighbors;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public Map<CellState, Integer> getNeighborStateOccurrences() {
        return neighborStateOccurrences;
    }

    public void setNeighborStateOccurrences(Map<CellState, Integer> neighborStateOccurrences) {
        this.neighborStateOccurrences = neighborStateOccurrences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row &&
                col == cell.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    public static void forEachCell(Cell[][] grid, CellAction cellAction) {
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                cellAction.act(cell);
            }
        }
    }

    public interface CellAction {
        void act(Cell cell);
    }

}

package com.tcg.imageblurring;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

public class MyColor implements Comparable<MyColor> {

    int r;
    int g;
    int b;
    int a;
    private Array<MyColor> neighbors;
    MyColor neighborAverage;

    public MyColor(int r, int g, int b, int a) {
        this.set(r, g, b, a);
    }

    public MyColor(int rgba) {
        this.set(rgba);
    }

    public MyColor(MyColor color) {
        this.set(color);
    }

    public void set(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void set(int rgba) {
        this.a = rgba & 0xFF;
        int rgb = rgba >> 8;
        this.b = rgb & 0xFF;
        int rg = rgb >> 8;
        this.g = rg & 0xFF;
        this.r = rg >> 8;
    }

    public void set(MyColor color) {
        this.set(color.r, color.g, color.b, color.a);
    }

    public void setToAverage() {
        this.set(this.neighborAverage);
    }

    public int toRGBA() {
        int r = this.r;
        int rg = r << 8;
        rg |= this.g;
        int rgb = rg << 8;
        rgb |= this.b;
        int rgba = rgb << 8;
        rgba |= this.a;
        return rgba;
    }

    public Color toColor() {
        return new Color(toRGBA());
    }

    public void updateNeighbors(MyColor[][] grid, int row, int col) {
        if (neighbors == null) {
            neighbors = new Array<>();
        } else {
            neighbors.clear();
        }
        for (int r = row - 1; r <= row + 1; r++) {
            if (r < 0 || r >= grid.length) continue;
            for (int c = col - 1; c <= col + 1; c++) {
                if (c < 0 || c >= grid[r].length) continue;
                if (c == col && r == row) continue;
                neighbors.add(new MyColor(grid[row][col]));
            }
        }
        float numNeighbors = neighbors.size;
        int rSum = 0;
        int gSum = 0;
        int bSum = 0;
        int aSum = 0;
        for (MyColor neighbor : neighbors) {
            rSum += neighbor.r * neighbor.r;
            gSum += neighbor.g * neighbor.g;
            bSum += neighbor.b * neighbor.b;
            aSum += neighbor.a * neighbor.a;
        }
        int rAvg = (int) Math.sqrt(rSum / numNeighbors);
        int gAvg = (int) Math.sqrt(gSum / numNeighbors);
        int bAvg = (int) Math.sqrt(bSum / numNeighbors);
        int aAvg = (int) Math.sqrt(aSum / numNeighbors);
        neighborAverage = new MyColor(rAvg, gAvg, bAvg, aAvg);
    }

    public int magSq() {
        return (int) (Math.pow(this.r, 2) + Math.pow(this.g, 2) + Math.pow(this.b, 2) + Math.pow(this.a, 2));
    }

    public int mag() {
        return (int) Math.sqrt(magSq());
    }

    @Override
    public int compareTo(MyColor o) {
        return this.magSq() - o.magSq();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        } else {
            MyColor other = (MyColor) o;
            return other.toRGBA() == this.toRGBA();
        }
    }

}

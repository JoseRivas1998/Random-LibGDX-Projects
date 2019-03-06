package com.tcg.imagefun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public final class Util {

    public static <T> void swap(T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static <T> void swap(T[][] arr, int r1, int c1, int r2, int c2) {
        T temp = arr[r1][c1];
        arr[r1][c1] = arr[r2][c2];
        arr[r2][c2] = temp;
    }

    public static <T extends Comparable<T>> int getMin(T[] arr, int start) {
        int minIndex = start;
        T minValue = arr[start];
        for (int j = minIndex + 1; j < arr.length; j++) {
            if (minValue.compareTo(arr[j]) > 0) {
                minIndex = j;
                minValue = arr[j];
            }
        }
        return minIndex;
    }

    public static <T extends Comparable<T>> int getMax(T[] arr, int start) {
        int maxIndex = start;
        T maxValue = arr[start];
        for (int j = maxIndex + 1; j < arr.length; j++) {
            if (maxValue.compareTo(arr[j]) < 0) {
                maxIndex = j;
                maxValue = arr[j];
            }
        }
        return maxIndex;
    }

    public static <T extends Comparable<T>> RowCol getMin(T[][] arr, int startRow, int startCol) {
        int minRow = startRow;
        int minCol = startCol;
        T minValue = arr[startRow][startCol];
        for (int row = minRow; row < arr.length; row++) {
            for (int col = 0; col < arr[row].length; col++) {
                if (row == startRow && col < startCol + 1) continue;
                if (minValue.compareTo(arr[row][col]) > 0) {
                    minRow = row;
                    minCol = col;
                    minValue = arr[row][col];
                }
            }
        }
        return new RowCol(minRow, minCol);
    }

    public static FileHandle getScreenshotFile(String format) {
        int i = 0;
        while (Gdx.files.local(String.format(format, i)).exists()) {
            i++;
        }
        return Gdx.files.local(String.format(format, i));
    }

    public static int clamp(int n, int min, int max) {
        if (n < min) {
            return min;
        } else if (n > max) {
            return max;
        } else {
            return n;
        }
    }

    public static final class RowCol {
        public final int row;
        public final int col;

        public RowCol(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

}

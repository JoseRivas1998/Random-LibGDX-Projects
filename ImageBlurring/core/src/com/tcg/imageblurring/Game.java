package com.tcg.imageblurring;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Arrays;

public class Game extends ApplicationAdapter {
	public static String filePath;
	private Pixmap pixmap;
	private ShapeRenderer shapeRenderer;
	private MyColor[][] colorMatrix;
	int currentRow = 0;
	int i = 0;

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		pixmap = new Pixmap(Gdx.files.absolute(filePath));
		colorMatrix = pixmapToColorMatrix(pixmap);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sortRow();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for (int row = 0; row < colorMatrix.length; row++) {
			for (int col = 0; col < colorMatrix[row].length; col++) {
				shapeRenderer.setColor(colorMatrix[row][col].toColor());
				shapeRenderer.rect(col, colorMatrix.length - row, 1, 1);
			}
		}
		shapeRenderer.end();
	}

    private void sortRow() {
        if(currentRow < colorMatrix.length) {
//            if(i < colorMatrix[currentRow].length - 1) {
//                int min = getMin(colorMatrix[currentRow], i);
//                swap(colorMatrix[currentRow], i, min);
//                i++;
//            } else {
//                currentRow++;
//                i = 0;
//            }
            Arrays.sort(colorMatrix[currentRow]);
            currentRow++;
        }
    }

    private <T> void swap(T[] arr, int a, int b) {
	    T temp = arr[a];
	    arr[a] = arr[b];
	    arr[b] = temp;
    }

    private  <T extends Comparable<T>> int getMin(T[] arr, int start) {
        int minIndex = start;
        T minValue = arr[start];
        for (int j = minIndex + 1; j < arr.length; j++) {
            if(minValue.compareTo(arr[j]) > 0) {
                minIndex = j;
                minValue = arr[j];
            }
        }
        return minIndex;
    }

    private void blur() {
        for (int row = 0; row < colorMatrix.length; row++) {
            for (int col = 0; col < colorMatrix[row].length; col++) {
                MyColor color = colorMatrix[row][col];
                color.updateNeighbors(colorMatrix, row, col);
            }
        }
        for (int row = 0; row < colorMatrix.length; row++) {
            for (int col = 0; col < colorMatrix[row].length; col++) {
                colorMatrix[row][col] = new MyColor(colorMatrix[row][col].neighborAverage);
            }
        }
    }

    @Override
	public void dispose () {
		pixmap.dispose();
		shapeRenderer.dispose();
	}

	private MyColor[][] pixmapToColorMatrix(Pixmap pixmap) {
		MyColor[][] matrix = new MyColor[pixmap.getHeight()][pixmap.getWidth()];
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				matrix[row][col] = new MyColor(pixmap.getPixel(col, row));
			}
		}
		return matrix;
	}

}

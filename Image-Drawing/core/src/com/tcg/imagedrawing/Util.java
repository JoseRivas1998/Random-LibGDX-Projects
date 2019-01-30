package com.tcg.imagedrawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/29/19
 */
public class Util {

    public static int[][] absoluteFilePathToColorMatrix(String absolutePath) {
        Pixmap pixmap = new Pixmap(Gdx.files.absolute(absolutePath));
        int[][] result = new int[pixmap.getHeight()][pixmap.getWidth()];
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                int color = pixmap.getPixel(
                        col,
                        pixmap.getHeight() - row - 1);
                result[row][col] = color;
            }
        }
        return result;
    }

}

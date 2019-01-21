package com.tcg.geneticimages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/20/19
 */
public class Util {

    private static int[] colorSet;

    public static Pixmap colorMatrixToPixmap(int[][] colors) {
        Pixmap pixmap = new Pixmap(colors[0].length, colors.length, Pixmap.Format.RGBA8888);
        for (int y = 0; y < colors.length; y++) {
            int[] row = colors[y];
            for (int x = 0; x < row.length; x++) {
                int color = row[x];
                pixmap.drawPixel(x, y, color);
            }
        }
        return pixmap;
    }

    public static Texture coloMatrixToTexture(int[][] colors) {
        return new Texture(colorMatrixToPixmap(colors));
    }

    public static int[][] absoluteFilePathToColorMatrix(String absolutePath) {
        List<Integer> colorSet = new ArrayList<>();
        Pixmap pixmap = new Pixmap(Gdx.files.absolute(absolutePath));
        int[][] result = new int[pixmap.getHeight()][pixmap.getWidth()];
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                int color = pixmap.getPixel(col, row);
                result[row][col] = color;
                colorSet.add(color);
            }
        }
        Util.colorSet = colorSet.stream().mapToInt(Integer::intValue).toArray();
        return result;
    }

    public static int randomRGBA() {
        return Util.colorSet[MathUtils.random(Util.colorSet.length - 1)];
    }

    public static int completelyRandomRGBA() {
        int r = MathUtils.random(0xFF);
        int g = MathUtils.random(0xFF);
        int b = MathUtils.random(0xFF);
        int a = MathUtils.random(0xFF);
        int rgba = r << 8;
        rgba |= g;
        rgba <<= 8;
        rgba |= b;
        rgba <<= 8;
        rgba |= a;
        return rgba;
    }

    public static float normalize(float value, float min, float max) {
        return (value - min) / (max - min);
    }

}

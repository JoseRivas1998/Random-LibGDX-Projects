package com.tcg.bubblesort;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game extends ApplicationAdapter {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static final int NUM_ELEMENTS = 50;

    private ShapeRenderer sr;
    private Viewport viewport;

    private int[] numbers;
    private boolean[] swapping;

    private int i;
    private int j;

    @Override
    public void create() {
        viewport = new StretchViewport(WIDTH, HEIGHT);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        sr = new ShapeRenderer();
        numbers = new int[NUM_ELEMENTS];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = MathUtils.random(HEIGHT);
        }
        swapping = new boolean[NUM_ELEMENTS];
        resetSwapping();
        i = 0;
        j = 0;
    }

    @Override
    public void render() {
        resetSwapping();
        if (i < numbers.length - 1) {
            if (j < numbers.length - i - 1) {
                if (numbers[j] > numbers[j + 1]) {
                    swap(numbers, j, j + 1);
                    swapping[j] = true;
                    swapping[j + 1] = true;
                }
                j++;
            } else {
                j = 0;
                i++;
            }
        }

        renderArray();

    }

    @Override
    public void dispose() {
        sr.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    private void resetSwapping() {
        for (int k = 0; k < swapping.length; k++) {
            swapping[k] = false;

        }
    }

    private void renderArray() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float rectWidth = (float) WIDTH / NUM_ELEMENTS;
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (int k = 0; k < numbers.length; k++) {
            int number = numbers[k];
            if (swapping[k]) {
                sr.setColor(Color.RED);
            } else if (k == j) {
                sr.setColor(Color.LIME);
            } else {
//				sr.setColor(new Color(genColor((float) number / HEIGHT)));
                sr.setColor(Color.WHITE);
            }
            float x = rectWidth * k;
            sr.rect(x, 0, rectWidth, number);
        }
        sr.end();


    }

    private int genColor(float h) {
        int color = java.awt.Color.HSBtoRGB(h, 1, 1);
        color <<= 8;
        color |= 0xFF;
        return color;
    }

}

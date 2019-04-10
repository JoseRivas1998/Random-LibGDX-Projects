package com.tcg.complexnumbers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game extends ApplicationAdapter {

    private float size;
    private Vector2 center;

    private ShapeRenderer sr;

    private final int WIDTH = 800;
    private final int HEIGHT = 800;

    private Viewport viewport;

    public final int[] COLORS = {
            0x00_00_00_FF,
            0xFF_00_00_FF,
            0xFF_00_FF_FF,
            0xFF_FF_00_FF,
            0xFF_FF_FF_FF,
            0x00_00_FF_FF,
            0x00_FF_00_FF,
            0x00_FF_FF_FF
    };

    int max = 2;

    @Override
    public void create() {
        viewport = new StretchViewport(WIDTH, HEIGHT);
        this.size = 2f;
        sr = new ShapeRenderer();
        size = 4f;
        center = new Vector2(0.25f, 0);
    }

    @Override
    public void render() {
        final float speed = 1e-2f;
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            center.y += speed * size;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            center.y -= speed * size;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            center.x += speed * size;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            center.x -= speed * size;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            size *= 0.9f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            size *= 1.1f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            max--;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            max++;
        }
        max = Math.max(max, 0);
        size = Math.max(size, 0);
        viewport.apply(true);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setProjectionMatrix(viewport.getCamera().combined);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                float x0 = map(i, 0, WIDTH, center.x - size / 2, center.x + size / 2);
                float y0 = map(j , 0, HEIGHT, center.y - size / 2, center.y + size / 2);
                ComplexNumber z0 = ComplexNumber.of(x0, y0);
                int grey = mand(z0, max);
                Color c = new Color(0, grey / (float) max, 0, 1);
                sr.setColor(c);
                sr.rect(i, j, 1, 1);
            }
        }
        sr.end();
        Gdx.graphics.setTitle(String.format("Madlebrot Set: Size: %f, Center: %s, Iterations: %d", size, center, max));
    }

    public static int mand(ComplexNumber z0, int max) {
        ComplexNumber z = ComplexNumber.of(z0);
        for (int i = 0; i < max; i++) {
            if (z.abs() > 2.82842f) {
                return i;
            }
            z = z.mult(z).add(z0);
        }
        return max;
    }

    public static float map(float value, float start1, float stop1, float start2, float stop2) {
        return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
    }

    public static Color grey(int grey) {
        float col = grey / 255f;
        return new Color(col, col, col, 1f);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}

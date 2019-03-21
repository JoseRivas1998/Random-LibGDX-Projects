package com.tcg.complexnumbers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Game extends ApplicationAdapter {

    private float size;
    private Vector2 center;

    private ShapeRenderer sr;

    private int n;

    @Override
    public void create() {
        n = Gdx.graphics.getWidth();
        this.size = 2f;
        sr = new ShapeRenderer();
        center = new Vector2(-0.5f, 0);
    }

    @Override
    public void render() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                float x0 = center.x - size / 2 + size * i / n;
                float y0 = center.y - size / 2 + size * j / n;
                ComplexNumber z0 = ComplexNumber.of(x0, y0);
                int grey = mand(z0, 255);
                Color c = new Color(Color.BLACK);
                sr.setColor(c.lerp(Color.GREEN, grey / 255f));
                sr.rect(i, n - 1 - j, 1, 1);
            }
        }
        sr.end();
    }

    public static int mand(ComplexNumber z0, int max) {
        ComplexNumber z = ComplexNumber.of(z0);
        for (int i = 0; i < max; i++) {
            if (z.abs() > 2.0) {
                return i;
            }
            z = z.mult(z).add(z0);
        }
        return max;
    }

    public static Color grey(int grey) {
        float col = grey / 255f;
        return new Color(col, col, col, 1f);
    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}

package com.tcg.butterflycurve;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class Curve extends ApplicationAdapter {

    private Viewport viewport;
    private ShapeRenderer sr;
    private Array<Vector2> points;
    private Array<Color> colors;
    private float theta;

    @Override
    public void create() {
        viewport = new ScreenViewport();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sr = new ShapeRenderer();
        points = new Array<Vector2>();
        colors = new Array<Color>();
        theta = getMin();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (theta <= getMax()) {
            float r = getScale() * r(theta);
            points.add(polarToRectangular(r, theta));
            Color base = new Color(getMinColor());
            Color target = new Color(getMaxColor());
            colors.add(base.lerp(target, percentage(theta)));
            theta += getDelta();
        }

        viewport.getCamera().position.set(new Vector3(0, 0, 0));
        viewport.getCamera().update();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.WHITE);
        sr.setProjectionMatrix(viewport.getCamera().combined);
        for (int i = 1; i < points.size; i++) {
            Vector2 curPoint = points.get(i);
            Color curColor = colors.get(i);
            Vector2 lastPoint = points.get(i - 1);
            Color lastColor = colors.get(i - 1);
            sr.line(curPoint.x, curPoint.y, lastPoint.x, lastPoint.y, curColor, lastColor);
        }
        sr.end();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
        viewport.getCamera().position.set(new Vector3(0, 0, 0));
        viewport.getCamera().update();
    }

    protected Vector2 polarToRectangular(float r, float theta) {
        float x = r * MathUtils.cos(theta);
        float y = r * MathUtils.sin(theta);
        return new Vector2(x, y);
    }

    protected abstract float r(float theta);

    public float getDelta() {
        return MathUtils.degreesToRadians * 0.5f;
    }

    public float getScale() {
        return 125f;
    }

    public final float getRange() {
        return getMax() - getMin();
    }

    public final float distanceFromMin(float val) {
        return val - getMin();
    }

    public final float percentage(float val) {
        return distanceFromMin(val) / getRange();
    }

    public float getMin() {
        return 0;
    }

    public float getMax() {
        return 12 * MathUtils.PI;
    }

    public Color getMinColor() {
        return new Color(Color.GREEN);
    }

    public Color getMaxColor() {
        return new Color(Color.PURPLE);
    }

}

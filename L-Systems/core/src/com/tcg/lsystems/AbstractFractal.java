package com.tcg.lsystems;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class AbstractFractal extends ApplicationAdapter {

    private final float camSpeed = 200f;

    protected ProductionRule[] rules;
    private Turtle turtle;
    private ShapeRenderer sr;
    private Viewport viewport;
    private int n;

    @Override
    public void create() {
        initRules();
        n = 0;
        updateTurtle();
        sr = new ShapeRenderer();
        viewport = new ScreenViewport();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            n++;
            updateTurtle();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            n = 0;
            updateTurtle();
            moveCamera(new Vector2(-viewport.getCamera().position.x, -viewport.getCamera().position.y));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveCamera(new Vector2(Vector2.Y).scl(camSpeed * dt));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveCamera(new Vector2(Vector2.Y).scl(-camSpeed * dt));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveCamera(new Vector2(Vector2.X).scl(camSpeed * dt));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveCamera(new Vector2(Vector2.X).scl(-camSpeed * dt));
        }

        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setProjectionMatrix(viewport.getCamera().combined);
        turtle.draw(sr);
        sr.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
    }

    @Override
    public void dispose() {
        sr.dispose();
    }

    protected abstract Turtle generateTree(String code);

    protected abstract void initRules();

    private void updateTurtle() {
        String code = Grammar.getNthProduction(n, getAxiom(), rules);
        turtle = generateTree(code);
    }

    protected abstract String getAxiom();

    private void moveCamera(Vector2 amount) {
        viewport.getCamera().position.add(new Vector3(amount, 0));
        viewport.getCamera().update();
    }
}

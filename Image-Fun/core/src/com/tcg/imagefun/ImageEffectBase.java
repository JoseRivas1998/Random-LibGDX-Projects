package com.tcg.imagefun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class ImageEffectBase extends ApplicationAdapter {

    protected MyImage image;
    protected ShapeRenderer sr;

    @Override
    public void create() {
        sr = new ShapeRenderer();
        image = new MyImage(Gdx.files.absolute(ImageFun.filePath));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        image.render(sr);
        sr.end();
    }

    @Override
    public void dispose() {
        sr.dispose();
        image.dispose();
    }
}

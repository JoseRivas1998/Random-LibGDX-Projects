package com.tcg.imagefun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class ImageEffectBase extends ApplicationAdapter {

    protected MyImage image;
    protected ShapeRenderer sr;
    private int frames;
    private float time;
    private int fps;

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
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Pixmap pixmap = this.image.toPixmap();
            PixmapIO.writePNG(Util.getScreenshotFile("screenshots/screenshot%02d.png"), pixmap);
            pixmap.dispose();
        }
        frames++;
        time += Gdx.graphics.getDeltaTime();
        if(time > 1f){
            fps = frames;
            frames = 0;
            time = 0;
        }
        Gdx.graphics.setTitle(String.format("%s | %d", ImageFun.title, fps));

    }

    @Override
    public void dispose() {
        sr.dispose();
        image.dispose();
    }
}

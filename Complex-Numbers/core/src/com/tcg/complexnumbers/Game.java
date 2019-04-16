package com.tcg.complexnumbers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Game extends ApplicationAdapter {

    private float size;
    private Vector2 center;

    private ShapeRenderer sr;

    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;

    private Viewport viewport;

    private final float GOLDEN_RATIO = 1.61803398875f;

    boolean saveGif;
    private List<String> files;

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

    private int max = 2;

    private String imagePath;
    private int[][] colorMatrix;

    public Game(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public void create() {
        generateColorMatrix();
        viewport = new StretchViewport(colorMatrix[0].length, colorMatrix.length);
        Gdx.graphics.setWindowedMode((int) viewport.getWorldWidth(), (int) viewport.getWorldHeight());
        this.size = 2f;
        sr = new ShapeRenderer();
        size = 4f;
        center = new Vector2(0, 0);
        saveGif = true;
        files = new ArrayList<>();
    }

    private void generateColorMatrix() {
        Pixmap pixmap = new Pixmap(Gdx.files.internal(this.imagePath));
        colorMatrix = new int[pixmap.getHeight()][pixmap.getWidth()];
        for (int row = 0; row < pixmap.getHeight(); row++) {
            for (int col = 0; col < pixmap.getWidth(); col++) {
                colorMatrix[row][col] = pixmap.getPixel(col, pixmap.getHeight() - row);
            }
        }
        pixmap.dispose();

    }

    @Override
    public void render() {
        final float speed = 1e-2f;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            center.y += speed * size;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            center.y -= speed * size;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            center.x += speed * size;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            center.x -= speed * size;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            size *= 0.9f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            size *= 1.1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            max--;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            max++;
        }
        max = Math.max(max, 0);
        size = Math.max(size, 0);
        viewport.apply(true);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setProjectionMatrix(viewport.getCamera().combined);
        for (int i = 0; i < colorMatrix[0].length; i++) {
            for (int j = 0; j < colorMatrix.length; j++) {
                float x0 = map(i, 0, WIDTH, center.x - size / 2, center.x + size / 2);
                float y0 = map(j, 0, HEIGHT, center.y - size / 2, center.y + size / 2);
                ComplexNumber z0 = ComplexNumber.of(x0, y0);
                int grey = mand(z0, max);
//                Color c = new Color(0, grey / (float) max, 0, 1);
//                Color c = new Color(COLORS[grey % COLORS.length]);
                Color c = new Color(Color.BLACK);
                c.lerp(new Color(colorMatrix[j][i]), grey / (float) max);
                sr.setColor(c);
                sr.rect(i, j, 1, 1);
            }
        }
        sr.end();
//        if (angle < MathUtils.PI2) {
//            takeScreenshot();
//        } else if(saveGif) {
//            System.out.println("Starting");
//            saveGif();
//            System.out.println("done");
//            saveGif = false;
//        }
        Gdx.graphics.setTitle(String.format("Madlebrot Set: Size: %f, Center: %s, Iterations: %d", size, center, max));
    }

    private void saveGif() {
        try (ImageOutputStream imageOutputStream = new FileImageOutputStream(new File("output.gif"))){
            BufferedImage first = ImageIO.read(new File(files.get(0)));

            float length = 3e3f;

            int millisPerFrame = (int) (length / files.size());

            GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(imageOutputStream, first.getType(), millisPerFrame, true);

            for (String file : files) {
                BufferedImage img = ImageIO.read(new File(file));
                gifSequenceWriter.writeToSequence(img);
            }

            gifSequenceWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void takeScreenshot() {
        int screenShotNum = 0;
        String fileName = String.format("screenshots/screenshot%03d.png", screenShotNum);
        while (Gdx.files.local(fileName).exists()) {
            screenShotNum++;
            fileName = String.format("screenshots/screenshot%03d.png", screenShotNum);
        }
        byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);
// this loop makes sure the whole screenshot is opaque and looks exactly like what the user is seeing
        for (int i = 4; i < pixels.length; i += 4) {
            pixels[i - 1] = (byte) 255;
        }
        Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
        BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
        PixmapIO.writePNG(Gdx.files.local(fileName), pixmap);
        files.add(fileName);
        pixmap.dispose();
    }

    public static int mand(ComplexNumber z0, ComplexNumber c, int max) {
        final float maxAbs = ComplexNumber.of(2, 2).abs();
        ComplexNumber z = ComplexNumber.of(z0);
        for (int i = 0; i < max; i++) {
            if (z.abs() > maxAbs * 2) {
                return i;
            }
            z = z.mult(z).add(c);
        }
        return max;
    }

    public static int mand(ComplexNumber z0, int max) {
        return mand(z0, z0, max);
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
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}

package com.tcg.imagefun;


import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

public class MyImage implements Disposable {

    private Pixmap pixmap;
    public MyColor[][] pixels;
    public final int width;
    public final int height;

    public MyImage(FileHandle file) {
        pixmap = new Pixmap(file);
        width = pixmap.getWidth();
        height = pixmap.getHeight();
        pixels = new MyColor[pixmap.getHeight()][pixmap.getWidth()];
        reset();
    }

    public void reset() {
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[row].length; col++) {
                pixels[row][col] = new MyColor(pixmap.getPixel(col, row));
            }
        }
    }

    public void render(ShapeRenderer sr) {
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[row].length; col++) {
                sr.setColor(pixels[row][col].toColor());
                sr.rect(col, pixels.length - row - 1, 1, 1);
            }
        }
    }

    @Override
    public void dispose() {
        pixmap.dispose();
    }

    public Pixmap toPixmap() {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[row].length; col++) {
                pixmap.setColor(pixels[row][col].toColor());
                pixmap.drawPixel(col, row);
            }
        }
        return pixmap;
    }

    public class MyColor implements Comparable<MyColor> {
        public int r;
        public int g;
        public int b;
        public int a;

        public MyColor() {
            this.set();
        }

        public MyColor(int r, int g, int b, int a) {
            this.set(r, g, b, a);
        }

        public MyColor(int rgba) {
            this.set(rgba);
        }

        public MyColor(MyColor color) {
            this.set(color );
        }

        public void set() {
            this.r = 0;
            this.g = 0;
            this.b = 0;
            this.a = 0;
        }

        public void set(int r, int g, int b, int a) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
        }

        public void set(int rgba) {
            this.a = rgba & 0xFF;
            int rgb = rgba >> 8;
            this.b = rgb & 0xFF;
            int rg = rgb >> 8;
            this.g = rg & 0xFF;
            this.r = rg >> 8;
        }

        public void set(MyColor color) {
            this.set(color.r, color.g, color.b, color.a);
        }

        public int toRGBA() {
            int r = this.r;
            int rg = r << 8;
            rg |= this.g;
            int rgb = rg << 8;
            rgb |= this.b;
            int rgba = rgb << 8;
            rgba |= this.a;
            return rgba;
        }

        public Color toColor() {
            return new Color(toRGBA());
        }

        public int magSq() {
            return (int) (Math.pow(this.r, 2) + Math.pow(this.g, 2) + Math.pow(this.b, 2) + Math.pow(this.a, 2));
        }

        public int mag() {
            return (int) Math.sqrt(magSq());
        }

        public double avg() {
            return (this.r + this.g + this.b) / 3.0;
        }

        @Override
        public int compareTo(MyColor o) {
            return this.magSq() - o.magSq();
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                MyColor other = (MyColor) o;
                return other.toRGBA() == this.toRGBA();
            }
        }
    }

}

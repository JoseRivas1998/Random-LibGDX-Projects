package com.tcg.minesweeper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

public class Content {

    public enum Font {
        ONE  (16, 0x0000FFFF),
        TWO  (16, 0x007B00FF),
        THREE(16, 0xFF0000FF),
        FOUR (16, 0x00007BFF),
        FIVE (16, 0x7B0000FF)
        ;
        public final int size;
        public final int color;

        Font(int size, int color) {
            this.size = size;
            this.color = color;
        }
    }

    private Map<Font, BitmapFont> fonts;
    private GlyphLayout gl;

    public Content() {
        fonts = new HashMap<Font, BitmapFont>();
        for (Font value : Font.values()) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ariblk.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
            param.size = value.size;
            param.color = new Color(value.color);
            fonts.put(value, generator.generateFont(param));
            generator.dispose();
        }
        gl = new GlyphLayout();
    }

    public BitmapFont getFont(Font font) {
        return fonts.get(font);
    }

    public float getWidth(Font font, String s) {
        gl.setText(getFont(font), s);
        return gl.width;
    }

    public float getWidth(Font font, String s, float targetWidth, int halign, boolean wrap) {
        gl.setText(getFont(font), s, getFont(font).getColor(), targetWidth, halign, wrap);
        return gl.width;
    }

    public float getHeight(Font font, String s) {
        gl.setText(getFont(font), s);
        return gl.height;
    }

    public float getHeight(Font font, String s, float targetWidth, int halign, boolean wrap) {
        gl.setText(getFont(font), s, getFont(font).getColor(), targetWidth, halign, wrap);
        return gl.height;
    }
    public void dispose() {
        for (Font value : Font.values()) {
            fonts.get(value).dispose();
        }
    }

}

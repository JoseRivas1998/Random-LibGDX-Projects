package com.tcg.imagedrawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/29/19
 */
public class TiledBackground {
    private Texture texture;

    public TiledBackground(String texturePath) {
        texture = new Texture(texturePath);
    }

    public void draw(SpriteBatch sb) {
        int numRows = Gdx.graphics.getWidth() / texture.getWidth();
        if(numRows * texture.getWidth() < Gdx.graphics.getWidth()) {
            numRows++;
        }
        int numCols = Gdx.graphics.getHeight() / texture.getHeight();
        if(numCols * texture.getHeight() < Gdx.graphics.getHeight()) {
            numCols++;
        }
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                float x = row * texture.getWidth();
                float y = col * texture.getHeight();
                sb.draw(texture, x, y);
            }
        }
    }

    public void dispose() {
        texture.dispose();
    }

}

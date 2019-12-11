package com.breiter.littleredhood.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class GlyphMap {

    private BitmapFont bitmapFont;
    private GlyphLayout layout;
    private float x;
    private float y;

    public GlyphMap(Color colour, float scale) {

        bitmapFont = new BitmapFont();
        bitmapFont.setColor(colour);
        bitmapFont.getData().setScale(scale);

        layout = new GlyphLayout();
    }

    //Display scores in top, right corner:
    public void setText(String text){
        layout.setText(bitmapFont, text);
    }

    public void draw(SpriteBatch batch, float x, float y) {
        this.x = x;
        this.y = y ;

        //Bitmap draws from the top-left corner
        bitmapFont.draw(batch, layout, this.x, this.y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHeight() {
        return layout.height;
    }

    public float getWidth() {
        return layout.width;
    }

    public void dispose() {
        bitmapFont.dispose();

    }
}

package com.breiter.littleredhood.figure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public abstract class Figure {

    private Random random = new Random();
    private Texture texture;
    private Vector2 position;
    private Rectangle bounds;


    public Figure(String path, float x) {
        texture = new Texture(path);
        position = new Vector2(x, getRandomY());
        bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());

    }

    public Figure(String path, float x, float y) {
        this(path, x);
        position = new Vector2(x, y);
    }

    public void moveLeft(float x) {
        position.x -= x;
        updateBounds();
    }


    public boolean fadeOnLeft() {
        return position.x < -texture.getWidth();

    }

    public void moveToBeginning(float x) {
        position.set(x, getRandomY());
        updateBounds();
    }


    public boolean colliding(Rectangle player) {
        return player.overlaps(bounds);

    }

    public void updateBounds() {
        bounds.setPosition(position.x, position.y);
    }

    public float getRandomY() {
        float maxY = Gdx.graphics.getHeight() - texture.getHeight();
        float minY = texture.getHeight();
        float y = random.nextFloat() * Gdx.graphics.getHeight();

        if (y > maxY)
            y = maxY;

        else if (y < minY)
            y = minY;

        return y;

    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
    }

}

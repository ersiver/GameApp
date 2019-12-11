package com.breiter.littleredhood.figure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
import static com.breiter.littleredhood.GameLRH.gameHeight;

public class WolfDuo extends Figure {
    public static final int WIDTH = 128;

    private Random r ;
    private float gap;
    private Vector2 position;
    private Vector2 positionBot;
    private Rectangle bounds;
    private Rectangle boundsBot;

    public WolfDuo(String path, float x) {
        super(path, x);

        r = new Random();
        position = new Vector2(x, getRandomY());
        positionBot = new Vector2(position.x + 10, position.y - gap);

        bounds = new Rectangle(position.x, position.y, getTexture().getWidth(), getTexture().getHeight());
        boundsBot = new Rectangle(positionBot.x, position.y, getTexture().getHeight(), getTexture().getHeight());
    }


    public void moveLeft(float velocityTop, float velocityBottom) {
        position.x -= velocityTop;
        positionBot.x -= velocityBottom;
        updateBounds();
    }


    @Override
    public boolean fadeOnLeft() {
        return position.x < -getTexture().getWidth();
    }


    @Override
    public void moveToBeginning(float x) {
        position.set(x, getRandomY());
        positionBot.set(x, position.y-gap);

        updateBounds();
    }


    @Override
    public void updateBounds() {
        bounds.setPosition(position.x, position.y);
        boundsBot.setPosition(positionBot.x, positionBot.y);
    }


    @Override
    public float getRandomY() {
        r = new Random();
        gap = r.nextFloat() * gameHeight() * 0.60f + getTexture().getHeight();
        float maxY = Gdx.graphics.getHeight() - getTexture().getHeight();
        float minY = gap;
        float y = r.nextFloat() * Gdx.graphics.getHeight();

        if (y > maxY)
            y = maxY;
        else if (y < minY)
            y = minY;

        return y;
    }


    @Override
    public boolean colliding(Rectangle player) {
        return player.overlaps(bounds) || player.overlaps(boundsBot);

    }


    public Vector2 getPositionTop() {
        return position;
    }


    public Vector2 getPositionBot() {
        return positionBot;
    }

}



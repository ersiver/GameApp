package com.breiter.littleredhood.figure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class LittleGirl extends Figure {

    public static final float GRAVITY = 0.3f;
    public static final int HEIGHT = 235; //texture height

    private Texture[] textures;
    private Rectangle bounds;
    private Vector2 position;

    private float velocity;
    private int walkState; //reflects an index in the textures array
    private int pause; //used to delay execution of the statement

    private Texture headIcon; //used in LevelTwo as a symbol of lives

    public LittleGirl(String path, float x, float y) {
        super(path, x, y);

        textures = new Texture[5];
        textures[0] = new Texture("im_walk0.png");
        textures[1] = new Texture("im_walk1.png");
        textures[2] = new Texture("im_walk2.png");
        textures[3] = new Texture("im_walk3.png");
        textures[4] = new Texture("im_walk4.png");

        position = new Vector2(x,y);
        bounds = new Rectangle(x, y, textures[walkState].getWidth(), textures[walkState].getHeight());

        headIcon = new Texture("im_head.png");
    }

    @Override
    public void updateBounds() {
        bounds.setPosition(position.x, position.y);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }


    public void fallDown() {
        velocity+= GRAVITY;
        getPosition().y -= velocity;
    }

    public void jump() {
        velocity = -6.5f;

    }

    public void jumpHigh() {
        velocity = -6.7f;

    }

    public void update() {

        velocity += GRAVITY;
        getPosition().y -= velocity;

            if (getPosition().y <= 0)
                getPosition().y = 0;

            setWalkingFrequency();
            updateBounds();
    }


    //Determine walkState value
    private void setWalkingFrequency() {

        if (pause < 3)
            pause++;
        else {
            pause = 0;

            if (walkState < 3)
                walkState++;
             else
                walkState = 0;

        }
    }

    public int getWalkState() {
        return walkState;
    }

    public Texture[] getTextures() {
        return textures;
    }

    public Texture getHeadIcon() {
        return headIcon;
    }

    @Override
    public void dispose() {
        for (Texture texture : textures)
            texture.dispose();


    }


}
package com.breiter.littleredhood.game;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public abstract class State {

    protected GameStateManager gsm;

    public State(GameStateManager gsm){
        this.gsm = gsm;

    }

    protected abstract void handleInput();
    public abstract void update();
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();
}

package com.breiter.littleredhood;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.breiter.littleredhood.game.GameStateManager;
import com.breiter.littleredhood.game.MenuState;

public class GameLRH extends ApplicationAdapter {

    public static final String TITLE = "Little Red Hood";

    private GameStateManager gsm;
    private SpriteBatch batch;

    public static float gameWidth() {
        return Gdx.graphics.getWidth();
    }

    public static float gameHeight(){
        return Gdx.graphics.getHeight();
    }


    @Override
    public void create() {

        batch = new SpriteBatch();
        gsm = new GameStateManager();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        gsm.push(new MenuState(gsm));

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update();
        gsm.render(batch);

    }

    @Override
    public void dispose() {
        super.dispose();

    }

}

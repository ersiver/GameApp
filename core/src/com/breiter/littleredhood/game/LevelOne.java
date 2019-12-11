package com.breiter.littleredhood.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.breiter.littleredhood.figure.Berry;
import com.breiter.littleredhood.figure.LittleGirl;
import com.breiter.littleredhood.figure.Wolf;
import com.breiter.littleredhood.figure.Figure;
import com.breiter.littleredhood.util.GlyphMap;
import com.breiter.littleredhood.util.MusicBuilder;
import java.util.ArrayList;
import java.util.List;
import static com.breiter.littleredhood.GameLRH.gameHeight;
import static com.breiter.littleredhood.GameLRH.gameWidth;

public class LevelOne extends State {
    private static final int NUMBER_OF_WOLVES = 8;
    private static final float DISTANCE_BETWEEN_WOLVES = gameWidth() * 0.71f;
    private static final int NUMBER_OF_BERRIES = 5;
    private static final float DISTANCE_BETWEEN_BERRIES = gameWidth();

    private Texture background;
    private MusicBuilder musicBuilder;
    private Sound success;
    private GlyphMap glyphMap; //displays scores

    private LittleGirl littleGirl;
    private List<Figure> wolves;
    private List<Figure> berries;

    private float velocityWolf = 7.0f;
    private float velocityBerry = 7.5f;
    private int berriesLeft = NUMBER_OF_BERRIES;
    private static int scores;


    public LevelOne(GameStateManager gsm) {
        super(gsm);

        background = new Texture("im_bg.jpeg");
        musicBuilder = MusicBuilder.create().play("music.mp3",0.2f);
        success = Gdx.audio.newSound(Gdx.files.internal("s_success.wav"));
        glyphMap = new GlyphMap(Color.YELLOW, 4);

        littleGirl = new LittleGirl("im_walk0.png",gameWidth()/ 6, gameHeight()/ 2 - LittleGirl.HEIGHT / 2);

        wolves = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_WOLVES; i++)
            wolves.add(new Wolf("im_wolf1.png", i * (Wolf.WIDTH + DISTANCE_BETWEEN_WOLVES)));

        berries = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_BERRIES; i++) {
            berries.add(new Berry("im_berry.png" ,i * (Berry.WIDTH + DISTANCE_BETWEEN_BERRIES)));
        }

        scores = 0;
    }


    public static int getScores() {
        return scores;
    }


    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
            littleGirl.jump();
    }


    @Override
    public void update() {
        handleInput();

       littleGirl.update();
        wolveslUpdate();
        berrieslUpdate();

    }


    //responsible for wolves' moving and collisions
    private void wolveslUpdate() {
        for (Figure wolf : wolves) {

            if (wolf.fadeOnLeft())
                wolf.moveToBeginning(wolf.getPosition().x + (Wolf.WIDTH + DISTANCE_BETWEEN_WOLVES) * NUMBER_OF_WOLVES);
            else
                wolf.moveLeft(velocityWolf);

            if (wolf.colliding(littleGirl.getBounds())) {
                gsm.set(new GameOver(gsm)); //Game Over

            }
        }
    }


    //responsible for collecting berries
    private void berrieslUpdate() {

        List<Figure> toBeRemove = new ArrayList<>();

        for (Figure berry : berries) {

            if (berry.fadeOnLeft())
                berry.moveToBeginning(berry.getPosition().x + (Berry.WIDTH + DISTANCE_BETWEEN_BERRIES) * berriesLeft);
            else
                berry.moveLeft(velocityBerry);

            if (berry.colliding(littleGirl.getBounds())) {
                toBeRemove.add(berry);
                berriesLeft--;
                scores++;

                if (scores == NUMBER_OF_BERRIES)
                    continue;

                success.play(0.05f);
            }
        }
        berries.removeAll(toBeRemove); //collected berries are removed from the List 'berries'

        increaseDifficulties(); //the more berries collected, the highest speed of wolves & berries

        if (berries.isEmpty()) {
            gsm.set(new LevelsInterval(gsm)); //Next Level
        }
    }

    private void increaseDifficulties() {

        if (berriesLeft <= 7)
            velocityWolf = 7.5f;

        if (berriesLeft <= 4) {
            velocityWolf = 7.9f;
            velocityBerry = 8.2f;
        }
    }


    @Override
    public void render(SpriteBatch batch) {
        batch.begin();

        batch.draw(background, 0, 0, gameWidth(), gameHeight());
        batch.draw(littleGirl.getTextures()[littleGirl.getWalkState()], littleGirl.getPosition().x, littleGirl.getPosition().y);

        for (Figure wolf : wolves)
            batch.draw(wolf.getTexture(), wolf.getPosition().x, wolf.getPosition().y);

        for (Figure berry : berries)
            batch.draw(berry.getTexture(), berry.getPosition().x, berry.getPosition().y);

        glyphMap.setText("L1: " + scores + "/" + NUMBER_OF_BERRIES);
        glyphMap.draw(batch, gameWidth() - glyphMap.getWidth() - 10, gameHeight() - 10);

        batch.end();
    }

    @Override
    public void dispose() {

        background.dispose();
        littleGirl.dispose();
        musicBuilder.dispose();
        success.dispose();
        glyphMap.dispose();

        for (Figure w : wolves)
            w.dispose();

        for (Figure b : berries)
            b.dispose();


    }
}
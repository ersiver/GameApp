package com.breiter.littleredhood.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.breiter.littleredhood.figure.Berry;
import com.breiter.littleredhood.figure.LittleGirl;
import com.breiter.littleredhood.figure.WolfDuo;
import com.breiter.littleredhood.figure.Figure;
import com.breiter.littleredhood.util.GlyphMap;
import com.breiter.littleredhood.util.MusicBuilder;
import java.util.ArrayList;
import java.util.List;
import static com.breiter.littleredhood.GameLRH.gameHeight;
import static com.breiter.littleredhood.GameLRH.gameWidth;

public class LevelTwo extends State {
    private static final int NUMBER_OF_WOLVES = 8;
    private static final float DISTANCE_BETWEEN_WOLVES = gameWidth() * 0.71f;
    private static final int NUMBER_OF_BERRIES = 5;
    private static final float DISTANCE_BETWEEN_BERRIES = gameWidth();

    private Texture background;
    private MusicBuilder musicBuilder;
    private Sound success;
    private GlyphMap glyphMap;

    private LittleGirl littleGirl;
    static int chances = 3; //reduced in the ChanceInterval
    private static int scores;

    private List<WolfDuo> wolves;
    private float velocityTop = 7.5f;
    private float velocityBot = 7.9f;

    private List<Figure> berries;
    private float velocityBerry = 8.2f;
    private int berriesLeft = NUMBER_OF_BERRIES;


    public LevelTwo(GameStateManager gsm) {
        super(gsm);

        background = new Texture("im_bg.jpeg");
        musicBuilder = MusicBuilder.create().play("music.mp3",0.2f);
        success = Gdx.audio.newSound(Gdx.files.internal("s_success.wav"));
        glyphMap = new GlyphMap(Color.YELLOW, 4);

        littleGirl = new LittleGirl("im_walk0.png", gameWidth() / 6, gameHeight() / 2 - LittleGirl.HEIGHT / 2);

        wolves = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_WOLVES; i++)
            wolves.add(new WolfDuo("im_wolf2.png" , i * (WolfDuo.WIDTH + DISTANCE_BETWEEN_WOLVES)));

        berries = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_BERRIES; i++)
            berries.add(new Berry("im_berry.png", i * (Berry.WIDTH + DISTANCE_BETWEEN_BERRIES)));

        scores = 0;

    }


    public static int getScores() {
        return scores;
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
            littleGirl.jumpHigh();
    }

    @Override
    public void update() {
        handleInput();

        littleGirl.update();
        wolveslUpdate();
        berrieslUpdate();

    }


    //responsible for wolves:
    private void wolveslUpdate() {
        for (WolfDuo wolf : wolves) {

            if (wolf.fadeOnLeft())
                wolf.moveToBeginning(wolf.getPositionTop().x + (WolfDuo.WIDTH + DISTANCE_BETWEEN_WOLVES) * NUMBER_OF_WOLVES);
            else
                wolf.moveLeft(velocityTop, velocityBot);


            if (wolf.colliding(littleGirl.getBounds())) {
                if (chances == 1)
                    gsm.set(new GameOver(gsm)); //game over
                else
                    gsm.set(new ChanceInterval(gsm)); //another chance
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

                if (berries.size() == 1)
                    continue; //When the last berries is collected, sound is skipped

                success.play(0.05f);
            }
        }
        berries.removeAll(toBeRemove);

        if (berries.isEmpty())
            gsm.set(new PlayerWin(gsm)); //player wins
    }


    @Override
    public void render(SpriteBatch batch) {

        batch.begin();
        batch.draw(background, 0, 0, gameWidth(), gameHeight());

        glyphMap.setText("L2: " + scores + "/" + NUMBER_OF_BERRIES);
        glyphMap.draw(batch, gameWidth() - glyphMap.getWidth() - 10, gameHeight() - 10);

        batch.draw(littleGirl.getTextures()[littleGirl.getWalkState()], littleGirl.getPosition().x, littleGirl.getPosition().y);

        for (WolfDuo wolf : wolves) {
            batch.draw(wolf.getTexture(), wolf.getPositionTop().x, wolf.getPositionTop().y);
            batch.draw(wolf.getTexture(), wolf.getPositionBot().x, wolf.getPositionBot().y);
        }

        for (Figure berry : berries)
            batch.draw(berry.getTexture(), berry.getPosition().x, berry.getPosition().y);

        if (chances == 3) {
            batch.draw(littleGirl.getHeadIcon(), headPositionX(1), headPositionY());
            batch.draw(littleGirl.getHeadIcon(), headPositionX(2), headPositionY());
            batch.draw(littleGirl.getHeadIcon(), headPositionX(3), headPositionY());
        }

        if (chances == 2) {
            batch.draw(littleGirl.getHeadIcon(), headPositionX(1), headPositionY());
            batch.draw(littleGirl.getHeadIcon(), headPositionX(2), headPositionY());
        }

        if (chances == 1)
            batch.draw(littleGirl.getHeadIcon(), headPositionX(1), headPositionY());

        batch.end();
    }

    private float headPositionX(int x) {
        return gameWidth() - littleGirl.getHeadIcon().getWidth() * x - 10;  //icons are position in right top corner
    }

    private float headPositionY() {
        return glyphMap.getY() - littleGirl.getHeadIcon().getHeight() * 2 - 10; //note bitmapFont starting point is top-right corner
    }

    @Override
    public void dispose() {
        background.dispose();
        littleGirl.dispose();
        musicBuilder.dispose();
        success.dispose();
        glyphMap.dispose();

        for (WolfDuo wolf : wolves)
            wolf.dispose();

        for (Figure b : berries)
            b.dispose();



    }

}
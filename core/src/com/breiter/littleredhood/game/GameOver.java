package com.breiter.littleredhood.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.breiter.littleredhood.figure.LittleGirl;
import com.breiter.littleredhood.util.LayoutManager;

import static com.breiter.littleredhood.GameLRH.gameWidth;
import static com.breiter.littleredhood.GameLRH.gameHeight;


public class GameOver extends State {

    private LayoutManager layout;
    private ImageButton imageButton;
    private Stage stage;
    private LittleGirl littleGirl;

    public GameOver(GameStateManager gsm) {
        super(gsm);

        //setup images and sound:
        layout = new LayoutManager();
        layout.setBackground("im_bg.jpeg");
        layout.setPlayButton("im_playBtn.png");
        layout.setTitleImage("t_gameOver.png");
        layout.setIconLeft("im_dizzyL.png");
        layout.setIconRight("im_dizzyR.png");
        layout.playSound("s_gameOver.wav");

        //setup a clickable button:
        imageButton = layout.getImageButton();
        stage = new Stage(new ScreenViewport());
        stage.addActor(imageButton);
        Gdx.input.setInputProcessor(stage);

        //will invoked 'falling down' dizzy texture
        littleGirl = new LittleGirl("im_dizzyL.png" , gameWidth()/2, gameHeight() - LittleGirl.HEIGHT);

        //reset value of static variable lives
        LevelTwo.chances = 3;
    }

    @Override
    protected void handleInput() {
        //once playButton is clicked, game starts from the beginning
        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new LevelOne(gsm));
            }
        });
    }


    @Override
    public void update() {
        handleInput();

        littleGirl.fallDown(); //will be falling down
    }


    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(layout.getBackground(), 0, 0, gameWidth(), gameHeight());
        batch.draw(layout.getIconLeft(), layout.getPositionIconlLeft().x, layout.getPositionIconlLeft().y);
        batch.draw(layout.getIconRight(), layout.getPositionIconlRight().x, layout.getPositionIconlRight().y);
        batch.draw(littleGirl.getTexture(), littleGirl.getPosition().x, littleGirl.getPosition().y);
        batch.draw(layout.getTitleImage(), layout.getPositionTitle().x, layout.getPositionTitle().y);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }


    @Override
    public void dispose() {
        layout.dispose();
        stage.dispose();
        littleGirl.dispose();

    }

}

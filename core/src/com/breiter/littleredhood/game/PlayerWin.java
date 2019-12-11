package com.breiter.littleredhood.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.breiter.littleredhood.util.GlyphMap;
import com.breiter.littleredhood.util.LayoutManager;

import static com.breiter.littleredhood.GameLRH.gameHeight;
import static com.breiter.littleredhood.GameLRH.gameWidth;

public class PlayerWin extends State {

    private LayoutManager layout;
    private ImageButton imageButton;
    private Stage stage;
    private GlyphMap glyphMap; //connects BitmapFont and GlyphLayout

    public PlayerWin(GameStateManager gsm) {
        super(gsm);

        //setup images and sound:
        layout = new LayoutManager();
        layout.setBackground("im_bg.jpeg");
        layout.setPlayButton("im_playBtn.png");
        layout.setTitleImage("t_youWon.png");
        layout.setIconLeft("im_jumpL.png");
        layout.setIconRight("im_jumpR.png");
        layout.playSound("s_fanfare.wav");

        //setup a clickable button:
        imageButton = layout.getImageButton();
        stage = new Stage(new ScreenViewport());
        stage.addActor(imageButton);
        Gdx.input.setInputProcessor(stage);

        //setup glyphMap, that will display info
        glyphMap = new GlyphMap(Color.YELLOW, 3);

        //resetting value of static variable lives
        LevelTwo.chances = 3;
    }


    @Override
    protected void handleInput() {
        //once playButton is clicked, game starts again:
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
    }


    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(layout.getBackground(), 0, 0, gameWidth(), gameHeight());
        batch.draw(layout.getIconLeft(), layout.getPositionIconlLeft().x, layout.getPositionIconlLeft().y);
        batch.draw(layout.getIconRight(), layout.getPositionIconlRight().x, layout.getPositionIconlRight().y);
        batch.draw(layout.getTitleImage(), layout.getPositionTitle().x, layout.getPositionTitle().y);
        glyphMap.setText("Great score! You have " + (LevelOne.getScores()+ LevelTwo.getScores()) + " berries.");
        glyphMap.draw(batch,gameWidth()/2 - glyphMap.getWidth()/2, layout.getPositionPlayBtn().y-60 );
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }


    @Override
    public void dispose() {
        layout.dispose();
        stage.dispose();
        glyphMap.dispose();

    }

}

package com.breiter.littleredhood.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
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

public class ChanceInterval extends State{


    private LayoutManager layout;
    private ImageButton imageButton;
    private Stage stage;
    private GlyphMap glyphMap; //connects BitmapFont and GlyphLayout

    public ChanceInterval(GameStateManager gsm) {
        super(gsm);

        //setup images and sound:
        layout = new LayoutManager();
        layout.setBackground("im_bg.jpeg");
        layout.setPlayButton("im_playBtn.png");
        layout.setTitleImage("t_interval.png");
        layout.playSound("s_warning.wav");

        //setup a clickable button:
        imageButton = layout.getImageButton();
        stage = new Stage(new ScreenViewport());
        stage.addActor(imageButton);
        Gdx.input.setInputProcessor(stage);

        //setup glyphMap, that will display scores
        glyphMap = new GlyphMap(Color.YELLOW, 3);

        //reduce value of static variable lives
        LevelTwo.chances --;


    }

    @Override
    protected void handleInput() {
            //once playButton is clicked, game starts again
            imageButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gsm.set(new LevelTwo(gsm));
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
        batch.draw(layout.getTitleImage(), layout.getPositionTitle().x, layout.getPositionTitle().y);
        glyphMap.setText(message());
        glyphMap.draw(batch,gameWidth()/2 - glyphMap.getWidth()/2, layout.getPositionPlayBtn().y-60 );
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }


    private String message(){
        String chance = LevelTwo.chances + " chance";

        if (LevelTwo.chances > 1)
            chance = chance.concat("s");

        if(LevelTwo.chances == 1)
            chance = "last chance";

        return "You have " + chance + " left...";
    }


    @Override
    public void dispose() {
        layout.dispose();
        stage.dispose();
        glyphMap.dispose();
    }

}

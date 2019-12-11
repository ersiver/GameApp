package com.breiter.littleredhood.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.breiter.littleredhood.util.LayoutManager;

import static com.breiter.littleredhood.GameLRH.gameHeight;
import static com.breiter.littleredhood.GameLRH.gameWidth;

public class MenuState extends State {

    private LayoutManager layout;
    private ImageButton imageButton;
    private Stage stage;

    public MenuState(GameStateManager gsm) {
        super(gsm);

        //setup images:
        layout = new LayoutManager();
        layout.setBackground("im_bg.jpeg");
        layout.setPlayButton("im_playBtn.png");
        layout.setTitleImage("t_title.png");
        layout.setIconLeft("im_jumpL.png");
        layout.setIconRight("im_jumpR.png");

        //setup a clickable button:
        imageButton = layout.getImageButton();
        stage = new Stage(new ScreenViewport());
        stage.addActor(imageButton);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    protected void handleInput() {
        //Once playButton is clicked, game starts
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
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void dispose() {
        layout.dispose();
        stage.dispose();

    }
}

package com.breiter.littleredhood.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static com.breiter.littleredhood.GameLRH.gameWidth;
import static com.breiter.littleredhood.GameLRH.gameHeight;

public class LayoutManager {

    private Texture background;
    private Texture playButton;
    private Texture titleImage;
    private Texture iconLeft;
    private Texture iconRight;
    private Vector2 positionPlayBtn;
    private Vector2 positionTitle;
    private Vector2 positionIconlLeft;
    private Vector2 positionIconlRight;
    private ImageButton imageButton;
    private Sound sound;

    public void setBackground(String backgroundPath) {
        background = new Texture(backgroundPath);

    }

    public void setPlayButton(String playButtonPath) {
        //Horizontally centered, vertically bottomed
        playButton = new Texture(playButtonPath);
        positionPlayBtn = new Vector2(gameWidth() / 2 - playButton.getWidth() / 2, gameHeight() * 0.4f - playButton.getHeight() / 2);

    }

    public ImageButton getImageButton() {
        imageButton = new ImageButton(new TextureRegionDrawable(playButton));
        imageButton.setPosition(positionPlayBtn.x, positionPlayBtn.y);
        return imageButton;

    }

    public void setTitleImage(String titleImagePath) {
        //Horizontally centered, vertically above the play button
        titleImage = new Texture(titleImagePath);
        positionTitle = new Vector2(gameWidth() / 2 - titleImage.getWidth() / 2, positionPlayBtn.y + 260);
    }

    public void setIconLeft(String iconLeftPath) {
        //Horizontally on the left side of title / vertically centered in title height
        iconLeft = new Texture(iconLeftPath);
        positionIconlLeft = new Vector2(positionTitle.x - iconLeft.getWidth() * 0.65f, positionTitle.y - iconLeft.getWidth() + iconLeft.getHeight() / 2);

    }

    public void setIconRight(String iconRightPath) {
        //Horizontally on the right side of title / vertically centered in title height
        iconRight = new Texture(iconRightPath);
        positionIconlRight = new Vector2(positionTitle.x + titleImage.getWidth() - iconRight.getWidth() * 0.35f, positionTitle.y - iconRight.getWidth() + iconRight.getHeight() / 2);

    }

    public void playSound(String soundPath) {
        sound = Gdx.audio.newSound(Gdx.files.internal(soundPath));
        sound.play();

    }


    public Texture getBackground() {
        return background;
    }

    public Texture getTitleImage() {
        return titleImage;
    }

    public Texture getIconLeft() {
        return iconLeft;
    }

    public Texture getIconRight() {
        return iconRight;
    }

    public Vector2 getPositionPlayBtn() {
        return positionPlayBtn;
    }

    public Vector2 getPositionTitle() {
        return positionTitle;
    }

    public Vector2 getPositionIconlLeft() {
        return positionIconlLeft;
    }

    public Vector2 getPositionIconlRight() {
        return positionIconlRight;
    }

    public void dispose() {

        if (background != null)
            background.dispose();

        if (playButton != null)
            playButton.dispose();

        if (titleImage != null)
            titleImage.dispose();

        if (iconLeft != null)
            iconLeft.dispose();

        if (iconRight != null)
            iconRight.dispose();

        if (sound != null)
            sound.dispose();
    }
}


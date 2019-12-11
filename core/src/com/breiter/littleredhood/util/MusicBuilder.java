package com.breiter.littleredhood.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicBuilder {

    private Music music;
    private float volume;
    private String path;

    public MusicBuilder() {
  }

    public static MusicBuilder create() {
        return new MusicBuilder();

    }


    public MusicBuilder play(String path, float volume) {
        this.path = path;
        this.volume = volume;
        music = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.setVolume(volume);
        music.setLooping(true);
        music.play();
        return this;
    }

    public void dispose() {
        music.dispose();
    }


}

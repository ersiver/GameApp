package com.breiter.littleredhood.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.breiter.littleredhood.GameLRH;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1500;
		config.height =800;
		config.title = GameLRH.TITLE;

		new LwjglApplication(new GameLRH(), config);
	}
}

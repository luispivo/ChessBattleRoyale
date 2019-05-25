package com.pddm.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.hiero.Hiero;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.pddm.game.ChessBattleRoyaleGame;

public class DesktopLauncher {
	public static void main (String[] arg) throws Exception {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.height = 768;
		config.width = 1360;
                //Hiero.main(arg);
                TexturePacker.process("../assets","../assets","ChessBattleRoyale_assets");
                new LwjglApplication(new ChessBattleRoyaleGame(), config);
	}
}

package com.pddm.game;

import Screens.PartidaScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ChessBattleRoyaleGame extends Game {

    private SpriteBatch batch;
    private PartidaScreen partida;
    private final AssetManager assetManager = new AssetManager();
    
    @Override
    public void create() {
        batch=new SpriteBatch();
            partida=new PartidaScreen(this);       
        setScreen(partida);       
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height); //To change body of generated methods, choose Tools | Templates.
        if (partida != null) partida.resize(width, height); 
    }

    @Override
    public void dispose() {
        super.dispose(); //To change body of generated methods, choose Tools | Templates.
        batch.dispose();
    }
    
    
    public SpriteBatch getSpriteBatch(){
        return batch;
    }
    public AssetManager getAssetManager() {
        return assetManager;
    }
}

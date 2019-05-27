package com.pddm.game;

import Screens.MenuScreen;
import Screens.PartidaScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ChessBattleRoyaleGame extends Game {

    private SpriteBatch batch;
    //private PartidaScreen partida;
    MenuScreen MenuPartida;
    private final AssetManager assetManager = new AssetManager();
    public FitViewport Viewport;
    
    @Override
    public void create() {
        batch=new SpriteBatch();
      //  partida=new PartidaScreen(this); 
        Viewport =new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());   
        MenuPartida=new MenuScreen(this);
        setScreen(MenuPartida); 
            
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height); //To change body of generated methods, choose Tools | Templates.
        //if (MenuPartida != null) MenuPartida.resize(width, height); 
    }

    @Override
    public void dispose() {
//        super.dispose(); //To change body of generated methods, choose Tools | Templates.
        batch.dispose();
    }
    
    
    public SpriteBatch getSpriteBatch(){
        return batch;
    }
    public AssetManager getAssetManager() {
        return assetManager;
    }
}

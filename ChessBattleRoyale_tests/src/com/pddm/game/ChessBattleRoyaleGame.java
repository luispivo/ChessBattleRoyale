package com.pddm.game;

import Screens.PartidaScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChessBattleRoyaleGame extends Game {

    private SpriteBatch batch;
    private PartidaScreen partida;
    
    @Override
    public void create() {
        batch=new SpriteBatch();
        partida=new PartidaScreen(this);
        setScreen(partida);
    }

    @Override
    public void dispose() {
        super.dispose(); //To change body of generated methods, choose Tools | Templates.
        batch.dispose();
    }
    
    
    public SpriteBatch getSpriteBatch(){
        return batch;
    }
}

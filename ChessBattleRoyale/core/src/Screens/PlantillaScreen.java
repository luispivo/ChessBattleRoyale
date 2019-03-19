/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pddm.game.ChessBattleRoyaleGame;

/**
 *
 * @author Luis
 */
public abstract class PlantillaScreen implements Screen {

    protected ChessBattleRoyaleGame game;
    protected SpriteBatch batch;
    public Sound moveSound;

    public PlantillaScreen(ChessBattleRoyaleGame game) {
        this.game = game;
        batch=game.getSpriteBatch();
    }
       
    @Override
    public void show() {
         moveSound = Gdx.audio.newSound(Gdx.files.internal("move.mp3"));
         moveSound.play();
    }
    
    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {
       
    }

    @Override
    public void resume() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hide() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

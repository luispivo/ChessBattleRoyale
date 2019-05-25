/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.pddm.game.ChessBattleRoyaleGame;

/**
 *
 * @author Luis
 */
public abstract class PlantillaScreen extends ScreenAdapter {

    protected ChessBattleRoyaleGame game;
    protected SpriteBatch batch;
    public Sound moveSound;

    
    public PlantillaScreen(ChessBattleRoyaleGame game) {
        this.game = game;
        batch = game.getSpriteBatch();
        game.getAssetManager().load("ChessBattleRoyale_assets.atlas", TextureAtlas.class);
        game.getAssetManager().finishLoading();
    }

    @Override
    public void show() {
        // moveSound = Gdx.audio.newSound(Gdx.files.internal("move.mp3"));
        //moveSound.play();

    }

    @Override
    public void pause() {
        //moveSound.pause();
    }

    @Override
    public void resume() {
        //moveSound.resume();
    }

    @Override
    public void hide() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height); //To change body of generated methods, choose Tools | Templates.
    }

}

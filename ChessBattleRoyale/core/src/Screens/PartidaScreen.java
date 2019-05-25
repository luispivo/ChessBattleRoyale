/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.pddm.game.ChessBattleRoyaleGame;
import LogicaAjedrezInicial.Board;
import LogicaAjedrezInicial.Casilla;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 *
 * @author Luis
 */
public class PartidaScreen extends PlantillaScreen{

    Board tablero;
    int NUMEROFILAS=14,NUMEROCOLUMNAS=14;
    Stage stage;
    
    public PartidaScreen(ChessBattleRoyaleGame game) {
        super(game);
        TextureAtlas atlas=game.getAssetManager().get("ChessBattleRoyale_assets.atlas");
        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);  
        tablero=new Board(NUMEROFILAS,NUMEROCOLUMNAS,atlas);
        tablero.TableroInicialPiezas14();
        //for (int i = 0; i < 3; i++) tablero.IncrementaAlertaTablero();       
        for (Casilla x: tablero.Tablero) stage.addActor(x);  
        stage.addActor(tablero);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void render(float f) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.pddm.game.ChessBattleRoyaleGame;
import LogicaAjedrezInicial.Board;
import LogicaAjedrezInicial.Casilla;
import LogicaAjedrezInicial.SillyIA;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
        tablero=new Board(NUMEROFILAS,NUMEROCOLUMNAS,atlas);
        tablero.TableroInicialPiezas14();
        stage=new Stage(new ScreenViewport());
        for (Casilla x: tablero.Tablero) stage.addActor(x);
        Gdx.input.setInputProcessor(stage);     
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.pddm.game.ChessBattleRoyaleGame;
import LogicaAjedrezInicial.Board;
import LogicaAjedrezInicial.Casilla;
import LogicaAjedrezInicial.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
        tablero=new Board(NUMEROFILAS,NUMEROCOLUMNAS);
        tablero.TableroInicialPiezas14();
        tablero.getCasilla(5,6).Ocupada=new King(LogicaAjedrezInicial.Color.BLACK);
        for (int i = 0; i < 4; i++) {
            tablero.IncrementaAlertaTablero();
            System.out.println(tablero);
            System.out.println(i);
        }
        stage=new Stage(new ScreenViewport());
        for (Casilla x: tablero.Tablero) stage.addActor(x);
       // stage.dispose();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
       
    }
    
}

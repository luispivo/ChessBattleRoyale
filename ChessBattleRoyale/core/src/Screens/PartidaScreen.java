/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.pddm.game.ChessBattleRoyaleGame;
import LogicaAjedrezInicial.Board;
import LogicaAjedrezInicial.Casilla;
import LogicaAjedrezInicial.Color;
import LogicaAjedrezInicial.Jugador;
import LogicaAjedrezInicial.TipoJugador;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.ArrayList;
import java.util.EnumSet;

/**
 *
 * @author Luis
 */
public class PartidaScreen extends PlantillaScreen{

    Board tablero;
    int NUMEROFILAS=14,NUMEROCOLUMNAS=14;
    Stage stage;
    ArrayList<Jugador> ListaJugadores;
    
    public PartidaScreen(ChessBattleRoyaleGame game, int minutos,String periodo) {
        super(game);
        TextureAtlas atlas=game.getAssetManager().get("ChessBattleRoyale_assets.atlas");
        ListaJugadores=new ArrayList();
        //esto habrá que retocarlo en el futuro porque los jugadores han de venir del menu de configuracion para 
        //para poner las ias o los remotos
        (EnumSet.allOf(Color.class)).forEach( x -> {
           ListaJugadores.add(new Jugador(minutos,TipoJugador.HUMANO,x));
        });
        stage=new Stage(game.Viewport);
        //stage=new Stage(new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);  
        int per=Integer.parseInt(periodo);
        tablero=new Board(NUMEROFILAS,NUMEROCOLUMNAS,atlas,ListaJugadores,per);
        tablero.TableroInicialPiezas14();        
        //for (int i = 0; i < 3; i++) tablero.IncrementaAlertaTablero();       
        for (Casilla x: tablero.Tablero) stage.addActor(x);  
        stage.addActor(tablero);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true); //To change body of generated methods, choose Tools | Templates.
        //stage.getViewport().project(Vector3.Zero);
    }


    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(222/255f,184/255f,135/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.draw();
    }

    @Override
    public void dispose() {
 //       super.dispose(); //To change body of generated methods, choose Tools | Templates.
        stage.dispose();
    }
    
}

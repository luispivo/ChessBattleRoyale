/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.pddm.game.ChessBattleRoyaleGame;
import LogicaAjedrezInicial.Board;

/**
 *
 * @author Luis
 */
public class PartidaScreen extends PlantillaScreen{

    Board tablero;
    int NUMEROFILAS=14,NUMEROCOLUMNAS=14;
    public PartidaScreen(ChessBattleRoyaleGame game) {
        super(game);
        tablero=new Board(NUMEROFILAS,NUMEROCOLUMNAS);
        tablero.TableroInicialPiezas14();
    }

    @Override
    public void render(float f) {
        batch.begin();        
        batch.end();        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import java.util.ArrayList;

class Queen extends Pieza{

    public Queen(Color color) {
        ColorJugador=color;
        ClasePieza=TipoPieza.QUEEN;
        MOVIMIENTOSMAXIMOS=51.;
        VALOR=10.;
    }
    public Queen(Pieza pieza){
        ColorJugador=pieza.ColorJugador;
        ClasePieza=pieza.ClasePieza;
        MOVIMIENTOSMAXIMOS=51.;
        VALOR=10.;
    }
    @Override
    ArrayList<Casilla> PossibleMoves(Casilla casilla, Board tablero) {
        //Las casillas a las que llega la reina son la suma de los movimientos de una torre y un alfil asi que...
        Bishop bishop=new Bishop(ColorJugador);
        Rook rook=new Rook(ColorJugador);
        ArrayList<Casilla> listaCasillas=new ArrayList<>();
        listaCasillas=bishop.PossibleMoves(casilla, tablero);
        listaCasillas.addAll(rook.PossibleMoves(casilla, tablero));
        return listaCasillas;        
    }

    @Override
    ArrayList<Casilla> PossibleCaptures(Casilla casilla, Board tablero) {
        return PossibleMoves(casilla,tablero);
    }

    @Override
    double FactorValor() {
        return VALOR;
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import java.util.ArrayList;

class Knight extends Pieza{

    public Knight(Color color) {
        ColorJugador=color;
        ClasePieza=TipoPieza.KNIGHT;
    }
    public Knight(Pieza pieza){
        ColorJugador=pieza.ColorJugador;
        ClasePieza=pieza.ClasePieza;
    }
    @Override
    ArrayList<Casilla> PossibleMoves(Casilla casilla, Board tablero) {
        ArrayList<Casilla> listaCasillas=new ArrayList<>();
        //Consigue la posición inicial de la pieza (la casilla)
        int fila=casilla.Fila;
        int columna=casilla.Columna;        
        //
        int aux;
        Casilla auxiliarCasilla;
        for (int i=-2;i<=2;i++){
            if (i!=0){
                aux=3-Math.abs(i);
                auxiliarCasilla=tablero.getCasilla(fila+i,columna+aux);
                if (auxiliarCasilla!=null && (auxiliarCasilla.Ocupada==null ||(auxiliarCasilla.Ocupada!=null && auxiliarCasilla.Ocupada.ColorJugador!=ColorJugador))) listaCasillas.add(auxiliarCasilla);
                auxiliarCasilla=tablero.getCasilla(fila+i,columna-aux);
                if (auxiliarCasilla!=null && (auxiliarCasilla.Ocupada==null ||(auxiliarCasilla.Ocupada!=null && auxiliarCasilla.Ocupada.ColorJugador!=ColorJugador))) listaCasillas.add(auxiliarCasilla);
            }
        }
        return listaCasillas;
    }    

    @Override
    ArrayList<Casilla> PossibleCaptures(Casilla casilla, Board tablero) {
        return PossibleMoves(casilla,tablero);
    }
}


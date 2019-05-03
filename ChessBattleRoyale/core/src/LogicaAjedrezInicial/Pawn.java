/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import java.util.ArrayList;

/**
 * El peón
 * @author Luis
 */
class Pawn extends Pieza{

    public Pawn(Color color) {
        ColorJugador=color;
        ClasePieza=TipoPieza.PAWN;
        MOVIMIENTOSMAXIMOS=8.;
        VALOR=1.; 
    }
    public Pawn(Pieza pieza){
        ColorJugador=pieza.ColorJugador;
        ClasePieza=pieza.ClasePieza;
        MOVIMIENTOSMAXIMOS=8.;
        VALOR=1.;
    }
    @Override
    ArrayList<Casilla> PossibleMoves(Casilla casilla, Board tablero) {
        ArrayList<Casilla> listaCasillas=new ArrayList<>();
        //Consigue la posición inicial de la pieza (la casilla)
        int fila=casilla.Fila;
        int columna=casilla.Columna;        
        Casilla auxiliarCasilla;
        for(int i=-1;i<=1;i++) for(int j=-1;j<=1;j++){
            auxiliarCasilla=tablero.getCasilla(fila+i, columna+j);
            //Movimientos sin captura (comprobar que no está ocupada la casilla (no salta)
            if (i==0||j==0 && i!=j){                
                if (auxiliarCasilla!=null && auxiliarCasilla.Ocupada==null) listaCasillas.add(auxiliarCasilla);
            }
            //Diagonales si hay piezas para capturar
            else  if (auxiliarCasilla!=null && auxiliarCasilla.Ocupada!=null && auxiliarCasilla.Ocupada.ColorJugador!=ColorJugador) listaCasillas.add(auxiliarCasilla);                           
        }
        /* La verdad es que queda más bonito ese bucle que esto de aqui abajo... que basicamente es lo mismo
        Casilla auxiliarCasilla=tablero.getCasilla(fila+1, columna);
        if (auxiliarCasilla!=null && auxiliarCasilla.Ocupada==null) listaCasillas.add(auxiliarCasilla);
        auxiliarCasilla=tablero.getCasilla(fila-1, columna);
        if (auxiliarCasilla!=null && auxiliarCasilla.Ocupada==null) listaCasillas.add(auxiliarCasilla);
        auxiliarCasilla=tablero.getCasilla(fila, columna+1);
        if (auxiliarCasilla!=null && auxiliarCasilla.Ocupada==null) listaCasillas.add(auxiliarCasilla);
        auxiliarCasilla=tablero.getCasilla(fila, columna-1);
        if (auxiliarCasilla!=null && auxiliarCasilla.Ocupada==null) listaCasillas.add(auxiliarCasilla);
        //Diagonales si hay piezas para capturar
        auxiliarCasilla=tablero.getCasilla(fila+1, columna-1);
        if (auxiliarCasilla!=null && auxiliarCasilla.Ocupada!=null && auxiliarCasilla.Ocupada.ColorJugador!=ColorJugador) listaCasillas.add(auxiliarCasilla);
        auxiliarCasilla=tablero.getCasilla(fila-1, columna+1);
        if (auxiliarCasilla!=null && auxiliarCasilla.Ocupada!=null && auxiliarCasilla.Ocupada.ColorJugador!=ColorJugador) listaCasillas.add(auxiliarCasilla);
        auxiliarCasilla=tablero.getCasilla(fila+1, columna+1);
        if (auxiliarCasilla!=null && auxiliarCasilla.Ocupada!=null && auxiliarCasilla.Ocupada.ColorJugador!=ColorJugador) listaCasillas.add(auxiliarCasilla);
        auxiliarCasilla=tablero.getCasilla(fila-1, columna-1);
        if (auxiliarCasilla!=null && auxiliarCasilla.Ocupada!=null && auxiliarCasilla.Ocupada.ColorJugador!=ColorJugador) listaCasillas.add(auxiliarCasilla);*/
        return listaCasillas;
    }

    @Override
    ArrayList<Casilla> PossibleCaptures(Casilla casilla, Board tablero) {
        ArrayList<Casilla> listaCasillas=new ArrayList<>();
        //Consigue la posición inicial de la pieza (la casilla)
        int fila=casilla.Fila;
        int columna=casilla.Columna;        
        Casilla auxiliarCasilla;
        for(int i=-1;i<=1;i+=2) for(int j=-1;j<=1;j+=2){
            auxiliarCasilla=tablero.getCasilla(fila+i, columna+j);            
            //Diagonales si hay piezas para capturar. Incluyo ahora si las vacías porque es por si alguno se pone ahi...
            //if ((auxiliarCasilla!=null && auxiliarCasilla.Ocupada==null)|| (auxiliarCasilla!=null && auxiliarCasilla.Ocupada!=null && auxiliarCasilla.Ocupada.ColorJugador!=ColorJugador)) listaCasillas.add(auxiliarCasilla);                           
            //Dejando de tonterias... Como es para lo del rey con ponerlas todas...valdria
            if (auxiliarCasilla!=null) listaCasillas.add(auxiliarCasilla);
        }
        return listaCasillas;
    }

    @Override
    double FactorValor() {
        return VALOR;
    }
    
}
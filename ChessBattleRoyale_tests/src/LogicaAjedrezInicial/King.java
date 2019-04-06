/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import java.util.ArrayList;

/**
 * El rey
 * @author Luis
 * @version 1.0 (Le falta lo de no "suicidarse"
 */
class King extends Pieza{
    public King(Color color) {
        ColorJugador=color;
        ClasePieza=TipoPieza.KING;
    }
    public King(Pieza pieza){
        ColorJugador=pieza.ColorJugador;
        ClasePieza=pieza.ClasePieza;
    }
    @Override
    ArrayList<Casilla> PossibleMoves(Casilla casilla, Board tablero) {
        ArrayList<Casilla> listaCasillas=new ArrayList<>();
        //Consigue la posición inicial de la pieza (la casilla)
        int fila=casilla.Fila;
        int columna=casilla.Columna;        
        Casilla auxiliarCasilla;
        for(int i=-1;i<=1;i++) for(int j=-1;j<=1;j++){           
            //Movimientos sin captura (comprobar que no está ocupada por ficha propia la casilla (no salta)
            if (!(i==0&&j==0)){   
                auxiliarCasilla=tablero.getCasilla(fila+i, columna+j);
                if ((auxiliarCasilla!=null && 
                        (auxiliarCasilla.Ocupada==null ||(auxiliarCasilla.Ocupada!=null && auxiliarCasilla.Ocupada.ColorJugador!=ColorJugador)))
                        && !tablero.CasillaAmenazadaPorOtroJugador(auxiliarCasilla, ColorJugador)) listaCasillas.add(auxiliarCasilla);
                // Falta la parte que no este amenazada por una pieza... 
                // Ahora mismo no se me ocurre salvo bucle por el tablero de todas las casillas y de todas las piezas e ir
                // comprobando que no está la casilla en la lista de posibles moves... Pero suena un poco largo de implementar
                // así que lo dejaré un poco para despues además en blitz es posible comerse los reyes asi que no sé que será 
                // mejor
                //Creo que ya lo he hecho en la clase tablero y añado...              
            }                       
        }
        return listaCasillas;
    }

    @Override
    ArrayList<Casilla> PossibleCaptures(Casilla casilla, Board tablero) {
        return PossibleMoves(casilla,tablero);
    }
}
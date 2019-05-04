/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import java.util.ArrayList;

class Rook extends Pieza{

    public Rook(Color color) {
        ColorJugador=color;
        ClasePieza=TipoPieza.ROOK; 
    }
    public Rook(Pieza pieza){
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
       
        Casilla esquinaInferiorIzquierda=tablero.Tablero.get(0);
        Casilla esquinaSuperiorDerecha=tablero.Tablero.get(tablero.Tablero.size()-1);
        //Aqui usar todas tiene su logica al ser movimiento recto
        int filaMinima=esquinaInferiorIzquierda.Fila;
        int filaMaxima=esquinaSuperiorDerecha.Fila;
        int columnaMinima=esquinaInferiorIzquierda.Columna;
        int columnaMaxima=esquinaSuperiorDerecha.Columna;

        //HACIA ARRIBA
        for (int i=1;i<=(filaMaxima-fila);i++){           
            auxiliarCasilla=tablero.getCasilla(fila+i,columna);
            if (CasillaNoDisponible(auxiliarCasilla, listaCasillas)) break;            
        }
        //HACIA DERECHA
        for (int i=1;i<=(columnaMaxima-columna);i++){           
            auxiliarCasilla=tablero.getCasilla(fila,columna+i);
            if (CasillaNoDisponible(auxiliarCasilla, listaCasillas)) break;            
        }
        //HACIA ABAJO
        for (int i=1;i<=(fila-filaMinima);i++){           
            auxiliarCasilla=tablero.getCasilla(fila-i,columna);
            if (CasillaNoDisponible(auxiliarCasilla, listaCasillas)) break;            
        }
        //HACIA IZQUIERDA
        for (int i=1;i<=(columna-columnaMinima);i++){           
            auxiliarCasilla=tablero.getCasilla(fila,columna-i);
            if (CasillaNoDisponible(auxiliarCasilla, listaCasillas)) break;            
        }
        return listaCasillas;
    }        

    @Override
    ArrayList<Casilla> PossibleCaptures(Casilla casilla, Board tablero) {
        return PossibleMoves(casilla,tablero);
    }
}

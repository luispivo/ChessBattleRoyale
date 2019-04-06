/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import java.util.ArrayList;

class Bishop extends Pieza {

    public Bishop(Color color) {
        ColorJugador=color;
        ClasePieza=TipoPieza.BISHOP; 
    }
    public Bishop(Pieza pieza){
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
        Casilla auxiliarCasilla;
        /* Creo que hay 2 formas de hacerlo más o menos aceptable porque puedo 
           conseguir las esquinas de los tableros (para saber hasta donde hacer los bucles)
           y usarlo para que no se vayan de madre. P.e. Así conseguiría las esquinas y las filas máxima
           y mínima */
        
        Casilla esquinaInferiorIzquierda=tablero.Tablero.get(0);
        Casilla esquinaSuperiorDerecha=tablero.Tablero.get(tablero.Tablero.size()-1);
        //con esas puedo sacar la fila y columna máxima y mínima (En realidad solo necesito las filas
        //o las columnas pero dejo comentado la otra parte por si acaso
        int filaMinima=esquinaInferiorIzquierda.Fila;
        int filaMaxima=esquinaSuperiorDerecha.Fila;
        int columnaMinima=esquinaInferiorIzquierda.Columna;
        int columnaMaxima=esquinaSuperiorDerecha.Columna;
        
        /* Pero creo que es mejor usar que cuando encuentre una nula ya no siga (de la misma manera 
        cuando encuentren otra pieza tampoco deberian seguir porque estas piezas no saltan) 
        Como límite máximo podemos de todas maneras que seria la filaMaxima-filaMinima o columnaMaxima-columnaMinima
        Usaremos todo en los bucles de las direcciones para limitar lo máximo posible los bucles.
        
        NO HAY QUE ceder a la tentación de hacerlo con 2 bucles traslandando la pieza a uno de los costados
        porque es posible que haya piezas en medio y asi encontraramos mal las casillas
        */                           
        
        //diagonal hacia arriba y derecha
        for (int i=1;i<=(columnaMaxima-columna);i++){           
            auxiliarCasilla=tablero.getCasilla(fila+i,columna+i);
            if (CasillaNoDisponible(auxiliarCasilla, listaCasillas)) break;            
        }
        //diagonal hacia abajo y derecha
        for (int i=1;i<=(columnaMaxima-columna);i++){           
            auxiliarCasilla=tablero.getCasilla(fila-i,columna+i);
            if (CasillaNoDisponible(auxiliarCasilla, listaCasillas)) break;            
        }
        //diagonal hacia abajo e izquierda
        for (int i=1;i<=(columna-columnaMinima);i++){           
            auxiliarCasilla=tablero.getCasilla(fila-i,columna-i);
            if (CasillaNoDisponible(auxiliarCasilla, listaCasillas)) break;            
        }
        //diagonal hacia arriba e izquierda
        for (int i=1;i<=(columna-columnaMinima);i++){           
            auxiliarCasilla=tablero.getCasilla(fila+i,columna-i);
            if (CasillaNoDisponible(auxiliarCasilla, listaCasillas)) break;            
        }
        return listaCasillas;
    }    

    @Override
    ArrayList<Casilla> PossibleCaptures(Casilla casilla, Board tablero) {
        return PossibleMoves(casilla,tablero);
    }
}

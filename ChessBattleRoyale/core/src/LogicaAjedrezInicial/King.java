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
public class King extends Pieza{
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
                //if ((auxiliarCasilla!=null && 
                //        (auxiliarCasilla.Ocupada==null ||(auxiliarCasilla.Ocupada!=null && auxiliarCasilla.Ocupada.ColorJugador!=ColorJugador)))
                //        && !tablero.CasillaAmenazadaPorOtroJugador(auxiliarCasilla, ColorJugador)) listaCasillas.add(auxiliarCasilla);                
                
                if (auxiliarCasilla!=null){
                    if(auxiliarCasilla.Ocupada==null ||(auxiliarCasilla.Ocupada!=null && auxiliarCasilla.Ocupada.ColorJugador!=ColorJugador)){
                        if(!tablero.CasillaAmenazadaPorOtroJugador(auxiliarCasilla, ColorJugador))listaCasillas.add(auxiliarCasilla);
                    }
                }
                // Ahora mismo no se me ocurre salvo bucle por el tablero de todas las casillas y de todas las piezas e ir
                // comprobando que no está la casilla en la lista de posibles moves... 
                // Creo que ya lo he hecho en la clase tablero y anyado... 
                // Pero tengo que revisar esta implementacion... primero porque en blitz es posible comerse los reyes asi que tendria
                // que quitar pero tambien porque al usar la IA me esta dando problemas de stack overflow...                           
            }                       
        }
        return listaCasillas;
    }

    @Override
    ArrayList<Casilla> PossibleCaptures(Casilla casilla, Board tablero) {
        //SIENTO LA REPETICION CASI DE CODIGO PERO LA UNICA DIFERENCIA ES QUE NO HAY QUE PONER EL CHEQUEO DE SI SE PUEDE MOVER
        //SI NO LO HAGO ASI ESA LLAMADA SE LLAMA RECURSIVAMENTE CUANDO CHEQUEAMOS Y ES IMPOSIBLE USAR FUNCIONES DE IA 
        //QUE TENGAN EN CUENTA LOS MOVIMIENTOS DE LOS REYES
        ArrayList<Casilla> listaCasillas=new ArrayList<>();
        //Consigue la posición inicial de la pieza (la casilla)
        int fila=casilla.Fila;
        int columna=casilla.Columna;        
        Casilla auxiliarCasilla;
        for(int i=-1;i<=1;i++) for(int j=-1;j<=1;j++){           
            //Movimientos sin captura (comprobar que no está ocupada por ficha propia la casilla (no salta)
            if (!(i==0&&j==0)){   
                auxiliarCasilla=tablero.getCasilla(fila+i, columna+j);
                //if ((auxiliarCasilla!=null && 
                //        (auxiliarCasilla.Ocupada==null ||(auxiliarCasilla.Ocupada!=null && auxiliarCasilla.Ocupada.ColorJugador!=ColorJugador)))
                //        && !tablero.CasillaAmenazadaPorOtroJugador(auxiliarCasilla, ColorJugador)) listaCasillas.add(auxiliarCasilla);                
                
                if (auxiliarCasilla!=null){
                    if(auxiliarCasilla.Ocupada==null ||(auxiliarCasilla.Ocupada!=null && auxiliarCasilla.Ocupada.ColorJugador!=ColorJugador)){
                        listaCasillas.add(auxiliarCasilla);
                    }
                }
          
                // Pero tengo que revisar esta implementacion... primero porque en blitz es posible comerse los reyes asi que tendria
                // que quitar pero tambien porque al usar la IA me esta dando problemas de stack overflow... 
                // quitandolo no
            }                       
        }
        return listaCasillas;
    }
}
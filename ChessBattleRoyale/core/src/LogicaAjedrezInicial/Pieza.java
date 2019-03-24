/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import java.util.ArrayList;

/**
 * Tipos de pieza
 * @author Luis
 */
enum TipoPieza{
    PAWN,KNIGHT,BISHOP,ROOK,QUEEN,KING
}
/**
 * Colores de los 4 jugadores del juego
 * @author Luis
 */
enum Color{
    BLACK,BLUE,PURPLE,GREEN
}
/**
 * Clase abstracta que contiene el tipo y el color de la pieza y de momento una lista de sus posibles 
 * movimientos dado un tablero y una casilla de inicio (Estoy pensando que para que hacerla abstracta
 * @author Luis
 */
abstract class Pieza {
    TipoPieza ClasePieza;
    Color ColorJugador;
    
    /**
     * Función para devolver todas las casillas a las que se puede mover la pieza situada en
     * @param casilla casilla donde esta la pieza situada
     * @param tablero tablero sobre el que nos estamos volviendo
     * @return todas las casillas a las que se puede mover en una lista
     */
    abstract ArrayList<Casilla> PossibleMoves(Casilla casilla, Board tablero);
    /**
     * Peones, peones, peones... Son los únicos que capturan distinto que mueven y para lo del rey 
     * necesito solamente las casillas amenazadas por los peones asi que me toca "duplicar" codigo
     * @param casilla
     * @param tablero
     * @return 
     */
    abstract ArrayList<Casilla> PossibleCaptures(Casilla casilla, Board tablero);
    /**
     * La siguiente función la necesitaré en todas las piezas mayores asi que la he pasado aqui.
     * Básicamente rompe añade a la lista de casillas la casilla si la pieza puede moverse ahí
     * y rompe el bucle de donde parta para que no siga buscando más adelante.
     * No la vi necesaria al empezar por piezas que solo mueven una casilla (o un conjunto limitado)
     * y tal vez deba mejorarlo para cambiar el movimiento del peón, rey y caballo con esta función
     * pero bueno es que en esas los "bucles" son de uno así que no necesito la devolución del booleano
     * para romperlos y era más corto la forma de hacerlo. Aunque por coherencia...
     * @param auxiliarCasilla casilla a comprobar si puede ser ocupada por la pieza
     * @param listaCasillas lista de casillas para añadirla si puede ser ocupada
     * @return 
     */
    boolean CasillaNoDisponible(Casilla auxiliarCasilla, ArrayList<Casilla> listaCasillas) {
        if (auxiliarCasilla==null) {
            return true;
        } else if (auxiliarCasilla.Ocupada==null) {
            listaCasillas.add(auxiliarCasilla);
        } else if (auxiliarCasilla.Ocupada.ColorJugador!=ColorJugador) {
            listaCasillas.add(auxiliarCasilla);
            return true;
        } else {
            return true;
        }
        return false;
    }
}
/**
 * El peón
 * @author Luis
 */
class Pawn extends Pieza{

    public Pawn(Color color) {
        ColorJugador=color;
        ClasePieza=TipoPieza.PAWN;    
    }
    public Pawn(Pieza pieza){
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
    
}
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
class Queen extends Pieza{

    public Queen(Color color) {
        ColorJugador=color;
        ClasePieza=TipoPieza.QUEEN;
    }
    public Queen(Pieza pieza){
        ColorJugador=pieza.ColorJugador;
        ClasePieza=pieza.ClasePieza;
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
    
}
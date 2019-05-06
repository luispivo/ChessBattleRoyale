/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import java.util.ArrayList;

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
        if (auxiliarCasilla==null) return true;
        else if (auxiliarCasilla.Ocupada==null) listaCasillas.add(auxiliarCasilla);
        else if (auxiliarCasilla.Ocupada.ColorJugador!=ColorJugador) {
            listaCasillas.add(auxiliarCasilla);
            return true;
        } else {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        //Caballo en ingles misma inicial que rey es un no no (Pensar en la internacionalización futura) que tendra que 
        //quitar esto por las iniciales
        String clasePieza,colorJugador;
        clasePieza=(ClasePieza==TipoPieza.KNIGHT?"N":ClasePieza.toString());
        colorJugador=(ColorJugador==Color.BLACK?"W":ColorJugador.toString());
        return String.format("%.1s  %.1s", clasePieza,colorJugador); //To change body of generated methods, choose Tools | Templates.
    }
    
}


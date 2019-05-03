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
    //Hay dos formas de tratar esta cuesti�n:
    //La primera y m�s pragm�tica simplemente calcular cuantos movimientos tiene un alfil en un tablero fijo
    //y parametrizarlo o "hardcodearlo". Quiz�s m�s sencilla y mejor
    //Otra forma es crear tablero vac�o para calcular el movimiento de un alfil en ese tablero
    //para que entre en la formula movimientos_posibles/movimientos_ideal
    //He preferido simplemente parametrizarlo pero dejo la nota para el futuro (y comprobar cuanto m�s de 
    //potencia de calculo/memoria necesitaria.
    //Por ahora simplemente pongo estos atributos aqui para luego que vaya a cada uno de ellos
    double MOVIMIENTOSMAXIMOS;
    double VALOR;
    
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

    double FactorMovimiento(Casilla casilla,Board tablero){
        return (double)PossibleMoves(casilla, tablero).size()/MOVIMIENTOSMAXIMOS;
    };
    
    abstract double FactorValor();
    @Override
    public String toString() {
        //Caballo en ingles misma inicial que rey es un no no (Pensar en la internacionalización futura) que tendra que 
        //quitar esto por las iniciales
        String clasePieza;
        clasePieza=(ClasePieza==TipoPieza.KNIGHT?"N":ClasePieza.toString());
        return String.format("%.1s  %.1s", clasePieza,ColorJugador); //To change body of generated methods, choose Tools | Templates.
    }
    
}


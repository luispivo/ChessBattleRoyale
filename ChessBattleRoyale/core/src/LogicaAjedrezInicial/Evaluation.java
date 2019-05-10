/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

/**
 * Implementaremos las evaluaciones que queramos usar para los motores IA con esta interfaz.
 * En cualquier caso esto es un trabajo un tanto preliminar sujeto a cambios.
 * @author Luis
 */

public interface Evaluation {
    /**
     * Función que pesa a las distintas piezas según la casilla que ocupen y el tablero para que piezas con mayor movilidad
     * valgan más que las que no disponen de movimientos
     * @param casilla a evaluar donde está la pieza
     * @param tablero
     * @return Un valor double que en principio será de 0 a 1 según la pieza sea completamente movible o no disponga de movimientos
     */
    double FactorMovimiento(Casilla casilla,Board tablero);  
    /**
    * Función que pesará el valor de las piezas. En las primeras implementaciones será un simple parametro que devuelva el
    * valor de una pieza pero para futuro se parametrizara para dar mayor flexibilidad a las evaluaciones de la IA
    * @param casilla
    * @return Valor doble que pesa la pieza
    */
    double FactorValor(TipoPieza pieza);
    
    /**
     * Funcion que pesara la distancia al borde del tablero para tener en cuenta que cuanto m�s lejos menos peligro
     * de ser consumida cuando el tablero disminuya de tamanyo
     * @param casilla
     * @param tablero
     * @return Valor doble que sopesa la distancia de la casilla al borde del tablero
     */
    double FactorDistancia(Casilla casilla,Board tablero);
    
    /**
     * Funcion que da un valor numerico a un tablero cualquiera para luego ir escogiendo el que sea correspondiente
     * para la eleccion de jugadas
     * @param tablero
     * @return 
     */
    double Evaluacion(Board tablero);
     
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * Mi primer intento de ir construyendo una clase que se pueda usar para jugar automaticamente
 * @author Luis
 */
class SillyIA implements Evaluation{

    //Hay dos formas de tratar esta cuesti�n:
    //La primera y m�s pragm�tica simplemente calcular cuantos movimientos tiene una pieza dada en un tablero fijo
    //y parametrizarlo o "hardcodearlo". Quiz�s m�s sencilla y mejor
    //Otra forma es crear tablero vac�o para calcular el movimiento de dicha pieza en ese tablero
    //para que entre en la formula movimientos_posibles/movimientos_ideal
    //He preferido simplemente parametrizarlo pero dejo la nota para el futuro (y comprobar cuanto m�s de 
    //potencia de calculo/memoria necesitaria.
    //Por ahora simplemente pongo estos atributos aqui para luego que vaya a cada uno de ellos
    
    ArrayList<ParametrosSillyIAPieza> Parametros;
    Color EvaluaColor;
    
    //Para la parametrización futura (y que se definan más piezas)
    //defino aqui los valores de los parámetros en vez de en la clase ParametrosSillyIAPieza o de crearlas cuando los cree
    //No tenía claro donde colocarlo realmente pero así puedo definir distintas IAsmodificandolos
    public SillyIA(Color color){
        EvaluaColor=color;
        Parametros=new ArrayList();
        
        (EnumSet.allOf(TipoPieza.class)).forEach( x -> {
            switch(x){
                case PAWN:
                    Parametros.add(new ParametrosSillyIAPieza(8.0,1.0,x));
                    break;
                case KNIGHT:
                    Parametros.add(new ParametrosSillyIAPieza(8.0,3.0,x));
                    break;
                case BISHOP:
                    Parametros.add(new ParametrosSillyIAPieza(25.0,3.5,x));
                    break;
                case ROOK:
                    Parametros.add(new ParametrosSillyIAPieza(26.0,5.0,x));
                    break;
                case QUEEN:
                    Parametros.add(new ParametrosSillyIAPieza(51.0,10.0,x));
                    break;
                case KING:
                    //Por poner algo de varias ordenes de magnitud probe 100 pero 
                    //aunque la perdida del rey hace perder la partida...
                    //Este factor tiene que ser m�s de la fuerza ofensiva
                    //y dejar si pierde el rey en los calculos como infinito
                    //que si no movera el rey para fomentar su movilidad
                    Parametros.add(new ParametrosSillyIAPieza(8.0,2.0,x)); 
                    break;                                    
                default:
                    throw new AssertionError(x.name());
            }
        });
    }
    
    /**
     * Factor movimiento. Para esto cálculo me aprovecho que he definido el equals de ParametrosSillyIAPieza para comparar
     * por tipo de pieza
     * @param casilla
     * @param tablero
     * @return 0 si falla un doble con el peso si está ok
     */
    @Override
    public double FactorMovimiento(Casilla casilla, Board tablero) {
        ParametrosSillyIAPieza aux=new ParametrosSillyIAPieza(0, 0, casilla.Ocupada.ClasePieza);
        for (ParametrosSillyIAPieza x: Parametros) if (x.equals(aux)) return (double)casilla.Ocupada.PossibleMoves(casilla, tablero).size()/x.MOVIMIENTOSMAXIMOS;
        return 0;
    }
    @Override
    public double FactorValor(TipoPieza pieza) {
        ParametrosSillyIAPieza aux=new ParametrosSillyIAPieza(0, 0, pieza);
        for (ParametrosSillyIAPieza x: Parametros) if (x.equals(aux)) return x.VALOR;
        return 0;
    }
     /**
     * Una función que usaré para la IA que intenta sopesar la relativa importancia de la situación de la pieza en el tablero.
     * Está elegida tras distintos ajustes y pruebas matemáticas. No creo que sea perfecta pero si creo que para empezar puede ser
     * un buen intento. Intenta forzar a las piezas a ir hacia el centro para que no se caigan (por así decirlo).
     * Ver la documentación del proyecto para más información. En cualquier caso estas funciones pueden ser sustituidas 
     * y/o parametrizadas para alcanzar el valor que haga jugar a la IA mejor.
     * @param casilla a la que se está haciendo el peso variable por su situacion en el tablero
     * @param tablero al que se refiere el calculo de la distancia
     * @return un valor double más o menos entre 0 y 1.x como mucho que indica un factor multiplicativo para sopesar el peso de una
     * pieza
     */
    @Override
    public double FactorDistancia(Casilla casilla,Board tablero){
    
         //Puede ser otro parametro y definirlo buscandolo pero de momento lo dejo como constante que más o menos he visto
        //que salía unos resultados que me parecían coherentes
        final int PARAMETRO=8;
        if (tablero.Rows!=2){
             //Relacionado con la distancia máxima del tablero
            double parametroRows=(double) tablero.Rows/2.-1.;        
            //Relaciona con la distancia real (considerando las filas en no peligro)
            double parametroDistancia= (double) tablero.DistanciaFinalTablero(casilla)/parametroRows;
            return Math.sqrt(parametroDistancia)+(1-parametroDistancia)/(PARAMETRO-parametroRows);
        }
        else return 1;
    }
    @Override
    public double Evaluacion(Board tablero) {
        //La ecuacion usada es 
        //(Sum_piezasJug (FactorValor*FactorMovimiento)-Sum_piezasEne (FactorValor*FactorMovimiento/NumeroEnemigos)/Sum_tablero (FactorValor*FactorMovimiento)
        //Multiplicado por un factor de que es un cambio unitario en este sistema (un peon)

        double valorJugador=0;
        double valorEnemigos=0;
        double denominador,numerador;
        for(Casilla x:tablero.Tablero){
            //if (x.Ocupada!=null && x.Ocupada.ClasePieza!=TipoPieza.KING){ //No entiendo porque tengo que quitar al rey...
            if (x.Ocupada!=null){ 
                if ( x.Ocupada.ColorJugador==EvaluaColor) valorJugador+=FactorDistancia(x,tablero)*FactorValor(x.Ocupada.ClasePieza)*FactorMovimiento(x,tablero);
                else if (x.Ocupada.ColorJugador!=EvaluaColor) valorEnemigos+=FactorDistancia(x,tablero)*FactorValor(x.Ocupada.ClasePieza)*FactorMovimiento(x,tablero);
            }
            
        }
        denominador=valorJugador+valorEnemigos;
        numerador=valorJugador-valorEnemigos/(double) (tablero.JugadoresActivos.size()-1);     
         
        //OUTPUTS DE PRUEBAS
        //System.out.println("TURNO"+tablero.TurnoJugador+"J: "+valorJugador+" E: "+valorEnemigos);
        //System.out.println("D: "+denominador);
        //System.out.println("N: "+numerador);
        //return (denominador-1)*numerador/((numerador-1)*denominador);
        return numerador/denominador;
    }   
}

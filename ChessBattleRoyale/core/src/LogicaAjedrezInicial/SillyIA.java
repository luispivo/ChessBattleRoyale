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
public class SillyIA implements Evaluation{

    //Hay dos formas de tratar esta cuesti�n:
    //La primera y m�s pragm�tica simplemente calcular cuantos movimientos tiene una pieza dada en un tablero fijo
    //y parametrizarlo o "hardcodearlo". Quiz�s m�s sencilla y mejor
    //Otra forma es crear tablero vac�o para calcular el movimiento de dicha pieza en ese tablero
    //para que entre en la formula movimientos_posibles/movimientos_ideal
    //He preferido simplemente parametrizarlo pero dejo la nota para el futuro (y comprobar cuanto m�s de 
    //potencia de calculo/memoria necesitaria.
    //Por ahora simplemente pongo estos atributos aqui para luego que vaya a cada uno de ellos
    
    ArrayList<ParametrosSillyIAPieza> Parametros;
    
    //Para la parametrización futura (y que se definan más piezas)
    //defino aqui los valores de los parámetros en vez de en la clase ParametrosSillyIAPieza
    //No tenía claro donde colocarlo realmente pero así puedo definir distintas IAsmodificandolos
    public SillyIA(){
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
                    Parametros.add(new ParametrosSillyIAPieza(8.0,1000000.0,x)); //Por poner algo de varias ordenes de magnitud
                                                                                 //La perdida del rey hace perder la partida...
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
    public double FactorValor(Casilla casilla) {
        ParametrosSillyIAPieza aux=new ParametrosSillyIAPieza(0, 0, casilla.Ocupada.ClasePieza);
        for (ParametrosSillyIAPieza x: Parametros) if (x.equals(aux)) return x.VALOR;
        return 0;
    }
    
}

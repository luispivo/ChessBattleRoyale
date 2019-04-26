/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * Para hacer las pruebas en ASCII posteriormente quitaré pero de momento pues aqui las dejo
 * @author Luis
 */
public class ChessRoyale {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Board tablero = new Board(14, 14);
       // System.out.println(tablero);
       // System.out.println("hola");

        // Prueba para ver como iba decreciendo el tablero... Se puede hasta i=8 con el tablero de 14 (el 9 haria
        // tablero nulo
        /* for (int i = 0; i < 3; i++) {
            tablero.IncrementaAlertaTablero();
            System.out.println(tablero);
            System.out.println(i);
        }
        System.out.println("\033[33;35m pepe");
        */
        //Prueba de unas cosillas de las enumeraciones para ver como hago para que vaya avanzando con ella por los turnos      
        Color color=Color.BLUE;
        System.out.println(color.ordinal());
        color=color.values()[color.ordinal()+1]; //Hace la siguiente del BLUE... El purple
        System.out.println(color.name());
        //Probando los enumset
        EnumSet<Color> colorines;
        colorines=EnumSet.allOf(Color.class);
        ArrayList<Color> colores=new ArrayList();
        
        colorines.remove(Color.BLUE);
        colorines.forEach( x -> colores.add(x) );
        System.out.println(colorines);
        System.out.println(colores);
        //colores.removeAll(colores);
        
        int contador=-1;
        for(int i=0;i<10;i++) {            
            if(contador>=colores.size()-1) contador=0;
            else contador++;
            System.out.println(colores.get(contador));
        }
        System.out.println("hola");
        for(Color x:colores) System.out.println(x);
        for(Color x:colorines) System.out.println(x);
        
        //Parece que con los EnumSet me puedo apañar
             
        //System.out.println(tablero);
        //Pruebas de movimientos...
        /* Pieza peoncito=new King(Color.BLACK);
        Casilla casillita=tablero.getCasilla(4,5);
        Pawn otroPeoncito=new Pawn(Color.GREEN);
        casillita.setPiezaCasilla(peoncito);
        Casilla masCasillita=tablero.getCasilla(2,5);
        masCasillita.setPiezaCasilla(otroPeoncito);
        masCasillita=tablero.getCasilla(3,3);
        masCasillita.setPiezaCasilla(peoncito);
        //masCasillita=tablero.getCasilla(3,6);
        //masCasillita.setPiezaCasilla(peoncito);
        
        System.out.println(peoncito.PossibleMoves(casillita, tablero));*/
        /*tablero.IncrementaAlertaTablero(1);
        System.out.println(tablero);
        System.out.println("hola");
        tablero.IncrementaAlertaTablero(1);
        tablero.IncrementaAlertaTablero(2);
        System.out.println(tablero);
        System.out.println("hola");
        tablero.IncrementaAlertaTablero(1);

        tablero.IncrementaAlertaTablero(2);
        tablero.IncrementaAlertaTablero(3);
        System.out.println(tablero);

        System.out.println("PEPE");
        tablero.IncrementaAlertaTablero(1);
        System.out.println(tablero);
       System.out.println("PEPE2");
        tablero.IncrementaAlertaTablero(2);
        System.out.println(tablero);
               System.out.println("PEPE2");
        tablero.IncrementaAlertaTablero(2);
        System.out.println(tablero);
        //System.out.println(tablero.TCasillas[1][1]);
        //tablero.Tablero.get(8).IncreaseDangerStatus(tablero);
        //System.out.println(tablero.Tablero.get(8).Status);
        //tablero.Tablero.get(8).IncreaseDangerStatus(tablero);
        //System.out.println(tablero.Tablero.get(8).Status);
        //tablero.Tablero.get(16).IncreaseDangerStatus(tablero);
        //System.out.println(tablero.Tablero.get(16).Status);
        //tablero.Tablero.get(25).IncreaseDangerStatus(tablero);
        //System.out.println(tablero.Tablero.get(25).Status);
        */
        
        // PRUEBAS DE COMO VA LOS MOVIMIENTOS
        System.out.println("IMPRIMO TABLERO SIN NADA");
        System.out.println(tablero);
        
        tablero.TableroInicialPiezas14();
        System.out.println("IMPRIMO TABLERO INICIAL");
        System.out.println(tablero);
        
        /*for (int i = 0; i < 3; i++) {
            System.out.println(i);
            tablero.IncrementaAlertaTablero();
            System.out.println(tablero);        
        }*/
        Casilla casillaAuxInicio, casillaAuxFinal;
        casillaAuxInicio=tablero.getCasilla(1, 5);
        System.out.println(casillaAuxInicio);
        casillaAuxFinal=tablero.getCasilla(2,5);
        if (casillaAuxInicio.Ocupada !=null&&casillaAuxInicio.Ocupada.PossibleMoves(casillaAuxInicio, tablero).contains(casillaAuxFinal))
        {
            tablero=tablero.Movimiento(casillaAuxInicio, casillaAuxFinal);
        }
        System.out.println(tablero);
    }

}

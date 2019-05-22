/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Para hacer las pruebas en ASCII posteriormente quitaré pero de momento pues aqui las dejo
 * @author Luis
 */
public class ChessRoyale {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //pruebaDecrecerTablero()
        //pruebaColorines();
        //pruebaValoraciones();
        //pruebaMovimientos();
        //pruebaMovimientosII();
        //pruebaDistancias();
        //pruebaCasillasRey();
        Board tablero = NuevoTablero();
        tablero=pruebaHorizonteTableros(50, tablero);
        System.out.println("**********************************************");
        System.out.println("**********************************************");
        
        //pruebaHorizonteTableros(4, tablero);
    }
    private static void pruebaDistancias(){      
        Board tablero = new Board(14, 14,null);
        System.out.println("------->VAMOS A VER DISTANCIAS***********************");
        Casilla casilla=tablero.getCasilla(7, 7);
        casilla.Ocupada=new King(Color.GREEN);
        
        try {
        FileWriter fichero=new FileWriter("salida.txt");
        
        System.out.println(tablero.DistanciaFinalTablero(casilla));
        for (int i = 0; i < 9; i++) {
            System.out.println(i);
            tablero.IncrementaAlertaTablero();
            System.out.println(tablero);
            fichero.write("\n\n"+tablero.toString());
            System.out.println("DISTANCIA: "+tablero.DistanciaFinalTablero(casilla)+ "Diferencias rows "+tablero.InitialRows+" "+tablero.Rows ) ;
        }
        fichero.close();
        } catch (IOException ex) {
        Logger.getLogger(ChessRoyale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static void pruebaMovimientosII(){
       // PRUEBAS DE COMO VA LOS MOVIMIENTOS
        Board tablero = new Board(14, 14,null);
        System.out.println("IMPRIMO TABLERO SIN NADA");
        System.out.println(tablero);
        
        tablero.TableroInicialPiezas14();
        //System.out.println("IMPRIMO TABLERO INICIAL");
        System.out.println(tablero);
        
        for (int i = 0; i < 3; i++) {
            System.out.println(i);
            tablero.IncrementaAlertaTablero();
            System.out.println(tablero);        
        }
        Casilla casillaAuxInicio, casillaAuxFinal;
        casillaAuxInicio=tablero.getCasilla(1, 5);
        //System.out.println(casillaAuxInicio);
        casillaAuxFinal=tablero.getCasilla(2,5);
        //System.out.println(tablero.getCasilla(7, 0));
        tablero.getCasilla(0,7).Ocupada=null;
        tablero.getCasilla(7,5).Ocupada=new King(Color.PURPLE);
        tablero.getCasilla(7,6).Ocupada=new King(Color.BLUE);
        //System.out.println(tablero.getCasilla(7, 0));
        if (tablero.MovimientoLegal(casillaAuxInicio, casillaAuxFinal)) tablero=tablero.Movimiento(casillaAuxInicio, casillaAuxFinal, true);
        System.out.println(tablero);
         
    }
    private static void pruebaMovimientos(){
        Board tablero = new Board(14, 14,null);
       
        //Pruebas de movimientos...
        Pieza peoncito=new King(Color.BLACK);
        Casilla casillita=tablero.getCasilla(4,5);
        Pawn otroPeoncito=new Pawn(Color.GREEN);
        casillita.setPiezaCasilla(peoncito);
        Casilla masCasillita=tablero.getCasilla(2,5);
        masCasillita.setPiezaCasilla(otroPeoncito);
        masCasillita=tablero.getCasilla(3,3);
        masCasillita.setPiezaCasilla(peoncito);
        //masCasillita=tablero.getCasilla(3,6);
        //masCasillita.setPiezaCasilla(peoncito);
        
        System.out.println(peoncito.PossibleMoves(casillita, tablero));        
    }
    private static void pruebaDecrecerTablero(){
        // Prueba para ver como iba decreciendo el tablero... Se puede hasta i=8 con el tablero de 14 (el 9 haria
        // tablero nulo
        Board tablero = new Board(14, 14,null);
        for (int i = 0; i < 3; i++) {
            tablero.IncrementaAlertaTablero();
            System.out.println(tablero);
            System.out.println(i);
        }
        
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

    }
    private static void pruebaColorines(){
        Board tablero = new Board(14, 14,null);
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
        //System.out.println(colorines);
        //System.out.println(colores);
        //colores.removeAll(colores);
        
        //int contador=-1;
        //for(int i=0;i<10;i++) {            
        //    if(contador>=colores.size()-1) contador=0;
        //    else contador++;
        //    System.out.println(colores.get(contador));
        //}
        System.out.println("hola");
        //for(Color x:colores) System.out.println(x);
        //for(Color x:colorines) System.out.println(x);
        
        System.out.println(tablero.TurnoJugador);
        
        
        
        //Parece que con los EnumSet me puedo apañar
             
        //System.out.println(tablero);
    }
    private static void pruebaValoraciones() {    
        //PRUEBAS DE VALORACIONES
        Board tablero = NuevoTablero();
        SillyIA sillyIABlack=new SillyIA(Color.BLACK);
        SillyIA sillyIABlue=new SillyIA(Color.BLUE);
        SillyIA sillyIAGreen=new SillyIA(Color.GREEN);
        SillyIA sillyIAPurple=new SillyIA(Color.PURPLE);
        SillyIA sillyIActiva=null;
        Casilla casillaAuxInicio, casillaAuxFinal;
        //System.out.println(tablero+" V: "+sillyIA.Evaluacion(tablero));
        //casillaAuxInicio=tablero.getCasilla(1, 5);
        //casillaAuxFinal=tablero.getCasilla(2,5);
        //if (tablero.MovimientoLegal(casillaAuxInicio, casillaAuxFinal)) tablero=tablero.Movimiento(casillaAuxInicio, casillaAuxFinal);
        //System.out.println(" V: "+sillyIA.Evaluacion(tablero));
        //tablero.TablerosPosibles().forEach( x -> System.out.println( sillyIA.Evaluacion(x)) );
        double mejorJugada;
        Board mejorTablero=null;
        double valoracion;
        int indice;
        Color siguiente;
        /*       casillaAuxInicio=tablero.getCasilla(1, 7);
        casillaAuxFinal=tablero.getCasilla(2,7);
        if (tablero.MovimientoLegal(casillaAuxInicio, casillaAuxFinal)) tablero=tablero.Movimiento(casillaAuxInicio, casillaAuxFinal);
        System.out.println(tablero);*/
  
        for (int i=0;i<=40;i++){
            mejorJugada=-1000000000;
            /*            indice=tablero.JugadoresActivos.indexOf(tablero.TurnoJugador)+1;
            if(indice>tablero.JugadoresActivos.size()-1) indice=0;
            else if (indice<0) indice=tablero.JugadoresActivos.size()-1;
            siguiente=tablero.JugadoresActivos.get(indice);*/
            switch (tablero.TurnoJugador){
                case BLACK:
                    sillyIActiva=sillyIABlack;
                    break;
                case BLUE:
                    sillyIActiva=sillyIABlue;
                    break;
                case PURPLE:
                    sillyIActiva=sillyIAPurple;
                    break;
                case GREEN:
                    sillyIActiva=sillyIAGreen;
                    break;                      
            }
            //for(int j=0;j<=3;j++){
                for (Board x: tablero.TablerosPosibles(null)) {
                    valoracion=sillyIActiva.Evaluacion(x);
                    if (valoracion>mejorJugada){
                        mejorJugada=valoracion;
                        mejorTablero=x;
                    }
                //System.out.println("Activa: "+sillyIActiva.EvaluaColor+" val "+valoracion);
                }
               
            //}
            tablero=new Board(mejorTablero);
            System.out.println(tablero+"V "+sillyIActiva.Evaluacion(tablero));//+sillyIActiva.Evaluacion(tablero));
        }
        /*casillaAuxInicio=tablero.getCasilla(3, 0);
        casillaAuxFinal=tablero.getCasilla(2,0);
        if (tablero.MovimientoLegal(casillaAuxInicio, casillaAuxFinal)) tablero=tablero.Movimiento(casillaAuxInicio, casillaAuxFinal);
        for (int i=0;i<=0;i++){
        mejorJugada=-1000000000;*/
        /*            indice=tablero.JugadoresActivos.indexOf(tablero.TurnoJugador)+1;
        if(indice>tablero.JugadoresActivos.size()-1) indice=0;
        else if (indice<0) indice=tablero.JugadoresActivos.size()-1;
        siguiente=tablero.JugadoresActivos.get(indice);*/
        /*    switch (tablero.TurnoJugador){
        case BLACK:
        sillyIActiva=sillyIABlack;
        break;
        case BLUE:
        sillyIActiva=sillyIABlue;
        break;
        case PURPLE:
        sillyIActiva=sillyIAPurple;
        break;
        case GREEN:
        sillyIActiva=sillyIAGreen;
        break;
        }
        int contador=0;
        for (Board x: tablero.TablerosPosibles()) {
        contador++;
        System.out.println(contador);
        valoracion=sillyIActiva.Evaluacion(x);
        if (valoracion>mejorJugada){
        mejorJugada=valoracion;
        mejorTablero=x;
        }
        // System.out.println("val "+valoracion);
        }
        tablero=new Board(mejorTablero);
        System.out.println(tablero+"V "+sillyIActiva.Evaluacion(tablero));//+sillyIActiva.Evaluacion(tablero));
        } */
        /*mejorJugada=-1000000000;
        int contador=0;
        for (Board x: tablero.TablerosPosibles()) {
        contador++;
        System.out.println(contador);
        valoracion=sillyIABlue.Evaluacion(x);
        if (valoracion>mejorJugada){
        mejorJugada=valoracion;
        mejorTablero=x;
        }
        // System.out.println("val "+valoracion);
        }*/
    }

    private static void pruebaCasillasRey() {
        Board tablero = NuevoTablero();
        //tablero.TableroInicialPiezas14();
        Casilla casilla=tablero.getCasilla(1, 7);
        Casilla casi = tablero.getCasilla(2,7);
        tablero=tablero.Movimiento(casilla, casi, true);
        //tablero.getCasilla(4, 4).Ocupada=new King(Color.BLACK);
        //tablero.getCasilla(3,3).Ocupada=new Rook(Color.BLUE);
        casilla=tablero.getCasilla(7, 1);
        casi = tablero.getCasilla(7,2);
        tablero=tablero.Movimiento(casilla, casi, true);
        System.out.println(tablero);
        System.out.println(tablero.getCasilla(0, 7).Ocupada);
        ArrayList<Casilla> casillas=tablero.getCasilla(0,7).Ocupada.PossibleMoves(tablero.getCasilla(0, 7), tablero);
        casillas.forEach(x -> System.out.println("F: "+x.Fila+" C: "+x.Columna));
    }

    private static Board pruebaHorizonteTableros(int numero, Board tablero) {

        SillyIA sillyIABlack=new SillyIA(Color.BLACK);
        SillyIA sillyIABlue=new SillyIA(Color.BLUE);
        SillyIA sillyIAGreen=new SillyIA(Color.GREEN);
        SillyIA sillyIAPurple=new SillyIA(Color.PURPLE);
        SillyIA sillyIActiva=null;
        Casilla casillaAuxInicio, casillaAuxFinal;
        double mejorJugada,valoracion;
        //Board tableroAuxiliar;
        Board mejorTablero=null;
        
        for (int i=0;i<=numero;i++){
            //tableroAuxiliar=new Board(tablero);
            mejorTablero=null;
            mejorJugada=-1000000000;
            valoracion=-1000000000;
            /*            indice=tablero.JugadoresActivos.indexOf(tablero.TurnoJugador)+1;
            if(indice>tablero.JugadoresActivos.size()-1) indice=0;
            else if (indice<0) indice=tablero.JugadoresActivos.size()-1;
            siguiente=tablero.JugadoresActivos.get(indice);*/
            switch (tablero.TurnoJugador){
                case BLACK:
                    sillyIActiva=sillyIABlack;
                    break;
                case BLUE:
                    sillyIActiva=sillyIABlue;
                    break;
                case PURPLE:
                    sillyIActiva=sillyIAPurple;
                    break;
                case GREEN:
                    sillyIActiva=sillyIAGreen;
                    break;                      
            }
            //alphabeta(origin, depth, ??, +?, TRUE)
            //valoracion=sillyIActiva.MinMax_AlphaBeta(tablero, 6, -100000, 1000000);
            //for(int j=0;j<=3;j++){
            //ESTO ES UNA JODIENDA PERO PARA SABER QUE JUGADA ES LA QUE HACE TENGO QUE HACER UN CICLO DE MAX 
            //PREVIO para modificar el tablero
            //System.out.println(tablero.TurnoJugador);
                for (Board x: tablero.TablerosPosibles(true)) {
                    //System.out.println(x.TurnoJugador);
                    //Lo mejor es que el numero de profundidad sea NumeroJugadores*entero-1 (p.e. siendo 4= 3,7,11 ...
                    //de profundidad... para que tome un turno entero de todos... problema... que conforme pasa de 
                    //profundidad los tiempos de computacion...
                    valoracion=sillyIActiva.MinMax_AlphaBeta(x, 3, -1000000, 1000000);
                    //System.out.println(valoracion+" "+sillyIActiva.Evaluacion(x));
                    //valoracion=sillyIActiva.Evaluacion(x);
                    //if (sillyIActiva.Evaluacion(x)!=sillyIActiva.MinMax_AlphaBeta(x, 0, -100000, 1000000)) System.out.println("Algo Pasa");
                    if (valoracion>mejorJugada){
                        mejorJugada=valoracion;
                        mejorTablero=x;
                    }
                //System.out.println("Activa: "+sillyIActiva.EvaluaColor+" val "+valoracion);
                }
               
            //}
            try{
            tablero=new Board(mejorTablero);
            }
            catch (Exception e){
                System.out.println(valoracion+" "+mejorJugada);
            }
            System.out.println(tablero+"V "+sillyIActiva.Evaluacion(tablero));//+sillyIActiva.Evaluacion(tablero));
        }
        return tablero;
    }

    private static Board NuevoTablero() {
        Board tablero = new Board(14, 14,null);
        tablero.TableroInicialPiezas14();
        return tablero;
    }

}

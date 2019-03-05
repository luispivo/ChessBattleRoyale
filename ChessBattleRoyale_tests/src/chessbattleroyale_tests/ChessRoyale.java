/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessbattleroyale_tests;

/**
 *
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
        for (int i = 0; i < 0; i++) {
            tablero.IncrementaAlertaTablero();
            System.out.println(tablero);
            System.out.println(i);
        }
        //System.out.println(tablero);
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

}

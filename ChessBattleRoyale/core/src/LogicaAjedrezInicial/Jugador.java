/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

/**
 *
 * @author Luis
 */
public class Jugador {
    String Nick,Nombre,Apellidos,Correo;
    ELO Elo;
    Long TiempoRestante;
    TipoJugador ClaseJugador;
    Color PiezasPartida;

    public Jugador(TipoJugador ClaseJugador, Color PiezasPartida) {
        this.ClaseJugador = ClaseJugador;
        this.PiezasPartida = PiezasPartida;
    }

    public Jugador(int TiempoRestante, TipoJugador ClaseJugador, Color PiezasPartida) {
        this.TiempoRestante = new Long(TiempoRestante*60000);
        this.ClaseJugador = ClaseJugador;
        this.PiezasPartida = PiezasPartida;
    }    
}
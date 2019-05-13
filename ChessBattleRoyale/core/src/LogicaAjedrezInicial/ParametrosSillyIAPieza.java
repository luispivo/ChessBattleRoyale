/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import java.util.Objects;

/**
 * Clase con los parámetros necesarios para una Pieza de la IA SillyIA
 * @author Luis
 */
public class ParametrosSillyIAPieza {
    //Perdón por la nomenclatura de poner esto valores todo en mayusculas pero como en realidad hasta que optimice IAs
    //son como parámetros fijos (o sea constantes...)
    double MOVIMIENTOSMAXIMOS;
    double VALOR;
    TipoPieza TipoPieza;

    public ParametrosSillyIAPieza(double movimientos, double valor, TipoPieza tipo) {
        this.MOVIMIENTOSMAXIMOS = movimientos;
        this.VALOR = valor;
        this.TipoPieza = tipo;
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.TipoPieza);
        return hash;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParametrosSillyIAPieza other = (ParametrosSillyIAPieza) obj;
        if (this.TipoPieza != other.TipoPieza) {
            return false;
        }
        return true;
    }
    
}

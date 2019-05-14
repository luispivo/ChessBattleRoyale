/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import java.util.ArrayList;

/**
 *
 * @author lpastora
 */
class TableroProfundidad {
    Board TableroGenerador;
    int Profundidad;
    ArrayList<Board> TablerosFuturos;
    double Valoracion;
    
    public TableroProfundidad(Board generator,int profundidad){
        TableroGenerador=generator;
        Profundidad=profundidad;
        TablerosFuturos=generator.TablerosPosibles(null);
    }
}

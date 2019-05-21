/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 *
 * @author Luis
 */
/**
 * Casilla. Básicamente un Vector (Fila, Columna) con un estado de peligrosidad de desaparición y que puede
 * contener una Pieza (null si está vacía).
 * @author Luis
 */
public class Casilla extends Actor{
    int Fila;
    int Columna;
    public Pieza Ocupada;
    EstadoCasilla Status;
    Boolean Clickada;
    Texture imagenCasilla;
    Texture darkwhite=new Texture(Gdx.files.internal("darkwhite.png"));
    Texture darkyellow=new Texture(Gdx.files.internal("darkyellow.png"));
    Texture darkorange=new Texture(Gdx.files.internal("darkorange.png"));
    Texture darkred=new Texture(Gdx.files.internal("darkred.png"));
    Texture white=new Texture(Gdx.files.internal("white.png"));
    Texture yellow=new Texture(Gdx.files.internal("yellow.jpg"));
    Texture orange=new Texture(Gdx.files.internal("orange.png"));
    Texture red=new Texture(Gdx.files.internal("red.png"));

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);//To change body of generated methods, choose Tools | Templates.
        
        if ((Fila+Columna)%2==0) {
            switch (Status){
                    case EMPTY:
                        imagenCasilla=darkwhite;
                    break;
                    case DANGERYELLOW:
                        imagenCasilla=darkyellow;
                    break;
                    case DANGERORANGE:
                        imagenCasilla=darkorange;
                    break;
                    case DANGERRED:
                        imagenCasilla=darkred;
                    break;
            }            
        }
        else{
             switch (Status){
                    case EMPTY:
                        imagenCasilla=white;
                    break;
                    case DANGERYELLOW:
                        imagenCasilla=yellow;
                    break;
                    case DANGERORANGE:
                        imagenCasilla=orange;
                    break;
                    case DANGERRED:
                        imagenCasilla=red;
                    break;
            }                             
        }
        batch.draw(imagenCasilla,Fila*50+30,Columna*50+30);
    }

    
    /**
     * Crea la casilla correspondiente
     * @param columna 
     * @param fila 
     */
    public Casilla(int fila,int columna) {
        Fila = fila;
        Columna = columna;
        Status=EstadoCasilla.EMPTY;
        Clickada=false;
    }
    
    public Casilla(Casilla casilla){
        Fila=casilla.Fila;
        Columna=casilla.Columna;
        Status=casilla.Status;
        Clickada=casilla.Clickada;
        Ocupada=casilla.Ocupada;
    }

    Pieza CopiaPiezaPorTipo() {
        //Problema es que claro tengo que copiar la pieza... pero claro eso depende de que pieza por usar la pieza
        //abstracta... quizás debería haber usado una interface y quitar todo estos problemas
        if (Ocupada != null) {
            switch (Ocupada.ClasePieza) {
                case PAWN:
                    Ocupada = new Pawn(Ocupada);
                    break;
                case KNIGHT:
                    Ocupada = new Knight(Ocupada);
                    break;
                case ROOK:
                    Ocupada = new Rook(Ocupada);
                    break;
                case BISHOP:
                    Ocupada = new Bishop(Ocupada);
                    break;
                case QUEEN:
                    Ocupada = new Queen(Ocupada);
                    break;
                case KING:
                    Ocupada = new King(Ocupada);
                    break;
            }
        }
        return Ocupada;
    }

    void setPiezaCasilla(Pieza Ocupada) {
        this.Ocupada = Ocupada;
    }
    
    /**
     * Incrementa el status de peligro de la casilla y si esta sobrepasa al rojo la elimina del conjunto
     * de casillas que forman el tablero
     * @param tablero El tablero. Necesario puesto que se tiene que eliminar la casilla de la colección si el
     * peligro sobrepasa al rojo
     * @return True si se ha eliminado una casilla (para luego tener en cuenta que hay que cambiar número
     * de filas y columnas o False si no se ha eliminado la casilla
     */
    boolean IncreaseDangerStatus(Board tablero){
       switch (Status) {
           case EMPTY:
               Status=EstadoCasilla.DANGERYELLOW;
               break;
           case DANGERYELLOW:
               Status=EstadoCasilla.DANGERORANGE;
               break;
           case DANGERORANGE:
               Status=EstadoCasilla.DANGERRED;
               break;
           case DANGERRED:
               tablero.EliminaCasilla(this);
               //System.out.println("ELIMINANDO fila:"+Fila+" Columna:"+Columna);
               return true;
       }
       return false;
    }
    // Que feos son los hashcode y los equals del netbeans pero bueno...
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.Fila;
        hash = 37 * hash + this.Columna;
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
        final Casilla other = (Casilla) obj;
        if (this.Fila != other.Fila) {
            return false;
        }
        return this.Columna == other.Columna;
    }
    
    /**
     * 
     * @return Un string significativo del status de la casilla (luego ya modificaré cuando tenga las piezas 
     * hechas para que además devuelva que pieza hay y que jugador para pintarlas en el LIBGDX
     */
    @Override
    public String toString() {
        String auxiliar=null;
        switch (Status) {
            case EMPTY:
               auxiliar="|";
               break;
           case DANGERYELLOW:
               //auxiliar="|Y";
               auxiliar="\u001B[42;32m|";
               break;
           case DANGERORANGE:
               //auxiliar="|O";
               auxiliar="\u001B[43;33m|";
               break;
           case DANGERRED:
               //auxiliar="|R";
               auxiliar="\u001B[41;31m|";
               break;
        }
        //return auxiliar+" "+Fila+"-"+Columna+" "+Ocupada+"|";
        return auxiliar+Ocupada+"|\u001B[40;30m";
        //return auxiliar;  
        
    }    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * Clase tablero: Contiene una collección de Casillas y todo lo necesario para manejar un tablero de
 * InitialRowsxInitialColumns dimensiones y como van reduciendose estas. Sé que para tableros normales
 * al ser cuadrados no es necesario distinguir entre número de columnas y número de filas pero por futuras
 * implementaciones de tableros rectangulares he preferido separar y repetir un poco la "lógica" de la clase.
 * 
 * @author Luis
 */
public class Board {
    int Rows,InitialRows;
    int Columns,InitialColumns;
    
    //No tengo claro si es mejor tener aquí simplemente un color representando al jugador que le toca mover
    //o tener al jugador entero. Vamos a ir explorando con esto pero lo mismo es mejor tener la referencia al jugador
    Color TurnoJugador;
    //La colección de colores de jugadores activos que no han sido eliminados el rey (y por lo tanto sus piezas todavía
    EnumSet<Color> JugadoresActivos;
    
    ArrayList<Casilla> Tablero;
    //Otra posibilidad sería trabajar con 
    //Casilla[][] TCasillas;
    //pero es mejor el arraylist a priori para no tener fijas las dimensiones

    /**
     * Constructor para el tablero vacío, sin piezas y que comienza el jugador negro
     * @param columns número de columnas del tablero (1 al número de columnas)
     * @param rows número de filas del tablero (1 al número de filas)
     */
    public Board(int columns, int rows) {
        Tablero=new ArrayList();
        this.Rows = rows;
        this.Columns = columns;
        InitialRows=rows;
        InitialColumns=columns;
        for(int i=0;i<Rows;i++) for(int j=0;j<Columns;j++) Tablero.add(new Casilla(i,j));
        TurnoJugador=Color.BLACK;
    } 
    /**
     * Constructor copia
     * @param board Tablero a copiar
     */
    public Board(Board board){
        Rows=board.Rows;
        InitialRows=board.InitialRows;
        Columns=board.Columns;
        InitialColumns=board.InitialColumns; 
        TurnoJugador=board.TurnoJugador;
        JugadoresActivos=board.JugadoresActivos;
        Tablero=new ArrayList();
        for (Casilla x:board.Tablero) {
            Casilla y=new Casilla(x);
            Tablero.add(y);
        }
    }
    /**
     * 
     * @param casilla Casilla que se elimina de la colección del tablero
     */
    void EliminaCasilla(Casilla casilla){
        Tablero.remove(casilla);
        //Cambiando las dimensiones del tablero...        
    }
    /**
     * 
     * @param fila
     * @param columna
     * @return La casilla correspondiente a esa fila y columna (null si ya eliminada)
     */
    Casilla getCasilla(int fila, int columna){
        if ((fila>=(InitialRows-Rows)/2 && fila <(InitialRows+Rows)/2)&&(columna>=(InitialColumns-Columns)/2&& columna<(InitialColumns+Columns)/2)){
            int index=Rows*(fila-(InitialRows-Rows)/2)+columna-(InitialColumns-Columns)/2;
            return Tablero.get(index);
        }
        else return null;
    }
    /**
     * Mira si esta casilla está amenazada por alguna de las piezas de otro color 
     * (Util para ver si puedo mover el rey a esa casilla) o para comprobar si debo preocuparme
     * del jaque en el movimiento). Todo esto es para chess "tradicional" quizás sea mejor eliminarlo 
     * para la variante Chess Royale que planteo...
     * @param casilla La casilla a calcular
     * @param color El color del jugador "amenazado"
     * @return true si alguna pieza la amenaza y en otro caso false
     */
    boolean CasillaAmenazadaPorOtroJugador(Casilla casilla, Color color){
        //Recorro todo el tablero en busca de las piezas de colores distintos al del jugador en cuestión 
        //Y me baso en que he definido el equals como las coordenadas de la casilla (no lo que contiene 
        //y su estado
        for (Casilla x: Tablero){
            if (!x.equals(casilla)&& x.Ocupada!=null && x.Ocupada.ColorJugador!=color){
                for(Casilla y: x.Ocupada.PossibleCaptures(x, this)){
                    //System.out.println("hola");
                    if (casilla.equals(y)) return true;
                }
            }
        }             
        return false;   
    }
    /**
     * NO IMPLEMENTADO
     * @return Un conjunto de tableros que constituyen las posibles jugadas a partir de él. No incluyen valoración
     * que eso lo haré dependiendo de parametros de mi intento de hacer IA
     */
    ArrayList<Board> JugadasPosibles(){
        ArrayList<Board> tablerosFuturos=new ArrayList();
        for (Casilla x: Tablero){
            if(x.Ocupada!=null && x.Ocupada.ColorJugador==TurnoJugador){
                for (Casilla y:x.Ocupada.PossibleMoves(x, this)) tablerosFuturos.add(Movimiento(x, y));
            }
        }
        
        return null;
    }
    /**
     * Incrementa el status de desaparición a un conjunto rectangular de casillas del tablero con el
     * @param fila fila y columna (y su complementaria en el otro lado del tablero) a la que se le incrementa
     * el status de peligro de desaparición
     */
    void IncrementaAlertaTablero(int fila){
        boolean eliminarParteTablero=false;
        for(int i=0;i<Tablero.size();i++){
            if(((Tablero.get(i).Fila==fila||Tablero.get(i).Fila==(InitialRows-fila-1))&&(Tablero.get(i).Columna<=(InitialRows-fila-1)&&Tablero.get(i).Columna>=fila)) 
                    ||((Tablero.get(i).Columna==fila||Tablero.get(i).Columna==(InitialColumns-fila-1)) &&(Tablero.get(i).Fila<=(InitialColumns-fila-1)&&Tablero.get(i).Fila>=fila)))  
                if(Tablero.get(i).IncreaseDangerStatus(this)){
                        eliminarParteTablero=true;
                        i--;
                }
        }
        //Se eliminan 2 filas y columnas con esto asi que se modifican estos valores de la clase tablero
        if (eliminarParteTablero) {
            Rows-=2;
            Columns-=2;
        }
    }
    /**
     * Llamando a esta función ya se encarga de cambiar el status a todo el tablero correspondientemente al juego
     * Consigo el status del borde con el status de la primera casilla (Tablero.get(0).Status) y pinta
     * correspondientemente un color más (y las filas interiores correspondientes).
     * Con (InitialRows-Rows)/2 se consigue la fila de casillas reales que se muestra según la numeración
     * inicial del tablero
     * OJO Al orden... es necesario usar el Incrementa de dentro afuera pues el de fuera cambia la variable 
     * Rows cuando hace desparecer las casillas
     */
    void IncrementaAlertaTablero(){
        int numeroFilas=0;
        switch(Tablero.get(0).Status){
            case DANGERYELLOW:     
                numeroFilas=1;
                break;
            case DANGERORANGE:
                numeroFilas=2;
                break;
            case DANGERRED:
                numeroFilas=3;
                break;
        }
        for (int i=numeroFilas;i>=0;i--) IncrementaAlertaTablero((InitialRows-Rows)/2+i);               
    }
    /**
     * Mueve una pieza de la casilla inicio a la casilla destino en un tablero copia
     * @param inicio Casilla inicio
     * @param destino Casilla destino
     */
    Board Movimiento(Casilla inicio, Casilla destino){
        Board nuevoTablero=new Board(this);
        Pieza pieza=inicio.CopiaPiezaPorTipo();
        nuevoTablero.getCasilla(inicio.Fila, inicio.Columna).Ocupada=null;
        nuevoTablero.getCasilla(destino.Fila, destino.Columna).Ocupada=pieza;
        return nuevoTablero;
    }
    /**
     * Pues colocar todas las piezas en el tablero, esto si que dependerá del tablero asi que una función por
     * cada tipo de juego que se quiera. Por eso no he considerado logico hacerlo en modo general, aprovecharemos
     * que sabemos que son 14 filas y columnas y 4 jugadores
     */
    public void TableroInicialPiezas14(){
        Pieza aux;
        for(int j=3;j<=10;j++) {
                //Los peones no necesitan un switch ...
                aux=new Pawn(Color.BLACK);                                        
                this.getCasilla(1,j).Ocupada=aux;
                aux=new Pawn(Color.BLUE);                                        
                this.getCasilla(j,1).Ocupada=aux;
                aux=new Pawn(Color.PURPLE);                                        
                this.getCasilla(12,j).Ocupada=aux;
                aux=new Pawn(Color.GREEN);                                        
                this.getCasilla(j,12).Ocupada=aux;
                //Las piezas si que depende de la columnan son distintas...
                switch (j) {
                    case 3:case 10:                         
                        aux=new Rook(Color.BLACK);
                        this.getCasilla(0,j).Ocupada=aux;
                        aux=new Rook(Color.PURPLE);
                        this.getCasilla(13,j).Ocupada=aux;
                        aux=new Rook(Color.BLUE);
                        this.getCasilla(j,0).Ocupada=aux;
                        aux=new Rook(Color.GREEN);
                        this.getCasilla(j,13).Ocupada=aux;                        
                        break;
                    case 4: case 9:                
                        aux=new Knight(Color.BLACK);
                        this.getCasilla(0,j).Ocupada=aux;               
                        aux=new Knight(Color.PURPLE);
                        this.getCasilla(13,j).Ocupada=aux;
                        aux=new Knight(Color.BLUE);
                        this.getCasilla(j,0).Ocupada=aux;
                        aux=new Knight(Color.GREEN);
                        this.getCasilla(j,13).Ocupada=aux;                        
                        break;                       
                    case 5: case 8:
                        aux=new Bishop(Color.BLACK);
                        this.getCasilla(0,j).Ocupada=aux;
                        aux=new Bishop(Color.PURPLE);
                        this.getCasilla(13,j).Ocupada=aux;
                        aux=new Bishop(Color.BLUE);
                        this.getCasilla(j,0).Ocupada=aux;
                        aux=new Bishop(Color.GREEN);
                        this.getCasilla(j,13).Ocupada=aux;                        
                        break;
                    case 6:
                        aux=new Queen(Color.BLACK);
                        this.getCasilla(0,j).Ocupada=aux;
                        aux=new Queen(Color.PURPLE);
                        this.getCasilla(13,j).Ocupada=aux;
                        aux=new Queen(Color.BLUE);
                        this.getCasilla(j,0).Ocupada=aux;
                        aux=new Queen(Color.GREEN);
                        this.getCasilla(j,13).Ocupada=aux;                        
                        break;                       
                    case 7:
                        aux=new King(Color.BLACK);
                        this.getCasilla(0,j).Ocupada=aux;
                        aux=new King(Color.PURPLE);
                        this.getCasilla(13,j).Ocupada=aux;
                        aux=new King(Color.BLUE);
                        this.getCasilla(j,0).Ocupada=aux;
                        aux=new King(Color.GREEN);
                        this.getCasilla(j,13).Ocupada=aux;
                        break;                    
                }                
            }
    }
    /**
    * 
    * @return string con el tablero (para pruebas antes de ir pintando con la libreria y demás LIBGDX
    */
    @Override
    public String toString() {
        int fila=Tablero.get(0).Fila;
        String auxiliar="";
        //Aqui estoy dibujando al reves ... la fila 0 al principio cuando en tablero normal deberia ser abajo
        for(int i=0;i<Tablero.size();i++){
           if (Tablero.get(i).Fila==fila) auxiliar+= Tablero.get(i).toString();
           else{               
               auxiliar+=("\n"+Tablero.get(i).toString());
               fila++;
           }
        }
        /*int contador=0;
        for(Casilla x:Tablero){
            contador++;
            if (contador>Rows) {
                auxiliar+="\n";
                contador=1;
            }
            auxiliar+=x;
        }*/
        return auxiliar;
    }   
}
/**
 * Enumeración de los distintos estados de la casilla
 * @author Luis
 */
enum EstadoCasilla{
    EMPTY,DANGERYELLOW,DANGERORANGE,DANGERRED
}
/**
 * Casilla. Básicamente un Vector (Fila, Columna) con un estado de peligrosidad de desaparición y que puede
 * contener una Pieza (null si está vacía).
 * @author Luis
 */
class Casilla{
    int Fila;
    int Columna;
    Pieza Ocupada;
    EstadoCasilla Status;
    Boolean Clickada;

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
        Ocupada=CopiaPiezaPorTipo();
    }

    Pieza CopiaPiezaPorTipo() {
        //Problema es que claro tengo que copiar la pieza... pero claro eso depende de que pieza por usar la pieza
        //abstracta... quizás debería haber usado una interface y quitar todo estos problemas
        switch (Ocupada.ClasePieza){
            case PAWN:
                Ocupada=new Pawn(Ocupada);
                break;
            case KNIGHT:
                Ocupada=new Knight(Ocupada);
                break;
            case ROOK:
                Ocupada=new Rook(Ocupada);
                break;
            case BISHOP:
                Ocupada=new Bishop(Ocupada);
                break;
            case QUEEN:
                Ocupada=new Queen(Ocupada);
                break;
            case KING:
                Ocupada=new King(Ocupada);
                break;
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
        if (this.Columna != other.Columna) {
            return false;
        }
        return true;
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
               auxiliar="| |";
               break;
           case DANGERYELLOW:
               auxiliar="|Y|";
               break;
           case DANGERORANGE:
               auxiliar="|O|";
               break;
           case DANGERRED:
               auxiliar="|R|";
               break;
        }
        return auxiliar+"*"+Fila+"*"+Columna+"*"+Ocupada;
        //return auxiliar;
    }    
}

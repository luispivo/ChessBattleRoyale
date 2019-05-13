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
    ArrayList<Color> JugadoresActivos;
    
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
        JugadoresActivos=new ArrayList<>();
        (EnumSet.allOf(Color.class)).forEach( x -> JugadoresActivos.add(x));
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
        board.Tablero.forEach( x -> Tablero.add(new Casilla(x)));
    }
    /**
     * Elimina la casilla del tablero
     * @param casilla Casilla que se elimina de la colección del tablero
     */
    void EliminaCasilla(Casilla casilla){
        Tablero.remove(casilla);
        //Cambiando las dimensiones del tablero...        
    }
    /**
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
        //y su estado. 
        // REPASAR
        /*for (Casilla x: Tablero){
            if (!x.equals(casilla)&& x.Ocupada!=null && x.Ocupada.ColorJugador!=color){
                for(Casilla y: x.Ocupada.PossibleCaptures(x, this)){
                    //System.out.println("hola");
                    if (casilla.equals(y)) return true;
                }
            }
        }  */ 
        
        for (Casilla x:Tablero){
            if (x.Ocupada!=null && x.Ocupada.ColorJugador!=color){
                //Esto Tengo que ver como lo soluciono... El problema es que con el rey se dedica a a llamar recursivamente
                //hasta el final
                if(x.Ocupada.PossibleCaptures(x, this).contains(casilla)) return true;                
            }
        }
        return false;   
    }
    
    
    /**
     * @return Un conjunto de tableros que constituyen las posibles jugadas a partir de él. No incluyen valoración
     * que eso lo haré dependiendo de parametros de mi intento de hacer IA
     */
    ArrayList<Board> TablerosPosibles(){
        ArrayList<Board> tablerosFuturos=new ArrayList();
        for (Casilla x: Tablero){
            //System.out.println("contador:"+contador);
            if(x.Ocupada!=null && x.Ocupada.ColorJugador==TurnoJugador){
                for (Casilla y:x.Ocupada.PossibleMoves(x, this)) tablerosFuturos.add(Movimiento(x, y));
            }
        }
        //System.out.println("Casillas "+Tablero.size()+" Tableros "+tablerosFuturos.size());
        return tablerosFuturos;
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
        Boolean cambiarTurno=false;
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
        for(Color x:JugadoresActivos) if (ColorSeVaDePartida(x)) {
            EliminaPiezasJugadorEliminado(x);
            cambiarTurno=true;           
        }
        if (cambiarTurno) EliminaJugadorYPasaAlSiguienteJugador(false);
    }
    /**
     * Mueve una pieza de la casilla inicio a la casilla destino en un tablero copia.
     * NOTA: No incluyo aqui comprobación que el movimiento es legal porque lo quiero usar con 
     * el generador de movimientos para la IA donde ya tengo que comprobar que le pongo movimientos
     * legales. Así que me pareció excesivo ponerle un doble check de ello. Esto hace que para las partidas 
     * normales este check de movimiento legal ha de hacerse SIEMPRE y genere algo un tanto más feo
     * pero es el precio a pagar para no incrementar la carga de "checks" de movimiento legal
     * @param inicio Casilla inicio
     * @param destino Casilla destino
     */
    Board Movimiento(Casilla inicio, Casilla destino){
        Board nuevoTablero=new Board(this);
        //System.out.println("UNA COSA");
        //System.out.println(nuevoTablero);
        Pieza pieza=inicio.CopiaPiezaPorTipo();
        nuevoTablero.getCasilla(inicio.Fila, inicio.Columna).Ocupada=null;
        nuevoTablero.getCasilla(destino.Fila, destino.Columna).Ocupada=pieza;
        nuevoTablero.EliminaJugadorYPasaAlSiguienteJugador(true);
        
        //Chequeo del jaque mate...
        //UNA posibilidad es comprobar lo de los reyes como hicimos con los cambios de colroes
        //nuevoTablero.JugadoresActivos.forEach ( x -> {if (ColorSeVaDePartida(x)) nuevoTablero.EliminaPiezasJugadorEliminado(x);});
        //PERO dado que es cosa del ultimo movimiento parece mas sencillo para menos ciclos y memoria...
        if (this.getCasilla(destino.Fila, destino.Columna).Ocupada!=null && this.getCasilla(destino.Fila, destino.Columna).Ocupada.ClasePieza==TipoPieza.KING) nuevoTablero.EliminaPiezasJugadorEliminado(destino.Ocupada.ColorJugador);
        
        return nuevoTablero;
    }
    /**
     * Comprueba que el movimiento de inicio a final se puede hacer
     * @param inicio casilla donde debe haber una pieza que se pueda mover
     * @param destino casilla a la que se mueve
     * @return 
     */
    Boolean MovimientoLegal(Casilla inicio, Casilla destino){
        return  (inicio.Ocupada !=null&&inicio.Ocupada.PossibleMoves(inicio, this).contains(destino));
    }
    /**
     * Elimina al jugador de  colore eliminado(y sus piezas)  y cambia el turno del jugador si es necesario 
     * @param cambio indice que indica si hay que cambiar el turno o no
     */
    private void EliminaJugadorYPasaAlSiguienteJugador(Boolean cambio) {
        int indice;
        Color colorSiguienteJugador;
        indice=JugadoresActivos.indexOf(TurnoJugador)+(cambio?1:0); 
        //System.out.println(TurnoJugador.toString()+indice+JugadoresActivos.get(indice));
        do{            
            if(indice>JugadoresActivos.size()-1) indice=0;
            else if (indice<0) indice=JugadoresActivos.size()-1;
            colorSiguienteJugador=JugadoresActivos.get(indice);
            if (ColorSeVaDePartida(colorSiguienteJugador)) {
                JugadoresActivos.remove(colorSiguienteJugador);
                EliminaPiezasJugadorEliminado(colorSiguienteJugador);
                //System.out.println(colorSiguienteJugador+"HOLA"+TurnoJugador);
                if (colorSiguienteJugador.equals(TurnoJugador)) cambio=true;
            }          
            //System.out.println(colorSiguienteJugador.toString()+ColorSeVaDePartida(colorSiguienteJugador).toString());
        } while (JugadoresActivos.size()>0 && ColorSeVaDePartida(colorSiguienteJugador));   
        if (cambio) TurnoJugador=colorSiguienteJugador;
    }
    /**
     * Comprueba que el jugador/color no ha desaparecido del tablero
     * @param color
     * @return Booleano de si ha perdido o continua en la partida
     */
    private Boolean ColorSeVaDePartida(Color color){
        for (Casilla x:Tablero) if( x.Ocupada!=null && x.Ocupada.ClasePieza==TipoPieza.KING && x.Ocupada.ColorJugador==color) return false;
        return true;
    }
    /**
     * Función para eliminar las piezas de un determinado color/jugador (p.e. porque su rey ha desaparecido
     * @param colorSiguienteJugador color de las piezas a eliminar
     */
    private void EliminaPiezasJugadorEliminado(Color color) {
        for (Casilla x:Tablero) if(x.Ocupada!=null && x.Ocupada.ColorJugador==color) x.Ocupada=null;
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
     * Esta función da la distancia a las casillas "rojas" para usar en la valoración estática del tablero
     * necesaria para el cálculo de la función que va a emplear la IA. Cuando aún no hay casillas en peligro
     * se le suma parte de la distancia para tener eso en cuenta en el factor.
     * @param casilla donde ese encuentra la pieza
     * @return Integer con la distancia parametrizada
     */
    protected int DistanciaFinalTablero(Casilla casilla){
        //En vez de pelearnos con las filas y columnas y tener expresiones complicadas 
        //vamos a emplear un truco un tanto rastrero teniendo en cuenta como se van colocando las piezas
        //en el ArrayList del tablero conforme vamos eliminandolas y demás.
        //Basicamente es lo mismo que empleamos para ir borrando las filas y columnas del tablero pero aqui 
        //en vez de borrarlas devolvemos la distancia a la que se encuentra
        //De hecho queda por implementar en un futuro reestructurar el código para usar esta función para ambas cosas
        //porque asi aunque inspirada parece más simple. Pero el cambio no es tan simple
        
        int contador=0;
        int fila=casilla.Fila;
        int columna=casilla.Columna;
        for(int i=0;i<=Rows/2;i++){
            if((fila==i+(InitialRows-Rows)/2||fila==(InitialRows+Rows-2*i-2)/2)||(columna==i+(InitialColumns-Columns)/2||columna==(InitialColumns+Columns-2*i-2)/2)){
               contador=i;
               break;
            }
        }
        //La siguiente parte es para tener en cuenta cuando aún no se han coloreado en peligro hay menos peligro de estar 
        //al borde del tablero
        switch(Tablero.get(0).Status){
            case DANGERYELLOW:
                contador+=2;
                break;
            case DANGERORANGE:
                contador+=1;
                break;
            case DANGERRED:
                break;
            default:
                contador+=3;
                break;
        }
        return contador;
    }
    Color jugadorAnterior(){
        int indice=JugadoresActivos.indexOf(TurnoJugador)-1;            
        if(indice<0) indice= JugadoresActivos.size()-1;
        return JugadoresActivos.get(indice);
    }
    /**
    * 
    * @return string con el tablero (para pruebas antes de ir pintando con la libreria y demás LIBGDX
    */
    @Override
    public String toString() {
        int fila=Tablero.get(0).Fila;
        String auxiliar=TurnoJugador+"\n";
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
              
        /*//UNA PRUEBA
        String auxiliar="";
        for(int i=0;i<Tablero.size();i++){
           if (Tablero.get(i).Fila==fila) auxiliar+= String.format("%.3f",this.FactorDistancia(Tablero.get(i)))+";";
           else{               
               auxiliar+="\n"+String.format("%.3f",this.FactorDistancia(Tablero.get(i)))+";";
               fila++;
           }
        }*/
        return auxiliar;
    }   
}

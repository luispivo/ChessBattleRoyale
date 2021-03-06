/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase tablero: Contiene una collección de Casillas y todo lo necesario para
 * manejar un tablero de InitialRowsxInitialColumns dimensiones y como van
 * reduciendose estas. Sé que para tableros normales al ser cuadrados no es
 * necesario distinguir entre número de columnas y número de filas pero por
 * futuras implementaciones de tableros rectangulares he preferido separar y
 * repetir un poco la "lógica" de la clase.
 *
 * @author Luis
 */
public class Board extends Actor {

    int Rows, InitialRows;
    int Columns, InitialColumns;

    //No tengo claro si es mejor tener aquí simplemente un color representando al jugador que le toca mover
    //o tener al jugador entero. Vamos a ir explorando con esto pero lo mismo es mejor tener la referencia al jugador
    public Color TurnoJugador;
    //La colección de colores de jugadores activos que no han sido eliminados el rey (y por lo tanto sus piezas todavía
    ArrayList<Color> JugadoresActivos;
    int Movimientos;
    //De momento para las pruebas lo pongo como constante
    final int AUMENTARPELIGRO;

    public ArrayList<Casilla> Tablero;
    //Otra posibilidad sería trabajar con 
    //Casilla[][] TCasillas;
    //pero es mejor el arraylist a priori para no tener fijas las dimensiones
    TextureAtlas Atlas;
    //Esta vez para variar de casilla he cogido y he puesto simplemente los nombres de las regiones 
    //Correspondientes... Quizás es menos modular para el futuro pero bueno 
    //Lo ideal seria juntar los colores con el TiemposReloj en una clase POJO pero no me atrevo a
    //refactorizarlo teniendo en cuenta el tiempo que me queda para la entrega. Lo apunto como 
    //una issue en github

    ArrayList<Jugador> Jugadores;
    Long TiempoAnterior;

    //Creo el sprite más que nada para darle un tamaño todo relacionado con la casilla asi que me da igual que imagen coger (creo)
    //Lo mismo es un poco torticero pero...
    Sprite sprite;

    BitmapFont Font;
    GlyphLayout GLayout;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha); //To change body of generated methods, choose Tools | Templates.
        batch.draw(Atlas.findRegion("titulo"), sprite.getWidth() * 15, sprite.getHeight() * 13);
        //System.out.println(TurnoJugador.toString());
        batch.draw(Atlas.findRegion(TurnoJugador.toString()), sprite.getWidth() * 17, sprite.getHeight() * 11);

        //Aqui hay un bug cuando se elimina un jugador el indice pasa de 1-4 a 1-3....
        //y esto no entiende de esas cosas
        //int indice=JugadoresActivos.indexOf(TurnoJugador);
        for (Jugador x : Jugadores) {
            if (x.PiezasPartida == TurnoJugador) {
                x.TiempoRestante -= (System.currentTimeMillis() - TiempoAnterior);
                if (x.TiempoRestante <= 0) {
                    EliminaPiezasJugadorEliminado(TurnoJugador);
                    EliminaJugadorYPasaAlSiguienteJugador(true);
                    //JugadoresActivos.remove(TurnoJugador);            
                    //TiempoJugadorACero(TurnoJugador);
                }
            }
            
        }
        TiempoAnterior = System.currentTimeMillis();

        PasarTiempoAString(Jugadores, batch);

    }

    private void PasarTiempoAString(ArrayList<Jugador> TiemposReloj, Batch batch) {
        String tiempo;
        long tiempoSobrante;
        for (int i = 0; i < TiemposReloj.size(); i++) {
            switch (TiemposReloj.get(i).PiezasPartida){
                case BLACK:
                    Font.setColor(0,0,0,1);
                    break;
                case GREEN:
                    Font.setColor(56/255f,86/255f,35/255f,1);
                    break;
                case BLUE:
                    Font.setColor(0,112/255f,192/255f,1);
                    break;
                case PURPLE:
                    Font.setColor(112/255f,48/255f,160/255f,1);
                    break;                           
            }
            
            Font.draw(batch, TiempoAString(TiemposReloj.get(i).TiempoRestante), sprite.getWidth() * 17, sprite.getHeight() * (10 - i));
        }
    }

    String TiempoAString(Long x) {
        String tiempo;
        long tiempoSobrante;
        tiempo = Long.toString(x / 3600000) + ":";
        tiempoSobrante = x % 3600000;
        if (tiempoSobrante / 60000 >= 10) {
            tiempo += Long.toString(tiempoSobrante / 60000) + ":";
        } else {
            tiempo += "0" + Long.toString(tiempoSobrante / 60000) + ":";
        }
        tiempoSobrante = tiempoSobrante % 60000;
        if (tiempoSobrante / 1000 >= 10) {
            tiempo += Long.toString(tiempoSobrante / 1000);
        } else {
            tiempo += "0" + Long.toString(tiempoSobrante / 1000);
        }
        //tiempo+=Long.toString(tiempoSobrante%1000); //Me parece excesivo sacar los milisegundos
        return tiempo;
    }

    /**
     * Constructor para el tablero vacío, sin piezas y que comienza el jugador
     * negro
     *
     * @param columns número de columnas del tablero (1 al número de columnas)
     * @param rows número de filas del tablero (1 al número de filas)
     */
    public Board(int columns, int rows, TextureAtlas atlas, ArrayList<Jugador> ListaJugadores, int aumentar) {
        Tablero = new ArrayList();
        this.Rows = rows;
        this.Columns = columns;
        InitialRows = rows;
        InitialColumns = columns;
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                Tablero.add(new Casilla(i, j, atlas, this));
            }
        }
        TurnoJugador = Color.BLACK;
        JugadoresActivos = new ArrayList<>();
        Atlas = atlas;
        (EnumSet.allOf(Color.class)).forEach(x -> {
            JugadoresActivos.add(x);
        });
        Movimientos = 0;
        sprite = new Sprite(new Texture(Gdx.files.internal("darkwhite.png")));
//        Turno=new Label("Le toca jugar a:", style);
        TiempoAnterior = System.currentTimeMillis();
        Font = new BitmapFont(new BitmapFont.BitmapFontData(Gdx.files.internal("time.fnt"), false), Atlas.findRegion("time"), true);
        GLayout = new GlyphLayout();
        Jugadores = ListaJugadores;
        AUMENTARPELIGRO=aumentar;
    }

    /**
     * Constructor copia
     *
     * @param board Tablero a copiar
     */
    public Board(Board board) {
        Rows = board.Rows;
        InitialRows = board.InitialRows;
        Columns = board.Columns;
        InitialColumns = board.InitialColumns;
        TurnoJugador = board.TurnoJugador;
        JugadoresActivos = new ArrayList();
        board.JugadoresActivos.forEach(x -> JugadoresActivos.add(x));
        Tablero = new ArrayList();
        board.Tablero.forEach(x -> Tablero.add(new Casilla(x)));
        Atlas = board.Atlas;
        Movimientos = board.Movimientos;
        this.sprite = board.sprite;
        Jugadores = board.Jugadores;
        TiempoAnterior = board.TiempoAnterior;
        Font = board.Font;
        GLayout = board.GLayout;
        AUMENTARPELIGRO=board.AUMENTARPELIGRO;
    }

    /**
     * Elimina la casilla del tablero
     *
     * @param casilla Casilla que se elimina de la colección del tablero
     */
    void EliminaCasilla(Casilla casilla) {
        Tablero.remove(casilla);
        //Que dolor de cabeza da a veces el Libgdx... 
        //hay que quitarlo de los actores además de la lista de casillas para que deje de mostrarse
        //PD: Tal vez se pueda refactorizar todo para usar esto de abajo... aunque creo que al hacer pruebas
        //no funcionaba añadir actores así asi que dado el tiempo que me queda se queda asi de momento.
        getStage().getActors().removeValue(casilla, true);
        //Cambiando las dimensiones del tablero...        
    }

    /**
     * @param fila
     * @param columna
     * @return La casilla correspondiente a esa fila y columna (null si ya
     * eliminada)
     */
    public Casilla getCasilla(int fila, int columna) {
        if ((fila >= (InitialRows - Rows) / 2 && fila < (InitialRows + Rows) / 2) && (columna >= (InitialColumns - Columns) / 2 && columna < (InitialColumns + Columns) / 2)) {
            int index = Rows * (fila - (InitialRows - Rows) / 2) + columna - (InitialColumns - Columns) / 2;
            return Tablero.get(index);
        } else {
            return null;
        }
    }

    /**
     * Mira si esta casilla está amenazada por alguna de las piezas de otro
     * color (Util para ver si puedo mover el rey a esa casilla) o para
     * comprobar si debo preocuparme del jaque en el movimiento). Todo esto es
     * para chess "tradicional" quizás sea mejor eliminarlo para la variante
     * Chess Royale que planteo...
     *
     * @param casilla La casilla a calcular
     * @param color El color del jugador "amenazado"
     * @return true si alguna pieza la amenaza y en otro caso false
     */
    boolean CasillaAmenazadaPorOtroJugador(Casilla casilla, Color color) {
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

        for (Casilla x : Tablero) {
            if (x.Ocupada != null && x.Ocupada.ColorJugador != color) {
                //Esto Tengo que ver como lo soluciono... El problema es que con el rey se dedica a a llamar recursivamente
                //hasta el final
                if (x.Ocupada.PossibleCaptures(x, this).contains(casilla)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param analisis the value of analisis
     * @return the java.util.ArrayList<LogicaAjedrezInicial.Board>
     */
    public ArrayList<Board> TablerosPosibles(Boolean noAnalisis) {
        ArrayList<Board> tablerosFuturos = new ArrayList();
        for (Casilla x : Tablero) {
            //System.out.println("contador:"+contador);
            if (x.Ocupada != null && x.Ocupada.ColorJugador == TurnoJugador) {
                for (Casilla y : x.Ocupada.PossibleMoves(x, this)) {
                    tablerosFuturos.add(Movimiento(x, y, noAnalisis));
                }
            }
        }
        //System.out.println("Casillas "+Tablero.size()+" Tableros "+tablerosFuturos.size());
        return tablerosFuturos;
    }

    /**
     * Incrementa el status de desaparición a un conjunto rectangular de
     * casillas del tablero con el
     *
     * @param fila fila y columna (y su complementaria en el otro lado del
     * tablero) a la que se le incrementa el status de peligro de desaparición
     */
    void IncrementaAlertaTablero(int fila) {
        boolean eliminarParteTablero = false;
        for (int i = 0; i < Tablero.size(); i++) {
            if (((Tablero.get(i).Fila == fila || Tablero.get(i).Fila == (InitialRows - fila - 1)) && (Tablero.get(i).Columna <= (InitialRows - fila - 1) && Tablero.get(i).Columna >= fila))
                    || ((Tablero.get(i).Columna == fila || Tablero.get(i).Columna == (InitialColumns - fila - 1)) && (Tablero.get(i).Fila <= (InitialColumns - fila - 1) && Tablero.get(i).Fila >= fila))) {
                if (Tablero.get(i).IncreaseDangerStatus(this)) {
                    eliminarParteTablero = true;
                    i--; //Al eliminar la casilla hay que tener en cuenta que el resto pasa a ocupar un indice menor asi que 
                    //lo elimino para continuar sin salirme de los limites en el for
                }
            }
        }
        //Se eliminan 2 filas y columnas con esto asi que se modifican estos valores de la clase tablero
        if (eliminarParteTablero) {
            Rows -= 2;
            Columns -= 2;
        }
    }

    /**
     * Llamando a esta función ya se encarga de cambiar el status a todo el
     * tablero correspondientemente al juego Consigo el status del borde con el
     * status de la primera casilla (Tablero.get(0).Status) y pinta
     * correspondientemente un color más (y las filas interiores
     * correspondientes). Con (InitialRows-Rows)/2 se consigue la fila de
     * casillas reales que se muestra según la numeración inicial del tablero
     * OJO Al orden... es necesario usar el Incrementa de dentro afuera pues el
     * de fuera cambia la variable Rows cuando hace desparecer las casillas
     */
    public void IncrementaAlertaTablero() {
        Boolean cambiarTurno = false;
        int numeroFilas = 0;
        switch (Tablero.get(0).Status) {
            case DANGERYELLOW:
                numeroFilas = 1;
                break;
            case DANGERORANGE:
                numeroFilas = 2;
                break;
            case DANGERRED:
                numeroFilas = 3;
                break;
        }
        for (int i = numeroFilas; i >= 0; i--) {
            IncrementaAlertaTablero((InitialRows - Rows) / 2 + i);
        }
        for (Color x : JugadoresActivos) {
            if (ColorSeVaDePartida(x)) {
                EliminaPiezasJugadorEliminado(x);
                cambiarTurno = true;
            }
        }
        if (cambiarTurno) {
            EliminaJugadorYPasaAlSiguienteJugador(true);
        }
    }

    /**
     * Mueve una pieza de la casilla inicio a la casilla destino en un tablero
     * copia.NOTA: No incluyo aqui comprobación que el movimiento es legal
     * porque lo quiero usar con el generador de movimientos para la IA donde ya
     * tengo que comprobar que le pongo movimientos legales. Así que me pareció
     * excesivo ponerle un doble check de ello. Esto hace que para las partidas
     * normales este check de movimiento legal ha de hacerse SIEMPRE y genere
     * algo un tanto más feo pero es el precio a pagar para no incrementar la
     * carga de "checks" de movimiento legal
     *
     * @param inicio Casilla inicio
     * @param destino Casilla destino
     * @param analisis the value of analisis
     * @return the LogicaAjedrezInicial.Board
     */
    Board Movimiento(Casilla inicio, Casilla destino, Boolean noAnalisis) {
        // Tal y como lo tenia antes por cuestiones de IA y demas y he tenido que cambiarlo por Libgdx 

        Board nuevoTablero = new Board(this);
        //Chequeo del jaque mate
        if (this.getCasilla(destino.Fila, destino.Columna).Ocupada != null && this.getCasilla(destino.Fila, destino.Columna).Ocupada.ClasePieza == TipoPieza.KING) {
            TiempoJugadorACero(destino.Ocupada.ColorJugador);
            nuevoTablero.EliminaPiezasJugadorEliminado(destino.Ocupada.ColorJugador);
        }
        Pieza pieza = inicio.CopiaPiezaPorTipo();
        nuevoTablero.getCasilla(inicio.Fila, inicio.Columna).Ocupada = null;
        nuevoTablero.getCasilla(destino.Fila, destino.Columna).Ocupada = pieza;

        nuevoTablero.EliminaJugadorYPasaAlSiguienteJugador(noAnalisis);
        if (nuevoTablero.TurnoJugador == nuevoTablero.JugadoresActivos.get(0)) {
            nuevoTablero.Movimientos++;
        }
        if (nuevoTablero.Movimientos % AUMENTARPELIGRO == 0) {
            nuevoTablero.IncrementaAlertaTablero();
        }

        return nuevoTablero;
    }

    void MovimientoNuevo(Casilla inicio, Casilla destino, Boolean noAnalisis) {

        //Chequeo del jaque mate...
        //UNA posibilidad es comprobar lo de los reyes como hicimos con los cambios de colroes
        //nuevoTablero.JugadoresActivos.forEach ( x -> {if (ColorSeVaDePartida(x)) nuevoTablero.EliminaPiezasJugadorEliminado(x);});
        //PERO dado que es cosa del ultimo movimiento parece mas sencillo para menos ciclos y memoria...
        if (this.getCasilla(destino.Fila, destino.Columna).Ocupada != null && this.getCasilla(destino.Fila, destino.Columna).Ocupada.ClasePieza == TipoPieza.KING) {
            //System.out.println("Algo pasa, inicio"+inicio.Fila+" "+inicio.Columna+" en destino f"+destino.Fila+" c"+destino.Columna+" Pieza: "+this.getCasilla(destino.Fila, destino.Columna).Ocupada.ClasePieza);
            TiempoJugadorACero(destino.Ocupada.ColorJugador);
            EliminaPiezasJugadorEliminado(destino.Ocupada.ColorJugador);
        }
        //System.out.println("UNA COSA");
        //System.out.println(nuevoTablero);
        Pieza pieza = inicio.CopiaPiezaPorTipo();
        getCasilla(inicio.Fila, inicio.Columna).Ocupada = null;
        getCasilla(destino.Fila, destino.Columna).Ocupada = pieza;

        EliminaJugadorYPasaAlSiguienteJugador(noAnalisis);
        if (JugadorAnterior() == JugadoresActivos.get(JugadoresActivos.size() - 1)) {
            Movimientos++;
            if (Movimientos % AUMENTARPELIGRO == 0) {
                IncrementaAlertaTablero();
            }
        }
    }

    /**
     * Comprueba que el movimiento de inicio a final se puede hacer
     *
     * @param inicio casilla donde debe haber una pieza que se pueda mover
     * @param destino casilla a la que se mueve
     * @return
     */
    Boolean MovimientoLegal(Casilla inicio, Casilla destino) {
        return (inicio.Ocupada != null && inicio.Ocupada.PossibleMoves(inicio, this).contains(destino));
    }

    /**
     * Elimina al jugador de colore eliminado(y sus piezas) y cambia el turno
     * del jugador si es necesario
     *
     * @param cambio indice que indica si hay que cambiar el turno o no
     */
    private void EliminaJugadorYPasaAlSiguienteJugador(Boolean noAnalisis) {
        int indice;
        Color colorSiguienteJugador;
        indice = JugadoresActivos.indexOf(TurnoJugador) + 1;//(cambio?1:0); 
        //System.out.println(TurnoJugador.toString()+indice+JugadoresActivos.get(indice));
        do {
            if (indice > JugadoresActivos.size() - 1) {
                indice = 0;
            }
            // else if (indice<0) indice=JugadoresActivos.size()-1;
            colorSiguienteJugador = JugadoresActivos.get(indice);
            if (ColorSeVaDePartida(colorSiguienteJugador) && noAnalisis) {
                //System.out.println("ENTRO A BORRAR2");
                //System.out.println(cambio.toString()+colorSiguienteJugador+this);
                JugadoresActivos.remove(colorSiguienteJugador);
                EliminaPiezasJugadorEliminado(colorSiguienteJugador);
                TiempoJugadorACero(colorSiguienteJugador);
            }
            //System.out.println(colorSiguienteJugador.toString()+ColorSeVaDePartida(colorSiguienteJugador).toString());
        } while (JugadoresActivos.size() > 0 && ColorSeVaDePartida(colorSiguienteJugador));
        //if (cambio) 
        TurnoJugador = colorSiguienteJugador;
    }

    void TiempoJugadorACero(Color colorSiguienteJugador) {
        for (Jugador x : Jugadores) {
            if (x.PiezasPartida == colorSiguienteJugador) {
                x.TiempoRestante = new Long(0);
            }
        }
        //System.out.println(colorSiguienteJugador+"HOLA"+TurnoJugador);
        //if (colorSiguienteJugador.equals(TurnoJugador)) cambio=true;
    }

    /**
     * Comprueba que el jugador/color no ha desaparecido del tablero
     *
     * @param color
     * @return Booleano de si ha perdido o continua en la partida
     */
    protected Boolean ColorSeVaDePartida(Color color) {
        for (Casilla x : Tablero) {
            if (x.Ocupada != null && x.Ocupada.ClasePieza == TipoPieza.KING && x.Ocupada.ColorJugador == color) {
                return false;
            }
        }
        return true;
    }

    /**
     * Función para eliminar las piezas de un determinado color/jugador (p.e.
     * porque su rey ha desaparecido
     *
     * @param colorSiguienteJugador color de las piezas a eliminar
     */
    private void EliminaPiezasJugadorEliminado(Color color) {
        //System.out.println("ENTRO A BORRAR1"+color);
        for (Casilla x : Tablero) {
            if (x.Ocupada != null && x.Ocupada.ColorJugador == color) {
                x.Ocupada = null;
            }
        }

    }

    /**
     * Pues colocar todas las piezas en el tablero, esto si que dependerá del
     * tablero asi que una función por cada tipo de juego que se quiera. Por eso
     * no he considerado logico hacerlo en modo general, aprovecharemos que
     * sabemos que son 14 filas y columnas y 4 jugadores
     */
    public void TableroInicialPiezas14() {
        Pieza aux;
        for (int j = 3; j <= 10; j++) {
            //Los peones no necesitan un switch ...
            aux = new Pawn(Color.BLACK);
            this.getCasilla(1, j).Ocupada = aux;
            aux = new Pawn(Color.BLUE);
            this.getCasilla(j, 1).Ocupada = aux;
            aux = new Pawn(Color.PURPLE);
            this.getCasilla(12, j).Ocupada = aux;
            aux = new Pawn(Color.GREEN);
            this.getCasilla(j, 12).Ocupada = aux;
            //Las piezas si que depende de la columnan son distintas...
            switch (j) {
                case 3:
                case 10:
                    aux = new Rook(Color.BLACK);
                    this.getCasilla(0, j).Ocupada = aux;
                    aux = new Rook(Color.PURPLE);
                    this.getCasilla(13, j).Ocupada = aux;
                    aux = new Rook(Color.BLUE);
                    this.getCasilla(j, 0).Ocupada = aux;
                    aux = new Rook(Color.GREEN);
                    this.getCasilla(j, 13).Ocupada = aux;
                    break;
                case 4:
                case 9:
                    aux = new Knight(Color.BLACK);
                    this.getCasilla(0, j).Ocupada = aux;
                    aux = new Knight(Color.PURPLE);
                    this.getCasilla(13, j).Ocupada = aux;
                    aux = new Knight(Color.BLUE);
                    this.getCasilla(j, 0).Ocupada = aux;
                    aux = new Knight(Color.GREEN);
                    this.getCasilla(j, 13).Ocupada = aux;
                    break;
                case 5:
                case 8:
                    aux = new Bishop(Color.BLACK);
                    this.getCasilla(0, j).Ocupada = aux;
                    aux = new Bishop(Color.PURPLE);
                    this.getCasilla(13, j).Ocupada = aux;
                    aux = new Bishop(Color.BLUE);
                    this.getCasilla(j, 0).Ocupada = aux;
                    aux = new Bishop(Color.GREEN);
                    this.getCasilla(j, 13).Ocupada = aux;
                    break;
                case 6:
                    aux = new Queen(Color.BLACK);
                    this.getCasilla(0, j).Ocupada = aux;
                    aux = new Queen(Color.PURPLE);
                    this.getCasilla(13, j).Ocupada = aux;
                    aux = new Queen(Color.BLUE);
                    this.getCasilla(j, 0).Ocupada = aux;
                    aux = new Queen(Color.GREEN);
                    this.getCasilla(j, 13).Ocupada = aux;
                    break;
                case 7:
                    aux = new King(Color.BLACK);
                    this.getCasilla(0, j).Ocupada = aux;
                    aux = new King(Color.PURPLE);
                    this.getCasilla(13, j).Ocupada = aux;
                    aux = new King(Color.BLUE);
                    this.getCasilla(j, 0).Ocupada = aux;
                    aux = new King(Color.GREEN);
                    this.getCasilla(j, 13).Ocupada = aux;
                    break;
            }
        }
    }

    /**
     * Esta función da la distancia a las casillas "rojas" para usar en la
     * valoración estática del tablero necesaria para el cálculo de la función
     * que va a emplear la IA. Cuando aún no hay casillas en peligro se le suma
     * parte de la distancia para tener eso en cuenta en el factor.
     *
     * @param casilla donde ese encuentra la pieza
     * @return Integer con la distancia parametrizada
     */
    protected int DistanciaFinalTablero(Casilla casilla) {
        //En vez de pelearnos con las filas y columnas y tener expresiones complicadas 
        //vamos a emplear un truco un tanto rastrero teniendo en cuenta como se van colocando las piezas
        //en el ArrayList del tablero conforme vamos eliminandolas y demás.
        //Basicamente es lo mismo que empleamos para ir borrando las filas y columnas del tablero pero aqui 
        //en vez de borrarlas devolvemos la distancia a la que se encuentra
        //De hecho queda por implementar en un futuro reestructurar el código para usar esta función para ambas cosas
        //porque asi aunque inspirada parece más simple. Pero el cambio no es tan simple

        int contador = 0;
        int fila = casilla.Fila;
        int columna = casilla.Columna;
        for (int i = 0; i <= Rows / 2; i++) {
            if ((fila == i + (InitialRows - Rows) / 2 || fila == (InitialRows + Rows - 2 * i - 2) / 2) || (columna == i + (InitialColumns - Columns) / 2 || columna == (InitialColumns + Columns - 2 * i - 2) / 2)) {
                contador = i;
                break;
            }
        }
        //La siguiente parte es para tener en cuenta cuando aún no se han coloreado en peligro hay menos peligro de estar 
        //al borde del tablero
        switch (Tablero.get(0).Status) {
            case DANGERYELLOW:
                contador += 2;
                break;
            case DANGERORANGE:
                contador += 1;
                break;
            case DANGERRED:
                break;
            default:
                contador += 3;
                break;
        }
        return contador;
    }

    Color JugadorAnterior() {
        int indice = JugadoresActivos.indexOf(TurnoJugador) - 1;
        if (indice < 0) {
            indice = JugadoresActivos.size() - 1;
        }
        return JugadoresActivos.get(indice);
    }

    /**
     *
     * @return string con el tablero (para pruebas antes de ir pintando con la
     * libreria y demás LIBGDX
     */
    @Override
    public String toString() {
        int fila = Tablero.get(0).Fila;
        String auxiliar = TurnoJugador + "\n";
        //Aqui estoy dibujando al reves ... la fila 0 al principio cuando en tablero normal deberia ser abajo
        for (int i = 0; i < Tablero.size(); i++) {
            if (Tablero.get(i).Fila == fila) {
                auxiliar += Tablero.get(i).toString();
            } else {
                auxiliar += ("\n" + Tablero.get(i).toString());
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

    boolean SoloUnaCasillaClickada() {
        for (Casilla x : Tablero) {
            if (x.Clickada) {
                return true;
            }
        }
        return false;
    }

    void QuitarClicks() {
        for (Casilla x : Tablero) {
            x.Clickada = false;
        }
    }

    Casilla CasillaClickada() {
        for (Casilla x : Tablero) {
            if (x.Clickada) {
                return x;
            }
        }
        return null;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaAjedrezInicial;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 *
 * @author Luis
 */
/**
 * Casilla. Básicamente un Vector (Fila, Columna) con un estado de peligrosidad
 * de desaparición y que puede contener una Pieza (null si está vacía).
 *
 * @author Luis
 */
public class Casilla extends Actor {

    Board Partida;
    TextureAtlas Atlas;
    int Fila;
    int Columna;
    public Pieza Ocupada;
    EstadoCasilla Status;
    Boolean Clickada;
//    TextureRegion darkwhite, darkyellow, darkorange, darkred, white, yellow, orange, red;
//    TextureRegion blackKnight, blackBishop, blackKing, blackPawn, blackQueen, blackRook;
//    TextureRegion blueKnight, blueBishop, blueKing, bluePawn, blueQueen, blueRook;
//    TextureRegion greenKnight, greenBishop, greenKing, greenPawn, greenQueen, greenRook;
//    TextureRegion purpleKnight, purpleBishop, purpleKing, purplePawn, purpleQueen, purpleRook;
    TextureRegion imagenCasilla, imagenPieza;
//    TextureRegion diana, darkDiana;

    //Creo el sprite más que nada para darle un tamaño asi que me da igual que imagen coger (creo)
    //Lo mismo es un poco torticero pero...
    Sprite sprite; 

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);//To change body of generated methods, choose Tools | Templates.
//NOTA: Este codigo se puede reformatear simplemente cambiando el nombre de las imagenes y creando una string
//para llamar a la region correspondiente. Cosas que te das cuenta a posteriori... pero bueno aunque queda como 
//una issue no sé si sería más eficiente en memoria y no sé como afectaria con los ToString que usaba para las
//enumeraciones... lo mismo también tendría que cambiar eso y el tiempo se echa encima...
        if ((Fila + Columna) % 2 == 0) {
            switch (Status) {
                case EMPTY:
                    imagenCasilla = Atlas.findRegion("darkwhite");
                    break;
                case DANGERYELLOW:
                    imagenCasilla = Atlas.findRegion("darkyellow");
                    break;
                case DANGERORANGE:
                    imagenCasilla = Atlas.findRegion("darkorange");
                    break;
                case DANGERRED:
                    imagenCasilla = Atlas.findRegion("darkred");
                    break;
            }
        } else {
            switch (Status) {
                case EMPTY:
                    imagenCasilla = Atlas.findRegion("white");
                    break;
                case DANGERYELLOW:
                    imagenCasilla = Atlas.findRegion("yellow");
                    break;
                case DANGERORANGE:
                    imagenCasilla = Atlas.findRegion("orange");
                    break;
                case DANGERRED:
                    imagenCasilla = Atlas.findRegion("red");
                    break;
            }
        }
        batch.draw(imagenCasilla, Fila * sprite.getWidth() + 30, Columna * sprite.getWidth() + 30);
        if (Ocupada != null) {
            switch (Ocupada.ClasePieza) {
                case PAWN:
                    switch (Ocupada.ColorJugador) {
                        case BLACK:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickblackPawn");
                            } else {
                                imagenPieza = Atlas.findRegion("chapablackPawn");
                            }
                            break;
                        case BLUE:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickbluePawn");
                            } else {
                                imagenPieza = Atlas.findRegion("chapabluePawn");
                            }
                            break;
                        case GREEN:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickgreenPawn");
                            } else {
                                imagenPieza = Atlas.findRegion("chapagreenPawn");
                            }
                            break;
                        case PURPLE:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickpurplePawn");
                            } else {
                                imagenPieza = Atlas.findRegion("chapapurplePawn");
                            }
                            break;
                    }
                    break;
                case KNIGHT:
                    switch (Ocupada.ColorJugador) {
                        case BLACK:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickblackKnight");
                            } else {
                                imagenPieza = Atlas.findRegion("chapablackKnight");
                            }
                            break;
                        case BLUE:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickblueKnight");
                            } else {
                                imagenPieza = Atlas.findRegion("chapablueKnight");
                            }
                            break;
                        case GREEN:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickgreenKnight");
                            } else {
                                imagenPieza = Atlas.findRegion("chapagreenKnight");
                            }
                            break;
                        case PURPLE:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickpurpleKnight");
                            } else {
                                imagenPieza = Atlas.findRegion("chapapurpleKnight");
                            }
                            break;
                    }
                    break;
                case BISHOP:
                    switch (Ocupada.ColorJugador) {
                        case BLACK:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickblackBishop");
                            } else {
                                imagenPieza = Atlas.findRegion("chapablackBishop");
                            }
                            break;
                        case BLUE:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickblueBishop");
                            } else {
                                imagenPieza = Atlas.findRegion("chapablueBishop");
                            }
                            break;
                        case GREEN:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickgreenBishop");
                            } else {
                                imagenPieza = Atlas.findRegion("chapagreenBishop");
                            }
                            break;
                        case PURPLE:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickpurpleBishop");
                            } else {
                                imagenPieza = Atlas.findRegion("chapapurpleBishop");
                            }
                            break;
                    }
                    break;
                case ROOK:
                    switch (Ocupada.ColorJugador) {
                        case BLACK:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickblackRook");
                            } else {
                                imagenPieza = Atlas.findRegion("chapablackRook");
                            }
                            break;
                        case BLUE:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickblueRook");
                            } else {
                                imagenPieza = Atlas.findRegion("chapablueRook");
                            }
                            break;
                        case GREEN:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickgreenRook");
                            } else {
                                imagenPieza = Atlas.findRegion("chapagreenRook");
                            }
                            break;
                        case PURPLE:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickpurpleRook");
                            } else {
                                imagenPieza = Atlas.findRegion("chapapurpleRook");
                            }
                            break;
                    }
                    break;
                case QUEEN:
                    switch (Ocupada.ColorJugador) {
                        case BLACK:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickblackQueen");
                            } else {
                                imagenPieza = Atlas.findRegion("chapablackQueen");
                            }
                            break;
                        case BLUE:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickblueQueen");
                            } else {
                                imagenPieza = Atlas.findRegion("chapablueQueen");
                            }
                            break;
                        case GREEN:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickgreenQueen");
                            } else {
                                imagenPieza = Atlas.findRegion("chapagreenQueen");
                            }
                            break;
                        case PURPLE:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickpurpleQueen");
                            } else {
                                imagenPieza = Atlas.findRegion("chapapurpleQueen");
                            }
                            break;
                    }
                    break;
                case KING:
                    switch (Ocupada.ColorJugador) {
                        case BLACK:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickblackKing");
                            } else {
                                imagenPieza = Atlas.findRegion("chapablackKing");
                            }
                            break;
                        case BLUE:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickblueKing");
                            } else {
                                imagenPieza = Atlas.findRegion("chapablueKing");
                            }
                            break;
                        case GREEN:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickgreenKing");
                            } else {
                                imagenPieza = Atlas.findRegion("chapagreenKing");
                            }
                            break;
                        case PURPLE:
                            if (Clickada) {
                                imagenPieza = Atlas.findRegion("chapaclickpurpleKing");
                            } else {
                                imagenPieza = Atlas.findRegion("chapapurpleKing");
                            }
                            break;
                    }
                    break;
            }
            batch.draw(imagenPieza, Fila * sprite.getWidth() + 30, Columna * sprite.getHeight() + 30);
        }
        if (Partida.SoloUnaCasillaClickada() && Partida.CasillaClickada().Ocupada.PossibleMoves(Partida.CasillaClickada(), Partida).contains(this)) {
            batch.draw(Atlas.findRegion("diana"), Fila * sprite.getWidth() + 30, Columna * sprite.getHeight() + 30);
        }
        //Tenia puesto que cambiara segun la casilla fuera oscura o clara pero bueno creo que esta imagen
        //gris es un buen compromiso (Aun asi dejo esto en parte por tener esto por si futuro y en parte
        //porque como no me salia por un error mio asi queda otra forma de implementar lo de este color

        //for (Casilla x : Partida.CasillaClickada().Ocupada.PossibleMoves(Partida.CasillaClickada(), Partida)) {
        //System.out.println("f+c"+x.Fila+"@"+Fila+"|"+x.Columna+"@"+Columna);
        //if (x.Fila==Fila && x.Columna==Columna) {
        //  if ((Fila + Columna) % 2 == 0) {
        //    System.out.println("hola");
        //    batch.draw(Atlas.findRegion("darkdiana"), Fila * sprite.getWidth() + 30, Columna * sprite.getHeight() + 30);
        //} else {
        //}
        //break;
        //}
        //}
        //batch.draw(Atlas.findRegion("diana"), Fila * sprite.getWidth() + 30, Columna * sprite.getHeight() + 30);
//            System.out.println(Partida.CasillaClickada().Ocupada.PossibleMoves(Partida.CasillaClickada(), Partida).contains(Partida.getCasilla(Fila, Columna)));//{
        /*if ((Fila + Columna) % 2 == 0) batch.draw(Atlas.findRegion("darkdiana."), Fila * sprite.getWidth() + 30, Columna * sprite.getHeight() + 30);
                else {
                    batch.draw(Atlas.findRegion("diana"), Fila * sprite.getWidth() + 30, Columna * sprite.getHeight() + 30);
                    System.out.println("pasoporaqui");
                }*/
        //}
    }

    /**
     * Crea la casilla correspondiente
     *
     * @param columna
     * @param fila
     */
    public Casilla(int fila, int columna, TextureAtlas atlas, Board partida) {
        Fila = fila;
        Columna = columna;
        Status = EstadoCasilla.EMPTY;
        Clickada = false;
        Atlas = atlas;
        Partida = partida;
        //Posicion y tamaño de la casilla (Perdon por lo del 30 pero estoy acelerando a malas maneras por la entrega...
        //NOTA: Revisar que necesitare acceder al tablero desde aqui para controlar que dos casillas han sido tocadas para 
        //hacer el movimiento... Creo que tengo la idea en la cabeza pero para que no se me olvide:
        //funcion busqueda en las filas que hay una clickada, casilla al tocar cambia estado clickada-no clickada,
        //si dos casillas (o sea se clicka una cuando ya hay otra realiza el movimiento y tendré que ver como cambia
        //el tiempo. Al clickar... estaria guay que resaltara casilla y pusiera movimientos si es una pieza. Al descklickar quita
        //Pero si no sale.... ya pasaría a lo siguiente       
        //Otra CUESTION tener en cuenta el TurnoJugador y que si es IA tiene que haber una manera de evitar esto ¿otro
        //booleano de jugador o enemigo?
        sprite = new Sprite(Atlas.findRegion("darkwhite"));
        setBounds(Fila * sprite.getWidth() + 30, Columna * sprite.getHeight() + 30, sprite.getWidth(), sprite.getHeight());
        setTouchable(Touchable.enabled);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //System.out.println("Toque la casilla " + Fila + "|" + Columna);
                if (Clickada) {
                    Clickada = false;
                } else if (Ocupada != null && (Ocupada.ColorJugador == Partida.TurnoJugador) && !Partida.SoloUnaCasillaClickada()) {
                    Clickada = true;
                } else if (Partida.SoloUnaCasillaClickada()) {
                  //  System.out.println("Tocaria moverse ahi");
                    //System.out.println(Partida.CasillaClickada());
                    //System.out.println(Partida.getCasilla(Fila, Columna));
                    if (Partida.MovimientoLegal(Partida.CasillaClickada(), Partida.getCasilla(Fila, Columna))) {//INNECESARIO YA HE COMPROBADO QUE EL MOVIMIENTO ES LEGAL
                        Partida.MovimientoNuevo(Partida.CasillaClickada(), Partida.getCasilla(Fila, Columna), true);
                        Partida.QuitarClicks();
                       // int indice = getStage().getActors().size;
                       // getStage().getActors().removeRange(0, indice - 1);
                       // for (Casilla cas : Partida.Tablero) {
                       //     getStage().addActor(cas);
                       // }
                       // getStage().addActor(Partida);
                        //Board prueba = (Board) (getStage().getActors().get(indice - 1));
                       // System.out.println(prueba + " " + prueba.CasillaClickada());
                       // System.out.println(getStage().getActors().size);

                        //int indice=getStage().getActors().size;
                        //getStage().getActors().removeIndex(indice-1);
                        //getStage().getActors().
                        //getStage().addActor(Partida);
                        //System.out.println(prueba);
                        //prueba=prueba.Movimiento(Partida.CasillaClickada(), Partida.getCasilla(Fila, Columna), true);
                        //Partida.QuitarClicks();   
                    }
                }

                return super.touchDown(event, x, y, pointer, button); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    public Casilla(Casilla casilla) {
        Fila = casilla.Fila;
        Columna = casilla.Columna;
        Status = casilla.Status;
        Clickada = casilla.Clickada;
        Ocupada = casilla.Ocupada;
        Atlas = casilla.Atlas;
        Partida = casilla.Partida;
        this.sprite=casilla.sprite;
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
     * Incrementa el status de peligro de la casilla y si esta sobrepasa al rojo
     * la elimina del conjunto de casillas que forman el tablero
     *
     * @param tablero El tablero. Necesario puesto que se tiene que eliminar la
     * casilla de la colección si el peligro sobrepasa al rojo
     * @return True si se ha eliminado una casilla (para luego tener en cuenta
     * que hay que cambiar número de filas y columnas o False si no se ha
     * eliminado la casilla
     */
    boolean IncreaseDangerStatus(Board tablero) {
        switch (Status) {
            case EMPTY:
                Status = EstadoCasilla.DANGERYELLOW;
                break;
            case DANGERYELLOW:
                Status = EstadoCasilla.DANGERORANGE;
                break;
            case DANGERORANGE:
                Status = EstadoCasilla.DANGERRED;
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
     * @return Un string significativo del status de la casilla (luego ya
     * modificaré cuando tenga las piezas hechas para que además devuelva que
     * pieza hay y que jugador para pintarlas en el LIBGDX
     */
    @Override
    public String toString() {
        String auxiliar = null;
        switch (Status) {
            case EMPTY:
                auxiliar = "|";
                break;
            case DANGERYELLOW:
                //auxiliar="|Y";
                auxiliar = "\u001B[42;32m|";
                break;
            case DANGERORANGE:
                //auxiliar="|O";
                auxiliar = "\u001B[43;33m|";
                break;
            case DANGERRED:
                //auxiliar="|R";
                auxiliar = "\u001B[41;31m|";
                break;
        }
        //return auxiliar+" "+Fila+"-"+Columna+" "+Ocupada+"|";
        return auxiliar + Ocupada + "|\u001B[40;30m";
        //return auxiliar;  

    }
}

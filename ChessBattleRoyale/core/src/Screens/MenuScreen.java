/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import LogicaAjedrezInicial.Color;
import LogicaAjedrezInicial.TipoJugador;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.pddm.game.ChessBattleRoyaleGame;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Luis
 */
public class MenuScreen extends PlantillaScreen{
    
    Stage stage;
    TextButton IniciarPartidaButton;
    Slider TiempoPorJugador;
    TextureAtlas Atlas;
    Label LabelMinutos,Desaparicion;
    Label LabelTiempoPorJugador;
    Image Reloj, Aguja, Control, TituloTiempo, Icono,Titulo;
    TextField DesaparicionField;
    //Table Jugadores;
    Image JugadorBlack,JugadorBlue,JugadorPurple,JugadorGreen;
    ButtonGroup<CheckBox> TipoBlack,TipoBlue,TipoPurple,TipoGreen;
    CheckBox HumanoW,IAW,RemotoW,HumanoB,IAB,RemotoB,HumanoP,IAP,RemotoP,HumanoG,IAG,RemotoG;
    
    
    public MenuScreen(ChessBattleRoyaleGame game) {
        super(game);
        //Esto lo voy a hacer con una skin con licencia CC BY 4.0. Give credit to Raymond "Raeleus" Buckley.
        //Aunque tras lo peleado lo mismo cambio por otras o hago la mia propia en un futuro muy muy lejano
        //Perdon por no calcular las cosas mejor e ir colocandolo asi como asi y no con el mundo
        //Pero de nuevo la fecha de entrega...
        stage=new Stage(game.Viewport);
        Gdx.input.setInputProcessor(stage);
        Atlas=game.getAssetManager().get("ChessBattleRoyale_assets.atlas");
        Skin skin=new Skin(new FileHandle("rusty-robot-ui.json"),new TextureAtlas("rusty-robot-ui.atlas"));
        IniciarPartidaButton=new TextButton("Empezar Partida", skin);
        IniciarPartidaButton.setPosition(9*stage.getViewport().getWorldWidth()/10, 50,Align.bottomRight);
        IniciarPartidaButton.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button); //To change body of generated methods, choose Tools | Templates.
                game.setScreen(new PartidaScreen(game,(int) TiempoPorJugador.getValue(),DesaparicionField.getText()));
            }        
        });
        stage.addActor(IniciarPartidaButton);
        //SliderStyle sl=new SliderStyle   //(skin.getDrawable("slider-horizontal"),skin.getDrawable("slider-knob-horizontal"));
        LabelMinutos=new Label("1",skin);
        LabelMinutos.setFontScale(2f);
        TiempoPorJugador = new Slider (1, 30, 1, false,skin);
        TiempoPorJugador.setSize(stage.getViewport().getWorldWidth()/4, 50);
        TiempoPorJugador.setPosition(9*stage.getViewport().getWorldWidth()/10, 200,Align.bottomRight);
        TiempoPorJugador.addListener(new ActorGestureListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                
                LabelMinutos.setText(Integer.toString((int) TiempoPorJugador.getValue()));
            }
            
        });
        //En fin... para colocarlo bien necesito el slider... para el slider necesito primero la label...
        LabelMinutos.setPosition(9*stage.getViewport().getWorldWidth()/10+35,200+TiempoPorJugador.getHeight()/3,Align.bottomLeft);
        
        Reloj=new Image(skin,"meter");
        Aguja=new Image(skin,"meter-needle");
        Control=new Image(skin,"meter-front");
        Reloj.setPosition(9*stage.getViewport().getWorldWidth()/10, 260,Align.bottomRight);
        Reloj.setWidth(75);
        Reloj.setHeight(75);
        Aguja.setPosition(9*stage.getViewport().getWorldWidth()/10, 260,Align.bottomRight);
        Aguja.setWidth(75);
        Aguja.setHeight(75);
        Control.setPosition(9*stage.getViewport().getWorldWidth()/10, 260,Align.bottomRight);
        Control.setWidth(75);
        Control.setHeight(75);
        
        
        LabelTiempoPorJugador=new Label("Escoja el tiempo", skin);
        
        LabelTiempoPorJugador.setPosition(8*stage.getViewport().getWorldWidth()/10, 260+Reloj.getHeight()/3,Align.left);
        TituloTiempo=new Image(skin, "label-bg");
        TituloTiempo.setPosition(LabelTiempoPorJugador.getX()-30, 260+Reloj.getHeight()/3,Align.left);
        TituloTiempo.setWidth(LabelTiempoPorJugador.getWidth()+60);
        TituloTiempo.setHeight(LabelTiempoPorJugador.getHeight()+5);
        
        DesaparicionField=new TextField("10", skin);
        DesaparicionField.setPosition(8*stage.getViewport().getWorldWidth()/10, 400,Align.left);
        Desaparicion=new Label("Jugadas Para aumentar peligro",skin);
        Desaparicion.setPosition(8*stage.getViewport().getWorldWidth()/10-15, 400,Align.right);
        
        //Jugadores=new Table(skin);
        //Jugadores.setPosition(200, 200);
        
        JugadorBlack=new Image(Atlas.findRegion("darkwhite"));
        JugadorBlack.setPosition(75, 300);
        JugadorBlue=new Image(Atlas.findRegion("cblue"));
        JugadorBlue.setPosition(75,225);
        JugadorGreen=new Image(Atlas.findRegion("cgreen"));
        JugadorGreen.setPosition(75,150);
        JugadorPurple=new Image(Atlas.findRegion("cpurple"));
        JugadorPurple.setPosition(75,75);
        
        HumanoW=new CheckBox("Humano", skin);        
        IAW=new CheckBox("IA", skin);        
        RemotoW=new CheckBox("Remoto",skin);
        HumanoB=new CheckBox("Humano", skin);
        IAB=new CheckBox("IA", skin);
        RemotoB=new CheckBox("Remoto",skin);        
        HumanoP=new CheckBox("Humano", skin);
        IAP=new CheckBox("IA", skin);
        RemotoP=new CheckBox("Remoto",skin);
        HumanoG=new CheckBox("Humano", skin);
        IAG=new CheckBox("IA", skin);
        RemotoG=new CheckBox("Remoto",skin);
        
        //Posiciones
        HumanoW.setPosition(150f,300f);
        IAW.setPosition(275+JugadorBlack.getImageWidth(),300);
        RemotoW.setPosition(350, 300);
        HumanoB.setPosition(150f,225f);
	IAB.setPosition(275+JugadorBlack.getImageWidth(),225);
	RemotoB.setPosition(350, 225);
        HumanoP.setPosition(150f,150f);
        IAP.setPosition(275+JugadorBlack.getImageWidth(),150);
        RemotoP.setPosition(350, 150);
        
        TipoBlack=new ButtonGroup();
        TipoBlack.add(HumanoW,IAW,RemotoW);
        TipoBlue=new ButtonGroup();
        TipoBlue.add(HumanoB,IAB,RemotoB);
        TipoPurple=new ButtonGroup();
        TipoPurple.add(HumanoP,IAP,RemotoP);
        TipoGreen=new ButtonGroup();
        TipoGreen.add(HumanoG,IAG,RemotoG);
        HumanoG.setPosition(150f,75f);
	IAG.setPosition(275+JugadorBlack.getImageWidth(),75);
	RemotoG.setPosition(350, 75);
        
        Icono=new Image(Atlas.findRegion("icono"));
        Icono.setPosition(25,400,Align.bottomLeft);
        
        Titulo=new Image(Atlas.findRegion("titulo_grande"));
        Titulo.setPosition(stage.getViewport().getWorldWidth(), 550,Align.bottomRight);
        
        stage.addActor(TiempoPorJugador);
        stage.addActor(LabelMinutos);
        stage.addActor(Reloj);
        stage.addActor(Control);
        stage.addActor(Aguja);
        stage.addActor(TituloTiempo);
        stage.addActor(LabelTiempoPorJugador);
        stage.addActor(DesaparicionField);
        stage.addActor(Desaparicion);
        stage.addActor(JugadorBlack);
        stage.addActor(JugadorBlue);
        stage.addActor(JugadorPurple);
        stage.addActor(JugadorGreen);
        stage.addActor(HumanoW);
        stage.addActor(IAW);
        stage.addActor(RemotoW);
                stage.addActor(HumanoB);
        stage.addActor(IAB);
        stage.addActor(RemotoB);
                stage.addActor(HumanoP);
        stage.addActor(IAP);
        stage.addActor(RemotoP);
                stage.addActor(HumanoG);
        stage.addActor(IAG);
        stage.addActor(RemotoG);
        stage.addActor(Icono);
        stage.addActor(Titulo);
 
        //stage.addActor(Jugadores);
        
                
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(222/255f,184/255f,135/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.draw(); //To change body of generated methods, choose Tools | Templates.
    }


    
}

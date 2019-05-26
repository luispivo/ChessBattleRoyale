/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pddm.game.ChessBattleRoyaleGame;

/**
 *
 * @author Luis
 */
public class MenuScreen extends PlantillaScreen{
    
    Stage stage;
    ImageButton IniciarPartidaButton;
    Slider TiempoPorJugador;
    TextureAtlas Atlas;
    
    public MenuScreen(ChessBattleRoyaleGame game) {
        super(game);
        stage=new Stage(game.Viewport);
        Gdx.input.setInputProcessor(stage);
        Atlas=game.getAssetManager().get("ChessBattleRoyale_assets.atlas");
        Skin skin=new Skin(Atlas);
        IniciarPartidaButton=new ImageButton(skin.getDrawable("buttonAceptar"));
        IniciarPartidaButton.setPosition(stage.getViewport().getWorldWidth(), 50,Align.bottomRight);
        IniciarPartidaButton.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button); //To change body of generated methods, choose Tools | Templates.
                game.setScreen(new PartidaScreen(game));
            }        
        });
        stage.addActor(IniciarPartidaButton);
        SliderStyle sl=new SliderStyle(skin.getDrawable("slider-horizontal"),skin.getDrawable("slider-knob-horizontal"));
        TiempoPorJugador = new Slider (1, 120, 1, false,sl);
        TiempoPorJugador.setPosition(stage.getViewport().getWorldWidth(), 100,Align.bottomRight );
        stage.addActor(TiempoPorJugador);

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

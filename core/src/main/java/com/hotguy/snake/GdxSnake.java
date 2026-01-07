package com.hotguy.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GdxSnake extends ApplicationAdapter {
    private float anchoPantalla, altoPantalla;
    private float anchoSys, altoSys;

    private SpriteBatch miSB;
    private Texture imgInicio, imgJuego, imgMuerto;

    private enum Pantallas {INICIO, JUEGO, MUERTO}

    private Pantallas misPantallas;

    float ratonX, ratonY; // Posicion del Raton al Clikar
    float cabezaX, cabezaY; // Posicion de la Cabeza de la Serpiente
    float difX, difY; // Diferencia entre ambas Posiciones
    private float lmtMinX, lmtMaxX, lmtMinY, lmtMaxY;
    private Serpiente miCuerpo;
    private int iContador;

    private Music mMusica, mMuerto;
    private boolean bMusicOnOFF;

    private OrthographicCamera camera;

    protected PanelNumeros letrero;

    @Override
    public void create() {
        anchoPantalla = 1000;
        altoPantalla = 600;
        anchoSys = Gdx.graphics.getWidth();
        altoSys = Gdx.graphics.getHeight();


        miSB = new SpriteBatch();
        imgInicio = new Texture("pantallaInicio.png");
        imgJuego = new Texture("pantallaJuego.png");
        imgMuerto = new Texture("pantallaMuerto.png");
        misPantallas = Pantallas.INICIO;

        lmtMinX = 20;
        lmtMaxX = anchoPantalla - 20;
        lmtMinY = 20;
        lmtMaxY = altoPantalla - 160;
        miCuerpo = new Serpiente();
        iContador = 0;

        mMusica = Gdx.audio.newMusic(Gdx.files.internal("AxelF.ogg"));
        mMusica.setLooping(true);
        mMuerto = Gdx.audio.newMusic(Gdx.files.internal("MarioMuerto.ogg"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, anchoPantalla, altoPantalla);
//        camera.setToOrtho(false, AnchoPantSys, AltoPantSys);

    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        miSB.setProjectionMatrix(camera.combined);
        miSB.begin();
        miSB.end();
        switch (misPantallas) {
            case INICIO:
                pantallaInicio();
                break;
            case JUEGO:
                pantallaJuego();
                break;
            case MUERTO:
                pantallaMuerto();
                break;
        }
    }

    //Métodos de trabajo de cada una de las pantallas
    private void pantallaInicio() {
        // Pintar Pantalla de Inicio
        miSB.begin();
        miSB.draw(imgInicio, 0, 0, anchoPantalla, altoPantalla);
        letrero = new PanelNumeros(450f, 470f, 90f);
        letrero.setData(0);
        miSB.end();

        if (Gdx.input.justTouched()) {
            misPantallas = Pantallas.JUEGO;
            miCuerpo = new Serpiente();
        }
    }

    private void pantallaJuego() {
        // Pintar Pantalla de Juego
        miSB.begin();
        miSB.draw(imgJuego, 0, 0, anchoPantalla, altoPantalla);
        letrero.pintarse(miSB);
        miSB.end();
        if (!bMusicOnOFF) {
            mMusica.play();
            bMusicOnOFF = true;
        }

        // Cuando nos tocan
        if (Gdx.input.justTouched()) {

            // Sabemos donde han tocado
            ratonX = Math.round(Gdx.input.getX() * (anchoPantalla / anchoSys)); // Ancho
            ratonY = Math.round((altoPantalla - Gdx.input.getY()) * (altoPantalla / altoSys)); // Invertir coordenadas.... // Alto

            cabezaX = miCuerpo.getCabezaX();
            cabezaY = miCuerpo.getCabezaY();
            difX = Math.abs(ratonX - cabezaX);
            difY = Math.abs(ratonY - cabezaY);

            if (difX > difY) { // En el Eje X
                if (ratonX > cabezaX) { // Derecha
                    miCuerpo.cambioDireccion(Cuadrado.Direccion.DERECHA);
                } else { // Izquierda
                    miCuerpo.cambioDireccion(Cuadrado.Direccion.IZQUIERDA);
                }
            } else { // En el eje Y
                if (ratonY > cabezaY) { // Arriba
                    miCuerpo.cambioDireccion(Cuadrado.Direccion.ARRIBA);
                } else { // Abajo
                    miCuerpo.cambioDireccion(Cuadrado.Direccion.ABAJO);
                }
            }
        }
        iContador++;

        // Configurar Estados
        if (miCuerpo.Colision() || !miCuerpo.estaDentro(lmtMinX, lmtMaxX, lmtMinY, lmtMaxY)) {
            // Aqui tiene que haber sangreeeeeee
            mMusica.stop();
            bMusicOnOFF = false;
            misPantallas = Pantallas.MUERTO;
            mMuerto.play();
        } else {
            if (iContador % 15 == 0) {
                miCuerpo.Mover();
            } else if (iContador == 59) {
                miCuerpo.Crecer();
                iContador = 0;
                letrero.incrementa(1);
            }
        }

        // Pintamos la Boa
        miCuerpo.render(miSB);
    }

    private void pantallaMuerto() {
        // Pintar Pantalla de Muerto
        miSB.begin();
        miSB.draw(imgMuerto, 0, 0, anchoPantalla, altoPantalla);
        miSB.end();
        if (Gdx.input.justTouched()) {
            mMuerto.stop();
            misPantallas = Pantallas.INICIO;
        }
    }

    @Override
    public void dispose() {
        miSB.dispose();
        imgInicio.dispose();
        imgJuego.dispose();
        imgMuerto.dispose();
        miCuerpo.dispose();
        mMusica.dispose();
        mMuerto.dispose();
        letrero.dispose();
    }
}

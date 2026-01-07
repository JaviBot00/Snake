package com.hotguy.snake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cuadrado {

    //ATRIBUTOS
    public enum Direccion {ARRIBA, ABAJO, IZQUIERDA, DERECHA}

    protected Texture img;
    protected float posX, posY;
    protected int lado;

    // CONSTRUCTORES
    public Cuadrado(float newX, float newY, int newLado, Texture Newimg) {
        posX = newX;
        posY = newY;
        lado = newLado;
        img = Newimg;
    }

    public Cuadrado(Cuadrado aux) {
        posX = aux.getPosX();
        posY = aux.getPosY();
        lado = aux.getLado();
        img = aux.getImg();
    }

    // Pintame
    public void render(SpriteBatch miLienzo) {
        miLienzo.begin();
        miLienzo.draw(img, posX, posY, lado, lado);
        miLienzo.end();
    }

    // Comportamiento
    public void Moverse(Direccion iDir) {
        switch (iDir) {
            case ARRIBA:
                posY += lado;
                break;
            case ABAJO:
                posY -= lado;
                break;
            case IZQUIERDA:
                posX -= lado;
                break;
            case DERECHA:
                posX += lado;
                break;
        }
    }

    public boolean Colision(Cuadrado aux) {
        return (posX == aux.getPosX() && posY == aux.getPosY());
    }

    // Getters & Setters
    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public int getLado() {
        return lado;
    }

    public Texture getImg() {
        return img;
    }
}

package com.hotguy.snake.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Square {

    //ATRIBUTOS
    protected float posX, posY;
    protected float lastX, lastY;
    protected float renderX, renderY;
    protected int lado;
    protected Texture img;
    public enum Direction {UP, DOWN, LEFT, RIGHT}

    // CONSTRUCTORES
    public Square(float newX, float newY, int newLado, Texture newImg) {
        posX = newX;
        posY = newY;
        lado = newLado;
        img = newImg;
        lastX = posX;
        lastY = posY;
        renderX = posX;
        renderY = posY;
    }

    public Square(Square aux) {
        posX = aux.getPosX();
        posY = aux.getPosY();
        lado = aux.getLado();
        img = aux.getImg();
        lastX = posX;
        lastY = posY;
        renderX = posX;
        renderY = posY;
    }

    public void saveLast() {
        lastX = posX;
        lastY = posY;
    }

    public void interpolate(float alpha) {
        renderX = lastX + (posX - lastX) * alpha;
        renderY = lastY + (posY - lastY) * alpha;
    }

    // Pintame
    public void render(SpriteBatch batch) {
//        batch.begin();
        batch.draw(img, renderX, renderY, lado, lado);
//        batch.end();
    }

    // Comportamiento
    public void move(Direction iDir) {
        switch (iDir) {
            case UP:
                posY += lado;
                break;
            case DOWN:
                posY -= lado;
                break;
            case LEFT:
                posX -= lado;
                break;
            case RIGHT:
                posX += lado;
                break;
        }
    }

    public boolean isCrashed(Square aux) {
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

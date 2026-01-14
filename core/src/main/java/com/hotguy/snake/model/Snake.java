package com.hotguy.snake.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hotguy.snake.SettingManager;

import java.util.ArrayList;

public class Snake {

    // ATRIBUTOS
    protected AssetManager assets;
    protected Texture imgHead;
    protected Texture imgBody;
    protected ArrayList<Square> myBody;
    protected Square.Direction myDirection;

    private boolean growPending = false;
    private float moveTimer = 1;
    private final float moveDelay = 35f;   // velocidad

    // CONSTRUCTOR
    public Snake() {
        if (assets == null) {
            assets = new AssetManager();
            assets.load(SettingManager.IMG_HEAD, Texture.class);
            assets.load(SettingManager.IMG_BODY, Texture.class);
            assets.finishLoading();
        }

        imgHead = assets.get(SettingManager.IMG_HEAD);
        imgBody = assets.get(SettingManager.IMG_BODY);

        myBody = new ArrayList<>();
        Square aux = new Square(SettingManager.START_POS_X, SettingManager.START_POS_Y, SettingManager.SIZE_BODY, imgHead);
        myBody.add(aux);
//        int r = (int) (Math.random() * 4);
        switch ((int) (Math.random() * 4)) {
            case (0):
                myDirection = Square.Direction.UP;
                break;
            case (1):
                myDirection = Square.Direction.DOWN;
                break;
            case (2):
                myDirection = Square.Direction.LEFT;
                break;
            case (3):
                myDirection = Square.Direction.RIGHT;
                break;
        }
//        myDirection = Square.UP;
        this.growUp();
    }

    // Pintame
    public void render(SpriteBatch batch) {
        for (Square aux : myBody) {
            aux.render(batch);
        }
    }

//    // Comportamiento
//    public void Mover() {
//        // Y si crezco y elimino el ultimo??
//        this.Crecer();
//        miCuerpo.remove(miCuerpo.size() - 1);
//    }

    public void update(float delta) {
        moveTimer += delta;

        if (moveTimer >= moveDelay) {
            moveTimer -= moveDelay;
            moveStep();
        }

        float alpha = moveTimer / moveDelay;
        alpha = Math.min(alpha, 1);

        for (Square c : myBody)
            c.interpolate(alpha);
    }

    private void moveStep() {
        for (Square c : myBody)
            c.saveLast();

        for (int i = myBody.size() - 1; i > 0; i--) {
            Square previousSquare = myBody.get(i - 1);
            Square actual = myBody.get(i);

            actual.posX = previousSquare.getPosX();
            actual.posY = previousSquare.getPosY();
        }

        myBody.get(0).move(myDirection);

        if (growPending) {
            growUp();
            growPending = false;
        }
    }

//    public void Crecer() {
//        // Para crecer, creo un nuevo cuadrado como en el constructor,
//        // lo muevo, y lo añado como nueva cabezaa
//        Cuadrado nuevaCabeza = new Cuadrado(miCuerpo.get(0));
//        nuevaCabeza.Moverse(miDireccion);
//        miCuerpo.add(0, nuevaCabeza);
//
//        Cuadrado nuevaCuerpo = new Cuadrado(miCuerpo.get(1).getPosX(), miCuerpo.get(1).getPosY(), miCuerpo.get(1).getLado(), imgCuerpo);
//        miCuerpo.remove(1);
//        miCuerpo.add(1, nuevaCuerpo);
//    }

    public void growUp() {
        Square tail = myBody.get(myBody.size() - 1);

        Square newPart = new Square(
            tail.lastX,
            tail.lastY,
            tail.getLado(),
            imgBody
        );

        myBody.add(newPart);
    }

//    public void cambioDireccion(Cuadrado.Direccion iNuevaDir) {
//        boolean bNoCambiar;
//        bNoCambiar = (miDireccion == Cuadrado.Direccion.ARRIBA && iNuevaDir == Cuadrado.Direccion.ABAJO);
//        bNoCambiar = bNoCambiar || (miDireccion == Cuadrado.Direccion.ABAJO && iNuevaDir == Cuadrado.Direccion.ARRIBA);
//        bNoCambiar = bNoCambiar || (miDireccion == Cuadrado.Direccion.IZQUIERDA && iNuevaDir == Cuadrado.Direccion.DERECHA);
//        bNoCambiar = bNoCambiar || (miDireccion == Cuadrado.Direccion.DERECHA && iNuevaDir == Cuadrado.Direccion.IZQUIERDA);
//        if (!bNoCambiar) {
//            miDireccion = iNuevaDir;
//        }
//    }

    public void changeDirection(Square.Direction nd) {
        boolean prohibido =
            (myDirection == Square.Direction.UP && nd == Square.Direction.DOWN) ||
                (myDirection == Square.Direction.DOWN && nd == Square.Direction.UP) ||
                (myDirection == Square.Direction.LEFT && nd == Square.Direction.RIGHT) ||
                (myDirection == Square.Direction.RIGHT && nd == Square.Direction.LEFT);

        if (!prohibido)
            myDirection = nd;
    }

//    public boolean estaDentro(float lmtMinX, float lmtMaxX, float lmtMinY, float lmtMaxY) {
//        return (this.getHeadX() >= lmtMinX &&
//            this.getHeadX() <= lmtMaxX - myBody.get(0).getLado() &&
//            this.getHeadY() >= lmtMinY &&
//            this.getHeadY() <= lmtMaxY - myBody.get(0).getLado());
//    }

    public boolean isInside(float minX, float maxX, float minY, float maxY) {
        Square h = myBody.get(0);
        return h.getPosX() >= minX &&
            h.getPosX() <= maxX - h.getLado() &&
            h.getPosY() >= minY &&
            h.getPosY() <= maxY - h.getLado();
    }

    public boolean isCrashed() {
        Square head = myBody.get(0);
        for (int i = 4; i < myBody.size(); i++) {
            if (myBody.get(i).isCrashed(head))
                return true;
        }
        return false;
    }

    public void requestGrow() {
        growPending = true;
    }

    public void dispose() {
//        if (imgHead != null) imgHead.dispose();
//        if (imgBody != null) imgBody.dispose();
        assets.dispose();
    }

    // Getters & Setters
    public float getHeadX() {
        return myBody.get(0).getPosX();
    }

    public float getHeadY() {
        return myBody.get(0).getPosY();
    }

    public int getSizeBody() {
        return myBody.size();
    }

}

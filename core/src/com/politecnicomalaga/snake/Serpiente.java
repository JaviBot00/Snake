package com.politecnicomalaga.snake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Serpiente {

    // ATRIBUTOS
    protected Texture imgCabeza = new Texture("imgCabeza.png");
    protected Texture imgCuerpo = new Texture("imgCuerpo.png");
    protected ArrayList<Cuadrado> miCuerpo;
    protected Cuadrado.Direccion miDireccion;

    // CONSTRUCTOR
    public Serpiente() {
        miCuerpo = new ArrayList<>();
        Cuadrado aux = new Cuadrado(500.0f, 200.0f, 20, imgCabeza);
        miCuerpo.add(aux);
        int iRandom = (int) (Math.random() * 3);
        switch (iRandom) {
            case (0):
                miDireccion = Cuadrado.Direccion.ARRIBA;
                break;
            case (1):
                miDireccion = Cuadrado.Direccion.ABAJO;
                break;
            case (2):
                miDireccion = Cuadrado.Direccion.IZQUIERDA;
                break;
            case (3):
                miDireccion = Cuadrado.Direccion.DERECHA;
                break;
        }
//        miDireccion = Cuadrado.ARRIBA;
    }

    // Pintame
    public void render(SpriteBatch miSB) {
        for (Cuadrado aux : miCuerpo) {
            aux.render(miSB);
        }
    }

    // Comportamiento
    public void Mover() {
        // Y si crezco y elimino el ultimo??
        this.Crecer();
        miCuerpo.remove(miCuerpo.size() - 1);
    }

    public void Crecer() {
        // Para crecer, creo un nuevo cuadrado como en el constructor,
        // lo muevo, y lo añado como nueva cabezaa
        Cuadrado nuevaCabeza = new Cuadrado(miCuerpo.get(0));
        nuevaCabeza.Moverse(miDireccion);
        miCuerpo.add(0, nuevaCabeza);

        Cuadrado nuevaCuerpo = new Cuadrado(miCuerpo.get(1).getPosX(), miCuerpo.get(1).getPosY(), miCuerpo.get(1).getLado(), imgCuerpo);
        miCuerpo.remove(1);
        miCuerpo.add(1, nuevaCuerpo);
    }

    public void cambioDireccion(Cuadrado.Direccion iNuevaDir) {
        boolean bNoCambiar;
        bNoCambiar = (miDireccion == Cuadrado.Direccion.ARRIBA && iNuevaDir == Cuadrado.Direccion.ABAJO);
        bNoCambiar = bNoCambiar || (miDireccion == Cuadrado.Direccion.ABAJO && iNuevaDir == Cuadrado.Direccion.ARRIBA);
        bNoCambiar = bNoCambiar || (miDireccion == Cuadrado.Direccion.IZQUIERDA && iNuevaDir == Cuadrado.Direccion.DERECHA);
        bNoCambiar = bNoCambiar || (miDireccion == Cuadrado.Direccion.DERECHA && iNuevaDir == Cuadrado.Direccion.IZQUIERDA);
        if (!bNoCambiar) {
            miDireccion = iNuevaDir;
        }
    }

    public boolean estaDentro(float lmtMinX, float lmtMaxX, float lmtMinY, float lmtMaxY) {
        return (this.getCabezaX() >= lmtMinX &&
                this.getCabezaX() <= lmtMaxX - miCuerpo.get(0).getLado() &&
                this.getCabezaY() >= lmtMinY &&
                this.getCabezaY() <= lmtMaxY - miCuerpo.get(0).getLado());
    }

    public boolean Colision() {
        Cuadrado aux = miCuerpo.get(0);
        for (int i = 4; i < miCuerpo.size(); i++) {
            if (miCuerpo.get(i).Colision(aux)) {
                return true;
            }
        }
        return false;
    }

    public void dispose() {
        if (imgCabeza != null) imgCabeza.dispose();
        if (imgCuerpo != null) imgCuerpo.dispose();
    }

    // Getters & Setters
    public float getCabezaX() {
        return miCuerpo.get(0).getPosX();
    }

    public float getCabezaY() {
        return miCuerpo.get(0).getPosY();
    }

    public int getTamañoCuerpo() {
        return miCuerpo.size();
    }

}

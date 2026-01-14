package com.hotguy.snake;

import com.badlogic.gdx.Game;
import com.hotguy.snake.view.MenuScreen;

public class GdxSnake extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}

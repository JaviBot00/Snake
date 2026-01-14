package com.hotguy.snake.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.hotguy.snake.GdxSnake;
import com.hotguy.snake.SettingManager;

public class DeadScreen implements Screen {

    private final GdxSnake game;

    private final SpriteBatch batch;
    private final Texture imgDead;
    private final OrthographicCamera camera;

    private final Music mDead;

    public DeadScreen(GdxSnake game) {
        this.game = game;

        batch = new SpriteBatch();
        imgDead = new Texture(SettingManager.SCREEN_DEAD);

        mDead = Gdx.audio.newMusic(Gdx.files.internal(SettingManager.MUSIC_DEAD));
        mDead.play();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SettingManager.SCREEN_WIDTH, SettingManager.SCREEN_HEIGHT);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(imgDead, 0, 0, SettingManager.SCREEN_WIDTH, SettingManager.SCREEN_HEIGHT);
        batch.end();

        if (Gdx.input.justTouched()) {
            mDead.stop();
            game.setScreen(new MenuScreen(game));
            dispose();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        imgDead.dispose();
        mDead.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}

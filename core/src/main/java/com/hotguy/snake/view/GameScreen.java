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
import com.hotguy.snake.model.Square;
import com.hotguy.snake.model.Snake;

public class GameScreen implements Screen {

    private final GdxSnake game;

    private final SpriteBatch batch;
    private final Texture background;
    private final OrthographicCamera camera;

    private final Snake snake;
    private final float minX = 20, maxX = 980;
    private final float minY = 20, maxY = 440;
    private final Music music;
    private int frameCount = 0;
    private boolean isMusicPlaying = false;

    public GameScreen(GdxSnake game) {
        this.game = game;

        batch = new SpriteBatch();
        background = new Texture(SettingManager.SCREEN_GAME);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SettingManager.SCREEN_WIDTH, SettingManager.SCREEN_HEIGHT);

        snake = new Snake();

        music = Gdx.audio.newMusic(Gdx.files.internal(SettingManager.MUSIC_GAME));
        music.setLooping(true);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        if (!isMusicPlaying) {
            music.play();
            isMusicPlaying = true;
        }

        handleInput();
        frameCount++;

        if (snake.isCrashed() || !snake.isInside(minX, maxX, minY, maxY)) {
            music.stop();
            game.setScreen(new DeadScreen(game));
            dispose();
            return;
        }

//        if (frameCount % 15 == 0) {
//            snake.update(2);
//        } else if (frameCount == 59) {
//            snake.growUp();
//            frameCount = 0;
//        }

        snake.update(2);
        if (frameCount == 59) {
            snake.requestGrow();
            frameCount = 0;
        }

        batch.begin();
        batch.draw(background, 0, 0, SettingManager.SCREEN_WIDTH, SettingManager.SCREEN_HEIGHT);
        snake.render(batch);
        batch.end();
    }

    private void handleInput() {

        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = SettingManager.SCREEN_HEIGHT - Gdx.input.getY();

            float cx = snake.getHeadX();
            float cy = snake.getHeadY();

            float dx = Math.abs(x - cx);
            float dy = Math.abs(y - cy);

            if (dx > dy) {
                if (x > cx) snake.changeDirection(Square.Direction.RIGHT);
                else snake.changeDirection(Square.Direction.LEFT);
            } else {
                if (y > cy) snake.changeDirection(Square.Direction.UP);
                else snake.changeDirection(Square.Direction.DOWN);
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        snake.dispose();
        music.dispose();
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

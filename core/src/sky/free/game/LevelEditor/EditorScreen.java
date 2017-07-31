package sky.free.game.LevelEditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import sky.free.game.Gameplay.Level;
import sky.free.game.LabyrinthGame;

/**
 * Created by denis on 31.07.17.
 */

public class EditorScreen implements Screen {
    Level level;
    LabyrinthGame gam;

    public EditorScreen(LabyrinthGame g,Level l){
        gam = g;
        level = l;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    @Override
    public void dispose() {

    }
}

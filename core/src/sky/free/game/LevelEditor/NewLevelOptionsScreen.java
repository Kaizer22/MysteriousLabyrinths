package sky.free.game.LevelEditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


import sky.free.game.Button;
import sky.free.game.Gameplay.Level;
import sky.free.game.LabyrinthGame;

/**
 * Created by denis on 31.07.17.
 */

public class NewLevelOptionsScreen implements Screen, InputProcessor {
    LabyrinthGame gam;

    int levelNumber;
    int levelSizeX;
    int levelSizeY;

    private OrthographicCamera camera;

    float scrH;
    float buttonHeight;
    float buttonWidth;

    private Button createLevelButton;
    private Button openLevelButton;
    private Button deleteLevelButton;
    private Button clearDBButton;


    public NewLevelOptionsScreen(final LabyrinthGame game){
        gam = game;
        camera = new OrthographicCamera();
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void show() {
        Input.TextInputListener numberInputListener = new Input.TextInputListener() {
            @Override
            public void input(String text) {
                levelNumber =  Integer.getInteger(text);
            }

            @Override
            public void canceled() {

            }
        };
        Input.TextInputListener xInputListener = new Input.TextInputListener() {
            @Override
            public void input(String text) {
                levelSizeX =  Integer.getInteger(text);
            }

            @Override
            public void canceled() {

            }
        };
        Input.TextInputListener yInputListener = new Input.TextInputListener() {
            @Override
            public void input(String text) {
                levelSizeY =  Integer.getInteger(text);
            }
            @Override
            public void canceled() {

            }
        };

        Gdx.input.getTextInput(yInputListener,"Y","","");
        Gdx.input.getTextInput(xInputListener,"X","","");
        Gdx.input.getTextInput(numberInputListener,"NUMBER OF LEVEL","","");

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        gam.batch.setProjectionMatrix(camera.combined);

        gam.batch.begin();
            gam.batch.draw(createLevelButton.currentTexture,createLevelButton.checkZone.x,createLevelButton.checkZone.y,buttonWidth,buttonHeight);
            gam.batch.draw(openLevelButton.currentTexture,openLevelButton.checkZone.x,openLevelButton.checkZone.y,buttonWidth,buttonHeight);
            gam.batch.draw(deleteLevelButton.currentTexture,deleteLevelButton.checkZone.x,deleteLevelButton.checkZone.y,buttonWidth,buttonHeight);
            gam.batch.draw(clearDBButton.currentTexture,clearDBButton.checkZone.x,clearDBButton.checkZone.y,buttonWidth,buttonHeight);
        gam.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false,width,height);

        buttonHeight = height/4;
        buttonWidth = width/3;

        scrH = height;

        Texture cLB = new Texture("editor/create.png");
        Texture cLBPressed = new Texture("editor/create_pressed.png");
        Rectangle r1 = new Rectangle(width/2-buttonWidth/2,buttonHeight*3,buttonWidth,buttonHeight);
        createLevelButton = new Button(r1,cLB,cLBPressed) {
            @Override
            public void makeAction() {
                super.makeAction();
                Level level = new Level(levelSizeX,levelSizeY,levelNumber);
                gam.setScreen(new EditorScreen(gam,level));
            }
        };

        Texture oLB = new Texture("editor/open.png");
        Texture oLBPressed = new Texture("editor/open_pressed.png");
        Rectangle r2 = new Rectangle(width/2-buttonWidth/2,buttonHeight*2,buttonWidth,buttonHeight);
        openLevelButton = new Button(r2,oLB,oLBPressed) {
            @Override
            public void makeAction() {
                super.makeAction();
                Level level = gam.lDB.getLevel(levelNumber);
                gam.setScreen(new EditorScreen(gam,level));
            }
        };

        Texture dLB = new Texture("editor/delete.png");
        Texture dLBPressed = new Texture("editor/delete_pressed.png");
        Rectangle r3 = new Rectangle(width/2-buttonWidth/2,buttonHeight,buttonWidth,buttonHeight);
        deleteLevelButton = new Button(r3,dLB,dLBPressed) {
            @Override
            public void makeAction() {
                super.makeAction();
                gam.lDB.deleteLevel(levelNumber);
            }
        };

        Texture clLB = new Texture("editor/clear.png");
        Texture clLBPressed = new Texture("editor/clear_pressed.png");
        Rectangle r4 = new Rectangle(width/2-buttonWidth/2,0,buttonWidth,buttonHeight);
        clearDBButton = new Button(r4,clLB,clLBPressed) {
            @Override
            public void makeAction() {
                super.makeAction();
                gam.lDB.clearDatabase();
            }
        };
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
        createLevelButton.disposeButton();
        clearDBButton.disposeButton();
        openLevelButton.disposeButton();
        deleteLevelButton.disposeButton();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (createLevelButton.checkZone.contains(screenX,scrH-screenY)) {
            createLevelButton.currentTexture = createLevelButton.texturePressed;

        }
        else if (deleteLevelButton.checkZone.contains(screenX,scrH-screenY)) {
            deleteLevelButton.currentTexture = deleteLevelButton.texturePressed;
        }
        else if (clearDBButton.checkZone.contains(screenX,scrH-screenY)) {
            clearDBButton.currentTexture = clearDBButton.texturePressed;
        }
        else if (openLevelButton.checkZone.contains(screenX,scrH-screenY)) {
            openLevelButton.currentTexture = openLevelButton.texturePressed;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        createLevelButton.currentTexture = createLevelButton.texture;
        openLevelButton.currentTexture = openLevelButton.texture;
        deleteLevelButton.currentTexture = deleteLevelButton.texture;
        clearDBButton.currentTexture = clearDBButton.texture;

        if (createLevelButton.checkZone.contains(screenX,scrH-screenY)) {
            createLevelButton.makeAction();
        }if (openLevelButton.checkZone.contains(screenX,scrH-screenY)) {
            openLevelButton.makeAction();
        }if (deleteLevelButton.checkZone.contains(screenX,scrH-screenY)) {
            deleteLevelButton.makeAction();
        }if (clearDBButton.checkZone.contains(screenX,scrH-screenY)) {
            clearDBButton.makeAction();
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

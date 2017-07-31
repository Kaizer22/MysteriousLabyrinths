package sky.free.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import sky.free.game.LevelEditor.NewLevelOptionsScreen;


/**
 * Created by denis on 19.06.17.
 */

public  class MenuScreen implements Screen, InputProcessor  {

    final boolean developerModeOn = true;  //TODO отключить в релизной версии

    float deltaMove;

    final LabyrinthGame gam;

    private OrthographicCamera camera;

    private int scrW;
    private int scrH;

    private Animation bgAnim;
    private TextureRegion currentBgFrame;
    private float stateTime;

    private boolean isButtonsReady;

    private Button buttonPlay;
    private Button buttonExit;
    private Button buttonMusic;
    private Button buttonEditor;


    public MenuScreen(final LabyrinthGame game){
        gam = game;
        camera = new OrthographicCamera();
        Gdx.input.setInputProcessor(this);



        int bg_anim_num_frames = 9;
        String bg_anim_directory = "main_menu_bg/";
        float bg_anim_frame_duration = 0.15f;
        bgAnim = Animator.initAnim(bg_anim_num_frames,bg_anim_directory,bg_anim_frame_duration);

        stateTime = 0f;
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        gam.batchUI.setProjectionMatrix(camera.combined);

        stateTime += Gdx.graphics.getDeltaTime();

        getCurrentFrames();
        //______________________________________________
        gam.batchUI.begin();

        if (isButtonsReady)
           drawMenu();
        else
           drawButtonComing();
        gam.batchUI.end();
        //______________________________________________


    }


    //*********************************************
    private void getCurrentFrames(){
        currentBgFrame = (TextureRegion) bgAnim.getKeyFrame(stateTime,true);

    }
    private void drawMenu(){
        gam.batchUI.draw(currentBgFrame,0,0,scrW,scrH);

        gam.batchUI.draw(buttonPlay.currentTexture,buttonPlay.checkZone.x, buttonPlay.checkZone.y, buttonPlay.checkZone.getWidth(), buttonPlay.checkZone.getHeight());
        gam.batchUI.draw(buttonExit.currentTexture, buttonExit.checkZone.x, buttonExit.checkZone.y, buttonExit.checkZone.getWidth(), buttonExit.checkZone.getHeight());
        gam.batchUI.draw(buttonMusic.currentTexture, buttonMusic.checkZone.x, buttonMusic.checkZone.y, buttonMusic.checkZone.getWidth(), buttonMusic.checkZone.getHeight());

        if (developerModeOn)
            gam.batchUI.draw(buttonEditor.currentTexture,buttonEditor.checkZone.x, buttonEditor.checkZone.y, buttonEditor.checkZone.getWidth(), buttonEditor.checkZone.getHeight());
    }

    private  void drawButtonComing(){
        gam.batchUI.draw(currentBgFrame,0,0,scrW,scrH);

        gam.batchUI.draw(buttonPlay.currentTexture,buttonPlay.checkZone.x, buttonPlay.checkZone.y, buttonPlay.checkZone.getWidth(), buttonPlay.checkZone.getHeight());
        gam.batchUI.draw(buttonExit.currentTexture, buttonExit.checkZone.x, buttonExit.checkZone.y, buttonExit.checkZone.getWidth(), buttonExit.checkZone.getHeight());
        gam.batchUI.draw(buttonMusic.currentTexture, buttonMusic.checkZone.x, buttonMusic.checkZone.y, buttonMusic.checkZone.getWidth(), buttonMusic.checkZone.getHeight());

        if (buttonPlay.checkZone.x > scrW/4*3-buttonPlay.checkZone.getWidth()/2){
            buttonPlay.checkZone.x-= deltaMove;

        }else
            isButtonsReady = true;

        if (buttonExit.checkZone.x > scrW/4*3-buttonExit.checkZone.getWidth()/2){
            buttonExit.checkZone.x-=deltaMove;
        }

        if (buttonMusic.checkZone.x< 10){
            buttonMusic.checkZone.x += deltaMove / 3;
        }


    }


    //*********************************************

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (buttonPlay.checkZone.contains((float)screenX,(float)scrH-screenY)){
            buttonPlay.currentTexture = buttonPlay.texturePressed;

        }else if (buttonExit.checkZone.contains((float)screenX,(float)scrH-screenY)){
            buttonExit.currentTexture = buttonExit.texturePressed;

        }else if (buttonMusic.checkZone.contains((float)screenX,(float)scrH-screenY)){
            if (buttonMusic.isActivated)
                buttonMusic.currentTexture = buttonMusic.texturePressedCon1;
            else
                buttonMusic.currentTexture = buttonMusic.texturePressed;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        buttonPlay.currentTexture = buttonPlay.texture;
        buttonExit.currentTexture = buttonExit.texture;

        if (buttonMusic.isActivated)
            buttonMusic.currentTexture = buttonMusic.textureCon1;
        else
            buttonMusic.currentTexture = buttonMusic.texture;


        if (buttonPlay.checkZone.contains((float)screenX,(float)scrH-screenY)){
            buttonPlay.makeAction();
        }
        else if (buttonExit.checkZone.contains((float)screenX,(float)scrH-screenY)){
            buttonExit.makeAction();

        }
        else if (buttonMusic.checkZone.contains((float)screenX,(float)scrH- screenY)){
            buttonMusic.makeAction();

        }else if (buttonEditor.checkZone.contains((float)screenX,(float)scrH- screenY) && developerModeOn){
            buttonEditor.makeAction();

        }


        return true;
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false,width,height);

        scrH = height;
        scrW = width;

        deltaMove = scrH/200;

        Rectangle r1 = new Rectangle(width,height/2,width/4,height/6);
        Rectangle r2 = new Rectangle(width,height/2-height/6-10,width/4,height/6);
        Rectangle r3 = new Rectangle(-1*height/9,height/9*8-10,height/9,height/9);
        Rectangle r4 = new Rectangle(0,0,width/5,height/3);

        Texture bP = new Texture("main_menu_bp/button_play.png");
        Texture bPPressed = new Texture("main_menu_bp/button_play_pressed.png");
        buttonPlay = new Button(r1,bP,bPPressed) {
            @Override
            public void makeAction() {
                super.makeAction();
                dispose();
                GameplayScreen sc = new GameplayScreen(gam);
                Gdx.input.setInputProcessor(sc);
                gam.setScreen(sc);
            }
        };



        Texture bE = new Texture("main_menu_be/button_exit.png");
        Texture bEPressed = new Texture("main_menu_be/button_exit_pressed.png");
        buttonExit = new Button(r2,bE,bEPressed) {
            @Override
            public void makeAction() {
                super.makeAction();
                dispose();
                gam.dispose();


            }
        };


        Texture bS = new Texture("main_menu_bs/button_sound.png");
        Texture bSPressed = new Texture("main_menu_bs/button_sound_pressed.png");
        Texture bSCon1 = new Texture("main_menu_bs/button_no_sound.png");
        Texture bSCon1Pressed = new Texture("main_menu_bs/button_no_sound_pressed.png");
        buttonMusic = new Button(r3,bS,bSPressed,bSCon1,bSCon1Pressed) {
            @Override
            public void makeAction() {
                super.makeAction();
                Gdx.app.log("INFO","I AM HERE");
                if (!this.isActivated) {
                    gam.maintheme.stop();

                    this.isActivated = true;
                } else {
                    gam.maintheme.play();
                    this.isActivated = false;
                }
            }
        };

        Texture bEd = new Texture("col.png");
        buttonEditor = new Button(r4,bEd,bEd) {
            @Override
            public void makeAction() {
                super.makeAction();
                gam.setScreen(new NewLevelOptionsScreen(gam));
            }
        };


        Gdx.app.log("INFO",width + " " + height);
    }

    @Override
    public void dispose() {
        buttonPlay.disposeButton();
        buttonExit.disposeButton();
        buttonMusic.disposeButton();
    }





    @Override
    public void pause() {

    }
    @Override
    public void resume() {
        isButtonsReady = false;
        resize(scrW,scrH);
    }
    @Override
    public void hide() {

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

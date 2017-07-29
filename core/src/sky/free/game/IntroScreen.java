package sky.free.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by denis on 12.06.17.
 */

public class IntroScreen implements Screen,InputProcessor {
    final LabyrinthGame gam;

    int condition = 0;

    private Music street_noise;
    private Music spooky;

    private Sound telephone;

    private boolean trigger;



    private OrthographicCamera camera;

    private int scrH;
    private int scrW;
    private int spSize;
    private int spX;
    private int spY;
    private Animation logoAnim;
    private Animation streetAnim;
    private Animation officeAnim;
    private Animation thunderAnim;
    private Animation houseAnim;
    private TextureRegion currentFrame;

    private Texture controlZ;
    private float stateTime;

    public IntroScreen(final LabyrinthGame game){
        gam = game;

        camera = new OrthographicCamera();

        Gdx.input.setInputProcessor(this);

        float logo_anim_frame_duration = 0.5f;
        int logo_anim_num_frames = 23;
        String logo_anim_directory = "intro_logo/";
        logoAnim = Animator.initAnim(logo_anim_num_frames, logo_anim_directory, logo_anim_frame_duration);

        float street_anim_frame_duration = 0.1f;
        int street_anim_num_frames = 6;
        String street_anim_directory = "intro_street/";
        streetAnim = Animator.initAnim(street_anim_num_frames, street_anim_directory, street_anim_frame_duration);

        float office_anim_frame_duration = 0.25f;
        int office_anim_num_frames = 10;
        String office_anim_directory = "intro_office/";
        officeAnim = Animator.initAnim(office_anim_num_frames, office_anim_directory, office_anim_frame_duration);


        float thunder_anim_frame_duration = 0.07f;
        int thunder_anim_num_frames = 8;
        String thunder_anim_directory = "intro_house/thunder/";
        thunderAnim = Animator.initAnim(thunder_anim_num_frames, thunder_anim_directory, thunder_anim_frame_duration);


        float house_anim_frame_duration = 0.333f;
        int house_anim_num_frames = 3;
        String house_anim_directory = "intro_house/";
        houseAnim = Animator.initAnim(house_anim_num_frames, house_anim_directory, house_anim_frame_duration);


        street_noise = Gdx.audio.newMusic(Gdx.files.internal("street_noise.wav"));
        street_noise.setLooping(true);
        spooky = Gdx.audio.newMusic(Gdx.files.internal("spooky.wav"));

        telephone = Gdx.audio.newSound(Gdx.files.internal("phone.wav"));


        controlZ = new Texture("intro_el1.png");

        stateTime = 0f;



    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        gam.batchUI.setProjectionMatrix(camera.combined);

        stateTime += Gdx.graphics.getDeltaTime();

        getCurrentFrames();

//_______________________________________________________________
        gam.batchUI.begin();
        drawIntro();
        gam.batchUI.end();
//_______________________________________________________________




        //Gdx.app.log("MEMORY_USED",Gdx.app.getJavaHeap()+" ");
    }

//*************************************************************
   private void drawIntro(){
       if (condition == 0) {
           gam.batchUI.draw(currentFrame, spX, spY, spSize, spSize);
           gam.font.draw(gam.batchUI, "FREE SKY GAMES presents", spX, spY - gam.font.getCapHeight(), 500, 10, true);
           gam.font.draw(gam.batchUI, "tap anywhere to continue", spX, gam.font.getCapHeight() * 2, 500, 10, true);
       }else if(condition == 1){
           gam.batchUI.draw(currentFrame, spX, spY, spSize, spSize);
       }else
           gam.batchUI.draw(currentFrame, 0, 0, scrW, scrH);
   }


   private void getCurrentFrames(){
       switch (condition){
           case 0:
               currentFrame = (TextureRegion) logoAnim.getKeyFrame(stateTime,true);
               break;
           case 1:
               currentFrame = (TextureRegion) logoAnim.getKeyFrame(stateTime,true);
               spSize-=4;
               spX+=2;
               spY+=2;
               if (spSize<0){
                   condition++;
               }
               break;
           case 2:
               if (trigger){
                   street_noise.play();
                   trigger = false;
               }
               currentFrame = (TextureRegion) streetAnim.getKeyFrame(stateTime,true);

               break;
           case 3:
               if (trigger){
                   street_noise.stop();
                   telephone.play();
                   trigger = false;
               }
               currentFrame = (TextureRegion) officeAnim.getKeyFrame(stateTime,true);
               break;
           case 4:
               if (trigger){
                   telephone.stop();
                   spooky.play();
                   trigger = false;
                   stateTime = 0f;
               }
               currentFrame = (TextureRegion) thunderAnim.getKeyFrame(stateTime,true);

               if (thunderAnim.isAnimationFinished(stateTime))
                   condition++;
               break;
           case 5:
               currentFrame = (TextureRegion) houseAnim.getKeyFrame(stateTime,true);

               break;
           case 6:
               spooky.stop();
               gam.maintheme.play();
               gam.setScreen(new MenuScreen(gam));
               break;
       }

   }

//*************************************************************



    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false,width,height);

        scrH = height;
        scrW = width;
        spSize = height/2;
        spX = width/2- spSize /2;
        spY = height/2- spSize /2;

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
        controlZ.dispose();

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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        condition++;

        trigger = true;
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

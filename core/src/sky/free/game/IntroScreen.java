package sky.free.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by denis on 12.06.17.
 */

public class IntroScreen implements Screen {
    final LabyrinthGame gam;

    private Rectangle cursor;
    private Rectangle controlZone;

    private boolean toNextScreen;
    private boolean isReadyToNext;


    private OrthographicCamera camera;


    private int spSize;
    private int spX;
    private int spY;
    private Animation logoAnim;
    private Animation cursorAnim;
    private TextureRegion currentChFrame;
    private TextureRegion currentCursFrame;
    private Texture controlZ;
    private float stateTime;

    public IntroScreen(final LabyrinthGame game){
        gam = game;

        camera = new OrthographicCamera();

        float logo_anim_frame_duration = 0.5f;
        int logo_anim_num_frames = 23;
        String logo_anim_directory = "intro_logo/";
        logoAnim = Animator.initAnim(logo_anim_num_frames, logo_anim_directory, logo_anim_frame_duration);


        int cursor_anim_num_frames = 5;
        String cursor_anim_directory = "intro_cursor/";
        float cursor_anim_frame_duration = 0.15f;
        cursorAnim = Animator.initAnim(cursor_anim_num_frames, cursor_anim_directory, cursor_anim_frame_duration);

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
        if (toNextScreen){
            drawChanges();
        }else
            drawIntro();
        gam.batchUI.end();
//_______________________________________________________________


        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            cursor.x = touchPos.x - cursor.getWidth() / 2;
            cursor.y = touchPos.y - cursor.getWidth() / 2;
                toNextScreen = true;

        }
        if (isReadyToNext) {
            dispose();
            gam.setScreen(new MenuScreen(gam));
        }
        //Gdx.app.log("MEMORY_USED",Gdx.app.getJavaHeap()+" ");
    }

//*************************************************************
   private void drawIntro(){

       gam.batchUI.draw(currentChFrame,spX,spY, spSize, spSize);
       gam.font.draw(gam.batchUI,"FREE SKY GAMES presents",spX,spY-gam.font.getCapHeight(),500,10,true);
       gam.font.draw(gam.batchUI,"tap anywhere to continue",spX,gam.font.getCapHeight()*2,500,10,true);
       gam.batchUI.draw(currentCursFrame, cursor.x, cursor.y, cursor.getWidth(), cursor.getHeight());

   }

   private void drawChanges(){
       gam.batchUI.draw(currentChFrame,spX,spY, spSize, spSize);
       spSize-=4;
       spX+=2;
       spY+=2;
       if (spSize<0){
           isReadyToNext = true;
       }

   }

   private void getCurrentFrames(){
       currentChFrame = (TextureRegion) logoAnim.getKeyFrame(stateTime, true);
       currentCursFrame  = (TextureRegion) cursorAnim.getKeyFrame(stateTime, true);
   }

//*************************************************************



    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false,width,height);

        spSize = height/2;
        spX = width/2- spSize /2;
        spY = height/2- spSize /2;

        cursor = new Rectangle(0,0,height/5,height/5);
        controlZone = new Rectangle(width-height/5,height-height/5,height/5,height/5);



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
}

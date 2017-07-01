package sky.free.game.Gameplay;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import sky.free.game.Animator;

/**
 * Created by denis on 26.06.17.
 */

public class Player extends Actor {
     // или currentAnimation
    public Rectangle collisionModel;
    public CurrentDirection direction;


    public float x;
    public float y;
    public float playerSize;
    public int playerBlockX;
    public int playerBlockY;

    public boolean isCanGoUP;
    public boolean isCanGoDOWN;
    public boolean isCanGoLEFT;
    public boolean isCanGoRIGHT;

    public Animation moveLeft;
    public Animation moveRight;
    public Animation moveUp;
    public Animation moveDown;

    public Animation standLeft;
    public Animation standRight;
    public Animation standUp;
    public Animation standDown;


    public Player(float X,float Y,float blockSize){

        moveLeft = Animator.initAnim(5,"gameplay/player/walk_left/",0.3f);
        moveRight = Animator.initAnim(5,"gameplay/player/walk_right/",0.3f);
        moveDown = Animator.initAnim(7,"gameplay/player/walk_down/",0.2f);
        moveUp = Animator.initAnim(6,"gameplay/player/walk_up/",0.25f);
        standRight = Animator.initAnim(6,"gameplay/player/stand_right/",0.25f);
        standLeft = Animator.initAnim(6,"gameplay/player/stand_left/",0.25f);
        standDown = Animator.initAnim(6,"gameplay/player/stand_down/",0.25f);
        standUp = Animator.initAnim(5,"gameplay/player/stand_up/",0.3f);

        isCanGoUP = true;
        isCanGoDOWN = true;
        isCanGoLEFT = true;
        isCanGoRIGHT = true;

        x = X;
        y = Y;


        direction = CurrentDirection.RIGHT;
        collisionModel = new Rectangle(x+blockSize/3,y,blockSize/3,blockSize/10*2);
        playerSize = blockSize;
        playerBlockX = (int)(collisionModel.x/playerSize);
        playerBlockY = (int)(collisionModel.y/playerSize);
    }

    public void updateState(){
        isCanGoUP = true;
        isCanGoDOWN = true;
        isCanGoLEFT = true;
        isCanGoRIGHT = true;
    }
    public void setDeltaX(float delta){
        x+=delta;
        collisionModel.x+=delta;
        playerBlockX = (int)(collisionModel.x/playerSize);
    }

    public void setDeltaY(float delta){
        y+=delta;
        collisionModel.y +=delta;
        playerBlockY = (int)(collisionModel.y/playerSize);
    }

    public TextureRegion getCurrentTexture(float stateTime){
        switch (direction){
            case LEFT:
                return (TextureRegion)moveLeft.getKeyFrame(stateTime,true);
            case RIGHT:
                return (TextureRegion)moveRight.getKeyFrame(stateTime,true);
            case DOWN:
                return (TextureRegion)moveDown.getKeyFrame(stateTime,true);
            case UP:
                return (TextureRegion)moveUp.getKeyFrame(stateTime,true);
            case STAND_UP:
                return (TextureRegion)standUp.getKeyFrame(stateTime,true);
            case STAND_DOWN:
                return (TextureRegion)standDown.getKeyFrame(stateTime,true);
            case STAND_LEFT:
                return (TextureRegion)standLeft.getKeyFrame(stateTime,true);
            case STAND_RIGHT:
                return (TextureRegion)standRight.getKeyFrame(stateTime,true);

            default:
                return null;
        }
    }

   // @Override
    //public float getX() {
       // return collisionModel.getX();
   // }

    //@Override
   // public float getY() {
       // return collisionModel.getY();
    //}

    public enum CurrentDirection{
        LEFT, RIGHT, DOWN, UP, STAND_UP, STAND_DOWN, STAND_LEFT, STAND_RIGHT;
    }
}

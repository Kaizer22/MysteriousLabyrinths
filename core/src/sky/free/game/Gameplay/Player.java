package sky.free.game.Gameplay;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by denis on 26.06.17.
 */

public class Player extends Actor {
    public Texture currentTexture; // или currentAnimation
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

    public Texture moveLeft;
    public Texture moveRight;
    public Texture moveUp;
    public Texture moveDown;


    public Player(float X,float Y,float blockSize){

        moveLeft = new Texture("gameplay/player_left.png");
        moveRight = new Texture("gameplay/player_right.png");
        moveDown = new Texture("gameplay/player_down.png");


        isCanGoUP = true;
        isCanGoDOWN = true;
        isCanGoLEFT = true;
        isCanGoRIGHT = true;

        x = X;
        y = Y;

        currentTexture = moveRight; ;
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

   // @Override
    //public float getX() {
       // return collisionModel.getX();
   // }

    //@Override
   // public float getY() {
       // return collisionModel.getY();
    //}

    public enum CurrentDirection{
        LEFT, RIGHT, DOWN, UP;
    }
}

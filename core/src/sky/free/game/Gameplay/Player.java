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

    boolean isCanGoUP;
    boolean isCanGoDOWN;
    boolean iscanGoLEFT;
    boolean isCanGoRIGHT;

    public Texture moveLeft;
    public Texture moveRight;
    public Texture moveUp;
    public Texture moveDown;


    public Player(float X,float Y,float blockSize){

        moveLeft = new Texture("gameplay/player_left.png");
        moveRight = new Texture("gameplay/player_right.png");

        currentTexture = moveRight; ;
        direction = CurrentDirection.RIGHT;
        collisionModel = new Rectangle(X,Y,blockSize,blockSize/3);
    }

    @Override
    public float getX() {
        return collisionModel.getX();
    }

    @Override
    public float getY() {
        return collisionModel.getY();
    }

    public enum CurrentDirection{
        LEFT, RIGHT, DOWN, UP;
    }
}

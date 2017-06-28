package sky.free.game.Gameplay;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by denis on 26.06.17.
 */

public class Block extends Actor{
    public Type type;
    public Shape shape;

    public Rectangle collisionModel;

    public float x;
    public float y;

    public Block(Type t,Shape s, int size, float X,float Y){
        type = t;
        shape = s;
        x = X;
        y = Y;
        if (type == Type.STONE_WALL)
            collisionModel = setCollision(size);
    }

    public Block( float X,float Y){
        type = Type.NONE;
        shape = Shape.NONE;
        collisionModel = null;
        x = X;
        y = Y;
    }

    private Rectangle setCollision(float size){
        float height;

        switch (shape){
            case TRIPARTATE_LEFT:
                height = size/2;
                break;
            case TRIPARTATE_RIGHT:
                height = size/2;
                break;
            case CONNECTOR_HORIZONTAL:
                height = size/2;
                break;

            default:
                height = size;
        }

        return new Rectangle(x,y,size,height);
    }

    public enum Type{
        STONE_BACKGROUND,STONE_WALL,DARKNESS    ,NONE
    }
    public enum Shape{
        TRIPARTATE_LEFT,TRIPARTATE_RIGHT,CONNECTOR_HORIZONTAL     ,NONE
    }
}

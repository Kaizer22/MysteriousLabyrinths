package sky.free.game.Gameplay;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by denis on 26.06.17.
 */

public class Block {
    public Type type;
    public Shape shape;

    public boolean isTorchOnIt;
    public boolean isTorchActive;

    public Rectangle collisionModel;

    public float x;
    public float y;

    public Block(Type t,Shape s,boolean isTorch){ //int size, float X,float Y){
        type = t;
        shape = s;
        isTorchOnIt = isTorch;

    }

    public Block(){
        type = Type.NONE;
        shape = Shape.NONE;
        collisionModel = null;
    }

    public void setParam(float X, float Y, float size){
        x = X;
        y = Y;
        if (type == Type.WALL)
            collisionModel = setCollision(size);
    }

    private Rectangle setCollision(float size){
        float height;

        switch (shape){
            case QUADRIPARTITE:
                height = size/2;
                break;
            case CORNER_L_T:
                height = size/2;
                break;
            case CORNER_R_T:
                height = size/2;
                break;
            case DOUBLE_INNER_CORNER_D:
                height = size/2;
                break;
            case EXTERNAL_INNER_CORNER_L_T:
                height = size/2;
                break;
            case EXTERNAL_INNER_CORNER_R_T:
                height = size/2;
                break;
            case INNER_CORNER_L_D_WALL_T:
                height = size/2;
                break;
            case INNER_CORNER_R_D_WALL_T:
                height = size/2;
                break;
            case TRIPARTITE_TOP:
                height = size/2;
                break;
            case TRIPARTITE_LEFT:
                height = size/2;
                break;
            case TRIPARTITE_RIGHT:
                height = size/2;
                break;
            case CONNECTOR_HORIZONTAL:
                height = size/2;
                break;
            case WALL_TOP:
                height = size/2;
                break;

            default:
                height = size;
        }

        return new Rectangle(x,y,size,height);
    }

    public enum Type{
        BACKGROUND, WALL,NONE,

        DOESNT_MATTER;

        public static Type giveValue(String name){
            for (Type t:Type.values()) {
                if (t.toString().equals(name))
                    return  t;
            }
            return NONE;
        }
    }
    public enum Shape{
        QUADRIPARTITE,
        TRIPARTITE_LEFT,TRIPARTITE_RIGHT, TRIPARTITE_TOP, TRIPARTITE_DOWN,
        WALL_LEFT,WALL_RIGHT,WALL_TOP, WALL_DOWN,
        CORNER_L_D,CORNER_L_T,CORNER_R_D, CORNER_R_T,
        CONNECTOR_HORIZONTAL, CONNECTOR_VERTICAL,
        INNER_CORNER_L_D,INNER_CORNER_R_D,INNER_CORNER_R_T, INNER_CORNER_L_T,
        INNER_CORNER_R_T_WALL_L, INNER_CORNER_R_T_WALL_D, INNER_CORNER_L_T_WALL_D, INNER_CORNER_L_D_WALL_T, INNER_CORNER_L_T_WALL_R, INNER_CORNER_R_D_WALL_L,INNER_CORNER_R_D_WALL_T, INNER_CORNER_L_D_WALL_R,
        EXTERNAL_INNER_CORNER_L_D, EXTERNAL_INNER_CORNER_R_T, EXTERNAL_INNER_CORNER_L_T, EXTERNAL_INNER_CORNER_R_D,
        DOUBLE_INNER_CORNER_L, DOUBLE_INNER_CORNER_D, DOUBLE_INNER_CORNER_R, DOUBLE_INNER_CORNER_T,
        DARKNESS


        ,NONE;

        public static Shape giveValue(String name){
            for (Shape t:Shape.values()) {
                if (t.toString().equals(name))
                    return  t;
            }
            return NONE;
        }
    }
}

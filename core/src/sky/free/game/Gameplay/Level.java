package sky.free.game.Gameplay;

import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Created by denis on 26.06.17.
 */

public class Level {
    public int number;
    public Block[][] layer1;
    public Block[][] layer2;

    public int startX;
    public int startY;

    public int finishX;
    public int finishY;

    public Level(Block[][] layer_1, Block[][] layer_2, int stX, int stY, int finX, int finY){
        layer1 = layer_1;
        layer2 = layer_2;

        startX = stX;
        startY = stY;

        finishX = finX;
        finishY = finY;


    }

    public Level(int X,int Y,int num){
        number = num;

        startX = 0;
        startY = 0;
        finishX = 1;
        finishY = 1;



        layer1 = new Block[Y][X];
        layer2 = new Block[Y][X];

        for (int i = 0; i < Y ; i++) {
            for (int j = 0; j < X ; j++) {
                layer1[i][j] = new Block(Block.Type.BACKGROUND, Block.Shape.NONE,false);
                layer2[i][j] = new Block(Block.Type.WALL, Block.Shape.DARKNESS,false);

            }

        }
    }


}

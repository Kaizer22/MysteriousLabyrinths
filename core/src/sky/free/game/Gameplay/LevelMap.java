package sky.free.game.Gameplay;

import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Created by denis on 26.06.17.
 */

public class LevelMap extends Group {
    public Block[][] layer1;
    public Block[][] layer2;

    public LevelMap(Block[][] layer_1,Block[][] layer_2){
        layer1 = layer_1;
        layer2 = layer_2;


    }


}
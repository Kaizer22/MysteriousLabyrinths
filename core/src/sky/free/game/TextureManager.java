package sky.free.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import sky.free.game.Gameplay.Block;

/**
 * Created by denis on 26.06.17.
 */

public class TextureManager {

    Texture leftButtonBar;
    Texture rightButtonBar;



    HashMap<String,Texture> blocks;
    public TextureManager() {
        blocks = new HashMap<String, Texture>();
        leftButtonBar = new Texture("gameplay/ui/left_bar.png");
        rightButtonBar = new Texture("gameplay/ui/right_bar.png");


        for (Block.Type t : Block.Type.values()) {      // переделать под TextureAtlas
            if (t == Block.Type.STONE_WALL) {
                for (Block.Shape s : Block.Shape.values()) {
                    if (s != Block.Shape.NONE) {
                        blocks.put(t.toString().toLowerCase() + "_" + s.toString().toLowerCase(), new Texture("gameplay/block_textures/" + t.toString().toLowerCase() + "/" + s.toString().toLowerCase() + ".png"));
                    }
                }
            }else if(t != Block.Type.NONE){
                blocks.put(t.toString().toLowerCase() + "_" + "none", new Texture("gameplay/block_textures/" + t.toString().toLowerCase() + "/" + "none.png"));
            }

        }
    }

    public Texture getBlockTexture(Block.Type t, Block.Shape s){
        return blocks.get(t.toString().toLowerCase()+"_"+s.toString().toLowerCase());
    }

    public void dispose(){
        for(Texture t:blocks.values()){
            t.dispose();
        }
        leftButtonBar.dispose();
        rightButtonBar.dispose();

    }
}

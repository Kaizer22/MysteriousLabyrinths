package sky.free.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.HashMap;

import sky.free.game.Gameplay.Block;

/**
 * Created by denis on 26.06.17.
 */

public class TextureManager {

    Texture leftButtonBar;
    Texture rightButtonBar;

    Texture torch;

    Animation torchA;



    HashMap<String,Texture> blocks;
    public TextureManager() {
        blocks = new HashMap<String, Texture>();
        leftButtonBar = new Texture("gameplay/ui/left_bar.png");
        rightButtonBar = new Texture("gameplay/ui/right_bar.png");
        torch = new Texture("gameplay/torch.png");

        torchA = Animator.initAnim(8,"gameplay/torch/",0.15f);


        for (Block.Type t : Block.Type.values()) {      // переделать под TextureAtlas
            if (t == Block.Type.WALL) {
                for (Block.Shape s : Block.Shape.values()) {
                    if (s != Block.Shape.NONE) {
                        blocks.put(t.toString().toLowerCase() + "_" + s.toString().toLowerCase(), new Texture("gameplay/block_textures/" + t.toString().toLowerCase() + "/" + s.toString().toLowerCase() + ".png"));
                    }
                }
            }else if(t != Block.Type.NONE && t != Block.Type.DOESNT_MATTER){
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
        torch.dispose();

    }
}

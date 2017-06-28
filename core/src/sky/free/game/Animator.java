package sky.free.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by denis on 19.06.17.
 */

public  class Animator {

    static Animation initAnim(int num_frames, String directory,float duration){
        TextureRegion[] tr;
        tr = new TextureRegion[num_frames];
        for (int i = 0; i < num_frames; i++){
            tr[i] = new TextureRegion();
            tr[i].setRegion(new Texture(directory+"f_"+i+".png"));
        }
        return new Animation(duration,tr);
    }
}

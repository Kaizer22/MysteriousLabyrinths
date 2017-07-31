package sky.free.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by denis on 20.06.17.
 */

public abstract class Button  {
    public Rectangle checkZone;

    boolean isActivatable;
    boolean isActivated = false; // Condition 0/1

    public Texture currentTexture;

    public Texture texture;            // Condition 0
    public Texture texturePressed;

    public Texture textureCon1;        // Condition 1
    public Texture texturePressedCon1;

    public Button (Rectangle b,Texture t, Texture tP ){
        checkZone = b;
        texture = t;
        currentTexture = texture;
        texturePressed = tP;
    }

    public Button (Rectangle b,Texture t, Texture tP,Texture tC1, Texture tPC1 ){
        checkZone = b;
        texture = t;
        currentTexture = texture;
        texturePressed = tP;
        textureCon1 = tC1;
        texturePressedCon1 = tPC1;
        isActivatable = true;
    }

    public void makeAction(){}

    public void disposeButton(){
        texture.dispose();
        texturePressed.dispose();

        if (isActivatable) {
            textureCon1.dispose();
            texturePressedCon1.dispose();
        }
    }
}

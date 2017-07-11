package sky.free.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

import sky.free.game.Gameplay.Block;
import sky.free.game.Gameplay.LevelMap;
import sky.free.game.Gameplay.MapBuilder;
import sky.free.game.Gameplay.Player;

/**
 * Created by denis on 16.06.17.
 */

public class GameplayScreen implements Screen,InputProcessor {
    int[][] levelmap = {
            {2,1,3,3,1,1,1,1,1,1,1,1,1,1},
            {1,1,2,2,3,0,1,0,0,0,0,0,0,1},
            {1,0,1,0,0,0,2,0,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,2,3,0,0,0,0,0,0,0,0,1},
            {1,0,1,2,3,0,0,0,0,0,0,0,0,1},
            {1,0,1,2,3,0,0,0,0,0,0,0,0,1},
            {1,0,1,2,3,0,0,4,4,0,0,0,0,1},
            {1,0,1,2,3,0,0,0,0,0,0,0,0,1},
            {1,0,1,2,3,0,0,0,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,0,0,0,2,0,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1}

    };
    LabyrinthGame gam;

    Stage level;

    LevelMap levelMap;

    Player player;


    boolean isPlayerDrawn;

    TextureManager txM;
    Texture testcol;

    int scrH;
    int scrW;
    int blockSize;

    Button actionButton;
    Button menuButton;
    Button upButton;
    Button downButton;
    Button leftButton;
    Button rightButton;

    private OrthographicCamera cameraUI;
    private OrthographicCamera camera;

    private float stateTime;

    public GameplayScreen (final LabyrinthGame game){
        gam = game;

        cameraUI = new OrthographicCamera();
        camera = new OrthographicCamera();

        txM = new TextureManager();

        testcol = new Texture("col.png");






    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cameraUI.update();
        camera.update();

        gam.batchUI.setProjectionMatrix(cameraUI.combined);
        gam.batch.setProjectionMatrix(camera.combined);
        stateTime += Gdx.graphics.getDeltaTime();

        //_______________________________________
        gam.batch.begin();
        drawMap();
        gam.batch.end();
        gam.batchUI.begin();
        drawUI();
        gam.batchUI.end();
        //_______________________________________

        checkCollisions();

        if (Gdx.input.isTouched()){
            if (downButton.checkZone.contains(Gdx.input.getX(),scrH-Gdx.input.getY()))
                downButton.makeAction();
            if (upButton.checkZone.contains(Gdx.input.getX(),scrH-Gdx.input.getY()))
                upButton.makeAction();
            if (leftButton.checkZone.contains(Gdx.input.getX(),scrH-Gdx.input.getY()))
                leftButton.makeAction();
            if (rightButton.checkZone.contains(Gdx.input.getX(),scrH-Gdx.input.getY()))
                rightButton.makeAction();
        }else{
            switch (player.direction){
                case LEFT:
                    player.direction = Player.CurrentDirection.STAND_LEFT;
                    break;
                case RIGHT:
                    player.direction = Player.CurrentDirection.STAND_RIGHT;
                    break;
                case DOWN:
                    player.direction = Player.CurrentDirection.STAND_DOWN;
                    break;
                case UP:
                    player.direction = Player.CurrentDirection.STAND_UP;
            }
        }

        player.updateState();
    }

    @Override
    public void resize(int width, int height) {
        cameraUI.setToOrtho(false,width,height);
        camera.setToOrtho(false,width,height);


        scrH = height;
        scrW = width;

        blockSize = scrH/4;
        loadLevel(0);
        player = new Player(scrW/2-blockSize/2,scrH/2-blockSize/2,blockSize);

        Rectangle r1 = new Rectangle(scrW/4*3+scrW/8-scrH/6,scrH/5*2-scrH/6,scrH/3,scrH/3);
        Texture aB = new Texture("gameplay/ui/button_action.png");
        Texture aBP = new Texture("gameplay/ui/button_action_pressed.png");
        actionButton = new Button(r1,aB,aBP) {
            @Override
            public void makeAction() {

                if (player.playerBlockY + 1 < levelMap.layer2.length )
                    if (levelMap.layer2[player.playerBlockY + 1][player.playerBlockX].type == Block.Type.STONE_WALL && player.y < levelMap.layer2[player.playerBlockY + 1][player.playerBlockX].y && levelMap.layer2[player.playerBlockY + 1][player.playerBlockX].isTorchOnIt){

                        if (player.actionZone.overlaps(levelMap.layer2[player.playerBlockY + 1][player.playerBlockX].collisionModel))
                            Gdx.app.log("ASSA", "Im HERE");
                            levelMap.layer2[player.playerBlockY + 1][player.playerBlockX].isTorchActive = true;
                }

                if (player.playerBlockX + 1 < levelMap.layer2[0].length )
                    if (levelMap.layer2[player.playerBlockY][player.playerBlockX+1].type == Block.Type.STONE_WALL && player.y<levelMap.layer2[player.playerBlockY ][player.playerBlockX+1].y && levelMap.layer2[player.playerBlockY][player.playerBlockX+1].isTorchOnIt )
                        if(player.actionZone.overlaps(levelMap.layer2[player.playerBlockY][player.playerBlockX+1].collisionModel))
                            levelMap.layer2[player.playerBlockY][player.playerBlockX+1].isTorchActive = true;

                if (player.playerBlockX - 1 > 0 )
                    if (levelMap.layer2[player.playerBlockY][player.playerBlockX-1].type == Block.Type.STONE_WALL && player.y<levelMap.layer2[player.playerBlockY ][player.playerBlockX-1].y && levelMap.layer2[player.playerBlockY][player.playerBlockX-1].isTorchOnIt )
                        if(player.actionZone.overlaps(levelMap.layer2[player.playerBlockY][player.playerBlockX-1].collisionModel))
                            levelMap.layer2[player.playerBlockY][player.playerBlockX-1].isTorchActive = true;

            }
        };

        Rectangle r2 = new Rectangle(scrW/4*3+scrW/48,scrH-scrH/6-scrW/48,scrH/6*1.5f,scrH/6);
        Texture mB = new Texture("gameplay/ui/button_menu.png");
        Texture mBP = new Texture("gameplay/ui/button_menu_pressed.png");
        menuButton = new Button(r2,mB,mBP) {
            @Override
            public void makeAction() {
                super.makeAction();
                dispose();
                gam.setScreen(new MenuScreen(gam));
            }
        };


        Rectangle r3 = new Rectangle(scrW/8-scrH/25,scrH/5*2+scrH/25,scrH/25*2,scrH/25*4);
        Texture upB = new Texture("gameplay/ui/button_move1.png");
        Texture upBP = new Texture("gameplay/ui/button_move1_pressed.png");
        upButton = new Button(r3,upB,upBP) {
            @Override
            public void makeAction() {
                super.makeAction();
                if (player.isCanGoUP) {
                    camera.position.y += 5;
                    player.setDeltaY(5);
                }
                if (player.direction != Player.CurrentDirection.UP){
                    player.direction = Player.CurrentDirection.UP;
                }
            }
        };

        Rectangle r4 = new Rectangle(scrW/8-scrH/25,scrH/5,scrH/25*2,scrH/25*4);
        Texture downB = new Texture("gameplay/ui/button_move1.png");
        Texture downBP = new Texture("gameplay/ui/button_move1_pressed.png");
        downButton = new Button(r4,downB,downBP) {
            @Override
            public void makeAction() {
                super.makeAction();
                if (player.isCanGoDOWN) {
                    camera.position.y -= 5;
                    player.setDeltaY(-5);
                }
                if (player.direction != Player.CurrentDirection.DOWN){
                    player.direction = Player.CurrentDirection.DOWN;
                }

            }
        };

        Rectangle r5 = new Rectangle(scrW/8-scrH/25-scrH/25*4,scrH/5*2-scrH/25,scrH/25*4,scrH/25*2);
        Texture leftB = new Texture("gameplay/ui/button_move.png");
        Texture leftBP = new Texture("gameplay/ui/button_move_pressed.png");
        leftButton = new Button(r5,leftB,leftBP) {
            @Override
            public void makeAction() {
                super.makeAction();
                if (player.isCanGoLEFT) {
                    camera.position.x -= 5;
                    player.setDeltaX(-5);
                }
                if (player.direction != Player.CurrentDirection.LEFT){
                    player.direction = Player.CurrentDirection.LEFT;
                }

            }
        };

        Rectangle r6 = new Rectangle(scrW/8+scrH/25,scrH/5*2-scrH/25,scrH/25*4,scrH/25*2);
        Texture rightB = new Texture("gameplay/ui/button_move.png");
        Texture rightBP = new Texture("gameplay/ui/button_move_pressed.png");
        rightButton = new Button(r6,rightB,rightBP) {
            @Override
            public void makeAction() {
                super.makeAction();
                if (player.isCanGoRIGHT) {
                    camera.position.x += 5;                                                            //переделать на доли от размера экрана
                    player.setDeltaX(5);
                }
                if (player.direction != Player.CurrentDirection.RIGHT){
                    player.direction = Player.CurrentDirection.RIGHT;
                }
            }
        };

    }


    //*****************************************************
    public void drawUI(){
        gam.batchUI.draw(txM.leftButtonBar,0,0,scrW/4,scrH);
        gam.batchUI.draw(txM.rightButtonBar,scrW/4*3,0,scrW/4,scrH);
        gam.batchUI.draw(actionButton.currentTexture,actionButton.checkZone.x,actionButton.checkZone.y,actionButton.checkZone.getWidth(),actionButton.checkZone.getHeight());
        gam.batchUI.draw(menuButton.currentTexture,menuButton.checkZone.x,menuButton.checkZone.y,menuButton.checkZone.getWidth(),menuButton.checkZone.getHeight());
        gam.batchUI.draw(upButton.currentTexture,upButton.checkZone.x,upButton.checkZone.y,upButton.checkZone.getWidth(),upButton.checkZone.getHeight());
        gam.batchUI.draw(downButton.currentTexture,downButton.checkZone.x,downButton.checkZone.y,downButton.checkZone.getWidth(),downButton.checkZone.getHeight());
        gam.batchUI.draw(leftButton.currentTexture,leftButton.checkZone.x,leftButton.checkZone.y,leftButton.checkZone.getWidth(),leftButton.checkZone.getHeight());
        gam.batchUI.draw(rightButton.currentTexture,rightButton.checkZone.x,rightButton.checkZone.y,rightButton.checkZone.getWidth(),rightButton.checkZone.getHeight());

    }

    public void drawMap() {

        for (int i = 0; i < levelMap.layer1.length; i++) {
            for (int j = 0; j < levelMap.layer1[0].length; j++) {
                drawBlock(levelMap.layer1[i][j]);
            }
        }

        for (int i = levelMap.layer2.length-1; i > -1 ; i--) {

            for (int j = 0; j < levelMap.layer2[0].length; j++) {

                if (player.y > levelMap.layer2[i][0].y && !isPlayerDrawn){
                    gam.batch.draw(player.getCurrentTexture(stateTime), player.x, player.y, blockSize, blockSize);
                    isPlayerDrawn = true;
                }

                drawBlock(levelMap.layer2[i][j]);

                if (player.y < levelMap.layer2[i][0].y && isPlayerDrawn){
                    gam.batch.draw(player.getCurrentTexture(stateTime), player.x, player.y, blockSize, blockSize);
                    isPlayerDrawn = false;
                }
                //gam.batch.draw(testcol, player.collisionModel.x, player.collisionModel.y, player.collisionModel.getWidth(), player.collisionModel.getHeight());
            }
        }
        isPlayerDrawn = false;

    }

    public void checkCollisions(){


            if (player.playerBlockY + 1 < levelMap.layer2.length)
                if (levelMap.layer2[player.playerBlockY + 1][player.playerBlockX].type == Block.Type.STONE_WALL)
                    if (player.collisionModel.overlaps(levelMap.layer2[player.playerBlockY + 1][player.playerBlockX].collisionModel))
                        player.isCanGoUP = false;




            if (player.playerBlockX + 1 < levelMap.layer2[0].length)
                if (levelMap.layer2[player.playerBlockY][player.playerBlockX + 1].type == Block.Type.STONE_WALL)
                    if (player.collisionModel.overlaps(levelMap.layer2[player.playerBlockY][player.playerBlockX + 1].collisionModel))
                        player.isCanGoRIGHT = false;

            if (levelMap.layer2[player.playerBlockY][player.playerBlockX].type == Block.Type.STONE_WALL)
                if (player.collisionModel.overlaps(levelMap.layer2[player.playerBlockY][player.playerBlockX].collisionModel)){
                    player.isCanGoDOWN = false;
                    player.isCanGoLEFT = false;
                }


            if (player.playerBlockY + 1 < levelMap.layer2.length && player.playerBlockX + 1 < levelMap.layer2[0].length)
                if (levelMap.layer2[player.playerBlockY + 1][player.playerBlockX+1].type == Block.Type.STONE_WALL)
                    if (player.collisionModel.overlaps(levelMap.layer2[player.playerBlockY + 1][player.playerBlockX+1].collisionModel))
                        player.isCanGoUP = false;

           if (player.playerBlockY + 1 < levelMap.layer2.length && player.playerBlockX - 1 > 0)
                if (levelMap.layer2[player.playerBlockY + 1][player.playerBlockX-1].type == Block.Type.STONE_WALL)
                    if (player.collisionModel.overlaps(levelMap.layer2[player.playerBlockY + 1][player.playerBlockX-1].collisionModel))
                        player.isCanGoUP = false;

             if(player.playerBlockY - 1 >  0 && player.playerBlockX - 1 > 0)
                if (levelMap.layer2[player.playerBlockY - 1][player.playerBlockX-1].type == Block.Type.STONE_WALL)
                    if (player.collisionModel.overlaps(levelMap.layer2[player.playerBlockY - 1][player.playerBlockX-1].collisionModel))
                        player.isCanGoDOWN = false;

            if (player.playerBlockY - 1 >  0 && player.playerBlockX + 1 < levelMap.layer2[0].length)
                if (levelMap.layer2[player.playerBlockY - 1][player.playerBlockX + 1].type == Block.Type.STONE_WALL)
                    if (player.collisionModel.overlaps(levelMap.layer2[player.playerBlockY - 1][player.playerBlockX + 1].collisionModel))
                        player.isCanGoDOWN = false;


       // }
    }

    public void loadLevel(int num){  //загрузка будет происходить из базы данных
        Gdx.app.log("INFO","SIZE ");
        //load int[][] from database, using num
        levelMap = gam.allLevels.get(0);
    }

    public void drawBlock(Block block){
        if (block.type != Block.Type.NONE) {
            gam.batch.draw(txM.getBlockTexture(block.type, block.shape), block.x, block.y, blockSize, blockSize);
            if (block.isTorchOnIt)
                if (block.isTorchActive){
                    gam.batch.draw((TextureRegion)txM.torchA.getKeyFrame(stateTime,true),block.x, block.y, blockSize, blockSize);
                }else
                    gam.batch.draw(txM.torch, block.x, block.y, blockSize, blockSize);
            //if (block.type == Block.Type.STONE_WALL)
                //gam.batch.draw(testcol, block.collisionModel.x, block.collisionModel.y, block.collisionModel.getWidth(), block.collisionModel.getHeight());
        }
    }

    //*****************************************************


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        actionButton.disposeButton();
        downButton.disposeButton();
        leftButton.disposeButton();
        rightButton.disposeButton();
        menuButton.disposeButton();
        upButton.disposeButton();
        txM.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (actionButton.checkZone.contains(screenX,scrH-screenY)) {
            actionButton.currentTexture = actionButton.texturePressed;

        }
        else if (menuButton.checkZone.contains(screenX,scrH-screenY)) {
            menuButton.currentTexture = menuButton.texturePressed;
        }
        else if (upButton.checkZone.contains(screenX,scrH-screenY)) {
            upButton.currentTexture = upButton.texturePressed;
        }
        else if (downButton.checkZone.contains(screenX,scrH-screenY)) {
            downButton.currentTexture = downButton.texturePressed;
        }
        else if (leftButton.checkZone.contains(screenX,scrH-screenY)) {
            leftButton.currentTexture = leftButton.texturePressed;
        }
        else if (rightButton.checkZone.contains(screenX,scrH-screenY)) {
            rightButton.currentTexture = rightButton.texturePressed;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        actionButton.currentTexture = actionButton.texture;
        menuButton.currentTexture = menuButton.texture;
        upButton.currentTexture = upButton.texture;
        downButton.currentTexture = downButton.texture;
        leftButton.currentTexture = leftButton.texture;
        rightButton.currentTexture = rightButton.texture;

        if (actionButton.checkZone.contains(screenX,scrH-screenY)) {
            actionButton.makeAction();
        }
        else if (menuButton.checkZone.contains(screenX,scrH-screenY)) {
            menuButton.makeAction();
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

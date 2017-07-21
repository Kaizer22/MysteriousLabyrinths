package sky.free.game.Gameplay;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by denis on 04.07.17.
 */

public class MapBuilder {
    /**LinkedList<Mask> allMasks;

    public MapBuilder(){
       loadMasks();
        //add masks from database
    }

    public LevelMap createLevelMap(int[][] levelmap, int blockSize){
        LevelMap levelMap = new LevelMap(new Block[levelmap.length][levelmap[0].length],new Block[levelmap.length][levelmap[0].length]);
        for (int i = 0; i < levelmap.length; i++) {
            for (int j = 0; j < levelmap[0].length; j++) {
                switch (levelmap[i][j]){
                    case 0:
                        levelMap.layer1[i][j] = new Block(Block.Type.BACKGROUND, Block.Shape.NONE,blockSize,j*blockSize,i*blockSize);
                        levelMap.layer2[i][j] = new Block(j*blockSize,i*blockSize);
                        break;
                    case 1:
                        levelMap.layer2[i][j] = new Block(Block.Type.WALL, Block.Shape.TRIPARTATE_LEFT,blockSize,j*blockSize,i*blockSize);
                        levelMap.layer1[i][j] = new Block(Block.Type.BACKGROUND, Block.Shape.NONE,blockSize,j*blockSize,i*blockSize);
                        break;
                    case 2:
                        levelMap.layer2[i][j] = new Block(Block.Type.WALL, Block.Shape.CONNECTOR_HORIZONTAL,blockSize,j*blockSize,i*blockSize);
                        levelMap.layer1[i][j] = new Block(Block.Type.BACKGROUND, Block.Shape.NONE,blockSize,j*blockSize,i*blockSize);
                        break;
                    case 3:
                        levelMap.layer2[i][j] = new Block(Block.Type.WALL, Block.Shape.TRIPARTATE_RIGHT,blockSize,j*blockSize,i*blockSize);
                        levelMap.layer1[i][j] = new Block(Block.Type.BACKGROUND, Block.Shape.NONE,blockSize,j*blockSize,i*blockSize);
                        break;

                    case 4:
                        levelMap.layer2[i][j] = new Block(Block.Type.WALL, Block.Shape.TRIPARTATE_RIGHT,blockSize,j*blockSize,i*blockSize);
                        levelMap.layer2[i][j].isTorchOnIt = true;
                        levelMap.layer1[i][j] = new Block(Block.Type.BACKGROUND, Block.Shape.NONE,blockSize,j*blockSize,i*blockSize);
                        break;
                }
            }
        }
        return levelMap;
    }

    private Block.Shape getShape(Block[][] layer2, int x, int y){
        Mask m = new Mask(Block.Shape.NONE);
        for (int i = 0; i < 2; i++) {
            for (int j = -0; j < 2 ; j++) {
                m.mask[i][j] = layer2[y + i-1][x+i-1].type;
            }
        }

        for (Mask mask:allMasks) {
            if (mask.equals(m)){
                return mask.maskOf;
            }
        }
        return Block.Shape.CONNECTOR_HORIZONTAL;
    }

    private class Mask{
        public Block.Shape maskOf;
        public Block.Type[][] mask = new Block.Type[3][3];

        Mask(Block.Shape s){
            maskOf = s;
        }
        @Override
        public boolean equals(Object o) {
            Mask m2 = (Mask) o;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if( m2.mask[i][j] != this.mask[i][j] && this.mask[i][j] != Block.Type.DOESNT_MATTER )
                        return false;
                }
            }
            return true;

        }
    }

    private void loadMasks(){
        allMasks = new LinkedList<Mask>();
    }

    //public Block.Shape getShape(Block[][]layer,int x,int y){
       // String code;

      //  return allShapes.get(code);
  //  }
**/

}

package sky.free.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.DatabaseFactory;
import com.badlogic.gdx.sql.SQLiteGdxException;

import sky.free.game.Gameplay.Block;
import sky.free.game.Gameplay.Level;

/**
 * Created by denis on 28.07.17.
 */

public class LevelDatabase {
    private Database dbHelper;


    private static final String TABLE_LEVELS_INFO = "levelInfo";
    private static final String TABLE_PLAYER_INFO = "playerInfo";
    private static final String TABLE_LEVEL_MAPS = "levelMaps";

    private static final String KEY_ID = "_id";

    private static final String KEY_LEVEL_SIZE_X = "level_size_x";
    private static final String KEY_LEVEL_SIZE_Y = "level_size_y";
    private static final String KEY_LEVEL_START_X = "level_start_x";
    private static final String KEY_LEVEL_START_Y = "level_start_y";
    private static final String KEY_LEVEL_FINISH_X = "level_finish_x";
    private static final String KEY_LEVEL_FINISH_Y = "level_finish_y";

    private static final String KEY_BLOCK_X = "block_x";
    private static final String KEY_BLOCK_Y = "block_y";
    private static final String KEY_BLOCK_TYPE = "block_type";
    private static final String KEY_BLOCK_SHAPE = "block_shape";
    private static final String KEY_IS_TORCH_ON_BLOCK = "is_torch_on_block";  //Integer 1/0
    private static final String KEY_NUM_LEVEL = "num_level";

    private static String DB_NAME = "gameplay.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_LEVELS_INFO +
            "(" + KEY_ID + " INTEGER PRIMARY KEY, " +
            KEY_LEVEL_SIZE_X + " INTEGER NOT NULL, " +
            KEY_LEVEL_SIZE_Y + " INTEGER NOT NULL, " +
            KEY_LEVEL_START_X + " INTEGER NOT NULL, " +
            KEY_LEVEL_START_Y + " INTEGER NOT NULL, " +
            KEY_LEVEL_FINISH_X + " INTEGER NOT NULL, " +
            KEY_LEVEL_FINISH_Y + " INTEGER NOR NULL); " +

            "CREATE TABLE IF NOT EXISTS " + TABLE_LEVEL_MAPS +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_BLOCK_X + " INTEGER NOT NULL, " +
            KEY_BLOCK_Y + " INTEGER NOT NULL, " +
            KEY_BLOCK_TYPE + " TEXT NOT NULL, " +
            KEY_BLOCK_SHAPE + " TEXT NOT NULL, " +
            KEY_IS_TORCH_ON_BLOCK + " INTEGER NOT NULL, " +
            KEY_NUM_LEVEL + " INTEGER NOT NULL);";

    public LevelDatabase() {
        Gdx.app.log("DatabaseTest", "creation started");
        dbHelper = DatabaseFactory.getNewDatabase(DB_NAME, DB_VERSION, DATABASE_CREATE, null);
        dbHelper.setupDatabase();
        try {
            dbHelper.openOrCreateDatabase();
            dbHelper.execSQL(DATABASE_CREATE);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        Gdx.app.log("DatabaseTest", "created successfully");


    }

    public void insertLevel(Level level){
        String DATABASE_INSERT_LEVEL_INFO = "INSERT INTO " + TABLE_LEVELS_INFO + " ("+ KEY_ID+", "+ KEY_LEVEL_SIZE_X+", "+ KEY_LEVEL_SIZE_Y+", "+KEY_LEVEL_START_X+", "+KEY_LEVEL_START_Y+", "+KEY_LEVEL_FINISH_X+", "+KEY_LEVEL_FINISH_Y+" ) " +
                "VALUES ( "+level.number+", "+level.layer1[0].length+", "+ level.layer1.length+", "+ level.startX+", "+level.startY+", "+level.finishX+", "+ level.finishY+" );";
        String DATABASE_INSERT_LEVEL_MAP = "INSERT INTO " + TABLE_LEVEL_MAPS + " ( "+KEY_BLOCK_X + ", "+ KEY_BLOCK_Y+", "+ KEY_BLOCK_TYPE+", "+KEY_BLOCK_SHAPE+", "+ KEY_IS_TORCH_ON_BLOCK+", "+ KEY_NUM_LEVEL+" )"+
                "VALUES";

        Block bufBlock;
        for (int i = 0; i <level.layer2.length; i++) {
            for (int j = 0; j <level.layer2[0].length ; j++) {
                bufBlock = level.layer2[i][j];
                DATABASE_INSERT_LEVEL_MAP+= " ( "+j+", "+", "+i+", "+bufBlock.type+", "+bufBlock.shape+", "+ (bufBlock.isTorchOnIt ? 1:0)+ ", "+ level.number +" ) ";
                if (j != level.layer2[0].length-1 && i != level.layer2.length-1 ){
                    DATABASE_INSERT_LEVEL_MAP += ",";
                }
            }
        }

        DATABASE_INSERT_LEVEL_MAP += ";";

        Gdx.app.log("Insert","Start");
        try {

            dbHelper.execSQL(DATABASE_INSERT_LEVEL_INFO);
            dbHelper.execSQL(DATABASE_INSERT_LEVEL_MAP);
            dbHelper.closeDatabase();
            Gdx.app.log("Insert","Successful");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }



    }

    public void deleteLevel(int number){
        Gdx.app.log("Delete","Start");
        try {
            dbHelper.execSQL("DELETE FROM " + TABLE_LEVELS_INFO +
            " WHERE " + KEY_ID + "=" + number+";");
            dbHelper.execSQL("DELETE FROM " + TABLE_LEVEL_MAPS +
            " WHERE " + KEY_NUM_LEVEL + "=" + number+";");
            dbHelper.closeDatabase();
            Gdx.app.log("Delete","Successful");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public Level getLevel(int number){
        Level level;
        DatabaseCursor cursorInfo = null;
        DatabaseCursor cursorMap = null;

        try {
            cursorInfo = dbHelper.rawQuery("SELECT * FROM " + TABLE_LEVELS_INFO+
                    "WHERE "+KEY_ID +"="+number+";");
            cursorMap = dbHelper.rawQuery("SELECT * FROM " + TABLE_LEVEL_MAPS+
                    "WHERE "+KEY_NUM_LEVEL +"="+number+";");
            dbHelper.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        int levelSizeX = cursorInfo.getInt(1);
        int levelSizeY = cursorInfo.getInt(2);
        int levelStartX = cursorInfo.getInt(3);
        int levelStartY = cursorInfo.getInt(4);
        int levelFinishX = cursorInfo.getInt(5);
        int levelFinishY = cursorInfo.getInt(6);

        Block[][] bufLayer1 = new Block[levelSizeY][levelSizeX];
        Block[][] bufLayer2 = new Block[levelSizeY][levelSizeX];

        int blockX;
        int blockY;
        String blockType;
        String blockShape;
        boolean isTorchOnBlock;

        while(cursorMap.next()){
            blockX = cursorMap.getInt(1);
            blockY = cursorMap.getInt(2);
            blockType = cursorMap.getString(3);
            blockShape = cursorMap.getString(4);
            isTorchOnBlock = cursorMap.getInt(5)==1 ;

            bufLayer1[blockY][blockX] = new Block(Block.Type.BACKGROUND, Block.Shape.NONE,false);
            bufLayer2[blockY][blockX] = new Block(Block.Type.valueOf(blockType), Block.Shape.valueOf(blockShape),isTorchOnBlock);
        }

        level = new Level(bufLayer1,bufLayer2,levelStartX,levelStartY,levelFinishX,levelFinishY);

        return level;
    }

    public void clearDatabase(){
        Gdx.app.log("Clear","Start");
        try {
            dbHelper.execSQL("DROP TABLE IF EXISTS" + TABLE_LEVELS_INFO);
            dbHelper.execSQL("DROP TABLE IF EXISTS" + TABLE_LEVEL_MAPS);
            dbHelper.closeDatabase();
            Gdx.app.log("Clear","Successful");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }
}
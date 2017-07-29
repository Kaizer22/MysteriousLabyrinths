package sky.free.game;

import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseFactory;
import com.badlogic.gdx.sql.SQLiteGdxException;

/**
 * Created by denis on 28.07.17.
 */

public class LevelDatabase {
    Database dbHelper;

    static final String TABLE_LEVELS_INFO = "levelInfo";
    static final String TABLE_PLAYER_INFO= "playerInfo";
    static final String TABLE_LEVEL_MAPS = "levelMaps";

    static final String KEY_ID = "_id";

    static final String KEY_LEVEL_SIZE_X = "level_size_x";
    static final String KEY_LEVEL_SIZE_Y = "level_size_y";
    static final String KEY_LEVEL_START_X = "level_start_x";
    static final String KEY_LEVEL_START_Y = "level_start_y";
    static final String KEY_LEVEL_FINISH_X = "level_finish_x";
    static final String KEY_LEVEL_FINISH_Y = "level_finish_y";

    static final String KEY_BLOCK_X = "block_x";
    static final String KEY_BLOCK_Y = "block_y";
    static final String KEY_BLOCK_TYPE = "block_type";
    static final String KEY_BLOCK_SHAPE = "block_shape";
    static final String KEY_IS_TORCH_ON_BLOCK = "is_torch_on_block";  //Integer 1/0
    static final String KEY_NUM_LEVEL = "num_level";

    private static String DB_NAME = "gameplay.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;


    LevelDatabase(){
        dbHelper = DatabaseFactory.getNewDatabase(DB_NAME,DB_VERSION,null,null);
        dbHelper.setupDatabase();
        try {
            dbHelper.openOrCreateDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }
}

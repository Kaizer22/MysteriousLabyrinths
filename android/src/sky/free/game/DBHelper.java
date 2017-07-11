package sky.free.game;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by denis on 07.07.17.
 */

public class DBHelper extends SQLiteOpenHelper {
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

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }






    @Override
    public void onCreate(SQLiteDatabase db) {//create PLAYER_INFO table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
        //destroy PLAYER_INFO table
    }
}

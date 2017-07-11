package sky.free.game;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.utils.Array;

import java.io.IOException;

import sky.free.game.Gameplay.Block;
import sky.free.game.Gameplay.LevelMap;


public class AndroidLauncher extends AndroidApplication {
	DBHelper db;

	Array <LevelMap> levels;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = new DBHelper(this);

		levels = new Array<>();

		loadLevels();
		launchGame();

	}

	private  void launchGame(){
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useGyroscope = false;
		initialize(new LabyrinthGame(levels), config);
	}

	private void loadLevels(){
		SQLiteDatabase database;

		try {
			db.updateDataBase();
		} catch (IOException mIOException) {
			throw new Error("UnableToUpdateDatabase");
		}

		try {
			database = db.getWritableDatabase();
		} catch (SQLException mSQLException) {
			throw mSQLException;
		}

		Block[][] bufLayer1;
		Block[][] bufLayer2;


		String selection;

		int levelIDIndex;
		int levelSizeXIndex;
		int levelSizeYIndex;
		int levelStartXIndex;
		int levelStartYIndex;
		int levelFinishXIndex;
		int levelFinishYIndex;

		int levelID;
		int levelSizeX;
		int levelSizeY;
		int levelStartX;
		int levelStartY;
		int levelFinishX;
		int levelFinishY;

		int blockXIndex;
		int blockYIndex;
		int blockTypeIndex;
		int blockShapeIndex;
		int blockIsTorchOnItIndex;

		int blockX;
		int blockY;
		String blockType;
		String blockShape;
		int blockIsTorchOnIt;



		Cursor cursor = database.query(DBHelper.TABLE_LEVELS_INFO,null,null,null,null,null,null);
		Cursor blocksCursor;
		cursor.moveToFirst();

		levelIDIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
		levelSizeXIndex = cursor.getColumnIndex(DBHelper.KEY_LEVEL_SIZE_X);
		levelSizeYIndex = cursor.getColumnIndex(DBHelper.KEY_LEVEL_SIZE_Y);
		levelStartXIndex = cursor.getColumnIndex(DBHelper.KEY_LEVEL_START_X);
		levelStartYIndex = cursor.getColumnIndex(DBHelper.KEY_LEVEL_START_Y);
		levelFinishXIndex = cursor.getColumnIndex(DBHelper.KEY_LEVEL_FINISH_X);
		levelFinishYIndex = cursor.getColumnIndex(DBHelper.KEY_LEVEL_FINISH_Y);


		do {
			cursor = database.query(DBHelper.TABLE_LEVELS_INFO,null,null,null,null,null,null);
			levelID = cursor.getInt(levelIDIndex);
			levelSizeX = cursor.getInt(levelSizeXIndex);
			levelSizeY = cursor.getInt(levelSizeYIndex);
			levelStartX = cursor.getInt(levelStartXIndex);
			levelStartY = cursor.getInt(levelStartYIndex);
			levelFinishX = cursor.getInt(levelFinishXIndex);
			levelFinishY = cursor.getInt(levelFinishYIndex);

			bufLayer1 = new Block[levelSizeY][levelSizeX];
			bufLayer2 = new Block[levelSizeY][levelSizeX];

			selection = DBHelper.KEY_NUM_LEVEL + " = " + levelID;

			blocksCursor = database.query(DBHelper.TABLE_LEVEL_MAPS,null,selection,null,null,null,null);
			blocksCursor.moveToFirst();

			blockXIndex = blocksCursor.getColumnIndex(DBHelper.KEY_BLOCK_X);
			blockYIndex = blocksCursor.getColumnIndex(DBHelper.KEY_BLOCK_Y);
			blockTypeIndex = blocksCursor.getColumnIndex(DBHelper.KEY_BLOCK_TYPE);
			blockShapeIndex = blocksCursor.getColumnIndex(DBHelper.KEY_BLOCK_SHAPE);
			blockIsTorchOnItIndex = blocksCursor.getColumnIndex(DBHelper.KEY_IS_TORCH_ON_BLOCK);

			do{
				blockX = blocksCursor.getInt(blockXIndex);
				blockY = blocksCursor.getInt(blockYIndex);
				blockType = blocksCursor.getString(blockTypeIndex);
				blockShape = blocksCursor.getString(blockShapeIndex);
				blockIsTorchOnIt = blocksCursor.getInt(blockIsTorchOnItIndex);

				bufLayer2[blockY][blockX] = new Block(StringConverter.getTypeByString(blockType),StringConverter.getShapeByString(blockShape),blockIsTorchOnIt==1);
				bufLayer1[blockY][blockX] = new Block(Block.Type.STONE_BACKGROUND, Block.Shape.NONE,false);
			}while (blocksCursor.moveToNext());

			levels.add(new LevelMap(bufLayer1,bufLayer2,levelStartX,levelStartY,levelFinishX,levelFinishY));
		}while (cursor.moveToNext());

	}
}

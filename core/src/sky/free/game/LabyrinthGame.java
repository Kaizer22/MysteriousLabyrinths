package sky.free.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import sky.free.game.Gameplay.LevelMap;

public class LabyrinthGame extends Game {

	SpriteBatch batchUI;
	SpriteBatch batch;
	BitmapFont font;
	Music maintheme;
	Array<LevelMap> allLevels;


	public LabyrinthGame(Array<LevelMap> aL){
		allLevels = aL;
	}
	
	@Override
	public void create () {
		batchUI = new SpriteBatch();
		batch = new SpriteBatch();
		font = new BitmapFont();
		maintheme = Gdx.audio.newMusic(Gdx.files.internal("maintheme.mp3"));
		maintheme.setLooping(true);


		this.setScreen(new IntroScreen(this));
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.render();
		font.dispose();
		batchUI.dispose();
		batch.dispose();
		maintheme.dispose();
		Gdx.app.exit();
	}
}
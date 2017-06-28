package sky.free.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import sky.free.game.LabyrinthGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new LabyrinthGame(), config);
		config.width = 800;
		config.height = 600;
		config.title = "Misteryous Labyrinth";
	}
}

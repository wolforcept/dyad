package levels;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import code.general.Level;

public class LevelLoader {

	public static Level[] levels;

	public static void preload() {
		LinkedList<Level> levelList = new LinkedList<>();

		levelList.add(loadLevelResource("levels/level1.dyle"));
		// levelList.add(loadLevelResource("levels/level2.dyle"));

		levels = new Level[levelList.size()];
		levelList.toArray(levels);
	}

	private static Level loadLevelResource(String path) {
		try {

			InputStream fin = ClassLoader.getSystemClassLoader()
					.getResourceAsStream(path);

			ObjectInputStream ois = new ObjectInputStream(fin);
			Level level = (Level) ois.readObject();
			System.out.println(level.toString());
			ois.close();

			return level;
		} catch (FileNotFoundException e) {
			System.err.println("Could not load " + path);
			System.err.println("File not found");
			return null;
		} catch (IOException e) {
			System.err.println("loading stopped");
			return null;
		} catch (ClassNotFoundException e) {
			System.err.println("Could not load " + path);
			System.err.println("Class not found");
			return null;
		}
	}

	public static Level loadLevel(String path) {

		try {
			FileInputStream fin = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fin);
			Level level = (Level) ois.readObject();
			System.out.println(level.toString());
			ois.close();

			return level;
		} catch (FileNotFoundException e) {
			System.err.println("Could not load " + path);
			System.err.println("File not found");
			return null;
		} catch (IOException e) {
			System.err.println("loading stopped");
			return null;
		} catch (ClassNotFoundException e) {
			System.err.println("Could not load " + path);
			System.err.println("Class not found");
			return null;
		}
	}
}

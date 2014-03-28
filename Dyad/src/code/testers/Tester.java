package code.testers;

import java.awt.FontFormatException;
import java.io.IOException;

import levels.LevelLoader;
import code.general.GameData;
import code.ui.MainMenu;
import code.ui.TaylorData;

public class Tester {

	public static void main(String[] args) {

		try {
			LevelLoader.load();
			TaylorData.init();
			GameData.init();
		} catch (IOException e) {
			System.err.println("inits -> Failed to fetch image data");
		} catch (FontFormatException e) {
			
			System.err.println("inits -> Font Format Exception");
		}
		new MainMenu(true);
	}
}

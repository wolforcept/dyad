package code.testers;

import java.io.IOException;

import levels.LevelLoader;
import code.general.GameData;
import code.ui.MainMenu;
import code.ui.TaylorData;

public class Tester {

	public static void main(String[] args) {

		GameData.init();
		try {
			LevelLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			TaylorData.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new MainMenu();
	}
}

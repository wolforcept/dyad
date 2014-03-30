package code.testers;

import java.io.IOException;

import code.editor.LevelEditor;
import code.ui.TaylorData;

public class DyadLevelEditor {
	public static void main(String[] args) {
		try {
			TaylorData.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new LevelEditor();
	}
}

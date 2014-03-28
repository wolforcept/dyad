package code.general;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameData {

	public static Image title_image, start_button, exit_button;

	public static void init() {

		try {

			ClassLoader loader = ClassLoader.getSystemClassLoader();

			title_image = ImageIO.read(loader
					.getResource("resources/title_image.png"));

			start_button = ImageIO.read(loader
					.getResource("resources/start_button.png"));

			exit_button = ImageIO.read(loader
					.getResource("resources/exit_button.png"));

		} catch (IOException e) {
			System.err.println("GameData.init() -> Failed to fetch image data");
		}

	}
}

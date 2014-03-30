package code.general;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameData {

	public static BufferedImage title_image, mainmenu_button, level_button;

	public static Font font;

	public static void init() throws FontFormatException, IOException {

		ClassLoader loader = ClassLoader.getSystemClassLoader();

		title_image = ImageIO.read(loader
				.getResource("resources/title_image.png"));

		mainmenu_button = ImageIO.read(loader
				.getResource("resources/mainmenu_button.png"));

		level_button = ImageIO.read(loader
				.getResource("resources/level_button.png"));

		font = Font.createFont(
				Font.TRUETYPE_FONT,//
				ClassLoader.getSystemClassLoader().getResourceAsStream(
						"resources/font.ttf"));
	}
}

package code.objects;

import java.awt.Point;

import code.ui.TaylorData;
import code.enums.Material;
import code.objects.Collectable.CollectableType;
import code.objects.Touchable.TouchableType;

public abstract class FieldObject {

	private int currentImage;
	private String name;
	private int numberOfImages;
	private int x, y, strength;

	public FieldObject(String name, int x, int y, int str) {
		try {
			this.name = name;
			currentImage = 0;
			numberOfImages = TaylorData.getNumberOfImages(name);
			this.x = x;
			this.y = y;
			strength = str;
		} catch (Exception e) {
			System.err.println(//
					"Failed to create GameObject "
							+ this.getClass().getSimpleName() + " with name: "
							+ name);
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}

	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getCurrentImage() {
		return currentImage;
	}

	public void incrementImage() {
		currentImage = (currentImage == numberOfImages) ? 0 : currentImage + 1;
	}

	public static FieldObject makeByName(String name, int x, int y) {

		switch (name) {

		// PLAYERS
		case "magus":
			return new Magus(x, y);
		case "champion":
			return new Champion(x, y);

			// HUMANOIDS
		case "humanoid":
			return new Humanoid(x, y);

			// COLLECTABLES
		case "red_key":
			return new Collectable(x, y, CollectableType.RED_KEY);
		case "blue_key":
			return new Collectable(x, y, CollectableType.BLUE_KEY);

			// TOUCHABLES
		case "red_door":
			return new Touchable(x, y, TouchableType.RED_DOOR, Material.WOOD);
		case "blue_door":
			return new Touchable(x, y, TouchableType.BLUE_DOOR, Material.WOOD);
		case "switch1_door":
			return new Touchable(x, y, TouchableType.SWITCH1_DOOR,
					Material.METAL);
		case "switch1":
			return new Touchable(x, y, TouchableType.SWITCH1, Material.METAL);
		case "switch2":
			return new Touchable(x, y, TouchableType.SWITCH2, Material.METAL);

			// WALLS
		case "wood_wall":
			return new Wall(x, y, Material.WOOD);
			// case "stone_wall":
			// return new Wall(x, y, Material.STONE);
		case "brick_wall":
			return new Wall(x, y, Material.BRICK);
			// case "everflamewall":
			// return new Wall(x, y, Material.EVERFLAME);
			// case "evericewall":
			// return new Wall(x, y, Material.EVERICE);

			// OBJECTS
		case "core":
			return new Core(x, y);
		case "scroll":
			return new Collectable(x, y, CollectableType.SCROLL);
		case "door":
			return new Door(x, y);
		default:
			return null;
		}

	}

	public String getName() {
		return name;
	}

	public int getNumberOfImages() {
		return numberOfImages;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int s) {
		strength = s;
	}

	public Point getPosition() {
		return new Point(x, y);
	}

}

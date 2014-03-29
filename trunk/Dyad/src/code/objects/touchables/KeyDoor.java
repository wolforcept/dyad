package code.objects.touchables;

import code.general.Ivory;
import code.objects.Collectable;
import code.objects.player.Player;

public class KeyDoor extends Touchable {

	private String color;

	public KeyDoor(int x, int y, String color) {
		super(color + "_door", x, y, 8);
		this.color = color;
	}

	public void touch(Player p, Ivory ivory) {

		switch (color) {
		case "red":
			if (p.has(Collectable.CollectableType.RED_KEY)) {
				p.removeItemFromInventory(Collectable.CollectableType.RED_KEY);
				ivory.setPosition(getX(), getY(), null);
			}
			break;
		case "blue":
			if (p.has(Collectable.CollectableType.BLUE_KEY)) {
				p.removeItemFromInventory(Collectable.CollectableType.BLUE_KEY);
				ivory.setPosition(getX(), getY(), null);
			}
			break;

		default:
			break;

		}
	}
}

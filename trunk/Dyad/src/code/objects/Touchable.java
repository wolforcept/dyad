package code.objects;

import code.enums.Material;
import code.general.Ivory;

public class Touchable extends FieldObject {

	private TouchableType type;
	private Material material;

	public Touchable(int x, int y, TouchableType type, Material material) {
		super(type.toString().toLowerCase(), x, y, type.str);
		this.type = type;
		this.material = material;
	}

	public enum TouchableType {
		RED_DOOR(1, 8), BLUE_DOOR(1, 8), SWITCH1(2, 8), SWITCH2(2, 8), SWITCH1_DOOR(
				1, 8);

		private int noi, str;

		private TouchableType(int noi, int str) {
			this.noi = noi;
		}

		public int getNoi() {
			return noi;
		}
	}

	public TouchableType getType() {
		return type;
	}

	public Material getMaterial() {
		return material;
	}

	public void touch(Player p, Ivory ivory) {
		switch (type) {
		case RED_DOOR:
			if (p.has(Collectable.CollectableType.RED_KEY)) {
				p.removeItemFromInventory(Collectable.CollectableType.RED_KEY);
				ivory.setPosition(getX(), getY(), null);
			}
			break;
		case BLUE_DOOR:
			if (p.has(Collectable.CollectableType.BLUE_KEY)) {
				p.removeItemFromInventory(Collectable.CollectableType.BLUE_KEY);
				ivory.setPosition(getX(), getY(), null);
			}
			break;

		case SWITCH1:
			ivory.switchSwitch(1);
			break;

		case SWITCH2:
			ivory.switchSwitch(2);
			break;
		case SWITCH1_DOOR:
			break;
		default:
			break;
			
		}

	}

}

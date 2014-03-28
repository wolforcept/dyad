package code.general;

import java.awt.Point;

import code.enums.Facing;
import code.enums.SpellType;
import code.objects.FieldObject;

public class Spell {

	private SpellType type;
	private Point[] area;
	private Facing facing;

	private int x, y, currentImage;

	public Spell(SpellType spellType, int x, int y, Point[] area, Facing facing) {
		this.x = x;
		this.y = y;
		this.type = spellType;
		this.area = area;
		this.facing = facing;
		currentImage = 0;
	}

	/**
	 * @param tar
	 * @return Returns if the target was removed
	 */
	public boolean interact(FieldObject tar) {
		if (tar != null) {
			if (tar.getStrength() < getStrength())
				return true;
		}
		return false;

	}

	public int getStrength() {
		return type.getStrength();
	}

	public SpellType getType() {
		return type;
	}

	public Point[] getArea() {
		return area;
	}

	public Facing getFacing() {
		return facing;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getName() {
		return type.toString().toLowerCase();
	}

	public int getCurrentImage() {
		return currentImage;
	}

	public int getNumberOfImages() {
		return type.getNOI();
	}

	public void incrementImage() {
		currentImage++;
	}

}

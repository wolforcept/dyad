package code.enums;

import java.awt.Point;
import java.util.HashMap;

public enum SpellType {

	METEOR(4, 1, 19, "energy=1:matter=1", new Point(40, 80)), //
	SHATTER(8, 1, 170, "energy=1:mind=1", new Point(20, 20)), //
	CHILLWIND(4, 0, 12, "lethargy=2", new Point(20, 20)), //
	CORRUPTION(4, 1, 6, "matter=1:mind=1", new Point(20, 20)), //
	FORM(4, 1, 6, "energy=1:matter=1", new Point(20, 20)), //
	DARKNESS(4, 2, 19, "mind=1:lethargy=1", new Point(40, 80));

	// COLUMN_OF_FIRE(4, 0, 6, "energy=2:mind=1", new Point(20, 20)), //
	// WORLDS_END(4, 0, 6, "energy=2:matter=1", new Point(20, 20)), //
	// FREEZE(4, 1, 170, "lethargy=1", new Point(20, 20)), //
	// SNOW(4, 2, 6, "lethargy=2", new Point(20, 20)), //
	// CHAOS(4, 1, 12, "mind=2", new Point(20, 20)), //
	// LIGHT(4, 2, 6, "mind=2:energy=1", new Point(20, 20)), //

	private boolean anywhere, everywhere;
	private int noi, noib, strength;
	private HashMap<MagusMana, Integer> manacost;
	private Point centre;
	private String buttonName;

	private SpellType(int strength, int where, int noi, String allcosts,
			Point centre) {
		this.anywhere = (where == 1);
		this.everywhere = (where == 2);
		this.noi = noi;
		this.noib = 1;
		this.strength = strength;
		this.centre = centre;
		this.buttonName = name().toLowerCase() + "_button";

		manacost = new HashMap<>();

		String[] allcostsarray = allcosts.split(":");
		for (String cost : allcostsarray) {
			int val = Integer.parseInt(cost.split("=")[1]);
			MagusMana m = MagusMana.valueOf(cost.split("=")[0].toUpperCase());
			System.out.println(this + " costs " + val + " of " + m);
			manacost.put(m, val);
		}
		for (MagusMana m : MagusMana.values()) {
			if (!manacost.containsKey(m))
				manacost.put(m, 0);
		}

	}

	public boolean isAnywhere() {
		return anywhere;
	}

	public int getNOIB() {
		return noib;
	}

	public int getNOI() {
		return noi;
	}

	public int getStrength() {
		return strength;
	}

	public int getManacost(MagusMana m) {
		return manacost.get(m);
	}

	public Point[] getArea() {
		switch (this) {
		case METEOR:
			return new Point[] { new Point(-1, 0), new Point(0, 0),
					new Point(1, 0), new Point(0, 1), new Point(0, -1) };
			// case COLUMN_OF_FIRE:
			// return new Point[] { new Point(1, 0), new Point(2, 0),
			// new Point(3, 0), new Point(4, 0) };
		case CHILLWIND:
			return new Point[] { new Point(1, 0), new Point(2, 0),
					new Point(3, 0), new Point(4, 0) };
		case FORM:
			return new Point[] { new Point(0, 0), new Point(1, 0),
					new Point(0, 1), new Point(1, 1) };
			// case WORLDS_END:
			// return new Point[] { new Point(1, -2), new Point(2, -1),
			// new Point(2, 0), new Point(2, 1), new Point(1, 2) };
		default:
			return new Point[] { new Point(0, 0) };
		}
	}

	public Point getImageCentre() {
		return centre;
	}

	public boolean isEverywhere() {
		return everywhere;
	}

	public String getButtonName() {
		return buttonName;
	}

}

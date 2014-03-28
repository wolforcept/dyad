package code.general;

import java.awt.Image;
import java.util.HashMap;

import code.enums.MagusMana;
import code.enums.SpellType;
import code.ui.Measures;
import code.ui.TaylorData;

public class SpellButton {

	private static int X, Y, W, H;

	public static void setPlaceInWindow(int x, int y, int w, int h) {
		X = x;
		Y = y;
		W = w;
		H = h;
	}

	private SpellType type;
	private int orderNr;
	private Measures m;

	public SpellButton(SpellType type, int orderNr, Measures m) {
		this.type = type;
		this.orderNr = orderNr;
		this.m = m;
	}

	public SpellType getSpellType() {
		return type;
	}

	public boolean isPossible(HashMap<MagusMana, Integer> mana_avaliable) {
		for (MagusMana m : MagusMana.values()) {

			int required = type.getManacost(m);
			if (mana_avaliable.containsKey(m)) {
				if (mana_avaliable.get(m) < required)
					return false;
			} else {
				if (required > 0)
					return false;
			}
		}
		return true;
	}

	public Image getImage(TaylorData data) {
		try {
			return data.getImage(type.getButtonName());
		} catch (Exception e) {
			System.err.println("Unable to get Button Image for " + type.name());
			System.exit(0);
			return null;
		}
	}

	public int getX() {
		return m.centerX + X;
	}

	public int getY() {
		return Y + H * orderNr;
	}

	public int getW() {
		return W;
	}

	public int getH() {
		return H;
	}

}

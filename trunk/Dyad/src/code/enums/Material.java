package code.enums;

public enum Material {

	WOOD(1), BRICK(7), METAL(9);
	// WOOD(1), STONE(6), BRICK(7), EVERFLAME(5), EVERICE(5), METAL(9);

	private int strength;

	private Material(int s) {
		this.strength = s;
	}

	public int getStrength() {
		return strength;
	}

}

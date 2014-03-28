package code.objects;

import code.enums.Material;

public class Wall extends FieldObject {

	private Material walltype;

	public Wall(int x, int y, Material walltype) {
		super(walltype.toString().toLowerCase() + "_wall", x, y, walltype
				.getStrength());
		this.walltype = walltype;
	}

	public Material getWalltype() {
		return walltype;
	}
}

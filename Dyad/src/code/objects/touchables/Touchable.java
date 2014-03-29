package code.objects.touchables;

import code.general.Ivory;
import code.objects.FieldObject;
import code.objects.player.Player;

public abstract class Touchable extends FieldObject {

	public Touchable(String name, int x, int y, int str) {
		super(name, x, y, str);
	}

	public abstract void touch(Player p, Ivory ivory);

}

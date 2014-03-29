package code.objects.touchables;

import code.general.Ivory;
import code.objects.player.Player;

public class Switch extends Touchable {

	private int id;

	public Switch(int x, int y, int id) {
		super("switch", x, y, 8);
		this.id = id;
	}

	public void touch(Player p, Ivory ivory) {
		ivory.switchTouchablesById(id);
	}
}

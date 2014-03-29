package code.objects;

public class SwitchDoor extends FieldObject {

	private int id;
	private boolean isOpened;

	public SwitchDoor(int x, int y, int id, boolean isOpened) {
		super("switch_door", x, y, 5); // TODO SWITCH DOOR STRENGHT
		this.id = id;
		this.isOpened = isOpened;
	}

	public int getId() {
		return id;
	}

	public boolean isOpened() {
		return isOpened;
	}

}

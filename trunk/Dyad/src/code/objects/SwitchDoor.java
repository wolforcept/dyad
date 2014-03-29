package code.objects;


public class SwitchDoor extends FieldObject {

	private int id;

	public SwitchDoor(int x, int y, int id) {
		super("switch_door", x, y, 5); // TODO SWITCH DOOR STRENGHT
		this.id = id;
	}

	public int getId() {
		return id;
	}

}

package code.objects;

import code.enums.State;

public class Humanoid extends FieldObject {

	private State state;
	private String humanoidName;
	private int direction;

	public Humanoid(int x, int y) {
		super("humanoid", x, y, 0);
		int humanoidNr = (int) (Math.random() * 10);
		humanoidName = createHumanoidName(humanoidNr);
	}

	private String createHumanoidName(int i) {
		switch (i) {
		case 0:
			return "pete";
		case 1:
			return "mark";
		case 2:
			return "shoy";
		case 3:
			return "norm";
		case 4:
			return "colt";
		case 5:
			return "bach";
		case 6:
			return "saur";
		case 7:
			return "buma";
		case 8:
			return "chev";
		case 9:
			return "seth";
		default:
			return "nobody";
		}
	}

	public State getState() {
		return state;
	}

	public String getHumanoidName() {
		return humanoidName;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public int getDirection() {
		return direction;
	}
}

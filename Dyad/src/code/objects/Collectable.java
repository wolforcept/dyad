package code.objects;

public class Collectable extends FieldObject {

	private CollectableType type;

	public Collectable(int x, int y, CollectableType type) {
		super(type.toString().toLowerCase(), x, y, type.str);
		this.type = type;
	}

	public enum CollectableType {
		SCROLL(1, 1), RED_KEY(1, 5), BLUE_KEY(1, 5);

		private int noi, str;

		private CollectableType(int noi, int str) {
			this.noi = noi;
			this.str = str;
		}

		public int getNoi() {
			return noi;
		}
	}

	public CollectableType getType() {
		return type;
	}

}

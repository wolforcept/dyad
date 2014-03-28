package code.general;

public class UnbuiltObject {
	String obj;
	int x, y;
	String[] properties;

	public UnbuiltObject(String obj, int x, int y, String[] properties) {
		this.obj = obj;
		this.x = x;
		this.y = y;
		this.properties = properties;
	}

	@Override
	public String toString() {
		return obj + " (" + x + "," + y + ") " + (properties != null);
	}
}
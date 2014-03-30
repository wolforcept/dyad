package code.general;

import java.io.Serializable;
import java.util.HashMap;

public class UnbuiltObject implements Serializable {

	private static final long serialVersionUID = 1L;

	String obj;
	int x, y;
	HashMap<String, String> properties;

	public UnbuiltObject(String obj, int x, int y,
			HashMap<String, String> properties) {
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
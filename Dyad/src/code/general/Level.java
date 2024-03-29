package code.general;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

import code.enums.ObjectiveType;

public class Level implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	private Dimension gridSize;
	private HashMap<String, Integer> mana;
	private ObjectiveType objective;
	private Object target;
	private LinkedList<UnbuiltObject> objectList;

	public Level(String title, Dimension gridSize,
			HashMap<String, Integer> mana, ObjectiveType objective,
			Object target, LinkedList<UnbuiltObject> objectList) {
		this.title = title;
		this.gridSize = gridSize;
		this.mana = mana;
		this.objective = objective;
		this.target = target;
		this.objectList = objectList;
	}

	public ObjectiveType getObjective() {
		return objective;
	}

	public LinkedList<UnbuiltObject> getObjectList() {
		return objectList;
	}

	public HashMap<String, Integer> getMana() {
		return mana;
	}

	public Dimension getGridSize() {
		return gridSize;
	}

	public Object getTarget() {
		return target;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return "Level [title=" + title + ", gridSize=" + gridSize + ", mana="
				+ mana + ", objective=" + objective + ", target=" + target
				+ ", objectList=" + objectList + "]";
	}

}

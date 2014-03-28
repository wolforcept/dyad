package code.objects;

import java.util.Iterator;
import java.util.LinkedList;

import code.objects.Collectable.CollectableType;

public class Player extends FieldObject {

	public static final int INVENTORY_ROOM = 6;
	LinkedList<Collectable> inventory;

	public Player(String name, int x, int y, int str) {
		super(name, x, y, str);
		inventory = new LinkedList<Collectable>();
	}

	@Override
	public int getStrength() {
		return 10;
	}

	public LinkedList<Collectable> getInventoryClone() {
		return new LinkedList<Collectable>(inventory);
	}

	public boolean winCollectable(Collectable c) {
		if (inventory.size() < INVENTORY_ROOM) {
			inventory.add(c);
			return true;
		}
		return false;
	}

	public void removeItemFromInventory(CollectableType redKey) {

		for (Iterator<Collectable> iterator = inventory.iterator(); iterator
				.hasNext();) {
			Collectable c2 = (Collectable) iterator.next();
			if (redKey.equals(c2.getType())) {
				iterator.remove();
			}
		}
	}

	public boolean has(CollectableType c2) {
		for (Collectable c : getInventoryClone()) {
			if (c.getType().equals(c2))
				return true;
		}
		return false;
	}

	public int getInventorySize() {
		return inventory.size();
	}
}

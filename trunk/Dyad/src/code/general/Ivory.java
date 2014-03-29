package code.general;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import code.auxis.Auxi;
import code.enums.Facing;
import code.enums.MagusMana;
import code.enums.ObjectiveType;
import code.enums.SpellType;
import code.objects.Collectable;
import code.objects.FieldObject;
import code.objects.SwitchDoor;
import code.objects.player.Champion;
import code.objects.player.Magus;
import code.objects.player.Player;
import code.ui.Measures;

public class Ivory {

	public static final int REFRESH_FREQUENCY = 60;
	public static final Dimension NUMBER_DIMENIONS = new Dimension(12, 12);
	// private static final int NUMBER_OF_SWITCHES = 2;
	private static final int BUTTON_SIZE = 60;
	public static int CELL_SIZE = 40;

	private Point mouse;

	private FieldObject[][] field;

	private boolean selected;

	private Magus magus;
	private Champion champion;

	private Spell spell;
	private SpellType spelltype;

	private Object target;
	private ObjectiveType objective;

	private HashMap<MagusMana, Integer> manapool;

	private LinkedList<SwitchDoor> hiddenSwitchDoors;

	private int width, height;
	private SpellButton[] spellButtons;

	private Measures m;

	private String title;

	// private boolean[] switches;

	public Ivory(Level level) {

		title = level.getTitle();

		m = new Measures();

		target = level.getTarget();
		objective = level.getObjective();
		hiddenSwitchDoors = new LinkedList<>();

		manapool = new HashMap<>();
		for (Entry<String, Integer> e : level.getMana().entrySet()) {
			manapool.put(MagusMana.valueOf(e.getKey().toUpperCase()),
					e.getValue());
		}
		for (MagusMana m : MagusMana.values()) {
			if (!manapool.containsKey(m))
				manapool.put(m, 0);
		}

		width = level.getGridSize().width;
		height = level.getGridSize().height;
		field = new FieldObject[width][height];

		mouse = new Point(0, 0);

		SpellType[] vals = SpellType.values();
		SpellButton.setPlaceInWindow(220, 100, BUTTON_SIZE, BUTTON_SIZE);
		spellButtons = new SpellButton[vals.length];
		for (int i = 0; i < vals.length; i++)
			spellButtons[i] = //
			new SpellButton(vals[i], i, m);

		spell = null;

		// switches = new boolean[Ivory.NUMBER_OF_SWITCHES];

		for (UnbuiltObject o : level.getObjectList()) {

			HashMap<String, String> properties = new HashMap<>();
			if (o.properties != null)
				for (int j = 0; j < o.properties.length; j++) {
					String[] property = o.properties[j].split("=");
					properties.put(property[0], property[1]);
				}

			System.out.println("putting " + o.obj + " on " + o.x + "," + o.y);
			field[o.x][o.y] = FieldObject.makeByName(o.obj, o.x, o.y,
					properties);
			if (o.obj.equals("champion")) {
				champion = (Champion) field[o.x][o.y];
				selected = true;
			}
			if (o.obj.equals("magus")) {
				magus = (Magus) field[o.x][o.y];
				selected = false;
			}

		}
	}

	public SpellButton[] getSpellButtons() {
		return spellButtons;
	}

	public FieldObject[][] getField() {
		return field;
	}

	public Magus getMagus() {
		return magus;
	}

	public Champion getChampion() {
		return champion;
	}

	public FieldObject getFieldObject(String name) {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j] != null && name.equals(field[i][j].getName())) {
					return field[i][j];
				}
			}
		}
		return null;
	}

	public void setMouse(Point mouse) {
		this.mouse.x = mouse.x;
		this.mouse.y = mouse.y;
	}

	public Point getMouse() {
		return mouse;
	}

	public void move(int x1, int y1, int x2, int y2) {
		field[x2][y2] = field[x1][y1];
		field[x2][y2].setPos(x2, y2);
		field[x1][y1] = null;
	}

	public boolean getSelected() {
		return selected;
	}

	public void toggleSelected() {
		selected = selected ? false : true;
	}

	public void using(SpellType spellType) {
		this.spelltype = spellType;
	}

	public boolean using() {
		return spelltype != null;
	}

	public SpellType getUsing() {
		return spelltype;
	}

	public void stopUse() {
		spelltype = null;
		spell = null;
	}

	public void startUse() {

		if (mouse.x < m.fieldX || mouse.y < m.fieldY || //
				mouse.x > m.fieldX2 || mouse.y > m.fieldY2 || //
				spelltype == null)
			return;

		System.out.println(spelltype + " STARTED");

		Point[] a = spelltype.getArea();
		Player p = selected ? getMagus() : getChampion();

		int x = 0, y = 0;

		if (spelltype.isAnywhere()) {
			if (mouse.x > m.fieldX && mouse.y > m.fieldY && mouse.x < m.fieldX2
					&& mouse.y < m.fieldY2) {
				x = (int) ((mouse.x - m.fieldX) / CELL_SIZE);
				y = (int) ((mouse.y - m.fieldY) / CELL_SIZE);

			}
		} else {
			x = p.getX();
			y = p.getY();
		}

		if (x < m.fieldWidth && x >= 0 && y < m.fieldHeight && y >= 0) {

			System.out.println("from " + p.getX() + "," + p.getY()
					+ " CREATED SPELL at " + x + "," + y + " dir = "
					+ getCurrentFacing());

			spell = new Spell(spelltype, x, y, a, getCurrentFacing());

			for (MagusMana mana : MagusMana.values()) {
				int cost = spelltype.getManacost(mana);
				if (cost > 0) {
					System.out.println("reducing " + mana + " by " + cost);
					reduceMana(mana, cost);
				}
			}
		}

		spelltype = null;

	}

	private void reduceMana(MagusMana m, int manacost) {
		int newValue = manapool.get(m) - manacost;
		manapool.put(m, newValue);
	}

	public Spell getSpell() {
		return spell;
	}

	public FieldObject getObjectAt(int x, int y) {
		return field[x][y];
	}

	public void setPosition(int x, int y, FieldObject object) {
		field[x][y] = object;
	}

	public HashMap<MagusMana, Integer> getMana() {
		return manapool;
	}

	public Dimension getFieldSize() {
		return new Dimension(width, height);
	}

	public void switchTouchablesById(int id) {
		LinkedList<SwitchDoor> switcheDoorsToHide = new LinkedList<>();
		for (int x = 0; x < m.fieldWidth; x++) {
			for (int y = 0; y < m.fieldHeight; y++) {

				FieldObject o = field[x][y];

				if (o instanceof SwitchDoor && ((SwitchDoor) o).getId() == id) {
					switcheDoorsToHide.add((SwitchDoor) o);
					field[x][y] = null;
				}
			}
		}
		for (Iterator<SwitchDoor> iterator = hiddenSwitchDoors.iterator(); iterator
				.hasNext();) {
			SwitchDoor touchable = (SwitchDoor) iterator.next();
			if (touchable.getId() == id) {
				field[touchable.getX()][touchable.getY()] = touchable;
				iterator.remove();
			}
		}

		hiddenSwitchDoors.addAll(switcheDoorsToHide);
	}

	public ObjectiveType getObjective() {
		return objective;
	}

	public Measures getMeasures() {
		return m;
	}

	public Facing getCurrentFacing() {
		Player p = selected ? getMagus() : getChampion();
		double dir = Math.toDegrees(Auxi.point_direction(
				realLocation(p.getX()), realLocation(p.getY()), mouse.x,
				mouse.y));
		return Auxi.getFacing(dir);
	}

	private double realLocation(int l) {
		return m.fieldX + m.cz * l + m.cz / 2;
	}

	public void finishSpell() {
		spell = null;
	}

	public Object getTarget() {
		return target;
	}

	public int getScrollNumber() {
		int scrolls = 0;

		LinkedList<Collectable> allCollectables = getMagus()
				.getInventoryClone();
		allCollectables.addAll(getChampion().getInventoryClone());

		for (Collectable collectable : allCollectables) {
			if (collectable.getType()
					.equals(Collectable.CollectableType.SCROLL))
				scrolls++;
		}
		return scrolls;
	}

	public String getTitle() {
		return title;
	}
}

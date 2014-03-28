package code.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import code.enums.MagusMana;
import code.enums.Material;
import code.enums.SpellType;
import code.objects.Collectable;
import code.objects.Touchable;

public class TaylorData {

	public static Dimension TAYLOR_SIZE = new Dimension(600, 600);
	private static Hashtable<String, Animation> images;

	public static void init() throws IOException {
		images = new Hashtable<>();
		addImage("magus");
		addImage("champion");
		addImage("magus_glow");
		addImage("champion_glow");
		addImage("core", 360);
		addImage("humanoid");
		addImage("back");
		addImage("spell_not_available");

		addImage("unable_button", "spells/unable_button");

		for (int i = 0; i < 10; i++) {
			String name = "number_" + i;
			addImage(name, "numbers/" + name);
		}

		Material[] materials = Material.values();
		for (int i = 0; i < materials.length; i++) {
			String name = materials[i].toString().toLowerCase() + "_wall";
			addImage(name, "walls/" + name);
		}

		MagusMana[] manas = MagusMana.values();
		for (int i = 0; i < manas.length; i++) {
			String name = manas[i].toString().toLowerCase();
			addImage(name, "mana/" + name);
		}

		Collectable.CollectableType[] collectables = Collectable.CollectableType
				.values();
		for (int i = 0; i < collectables.length; i++) {
			String name = collectables[i].toString().toLowerCase();
			addImage(name, "collectables/" + name, collectables[i].getNoi());
		}

		Touchable.TouchableType[] touchables = Touchable.TouchableType.values();
		for (int i = 0; i < touchables.length; i++) {
			String name = touchables[i].toString().toLowerCase();
			addImage(name, "touchables/" + name, touchables[i].getNoi());
		}

		SpellType[] vals = SpellType.values();
		for (int i = 0; i < vals.length; i++) {
			String name = vals[i].toString().toLowerCase();
			addImage(name, "spells/" + name, vals[i].getNOI(),
					vals[i].getImageCentre());
			addImage(name + "_button", "spells/" + name + "_button",
					vals[i].getNOIB());
		}

	}

	private static void addImage(String name, String location, int noi, Point p)
			throws IOException {
		String path = "resources/" + location + ".png";
		System.out.println("created image <" + name + "> from <" + path + ">");
		BufferedImage img = ImageIO.read(new TaylorData().getClass()
				.getClassLoader().getResource(path));
		Animation anim = new Animation(img, 1, noi);
		images.put(name, anim);

	}

	private static void addImage(String name, String location, int noi)
			throws IOException {
		addImage(name, location, noi, null);
	}

	private static void addImage(String name, int noi) throws IOException {
		addImage(name, name, noi, null);
	}

	private static void addImage(String name) throws IOException {
		addImage(name, name, 1, null);
	}

	private static void addImage(String name, String location)
			throws IOException {
		addImage(name, location, 1, null);
	}

	public Animation getImages(String name) {
		try {
			if (images.containsKey(name)) {
				return images.get(name);
			} else {

				throw new Exception(//
						"Image " + name
								+ " not present in Images StaticHashtable");

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public BufferedImage getImage(String name) {
		try {
			if (images.containsKey(name)) {
				return images.get(name).getImage(0);
			} else {

				throw new Exception(//
						"Image " + name
								+ " not present in Images StaticHashtable");

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int getNumberOfImages(String name) throws Exception {
		if (images.containsKey(name)) {
			return images.get(name).getLen();
		} else {
			throw new Exception(//
					"Image " + name + " not present in Images Static Hashtable");
		}
	}

}

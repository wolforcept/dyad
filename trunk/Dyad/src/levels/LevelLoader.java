package levels;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;

import code.enums.ObjectiveType;
import code.general.Level;
import code.general.UnbuiltObject;

public class LevelLoader {

	public static Level[] levels = new Level[3];

	public static void load() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(
				ClassLoader.getSystemClassLoader().getResourceAsStream(
						"levels/levels.txt")));

		int i = 0;

		do {
			String nextLine = null;
			do {
				nextLine = br.readLine();
				if (nextLine == null)
					return;
			} while (!nextLine.contains("{"));

			/*
			 * READ TITLE
			 */

			String title = nextLine.substring(0, nextLine.indexOf("{"));
			System.out.println("------------- " + title + " --------------");

			/*
			 * READ GRID SIZE
			 */
			String[] gridSizeline = br.readLine().replaceAll("\\s", "")
					.split(",");
			Dimension gridSize = new Dimension(
					Integer.parseInt(gridSizeline[0]),
					Integer.parseInt(gridSizeline[1]));
			System.out.println(gridSize);

			/*
			 * READ MANA
			 */
			HashMap<String, Integer> mana = new HashMap<>();

			String[] manaline = br.readLine().replaceAll("\\s", "").split(",");
			for (String s : manaline) {
				String[] thisMana = s.split("=");
				mana.put(thisMana[0], Integer.parseInt(thisMana[1]));
			}
			System.out.println(mana.toString());

			/*
			 * READ OBJECTIVE
			 */
			String[] objectiveline = br.readLine().replaceAll("\\s", "")
					.split(":");
			ObjectiveType objective = //
			ObjectiveType.valueOf(objectiveline[0].toUpperCase());
			Object target = null;
			switch (objective) {
			case RETRIEVE_SCROLLS:
				target = Integer.parseInt(objectiveline[1]);
				break;

			default:
				break;
			}

			LinkedList<UnbuiltObject> objectList = new LinkedList<>();

			/*
			 * READ OBJECTS
			 */

			boolean exit = false;

			while (!exit) {

				String line = br.readLine().replaceAll("\\s", "");

				if (line.equals("}")) {

					exit = true;

				} else {

					String[] objLine = line.split("/");
					String[] obj = objLine[0].split(":");
					String[] point = obj[1].split(",");

					String objName = obj[0];
					int x = Integer.parseInt(point[0]);
					int y = Integer.parseInt(point[1]);

					String[] properties = {};
					if (objLine.length == 2) {
						properties = objLine[1].split(";");
					}

					UnbuiltObject o = //
					new UnbuiltObject(objName, x, y, properties);

					System.out.println(o);
					objectList.add(o);

				}
			}

			levels[i++] = new Level(title, gridSize, mana, objective, target,
					objectList);

		} while (true);
	}
}

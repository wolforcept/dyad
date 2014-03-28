package code.general;

import java.awt.Point;

import javax.swing.JFrame;

import code.auxis.Auxi;
import code.objects.FieldObject;
import code.ui.MainMenu;
import code.ui.Taylor;

public class ControllerThread extends Thread {

	Ivory ivory;
	Taylor taylor;
	JFrame frame;

	public ControllerThread(Ivory ivory, Taylor taylor, JFrame frame) {
		this.ivory = ivory;
		this.taylor = taylor;
		this.frame = frame;
	}

	public void run() {
		try {
			while (true) {
				setName("Updater");
				updateSpell();

				if (checkFinish()) {
					taylor.showFinish(true);
					taylor.repaint();
					sleep(1000);
					frame.dispose();
					new MainMenu();
					return;
				}

				taylor.repaint();
				sleep(1000 / Ivory.REFRESH_FREQUENCY);

			}
		} catch (InterruptedException e) {
			System.err
					.println("Updater interrupted\nController.Thread() {...}.run()");
			System.exit(0);
			System.gc();
		}

	}

	private boolean checkFinish() {

		switch (ivory.getObjective()) {

		case RETRIEVE_SCROLLS:

			return ivory.getScrollNumber() == (int) ivory.getTarget();

		default:
			return true;
		}
	}

	private void updateSpell() {

		Spell spell = ivory.getSpell();
		if (spell == null)
			return;

		FieldObject[][] field = ivory.getField();

		if (spell.getCurrentImage() == spell.getNumberOfImages()) {
			Point[] area = spell.getArea();
			// Array coordinates of the spell centre
			int x = spell.getX(), y = spell.getY();
			if (!spell.getType().isAnywhere()) {
				Auxi.rotatePointMatrixWithDir(area, spell.getFacing());
			}
			for (int p = 0; p < area.length; p++) {
				int px = x + area[p].x;
				int py = y + area[p].y;
				if (px >= 0 && py >= 0 && px < field.length
						&& py < field[0].length)

					if (spell.interact(field[px][py])) {
						field[px][py] = null;
					}
			}
			ivory.finishSpell();
		}
		spell.incrementImage();

	}
}

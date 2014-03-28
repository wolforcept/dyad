package code.general;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;

import code.auxis.Auxi;
import code.objects.Collectable;
import code.objects.Player;
import code.objects.Touchable;
import code.ui.Taylor;
import code.ui.TaylorData;

public class Controller {

	private JFrame frame;
	private Taylor taylor;
	private Ivory ivory;

	public Controller(Level level) throws IOException, IllegalArgumentException {

		if (level == null)
			throw new IllegalArgumentException("NULL LEVEL");

		frame = new JFrame("Dyad");

		ivory = new Ivory(level);

		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					ivory.toggleSelected();
				}

				switch (e.getKeyCode()) {

				case KeyEvent.VK_W:
					movePlayer(0, -1);
					break;

				case KeyEvent.VK_S:
					movePlayer(0, 1);
					break;

				case KeyEvent.VK_A:
					movePlayer(-1, 0);
					break;

				case KeyEvent.VK_D:
					movePlayer(1, 0);
					break;

				}
			}
		});

		taylor = new Taylor(ivory);

		taylor.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				ivory.setMouse(e.getPoint());
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}
		});

		taylor.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (ivory.using()) {
						ivory.startUse();
					}
				} else
					ivory.stopUse();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1
						&& ivory.getSpell() == null) {
					SpellButton[] a = ivory.getSpellButtons();
					for (int i = 0; i < a.length; i++) {
						if (a[i].isPossible(ivory.getMana())) {
							if (Auxi.collides(e.getPoint(), a[i])) {
								ivory.using(a[i].getSpellType());
								System.out.println("using now "
										+ a[i].getSpellType());
							}
						}
					}
				}
			}
		});

		frame.add(taylor);
		taylor.requestFocus();
		new ControllerThread(ivory, taylor, frame).start();
		taylor.setPreferredSize(TaylorData.TAYLOR_SIZE);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

	}

	private void movePlayer(int xx, int yy) {
		Player selected;
		if (ivory.getSelected()) {
			selected = (Player) ivory.getFieldObject("magus");
		} else {
			selected = (Player) ivory.getFieldObject("champion");
		}
		int x = selected.getX();
		int y = selected.getY();
		int x2 = x + xx;
		int y2 = y + yy;

		if (x2 >= 0 && y2 >= 0 && x2 < ivory.getFieldSize().width
				&& y2 < ivory.getFieldSize().height) {
			if (ivory.getObjectAt(x2, y2) != null) {
				if (ivory.getObjectAt(x2, y2) instanceof Collectable
						&& selected.getInventorySize() < Player.INVENTORY_ROOM) {
					selected.winCollectable((Collectable) ivory.getObjectAt(x2,
							y2));
					ivory.move(x, y, x2, y2);
				}
				if (ivory.getObjectAt(x2, y2) instanceof Touchable) {
					((Touchable) ivory.getObjectAt(x2, y2)).touch(selected,
							ivory);
				}
			} else if (ivory.getObjectAt(x2, y2) == null) {
				ivory.move(x, y, x2, y2);
			}
		}
	}
}

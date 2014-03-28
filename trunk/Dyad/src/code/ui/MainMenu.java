package code.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import levels.LevelLoader;
import code.general.Controller;
import code.general.GameData;
import code.general.Level;

public class MainMenu {
	class LevelGroup {
		String name;
		JList<LevelEntry> levelList;

	}

	class LevelEntry {
		String name;
		Level level;

		public LevelEntry(String name, Level level) {
			this.name = name;
			this.level = level;
		}

		public Level getLevel() {
			return level;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	JFrame frame;
	JList<LevelGroup> levelList;

	public MainMenu() {

		frame = new JFrame("Dyad");
		// frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel content = new PanelWithBackground(GameData.title_image);
		content.setLayout(new BorderLayout());
		frame.add(content);

		JPanel buttons_main = new JPanel(new FlowLayout());
		buttons_main.setOpaque(false);
		content.add(buttons_main, BorderLayout.SOUTH);

		JButton button_start = new ImageButton(GameData.start_button);
		buttons_main.add(button_start);

		JButton button_exit = new ImageButton(GameData.exit_button);
		buttons_main.add(button_exit);

		button_start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new Controller(LevelLoader.levels[0]);
					frame.setVisible(false);
					frame.dispose();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});

		button_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		/*
		 * JButton button_exit = new JButton("Exit"); buttons.add(button_exit);
		 */

		/*
		 * JPanel listAndStartButtonPanel = new JPanel();
		 * listAndStartButtonPanel.setLayout(new BorderLayout());
		 * 
		 * LevelEntry[] levels = new LevelEntry[Level.getNumberOfLevels()]; for
		 * (int i = 0; i < levels.length; i++) { levels[i] = new
		 * LevelEntry("Level " + (i + 1), Level.getLevel(i + 1)); } levelList =
		 * new JList<>(levels); levelList.setSelectedIndex(0);
		 * levelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 * listAndStartButtonPanel.add(levelList, BorderLayout.CENTER);
		 * 
		 * JButton startButton = new JButton("Start");
		 * listAndStartButtonPanel.add(startButton, BorderLayout.SOUTH);
		 * startButton.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { try { new
		 * Controller(levelList.getSelectedValue().getLevel());
		 * frame.setVisible(false); frame.dispose(); } catch (IOException ex) {
		 * ex.printStackTrace(); } } });
		 * 
		 * frame.add(listAndStartButtonPanel, BorderLayout.EAST);
		 */

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	class PanelWithBackground extends JPanel {

		private static final long serialVersionUID = 1L;
		Image background;

		public PanelWithBackground(Image background) {
			this.background = background;
			setPreferredSize(new Dimension(background.getWidth(this),
					background.getHeight(this)));
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (background != null) {
				Insets insets = getInsets();

				int width = getWidth() - 1 - (insets.left + insets.right);
				int height = getHeight() - 1 - (insets.top + insets.bottom);

				int x = (width - background.getWidth(this)) / 2;
				int y = (height - background.getHeight(this)) / 2;

				g.drawImage(background, x, y, this);
			}
		}

	}

	class ImageButton extends JButton {

		private static final long serialVersionUID = 1L;
		Image background;

		public ImageButton(Image background) {
			this.background = background;
			setPreferredSize(new Dimension(background.getWidth(this),
					background.getHeight(this)));
		}

		@Override
		public void paint(Graphics g) {

			if (background != null) {
				g.drawImage(background, 0, 0, this);
			}
		}

	}
}

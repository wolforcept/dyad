package code.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import levels.LevelLoader;
import code.general.GameData;
import code.general.Level;
import code.general.Controller;

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

	private JFrame frame;
	// private JList<LevelGroup> levelList;

	private String MAINMENU_CARD = "mainmanu", PLAY_CARD = "tutorial";
	private JPanel content;
	private ImageButton button_start, button_exit;

	public MainMenu(boolean firstTime) {

		frame = new JFrame("Dyad");
		// frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		content = new PanelWithBackground(GameData.title_image);
		content.setLayout(new CardLayout());
		frame.add(content);

		{
			JPanel mainMenu = new JPanel(new BorderLayout());
			content.add(mainMenu, MAINMENU_CARD);

			JPanel buttons_main = new JPanel(new FlowLayout());
			buttons_main.setOpaque(false);
			mainMenu.setOpaque(false);
			mainMenu.add(buttons_main, BorderLayout.SOUTH);

			button_start = new ImageButton(GameData.start_button);
			buttons_main.add(button_start);

			button_exit = new ImageButton(GameData.exit_button);
			buttons_main.add(button_exit);

			button_start.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					((CardLayout) (content.getLayout())).//
							show(content, PLAY_CARD);
				}
			});

			button_exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
		}

		// LEVELS CARD
		{
			JPanel levelsMenu = new JPanel(new GridLayout(3, 10));
			levelsMenu.setOpaque(false);
			content.add(levelsMenu, PLAY_CARD);

			for (int i = 0; i < 30; i++) {
				if (i < LevelLoader.levels.length) {
					final Level level = LevelLoader.levels[i];

					ImageButton lb = new ImageButton(GameData.level_button, ""
							+ (i + 1));
					lb.setOpaque(false);
					levelsMenu.add(lb);

					lb.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							try {
								new Controller(level);
								frame.setVisible(false);
								frame.dispose();
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
					});
				}
			}
		}

		((CardLayout) (content.getLayout())).//
				show(content, firstTime ? MAINMENU_CARD : PLAY_CARD);

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
		private Image background;
		private String text;

		public ImageButton(Image background) {
			this.background = background;
			setPreferredSize(new Dimension(background.getWidth(this),
					background.getHeight(this)));
			text = null;
		}

		public ImageButton(Image background, String text) {
			this(background);
			this.text = text;
		}

		@Override
		public void paint(Graphics g) {

			if (background != null) {
				g.drawImage(background, 0, 0, this);
			}

			if (text != null) {
				Font font = GameData.font.deriveFont(50f);
				g.setFont(font);
				((Graphics2D) g).setRenderingHints(new RenderingHints(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
				FontMetrics fm = getFontMetrics(font);
				int tx = 75 - fm.stringWidth(text);
				int ty = 65;
				g.setColor(new Color(0, 0, 0, 0.7f));
				g.drawString(text, tx + 2, ty + 2);
				g.setColor(new Color(1, 1, 1, 0.8f));
				g.drawString(text, tx, ty);
			}
		}
	}
}

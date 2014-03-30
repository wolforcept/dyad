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
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import levels.LevelLoader;
import code.editor.LevelEditor;
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

	private JFrame frame;
	// private JList<LevelGroup> levelList;

	final private String MAINMENU_CARD = "mainmanu", PLAY_CARD = "tutorial";
	final int MAINMENU_BUTTON_Y_DISTANCE = -8;

	private JPanel content;
	private ImageButton button_start, button_editor, button_exit,
			button_custom, button_back;

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

			button_start = new ImageButton(GameData.mainmenu_button, "Start",
					MAINMENU_BUTTON_Y_DISTANCE);
			buttons_main.add(button_start);

			button_custom = new ImageButton(GameData.mainmenu_button, "Custom",
					MAINMENU_BUTTON_Y_DISTANCE);
			buttons_main.add(button_custom);

			button_editor = new ImageButton(GameData.mainmenu_button, "Editor",
					MAINMENU_BUTTON_Y_DISTANCE);
			buttons_main.add(button_editor);

			button_exit = new ImageButton(GameData.mainmenu_button, "Exit",
					MAINMENU_BUTTON_Y_DISTANCE);
			buttons_main.add(button_exit);

			button_start.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					((CardLayout) (content.getLayout())).//
							show(content, PLAY_CARD);
				}
			});

			button_editor.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					new LevelEditor();
					frame.setVisible(false);
					frame.dispose();
				}
			});

			button_custom.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser filechooser = new JFileChooser();
					filechooser
							.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					filechooser.setMultiSelectionEnabled(false);
					filechooser.setAcceptAllFileFilterUsed(false);
					FileNameExtensionFilter filter = new FileNameExtensionFilter(
							".dyle", "dyle");
					filechooser.setFileFilter(filter);

					int opt = filechooser.showOpenDialog(null);

					if (opt == JFileChooser.APPROVE_OPTION) {
						String path = filechooser.getSelectedFile()
								.getAbsolutePath();

						try {
							new Controller(LevelLoader.loadLevel(path), true);
						} catch (IllegalArgumentException | IOException e) {
							System.out.println("Could not init controller");
						}
						frame.setVisible(false);
						frame.dispose();
					}
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
			JPanel levelsMenu = new JPanel(new FlowLayout());
			levelsMenu.setOpaque(false);
			content.add(levelsMenu, PLAY_CARD);

			button_back = new ImageButton(GameData.mainmenu_button, "Back",
					MAINMENU_BUTTON_Y_DISTANCE);
			levelsMenu.add(button_back);

			button_back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					((CardLayout) (content.getLayout())).//
							show(content, MAINMENU_CARD);
				}
			});

			for (int i = 0; i < LevelLoader.levels.length; i++) {
				final Level level = LevelLoader.levels[i];

				ImageButton lb = new ImageButton(GameData.level_button, ""
						+ (i + 1), -10);
				lb.setOpaque(false);
				levelsMenu.add(lb);

				lb.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (level != null)
							try {
								new Controller(level, false);
								frame.setVisible(false);
								frame.dispose();
							} catch (IOException ex) {
								ex.printStackTrace();
							}
					}
				});
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
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());

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
		private BufferedImage background;
		private String text;
		private int top;

		public ImageButton(BufferedImage background, String text) {
			this.background = background;
			setPreferredSize(new Dimension(background.getWidth(this),
					background.getHeight(this)));
			this.text = text;
			top = 0;
		}

		public ImageButton(BufferedImage background, String text, int top) {
			this(background, text);
			this.top = top;
		}

		@Override
		public void paint(Graphics g) {

			if (background != null) {
				int bx = getWidth() / 2 - background.getWidth() / 2;
				int by = getHeight() / 2 - background.getHeight() / 2;

				g.drawImage(background, bx, by, this);
			}

			if (text != null) {

				Font font = GameData.font.deriveFont(50f);
				g.setFont(font);
				((Graphics2D) g).setRenderingHints(new RenderingHints(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
				FontMetrics fm = getFontMetrics(font);
				int tx = getWidth() / 2 - fm.stringWidth(text) / 2;
				int ty = top + getHeight() / 2 + fm.getAscent() / 2;
				g.setColor(new Color(0, 0, 0, 0.7f));
				g.drawString(text, tx + 2, ty + 2);
				g.setColor(new Color(1, 1, 1, 0.8f));
				g.drawString(text, tx, ty);
			}
		}
	}
}

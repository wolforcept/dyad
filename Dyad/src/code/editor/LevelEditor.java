package code.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import code.ui.MainMenu;
import code.ui.TaylorData;

public class LevelEditor {

	JFrame mainframe, objsframe, propsframe;
	EditorTaylor taylor;
	ObjectsTaylor objsTaylor;
	int width, height;
	final int cz = 40;
	final String[] objects = { "magus", "champion", "wood_wall", "scroll",
			"switch", "switch_door", "keydoor", "key" };
	Font font;
	int selected = 0;
	TaylorData data = new TaylorData();
	EditorObject[][] field;
	JTextField nameTextField;
	JFileChooser filechooser;

	public LevelEditor() {

		filechooser = new JFileChooser() {
			private static final long serialVersionUID = 1L;

			@Override
			public void approveSelection() {
				File f = getSelectedFile();
				if (f.exists() && getDialogType() == SAVE_DIALOG) {
					int result = JOptionPane.showConfirmDialog(this,
							"The file exists, overwrite?", "Existing file",
							JOptionPane.YES_NO_CANCEL_OPTION);
					switch (result) {
					case JOptionPane.YES_OPTION:
						super.approveSelection();
						return;
					case JOptionPane.NO_OPTION:
					case JOptionPane.CLOSED_OPTION:
						return;
					case JOptionPane.CANCEL_OPTION:
						cancelSelection();
						return;
					}
				}
				super.approveSelection();
			}
		};
		filechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		filechooser.setMultiSelectionEnabled(false);
		filechooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("txt",
				"txt");
		filechooser.setFileFilter(filter);

		try {
			font = Font.createFont(
					Font.TRUETYPE_FONT,//
					ClassLoader.getSystemClassLoader().getResourceAsStream(
							"resources/font.ttf"));
		} catch (FontFormatException | IOException e1) {
			System.err.println("Font error");
		}

		width = height = 6;

		field = new EditorObject[width][height];
		mainframe = new JFrame("Level Editor");
		objsframe = new JFrame("Objects");
		propsframe = new JFrame("Properties");

		taylor = new EditorTaylor();

		objsTaylor = new ObjectsTaylor();
		objsTaylor.setPreferredSize(new Dimension(200, objects.length * cz));

		taylor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int x = (int) ((float) e.getX() / cz);
				int y = (int) ((float) e.getY() / cz);
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (field[x][y] != null)
						field[x][y] = null;
					else
						field[x][y] = new EditorObject(objects[selected]);
				}

				if (e.getButton() == MouseEvent.BUTTON3) {
					if (field[x][y] != null)
						field[x][y].rightClick();
				}
				objsframe.toFront();
				propsframe.toFront();
				objsframe.setState(JFrame.NORMAL);
				propsframe.setState(JFrame.NORMAL);
				taylor.repaint();

			}
		});

		objsTaylor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				selected = (int) ((double) e.getY() / cz);
				objsTaylor.repaint();
			}
		});

		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new GridLayout(4, 1));
		nameTextField = new JTextField("my dyad map");
		propertiesPanel.add(nameTextField);
		propertiesPanel.add(new Property("width", width + ""));
		propertiesPanel.add(new Property("height", height + ""));

		JPanel propertiesButtonsPanel = new JPanel();
		propertiesButtonsPanel.setLayout(new GridLayout(1, 2));
		JButton saveButton = new JButton("save");
		propertiesButtonsPanel.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		JButton exitButton = new JButton("exit");
		propertiesButtonsPanel.add(exitButton);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
				new MainMenu(true);
			}
		});
		propertiesPanel.add(propertiesButtonsPanel);
		propsframe.add(propertiesPanel);

		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.getContentPane().add(taylor);
		mainframe.pack();
		mainframe.setResizable(false);
		mainframe.setVisible(true);

		objsframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		objsframe.setFocusableWindowState(false);
		objsframe.getContentPane().add(objsTaylor);
		objsframe.pack();
		objsframe.setResizable(false);
		objsframe.setVisible(true);

		propsframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		propsframe.setFocusableWindowState(false);
		propsframe.pack();
		propsframe.setResizable(false);
		propsframe.setVisible(true);

		reEverything();
	}

	private void exit() {
		mainframe.setVisible(false);
		mainframe.dispose();
		objsframe.setVisible(false);
		objsframe.dispose();
		propsframe.setVisible(false);
		propsframe.dispose();
	}

	private void reEverything() {
		EditorObject[][] field2 = new EditorObject[width][height];
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				if (x < field2.length && y < field2[x].length)
					field2[x][y] = field[x][y];
			}
		}
		field = field2;

		taylor.setPreferredSize(new Dimension(width * cz, height * cz));
		taylor.setSize(new Dimension(width * cz, height * cz));

		mainframe.setSize(new Dimension(10, 10));
		mainframe.pack();

		mainframe.setLocationRelativeTo(null);

		Point objsLoc = mainframe.getLocation();
		objsLoc.translate(cz + cz * width, 0);
		objsframe.setLocation(objsLoc);

		Point propsLoc = mainframe.getLocation();
		propsLoc.translate(-250, 0);
		propsframe.setLocation(propsLoc);

		taylor.repaint();
	}

	private void save() {
		try {

			int opt = filechooser.showSaveDialog(mainframe);
			if (opt != JFileChooser.APPROVE_OPTION)
				return;

			String filepath = filechooser.getSelectedFile().getAbsolutePath();

			if (!filepath.endsWith(".txt")) {
				filepath += ".txt";
			}

			BufferedWriter out = new BufferedWriter(new FileWriter(filepath));

			createSave(out);

			System.out.println("File Saved to " + filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createSave(BufferedWriter out) throws IOException {

		out.write(nameTextField.getText() + " {");
		out.newLine();

		out.write(width + "," + height);
		out.newLine();

		out.write("energy = 0, lethargy = 0, mind = 0, matter = 0");
		out.newLine();

		out.write("retrieve_scrolls:2");
		out.newLine();

		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				if (field[x][y] != null) {
					out.write("    " + field[x][y].toSave(x, y));
					out.newLine();
				}
			}
		}

		out.write("}");

		out.close();
	}

	private void setProperty(String prop, String val) {
		if (prop.equals("width")) {
			width = Math.min(10, Integer.parseInt(val));
		}
		if (prop.equals("height")) {
			height = Math.min(10, Integer.parseInt(val));
		}
		reEverything();
	}

	class EditorObject {

		String name;
		HashMap<String, String> properties;

		public EditorObject(String name) {
			this.name = name;
			properties = new HashMap<>();
			switch (name) {
			case "keydoor":
				properties.put("color", "red");
			case "key":
				properties.put("color", "red");
			}

		}

		void addProperty(String key, String value) {
			properties.put(key, value);
		}

		String toSave(int x, int y) {
			switch (name) {
			case "keydoor":
				return "keydoor" + ":" + x + "," + y + "/ color="
						+ properties.get("color");
			case "key":
				return "key" + ":" + x + "," + y + "/ color="
						+ properties.get("color");

			default:
				return name + ":" + x + "," + y;
			}
		}

		String getImageName() {
			switch (name) {
			case "keydoor":
				return properties.get("color") + "_door";
			case "key":
				return properties.get("color") + "_key";
			default:
				return name;
			}

		}

		void rightClick() {
			switch (name) {
			case "keydoor":
			case "key":
				String color = properties.get("color");
				if (color.equals("red")) {
					properties.remove("color");
					properties.put("color", "blue");
				} else {
					properties.remove("color");
					properties.put("color", "red");
				}
				break;

			default:
			}
		}
	}

	class EditorTaylor extends JPanel {

		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g) {
			g.clearRect(0, 0, getWidth(), getHeight());
			// IMAGES
			{
				for (int x = 0; x < field.length; x++) {
					for (int y = 0; y < field[x].length; y++) {

						if (field[x][y] != null) {
							BufferedImage img = data.getImage(field[x][y]
									.getImageName());
							g.drawImage(img, x * cz, y * cz, this);
						}

					}
				}
			}
			// LINES
			{
				for (int x = 0; x <= width; x++) {
					g.drawLine(x * cz, 0, x * cz, height * cz);
				}

				for (int y = 0; y <= height; y++) {
					g.drawLine(0, y * cz, width * cz, y * cz);
				}
			}
		}
	}

	class ObjectsTaylor extends JPanel {

		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g) {

			((Graphics2D) g).setRenderingHints(new RenderingHints(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON));

			g.setFont(font.deriveFont(20f));

			g.clearRect(0, 0, getWidth(), getHeight());

			for (int y = 0; y < objects.length; y++) {
				switch (objects[y]) {
				case "keydoor":
					g.drawImage(data.getImage("red_door"), 0, cz * y, this);
					break;

				case "key":
					g.drawImage(data.getImage("red_key"), 0, cz * y, this);
					break;

				default:
					g.drawImage(data.getImage(objects[y]), 0, cz * y, this);
				}
				g.drawString(objects[y], cz + 20, cz * y + cz - 15);
			}

			g.setColor(new Color(1, 0, 0, 0.6f));
			g.drawRect(0, cz * selected, cz, cz);
			g.setColor(new Color(1, 1, 0, 0.6f));
			g.drawRect(1, cz * selected + 1, cz - 2, cz - 2);
		}
	}

	class Property extends JPanel {

		private static final long serialVersionUID = 1L;

		JTextField tfield;
		String text;
		JButton butt;

		public Property(String textArg, String pre) {
			text = textArg;
			setLayout(new FlowLayout());
			add(new JLabel(text));
			tfield = new JTextField(10);
			tfield.setText(pre);
			add(tfield);
			butt = new JButton(">");
			add(butt);
			butt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setProperty(text, tfield.getText());
				}
			});
		}
	}

}

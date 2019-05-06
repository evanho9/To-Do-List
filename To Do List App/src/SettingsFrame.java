import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class SettingsFrame extends JFrame {

	private final Dimension FRAME_DIMENSION;

	private ArrayList<JPanel> backgrounds;
	private ArrayList<JButton> buttons;

	private CalendarFrame cFrame;
	private ToDoListFrame tdFrame;

	public SettingsFrame(CalendarFrame cFrame, ToDoListFrame tdFrame, Dimension frameDimension) {
		FRAME_DIMENSION = frameDimension;

		this.cFrame = cFrame;
		this.tdFrame = tdFrame;

		backgrounds = new ArrayList<>();
		buttons = new ArrayList<>();

		initFrame();
		initMainPanel();
		endOperations();
	}

	private void initFrame() {
		setPreferredSize(new Dimension(FRAME_DIMENSION.width / 3, FRAME_DIMENSION.height / 8));
		setLayout(new BorderLayout());
		setTitle("Settings");
	}

	private void initMainPanel() {
		JPanel mainPanel = new JPanel();
		backgrounds.add(mainPanel);
		
		JButton lightTheme = new JButton("Light Theme");
		buttons.add(lightTheme);
		lightTheme.addActionListener(event -> {
			Color c = Color.WHITE;
			tdFrame.setFrameBackgroundColor(c);
			System.out.println("light theme pressed");
			cFrame.setFrameBackgroundColor(c);
			changeColor(c);
		});
		mainPanel.add(lightTheme);

		JButton darkTheme = new JButton("Dark Theme");
		buttons.add(darkTheme);
		darkTheme.addActionListener(event -> {
			Color c = Color.BLACK;
			tdFrame.setFrameBackgroundColor(c);
			System.out.println(Arrays.toString(cFrame.getComponents()));
			System.out.println("dark theme pressed");
			cFrame.setFrameBackgroundColor(c);
			changeColor(c);
		});
		mainPanel.add(darkTheme);

		add(mainPanel);
	}

	private void endOperations() {
		try {
			ImageIcon imageIcon = new ImageIcon("icon.png");
			setIconImage(imageIcon.getImage());
		} catch (Exception e) {
			System.out.println("Icon image not found.");
		}
		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void changeColor(Color c) {
		for (JPanel p : backgrounds) {
			p.setBackground(c);
		}
		
		if (c.equals(Color.BLACK)) {
			for (JButton b : buttons) {
				b.setBorderPainted(false);
				b.setOpaque(true);
				b.setBackground(c);
				b.setForeground(Color.WHITE);
			}
		} else {
			for (JButton b : buttons) {
				b.setBorderPainted(false);
				b.setOpaque(true);
				b.setBackground(c);
				b.setForeground(Color.BLACK);
			}
		}
	}
}

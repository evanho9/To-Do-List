import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class SettingsFrame extends JFrame {
	
	private final Dimension FRAME_DIMENSION;
	
	private CalendarFrame cFrame;
	private ToDoListFrame tdFrame;
	
	public SettingsFrame(CalendarFrame cFrame, ToDoListFrame tdFrame, Dimension frameDimension) {
		FRAME_DIMENSION = frameDimension;
		
		this.cFrame = cFrame;
		this.tdFrame = tdFrame;
		
		initFrame();
		initMainPanel();
		endOperations();
	}
	
	private void initFrame() {	
		setPreferredSize(new Dimension(FRAME_DIMENSION.width/3, FRAME_DIMENSION.height/8));
		setLayout(new BorderLayout());
		setTitle("Settings");
	}
	
	private void initMainPanel() {
		JPanel mainPanel = new JPanel();
		
		JButton lightTheme = new JButton("Light Theme");
		lightTheme.addActionListener(event -> {
			Color c = Color.WHITE;
			tdFrame.setFrameBackgroundColor(c);
			System.out.print("light theme pressed");
			cFrame.setFrameBackgroundColor(c);
		});
		mainPanel.add(lightTheme);
		
		JButton darkTheme = new JButton("Dark Theme");
		darkTheme.addActionListener(event -> {
			Color c = Color.GRAY;
			tdFrame.setFrameBackgroundColor(c);
			System.out.print("dark theme pressed");
			cFrame.setFrameBackgroundColor(c);
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
}

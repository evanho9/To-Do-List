import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class CalendarFrame extends JFrame {
	//testing if im connected
	private JPanel headerPanel;
	private JPanel mainPanel;
	private JPanel footerPanel;
	
	private final static Dimension FRAME_DIMENSION = new Dimension(500,500);
	
	public CalendarFrame() {
		setPreferredSize(new Dimension(FRAME_DIMENSION));
		setLayout(new BorderLayout());
		
		initHeader();
		initTaskList();
		initFooter();
		endOperations();
	}

	private void initHeader() {
		headerPanel = new JPanel();
		headerPanel.setBackground(Color.GRAY);
		headerPanel.setLayout(new FlowLayout());
		
		
		headerPanel.setPreferredSize(new Dimension(FRAME_DIMENSION.width-50, FRAME_DIMENSION.height/15));
		add(headerPanel, BorderLayout.NORTH);
	}

	private void initTaskList() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.GRAY);
		mainPanel.setLayout(new FlowLayout());
		
		
		
		mainPanel.setPreferredSize(new Dimension(FRAME_DIMENSION.width-50, 7*FRAME_DIMENSION.height/10));
		add(mainPanel, BorderLayout.CENTER);
	}
	
	private void initFooter() {
		footerPanel = new JPanel();
		footerPanel.setBackground(Color.GRAY);
		footerPanel.setLayout(new FlowLayout());
		
		
		add(footerPanel, BorderLayout.SOUTH);
	}
	
	private void endOperations() {
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		CalendarFrame f = new CalendarFrame();
		
	}
}

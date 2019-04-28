import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class ToDoListFrame extends JFrame {
	
	private JPanel headerPanel;
	private JPanel mainPanel;
	private JPanel footerPanel;
	
	private final static Dimension FRAME_DIMENSION = new Dimension(500,500);
	
	public ToDoListFrame() {
		setPreferredSize(new Dimension(FRAME_DIMENSION));
		setLayout(new BorderLayout());
		setTitle("To-Do List");
		
		initHeader();
		initTaskList();
		initFooter();
		endOperations();
	}

	private void initHeader() {
		headerPanel = new JPanel();
		headerPanel.setBackground(Color.GRAY);
		headerPanel.setLayout(new FlowLayout());
		
		JLabel dateSelectedLabel = new JLabel("Date: ");
		dateSelectedLabel.setForeground(Color.WHITE);
		
		headerPanel.add(dateSelectedLabel);
		headerPanel.setPreferredSize(new Dimension(FRAME_DIMENSION.width-50, FRAME_DIMENSION.height/15));
		add(headerPanel, BorderLayout.NORTH);
	}

	private void initTaskList() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.GRAY);
		mainPanel.setLayout(new FlowLayout());
		
		final DefaultListModel taskNames = new DefaultListModel();
		
		/*
		taskNames.addElement("task1name");
		taskNames.addElement("task2name");
		taskNames.addElement("task3name");
		taskNames.addElement("task4name");
		taskNames.addElement("task5name");
		taskNames.addElement("task6name");
		taskNames.addElement("task7name");
		taskNames.addElement("task8name");
		*/
		
		JList taskList = new JList(taskNames);
		taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		taskList.setSelectedIndex(0);
		taskList.setVisibleRowCount(10);  
		
		JScrollPane taskScrollPane = new JScrollPane(taskList);
		taskScrollPane.setPreferredSize(new Dimension(FRAME_DIMENSION.width-50, 9*FRAME_DIMENSION.height/10));
		
		
		mainPanel.add(taskScrollPane);
		add(mainPanel, BorderLayout.CENTER);
	}
	
	private void initFooter() {
		footerPanel = new JPanel();
		footerPanel.setBackground(Color.GRAY);
		footerPanel.setLayout(new FlowLayout());
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBackground(Color.WHITE);
		deleteButton.setPreferredSize(new Dimension(FRAME_DIMENSION.width/5, FRAME_DIMENSION.height/10));
		deleteButton.addActionListener(event -> {
			
		});
		
		JButton editButton = new JButton("Edit");
		editButton.setBackground(Color.WHITE);
		editButton.setPreferredSize(new Dimension(FRAME_DIMENSION.width/5, FRAME_DIMENSION.height/10));
		editButton.addActionListener(event -> {
			
		});
		
		JButton addButton = new JButton("Add");
		addButton.setBackground(Color.WHITE);
		addButton.setPreferredSize(new Dimension(FRAME_DIMENSION.width/5, FRAME_DIMENSION.height/10));
		addButton.addActionListener(event -> {
			
		});
		
		JButton exportButton = new JButton("Export");
		exportButton.setBackground(Color.WHITE);
		exportButton.setPreferredSize(new Dimension(FRAME_DIMENSION.width/5, FRAME_DIMENSION.height/10));
		exportButton.addActionListener(event -> {
			
		});
		
		footerPanel.add(deleteButton);
		footerPanel.add(editButton);
		footerPanel.add(addButton);
		footerPanel.add(exportButton);
		add(footerPanel, BorderLayout.SOUTH);
	}
	
	private void endOperations() {
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}

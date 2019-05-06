import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class ToDoListFrame extends JFrame {
	
	private JPanel headerPanel;
	private JLabel dateLabel;
	private JPanel mainPanel;
	private JList taskList;
	private JScrollPane taskScrollPane;
	private JPanel footerPanel;
	private JTextField textField;
	private JButton addButton, deleteButton, editButton, exportButton;
	
	private final Dimension FRAME_DIMENSION;
	
	private ToDoList tdList;
	
	private int currentYear, currentMonth, currentDay;
	
	private String[] months = {"January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December"};
	
	
	private ArrayList<JPanel> backgrounds;
	private ArrayList<JButton> buttons;
	private Color textColor;
	private Color frameBackgroundColor;
	private Color buttonColor;
	
	public ToDoListFrame(int realDay, int realMonth, int realYear, Dimension frameDimension) {
		FRAME_DIMENSION = frameDimension;
		initFrame();
		tdList = new ToDoList();
		
		backgrounds = new ArrayList<>();
		buttons = new ArrayList<>();
		initColors();
		initHeader();
		initTaskList();
		initFooter();
		endOperations();
	}
	
	private void initFrame() {	
		setPreferredSize(new Dimension(FRAME_DIMENSION));
		setLayout(new BorderLayout());
		setTitle("To-Do List");
	}
	
	private void initColors() {
		textColor = Color.BLACK;
		frameBackgroundColor = Color.LIGHT_GRAY;
		buttonColor = Color.LIGHT_GRAY;
	}

	private void initHeader() {
		headerPanel = new JPanel();
		backgrounds.add(headerPanel);
		headerPanel.setBackground(frameBackgroundColor);
		headerPanel.setLayout(new FlowLayout());
		
		dateLabel = new JLabel("Date: ");
		dateLabel.setText("Date: " + (currentMonth) + "/" 
				+ (currentDay) + "/" + (currentYear));
		dateLabel.setForeground(textColor);
		
		headerPanel.add(dateLabel);
		headerPanel.setPreferredSize(new Dimension(FRAME_DIMENSION.width-50, FRAME_DIMENSION.height/15));
		add(headerPanel, BorderLayout.NORTH);
	}

	private void initTaskList() {
		mainPanel = new JPanel();
		backgrounds.add(mainPanel);		
		mainPanel.setBackground(frameBackgroundColor);
		mainPanel.setLayout(new FlowLayout());
		
		taskList = new JList();
		taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		taskList.setSelectedIndex(0);
		taskList.setVisibleRowCount(10);  
		
		taskScrollPane = new JScrollPane(taskList);
		taskScrollPane.setPreferredSize(new Dimension(FRAME_DIMENSION.width-50, 8*FRAME_DIMENSION.height/10));
		
		mainPanel.add(taskScrollPane);
		add(mainPanel, BorderLayout.CENTER);
	}
	
	private void initFooter() {
		footerPanel = new JPanel();
		backgrounds.add(footerPanel);
		footerPanel.setBackground(frameBackgroundColor);
		footerPanel.setLayout(new BorderLayout());
		
		textField = new JTextField("");

		textField.setPreferredSize(new Dimension(FRAME_DIMENSION.width -50, FRAME_DIMENSION.height/20));
		
		deleteButton = new JButton("Delete");
		buttons.add(deleteButton);
		deleteButton.setBackground(buttonColor);
		deleteButton.setBorderPainted(false);
		deleteButton.setOpaque(true);
		deleteButton.setPreferredSize(new Dimension(FRAME_DIMENSION.width/5, FRAME_DIMENSION.height/10));
		deleteButton.addActionListener(event -> {
			try {
				int currentSelected = taskList.getSelectedIndex();
				tdList.removeTask(currentSelected);
			}
			catch (Exception e) {
				//If nothing selected don't throw any errors.
			}
			
			update(currentDay, currentMonth,currentYear);
		});
		
		editButton = new JButton("Edit");
		buttons.add(editButton);
		editButton.setBackground(buttonColor);
		editButton.setBorderPainted(false);
		editButton.setOpaque(true);
		editButton.setPreferredSize(new Dimension(FRAME_DIMENSION.width/5, FRAME_DIMENSION.height/10));
		editButton.addActionListener(event -> {
			try {
				int currentSelected = taskList.getSelectedIndex();
				Task newTask = new Task(currentDay, currentMonth, currentYear, textField.getText());
				tdList.editTask(currentSelected, newTask);
				textField.setText("");
			}
			catch (Exception e) {
				//If nothing is selected don't throw any errors.
			}
			
			update(currentDay, currentMonth,currentYear);
		});
		
		addButton = new JButton("Add");
		buttons.add(addButton);
		addButton.setBackground(buttonColor);
		addButton.setBorderPainted(false);
		addButton.setOpaque(true);
		addButton.setPreferredSize(new Dimension(FRAME_DIMENSION.width/5, FRAME_DIMENSION.height/10));
		addButton.addActionListener(event -> {
			if(textField.getText().equals("")) {
				return;
			}
			Task task = new Task(currentDay, currentMonth, currentYear, textField.getText());
			textField.setText("");
			tdList.addTask(task);

			update(currentDay, currentMonth, currentYear);
		});
		
		exportButton = new JButton("Export");
		buttons.add(exportButton);
		exportButton.setBackground(buttonColor);
		exportButton.setBorderPainted(false);
		exportButton.setOpaque(true);
		exportButton.setPreferredSize(new Dimension(FRAME_DIMENSION.width/5, FRAME_DIMENSION.height/10));
		exportButton.addActionListener(event -> {
			exportThisMonth();
		});
		
		
		JPanel textFieldPanel = new JPanel();
		backgrounds.add(textFieldPanel);
		textFieldPanel.setBackground(frameBackgroundColor);
		textFieldPanel.setLayout(new FlowLayout());
		textFieldPanel.add(textField);
		footerPanel.add(textFieldPanel, BorderLayout.NORTH);
		JPanel buttonPanel = new JPanel();
		backgrounds.add(buttonPanel);
		buttonPanel.setBackground(frameBackgroundColor);
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(editButton);	
		buttonPanel.add(exportButton);
		footerPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		add(footerPanel, BorderLayout.SOUTH);
	}
	
	private void endOperations() {
		try {
			ImageIcon imageIcon = new ImageIcon("icon.png");
			setIconImage(imageIcon.getImage());
		} catch (Exception e) {
			System.out.println("Icon image not found.");
		}
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void update(int currentDay, int currentMonth, int currentYear) {
		this.currentDay = currentDay;
		this.currentMonth = currentMonth;
		this.currentYear = currentYear;
		
		dateLabel.setText("Date: " + (currentMonth) + "/" 
				+ (currentDay) + "/" + (currentYear));
		ArrayList<Task> tasksOnCurrentDay = tdList.getTasksOnDay(currentDay, currentMonth, currentYear);
		
		final DefaultListModel taskNames = new DefaultListModel();
		
		for (Task t : tasksOnCurrentDay) {
			taskNames.addElement(t.getTask());
		} 
		
		taskList.setModel(taskNames);
		taskScrollPane.repaint();
	}
	
	public void selectedTask() {
		boolean running = true;
		while (running) {
			try {
				int currentSelected = taskList.getSelectedIndex();
				String currentTask = tdList.getTaskAtIndex(currentSelected).getTask();
				textField.setText(currentTask);
				update(currentDay, currentMonth,currentYear);
				}
			catch (Exception e) {
				//If nothing is selected don't throw an error.
			}
		}
	}
	
	private String getMonth(int index) {
		return months[index-1];
	}
	
	private void exportThisMonth() { 
		PrintWriter writer;
		try {
			writer = new PrintWriter(getMonth(currentMonth) + "_" + currentYear + ".txt", "UTF-8");
			writer.println("To-Do List " + "(" + getMonth(currentMonth) + "," + currentYear + "): ");
			writer.println(tdList.getAllTasksInCurrentMonth(currentMonth, currentYear));
			writer.close();
		}
		catch (Exception e) {
			
		}
	     
	}
	
	public void setFrameBackgroundColor(Color color) {	
		if(color.equals(Color.BLACK)) {
			for (JPanel p : backgrounds) {
				p.setBackground(color);
			}
			dateLabel.setForeground(Color.WHITE);
			for(JButton b : buttons) {
				b.setBorderPainted(false);
				b.setOpaque(true);
				b.setBackground(color);
				b.setForeground(Color.WHITE);
			}
			taskScrollPane.getViewport().getView().setBackground(Color.BLACK);
			taskScrollPane.getViewport().getView().setForeground(Color.WHITE);
			
			textField.setBackground(Color.DARK_GRAY);
			textField.setForeground(Color.WHITE);
		}
		else {
			for (JPanel p : backgrounds) {
				p.setBackground(Color.LIGHT_GRAY);
			}
			dateLabel.setForeground(Color.BLACK);
			for(JButton b : buttons) {
				b.setBorderPainted(false);
				b.setOpaque(true);
				b.setBackground(Color.LIGHT_GRAY);
				b.setForeground(Color.BLACK);
			}
			taskScrollPane.getViewport().getView().setBackground(color);
			taskScrollPane.getViewport().getView().setForeground(Color.BLACK);
			
			textField.setBackground(color);
			textField.setForeground(Color.BLACK);
		}
			
		setBackground(color);

	}
	
}

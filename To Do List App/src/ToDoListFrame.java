import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
	
	
	private final Dimension FRAME_DIMENSION;
	
	private ToDoList tdList;
	
	private int currentYear, currentMonth, currentDay;
	
	private String[] months = {"January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December"};
	
	public ToDoListFrame(int realDay, int realMonth, int realYear, Dimension frameDimension) {
		FRAME_DIMENSION = frameDimension;
		setPreferredSize(new Dimension(FRAME_DIMENSION));
		setLayout(new BorderLayout());
		setTitle("To-Do List");
		tdList = new ToDoList();
		
		initHeader();
		initTaskList();
		initFooter();
		endOperations();
		//selectedTask();
	}

	private void initHeader() {
		headerPanel = new JPanel();
		headerPanel.setBackground(Color.GRAY);
		headerPanel.setLayout(new FlowLayout());
		
		dateLabel = new JLabel("Date: ");
		dateLabel.setText("Date: " + (currentMonth) + "/" 
				+ (currentDay) + "/" + (currentYear));
		dateLabel.setForeground(Color.WHITE);
		
		headerPanel.add(dateLabel);
		headerPanel.setPreferredSize(new Dimension(FRAME_DIMENSION.width-50, FRAME_DIMENSION.height/15));
		add(headerPanel, BorderLayout.NORTH);
	}

	private void initTaskList() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.GRAY);
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
		footerPanel.setBackground(Color.GRAY);
		footerPanel.setLayout(new BorderLayout());
		
		textField = new JTextField("");
		//textField.setBackground(Color.WHITE);
		textField.setPreferredSize(new Dimension(FRAME_DIMENSION.width -50, FRAME_DIMENSION.height/20));
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBackground(Color.WHITE);
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
		
		JButton editButton = new JButton("Edit");
		editButton.setBackground(Color.WHITE);
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
		
		JButton addButton = new JButton("Add");
		addButton.setBackground(Color.WHITE);
		addButton.setPreferredSize(new Dimension(FRAME_DIMENSION.width/5, FRAME_DIMENSION.height/10));
		addButton.addActionListener(event -> {
			
			Task task = new Task(currentDay, currentMonth, currentYear, textField.getText());
			textField.setText("");
			tdList.addTask(task);

			update(currentDay, currentMonth, currentYear);
		});
		
		JButton exportButton = new JButton("Export");
		exportButton.setBackground(Color.WHITE);
		exportButton.setPreferredSize(new Dimension(FRAME_DIMENSION.width/5, FRAME_DIMENSION.height/10));
		exportButton.addActionListener(event -> {
			 BufferedWriter output = null;
		        try {
		            File file = new File(getMonth(currentMonth) + "_" + currentYear + ".txt");
		            output = new BufferedWriter(new FileWriter(file));
		            for(int i = 0; i<tdList.getTasks().size(); i++) {
		            	output.write(tdList.getTaskAtIndex(i).toString());
		            }
		            output.close();
		        } catch (Exception e ) {
		      
		          }
		});
		
		
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setBackground(Color.GRAY);
		textFieldPanel.setLayout(new FlowLayout());
		textFieldPanel.add(textField);
		footerPanel.add(textFieldPanel, BorderLayout.NORTH);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.GRAY);
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(editButton);	
		buttonPanel.add(exportButton);
		footerPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		add(footerPanel, BorderLayout.SOUTH);
	}
	
	private void endOperations() {
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void update(int currentDay, int currentMonth, int currentYear) {
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
	
	public void goToDate(int day, int month, int year) {
		currentDay = day;
		currentMonth = month;
		currentYear = year;
		update(currentDay, currentMonth, currentYear);
		System.out.println("Reached day: " + day + ", month: " + month + ", year: "+ year);
	}
	
	public void selectedTask() {
		boolean running = true;
		while (running) {
			try {
				int currentSelected = taskList.getSelectedIndex();
				String currentTask = tdList.getTaskAtIndex(currentSelected).getTask();
				System.out.println("Currently selected : " + currentTask);
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
	
	
}

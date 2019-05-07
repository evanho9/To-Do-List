import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class CurrentMonthToDo extends JFrame {
	private final Dimension FRAME_DIMENSION = new Dimension(300, 300);
	
	private ToDoList td;
	private JList taskList;
	private JScrollPane taskScrollPane;
	private JLabel dateLabel;
	
	private int currentMonth, currentYear;
	private String[] months = {"January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December"};
	
	
	public CurrentMonthToDo(ToDoList td, int currentMonth, int currentYear) {
		this.td = td;
		this.currentMonth = currentMonth;
		this.currentYear = currentYear;
		
		initFrame();
		initMainFrame();
		addTasksToMainFrame();
		endOperations();
	}
	
	private void initFrame() {
		setPreferredSize(new Dimension(FRAME_DIMENSION.width, FRAME_DIMENSION.height));
		setLayout(new BorderLayout());
		setTitle("To-Do List: " +getMonth(currentMonth) + "," + currentYear);
	}
	
	private void initMainFrame() {
		JPanel mainPanel = new JPanel();
		taskList = new JList();
		taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		taskList.setSelectedIndex(0);
		taskList.setVisibleRowCount(10);  
		
		taskScrollPane = new JScrollPane(taskList);
		taskScrollPane.setPreferredSize(new Dimension(FRAME_DIMENSION.width-50, 8*FRAME_DIMENSION.height/10));
		
		mainPanel.add(taskScrollPane);
		add(mainPanel, BorderLayout.CENTER);
	}
	
	private void addTasksToMainFrame() {
		final DefaultListModel taskNames = new DefaultListModel();
		ArrayList<Task> currentTaskList = td.getAllTasksInCurrentMonthInArrayList(currentMonth, currentYear);
		for(Task t : currentTaskList) {
			taskNames.addElement(t.toString() + "\n");
		}
		//taskNames.addElement(td.getAllTasksInCurrentMonth(currentMonth, currentYear));
		taskList.setModel(taskNames);
		taskScrollPane.repaint();
	}
	
	private void endOperations() {
		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	
	private String getMonth(int index) {
		return months[index-1];
	}
}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.GregorianCalendar;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class CalendarFrame extends JFrame {
	
	private JPanel headerPanel, mainPanel, footerPanel;
	private JLabel dateLabel;
	private JButton selected;
	
	private ToDoListFrame t;
	
	private int currentYear, currentMonth, currentDay;
	
	private final static Dimension FRAME_DIMENSION = new Dimension(500,500);
	
	public CalendarFrame() {
		setPreferredSize(new Dimension(FRAME_DIMENSION));
		setLayout(new BorderLayout());
		setTitle("Calendar Frame");
		
		initHeader();
		initCalendarView();
		initFooter();
		endOperations();
	}

	private void initHeader() {
		headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout());
		headerPanel.setBackground(Color.GRAY);
		
		dateLabel = new JLabel("Date: ");
		dateLabel.setHorizontalAlignment(JLabel.CENTER);
		dateLabel.setForeground(Color.WHITE);
		
		headerPanel.add(dateLabel, BorderLayout.CENTER);
		
		JButton forwardMonth = new JButton("-->");
		forwardMonth.addActionListener(event -> {
			if (currentMonth == 11) {
				currentMonth = 0;
				currentYear++;
				currentDay = 0;
			} else {
				currentMonth++;
				currentDay = 0;
			}
			refreshCalendar(currentDay, currentMonth, currentYear);
		});
		headerPanel.add(forwardMonth, BorderLayout.EAST);
		
		JButton previousMonth = new JButton("<--");
		previousMonth.addActionListener(event -> {
			if (currentMonth == 0) {
				currentMonth = 11;
				currentYear--;
				currentDay = 0;
			} else {
				currentMonth--;
				currentDay = 0;
			}
			refreshCalendar(currentDay, currentMonth, currentYear);
		});
		headerPanel.add(previousMonth, BorderLayout.WEST);
		
		add(headerPanel, BorderLayout.NORTH);
	}

	private void initCalendarView() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(6, 7));
		mainPanel.setBackground(Color.WHITE);

		String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		for (String day: days) {
			JLabel dayLabel = new JLabel(day);
			mainPanel.add(dayLabel);
		}
		
		GregorianCalendar cal = new GregorianCalendar();
		int realDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
		int realMonth = cal.get(GregorianCalendar.MONTH);
		int realYear = cal.get(GregorianCalendar.YEAR); 
		int daysInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		currentDay = realDay;
		currentMonth = realMonth;
		currentYear = realYear;

		int i = 0;
		while (i < 35) {
			if (i < daysInMonth) {
				JButton dayButton = new JButton(Integer.toString(i+1));
				int currentDayOfButton = Integer.parseInt(dayButton.getText());
				dayButton.setBackground(Color.WHITE);
				dayButton.addActionListener(event -> {
					t.goToDate(currentDayOfButton, currentMonth, currentYear);
					selected.setBackground(Color.WHITE);
					selected = dayButton;
					dayButton.setBackground(Color.RED);
					dateLabel.setText("Date: " + (currentMonth+1) + "/" 
					+ (currentDayOfButton) + "/" + (currentYear));
				});
				if (i == currentDay) {
					selected = dayButton;
					dayButton.setBackground(Color.RED);
				}
				mainPanel.add(dayButton);
			} else {
				JButton placeholderButton = new JButton("");
				placeholderButton.setBackground(Color.lightGray);
				placeholderButton.addActionListener(event -> {
					
				});
				mainPanel.add(placeholderButton);
			}
			i++;
		}
		add(mainPanel, BorderLayout.CENTER);
		dateLabel.setText("Date: " + (realMonth+1) + "/" + (realDay+1) + "/" + (realYear));
	}
	
	private void initFooter() {
		footerPanel = new JPanel();
		footerPanel.setLayout(new FlowLayout());
		footerPanel.setBackground(Color.GRAY);

		add(footerPanel, BorderLayout.SOUTH);
	}
	
	private void endOperations() {
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(t);
	}
	
	private void refreshCalendar(int currentDay, int month, int year) {
		mainPanel.removeAll();
		
		String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		for (String dayName: days) {
			JLabel dayLabel = new JLabel(dayName);
			mainPanel.add(dayLabel);
		}
		
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		int daysInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		int startingDayOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);

		int i = 0;
		while (i < 35) {
			if (i < daysInMonth) {
				JButton dayButton = new JButton(Integer.toString(i+1));
				int currentDayOfButton = Integer.parseInt(dayButton.getText());
				dayButton.setBackground(Color.WHITE);
				dayButton.addActionListener(event -> {
					t.goToDate(currentDayOfButton, currentMonth, currentYear);
					selected.setBackground(Color.WHITE);
					selected = dayButton;
					dayButton.setBackground(Color.RED);
					dateLabel.setText("Date: " + (currentMonth+1) + "/" 
					+ (currentDayOfButton) + "/" + (currentYear));
				});
				if (i == currentDay) {
					selected = dayButton;
					dayButton.setBackground(Color.RED);
				}
				mainPanel.add(dayButton);
			} else {
				JButton placeholderButton = new JButton();
				placeholderButton.setBackground(Color.lightGray);
				placeholderButton.addActionListener(event -> {
					
				});
				mainPanel.add(placeholderButton);
			}
			i++;
		}
		dateLabel.setText("Date: " + (currentMonth+1) + "/" + (currentDay+1) + "/" + (currentYear));
		mainPanel.repaint();
	}
	
	public void connectToDoListFrame(ToDoListFrame toDoListFrame) {
		t = toDoListFrame;
	}
}

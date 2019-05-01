import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class CalendarFrame extends JFrame {
	
	private JPanel headerPanel, mainPanel, footerPanel, centerPanel, buttonPanel;
	private JLabel dateLabel, monthLabel;
	private JButton selected;
	private JComboBox<Integer> yearSelector;
	
	private final Dimension FRAME_DIMENSION;
	
	private ToDoListFrame t;
	
	private int currentYear, currentMonth, currentDay;	
	private String[] months = {"January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December"};

	
	public CalendarFrame(Dimension frameDimension) {
		FRAME_DIMENSION = frameDimension;
		setPreferredSize(new Dimension(FRAME_DIMENSION));
		setLayout(new BorderLayout());
		setTitle("Calendar Frame");
		setBackground(Color.GRAY);
		
		GregorianCalendar cal = new GregorianCalendar();
		int realDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
		int realMonth = cal.get(GregorianCalendar.MONTH);
		int realYear = cal.get(GregorianCalendar.YEAR); 
		int daysInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		int startingDayOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);
		currentDay = realDay;
		currentMonth = realMonth+1;
		currentYear = realYear;
		
		t = new ToDoListFrame(realDay, realMonth, realYear, FRAME_DIMENSION);
		t.update(currentDay, currentMonth, currentYear);
		
		initHeader();
		initFooter();
		initCalendarView(daysInMonth, startingDayOfMonth);
		endOperations();
	}

	private void initHeader() {
		headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout());
		headerPanel.setBackground(Color.GRAY);
		
		initCenterPanel();
		initButtonPanel();
		centerPanel.add(buttonPanel);
		
		headerPanel.add(centerPanel, BorderLayout.CENTER);

		
		JButton forwardMonth = new JButton("Next Month -->");
		forwardMonth.setBackground(Color.WHITE);
		forwardMonth.setFocusPainted(false);
		forwardMonth.addActionListener(event -> {
			if (currentMonth == 12) {
				currentMonth = 1;
				currentYear++;
				currentDay = 1;
			} else {
				currentMonth++;
				currentDay = 1;
			}
			t.update(currentDay, currentMonth, currentYear);
			refreshCalendar(currentDay, currentMonth, currentYear);
		});
		headerPanel.add(forwardMonth, BorderLayout.EAST);
		
		JButton previousMonth = new JButton("<-- Prev. Month");
		previousMonth.setFocusPainted(false);
		previousMonth.setBackground(Color.WHITE);
		previousMonth.addActionListener(event -> {
			if (currentMonth == 1) {
				currentMonth = 12;
				currentYear--;
				currentDay = 1;
			} else {
				currentMonth--;
				currentDay = 1;
			}
			t.update(currentDay, currentMonth, currentYear);
			refreshCalendar(currentDay, currentMonth, currentYear);
		});
		headerPanel.add(previousMonth, BorderLayout.WEST);
		
		
		add(headerPanel, BorderLayout.NORTH);
	}
	
	
	private void initCenterPanel() {
		centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		centerPanel.setBackground(Color.GRAY);
		
		monthLabel = new JLabel(getMonth(currentMonth));
		centerPanel.add(monthLabel);
		
		yearSelector = new JComboBox<>();
		yearSelector.setBackground(Color.WHITE);
		for(int i = 1583; i <= 3000; i++) {
			yearSelector.addItem(i);
		}
		centerPanel.add(yearSelector);
		
		yearSelector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JComboBox<Integer> combo = (JComboBox<Integer>) event.getSource();
				if(currentYear != (int) combo.getSelectedItem()) {
					currentYear = (int) combo.getSelectedItem();
					t.update(currentDay, currentMonth, currentYear);
					refreshCalendar(currentDay, currentMonth, currentYear);
				}
			}
		}
		);
	}
	
	private void initButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBackground(Color.GRAY);
		
		JButton forwardYear = new JButton("Λ (+1 Year)");
		forwardYear.setBackground(Color.WHITE);
		forwardYear.setFocusPainted(false);
		forwardYear.addActionListener(event -> {
			currentYear++;
			t.update(currentDay, currentMonth, currentYear);
			refreshCalendar(currentDay, currentMonth, currentYear);
		});
		
		JButton previousYear = new JButton("V (-1 Year)");
		previousYear.setBackground(Color.WHITE);
		previousYear.setFocusPainted(false);
		previousYear.addActionListener(event -> {
			currentYear--;
			t.update(currentDay, currentMonth, currentYear);
			refreshCalendar(currentDay, currentMonth, currentYear);
		});
		
		buttonPanel.add(forwardYear);
		buttonPanel.add(previousYear);
	}

	private void initCalendarView(int daysInMonth, int startingDayOfMonth) {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(7, 7, 2, 2));
		mainPanel.setBackground(Color.LIGHT_GRAY);

		String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		for (String dayName: days) {
			JButton placeholderButton = new JButton(dayName);
			placeholderButton.setBackground(Color.LIGHT_GRAY);
			placeholderButton.setOpaque(true);
			placeholderButton.setContentAreaFilled(true);
			placeholderButton.setBorderPainted(false);
			placeholderButton.addActionListener(event -> {
				
			});
			mainPanel.add(placeholderButton);
		}
		
		Border thickBorder = new LineBorder(Color.MAGENTA, 1);
		
		int i = 1;
		int dayNumber = 1;
		while (i <= 42) {
			if (dayNumber < daysInMonth && i >= startingDayOfMonth) {
				JButton dayButton = new JButton(Integer.toString(dayNumber));
				int currentDayOfButton = Integer.parseInt(dayButton.getText());
				dayButton.setBackground(Color.WHITE);
				dayButton.setBorderPainted(false);
				dayButton.setFocusPainted(false);
				dayButton.setOpaque(true);
				dayButton.addActionListener(event -> {
					selected.setForeground(Color.BLACK);
					selected.setBorderPainted(false);
					selected.setFocusPainted(false);
					selected = dayButton;
					dayButton.setForeground(Color.MAGENTA);
					dayButton.setBorderPainted(true);
					dayButton.setBorder(thickBorder);
					dateLabel.setText("Date: " + (currentMonth) + "/" 
					+ (currentDayOfButton) + "/" + (currentYear));
					t.update(currentDayOfButton, currentMonth, currentYear);
				});
				if (dayNumber == currentDay) {
					selected = dayButton;
					dayButton.setForeground(Color.MAGENTA);
					dayButton.setBorderPainted(true);
					dayButton.setFocusPainted(false);
					dayButton.setBorder(thickBorder);
				}
				mainPanel.add(dayButton);
				dayNumber++;
			} else {
				JButton placeholderButton = new JButton("");
				placeholderButton.setBackground(Color.LIGHT_GRAY);
				placeholderButton.setOpaque(true);
				placeholderButton.setContentAreaFilled(true);
				placeholderButton.setBorderPainted(false);
				placeholderButton.addActionListener(event -> {
					
				});
				mainPanel.add(placeholderButton);
			}
			i++;
		}
		add(mainPanel, BorderLayout.CENTER);
		dateLabel.setText("Date: " + (currentMonth) + "/" + (currentDay) + "/" + (currentYear));
		monthLabel.setText(getMonth(currentMonth));
		yearSelector.setSelectedItem(currentYear);
	}
	
	private void initFooter() {
		footerPanel = new JPanel();
		footerPanel.setLayout(new FlowLayout());
		footerPanel.setBackground(Color.GRAY);
		
		dateLabel = new JLabel("Date: ");
		dateLabel.setForeground(Color.WHITE);
		
		footerPanel.add(dateLabel);
		
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
		setLocationRelativeTo(t);
	}
	
	private void refreshCalendar(int currentDay, int month, int year) {
		mainPanel.removeAll();
		
		String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		for (String dayName: days) {
			JButton placeholderButton = new JButton(dayName);
			placeholderButton.setBackground(Color.LIGHT_GRAY);
			placeholderButton.setOpaque(true);
			placeholderButton.setContentAreaFilled(true);
			placeholderButton.setBorderPainted(false);
			placeholderButton.addActionListener(event -> {
				
			});
			mainPanel.add(placeholderButton);
		}
		
		GregorianCalendar cal = new GregorianCalendar(year, month-1, 1);
		int daysInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)+1;
		int startingDayOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		Border thickBorder = new LineBorder(Color.MAGENTA, 1);
		
		int i = 1;
		int dayNumber = 1;
		while (i <= 42) {
			if (dayNumber < daysInMonth && i >= startingDayOfMonth) {
				JButton dayButton = new JButton(Integer.toString(dayNumber));
				int currentDayOfButton = Integer.parseInt(dayButton.getText());
				dayButton.setBackground(Color.WHITE);
				dayButton.setBorderPainted(false);
				dayButton.setFocusPainted(false);
				dayButton.setOpaque(true);
				dayButton.addActionListener(event -> {
					selected.setForeground(Color.BLACK);
					selected.setBorderPainted(false);
					selected.setFocusPainted(false);
					selected = dayButton;
					dayButton.setForeground(Color.MAGENTA);
					dayButton.setBorderPainted(true);
					dayButton.setBorder(thickBorder);
					dateLabel.setText("Date: " + (currentMonth) + "/" 
					+ (currentDayOfButton) + "/" + (currentYear));
					t.update(currentDayOfButton, currentMonth, currentYear);
				});
				if (dayNumber == currentDay) {
					selected = dayButton;
					dayButton.setForeground(Color.MAGENTA);
					dayButton.setBorderPainted(true);
					dayButton.setFocusPainted(false);
					dayButton.setBorder(thickBorder);
				}
				mainPanel.add(dayButton);
				dayNumber++;
			} else {
				JButton placeholderButton = new JButton("");
				placeholderButton.setBackground(Color.LIGHT_GRAY);
				placeholderButton.setOpaque(true);
				placeholderButton.setContentAreaFilled(true);
				placeholderButton.setBorderPainted(false);
				placeholderButton.addActionListener(event -> {
					
				});
				mainPanel.add(placeholderButton);
			}
			i++;
		}
		dateLabel.setText("Date: " + (currentMonth) + "/" + (currentDay) + "/" + (currentYear));
		monthLabel.setText(getMonth(currentMonth));
		yearSelector.setSelectedItem(currentYear);
		mainPanel.repaint();
	}
	
	private String getMonth(int index) {
		return months[index-1];
	}
}

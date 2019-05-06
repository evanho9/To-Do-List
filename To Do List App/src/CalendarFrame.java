import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class CalendarFrame extends JFrame {
	
	private JPanel headerPanel, mainPanel, footerPanel, centerPanel, buttonPanel;
	private JLabel dateLabel, monthLabel;
	private JButton selected, forwardMonth, previousMonth, forwardYear, previousYear;
	private JComboBox<Integer> yearSelector;
	
	private final Dimension FRAME_DIMENSION;
	
	private ToDoListFrame t;
	
	private int currentYear, currentMonth, currentDay;	
	private String[] months = {"January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December"};

	private ArrayList<JPanel> backgrounds;
	private ArrayList<JButton> buttons;
	private Color textColor;
	private Color frameBackgroundColor;
	private Color buttonColor;
	private Color calendarBackgroundColor;
	private Color selectedDayColor;
	
	public CalendarFrame(Dimension frameDimension) {
		FRAME_DIMENSION = frameDimension;
		initFrame();
		initColors();
		
		GregorianCalendar cal = new GregorianCalendar();
		int realDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
		int realMonth = cal.get(GregorianCalendar.MONTH);
		int realYear = cal.get(GregorianCalendar.YEAR); 
		int daysInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)+1;
		int startingDayOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);
		currentDay = realDay;
		currentMonth = realMonth+1;
		currentYear = realYear;
		
		t = new ToDoListFrame(realDay, realMonth, realYear, FRAME_DIMENSION);
		t.update(currentDay, currentMonth, currentYear);
		
		backgrounds = new ArrayList<>();
		buttons = new ArrayList<>();
		initHeader();
		initFooter();
		initCalendarView(daysInMonth, startingDayOfMonth, currentYear);
		endOperations();
	}
	
	private void initFrame() {
		setPreferredSize(new Dimension(FRAME_DIMENSION));
		setLayout(new BorderLayout());
		setTitle("Calendar Frame");
	}
	
	private void initColors() {
		textColor = Color.BLACK;
		frameBackgroundColor = Color.LIGHT_GRAY;
		buttonColor = Color.LIGHT_GRAY;
		calendarBackgroundColor = new Color(240, 240, 240);
		selectedDayColor = Color.MAGENTA;
	}

	private void initHeader() {
		headerPanel = new JPanel();
		backgrounds.add(headerPanel);
		headerPanel.setLayout(new BorderLayout());
		headerPanel.setBackground(frameBackgroundColor);
		
		initCenterPanel();
		initButtonPanel();
		centerPanel.add(buttonPanel);
		
		headerPanel.add(centerPanel, BorderLayout.CENTER);
		
		forwardMonth = new JButton("Next Month -->");
		buttons.add(forwardMonth);
		forwardMonth.setBackground(buttonColor);
		forwardMonth.setFocusPainted(false);
		forwardMonth.setBorderPainted(false);
		forwardMonth.setOpaque(true);
		forwardMonth.addActionListener(event -> {
			if (!(currentYear == 3000 && currentMonth == 12)) {
				if (currentMonth == 12) {
					currentMonth = 1;
					currentYear++;
					currentDay = 1;
				} else {
					currentMonth++;
					currentDay = 1;
				}
				refreshCalendar(currentDay, currentMonth, currentYear);
				t.update(currentDay, currentMonth, currentYear);
			}
		});
		headerPanel.add(forwardMonth, BorderLayout.EAST);
		
		previousMonth = new JButton("<-- Prev. Month");
		buttons.add(previousMonth);
		previousMonth.setFocusPainted(false);
		previousMonth.setBackground(buttonColor);
		previousMonth.setBorderPainted(false);
		previousMonth.setOpaque(true);
		previousMonth.addActionListener(event -> {
			if (!(currentYear == 1583 && currentMonth == 1)) {
				if (currentMonth == 1 && currentYear != 1583) {
					currentMonth = 12;
					currentYear--;
					currentDay = 1;
				} else {
					currentMonth--;
					currentDay = 1;
				}
				refreshCalendar(currentDay, currentMonth, currentYear);
				t.update(currentDay, currentMonth, currentYear);
			}
		});
		headerPanel.add(previousMonth, BorderLayout.WEST);
		
		add(headerPanel, BorderLayout.NORTH);
	}
	
	
	private void initCenterPanel() {
		centerPanel = new JPanel();
		backgrounds.add(centerPanel);
		centerPanel.setLayout(new FlowLayout());
		centerPanel.setBackground(frameBackgroundColor);
		
		monthLabel = new JLabel(getMonth(currentMonth));
		monthLabel.setForeground(textColor);
		centerPanel.add(monthLabel);
		
		yearSelector = new JComboBox<>();
		yearSelector.setBackground(calendarBackgroundColor);
		for(int i = 1583; i <= 3000; i++) {
			yearSelector.addItem(i);
		}
		centerPanel.add(yearSelector);
		
		yearSelector.addActionListener(event -> {
			JComboBox<Integer> combo = (JComboBox<Integer>) event.getSource();
			if(currentYear != (int) combo.getSelectedItem()) {
				currentYear = (int) combo.getSelectedItem();
				refreshCalendar(currentDay, currentMonth, currentYear);
				t.update(currentDay, currentMonth, currentYear);
			}
		});
	}
	
	private void initButtonPanel() {
		buttonPanel = new JPanel();
		backgrounds.add(buttonPanel);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBackground(frameBackgroundColor);
		
		forwardYear = new JButton("Λ (+1 Year)");
		buttons.add(forwardYear);
		forwardYear.setBackground(buttonColor);
		forwardYear.setFocusPainted(false);
		forwardYear.setBorderPainted(false);
		forwardYear.setOpaque(true);
		forwardYear.addActionListener(event -> {
			if(currentYear != 3000) {
				currentYear++;
				refreshCalendar(currentDay, currentMonth, currentYear);
				t.update(currentDay, currentMonth, currentYear);
			}
		});
		
		previousYear = new JButton("V (-1 Year)");
		buttons.add(previousYear);
		previousYear.setBackground(buttonColor);
		previousYear.setFocusPainted(false);
		previousYear.setBorderPainted(false);
		previousYear.setOpaque(true);
		previousYear.addActionListener(event -> {
			if(currentYear != 1583) {
				currentYear--;
				refreshCalendar(currentDay, currentMonth, currentYear);
				t.update(currentDay, currentMonth, currentYear);
			}
		});
		
		buttonPanel.add(forwardYear);
		buttonPanel.add(previousYear);
	}

	private void initCalendarView(int daysInMonth, int startingDayOfMonth, int year) {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(7, 7, 2, 2));
		mainPanel.setBackground(calendarBackgroundColor);

		String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		for (String dayName: days) {
			JButton placeholderButton = new JButton(dayName);
			placeholderButton.setBackground(calendarBackgroundColor);
			placeholderButton.setOpaque(true);
			placeholderButton.setContentAreaFilled(true);
			placeholderButton.setBorderPainted(false);
			placeholderButton.addActionListener(event -> {
				
			});
			mainPanel.add(placeholderButton);
		}
		
		Border thickBorder = new LineBorder(selectedDayColor, 1);
		
		int i = 1;
		
		GregorianCalendar cal;
		int daysBefore = startingDayOfMonth-1;
		if (currentMonth == 1)
			cal = new GregorianCalendar(year-1, 11, 1);
		else
			cal = new GregorianCalendar(year, currentMonth, 1);
		int daysInPrevMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)+1;
		int prevMonthDay = daysInPrevMonth - daysBefore;
		
		int afterMonthDay = 1;
		
		int dayNumber = 1;
		while (i <= 42) {
			if (i <= daysBefore) {
				JButton placeholderButton = new JButton(Integer.toString(prevMonthDay));
				placeholderButton.setBackground(calendarBackgroundColor);
				placeholderButton.setForeground(frameBackgroundColor);
				placeholderButton.setOpaque(true);
				placeholderButton.setContentAreaFilled(true);
				placeholderButton.setBorderPainted(false);
				placeholderButton.addActionListener(event -> {
					
				});
				mainPanel.add(placeholderButton);
				prevMonthDay++;
			} else if (dayNumber >= daysInMonth) {
				JButton placeholderButton = new JButton(Integer.toString(afterMonthDay));
				placeholderButton.setBackground(calendarBackgroundColor);
				placeholderButton.setForeground(frameBackgroundColor);
				placeholderButton.setOpaque(true);
				placeholderButton.setContentAreaFilled(true);
				placeholderButton.setBorderPainted(false);
				placeholderButton.addActionListener(event -> {
					
				});
				mainPanel.add(placeholderButton);
				afterMonthDay++;
			} else if (dayNumber < daysInMonth && i >= startingDayOfMonth) {
				JButton dayButton = new JButton(Integer.toString(dayNumber));
				int currentDayOfButton = Integer.parseInt(dayButton.getText());
				dayButton.setBackground(Color.WHITE);
				dayButton.setBorderPainted(false);
				dayButton.setFocusPainted(false);
				dayButton.setOpaque(true);
				dayButton.addActionListener(event -> {
					selected.setForeground(textColor);
					selected.setBorderPainted(false);
					selected.setFocusPainted(false);
					selected = dayButton;
					dayButton.setForeground(selectedDayColor);
					dayButton.setBorderPainted(true);
					dayButton.setBorder(thickBorder);
					dateLabel.setText("Date: " + (currentMonth) + "/" 
					+ (currentDayOfButton) + "/" + (currentYear));
					t.update(currentDayOfButton, currentMonth, currentYear);
				});
				if (dayNumber == currentDay) {
					selected = dayButton;
					dayButton.setForeground(selectedDayColor);
					dayButton.setBorderPainted(true);
					dayButton.setFocusPainted(false);
					dayButton.setBorder(thickBorder);
				}
				mainPanel.add(dayButton);
				dayNumber++;
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
		backgrounds.add(footerPanel);
		footerPanel.setLayout(new BorderLayout());
		footerPanel.setBackground(frameBackgroundColor);
		
		dateLabel = new JLabel("Date: ");
		dateLabel.setForeground(buttonColor);
		dateLabel.setHorizontalAlignment(JLabel.CENTER);
		dateLabel.setForeground(textColor);
		
		footerPanel.add(dateLabel, BorderLayout.CENTER);
		
		JButton settingsButton = new JButton("Settings");
		buttons.add(settingsButton);
		settingsButton.setBackground(buttonColor);
		settingsButton.setFocusPainted(false);
		settingsButton.setBorderPainted(false);
		settingsButton.addActionListener(event -> {
			SettingsFrame settingsFrame = new SettingsFrame(this, t, FRAME_DIMENSION);
		});
		footerPanel.add(settingsButton, BorderLayout.EAST);
		
		add(footerPanel, BorderLayout.SOUTH);
	}
	
	private void endOperations() {
		try {
			ImageIcon imageIcon = new ImageIcon("icon.png");
			setIconImage(imageIcon.getImage());
		} catch (Exception e) {
			System.out.println("Icon image not found.");
		}
		setBackground(frameBackgroundColor);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(t);
	}
	
	private void refreshCalendar(int day, int month, int year) {
		mainPanel.removeAll();
		
		String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		for (String dayName: days) {
			JButton placeholderButton = new JButton(dayName);
			placeholderButton.setBackground(calendarBackgroundColor);
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
		
		Border thickBorder = new LineBorder(selectedDayColor, 1);
		
		int i = 1;
		
		int daysBefore = startingDayOfMonth-1;
		if (currentMonth == 1)
			cal = new GregorianCalendar(year-1, 11, 1);
		else
			cal = new GregorianCalendar(year, currentMonth, 1);
		int daysInPrevMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)+1;
		int prevMonthDay = daysInPrevMonth - daysBefore;
		
		int afterMonthDay = 1;
		
		int dayNumber = 1;
		while (i <= 42) {
			if (i <= daysBefore) {
				JButton placeholderButton = new JButton(Integer.toString(prevMonthDay));
				placeholderButton.setBackground(calendarBackgroundColor);
				placeholderButton.setForeground(frameBackgroundColor);
				placeholderButton.setOpaque(true);
				placeholderButton.setContentAreaFilled(true);
				placeholderButton.setBorderPainted(false);
				placeholderButton.addActionListener(event -> {
					
				});
				mainPanel.add(placeholderButton);
				prevMonthDay++;
			} else if (dayNumber >= daysInMonth) {
				JButton placeholderButton = new JButton(Integer.toString(afterMonthDay));
				placeholderButton.setBackground(calendarBackgroundColor);
				placeholderButton.setForeground(frameBackgroundColor);
				placeholderButton.setOpaque(true);
				placeholderButton.setContentAreaFilled(true);
				placeholderButton.setBorderPainted(false);
				placeholderButton.addActionListener(event -> {
					
				});
				mainPanel.add(placeholderButton);
				afterMonthDay++;
			} else if (dayNumber < daysInMonth && i >= startingDayOfMonth) {
				JButton dayButton = new JButton(Integer.toString(dayNumber));
				int currentDayOfButton = Integer.parseInt(dayButton.getText());
				dayButton.setBackground(Color.WHITE);
				dayButton.setBorderPainted(false);
				dayButton.setFocusPainted(false);
				dayButton.setOpaque(true);
				dayButton.addActionListener(event -> {
					selected.setForeground(textColor);
					selected.setBorderPainted(false);
					selected.setFocusPainted(false);
					selected = dayButton;
					dayButton.setForeground(selectedDayColor);
					dayButton.setBorderPainted(true);
					dayButton.setBorder(thickBorder);
					dateLabel.setText("Date: " + (currentMonth) + "/" 
					+ (currentDayOfButton) + "/" + (currentYear));
					t.update(currentDayOfButton, currentMonth, currentYear);
				});
				if (dayNumber == currentDay) {
					selected = dayButton;
					dayButton.setForeground(selectedDayColor);
					dayButton.setBorderPainted(true);
					dayButton.setFocusPainted(false);
					dayButton.setBorder(thickBorder);
				}
				mainPanel.add(dayButton);
				dayNumber++;
			}
			i++;
		}
		dateLabel.setText("Date: " + (currentMonth) + "/" + (currentDay) + "/" + (currentYear));
		monthLabel.setText(getMonth(currentMonth));
		yearSelector.setSelectedItem(currentYear);
		mainPanel.repaint();
	}
	
	public void repaintAll() {
		for (Component c : this.getRootPane().getComponents()) {
			c.repaint();
		}
	}
	
	private String getMonth(int index) {
		return months[index-1];
	}
	
	public void setFrameBackgroundColor(Color color) {
		if(color.equals(Color.BLACK)) {
			for (JPanel p : backgrounds) {
				p.setBackground(color);
			} 
			for(JButton b : buttons) {
				b.setBorderPainted(false);
				b.setOpaque(true);
				b.setBackground(color);
				b.setForeground(Color.WHITE);
			}
			monthLabel.setForeground(Color.WHITE);
			dateLabel.setForeground(Color.WHITE);
		}
		else {
			for (JPanel p : backgrounds) {
				p.setBackground(Color.LIGHT_GRAY);
			} 
			for(JButton b : buttons) {
				b.setBorderPainted(false);
				b.setOpaque(true);
				b.setBackground(Color.LIGHT_GRAY);
				b.setForeground(Color.BLACK);
			}
			monthLabel.setForeground(Color.BLACK);
			dateLabel.setForeground(Color.BLACK);
		}
	
		setBackground(color);
	}
	
	
	public void setCalendarBackgroundColor(Color c) {
		calendarBackgroundColor = c;
	}
	
	public void setSelectedDayColor(Color c) {
		selectedDayColor = c;
	}

	public Color getFrameBackgroundColor() {
		return frameBackgroundColor;
	}
	
	public Color getCalendarBackgroundColor() {
		return calendarBackgroundColor;
	}
	
	public Color getSelectedDayColor() {
		return selectedDayColor;
	}
}

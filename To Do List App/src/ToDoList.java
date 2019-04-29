import java.util.ArrayList;

public class ToDoList {
	private ArrayList<Day> days;
	
	public ToDoList() {
		days = new ArrayList<>();
	}
	
	public ArrayList<Day> getDays() {
		return days;
	}
	
	public void addDay(Day d) {
		days.add(d);
	}
	
	public Day getDay(int day, int month, int year) {
		for(Day d : days) {
			if(d.getDay() == day && d.getMonth() == month && d.getYear() == year)
				return d;
		}
		return null;
	}
	
	public void removeDay(Day d) {
		if(days.contains(d))
			days.remove(d);
	}
}

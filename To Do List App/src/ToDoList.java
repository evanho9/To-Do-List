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
	
	public Day getDay(String day, String month, String year) {
		for(Day d : days) {
			if(d.getDay().equals(day) && 
					d.getMonth().equals(month) &&
					d.getYear().equals(year))
				return d;
		}
		return null;
	}
	
	public void removeDay(Day d) {
		if(days.contains(d))
			days.remove(d);
	}
}

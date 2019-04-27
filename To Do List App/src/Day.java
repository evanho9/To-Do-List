import java.util.ArrayList;

public class Day {
	private ArrayList<String> tasks;
	private String day, month, year;
	
	public Day (String day, String month, String year) {
		tasks = new ArrayList<>();
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public ArrayList<String> getTasks() {
		return tasks;
	}
	
	public void addTask(String s) {
		tasks.add(s);
	}
	
	public String getTask(int index) {
		return tasks.get(index);	
	}
	
	public void removeTask(String t) {
		if(tasks.contains(t))
			tasks.remove(t);
	}
	
	public String getDay() {
		return day;
	}
	
	public String getMonth() { 
		return month;
	}
	
	public String getYear() {
		return year;
	}
	
}

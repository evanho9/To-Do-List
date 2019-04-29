import java.util.ArrayList;

public class Day {
	private ArrayList<Task> tasks;
	private int day, month, year;
	
	public Day (int day, int month, int year) {
		tasks = new ArrayList<>();
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	
	public void addTask(Task s) {
		tasks.add(s);
	}
	
	public Task getTask(int index) {
		return tasks.get(index);	
	}
	
	public void removeTask(String t) {
		if(tasks.contains(t))
			tasks.remove(t);
	}
	
	public int getDay() {
		return day;
	}
	
	public int getMonth() { 
		return month;
	}
	
	public int getYear() {
		return year;
	}
	
}

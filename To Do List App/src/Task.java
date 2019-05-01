
public class Task {
	
	private int day, month, year;
	private String task;
	
	public Task (int day, int month, int year, String s) {
		this.day = day;
		this.month = month;
		this.year = year;
		task = s;
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
	
	public String getTask() {
		return task;
	}
	
	public void setTask(String s) {
		task = s;
	}
	
	public String toString() {
		return "Task: " + task + "." + "(Day: " + day + ". Month:" + month + ". Year:" + year + ")";
	}
}

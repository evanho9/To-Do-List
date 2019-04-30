import java.util.ArrayList;

public class ToDoList {
	private ArrayList<Task> tasks;
	
	public ToDoList() {
		tasks = new ArrayList<>();
	}
	
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	
	public void addTask(Task t) {
		tasks.add(t);
	}
	
	public ArrayList<Task> getTasksOnDay(int day, int month, int year) {
		ArrayList<Task> result = new ArrayList<>();
		for(Task t : tasks) {
			System.out.println(t);
			if(t.getDay() == day && t.getMonth() == month && t.getYear() == year)
				result.add(t);
		}
		return result;
	}
	
	public void removeTask(int index) {
		tasks.remove(index);
	}
	
	public Task getTaskAtIndex(int index) {
		return tasks.get(index);
	}
}

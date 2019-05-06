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
			if(t.getDay() == day && t.getMonth() == month && t.getYear() == year)
				result.add(t);
		}
		return result;
	}
	
	public void removeTask(Task task) {
		tasks.remove(task);
	}
	
	public Task getTaskAtIndex(int index) {
		return tasks.get(index);
	}
	
	public void editTask(Task currentTask, Task newTask) {
		int currentTaskIndex = 0;
		for(int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).getDay() == currentTask.getDay() && tasks.get(i).getMonth() == currentTask.getMonth()
					&& tasks.get(i).getYear() == currentTask.getYear() 
					&& tasks.get(i).getTask().equals(currentTask.getTask()))
					currentTaskIndex = i;
		}
		tasks.set(currentTaskIndex, newTask);
	}
	
	public String getAllTasksInCurrentMonth(int month, int year) {
		String result = "";
		for(Task t : tasks) {
			if(t.getMonth() == month && t.getYear() == year)
				result += "\t" + t.toString() + "\n";
		}
		
		return result;
		
	}
}

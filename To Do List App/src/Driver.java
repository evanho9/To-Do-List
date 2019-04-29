
public class Driver {
	public static void main(String[] args) {
		CalendarFrame f = new CalendarFrame();
		ToDoListFrame t = new ToDoListFrame();
		f.connectToDoListFrame(t);
	}
}

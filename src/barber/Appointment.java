package barber;

public class Appointment {
	private String name;
	private int price;
	private int duration;
	private String description;
	
	public Appointment(){
		name = "empty";
		price = -1;
		duration = -1;
		description = "";
	}
	
	boolean isEmpty(){
		return name.equals("empty");
	}
	
	public int getDuration() {
		return duration;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String toString(){
		String ans = "[";
		ans += "Name: " + name;
		ans += "\nPrice: " + price;
		ans += "\nDuration: " + duration;
		ans += "\nDescription: " + description;
		ans += "]\n";
		return ans;
	}

	public int getMinutes() {
		return duration%60;
	}
	public int getHours() {
		return duration/60;
	}
}

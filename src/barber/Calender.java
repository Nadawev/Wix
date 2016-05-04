package barber;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.ConcurrentHashMap;
import com.google.gson.Gson;


public class Calender {
	private ConcurrentHashMap<Time, Appointment> calender;
	private barber.test.Appointment[] appointments;
	
	private static class SingletoneHolder{
		private static Calender instance = new Calender();
	}
	
	private Calender(){
		calender = new ConcurrentHashMap<Time, Appointment>();
		//resetCalender();
		Gson gson = new Gson();
		JsonObject o = null;
		BufferedReader jsonFile = null;
		try{
			jsonFile = new BufferedReader(new FileReader("appointments.json"));
			o = gson.fromJson(jsonFile, JsonObject.class);
			jsonFile.close();
		} catch (IOException e){} 
		appointments = o.getAppointment();
	}
	
	private void resetCalender() {
		for(int i=0;i<24; i++){
			for(int j=0;j<60;j++){
				Time time = new Time(i, j, 0);
				calender.put(time, null);
			}
		}
		
	}
	
	/*public String addAppointment(Appointment a, Time t){
		if(calender.get(t)!=null){
			for(int i=t.getHours(); i<a.getDuration().getHours(); i++){
				for(int j=t.getMinutes(); j<a.getDuration().getMinutes();j++){
					if(calender.get(new Time(i,j,0))!=null)
						return "Unable to make an appointment";
				}
			}
			for(int i=t.getHours(); i<a.getDuration().getHours(); i++){
				for(int j=t.getMinutes(); j<a.getDuration().getMinutes();j++){
					calender.put(new Time(i,j,0),a);
				}
			}
			return "Your appointment has been set at: " + t.toString();
		}
		return "Unable to make an appointment";
	}*/

	public static Calender getInstance(){
		return SingletoneHolder.instance;
	}
	
	public String printAppointments(){
		String ans = "";
		for(int i=0; i<appointments.length; i++)
			ans += appointments[i];
		return ans;
	}
	
	public static class Appointment {
		private String name;
		private int price;
		private int duration;
		private String description;
		
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
	}

}

package barber;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.ConcurrentHashMap;
import com.google.gson.Gson;


public class Calender {
	private ConcurrentHashMap<Time, Appointment> calender;
	private Appointment[] appointments;
	
	private static class SingletoneHolder{
		private static Calender instance = new Calender();
	}
	
	private Calender(){
		calender = new ConcurrentHashMap<Time, Appointment>();
		resetCalender();
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
				calender.put(time, new Appointment());
			}
		}
	}
	
	public String addAppointment(String str, Time t){
		Appointment a = searchForCut(str);
		if(calender.get(t)!=null && a != null){
			for(int i=t.getHours(); i<=t.getHours()+a.getHours(); i++){
				for(int j=t.getMinutes(); j<t.getMinutes()+a.getMinutes();j++){
					if(!calender.get(new Time(i,j,0)).isEmpty())
						return "Unable to make an appointment at " + t;
				}
			}
			for(int i=t.getHours(); i<=t.getHours()+a.getHours(); i++){
				for(int j=t.getMinutes(); j<t.getMinutes()+a.getMinutes();j++){
					calender.put(new Time(i,j,0),a);
				}
			}
			return "Your appointment has been set at: " + t.toString();
		}
		return "Unable to make an appointment for cut " + str;
	}

	private Appointment searchForCut(String str) {
		for(int i=0; i<appointments.length; i++)
			if (appointments[i].getName().equals(str))
				return appointments[i];
		return null;
	}

	public static Calender getInstance(){
		return SingletoneHolder.instance;
	}
	
	public String printAppointments(){
		String ans = "";
		for(int i=0; i<appointments.length; i++)
			ans += appointments[i] + "\n";
		return ans;
	}
	
}

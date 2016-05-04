package barber;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;

public class test {
	private Appointment[] a;
	
	public test(){
		Gson gson = new Gson();
		JsonObject o = null;
		BufferedReader jsonFile = null;
		try{
			jsonFile = new BufferedReader(new FileReader("appointments.json"));
			o = gson.fromJson(jsonFile, JsonObject.class);
			jsonFile.close();
		} catch (IOException e){System.out.println("fail");}
		a = o.getAppointment();
	}
	
	public static void main(String args[]) {
		test t = new test();
		Appointment[] q = t.getAppointment();
		for(int i=0; i<q.length; i++)
			System.out.println(q[i].toString());	
	}

	public Appointment[] getAppointment() {
		return a;
	}
	


}

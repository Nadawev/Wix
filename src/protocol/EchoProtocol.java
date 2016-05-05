package protocol;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import barber.Calender;
import tokenizer.StringMessage;

/**
 * a simple implementation of the server protocol interface
 */
public class EchoProtocol implements AsyncServerProtocol<StringMessage> {

	private boolean _shouldClose = false;
	private boolean _connectionTerminated = false;
	

	/**
	 * processes a message<BR>
	 * this simple interface prints the message to the screen, then composes a simple
	 * reply and sends it back to the client
	 *
	 * @param msg the message to process
	 * @param callback a unique object associated to the client, used to communicate with the server
	 * @return the reply that should be sent to the client, or null if no reply needed
	 */
	@Override
	public void processMessage(StringMessage msg, ProtocolCallback<StringMessage> callback) {        
		String result="UNIDENTIFIED";
		switch (msg.getCommand()){
		
			case "say":
				System.out.println("Got say");
				try {
					callback.sendMessage(new StringMessage("SERVER " + msg.getParam()+ " " + msg.getSecondParam()));
				} catch (IOException e) {e.printStackTrace();}
				break;
			
			
			case "print":
				System.out.println("Got print");
				result = Calender.getInstance().printAppointments();
				try {
					callback.sendMessage(new StringMessage(result));
				} catch (IOException e) {e.printStackTrace();}
				break;
				
			case "set":
				System.out.println("Got set");
				DateFormat formatter = new SimpleDateFormat("HH:mm");
				Time timeValue;
				try {
					timeValue = new Time(formatter.parse(msg.getSecondParam()).getTime());
					result = Calender.getInstance().addAppointment(msg.getParam(), timeValue);
				} catch (ParseException e1) {
					result = "error time parse";
				}
				try {
					callback.sendMessage(new StringMessage(result));
				} catch (IOException e) {e.printStackTrace();}
				break;
				
			case "free":
				System.out.println("Got free");
				result= Calender.getInstance().getFreeTime();
				try {
					callback.sendMessage(new StringMessage(result));
				} catch (IOException e) {e.printStackTrace();}
				break;
				
			default:
				try {
					callback.sendMessage(new StringMessage(result));
				} catch (IOException e) {e.printStackTrace();}
				break;
		}
	}

	/**
	 * detetmine whether the given message is the termination message
	 *
	 * @param msg the message to examine
	 * @return whether the command is 'QUIT'
	 */
	@Override
	public boolean isEnd(StringMessage msg) {
		return msg.getCommand().equals("QUIT");
	}

	/**
	 * Is the protocol in a closing state?.
	 * When a protocol is in a closing state, it's handler should write out all pending data, 
	 * and close the connection.
	 * @return true if the protocol is in closing state.
	 */
	@Override
	public boolean shouldClose() {
		return this._shouldClose;
	}

	/**
	 * Indicate to the protocol that the client disconnected.
	 */
	@Override
	public void connectionTerminated() {
		this._connectionTerminated = true;
	}

}

package protocol;

import java.io.IOException;

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
		String optional="";
		boolean msgProcessed = true;
		switch (msg.getCommand()){
		
			case "say":
				try {
					callback.sendMessage(new StringMessage("SERVER " + msg.getParam()));
				} catch (IOException e) {e.printStackTrace();}
				break;
			
			case "ooriya":
				try {
					callback.sendMessage(new StringMessage("ooriya is a yemen" ));
				} catch (IOException e) {e.printStackTrace();}
				break;
			
			case "print":
				result = Calender.getInstance().printAppointments();
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

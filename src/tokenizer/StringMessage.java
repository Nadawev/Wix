package tokenizer;

public class StringMessage implements Message<StringMessage> {
	private final String message;
	
	public StringMessage(String message){
		this.message=message;
	}

	public String getMessage(){
		return message;
	}
	
	@Override
	public String toString() {
		return message;
	}
	
	@Override
	public boolean equals(Object other) {
		return message.equals(other);
	}
	
	/**
	 * extracts the command from the message
	 * @return the command
	 */
	public String getCommand(){
		if (message.contains(" "))
			return message.substring(0,message.indexOf(" "));
		return message.substring(0,message.length()-1);
	}
	
	/**
	 * extracts the parameters from the message
	 * @return the parameters
	 * returns "null" if the parameter is empty
	 */
	public String getParam(){
		String s = "";
		if (message.indexOf(" ")!=-1){
			s = message.substring(message.indexOf(" ")+1);
			if(s.indexOf(" ") != -1)
				s = s.substring(0,s.indexOf(" "));
		}
		return s;
	}
	
	public String getSecondParam(){
		String s = "";
		if (message.indexOf(" ")!=-1){
			s = message.substring(message.indexOf(" ")+1);
			if(s.indexOf(" ") != -1)
				return s.substring(s.indexOf(" ")+1);
		}
		return s;
	}
}

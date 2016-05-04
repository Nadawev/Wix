package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
 
public class socket_client
{
    public static void main(String[] args) throws IOException 
    {
        Socket s = new Socket();
        String host = args[1];
        Integer port = Integer.parseInt(args[2]);         
        try
        {
        	s.connect(new InetSocketAddress(host , port));
        	System.out.println("Connected");	
        }
         
        //Host not found
        catch (UnknownHostException e) 
        {
            System.err.println("Don't know about host : " + host);
            System.exit(1);
        }
         
    new Thread(new sender(s)).start();  
    new Thread(new receiver(s)).start();
   }
}

class sender implements Runnable{
	private Socket s;
	public sender(Socket s){
		this.s = s;
	}

	public void run() {
		PrintWriter s_out = null;
		Scanner scanner = new Scanner(System.in);
		while(true){
			//writer for socket
            try {
				s_out = new PrintWriter( s.getOutputStream(), true);
			} catch (IOException e) {}
			String msg = scanner.nextLine();
			s_out.println(msg);
			if(s.isClosed())
				break;
		}
		scanner.close();
	}
}

class receiver implements Runnable{
	private Socket s;
	public receiver(Socket s){
		this.s=s;
	}
	
	public void run() {
		BufferedReader s_in = null;
		while(true){
			try {
				//reader for socket
				s_in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			} catch (IOException e) {}
			String response;
			try {
				while ((response = s_in.readLine()) != null){
				      System.out.println( response );
				}
			} catch (IOException e){}
			if(s.isClosed())
				break;
		}
		 
        
	}
	
	
}

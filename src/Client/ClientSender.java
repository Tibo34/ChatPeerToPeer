package Client;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSender{
	
	private Socket socketSend;
	private ObjectOutputStream output; // stream data out
	private User user;
	private Client client;
	
	public ClientSender(Socket socket,User u, Client c) {		
		socketSend=socket;		
		user=u;
		client=c;
		setupStreams();
		sendMessage(" user: "+user+", connecté");		
	}
	
	private void setupStreams(){	    
	    try {
	    	output = new ObjectOutputStream(socketSend.getOutputStream()); 
			output.flush();
		} catch (IOException e) {			
			e.printStackTrace();
			close();
		}
	}
	
	// send message to the client
		public boolean sendMessage(String message){
		    try{		    	
		        output.writeObject(message);
		        output.flush(); 
		        return true;
		    }catch(IOException ioexception){
		    	ioexception.printStackTrace();		    			    	
		    	close();
		    }
		    return false;
		}
	
		
		public void close() {
			try {
				socketSend.close();
				client.connectionClose();
				System.out.println("socket close");				
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}

		public void setUser(User u) {
			user=u;			
		}

}

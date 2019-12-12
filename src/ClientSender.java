import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSender {
	
	private Socket socketSend;
	private ObjectOutputStream output; // stream data out
	private ObjectInputStream input; // stream data in
	
	public ClientSender(Socket socket) {
		socketSend=socket;
		try {
			setupStreams();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setupStreams() throws IOException{
	    output = new ObjectOutputStream(socketSend.getOutputStream()); // set up pathway to send data out
	    output.flush(); // move data away from your machine	   
	}
	
	// send message to the client
		public boolean sendMessage(String message){
		    try{
		        output.writeObject(message);
		        output.flush(); 
		        return true;
		    }catch(IOException ioexception){
		    	ioexception.printStackTrace();
		        //theChatWindow.append("\n ERROR: Message cant send");
		    	return false;
		    }
		}

}

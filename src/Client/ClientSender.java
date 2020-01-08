package Client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Controller.ControllerChat;
import frames.FrameChat;

public class ClientSender implements Runnable{
	
	private Socket socketSend;
	private ObjectOutputStream output; // stream data out
	 private User user;
	
	public ClientSender(Socket socket) {
		socketSend=socket;
		try {
			setupStreams();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sendMessage(user+" connecté");
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
		    	FrameChat frame=ControllerChat.getController().getFrame();
		    	frame.showMessage("\n ERROR: Message cant send");
		    	return false;
		    }
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
		public void close() {
			try {
				socketSend.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void setUser(User u) {
			user=u;			
		}

}

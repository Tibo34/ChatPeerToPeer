package Client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import Controller.ControllerChat;

public class ClientRecever implements Runnable {
	
	
	private Socket socketRecever;
	private ObjectInputStream input; // stream data in
	private String message="";
	private User userConnect;
	private Client client;
	
	public ClientRecever(Socket socket, Client c) {
		socketRecever=socket;
		client=c;
		try {
			setupStreams();
			getUser();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	private void setupStreams() throws IOException{
	    input = new ObjectInputStream(socketRecever.getInputStream()); // set up pathway to allow data in
	}
	
	private void getUser() {		
		readMessage();						
		if(message.contains("user")) {
			String name=message.split(",")[0].split(":")[1];
			name.trim();
			userConnect=new User(name);				
			ControllerChat.getController().getFrame().addUser(userConnect);
			ControllerChat.getController().getFrame().editable();
		}		
	}
	

	public User getUserConnect() {
		return userConnect;
	}

	public void setUserConnect(User userConnect) {
		this.userConnect = userConnect;
	}

	@Override
	public void run() {
		System.out.println("recever ok");
		while(socketRecever.isConnected()) {			
				receveMessage();			 
		}		
		System.out.println("socket close");
	}
	
	public String getMessage() {		
		readMessage();
		System.out.println(message);			
		return message;
	}

	private void receveMessage() {
		readMessage();
		System.out.println(message);
		Message mess=new Message(userConnect, message);
		ControllerChat.getController().setMessage(mess);
	}

	private void readMessage(){
		try {
			message=(String)input.readObject();
		} catch (ClassNotFoundException | IOException e) {			
			e.printStackTrace();
			close();
		}
	}
	
	
	
	public void close() {
		try {
			socketRecever.close();
			ControllerChat.getController().getFrame().removeUser(userConnect);
			client.connectionClose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

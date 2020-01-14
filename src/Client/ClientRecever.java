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
	
	public ClientRecever(Socket socket) {
		socketRecever=socket;
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
		try {
			message=(String)input.readObject();
			System.out.println(message);
			if(message.contains("user")) {
				String name=message.split(",")[0].split(":")[1];
				name.trim();
				userConnect=new User(name);
			}
		} catch (Exception e) {
			// TODO: handle exception
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
		while(socketRecever.isConnected()) {
			try {
				receveMessage();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

	private void receveMessage() throws IOException, ClassNotFoundException {
		message=(String)input.readObject();
		Message mess=new Message(userConnect, message);
		ControllerChat.getController().setMessage(mess);
	}
	
	
	
	public void close() {
		try {
			socketRecever.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

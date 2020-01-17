package Controller;
import java.util.ArrayList;

import Client.Client;
import Client.Message;
import frames.Frame;


public class ControllerChat {
	
	
	private ArrayList<Client> clients;
	private Frame frame;	
	private static ControllerChat instance;
	
	
	private ControllerChat() {
			clients=new ArrayList<Client>();		
	}
	
	public static ControllerChat getController() {
		if(instance==null) {
			instance=new ControllerChat();
		}
		return instance;
	}
	
	public void sendMessAll(String str) {
		for (Client c : clients) {
			c.sendMessage(str);
		}
	}
	
	public void setMessage(Message mess) {
		frame.addMessage(mess);
	}	


	public Frame getFrame() {
		return frame;
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
	}

	
	public void addClient(Client c) {
		clients.add(c);
		frame.addUser(c.getUser());
	}
	
	public Client getLastClient() {
		return clients.get(clients.size()-1);
	}

	public void removeClient(Client client) {
		clients.remove(client);		
	}
	

}

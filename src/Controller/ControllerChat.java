package Controller;
import java.util.ArrayList;

import javax.swing.JFrame;

import Client.Client;
import Client.Message;
import Client.User;
import Serveur.Server;
import frames.Frame;


public class ControllerChat {
	
	
	private ArrayList<Client> clients;
	private Frame frame;
	private Server serve;
	
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

	public Server getServe() {
		return serve;
	}

	public void setServe(Server serve) {
		this.serve = serve;
	}
	
	public void addClient(Client c) {
		clients.add(c);
		frame.addUser(c.getUser());
	}

	public void setUser(User u) {
		serve.setUser(u);	
	}

	public Client getLastClient() {
		return clients.get(clients.size()-1);
	}
	
	
	
	

}

package Controller;
import Client.Client;
import Serveur.Server;
import frames.FrameChat;

public class ControllerChat {
	
	private Client client;
	private FrameChat frame;
	private Server serve;
	
	private static ControllerChat instance;
	
	
	private ControllerChat() {
			
		
	}
	
	public static ControllerChat getController() {
		if(instance==null) {
			instance=new ControllerChat();
		}
		return instance;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public FrameChat getFrame() {
		return frame;
	}

	public void setFrame(FrameChat frame) {
		this.frame = frame;
	}

	public Server getServe() {
		return serve;
	}

	public void setServe(Server serve) {
		this.serve = serve;
	}
	
	
	
	

}

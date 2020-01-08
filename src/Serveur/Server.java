package Serveur;
import java.io.*;
import java.net.*;

import javax.swing.JFrame;

import Client.Client;
import Controller.ControllerChat;
import Utile.Utility;
import frames.FrameChat;



public class Server implements Runnable {

	private ServerSocket server;
	private Socket connection; // socket means set up connetion between 2 computers
	private static Server instance;
	private Thread thread;
	private FrameChat frame;
	
	private Server() {
		server=Utility.getServerSocketPortFree(6000);
		frame=FrameChat.initFrame();
	}
	
	public void startServer() {
		thread=new Thread(this);
		thread.start();	
	}
	
	public void stop() {
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread.stop();		
	}
	
	@Override
	public void run() {		
		boolean serverOpen=true;
		while(serverOpen) {
			  waitForConnection(); 
		}			
	}
	
	
	public static Server createServer() {
		if(instance==null) {
			instance=new Server();
		}
		return instance;
	}
	
	//Wait for a connection then display connection information
	private void waitForConnection(){	 
	    try {
	        connection = server.accept();
	        Client client=new Client(connection);   	      
	        ControllerChat controller=ControllerChat.getController();
	        controller.setClient(client);
	        controller.setFrame(frame);
	        controller.setServe(instance);
	    } catch (IOException ioexception) {
	        ioexception.printStackTrace();
	    }	 
	}

	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}
	

	
	
	

	
}






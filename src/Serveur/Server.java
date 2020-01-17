package Serveur;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Client.Client;
import Client.User;
import Controller.ControllerChat;
import ScannerLan.ScannerLan;



public class Server implements Runnable {

	private ServerSocket server;
	private Socket connection; // socket means set up connetion between 2 computers
	private Thread thread;
	private User user;
	private ControllerChat controller;
	private Client lastClient=null;
	private Thread threadServer;
	private GestionServeur gestion;		
	private static int portStart=6000;
	private boolean serverOpen;	
	
	
	public Server(GestionServeur g,User u) {
		this(g,u,portStart);		
	}

	public Server(GestionServeur g, User u, int localPort) {
		user=u;
		controller=ControllerChat.getController();
		server=ScannerLan.getServerSocketPortFree(localPort);
		gestion=g;
		serverStart();
	}
	




	private void serverStart() {
		threadServer = new Thread(this);			
		threadServer.start();
	}
	
	
	public User getUser() {
		return user;
	}

	public void startServer() {
		thread=new Thread(this);
		thread.start();	
	}
	
	public void stop() {
		try {
			server.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		thread.stop();		
	}
	
	public void connectionClose() {
		gestion.restartLastServer();
	}
	
	@Override
	public void run() {				
		serverOpen=true;	
		while(serverOpen) {				
			waitForConnection(); 					 
		}			
	}	
	

	//Wait for a connection then display connection information
	private void waitForConnection(){	 
	    try {	    	
	        connection = server.accept();	
	        lastClient=gestion.getLastClient();
	        if(lastClient==null) {	        	
	        	lastClient=new Client(connection,user,this);
		        controller.addClient(lastClient);	        		        	
	        }else {	        	
	        	lastClient.setReceve(connection);
	        }	        
	        gestion.createServer();	
	        serverOpen=false;	       
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

	public void setUser(User u) {
		user=u;
		saveUser();
	}
	
	public void saveUser() {		
		gestion.saveUser();
	}	


	public Thread getThreadServer() {
		return threadServer;
	}


	public void setThreadServer(Thread threadServer) {
		this.threadServer = threadServer;
	}


	
	

	
}






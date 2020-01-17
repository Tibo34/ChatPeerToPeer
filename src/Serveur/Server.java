package Serveur;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

import Client.Client;
import Client.User;
import Controller.ControllerChat;
import ScannerLan.ScannerLan;
import frames.Frame;



public class Server implements Runnable {

	private static final String USER2 = "user";
	private ServerSocket server;
	private Socket connection; // socket means set up connetion between 2 computers
	private Thread thread;
	private ScannerLan scanNetWork;
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

	public void scanNetWork() {
		scanNetWork.scanLocal();
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
		System.out.println("Serveur lanc�");
		serverOpen=true;	
		while(serverOpen) {				
			waitForConnection(); 					 
		}			
	}	
	

	//Wait for a connection then display connection information
	private void waitForConnection(){	 
	    try {	    	
	        connection = server.accept();	
	        lastClient=new Client(connection,user,this);
	        controller.addClient(lastClient);
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






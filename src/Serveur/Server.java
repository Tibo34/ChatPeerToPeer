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
import Utile.Utility;
import frames.Frame;



public class Server implements Runnable {

	private static final String USER2 = "user";
	private ServerSocket server;
	private Socket connection; // socket means set up connetion between 2 computers
	private static Server instance;
	private Thread thread;
	private Frame frame;
	private ArrayList<AdressNetWork> ipsConnect;
	private String ipLocal;
	private ScannerLan scanNetWork;
	private User user;
	private ControllerChat controller;
	private Client lastClient=null;
	private Thread threadServer;
	
	private String fileName="userSave.properties";
	
	
	private Server(int p) {
		server=Utility.getServerSocketPortFree(p);	
		 threadServer = new Thread(this);
		ControllerChat.getController().setServe(this);
		scanNetWork=new ScannerLan();
		ipLocal=scanNetWork.getIP();
		scanNetWork();
		loadUser();		
		frame=new Frame(this,ipsConnect,user);		
		controller=ControllerChat.getController();
		controller.setFrame(frame);
		controller.setServe(this);
		threadServer.start();
	}
	
	
	public User getUser() {
		return user;
	}

	public void scanNetWork() {
		ipsConnect=scanNetWork.scanLocal();
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
	
	@Override
	public void run() {		
		System.out.println("Serveur lancé");
		boolean serverOpen=true;		
		while(serverOpen) {
			  waitForConnection(); 
		}			
	}
	
	
	public static Server createServer(int p) {
		if(instance==null) {			
			instance=new Server(p);
		}
		return instance;
	}
	
	public void connection(AdressNetWork addr) {		
		lastClient=new Client(addr,user);		
		controller.addClient(lastClient);
	}
	
	//Wait for a connection then display connection information
	private void waitForConnection(){	 
	    try {
	        connection = server.accept();
	        if(lastClient==null) {
	        	lastClient=new Client(connection,user);	 	        	
	        }else {
	        	System.out.println("client retour");	        	
	 	        lastClient.initConnectionReceve(connection);
	        }	     
	       
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
		Properties prop=new Properties();
		prop.setProperty(USER2, user.getName());
		try {
			File file=new File(fileName);
			FileOutputStream f= new FileOutputStream(file);
			prop.store(f, null);
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public void loadUser() {
		Properties prop=new Properties();
		try {			
			File file=new File(fileName);
			if(file.exists()) {
		        FileInputStream in = new FileInputStream(file); 	  
		        prop.load(in);	 
		        String userName=prop.getProperty(USER2);
		        if(!userName.isEmpty()) {
		        	 setUser(new User(userName));
		        }	    
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
				
	}


	public Thread getThreadServer() {
		return threadServer;
	}


	public void setThreadServer(Thread threadServer) {
		this.threadServer = threadServer;
	}


	public void restartServer(int p) {
		
		server=Utility.getServerSocketPortFree(p);
		frame.setPort(p);
		
	}
	
	
	

	
}






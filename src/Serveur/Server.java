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
	
	private String fileName="userSave.properties";
	
	
	private Server() {
		server=Utility.getServerSocketPortFree(6000);
		ControllerChat.getController().setServe(this);
		scanNetWork=new ScannerLan();
		ipLocal=scanNetWork.getIP();
		scanNetWork();
		loadUser();
		frame=new Frame(server,ipsConnect,user);		
		controller=ControllerChat.getController();
		controller.setFrame(frame);
		controller.setServe(this);
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
	
	public void connection(AdressNetWork addr) {
		Client client=new Client();
		client.ConnectionInitRecever(addr);
		controller.addClient(client);
	}
	
	//Wait for a connection then display connection information
	private void waitForConnection(){	 
	    try {
	        connection = server.accept();
	        Client last=ControllerChat.getController().getLastClient();
	        if(last.getSend()==null) {
	        	Client client=new Client(connection);   	       
	        	controller.addClient(client);
	        }else {
	        	last.setSend(connection);
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
	
	
	

	
}






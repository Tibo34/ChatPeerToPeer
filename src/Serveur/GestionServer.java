package Serveur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import Client.User;
import ScannerLan.ScannerLan;
import frames.Frame;

public class GestionServer {

	private ArrayList<Server> servers;
	private ArrayList<Thread> allthreads;
	private Frame frame;
	private User user;
	private ArrayList<AdressNetWork> ipsConnect;
	private String ipLocal;
	private ScannerLan scanNetWork;
	
	public GestionServer(){
		servers=new ArrayList<Server>();
		scanNetWork=new ScannerLan();
		ipLocal=scanNetWork.getIP();
		scanNetWork();
		loadUser();		
		frame=new Frame(this,ipsConnect,user);
	}
	
	public void scanNetWork() {
		ipsConnect=scanNetWork.scanLocal();
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

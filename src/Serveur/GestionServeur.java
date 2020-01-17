package Serveur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

import Client.Client;
import Client.User;
import Controller.ControllerChat;
import ScannerLan.ScannerLan;
import frames.Frame;
import frames.PopUp;

public class GestionServeur {
	
	private ArrayList<Server> servers;
	private Server lastServer;
	private Client lastClient;
	private Frame frame;
	

	private ArrayList<AdressNetWork> ipsConnect;	
	private ScannerLan scanNetWork;
	private User user;
	private ControllerChat controller;
	private static GestionServeur instance=new GestionServeur();
	private String fileName="userSave.properties";
	private int maxIp =25;
	private static final String USER2 = "user";	

	private GestionServeur() {
		super();	
		this.servers = new ArrayList<Server>();
		createServer();
		loadUser();
		scanNetWork=new ScannerLan(maxIp);		
		scanNetWork();				
		frame=new Frame(ipsConnect);		
		controller=ControllerChat.getController();
		controller.setFrame(frame);
		
		frame.setUserLocal(user);
		frame.setGestion(this);		
	}
	
	/**
	 * Retourne une instance de GestionServeur
	 * @return GestionServeur
	 */
	public static GestionServeur getGestionServer() {		
		return instance;
	}
	
	/**
	 * Cr�er une nouvelle SocketServer
	 * @return Server
	 */
	public Server createServer() {
		lastServer=new Server(this,user);
		servers.add(lastServer);
		return lastServer;
	}
	
	/**
	 * Sauvegarde le profil utilisateur dans un fichier local
	 */
	public void saveUser() {		
		Properties prop=new Properties();
		prop.setProperty(USER2, user.getName());
		prop.setProperty("maxIp",new Integer(maxIp).toString());
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
	
	

	/**
	 * Charge le profil utilisateur. Si il n'y en a pas demande d'en cr�er un
	 */
	public void loadUser() {
		Properties prop=new Properties();
					
			File file=new File(fileName);
			if(file.exists()) {	        	  
		        try {
		        	FileInputStream in = new FileInputStream(file); 
					prop.load(in);
					String userName=prop.getProperty(USER2);
					String max=prop.getProperty("maxIp");
					maxIp=Integer.parseInt(max);					 
					 if(!userName.isEmpty()) {
			        	 user=new User(userName);	
			        	 changeAlluserServers();
			        }    
				} catch (IOException|NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		       
			}
				
	}
	
	public User createUser() {
		String userName=PopUp.popUpInput("Saisir un nom : ");
		user=new User(userName);	
		changeAlluserServers();
		saveUser();
		return user;
	}
	
	private void changeAlluserServers() {
		for (Server server : servers) {
			server.setUser(user);
		}	
	}

	public void scanNetWork() {
		ipsConnect=scanNetWork.scanLocal();
	}

	public void closeAll() {
		for (Server server : servers) {
			server.stop();
		}		
	}
	
	public void setUser(User u) {
		user=u;
		saveUser();
	}

	public void connection(AdressNetWork addr) {
		lastClient=new Client(addr,user,lastServer);		
		controller.addClient(lastClient);	
		createServer();
	}
	
	public Socket connectionSender(AdressNetWork addr,int port) throws IOException {
		Socket send=scanNetWork.connect(addr,port);
		return send;
	}

	public void restartLastServer() {
		lastServer.stop();
		lastServer=new Server(this,user,lastServer.getServer().getLocalPort());		
		
	}

	public User getUser() {
		return user;
	}
	
	public Client getLastClient() {
		return lastClient;
	}

	public void setLastClient(Client lastClient) {
		this.lastClient = lastClient;
	}
	
	
	

}

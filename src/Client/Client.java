package Client;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import Controller.ControllerChat;
import Serveur.AdressNetWork;
import Utile.Utility;

public class Client{
	
	   private Socket send;	
	   private Socket receve;
	   private User user;
	   private ClientRecever recever;
	   private ClientSender sender;
	   private Thread clientRecever;
	   private Thread clientSender;
	   private User userConnect;
	   
   /**
    * génère un client avec une socket
	 * @param s Socket
	 */
	public Client(Socket s,User u) {
		  receve=s;		
		  user=u;
		  System.out.println("Client connecté");
		  ConnectionRetour();
		  createReceverClient();		 
	   }
	   
	public Client() {}	  


	public Client(AdressNetWork addr) {
		ConnectionInitSender(addr);
	}

	public void ConnectionRetour() {			
		  InetAddress addr = ((InetSocketAddress) receve.getRemoteSocketAddress()).getAddress();
		  send=Utility.getFreePort(receve.getLocalPort(),addr);		
		  ConnectionSender();
		  echangeUser();			  
	}

	private void createReceverClient() {
		  recever=new ClientRecever(receve);
		  clientRecever=new Thread(recever);
		  clientRecever.start();
	}
	
	public void initConnectionReceve(Socket socket) {
		receve=socket;
		createReceverClient();
	}
	
	private void echangeUser() {
		System.out.println("echange user");
		sender.sendMessage("user:"+user);
		String str=recever.getMessage().split(":")[1];
		System.out.println("user : "+str);
		User user=new User(str);
		userConnect=user;
		ControllerChat.getController().getFrame().addUser(user);
		ControllerChat.getController().getFrame().editable();
	}
	
	

	public void ConnectionSender() {
		  sender=new ClientSender(send);		  
		  clientSender=new Thread(sender);
		  clientSender.start();
	}
	   
	
	public void ConnectionInitSender(AdressNetWork addr) {
		try {
			System.out.println(addr.getAdress().getHostAddress());
			send=new Socket(addr.getAdress().getHostAddress(),6000);			
			ConnectionSender();						
		} catch (UnknownHostException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	   
	   public void sendMessage(String str) {
		   sender.sendMessage(userConnect.toString()+" : "+str);			
		}
	   
		public void closeChat(){
			recever.close();
			sender.close();
			clientRecever.stop();
			clientSender.stop();		   
		}



		public User getUser() {
			return user;
		}



		public void setUser(User user) {
			this.user = user;
			sender.setUser(user);
		}

		public Socket getSend() {
			return send;
		}

		public void setSend(Socket send) {
			this.send = send;			
		}

		public Socket getReceve() {
			return receve;
		}

		public void setReceve(Socket receve) {
			this.receve = receve;
		}
	  
	

}

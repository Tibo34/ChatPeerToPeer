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


	public Client(AdressNetWork addr,User u) {
		user=u;
		ConnectionInitSender(addr);
	}

	public void ConnectionRetour() {			
		  InetAddress addr = ((InetSocketAddress) receve.getRemoteSocketAddress()).getAddress();
		  send=Utility.getFreePort(receve.getLocalPort()+1,addr);		
		  ConnectionSender();		 			  
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

	public void ConnectionSender() {
		  sender=new ClientSender(send,user);		  
		  clientSender=new Thread(sender);
		  clientSender.start();
	}
	   
	
	public void ConnectionInitSender(AdressNetWork addr) {
		try {
			System.out.println(addr.getAdress().getHostAddress());
			ControllerChat.getController().getServe().restartServer(6001);			
			send=new Socket(addr.getAdress().getHostAddress(),6000);			
			ConnectionSender();						
		} catch (UnknownHostException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	   
	   public void sendMessage(String str) {
		   sender.sendMessage(str);			
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

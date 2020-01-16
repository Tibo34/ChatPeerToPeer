package Client;
import java.io.IOException;
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
    * g�n�re un client avec une socket
	 * @param s Socket
	 */
	public Client(Socket s) {
		   receve=s;		
		  System.out.println("Client connect�");
		  ConnectionSender();
		  ConnectionRetour();
	   }
	   
	public Client() {}	  


	public Client(AdressNetWork addr) {
		ConnectionInitSender(addr);
	}

	public void ConnectionRetour() {		 
		  SocketAddress adress=send.getRemoteSocketAddress();
		  receve=Utility.getFreePort(send.getLocalPort()+1,adress);		
		  createReceverClient();
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
		sender.sendMessage("user:"+userConnect);
		String str=recever.getMessage().split(":")[1];
		System.out.println("user : "+str);
		User user=new User(str);
		userConnect=user;
		ControllerChat.getController().getFrame().addUser(user);		
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
			if(send.isConnected()) {
				System.out.println("connect�");				
			}
			ConnectionSender();						
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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

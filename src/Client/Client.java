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
	   
	   public Client(Socket s) {
		  send=s;		
		  System.out.println("Client connecté");
		  ConnectionRetour();		
	   }
	   
	   public Client() {}
	   
	  


	private void ConnectionRetour() {
		  ConnectionSender();
		  SocketAddress adress=send.getRemoteSocketAddress();
		  receve=Utility.getFreePort(send.getLocalPort(),adress);		
		  recever=new ClientRecever(receve);
		  clientRecever=new Thread(recever);
		  echangeUser();
		  clientSender.start();
		  clientRecever.start();
		  
	}
	
	private void echangeUser() {
		System.out.println("echange user");
		sender.sendMessage("user:"+userConnect);
		String str=recever.getMessage().split(":")[1];
		System.out.println("user : "+str);
		User user=new User(str);
		ControllerChat.getController().getFrame().addUser(user);;
		
	}

	public void ConnectionSender() {
		  sender=new ClientSender(send);
		  sender.setUser(user);
		  clientSender=new Thread(sender);
	}
	   
	
	public void ConnectionInitRecever(AdressNetWork addr) {
		try {
			System.out.println(addr.getAdress().getHostAddress());
			receve=new Socket(addr.getAdress().getHostAddress(),6000);
			recever=new ClientRecever(receve);
			clientRecever=new Thread(recever);
			clientRecever.start();			
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
			ConnectionSender();
		}

		public Socket getReceve() {
			return receve;
		}

		public void setReceve(Socket receve) {
			this.receve = receve;
		}



		
	   
	  
	

}

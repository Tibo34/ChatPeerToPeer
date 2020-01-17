package Client;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import Controller.ControllerChat;
import ScannerLan.ScannerLan;
import Serveur.AdressNetWork;
import Serveur.GestionServeur;
import Serveur.Server;

public class Client{
	
	   private Socket send;	
	   private Socket receve;
	   private User user;
	   private ClientRecever recever;
	   private ClientSender sender;
	   private Thread clientRecever;	  
	   private Server server;	   
  
	/**
	 * gï¿½nï¿½rer un client avec une socket
	 * @param socket Socket de connection client
	 * @param u Utilisateur local
	 * @param s Server utilisï¿½
	 */
	public Client(Socket socket,User u, Server s) {
		  this(u, s);
		  receve=socket;		 
		  System.out.println("Client connecté");		  
		  ConnectionRetour();
		  createReceverClient();		 
	   }
	   
	/**
	 * initialisation de l'user et du serveur
	 * @param u Utilisateur local
	 * @param s Server utilisé
	 */
	public Client(User u,Server s) {
		 user=u;
		 server=s;
	}	  


	/**
	 * @param addr Adresse de connection
	 * @param u Utilisateur local
	 * @param s Server utilisé
	 */
	public Client(AdressNetWork addr,User u,Server s) {
		this(u, s);
		ConnectionInitSender(addr);
	}

	/**
	 * Lorsque le serveur reï¿½oit une connection, il demande une nouvelle connection ï¿½ cette mï¿½me adresse sur le port suivant.
	 */
	public void ConnectionRetour() {			
		  InetAddress addr = ((InetSocketAddress) receve.getRemoteSocketAddress()).getAddress();
		  send=ScannerLan.getFreePort(receve.getLocalPort()+1,addr);			  
		  ConnectionSender();		 			  
	}

	private void createReceverClient() {
		  recever=new ClientRecever(receve,this);
		  clientRecever=new Thread(recever);
		  clientRecever.start();		 
	}
	
	public void initConnectionReceve(Socket socket) {
		receve=socket;
		createReceverClient();		
	}	

	public void ConnectionSender() {
		  sender=new ClientSender(send,user,this);			 
	}
	   
	
	public void ConnectionInitSender(AdressNetWork addr) {
		try {
			send=GestionServeur.getGestionServer().connectionSender(addr,server.getServer().getLocalPort());
			ConnectionSender();
		} catch (IOException e) {			
			e.printStackTrace();
			System.err.println("echec de la connection");
		}						
		
	}
	   
	   public void sendMessage(String str) {
		   sender.sendMessage(str);			
		}
	   
		public void closeChat(){
			closeSocket();
			closethread();		   
		}

		private void closeSocket() {
			recever.close();
			sender.close();
		}

		private void closethread() {
			clientRecever.stop();			
		}
		
		public void connectionClose() {			
			closeSocket();
			closethread();
			ControllerChat.getController().removeClient(this);
			server.connectionClose();			
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((clientRecever == null) ? 0 : clientRecever.hashCode());		
			result = prime * result + ((receve == null) ? 0 : receve.hashCode());
			result = prime * result + ((recever == null) ? 0 : recever.hashCode());
			result = prime * result + ((send == null) ? 0 : send.hashCode());
			result = prime * result + ((sender == null) ? 0 : sender.hashCode());
			result = prime * result + ((server == null) ? 0 : server.hashCode());
			result = prime * result + ((user == null) ? 0 : user.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Client other = (Client) obj;			
			if (user == null) {
				if (other.user != null)
					return false;
			} else if (!user.equals(other.user))
				return false;
			return true;
		}
	  
	

}

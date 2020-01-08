package Client;
import java.net.Socket;
import java.net.SocketAddress;

import Utile.Utility;

public class Client{
	
	   private Socket send;	
	   private Socket receve;
	   private User user;
	   private ClientRecever recever;
	   private ClientSender sender;
	   private Thread clientRecever;
	   private Thread clientSender;
	   
	   
	   public Client(Socket s) {
		  send=s;		
		  sender=new ClientSender(send);
		  sender.setUser(user);
		  clientSender=new Thread(sender);
		  SocketAddress adress=send.getRemoteSocketAddress();
		  receve=Utility.getFreePort(send.getLocalPort(),adress);		
		  recever=new ClientRecever(receve);
		  clientRecever=new Thread(recever);
		  clientSender.start();
		  clientRecever.start();
	   }
	   
	   
	   public void sendMessage(String str) {
		   sender.sendMessage(user.toString()+" : "+str);			
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



		
	   
	  
	

}

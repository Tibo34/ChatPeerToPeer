package Utile;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class Utility {

	

	public static ServerSocket getServerSocketPortFree(int port) {		
		ServerSocket socket=null;
		boolean portOk=true;
		for(int p=port ; p <= 65535&&portOk; p++){
	         try {
	        	 socket= new ServerSocket(p);	 
	        	 portOk=false;
	         } catch (IOException e) {
	            System.err.println("Le port " + p + " est d�j� utilis� ! ");
	         }
	      }		
		return socket;
	}
	
	
	public static Socket getFreePort(int port,SocketAddress adress) {
		Socket socket=null;
		boolean portOk=true;
		for(int p=port ; p <= 65535&&portOk; p++){
	         try {
	        	 socket= new Socket(adress.toString(),p);	 
	        	 portOk=false;	        	 
	       } catch (IOException e) {
	            System.err.println("Le port " + p + " est d�j� utilis� ! ");
	         }
	     }		
		return socket;
	}
	
}
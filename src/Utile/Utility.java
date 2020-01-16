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
	        	 System.out.println("port : "+p+" libre.");
	         } catch (IOException e) {
	            System.err.println("Le port " + p + " est déjà utilisé ! ");
	         }
	      }		
		return socket;
	}
	
	
	public static Socket getFreePort(int port,SocketAddress adress) {
		Socket socket=null;
		boolean portOk=true;
		System.out.println(adress.toString());
		for(int p=port ; p <= 65535&&portOk; p++){
	         try {	        	 
	        	 socket= new Socket(adress.toString(),p);	 
	        	 portOk=false;	    
	        	 System.out.println("port : "+p);
	       } catch (IOException e) {
	            System.err.println("Le port " + p + " est déjà utilisé ! ");
	         }
	     }		
		return socket;
	}
	
}

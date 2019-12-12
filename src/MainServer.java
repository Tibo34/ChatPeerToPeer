import java.io.IOException;
import java.net.ServerSocket;

public class MainServer {
	
	public static void main(String[]args) {
		ServerSocket socket=getPortFree(6000);
		System.out.println(socket.getLocalPort());
		if(socket!=null) {
			Server server=new Server(socket);
			Thread threadServer=new Thread(server);
			threadServer.start();	
		}
	}
	
	public static ServerSocket getPortFree(int port) {		
		ServerSocket socket=null;
		boolean portOk=true;
		for(int p=port ; p <= 65535&&portOk; p++){
	         try {
	        	 socket= new ServerSocket(p);	 
	        	 portOk=false;
	         } catch (IOException e) {
	            System.err.println("Le port " + p + " est déjà utilisé ! ");
	         }
	      }		
		return socket;
	}

}

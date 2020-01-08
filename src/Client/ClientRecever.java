package Client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientRecever implements Runnable {
	
	
	private Socket socketRecever;
	private ObjectInputStream input; // stream data in
	private String message="";
	
	public ClientRecever(Socket socket) {
		socketRecever=socket;
		try {
			setupStreams();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setupStreams() throws IOException{
	    input = new ObjectInputStream(socketRecever.getInputStream()); // set up pathway to allow data in
	}
	
	

	@Override
	public void run() {
		while(socketRecever.isConnected()) {
			try {
				message=(String)input.readObject();
				System.out.println(message);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	public void close() {
		try {
			socketRecever.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientRecever {
	
	
	private Socket socketRecever;
	private ObjectOutputStream output; // stream data out
	private ObjectInputStream input; // stream data in
	
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
	
	// this code while run during chat conversions
	private void whileChatting() throws IOException{
		    String message = " You are now connected ";		  
		    do{
		        // have conversion while the client does not type end
		        try{
		            message = (String) input.readObject(); // stores input object message in a string variable		          
		        }catch(ClassNotFoundException classnotfoundException){

		          //  showMessage("\n i dont not what the user has sent");
		        }
		    }while(!message.equals("CLIENT - END"));// if user types end program stops

	}

}

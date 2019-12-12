import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client{
	
	   private Socket connexion;		 
	   private User user;
	   
	   
	   public Client(Socket s) {
		  connexion=s;		  		  
	   }
	   
	   
		public void run() {
			while(connexion.isConnected()) {				 
				try {
					 setupStreams();
					whileChatting();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	   
	 
	
		
		// this code while run during chat conversions
		private void whileChatting() throws IOException{

		    String message = " You are now connected ";
		   // sendMessage(message);
		    //allowTyping(true); // allow user to type when connection
		    do{
		        // have conversion while the client does not type end
		        try{

		            message = (String) input.readObject(); // stores input object message in a string variable
		           // showMessage("\n " +message);
		        }catch(ClassNotFoundException classnotfoundException){

		          //  showMessage("\n i dont not what the user has sent");
		        }
		    }while(!message.equals("CLIENT - END"));// if user types end program stops


		}
		
		
		
		
		private void setupStreams() throws IOException{

		    output = new ObjectOutputStream(connexion.getOutputStream()); // set up pathway to send data out
		    output.flush(); // move data away from your machine
		    input = new ObjectInputStream(connexion.getInputStream()); // set up pathway to allow data in
		    //showMessage("\n Connection streams are now setup \n");

		}
	   
		
		private void closeChat(){

		    //showMessage("\n closing connections...\n");
		    //allowTyping(true);
		    try{

		        output.close(); // close output stream
		        input.close(); // close input stream
		        connexion.close(); // close the main socket connection

		    }catch(IOException ioexception){

		        ioexception.printStackTrace();
		    }
		}
	   
	  
	

}

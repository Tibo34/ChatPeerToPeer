import java.io.*;
import java.net.*;



public class Server implements Runnable {

	private ObjectOutputStream output; // stream data out
	private ObjectInputStream input; // stream data in
	private ServerSocket server;
	private Socket connection; // socket means set up connetion between 2 computers
	private int port;
	
	//Constructor
	public Server(ServerSocket p){
		server=p;
		port=server.getLocalPort();
	}
	
	@Override
	public void run() {
		boolean serverOpen=true;
		while(serverOpen) {
			 //try{
                waitForConnection(); // wait for a connection between 2 computers 
                // setupStreams();  // set up a stream connection between 2 computers to communicate
              //  whileChatting();  // send message to each other
                // connect with someone and have a conversation
          /*  }catch(EOFException eofException){
                //showMessage("\n Server ended Connection");
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
                closeChat();
            }		*/	
		}			
	}
	
	

	


	//Wait for a connection then display connection information
	private void waitForConnection(){	 
	    try {
	        connection = server.accept();
	        Client client=new Client(connection);           
	    } catch (IOException ioexception) {
	        ioexception.printStackTrace();
	    }	 
	}
	 // stream function to send and recive data
	private void setupStreams() throws IOException{
	    output = new ObjectOutputStream(connection.getOutputStream()); // set up pathway to send data out
	    output.flush(); // move data away from your machine
	    input = new ObjectInputStream(connection.getInputStream()); // set up pathway to allow data in
	    //showMessage("\n Connection streams are now setup \n");

	}

	// this code while run during chat conversions
	private void whileChatting() throws IOException{

	    String message = " You are now connected ";
	    sendMessage(message);
	    //allowTyping(true); // allow user to type when connection
	    do{
	        // have conversion while the client does not type end
	        try{

	            message = (String) input.readObject(); // stores input object message in a string variable
	            //showMessage("\n " +message);
	        }catch(ClassNotFoundException classnotfoundException){

	           // showMessage("\n i dont not what the user has sent");
	        }
	    }while(!message.equals("CLIENT - END"));// if user types end program stops



	}

	private void closeChat(){

	    //showMessage("\n closing connections...\n");
	    //allowTyping(true);
	    try{

	        output.close(); // close output stream
	        input.close(); // close input stream
	        connection.close(); // close the main socket connection

	    }catch(IOException ioexception){

	        ioexception.printStackTrace();
	    }
	}

	// send message to the client
	private void sendMessage(String message){

	    try{

	        output.writeObject("Server - "+ message);
	        output.flush(); // send all data out
	        //showMessage("\nServer - "+ message);

	    }catch(IOException ioexception){

	        //theChatWindow.append("\n ERROR: Message cant send");
	    }
	}

	
}






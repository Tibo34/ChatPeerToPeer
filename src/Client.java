import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Client extends JFrame {
	
	   private Socket connexion;
	   private JTextField userInput; // 
	   private JTextArea theChatWindow; //
	   private String host;
	   private int port;
	   private ObjectOutputStream output; // stream data out
	   private ObjectInputStream input; // stream data in
	   
	   
	   public Client(String h,int p) {
		   super("My Chat Service");
		   host=h;
		   port=p;
		    userInput = new JTextField();
		    userInput.setEditable(false); // set this false so you dont send messages when noone is available to chat
		    // action event listener to check when the user hits enter for example
		    userInput.addActionListener(new ActionListener(){

		        public void actionPerformed(ActionEvent event){
		            sendMessage(event.getActionCommand()); // string entered in the textfield
		            userInput.setText(""); // reset text area to blank again
		        }
		    }
		    );
		    theChatWindow = new JTextArea();
		    add(new JScrollPane(theChatWindow));
		    // create the chat window
		    add(userInput, BorderLayout.SOUTH);
		   
		    setSize(300,150);
		    setVisible(true);
		   
		  
	   }
	   
	   
	   public void connect() {
		   try {
		         connexion = new Socket(host, port);
		         RunClient();
		      } catch (UnknownHostException e) {
		         e.printStackTrace();
		      } catch (IOException e) {
		         e.printStackTrace();
		      }
	   }
	// run the server after gui created
		public void RunClient(){

		    try{
		      
		        while(true){

		            try{
		               // waitForConnection(); // wait for a connection between 2 computers 
		                setupStreams();  // set up a stream connection between 2 computers to communicate
		                whileChatting();  // send message to each other
		                // connect with someone and have a conversation
		            }catch(EOFException eofException){

		                showMessage("\n Server ended Connection");
		            }finally{

		                closeChat();
		            }
		        }
		    }catch(IOException ioException){
		        ioException.printStackTrace();
		    }
		}
		
		// this code while run during chat conversions
		private void whileChatting() throws IOException{

		    String message = " You are now connected ";
		    sendMessage(message);
		    allowTyping(true); // allow user to type when connection
		    do{
		        // have conversion while the client does not type end
		        try{

		            message = (String) input.readObject(); // stores input object message in a string variable
		            showMessage("\n " +message);
		        }catch(ClassNotFoundException classnotfoundException){

		            showMessage("\n i dont not what the user has sent");
		        }
		    }while(!message.equals("CLIENT - END"));// if user types end program stops



		}
		
		
		// let the user type messages in their chat window

		private void allowTyping(final boolean trueOrFalse){

		    SwingUtilities.invokeLater(

		            new Runnable(){

		                public void run(){

		                    userInput.setEditable(trueOrFalse);


		                }
		            }

		       );
		   }
		// send message to the client
		private void sendMessage(String message){

		    try{

		        output.writeObject("Client - "+ message);
		        output.flush(); // send all data out
		        showMessage("\nServer - "+ message);

		    }catch(IOException ioexception){

		        theChatWindow.append("\n ERROR: Message cant send");
		    }


		}
		
		// update the chat window (GUI)
		private void showMessage(final String text){
		    SwingUtilities.invokeLater(
		            new Runnable(){
		                public void run(){
		                    theChatWindow.append(text);

		                }
		            }
		            );
		}
		
		private void setupStreams() throws IOException{

		    output = new ObjectOutputStream(connexion.getOutputStream()); // set up pathway to send data out
		    output.flush(); // move data away from your machine
		    input = new ObjectInputStream(connexion.getInputStream()); // set up pathway to allow data in
		    showMessage("\n Connection streams are now setup \n");

		}
	   
		
		private void closeChat(){

		    showMessage("\n closing connections...\n");
		    allowTyping(true);
		    try{

		        output.close(); // close output stream
		        input.close(); // close input stream
		        connexion.close(); // close the main socket connection

		    }catch(IOException ioexception){

		        ioexception.printStackTrace();
		    }
		}
	   
	   public static void main(String[] args) {
		Client client =new Client("127.0.0.1",6789);	
		client.connect();
	   }
	

}

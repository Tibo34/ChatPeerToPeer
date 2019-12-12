import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class FrameChat extends JFrame {

	
	   private JTextField userInput; // 
	   private JTextArea theChatWindow; //
	   
	   public FrameChat() {
		   super("My Chat Service");
		   userInput = new JTextField();
		    userInput.setEditable(false); 
		    userInput.addActionListener(new ActionListener(){

		        public void actionPerformed(ActionEvent event){
		            sendMessage(event.getActionCommand()); // string entered in the textfield
		            userInput.setText(""); // reset text area to blank again


		        }
		    });
		    
		    
		    theChatWindow = new JTextArea();
		    add(new JScrollPane(theChatWindow));
		    // create the chat window
		    add(userInput, BorderLayout.SOUTH);
		   
		    setSize(300,150);
		    setVisible(true);
		   	theChatWindow = new JTextArea();
		    add(new JScrollPane(theChatWindow));
		    // create the chat window
		    add(userInput, BorderLayout.SOUTH);
		   
		    setSize(300,150);
		    setVisible(true);
		}
	   
	   public void sendMessage(String str) {
		   
	   }
	   
	// update the chat window (GUI)
		public void showMessage(final String text){
		    SwingUtilities.invokeLater(
		            new Runnable(){
		                public void run(){
		                    theChatWindow.append(text);
		                }
		      });
		}

		// let the user type messages in their chat window

		public void allowTyping(final boolean trueOrFalse){
		    SwingUtilities.invokeLater(
		            new Runnable(){
		                public void run(){
		                    userInput.setEditable(trueOrFalse);
		                }
		            }
		       );
		}
	   
	   public static void main(String[]args) {
		   FrameChat chat=new FrameChat();
	   }
}

package frames;
import java.awt.BorderLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Client.Client;
import Controller.ControllerChat;
import Serveur.Server;

public class FrameChat extends JFrame implements WindowListener {

	
	   private JTextField userInput; // 
	   private JTextArea theChatWindow; //
	   private JMenuBar menu;
	  
	  
	   
	   public FrameChat() {
		   super("My Chat Service");
		   menu=new MenuFrame();
		   	userInput = new JTextField();
		    userInput.setEditable(false); 
		    userInput.addActionListener(new ActionListener(){
		        public void actionPerformed(ActionEvent event){
		            sendMessage(event.getActionCommand()); // string entered in the textfield
		            userInput.setText(""); // reset text area to blank again
		         }
		    });
		   
		    setJMenuBar(menu);
		    
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
	   
	   public static FrameChat initFrame() {
		   FrameChat frame=new FrameChat();
		   return frame;
	   }
	   
	   public void sendMessage(String str) {
		   ControllerChat.getController().getClient().sendMessage(str);
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

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		Server serv=Server.createServer();
		serv.stop();
		setVisible(false);
		dispose();
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}

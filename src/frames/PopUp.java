package frames;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Controller.ControllerChat;

public class PopUp extends JOptionPane {
	
	private JFrame frame;
	

	private static PopUp instance;
	
	private PopUp() {
		frame=ControllerChat.getController().getFrame();
	}
	
	public static JFrame getFrame() {
		PopUp pop=getPopUp();
		return pop.frame;
	}
	
	public static PopUp getPopUp() {
		if(instance==null) {
			instance=new PopUp();
		}
		return instance;
	}
	
	public static void displayPop(String mess) {	
		showMessageDialog(getFrame(),mess,"Selection",JOptionPane.DEFAULT_OPTION);
	}
	
	public static String popUpInput(String mess) {
		String str=showInputDialog(getFrame(), mess);
		return str;
	}

}

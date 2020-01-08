package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Client.User;
import Controller.ControllerChat;
import Serveur.Server;


public class MenuFrame extends JMenuBar {
	
	
	private JMenu file;
	private JMenu user;
	private JMenu server;
	private JMenuItem editUser;
	private JMenuItem editServer;
	private JMenuItem infoServer;
	private JMenuItem quit;
	
	
	public MenuFrame() {		
		file= new JMenu("Fichier");
		user=new JMenu("User");
		server=new JMenu("Serveur");
		editUser=new JMenuItem("Créer profils");
		editServer=new JMenuItem("Modifier Serveur");
		infoServer=new JMenuItem("Info serveur");
		infoServer.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				ServerSocket serve=Server.createServer().getServer();
				String ip=serve.getLocalSocketAddress().toString();
				int port=serve.getLocalPort();
				PopUp.displayPop( " Serveur \n ip: "+ip+"\n port: "+port);
				
			}
		});
		
		editUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String userName=PopUp.popUpInput("Saisir un nom : ");
				User user=new User(userName);
				ControllerChat.getController().getClient().setUser(user);				
			}
		});
		quit=new JMenuItem("Quitter");
		
		add(file);
		file.add(quit);
		add(user);		
		user.add(editUser);
		add(server);
		server.add(editServer);
		server.add(infoServer);
	}

}

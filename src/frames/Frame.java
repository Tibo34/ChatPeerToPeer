package frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import Client.Client;
import Client.Message;
import Client.User;
import Controller.ControllerChat;
import Serveur.AdressNetWork;
import Serveur.Server;
import javax.swing.JButton;

public class Frame extends JFrame implements WindowListener {

	private JPanel contentPane;
	private JTextField inputUser;
	private DefaultListModel<User> userListModel;
	private ArrayList<User> users;
	private LabelListUser listusers;
	private ArrayList<AdressNetWork> ipsConnect;
	private DefaultComboBoxModel<AdressNetWork> boxModelAdress;
	private int portLocal;
	private JList<User> usersJList;
 	private JTextPane infoReseau; 
 	private JComboBox<AdressNetWork> boxAdress;
 	private JList<Message> userMessageJList;
 	private DefaultListModel<Message> messageListModel;
 	private Server serve;
 	private User userLocal;
 	private JSplitPane splitCenter;
 	private JSplitPane splitNorth;
 	private JPanel panelSouth;

	/**
	 * Create the frame.
	 * @param user 
	 */
	public Frame(Server server,ArrayList<AdressNetWork> ips, User user) {
 		serve=server;
		ipsConnect=ips;
		portLocal=server.getServer().getLocalPort();
		users=new ArrayList<User>();		
		listusers=new LabelListUser();
		userListModel=new DefaultListModel<User>();
		if(user!=null) {
			userLocal=user;		
			addUser(userLocal);	
		}			
		initFrameComponent();		
	}
	
	
	 public void addUser(User u) {	
		 if(!users.contains(u)) {
		   users.add(u);
		   userListModel.addElement(u);		
		   if(usersJList!=null) {
			   usersJList.setVisibleRowCount(20);
			
		 }
	   }
	 }
	 
	// update the chat window (GUI)
	public void showMessage(final String text){
	    SwingUtilities.invokeLater(
	            new Runnable(){
	                public void run(){
	                    //theChatWindow.append(text);
	                }
	      });
	}

	private void initFrameComponent() {
		boxModelAdress=new DefaultComboBoxModel<AdressNetWork>();
		for (AdressNetWork adressNetWork : ipsConnect) {
			boxModelAdress.addElement(adressNetWork);
		}
	   	boxAdress=new JComboBox<AdressNetWork>(boxModelAdress);		
	   	boxAdress.setEditable(true);
	   
	   usersJList=new JList<User>(userListModel);
	   
	   usersJList.setCellRenderer(listusers);	
	   boxAdress.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox box=(JComboBox) e.getSource();		
			AdressNetWork addr=(AdressNetWork) box.getSelectedItem();
			serve.connection(addr);
			editable();
			}

		
	   });
			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 569, 377);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Fichier");
		menuBar.add(mnNewMenu);
		
		JMenuItem quit = new JMenuItem("Quitter");
		quit.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();				
			}
		});
		mnNewMenu.add(quit);
		
		JMenu mnNewMenu_1 = new JMenu("User");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem menuItemUser = new JMenuItem("Créer User");
		menuItemUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = createUser();
				ControllerChat.getController().setUser(user);	
			}
			
		});
		mnNewMenu_1.add(menuItemUser);
		
		
		
	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panelSouth = new JPanel();
		contentPane.add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new BorderLayout(0, 0));
		
		inputUser = new JTextField();
		inputUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mess=inputUser.getText();
				Message messUser=new Message(userLocal, mess);
				addMessage(messUser);
				inputUser.setText("");
				ControllerChat.getController().sendMessAll(mess);
			}
		});
		inputUser.setEditable(false);
		panelSouth.add(inputUser);
		inputUser.setColumns(10);
		
		splitNorth = new JSplitPane();
		contentPane.add(splitNorth, BorderLayout.NORTH);
		
		infoReseau = new JTextPane();
		infoReseau.setText(" port :"+portLocal);
		
		splitNorth.setLeftComponent(infoReseau);
		
			
		splitNorth.setRightComponent(boxAdress);
		
		splitCenter = new JSplitPane();
		contentPane.add(splitCenter, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		splitCenter.setRightComponent(scrollPane);
		messageListModel=new DefaultListModel<Message>();
		userMessageJList = new JList<Message>(messageListModel);
		scrollPane.setViewportView(userMessageJList);
		
		
		usersJList.setVisibleRowCount(20);
		
		splitCenter.setLeftComponent(usersJList);
		setVisible(true);
	}
	
	private User createUser() {
		String userName=PopUp.popUpInput("Saisir un nom : ");
		User user=new User(userName);	
		if(userLocal!=null) {
			removeUser(userLocal);
		}
		userLocal=user;		
		addUser(userLocal);
		serve.setUser(user);
		return user;
	}
	
	public void setIpsLocal(ArrayList<AdressNetWork> ips) {
		ipsConnect=ips;		
	}	
	
	
	public void addMessage(Message mess) {
		messageListModel.addElement(mess);
	}
	
	
	public void removeUser(User u) {
		int index=userListModel.indexOf(u);		
		userListModel.remove(index);
		users.remove(u);
	}

	
	public void editable() {
		inputUser.setEditable(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent e) {
		try {
			serve.getServer().close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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

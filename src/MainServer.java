import java.util.ArrayList;

import ScannerLan.ScannerLan;
import Serveur.Server;

public class MainServer {
	
	public static void main(String[]args) {
	
		
				
		//ArrayList<String>ipConnect=new ScannerLan().enumLocalNetwork();
		
		//displayIps(ipConnect);
		Server serve=Server.createServer();
		
	}
	
	
	public static void displayIps(ArrayList<String> ips) {
		for (String ip : ips) {
			System.out.println("ip connecté :"+ip);
		}
	}

	

}

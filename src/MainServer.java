import ScannerLan.ScannerLan;
import Serveur.Server;

public class MainServer {
	
	public static void main(String[]args) {
	
		String ip=ScannerLan.getIP();
		String ipboard=ScannerLan.getBroadcast();	
		String sub=ScannerLan.getSubnetMask();
	
	
		System.out.println(" ip : "+ip+" ipbroad : "+ipboard+" sub : "+sub);
		ScannerLan scanner=new ScannerLan();
		scanner.enumLocalNetwork(ip);
		//Server serve=Server.createServer();
		
	}
	

	

}

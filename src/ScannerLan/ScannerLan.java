package ScannerLan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Serveur.AdressNetWork;



public class ScannerLan {
	
	
	 public static String NOT_SET = "NOT_SET";
	 private String ipLocal;
	 private int localPort=6000;
	 private ArrayList<AdressNetWork> ipAddrs;
	 
	 
	 
	 public ScannerLan() {
		 ipLocal=getIP();
		 ipAddrs=new ArrayList<AdressNetWork>();
	 }

	  
	   
	   public ArrayList<AdressNetWork> scanLocal() {
		   ipAddrs=new ArrayList<AdressNetWork>();
		   ipAddrs=enumLocalNetwork();		  
		   return ipAddrs;
	   }
	    

	    public String getIP(){
	        String ip = NOT_SET; 
	        try {
	            ProcessBuilder builder = new ProcessBuilder(
	                    "cmd.exe", "/c", "netsh interface ip show addresses \"Wi-Fi\"");
	            builder.redirectErrorStream(true);
	            Process p = builder.start();
	            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line;
	            while ((line = r.readLine())!=null&&ip.equals("NOT_SET")) {	            
	                if (line.contains("Adresse IP")){	                	
	                    ip = line.split("\\s+")[3];	                   
	                }
	            }
	        } catch (IOException ex) {
	            Logger.getLogger(ScannerLan.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return ip;
	    }

	 
	  
	    /**
	     * Parcourir l'ensemble du réseau pour détecter les postes de travail ou les
	     * serveurs
	     *
	     * @param iproot plage de départ du scan
	     */
	    public ArrayList<AdressNetWork> enumLocalNetwork() {
	        //Test base Ip valide
	    	System.out.println("Début scan réseau!");
	        String[] nip = ipLocal.split("\\.");
	        ArrayList<AdressNetWork> ipConnected=new ArrayList<AdressNetWork>();
	       
	        if (nip.length != 4) {
	            System.out.println("Base Ip incorrecte !!! exemple 192.168.2.0");
	            return ipConnected;
	        }	 
	       
	       
	        byte[] ip = {(byte) Integer.parseInt(nip[0]), (byte) Integer.parseInt(nip[1]),
	            (byte) Integer.parseInt(nip[2]), (byte) 0};
	        byte local=ip[3];
	 
	        //Boucle sur l'ensemble du masque réseau arret à 100 mettre 255 pour tout le reseau
	        for (int i = 0; i < 25; i++) {
	            ip[3] = (byte) i;
	            if((byte)i!=local) {			            
			       try {
		                InetAddress addr = InetAddress.getByAddress(ip);		                
		                if (isAlive(addr.getHostAddress())) {
		                	ipConnected.add(new AdressNetWork(addr));
		                } 
		            } catch (UnknownHostException e) {
		                System.out.println(e.getMessage());
		            }
	            }
	        }	       
	        System.out.println(ipConnected.size()+ " personnes connecté!");
	        return ipConnected;
	    }
	    

	    /**
	     * Ping sur une adresse ip
	     *
	     * @param Ipv4Adr ip adresse ip du poste
	     * @return boolean
	     */
	    private boolean isAlive(String Ipv4Adr) {
	        Process p1;
	        boolean reachable = false;
	        try {
	            p1 = java.lang.Runtime.getRuntime().exec("ping -w 2 -n 2 " + Ipv4Adr);
	            int returnVal = p1.waitFor();
	            reachable = (returnVal == 0);
	        } catch (IOException | InterruptedException ex) {
	            Logger.getLogger(ScannerLan.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return reachable;
	    }
	 
	    /**
	     * Exemple de plage de port à scanner
	     *
	     * @param ip adresse ip du poste
	     * @return String
	     */
	    private String scanPort(String ip) {
	        String openPort = "";
	        int time=1;
	 
	        //Port de communication FTP
	        if (portIsOpen(ip, 21, time)) {
	            openPort += " FTP  21";
	        }
	        //Port standard pour le web, par ex Apache
	        if (portIsOpen(ip, 80, time)) {
	            openPort += " Http : 80";
	        }
	        //Port d'une imprimante
	        if (portIsOpen(ip, 515, time)) {
	            openPort += " Printer : 515";
	        }
	        //Port du serveur MySql
	        if (portIsOpen(ip, 3306, time)) {
	            openPort += " MySql : 3306";
	        }
	 
	        return openPort.trim();
	    }
	 
	    /**
	     * Tester l'état du port sur un poste
	     *
	     * @param ip adresse ip du poste
	     * @param port Numero du port
	     * @param timeout délai en ms
	     * @return port ouvert ou non
	     */
	    private boolean portIsOpen(String ip, int port, int timeout) {
	        try {
	            Socket socket = new Socket();
	            socket.connect(new InetSocketAddress(ip, port), timeout);
	            socket.close();
	            return true;
	        } catch (Exception ex) {
	            return false;
	        }
	    }
	 
	  

		public String getIpLocal() {
			return ipLocal;
		}



		public void setIpLocal(String ipLocal) {
			this.ipLocal = ipLocal;
		}



		public int getLocalPort() {
			return localPort;
		}



		public void setLocalPort(int localPort) {
			this.localPort = localPort;
		}



		public ArrayList<AdressNetWork> getIpAddrs() {
			return ipAddrs;
		}



		public void setIpAddrs(ArrayList<AdressNetWork> ipAddrs) {
			this.ipAddrs = ipAddrs;
		}
	    
	   
}

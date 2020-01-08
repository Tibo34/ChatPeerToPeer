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



public class ScannerLan {
	
	
	 public static String NOT_SET = "NOT_SET";
	 private String ipLocal;
	 private int localPort=6000;
	 
	 public ScannerLan() {
		 ipLocal=getIP();
	 }

	   public static boolean isEnabled(){
	        try {
	            String state;
	            ProcessBuilder builder = new ProcessBuilder(
	                    "cmd.exe", "/c", "netsh interface show interface \"Wi-Fi\"");
	            builder.redirectErrorStream(true);
	            Process p = builder.start();
	            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line;
	            while ((line = r.readLine())!=null) {
	                //line = r.readLine();
	                if (line.contains("Administrative state")){
	                    state = line.split("\\s+")[3];
	                    //System.out.println(state);
	                    state = state.toLowerCase();
	                    if(state.equals("enabled")){
	                        return true;
	                    }else{
	                        return false;
	                    }
	                }
	            }
	        } catch (IOException ex) {
	            Logger.getLogger(ScannerLan.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return false;
	    }
	   
	   public ArrayList<String> scanLocal() {
		   ArrayList<String> ips=new ArrayList<String>();
		   if(isConnected()) {
			   ips=enumLocalNetwork();
		   }
		   return ips;
	   }

	   
		
		public static boolean isConnected(){
	        try {
	            String state;
	            ProcessBuilder builder = new ProcessBuilder(
	                    "cmd.exe", "/c", "netsh interface show interface \"Wi-Fi\"");
	            builder.redirectErrorStream(true);
	            Process p = builder.start();
	            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line;
	            while ((line = r.readLine())!=null) {
	                if (line.contains("connexion")){
	                    state = line.split("\\s+")[4];
	                    state = state.toLowerCase().trim();
	                    if(state.contains("connect")){
	                        return true;
	                    }else{
	                        return false;
	                    }
	                }
	            }
	        } catch (IOException ex) {
	            Logger.getLogger(ScannerLan.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return false;
	    }

	    public static String getConnectedSSID(){
	        String ssid = NOT_SET;
	        try {
	            ProcessBuilder builder = new ProcessBuilder(
	                    "cmd.exe", "/c", "netsh wlan show interfaces");
	            builder.redirectErrorStream(true);
	            Process p = builder.start();
	            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line;
	            while ((line = r.readLine())!=null) {
	                //line = r.readLine();
	                if (line.contains("SSID")){
	                    ssid = line.split("\\s+")[3];
//	                    System.out.println(ssid);
	                    return ssid;
	                }
	            }
	        } catch (IOException ex) {
	            Logger.getLogger(ScannerLan.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return ssid;
	    }

	    public static String[] getListOfSSIDs(){
	        String [] ssid_List;
	        String ssid;
	        ArrayList<String> arr = new ArrayList<>();
	        try {
	            ProcessBuilder builder = new ProcessBuilder(
	                    "cmd.exe", "/c", "netsh wlan show networks");
	            builder.redirectErrorStream(true);
	            Process p = builder.start();
	            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line;
	            while ((line = r.readLine())!=null) {
	                //line = r.readLine();
	                if (line.contains("SSID")){
	                    ssid = line.split("\\s+")[3];
	                    //System.out.println(ssid);
	                    arr.add(ssid);
	                }
	            }
	        } catch (IOException ex) {
	            Logger.getLogger(ScannerLan.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        ssid_List = new String[arr.size()];
	        arr.toArray(ssid_List);
	        return ssid_List;
	    }

	    public static String getIP(){
	        String ip = NOT_SET; 
	        try {
	            ProcessBuilder builder = new ProcessBuilder(
	                    "cmd.exe", "/c", "netsh interface ip show addresses \"Wi-Fi\"");
	            builder.redirectErrorStream(true);
	            Process p = builder.start();
	            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line;
	            while ((line = r.readLine())!=null) {	            
	                if (line.contains("Adresse IP")){	                	
	                    ip = line.split("\\s+")[3];
	                    return ip;
	                }
	            }
	        } catch (IOException ex) {
	            Logger.getLogger(ScannerLan.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return ip;
	    }

	    public static String getSubnetMask(){
	        String sb = NOT_SET; 
	        try {
	            ProcessBuilder builder = new ProcessBuilder(
	                    "cmd.exe", "/c", "netsh interface ip show addresses \"Wi-Fi\"");
	            builder.redirectErrorStream(true);
	            Process p = builder.start();
	            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line;
	            while ((line = r.readLine())!=null) {
	                if (line.contains("Pr‚fixe")){
	                    sb = line.split("\\s+")[6];
	                    sb = sb.substring(0, sb.length() - 1);
	                    return sb;
	                }
	            }
	        } catch (IOException ex) {
	            Logger.getLogger(ScannerLan.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return sb;
	    }

	    public static String getBroadcast(){
	        String subnetMask = getSubnetMask();
	        String ip = getIP();

	        String []arrSubnetMask = subnetMask.split("\\.");
	        String []arrIP = ip.split("\\.");
	        int []networkAddress = new int[4];
	        int [] broadcastAddress = new int[4];

	        String broadcast = "";

	        for(int i=0; i< 4; i++){
	            networkAddress[i] =  Integer.parseInt(arrIP[i]) & Integer.parseInt(arrSubnetMask[i]);
	            
	        }

	        for(int i=0; i< 4; i++){
	            //broadcastAddress[i] =  networkAddress[i] | (~Integer.parseInt(arrSubnetMask[i]) & 0xff);
	            //System.out.println(broadcastAddress[i]);
	            broadcast = broadcast + "." + (networkAddress[i] | (~Integer.parseInt(arrSubnetMask[i]) & 0xff));
	        }

//	        System.out.println(broadcast.substring(1));

	        //mask AND ip you get network address
	        //Invert Mask OR Network Address you get broadcast

	        return broadcast.substring(1);
	    }
	    
	    /**
	     * Parcourir l'ensemble du réseau pour détecter les postes de travail ou les
	     * serveurs
	     *
	     * @param iproot plage de départ du scan
	     */
	    public ArrayList<String> enumLocalNetwork() {
	        //Test base Ip valide
	        String[] nip = ipLocal.split("\\.");
	        ArrayList<String> ipConnected=new ArrayList<String>();
	       
	        if (nip.length != 4) {
	            System.out.println("Base Ip incorrecte !!! exemple 192.168.2.0");
	            return ipConnected;
	        }
	 
	        //timer
	       // int ifound = 0;
	        //Long timestart = System.currentTimeMillis();
	 
	        //Entete du tableau
	        //System.out.printf("%-16s %-30s %-10s \n", "Adresse ip", "Nom du poste", "Port ouvert");
	        byte[] ip = {(byte) Integer.parseInt(nip[0]), (byte) Integer.parseInt(nip[1]),
	            (byte) Integer.parseInt(nip[2]), (byte) 0};
	        byte local=ip[3];
	 
	        //Boucle sur l'ensemble du masque réseau
	        for (int i = 0; i < 255; i++) {
	            ip[3] = (byte) i;
	            if((byte)i!=local) {			            
			       try {
		                InetAddress addr = InetAddress.getByAddress(ip);	                
		                if (isAlive(addr.getHostAddress())&&portIsOpen(addr.getHostAddress(), localPort, 1)) {
		                	ipConnected.add(addr.getHostAddress());
		                	System.out.println(addr.getHostAddress()+" connecté!");
		                   // System.out.printf("%-16s %-30s %-10s \n", addr.getHostAddress(),
		                     //       addr.getHostName(), scanPort(addr.getHostAddress()));
		                    //ifound++;
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
	 
	    /**
	     * Exemple de scan sur la plage 192.168.2.0
	     *
	     * @param args
	     * @throws IOException
	     * @throws InterruptedException
	     */
	    public static void main(String[] args) throws IOException, InterruptedException {
	       // LANScanner lan = new LANScanner();
	        // lan.enumLocalNetwork("192.168.2.0");
	    }
	    
	   
}

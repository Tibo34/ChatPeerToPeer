package Serveur;

import java.net.InetAddress;

public class AdressNetWork {
	
	private InetAddress adress;

	@Override
	public String toString() {
		return adress.getHostAddress()+" / "+adress.getHostName();
	}

	public AdressNetWork(InetAddress adr) {
		adress=adr;
	}

	public InetAddress getAdress() {
		return adress;
	}

	public void setAdress(InetAddress adress) {
		this.adress = adress;
	}
	
	
	

}

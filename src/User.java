
public class User {
	
	private String ip;
	private String name;
	
	
	public User(String i,String n) {
		ip=i;
		name=n;
	}


	@Override
	public String toString() {
		return  name;
	}

	
	
}

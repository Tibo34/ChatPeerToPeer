package Client;

public class Message {

	private User user;
	private String message;
	
	
	public Message(User u, String m) {
		user=u;
		message=m;
	}


	@Override
	public String toString() {
		return user + ": " + message;
	}
	
	
}

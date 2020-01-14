package frames;

import java.util.ArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import Client.User;

public class ListModelUser implements ListModel<User> {
	
	private ArrayList<User> list;
	
	public ListModelUser(ArrayList<User> l) {
		list=l;
	}
	
	public void add(User u) {
		list.add(u);
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User getElementAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

}

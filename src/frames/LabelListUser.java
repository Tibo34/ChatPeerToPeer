package frames;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import Client.User;

public class LabelListUser extends JLabel implements ListCellRenderer<User> {

	

	@Override
	public Component getListCellRendererComponent(JList<? extends User> list, User value, int index, boolean isSelected,
			boolean cellHasFocus) {
		
		 String s = value.toString();
	      if (isSelected) {
	         setBackground(list.getSelectionBackground());
	         setForeground(Color.red);	       
	        
	      }else{
	         setBackground(list.getBackground());
	         setForeground(list.getForeground());		        
	      }
	      setText(s);
	      setEnabled(list.isEnabled());
	      setFont(list.getFont());
	      setOpaque(true);
		return this;
	}

}

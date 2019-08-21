package Bl;

import java.awt.Component;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/** make validation for the phone number 
 * and use to save and send contact data*/
public class Contact implements Serializable, Comparable<Contact>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String phoneNumber;
	
	public Contact(String name, String phoneNumber) {
		setName(name);
		try {
			setPhoneNumber(phoneNumber);
		} catch (Exception e) {
			Component frame = new JFrame();
			JOptionPane.showMessageDialog(frame,
				    e.getMessage(),
				    "Inane warning",
				    JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		System.out.println("Contact.Contact()");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}

	/*phone number len is 5 and no other symbol except numbers
	 * throw Exception other*/
	public void setPhoneNumber(String phoneNumber2) throws Exception {
		Pattern pattern = Pattern.compile("\\d{5}");
		Matcher matcher = pattern.matcher(phoneNumber2);
		if (matcher.matches())
			phoneNumber = phoneNumber2;
		else 
			throw new Exception("Phone number dosent match");
	}

	@Override
	public String toString() {
		return "name=" + name + ", phoneNumber=" + phoneNumber;
	}
	
	public int compareTo(Contact other) {
		return this.name.compareTo(other.getName());
	}
	
}

package Bl;

import java.util.ArrayList;
import java.util.Queue;

public interface PhoneBookable {
	ArrayList<Contact> getAllContacts();
	void addContact(Contact contact);
	boolean updateContact(Contact contact);
	void removeContact(Contact contact);
	ArrayList<Contact> findContactByName(String name);
	
	//this function can be changed for pagination
	public Queue<Contact> getSortedContacts();
}

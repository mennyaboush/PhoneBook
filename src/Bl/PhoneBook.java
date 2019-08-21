package Bl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import storage.PhoneBookStorageManager;


public class PhoneBook implements PhoneBookable, Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	private ArrayList<PhoneBookModelEventListener> listeners;
	private Map contacts = Collections.synchronizedMap(new HashMap<String, ArrayList<Contact>>());
//	private Map contacts = new HashMap<String, ArrayList<Contact>>();
	// PriorityQ with init comparator.
	private Queue sortedContacts = new PriorityBlockingQueue<Contact>();

	
	public Map getContacts() {
		return contacts;
	}

	public void setContacts(Map contacts) {
		this.contacts = contacts;
	}

	public void setSortedContacts(Queue sortedContacts) {
		this.sortedContacts = sortedContacts;
	}

	@Override
	public ArrayList<Contact> getAllContacts() {
		return new ArrayList<Contact>(contacts.values());
	}

	@Override
	public void addContact(Contact contact) {
		// 1. add contact to map
		// 1.1 check if there are the same Key
		if (contacts.containsKey(contact.getName())) {
			// 1.1.1 if there are add the contact to the array
			((ArrayList<Contact>) (contacts.get(contact.getName()))).add(contact);
		} else {
			// 1.1.2 is there are not add new key and value
			ArrayList<Contact> a = new ArrayList<Contact>();
			a.add(contact);
			contacts.put(contact.getName(), a);
		}
		// 2. add contact to list
		sortedContacts.add(contact);
	}

	@Override
	public boolean updateContact(Contact contact) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeContact(Contact contact) {
		ArrayList<Contact> a = (ArrayList<Contact>) contacts.get(contact.getName());
		if (!a.isEmpty())
			for (int i = 0; i < a.size(); i++) {
				Contact c = a.get(i);
				if (c.equals(contact)) {
					a.remove(c);
					if (sortedContacts.contains(contact))
						sortedContacts.remove(contact);
				}
			}
		}

	@Override
	public ArrayList<Contact>findContactByName(String name) {
		return (ArrayList<Contact>) contacts.get(name);
	}

	public Queue<Contact> getSortedContacts() {
		return sortedContacts;
	}

	public void save() {
		PhoneBookStorageManager.getInstance().saveBook(this);
	}
	
	public void load() {
		PhoneBook pb = PhoneBookStorageManager.getInstance().loadBook();
		setContacts(pb.getContacts());
		setSortedContacts(pb.sortedContacts);
	}
	
	public Contact stringToContact(String contactS) {
		String name = contactS.substring(contactS.indexOf("=") + 1, contactS.indexOf(","));
		String phoneNumber = contactS.substring(contactS.indexOf("=") + 1);
		phoneNumber = phoneNumber.substring(phoneNumber.indexOf("=") + 1);
		return new Contact(name, phoneNumber);
	}

	/*if the name have many phone numbers the function update the first*/
	public void updateContact(String name, String phoneNumber) {
		if(contacts.containsKey(name)) {
			Contact c = ((ArrayList<Contact>)contacts.get(name)).get(0);
			sortedContacts.remove(c);
			c.setName(name);
			try {
				c.setPhoneNumber(phoneNumber);
			} catch (Exception e) {
				e.printStackTrace();
			}
			sortedContacts.add(c);
		}
	}
}

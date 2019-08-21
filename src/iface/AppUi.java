package iface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import Bl.Contact;
import Bl.PhoneBook;
import storage.PhoneBookStorageManager;

import java.awt.List;
import java.awt.TextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Queue;
import java.awt.event.ActionEvent;
import java.awt.Choice;

public class AppUi {

	private JFrame frame;
	private PhoneBook book = new PhoneBook();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppUi window = new AppUi();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppUi() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 860, 523);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JList list = new JList();
		list.setBounds(114, 360, 159, -260);
		frame.getContentPane().add(list);

		List ContactsList = new List();
		/* print the selected contact detail on the text fields */

		ContactsList.setBounds(40, 88, 349, 315);
		frame.getContentPane().add(ContactsList);

		TextField nameField = new TextField();
		nameField.setText("Name");
		nameField.setBounds(40, 27, 128, 35);
		frame.getContentPane().add(nameField);

		TextField phoneNumberField = new TextField();
		phoneNumberField.setText("Phone number");
		phoneNumberField.setBounds(200, 27, 189, 35);
		frame.getContentPane().add(phoneNumberField);
		ContactsList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 1. extract the contact value from a string
				String selectedContact = ContactsList.getSelectedItem();
				String name = selectedContact.substring(selectedContact.indexOf("=") + 1, selectedContact.indexOf(","));
				String phoneNumber = selectedContact.substring(selectedContact.indexOf("=") + 1);
				phoneNumber = phoneNumber.substring(phoneNumber.indexOf("=") + 1);
				// 2. print the current value on the text fields
				nameField.setText(name);
				phoneNumberField.setText(phoneNumber);
			}
		});
		JButton btnAddContact = new JButton("Add contact");
		btnAddContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contact contact = null;

				System.out.println("‪AppUi.initialize().new ActionListener() {...}‬.actionPerformed()");
				contact = new Contact(nameField.getText(), phoneNumberField.getText());

				book.addContact(contact);
				System.err.println("after fire event");
				showAllContacts();
			}

			private void showAllContacts() {
				Queue<Contact> contacts = book.getSortedContacts();
				ContactsList.removeAll();
				for (Contact contact : contacts) {
					ContactsList.add(contact.toString());
				}
			}
		});
		btnAddContact.setBounds(507, 27, 240, 41);
		frame.getContentPane().add(btnAddContact);

		JButton btnRemoveContact = new JButton("Remove contact");
		/** Remove contact by name and phone */
		btnRemoveContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				book.removeContact(new Contact(nameField.getText(), phoneNumberField.getText()));
				showAllContacts();
			}

			private void showAllContacts() {
				Queue<Contact> contacts = book.getSortedContacts();
				ContactsList.removeAll();
				for (Contact contact : contacts) {
					ContactsList.add(contact.toString());
				}
			}
		});
		btnRemoveContact.setBounds(507, 88, 240, 41);
		frame.getContentPane().add(btnRemoveContact);

		JButton btnCerc = new JButton("search phone by name");
		btnCerc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Contact> cArray = book.findContactByName(nameField.getText());
				showArray(cArray);
			}

			private void showArray(ArrayList<Contact> cArray) {
				ContactsList.removeAll();
				for (Contact contact : cArray) {
					ContactsList.add(contact.toString());
				}
			}
		});
		btnCerc.setBounds(439, 348, 352, 55);
		frame.getContentPane().add(btnCerc);

		JButton btnSaveBook = new JButton("Save book");
		btnSaveBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				book.save();
			}
		});
		btnSaveBook.setBounds(507, 212, 241, 41);
		frame.getContentPane().add(btnSaveBook);

		JButton btnNewButton = new JButton("Load book");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				book.load();
				showArray(book.getSortedContacts());
			}

			private void showArray(Queue<Contact> queue) {
				ContactsList.removeAll();
				for (Contact contact : queue) {
					ContactsList.add(contact.toString());
				}
			}
		});
		btnNewButton.setBounds(507, 274, 240, 41);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Update contact");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				book.updateContact(nameField.getText(), phoneNumberField.getText());
				showArray(book.getSortedContacts());
			}

			private void showArray(Queue<Contact> sortedContacts) {
				ContactsList.removeAll();
				for (Contact contact : sortedContacts) {
					ContactsList.add(contact.toString());
				}
			}

		});
		btnNewButton_1.setBounds(507, 145, 240, 49);
		frame.getContentPane().add(btnNewButton_1);
	}
}

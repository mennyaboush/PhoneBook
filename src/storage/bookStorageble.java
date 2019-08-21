package storage;

import Bl.PhoneBook;

public interface bookStorageble {
	public void saveBook(PhoneBook phoneBook);
	public PhoneBook loadBook();
}

package storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Semaphore;

import Bl.PhoneBook;

/*Singelton class to manage the data*/
public class PhoneBookStorageManager implements bookStorageble {
	private static PhoneBookStorageManager storageManager = null;
	private String url = "PhoneBook.txt";
	static Semaphore mutex = new Semaphore(1);
	
	
	public static PhoneBookStorageManager getInstance() {
		if (storageManager == null)
			storageManager = new PhoneBookStorageManager();
		return storageManager;

	}

	@Override
	public void saveBook(PhoneBook phoneBook) {
		
		try {
			mutex.acquire();
			FileOutputStream fileOutputStream = new FileOutputStream(url);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(phoneBook);
			objectOutputStream.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}finally {
			mutex.release();
		}
	}

	@Override
	public PhoneBook loadBook() {
		PhoneBook phoneBook = null;
		try {
			mutex.acquire();
			FileInputStream fileInputStream = new FileInputStream(url);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			phoneBook = (PhoneBook)objectInputStream.readObject();
			objectInputStream.close();
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			e.printStackTrace();
		}finally {
			mutex.release();
		}
		return phoneBook;
	}
}

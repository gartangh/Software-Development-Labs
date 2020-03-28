package serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {

	private static ArrayList<Person> db = new ArrayList<Person>();

	public static void main(String[] args) {
		fillInPhoneNumberDataBase();

		final int serverPort = 1024;
		ServerSocket listen = null;

		try {
			listen = new ServerSocket(serverPort);

			while (true) {
				Socket client = listen.accept();

				// Receive a person
				Person person = (Person) new ObjectInputStream(client.getInputStream()).readObject();

				person = lookUpPhoneNumber(person);

				// Send the reply
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
				objectOutputStream.writeObject(person);
				objectOutputStream.flush();
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (listen != null)
				try {
					listen.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private static void fillInPhoneNumberDataBase() {
		Person[] p = { new Person("Jan", "Janssens", new PhoneNumber("32", "9", "44 55 66")),
				new Person("Piet", "Pieters", new PhoneNumber("32", "50", "11 22 33")),
				new Person("Giovanni", "Totti", new PhoneNumber("49", "22", "00 99 88")),
				new Person("Jean", "Lefevre", new PhoneNumber("33", "4", "12 34 56")) };

		for (Person i : p)
			db.add(i);
	}

	private static Person lookUpPhoneNumber(Person p) {
		int index = db.indexOf(p);

		if (index >= 0)
			return db.get(index);
		else {
			p.setPhoneNumber(new PhoneNumber());
			
			return p;
		}
	}
}

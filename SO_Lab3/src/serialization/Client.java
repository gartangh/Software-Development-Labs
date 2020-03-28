package serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {
		final int serverPort = 1024;
		Socket socket = null;

		Person person = new Person("Jan", "Janssens");

		try {
			socket = new Socket(InetAddress.getLocalHost(), serverPort);

			// Send the person
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(person);
			objectOutputStream.flush();

			// Receive the reply
			System.out.println(new ObjectInputStream(socket.getInputStream()).readObject().toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

}

package greeter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {
		final int serverPort = 1024;
		Socket socket = null;

		try {
			InetAddress host = InetAddress.getLocalHost();
			socket = new Socket(host, serverPort);
			OutputStream outputStream = socket.getOutputStream();
			InputStream inputStream = socket.getInputStream();

			String name = "";

			do {
				// Read in a name
				System.out.println("Enter a name: ");
				name = new BufferedReader(new InputStreamReader(System.in)).readLine();

				// Send the name
				new PrintWriter(new OutputStreamWriter(outputStream), true).println(name); // Autoflush

				if (name.equals("stop"))
					break;

				// Receive the reply
				String reply = new BufferedReader(new InputStreamReader(inputStream)).readLine();

				// print the greeting
				System.out.println(reply);
			} while (!(name.equals("stop")));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

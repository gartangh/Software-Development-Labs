package parallelgreeter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

	public static void main(String[] args) {
		final int serverPort = 1024;
		ServerSocket listen = null;

		try {
			listen = new ServerSocket(serverPort);

			while (true) {
				Socket client = listen.accept();

				// Anonymous class
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							BufferedReader bufferedReader = new BufferedReader(
									new InputStreamReader(client.getInputStream()));
							String name;

							while (true) {
								// Receive a name
								name = bufferedReader.readLine();

								if (name.equals("stop"))
									break;

								// Send the reply
								new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true)
										.println("Hello, " + name); // Autoflush
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

}

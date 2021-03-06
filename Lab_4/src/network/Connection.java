package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import eventbroker.Event;
import eventbroker.EventBroker;

public class Connection {

	private Socket socket;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private Network network;
	
	
	private int connectionID;

	// Package local would be safer
	public Connection(Socket socket, Network network) {
		this.socket = socket;

		try {
			// Server 5, Client 3
			this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
			// Server 6, Client 4
			this.objectOutputStream.flush();
			// Server 7, Client 5
			this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.network = network;
	}

	// Package local would be safer
	public void send(Event e) {
		try {
			synchronized (this) {
				// Client 7.1
				objectOutputStream.writeObject(e);
				// Client 7.2
				objectOutputStream.flush();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// Package local would be safer
	public void receive() {
		// Server 4.2.2.1 and Server 4.2.2.2
		new Thread(new ReceiverThread()).start();
	}

	// Package local would be safer
	public void close() {
		synchronized (this) {
			try {
				objectOutputStream.writeObject(new Event("stop", "stop"));
				objectOutputStream.flush();

				objectInputStream.close();
				objectOutputStream.close();
				socket.close();
			} catch (SocketException e) {
				//e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Connection closed");
	}
	
	public void stopLocally() {
		synchronized (this) {
			try {
				objectOutputStream.writeObject(new Event("stopLocally", "stopLocally"));
				objectOutputStream.flush();
				
				objectInputStream.close();
				objectOutputStream.close();
				socket.close();
			} catch (SocketException e) {
				//e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Connection closed");
	}

	public int getConnectionID() {
		return connectionID;
	}

	public void setConnectionID(int connectionID) {
		this.connectionID = connectionID;
	}

	// Internal class
	private class ReceiverThread implements Runnable {

		
		private int n = 0;
		private int ID = n++;
		@Override
		public void run() {
			while (true) {
				synchronized (this) {
					try {
						// Server 4.2.2.2.1
						
						Event event = (Event) objectInputStream.readObject();
						
						if (event.getMessage().equals("stop")) {
							EventBroker.getEventBroker().stop();
							break;
						}
						else if (event.getMessage().equals("stopLocally")) {
							break;
						} else
							network.publishEvent(event);
					} catch (SocketException e) {
						//e.printStackTrace();

						break;
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			System.out.println("ReceiverThread terminated");
		}

	}

}

package eventbroker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import network.Network;

final public class EventBroker implements Runnable {

	protected final static EventBroker broker = new EventBroker(); // Singleton
	protected Map<String, ArrayList<EventListener>> listeners = new HashMap<>();
	LinkedList<QueueItem> queue = new LinkedList<>();

	private boolean stop = false;
	private boolean proceed;
	private boolean done;

	private EventBroker() {
		// Empty constructor
	}

	// Getters
	public static EventBroker getEventBroker() {
		return broker;
	}

	// Internal class
	private class QueueItem {

		Event event;
		EventPublisher source;

		public QueueItem(EventPublisher source, Event e) {
			this.source = source;
			this.event = e;
		}

	}

	public void addEventListener(EventListener el) {
		if (listeners.get("all") == null)
			listeners.put("all", new ArrayList<EventListener>());

		listeners.get("all").add(el);
	}

	public void addEventListener(String type, EventListener eventListener) {
		if (listeners.get(type) == null)
			listeners.put(type, new ArrayList<EventListener>());

		listeners.get(type).add(eventListener);
	}

	public void removeEventListener(EventListener eventListener) {
		for (Map.Entry<String, ArrayList<EventListener>> entry : listeners.entrySet()) {
			if (entry.getValue() == eventListener)
				listeners.get(entry.getKey()).remove(eventListener);
		}
	}

	public void removeEventListener(String type, EventListener eventListener) {
		listeners.get(type).remove(eventListener);
	}

	void addEvent(EventPublisher source, Event event) {
		QueueItem qI = new QueueItem(source, event);

		synchronized (this) {
			queue.add(qI);
			this.proceed = true;
			this.notifyAll();
		}
	}

	private void process(EventPublisher source, Event event) {
		for (Map.Entry<String, ArrayList<EventListener>> entry : listeners.entrySet()) {
			if (entry.getKey().equals(event.type) || entry.getKey() == "all") {

				ArrayList<EventListener> fire = new ArrayList<>();
				ArrayList<EventListener> police = new ArrayList<>();
				ArrayList<EventListener> hospital = new ArrayList<>();
				ArrayList<EventListener> all = new ArrayList<>();

				for (EventListener el : entry.getValue()) {
					if (el instanceof alarmevent.PoliceDepartment)
						police.add(el);
					else if (el instanceof alarmevent.FireDepartment)
						fire.add(el);
					else if (el instanceof alarmevent.Hospital)
						hospital.add(el);
					else if (el instanceof network.Network)
						all.add(el);
					else
						System.out.println("Not a correct instance!");
				}

				Random random = new Random();
				if (!all.isEmpty())
					// Client 6
					all.get(random.nextInt(all.size())).handleEvent(event);
				else {
					switch (event.getType()) {
					case "fire":
						if (!fire.isEmpty())
							fire.get(random.nextInt(fire.size())).handleEvent(event);
						break;
					case "crash":
						if (!police.isEmpty())
							police.get(random.nextInt(police.size())).handleEvent(event);

						if (!hospital.isEmpty())
							hospital.get(random.nextInt(hospital.size())).handleEvent(event);
						break;
					case "assault":
						if (!police.isEmpty())
							police.get(random.nextInt(police.size())).handleEvent(event);
						break;
					default:
						if (!police.isEmpty())
							(police.get(random.nextInt(police.size()))).handleEvent(event);
					}
				}
			}
		}
	}

	@Override
	public void run() {
		while (!stop) {
			synchronized (this) {
				try {
					while (!proceed) {
						if (stop) {
							done = true;
							this.notifyAll();

							break;
						}
						wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.setProceed(false);
			}

			while (!queue.isEmpty()) {
				QueueItem qI = null;

				synchronized (this.queue) {
					if (!queue.isEmpty())
						qI = queue.poll();
				}

				if (qI != null)
					process(qI.source, qI.event);
			}
		}

		synchronized (this) {
			done = true;
			this.notifyAll();
		}

		System.out.println("EventBroker terminated");
	}

	public void start() {
		new Thread(this).start();
	}

	public void stop(Network network) {
		synchronized (this) {
			this.stop = true;
			this.proceed = true;
			this.notifyAll();

			try {
				while (!done) {
					wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		network.terminate();
	}

	public synchronized void setProceed(boolean p) {
		proceed = p;
	}

}

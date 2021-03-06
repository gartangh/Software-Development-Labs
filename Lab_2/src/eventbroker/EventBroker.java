package eventbroker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

final public class EventBroker implements Runnable {

	protected Map<String, ArrayList<EventListener>> listeners = new HashMap<>();

	protected final static EventBroker broker = new EventBroker(); // Singleton

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
		if (listeners.get("all") == null) {
			ArrayList<EventListener> al = new ArrayList<>();
			listeners.put("all", al);
		}

		listeners.get("all").add(el);
	}

	public void addEventListener(String type, EventListener el) {
		if (listeners.get(type) == null) {
			ArrayList<EventListener> al = new ArrayList<>();
			listeners.put(type, al);
		}

		listeners.get(type).add(el);
	}

	public void removeEventListener(EventListener el) {
		for (Map.Entry<String, ArrayList<EventListener>> entry : listeners.entrySet()) {
			if (entry.getValue() == el)
				listeners.get(entry.getKey()).remove(el);
		}
	}

	void addEvent(EventPublisher source, Event e) {
		QueueItem qI = new QueueItem(source, e);
		synchronized (queue) {
			queue.add(qI);
		}
	}

	private void process(EventPublisher source, Event e) {
		for (Map.Entry<String, ArrayList<EventListener>> entry : listeners.entrySet()) {
			if (entry.getKey().equals(e.type)) {
				for (EventListener el : entry.getValue())
					el.handleEvent(e);
			}
		}
	}

	@Override
	public void run() {
		while (!stop) {
			synchronized (this) {
				try {
					while (!proceed){
						if(stop){
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
	}

	public void start() {
		new Thread(this).start();
	}

	public void stop() {
		synchronized(this){
			stop = true;
			this.notifyAll();
		}
		
		synchronized (this) {
			try {
				while (!done){
					wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void setProceed(boolean p) {
		proceed = p;
	}

}

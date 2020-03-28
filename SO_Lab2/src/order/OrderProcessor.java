package order;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eventbroker.Event;
import eventbroker.EventBroker;
import eventbroker.EventListener;

public class OrderProcessor implements EventListener {

	protected static int processedOrders = 0;
	
	private static ExecutorService executorService = Executors.newFixedThreadPool(4);

	public OrderProcessor() {
		// Empty constructor
	}

	// Factory method
	public static OrderProcessor createOrderProcessor() {
		OrderProcessor orderProcessor = new OrderProcessor();
		EventBroker.getEventBroker().addEventListener(OrderEvent.TYPE_ORDER, orderProcessor);

		return orderProcessor;
	}

	@Override
	public void handleEvent(Event e) {
		OrderEvent order = (OrderEvent) e;
		processOrder(order);
	}

	protected synchronized void processOrder(OrderEvent order) {
		doWork(20);
		processedOrders++;
		System.out.println("Order of item " + order.getItem() + " for customer " + order.getCustomer() + " processed!");
	}

	public static int getNumberOfOrders() {
		return processedOrders;
	}

	// Do some dummy work for @milliseconds ms
	private void doWork(int milliseconds) {
		long t1 = System.currentTimeMillis();
		while(System.currentTimeMillis()-t1 < milliseconds) {}
	}
	
	public static ExecutorService getExecutorService() {
		return executorService;
	}

}

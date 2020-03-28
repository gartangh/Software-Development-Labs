package main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import eventbroker.EventBroker;
import order.BlacklistOrderProcessor;
import order.Customer;
import order.OrderProcessor;

public class Main {

	public static int noOrders = 10;

	public static void main(String[] args) {
		String[] names = new String[]{"Jan", "Piet", "Joris", "Corneel"};
		//Thread[] s = new Thread[names.length];
		//int i = 0;

		BlacklistOrderProcessor blacklistOrderProcessor = BlacklistOrderProcessor.createBlacklistOrderProcessor();

		EventBroker eventBroker = EventBroker.getEventBroker();
		eventBroker.start();

		ExecutorService executorService = OrderProcessor.getExecutorService();

		for(String name : names) {
			// Anonymous class
			Runnable r = new Runnable() {

				Customer customer = new Customer(name);

				@Override
				public void run() {
					System.out.println("using thread: " + customer.getName());
					for(int i = 0; i < noOrders; i++) {
						customer.buy("item " + i);
						
						try {
							Thread.sleep(1000);
						}
						catch (InterruptedException e) {
							e.printStackTrace();
						}

						synchronized (eventBroker) {
							eventBroker.setProceed(true);
							eventBroker.notifyAll();
						}
					}
					System.out.println("Stopped using thread: " + customer.getName());
				}

			};

			//			Thread t = new Thread(r);
			//			s[i++] = t;

			executorService.execute(r);
		}

		//		for (Thread t : s) {
		//			try {
		//				t.join();
		//				//System.out.println("Thread joined");
		//			}
		//			catch (InterruptedException e) {
		//				e.printStackTrace();
		//			}
		//		}

		//System.out.println("Threads joined");		

		executorService.shutdown();
		
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		eventBroker.stop();
		
		System.out.println("Number of orders: " + OrderProcessor.getNumberOfOrders());
	}

}

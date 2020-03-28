package order;

import eventbroker.EventBroker;
import java.util.HashSet;
import java.util.Set;

public class BlacklistOrderProcessor extends OrderProcessor {

	private Set<String> blacklist;

	public BlacklistOrderProcessor() {
		super();

		// Do some dummy work to get to the blacklist data
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Add the blacklisted customers
		blacklist = new HashSet<String>();
		blacklist.add("Jan");
	}

	// Factory method
	public static BlacklistOrderProcessor createBlacklistOrderProcessor() {
		BlacklistOrderProcessor blacklistOrderProcessor = new BlacklistOrderProcessor();
		EventBroker.getEventBroker().addEventListener(OrderEvent.TYPE_ORDER, blacklistOrderProcessor);

		return blacklistOrderProcessor;
	}

	@Override
	protected synchronized void processOrder(OrderEvent order) {
		// Ignore blacklisted customers
		if (blacklist.contains(order.getCustomer()))
			System.out.println("Order " + order.getItem() + " of customer " + order.getCustomer() + " discarded");
		else
			super.processOrder(order);
	}

}

package order;

import eventbroker.EventPublisher;

public class Customer extends EventPublisher {

	private String name;

	public Customer(String name) {
		this.name = name;
	}
	
	//to test
	public String getName(){
		return this.name;
	}

	public void buy(String item) {
		System.out.println(name + " places an order for item " + item);
		publishEvent(new OrderEvent(name, item));
	}
}

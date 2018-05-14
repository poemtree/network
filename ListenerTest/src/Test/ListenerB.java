package Test;

public class ListenerB implements EventListener {
	public ListenerB() {
		EventHandler.addListener(this);
	}

	@Override
	public void onEvent(String event) {
		if (event.equals("EVENT_B")) {
			// do something here for EVENT_B event.
			System.out.println("I am B !");
		}
	}
}
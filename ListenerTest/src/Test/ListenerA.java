package Test;

public class ListenerA implements EventListener {
	public ListenerA() {
		EventHandler.addListener(this);
	}

	@Override
	public void onEvent(String event) {
		if (event.equals("EVENT_A")) {
			// do something here for EVENT_A event.
			System.out.println("I am A !");
		}
	}
}
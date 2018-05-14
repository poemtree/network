package Listener;

public interface EventListener {
	public void onEvent(String event, String rawTotal, String rawCanID);
}
package Test;

public class Main {
	public static void main(String[] args) {
		ListenerA listenerA = new ListenerA();
		ListenerB listenerB = new ListenerB();

		EventHandler.callEvent(Main.class, "EVENT_A", false); // 동기 호출
		System.out.println("After introduce ListenerA");
		EventHandler.callEvent(Main.class, "EVENT_B", true); // 비동기 호출
	}
}
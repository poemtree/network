package Test;

public class Main {
	public static void main(String[] args) {
		ListenerA listenerA = new ListenerA();
		ListenerB listenerB = new ListenerB();

		EventHandler.callEvent(Main.class, "EVENT_A", false); // ���� ȣ��
		System.out.println("After introduce ListenerA");
		EventHandler.callEvent(Main.class, "EVENT_B", true); // �񵿱� ȣ��
	}
}
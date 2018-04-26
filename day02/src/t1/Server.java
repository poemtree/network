package t1;

import java.util.Scanner;

public class Server {
	
	int count;
	boolean flag;
	
	public Server() {
		this.count = 0;
		this.flag = true;
	}
	
	public static void main(String[] args) {
		
		
		System.out.println("---Server Start---");
		new Server().startServer();
		System.out.println("---Server Stop---");
	}
	
	public void startServer() {
		count++;
		Scanner client = new Scanner(System.in);
		while(flag) {
			System.out.println("Server Ready.."+count);
			String msg = client.nextLine();
			Receiver receiver = new Receiver(msg);
			receiver.start();
		}
		
	}
	
	// Receiver 
	// 요청을 받아서 처리하고 다시 전송 한다.
	class Receiver extends Thread {
		
		private String msg;
		
		public Receiver() {
			super();
		}
		
		public Receiver(String msg) {
			this.msg = msg;
		}
		
		public String getMsg() {
			return this.msg;
		}
		
		public void setMsg(String msg) {
			this.msg = msg;
		}
		
		@Override
		public void run() {
			for(int i=0; i<10; i++) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.print(i+"..");
			}
			System.out.println(msg+" Completed");
			// Sender Thread를 통해 Client에게 전송
			
			Sender sender = new Sender(msg);
			sender.start();
		}
	}
	// end Receiver
	
	class Sender extends Thread {
		private String msg;
		
		public Sender() {
			super();
		}
		
		public Sender(String msg) {
			this.msg = msg;
		}
		
		public String getMsg() {
			return this.msg;
		}
		
		public void setMsg(String msg) {
			this.msg = msg;
		}
				
		@Override
		public void run() {
			for(int i=0;i<10;i++) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.print(i+"..");
			}
			System.out.println(msg+" Send Completed");
		}
	}
}



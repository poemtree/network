package serial;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import Listener.EventHandler;
import Listener.EventListener;

public class  Client extends Thread {
	
	String address;
	int port;
	Socket socket;
	boolean rflag;
	ClientListener listener;

	public Client() {
		rflag = true;
		address = "127.0.0.1";
		port = 8888;
		listener = new ClientListener();
	}

	public boolean connectServer() {
		boolean result = false;
		int count = 0;
		while (count < 11) { // 서버와 통신 될 때 까지 접속 시도 루프
			try {
				socket = new Socket(address, port);
				if (socket != null && socket.isConnected()) {
					count = 11;
					result = true;
				}
			} catch (IOException e) {
				count++;
				System.out.println("Re-Try Connection..." + count);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		return result;
	}
	
/*	public void sendMessage(String sendMessage) {

	}*/
	
	public void run(){
		if(connectServer()) {
			try {
				new Receiver(socket).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	class Sender implements Runnable {
		Socket socket;
		OutputStream out;
		DataOutputStream outw;
		String sendMsg;

		public Sender(Socket socket, String sendMsg) throws IOException {
			this.socket = socket;
			this.sendMsg = sendMsg;
			out = socket.getOutputStream();
			outw = new DataOutputStream(out);
		}

		public void setSendMsg(String sendMsg) {
			this.sendMsg = sendMsg;
		}

		@Override
		public void run() {
			try {
				if (outw != null) {
					outw.writeUTF(sendMsg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	class Receiver extends Thread {
		Socket socket;
		InputStream in;
		DataInputStream inr;
		
		public Receiver(Socket socket) throws IOException {
			this.socket = socket;
			in = socket.getInputStream();
			inr = new DataInputStream(in);
		}

		@Override
		public void run() {
			while (rflag) {
				try {
					String str = inr.readUTF();
					System.out.println(str);
					EventHandler.callEvent(Client.class, "sendSerial", "W2810003B010000000000005011", "10003B01");
					if (str.trim().equals("q")) {
						inr.close();
						break;
					}
				} catch (Exception e) {
					System.out.println("Disconnected...");
					break;
				}
			}

			try {
				Thread.sleep(1000);
				socket.close();
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	class ClientListener implements EventListener {
		public ClientListener() {
			EventHandler.addListener(this);
		}
		@Override
		public void onEvent(String event, String rawTotal, String rawCanID) {
			if(event.equals("sendSocket")) {
				try {
					new Thread(new Sender(socket, rawCanID+rawTotal)).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}

package tcp3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	private String ip;
	private int port;
	private Socket socket;
	boolean connection;
	
	public Client() {
		super();
	}

	public Client(String ip, int port) throws UnknownHostException, IOException {
		this.ip = ip;
		this.port = port;
		if (connectServer()) {
			connection = true;
			startClient();
		}
	}

	public boolean connectServer() {
		boolean result = false;
		int count = 0;
		while (count < 11) { // 서버와 통신 될 때 까지 접속 시도 루프
			try {
				socket = new Socket(ip, port);
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

	public void startClient() throws UnknownHostException, IOException {
		System.out.println("Connected " + socket.getInetAddress());
		try {
			Thread receiver = new Thread(new Receiver(socket));
			Thread sender = new Thread(new Sender(socket));
			receiver.start();
			sender.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Client client = null;
		try {
			client = new Client("70.12.114.134", 7777);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class Receiver implements Runnable {
		Socket socket;
		String receiveMessage;
		DataInputStream in;

		public Receiver(Socket socket) {
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while (connection) {
				try {
					receiveMessage = in.readUTF();
					System.out.println(receiveMessage);
				} catch (IOException e) {
					System.out.println("Disconnected");
				}
			}

		}
	}

	class Sender implements Runnable {
		Socket socket;
		String sendMessage;
		DataOutputStream out;
		String client;

		public Sender(Socket socket) {
			this.socket = socket;
			try {
				client = "[" + socket.getInetAddress() + "]";
				out = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			Scanner scn = new Scanner(System.in);
			while (connection) {
				sendMessage = scn.nextLine();
				if(sendMessage.equals("q")){
					connection = false;
				} else {
					sendMessage = client + " " + sendMessage;
				}
				try {
					out.writeUTF(sendMessage);
				} catch (IOException e) {
					connection = false;
				}
			}
			scn.close();
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

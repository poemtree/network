package tcp3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private int port;
	private ServerSocket serverSocket;
	boolean connection;
	Thread receiver;
	Thread sender;
	BufferedReader br;
	
	public Server() throws IOException {
		port = 7777;
		serverSocket = new ServerSocket(port);
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void startServer() throws IOException {
		System.out.println("Start Server...");
		System.out.println("Ready Server...");
		try {
			Socket socket = serverSocket.accept();
			System.out.println("Connected Client... " + socket.getInetAddress());
			connection = true;
			receiver = new Thread(new Receiver(socket));
			sender = new Thread(new Sender(socket));
			receiver.start();
			sender.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			Server server = new Server();
			server.startServer();
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
					if(receiveMessage.equals("q")) {
						connection = false;
						receiveMessage = "Disconnected";
						br.close();
						System.out.println("Scanner ²¨Á®¶ó Á»");
					}
					System.out.println(receiveMessage);
				} catch (IOException e) {
					connection = false;
				}
			}
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
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
			while (connection) {
				try {
					while(!br.ready()) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					sendMessage = br.readLine();
					out.writeUTF(client + " " + sendMessage);
				} catch (IOException e) {
					connection = false;
				}
			}
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}

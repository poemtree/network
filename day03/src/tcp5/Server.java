package tcp5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;

public class Server {
	final private int port=9999;
	private ServerSocket serverSocket;
	private boolean flag;
	private HashMap<String, DataOutputStream> map;
	
	public Server() throws IOException{
		flag = true;
		map = new HashMap<>();
		
		serverSocket = new ServerSocket(port);
		System.out.println("Server is ready...");
		
	}
	
	public void startServer() throws IOException{
		while(flag) {
			Socket socket = serverSocket.accept();
			System.out.println("Connected Client..." + socket.getInetAddress());
			Thread receiver = new Thread(new Receiver(socket));
			System.out.printf("Client Num(%d)\n", map.size());
			receiver.start();
			
		}
		
	}
	
	public static void main(String[] agrs) {
		Server server = null;
		try {
			server = new Server();
			server.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	class Receiver implements Runnable {
		private Socket socket;
		private String address;
		private DataInputStream dis;
		private String receiveMessage;
		
		public Receiver(Socket socket) throws IOException {
			this.socket = socket; 
			address = this.socket.getInetAddress().toString();
			dis = new DataInputStream(this.socket.getInputStream());
			map.put(address, new DataOutputStream(this.socket.getOutputStream()));
		}
		
		@Override
		public void run() {
			while(dis != null) {
				try {
					receiveMessage = dis.readUTF();
					new SendHttp(receiveMessage).start();
					if(receiveMessage.equals("q")) {
						receiveMessage = "Disconnected.." + address;
						break;
					} else {
						receiveMessage = address + " : " + receiveMessage;
					}
					for(String adr : map.keySet()) {
						if(!adr.equals(address)) {
							map.get(adr).writeUTF(receiveMessage);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			try {
				if(dis != null)
					dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	class SendHttp extends Thread {
		String adr = "http://127.0.0.1/ws/main.do";
		String speed;
		String temp;
		public SendHttp(String msg) {
			speed = msg.split("/")[0];
			temp = msg.split("/")[1];
			adr += "?speed=" + speed + "&temp="+temp;
		}
		
		public void run() {
			URL url = null;
			HttpURLConnection con = null;
			
			try {
				url = new URL(adr);
				con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true); 
				con.setInstanceFollowRedirects(false); 
				con.setRequestMethod("POST"); 
				con.setRequestProperty("Content-Type", "text/plain"); 
				con.setRequestProperty("charset", "utf-8");
				con.setDoOutput(true);
				con.setConnectTimeout(5000);
				con.getInputStream();
				System.out.println("Http OK");
			} catch (MalformedURLException e) {
				e.printStackTrace();
				System.out.println("Http Error");
			} catch (IOException e) {
					e.printStackTrace();
			}
		}
	}
	
}

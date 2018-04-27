package tcp2;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private int port;
	private boolean flag;
	private ServerSocket serverSocket;
	
	public Server() throws IOException {
		port = 7777;
		flag = true;
		serverSocket = new ServerSocket(port);
	}
	
	// Accept Client Socket And
	// Sender Thread Create(Send Socket) and Start
	public void startServer() throws IOException {	
		System.out.println("Start Server...");
		System.out.println("Ready Server...");
		while(flag) {
			Thread clientThread = new Thread(new Sender(serverSocket.accept()));
			clientThread.start();
		}
		System.out.println("End Server...");
	}
	
	class Sender implements Runnable {
		
		private Socket socket;
				
		public Sender(Socket socket) {
			this.socket = socket;
			System.out.println("Connected Client... " + socket.getInetAddress());
		}
		
		@Override
		public void run() {
			try (OutputStream os = socket.getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(os);)
			{
				Thread.sleep(3000);
				osw.write("통신 완료..");
			} catch (IOException e) {
				System.out.println("통신 중 오류 발생..");
			} catch (InterruptedException e) {
				System.out.println("작업 중 오류 발생..");
			} finally {
				System.out.println("Disconnected Client... " + socket.getInetAddress());
					try {
						if(socket != null)
							socket.close();
					} catch (IOException e) {
						System.out.println("통신 종료 중 오류 발생..");
					}
			}
		}
	}
			
	public static void main(String[] args) {
		try {
			Server server = new Server();
			server.startServer();
		} catch (IOException ie) {
			ie.printStackTrace();
		} finally {
			
		}
	}
}

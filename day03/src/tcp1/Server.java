package tcp1;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

// 소켓프로그래밍 시작.... 매우 중요
public class Server {
	
	// 통신에서 빠질 수 없는 포트..  
	int port;
	ServerSocket serverSocket;
	Socket socket;
	OutputStream os;
	OutputStreamWriter osw;
	boolean flag;
	
	public Server() throws IOException {
		port = 7777;
		flag = true;
		serverSocket = new ServerSocket(port);
	}
	
	public void startServer() {	
		System.out.println("Start Server...");
		System.out.println("Ready Server...");
		while(flag) {
			try {
				socket = serverSocket.accept();
				System.out.println("Connected Client... " + socket.getInetAddress());
				os = socket.getOutputStream();
				osw = new OutputStreamWriter(os);
				Thread.sleep(10000);
				osw.write("통신 완료..");
			} catch (IOException e) {
				System.out.println("통신 중 오류 발생...");
			} catch (Exception e) {
				
			} finally {
				if(osw != null)
					try {
						osw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(socket != null)
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		System.out.println("End Server...");
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
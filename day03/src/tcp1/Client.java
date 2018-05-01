package tcp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	String ip;
	int port;
	Socket socket;
	InputStream is;
	InputStreamReader ir;
	BufferedReader br;
	
	public Client() {
		super();
	}

	public Client(String ip, int port) throws UnknownHostException, IOException {
		this.ip = ip;
		this.port = port;
		connectServer();
		startClient();
	}
	
	public void connectServer() {
		boolean flag = true;	// 루프 제어 플래그
		while(flag) {	// 서버와 통신 될 때 까지 접속 시도 루프
			try {
				socket = new Socket(ip, port);
				if(socket != null && socket.isConnected()) {
					flag = false ;
				}
			} catch (IOException e) {
				System.out.println("Re-Try Connection...");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void startClient() throws UnknownHostException, IOException {
		try {		
			System.out.println("Connected Server..");
			is = socket.getInputStream();
			ir = new InputStreamReader(is);
			br = new BufferedReader(ir);
			String str = br.readLine();
			System.out.println(str);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(socket != null)
				socket.close();
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
	
}

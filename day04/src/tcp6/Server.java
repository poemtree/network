package tcp6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;

public class Server extends Thread {

    private int port;
    private ServerSocket serverSocket;
    private boolean flag;
    private HashMap<String, DataOutputStream> map;
    private boolean rflag;
	
    public Server() {
    	flag = true;
    	rflag = true;
    	port = 9999;
        map = new HashMap<>();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
        	System.out.println("비정상적으로 종료 되었습니다...Receiver(0)");
        }
 
    }
 
    public void run() {
    	System.out.println("Server starts...");
        while(flag) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connected Client..." + socket.getInetAddress());
                Thread receiver = new Thread(new Receiver(socket));
                System.out.printf("Client Num(%d)\n", map.size());
                receiver.start();
            } catch (IOException e) {
            	System.out.println("비정상적으로 종료 되었습니다...Server(1)");
            }
        }
    }
    
    class Sender extends Thread {
    	
    	private String sendMessage;
    	private String address;
    	
    	public Sender(String address, String sendMessage) {
    		this.address = address;
    		this.sendMessage = sendMessage;
    	}
    	
    	@Override
    	public void run() {
    		try {
	    		for(String adr : map.keySet()) {
	                if(!adr.equals(address)) {            
						map.get(adr).writeUTF(sendMessage);
	                }
	            }
    		} catch (IOException e) {
    			System.out.println("비정상적으로 종료 되었습니다...Sender");
			}
    	}
    }
    
    class Receiver implements Runnable {
        private Socket socket;
        private String address;
        private DataInputStream dis;
        private DataOutputStream dos;
        private String receiveMessage;
        
        public Receiver(Socket socket) {
            this.socket = socket;
           
            address = this.socket.getInetAddress().toString();
            try {
				dis = new DataInputStream(this.socket.getInputStream());
				dos = new DataOutputStream(this.socket.getOutputStream());
				map.put(address, dos);
			} catch (IOException e) {
				System.out.println("비정상적으로 종료 되었습니다...Receiver(0)");
			}
            
        }

        @Override
        public void run() {
        	try {
		        while(rflag) {
		            	receiveMessage = dis.readUTF();     	
		                if(receiveMessage.equals("q")) {
		                	map.remove(address);
		                    receiveMessage = "Disconnected.." + address;
		                    break;
		                } else {
		                    receiveMessage = address + " : " + receiveMessage;
		                }
		                new Sender(address, receiveMessage).start();
		        }
		        
            } catch (Exception e) {
            	System.out.println("비정상적으로 종료 되었습니다...Receiver(1)");
             
            } finally {
	            try {
	                if(dis != null)
	                    dis.close();
	            } catch (IOException e) {
	                System.out.println("비정상적으로 종료 되었습니다...Receiver(2)");
	            }
	            try {
	                if(socket != null)
	                    socket.close();
	            } catch (IOException e) {
	            	System.out.println("비정상적으로 종료 되었습니다...Receiver(3)");
	            }
	
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
                System.out.println("Http Error...(0)");
            } catch (IOException e) {
            	System.out.println("Http Error...(1)");
            }
        }
    }
    
}

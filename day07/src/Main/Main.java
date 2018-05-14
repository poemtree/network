package Main;

import java.io.IOException;

import Listener.EventHandler;
import Listener.EventListener;
import serial.Client;
import serial.SendAndReceiveSerial;

public class Main {

	public static void main(String args[]) throws Exception {

		SendAndReceiveSerial serialProtocol = new SendAndReceiveSerial("COM9", true);
		Client client = new Client();
		client.start();		
	}
	
}

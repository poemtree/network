package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Http1 {
	
	public static void main(String[] args) {
		URL url = null;
		String address = "http://localhost/";
		
		try {
			url = new URL(address);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		StringBuffer sb = new StringBuffer();
		String str = null;
		
		try(InputStreamReader ir = new InputStreamReader(url.openStream()); BufferedReader br = new BufferedReader(ir);) {
			while(true) {
				str = br.readLine();
				if(str == null) {
					break;
				}
				sb.append(str+"\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sb.toString());
	}
}

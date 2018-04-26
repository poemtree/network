package http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Http2 {

	public static void main(String[] args) throws Exception{
		String surl = "http://localhost/login?id=qq&pwd=11&name=";
		URL url = new URL(surl+ URLEncoder.encode("영무","UTF-8"));
		URLConnection con = url.openConnection();
		con.setConnectTimeout(5000);
		InputStream is = con.getInputStream();
		InputStreamReader ir = new InputStreamReader(is,"UTF-8");
		BufferedReader br = new BufferedReader(ir);
		
		String str = br.readLine();
		System.out.println(str);
		
		br.close();
		ir.close();
		is.close();
	}
}

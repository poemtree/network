package http;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class Http3 {
	
	public static void main(String[] args) throws Exception{
		URL url = new URL("http://localhost/bg1.jpg");
		InputStream in = url.openStream();
		FileOutputStream out = new FileOutputStream("down.jpg");
		int i=0;
		while(true) {
			i = in.read();
			if(i == -1) {
				break;
			}
			out.write(i);
		}
	}

}

package calculator;

import java.io.IOException;
import java.net.*;

public class CalcServer {

	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(12345);
		while (true){
			Socket socket = s.accept();
			SserverProc proc = new SserverProc(socket);
			proc.start();
		}

	}

}

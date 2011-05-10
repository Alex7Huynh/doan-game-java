package calculator;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
public class SserverProc extends Thread {
	Socket socket;
	BufferedReader netIn;
	PrintWriter netOut;
	char oper;
	double op1, op2;
	
	public SserverProc(Socket socket) throws IOException {
		this.socket = socket;
		netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		netOut = new PrintWriter(socket.getOutputStream(),true);
		netOut.println("Welcome to computing system....");
	}
	public void run(){
		String line;
		try{
		while(true){
		 try{	
			line = netIn.readLine();
			if (line.equalsIgnoreCase("exit")) break;
			procCommand(line);
			
			double res = calc();
			
			line = line + " = " + res;
			netOut.println(line);
		 } catch(NoSuchMethodException e){
			 netOut.println(e.getMessage());
		 }
		}
		socket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private double calc() {
		double r = 0;
		switch (oper){
		case '+': r = op1+op2;
			break;
		case '-': r = op1-op2;
		break;
		case '*': r = op1*op2;
		break;
		case '/': r = op1/op2;
		break;
		}
		return r;
	}
	private void procCommand(String line) throws NoSuchMethodException {
		StringTokenizer st = new StringTokenizer(line,"+-*/");
		String sop1 = st.nextToken();
		String sop2 = st.nextToken();
		oper = line.charAt(sop1.length());
		try{
		op1 = Double.parseDouble(sop1.trim());
		} catch(NumberFormatException e){
			throw new NoSuchMethodException("Tham so 1 khong phai so");
		}
		try{
		op2 = Double.parseDouble(sop2.trim());
		} catch(NumberFormatException e){
			throw new NoSuchMethodException("Tham so 2 khong phai so");
		}
	}
}

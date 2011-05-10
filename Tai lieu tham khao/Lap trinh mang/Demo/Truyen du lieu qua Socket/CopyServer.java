package TCP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CopyServer {
    public static final int port = 12345;

    public static void main(String[] args) throws IOException {
        // do server là người truyền dữ liệu nên server fai la người đóng kết nối - socket.close();
        String sourceFile = "E:\\Test\\Test.rar";

        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(sourceFile));
        System.out.println("đợi kết nối....");
        
        ServerSocket server = new ServerSocket(port);
        Socket socket = server.accept();
        System.out.println("đã kết nối....");
        
        BufferedInputStream netIn = new BufferedInputStream(socket.getInputStream());
        BufferedOutputStream netOut = new BufferedOutputStream(socket.getOutputStream());
        
        int c;
        while ((c = fis.read()) != -1)
            netOut.write(c);
        netOut.flush();
        
        fis.close();
     // do server là người truyền dữ liệu nên server fai la người đóng kết nối
        socket.close();
        
        System.out.println("kết thúc");
    }
}

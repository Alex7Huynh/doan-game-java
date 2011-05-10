package TCP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;

public class CopyClient {
    public static void main(String[] args) throws UnknownHostException, IOException {
        String destFile = "D:\\copy.rar";
        BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(destFile));

        Socket socket = new Socket("127.0.0.1", CopyServer.port);

        BufferedInputStream netIn = new BufferedInputStream(socket.getInputStream());
        BufferedOutputStream netOut = new BufferedOutputStream(socket.getOutputStream());

        int c;
        while ((c = netIn.read()) != -1)
            fos.write(c);
        fos.flush();

        fos.close();
        
        // khi nằm ở trên 2 máy tính khác nhau thì khi server đóng kết nối rồi thì client mới đóng kết nối
        //còn do ở đây làm trên 1 máy tính nên ta ko cần đóng kết nối
        // socket.close();

    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hearts_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Czytelnik
 */
public class ListenClientThread extends Thread {

    ListenThread ListenT;
    BufferedReader BuffReader;
    int IdClient;
    boolean Running;

    ListenClientThread(ListenThread lt, BufferedReader br, int i) {
        BuffReader = br;
        ListenT = lt;
        IdClient = i;
        start();
    }

    public void run() {
        Running = true;
        String MessageClient = "";

        try {
            while (Running) {
                MessageClient = BuffReader.readLine();
                if (MessageClient.startsWith("1")) {
                    ListenT.GameT.MessageClient[IdClient] = MessageClient.substring(2);
                } else {
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ListenClientThread.class.getName()).log(Level.SEVERE, null, ex);
            //xử lý khi 1 client ngưng chơi đột ngột
            if (ListenT.GameT != null) {
                ListenT.GameT.stop();
            }
            for (int k = 1; k < 4; k++) {
                try {
                    ListenT.ListClient[(IdClient + k) % 4].BuffWriter.write("9%" + ListenT.ListClient[IdClient].Name + "\n");
                    ListenT.ListClient[(IdClient + k) % 4].BuffWriter.flush();
                    ListenT.ListClient[(IdClient + k) % 4].BuffWriter.write("10%" + ListenT.ListClient[0].Name + "%" + ListenT.ListClient[0].TongDiem + "%" + ListenT.ListClient[1].Name + "%" + ListenT.ListClient[1].TongDiem + "%" + ListenT.ListClient[2].Name + "%" + ListenT.ListClient[2].TongDiem + "%" + ListenT.ListClient[3].Name + "%" + ListenT.ListClient[3].TongDiem + "\n");
                    ListenT.ListClient[(IdClient + k) % 4].BuffWriter.flush();
                } catch (IOException ex1) {
                    Logger.getLogger(ListenClientThread.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }


        }
    }
}

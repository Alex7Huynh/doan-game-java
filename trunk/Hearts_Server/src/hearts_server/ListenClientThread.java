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
                }
                if (MessageClient.startsWith("2")) {
                    for(int i=0;i<4;i++){
                        ListenT.ListClient[i].BuffWriter.write("13%"+ListenT.ListClient[IdClient].Name+": "+MessageClient.substring(2)+"\n");
                        ListenT.ListClient[i].BuffWriter.flush();
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ListenClientThread.class.getName()).log(Level.SEVERE, null, ex);
            //xử lý khi 1 client ngưng chơi đột ngột
            if (ListenT.GameT != null) {
                ListenT.GameT.stop();
            }
            try {
                if (ListenT.SoClient == 4) {
                    // gửi kết quả cuối cùng cho 3 client còn lại
                    for (int k = 1; k < 4; k++) {
                        ListenT.ListClient[(IdClient + k) % 4].BuffWriter.write("9%" + ListenT.ListClient[IdClient].Name + "\n");
                        ListenT.ListClient[(IdClient + k) % 4].BuffWriter.flush();
                        ListenT.ListClient[(IdClient + k) % 4].BuffWriter.write("10%" + ListenT.ListClient[0].Name + "%" + ListenT.ListClient[0].TongDiem + "%" + ListenT.ListClient[1].Name + "%" + ListenT.ListClient[1].TongDiem + "%" + ListenT.ListClient[2].Name + "%" + ListenT.ListClient[2].TongDiem + "%" + ListenT.ListClient[3].Name + "%" + ListenT.ListClient[3].TongDiem + "\n");
                        ListenT.ListClient[(IdClient + k) % 4].BuffWriter.flush();
                        Thread.sleep(500);
                        ListenT.ListListenClientT[(IdClient + k) % 4].stop();
                        ListenT.ListClient[(IdClient + k) % 4].BuffWriter.close();
                        ListenT.ListListenClientT[(IdClient + k) % 4].BuffReader.close();
                        ListenT.ListClient[(IdClient + k) % 4].Soc.close();
                    }
                    ListenT.ListClient[IdClient].BuffWriter.close();
                    ListenT.ListListenClientT[IdClient].BuffReader.close();
                    ListenT.ListClient[IdClient].Soc.close();
                    ListenT.SoClient = 0;
                } else {// trường hợp mất liên lạc với 1 client khi game chưa bắt đầu chơi
                    ListenT.ListClient[IdClient].BuffWriter.close();
                    ListenT.ListListenClientT[IdClient].BuffReader.close();
                    ListenT.ListClient[IdClient].Soc.close();
                    int temp = ListenT.SoClient;
                    --ListenT.SoClient;
                    if (IdClient < temp - 1) {
                        for (int i = IdClient; i < temp; i++) {
                            ListenT.ListClient[i] = ListenT.ListClient[i + 1];
                            ListenT.ListListenClientT[i] = ListenT.ListListenClientT[i + 1];
                            ListenT.ListClient[i].Id--;
                            ListenT.ListListenClientT[i].IdClient--;
                        }
                    }
                }
            } catch (InterruptedException ex1) {
                Logger.getLogger(ListenClientThread.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IOException ex1) {
                Logger.getLogger(ListenClientThread.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
}



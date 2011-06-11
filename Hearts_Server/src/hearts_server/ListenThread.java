/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hearts_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Czytelnik
 */
public class ListenThread extends Thread {

    public boolean Running;
    public ServerSocket ServerS;
    int SoClient;
    public Client ListClient[];
    public ListenClientThread ListListenClientT[];
    public GameThread GameT;

    ListenThread(ServerSocket Soc) {

        ServerS = Soc;
        ListClient = new Client[4];
        ListListenClientT = new ListenClientThread[4];
        SoClient = 0;
        start();
    }

    @Override
    public void run() {
        Running = true;
        Socket temp = null;
        while (Running) {
            try {
                temp = ServerS.accept();
                OutputStream os;
                BufferedWriter bw;
                if (SoClient == 4) {
                    os = temp.getOutputStream();
                    bw = new BufferedWriter(new OutputStreamWriter(os));
                    bw.write("1%DaDuNguoiChoi\n");
                    bw.flush();
                    temp.close();
                } else {
                    os = temp.getOutputStream();
                    bw = new BufferedWriter(new OutputStreamWriter(os));
                    InputStream is = temp.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    ListListenClientT[SoClient] = new ListenClientThread(this,br,SoClient);
                    ListClient[SoClient] = new Client(temp, bw, SoClient);
                    SoClient++;
                    if (SoClient == 4) {
                        GameT = new GameThread(this);
                    } else {
                        bw.write("1%ChuaDuNguoiChoi\n");
                        bw.flush();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}

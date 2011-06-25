package hearts_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import mypackage.Hearts;

public class ListenThread extends Thread {
    Socket ClientS;
    BufferedReader BuffReader;
    ConnectJFrame ConnectF;
    LoginJFrame LoginF;
    Hearts myHearts;
    boolean Running;
    int LanGui = 0;

    ListenThread(ConnectJFrame cf, Socket s, BufferedReader br) {
        ClientS = s;
        BuffReader = br;
        ConnectF = cf;
        start();
    }

    public void run() {
        Running = true;
        String MessageServer;
        String NoiDung;
        int Co;
        try {
            while (Running) {
                MessageServer = BuffReader.readLine();
                Co = Integer.parseInt(MessageServer.substring(0, MessageServer.indexOf("%")));
                NoiDung = MessageServer.substring(MessageServer.indexOf("%") + 1);
                switch (Co) {
                    case 1: {
                        if (NoiDung.equals("ChuaDuNguoiChoi")) {
                            JOptionPane.showMessageDialog(new JFrame(), "Hiện chưa đủ người chơi. Bạn vui lòng đợt trong giây lát. ", null, JOptionPane.INFORMATION_MESSAGE);                            
                        }
                        if (NoiDung.equals("DaDuNguoiChoi")) {
                            JOptionPane.showMessageDialog(new JFrame(), "Hiện đã đủ người chơi. Hẹn bạn lần sau.. ", null, JOptionPane.INFORMATION_MESSAGE);
                            ClientS.close();
                            BuffReader.close();
                            Running = false;
                            ConnectF.setVisible(true);
                        }
                        if (NoiDung.equals("YeuCauUserName")) {                            
                            LoginF = new LoginJFrame(ConnectF);
                            LoginF.setVisible(true);
                        }
                        if (NoiDung.equals("UserNameDaCo")) {
                            JOptionPane.showMessageDialog(new JFrame(), "UserName đã có người dùng. Mời bạn chọn UserName khác. ", null, JOptionPane.INFORMATION_MESSAGE);
                            ConnectF.setVisible(true);
                        }
                        if (NoiDung.startsWith("BatDauChoi")) {
                            LoginF.setVisible(false);                            

                            myHearts = new Hearts();
                            myHearts.makeConnection(ConnectF);                            
                            myHearts.setPlayerName(0, ConnectF.MyName);                            
                            myHearts.setTitle(ConnectF.MyName);
                            
                            NoiDung = NoiDung.substring(NoiDung.indexOf("%") + 1);
                            String temp[] = NoiDung.split("%");
                            int dem = 0;
                            for (int i = 0; i < 4; i++) {
                                if (temp[i].equals(ConnectF.MyName)) {
                                    dem = i;
                                    break;
                                }
                            }
                            for (int i = 0; i < 3; i++) {                                
                                myHearts.setPlayerName(i + 1, temp[++dem % 4]);
                            }
                        }
                        break;
                    }
                    case 2: {                        
                        Hearts.notice(NoiDung);
                        myHearts.xuLyKetQuaLuotChuoi(NoiDung);
                        break;
                    }
                    case 3: {                        
                        myHearts.createListCard(NoiDung);
                        myHearts.newGame();

                        break;
                    }
                    case 4: {                        
                        Hearts.notice(NoiDung);
                        myHearts.passCard();
                        break;
                    }
                    case 5: {                        
                        Hearts.notice(NoiDung);                        
                        myHearts.playCard();
                        break;
                    }
                    case 6: {                        
                        Hearts.notice(NoiDung);
                        myHearts.showPlayCard(NoiDung);
                        break;
                    }
                    case 7: {                        
                        Hearts.notice(NoiDung);
                        myHearts.CoQuyenDiCo(true);
                        break;
                    }
                    case 8: {
                        Hearts.notice("8%"+NoiDung);
                        myHearts.thongBaoVanChoiKetThuc(NoiDung);
                        break;
                    }
                    case 9: {                        
                        Hearts.notice(NoiDung);
                        myHearts.thongBaoNgungChoi(NoiDung);
                        break;
                    }
                    case 10: {                        
                        Hearts.notice("10%"+NoiDung);
                        myHearts.thongBaoTroChoiKetThuc(NoiDung);                        
                        ClientS.close();
                        ConnectF.setVisible(true);
                        break;
                    }
                    case 11: {                        
                        Hearts.notice(NoiDung);
                        myHearts.receiveChangeCard(NoiDung);                        
                        break;
                    }
                    case 12: {
                        Hearts.notice(NoiDung);
                        myHearts.nhanChatBaiLuotChoi(NoiDung);
                        break;
                    }
                    case 13: {
                        myHearts.receiveMessage(NoiDung);
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            try {
                ClientS.close();
            } catch (IOException ex1) {
                Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
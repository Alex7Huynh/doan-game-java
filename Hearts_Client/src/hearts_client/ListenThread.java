/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hearts_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mypackage.Hearts;

/**
 *
 * @author Czytelnik
 */
public class ListenThread extends Thread {

    Socket ClientS;
    BufferedReader BuffReader;
    ConnectJFrame ConnectF;
    LoginJFrame LoginF;
    //GameJFrame GameF;
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
                            // ConnectF.setVisible(false);
                        }
                        if (NoiDung.equals("DaDuNguoiChoi")) {
                            JOptionPane.showMessageDialog(new JFrame(), "Hiện đã đủ người chơi. Hẹn bạn lần sau.. ", null, JOptionPane.INFORMATION_MESSAGE);
                            ClientS.close();
                            BuffReader.close();
                            Running = false;
                            ConnectF.setVisible(true);
                        }
                        if (NoiDung.equals("YeuCauUserName")) {
                            //ConnectF.setVisible(false);
                            LoginF = new LoginJFrame(ConnectF);
                            LoginF.setVisible(true);

                        }
                        if (NoiDung.equals("UserNameDaCo")) {
                            JOptionPane.showMessageDialog(new JFrame(), "UserName đã có người dùng. Mời bạn chọn UserName khác. ", null, JOptionPane.INFORMATION_MESSAGE);
                            ConnectF.setVisible(true);
                        }
                        if (NoiDung.startsWith("BatDauChoi")) {
                            LoginF.setVisible(false);
                            //GameF = new GameJFrame(ConnectF);

                            myHearts = new Hearts();
                            myHearts.makeConnection(ConnectF);

                            //GameF.MyName= ConnectF.MyName;
                            //GameF.setPlayerName(0, ConnectF.MyName);
                            myHearts.setPlayerName(0, ConnectF.MyName);
                            //GameF.setTitle(ConnectF.MyName);
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
                                //GameF.YourName[i]=temp[++dem%4];
                                //GameF.setPlayerName(i + 1, temp[++dem % 4]);
                                myHearts.setPlayerName(i + 1, temp[++dem % 4]);
                            }
//                            GameF.LabMy.setText(GameF.MyName);
//                            GameF.LabYou1.setText(GameF.YourName[0]);
//                            GameF.LabYou2.setText(GameF.YourName[1]);
//                            GameF.LabYou3.setText(GameF.YourName[2]);

                            //GameF.setVisible(true);
                            //GameF.myGame.newGame();

                        }
                        break;
                    }
                    case 2: {
                        //GameF.LabMeg.setText(NoiDung);
                        Hearts.notice(NoiDung);
                        myHearts.xuLyKetQuaLuotChuoi(NoiDung);
                        break;
                    }
                    case 3: {
                        //GameF.LabBai.setText(NoiDung);
                        //GameF.createListCard(NoiDung);
                        myHearts.createListCard(NoiDung);
                        myHearts.newGame();

                        break;
                    }
                    case 4: {
                        //GameF.LabMeg.setText(NoiDung);
                        Hearts.notice(NoiDung);
                        myHearts.continueCheckStartNewRound();
                        break;
                    }
                    case 5: {
                        //GameF.LabMeg.setText(NoiDung);
                        //GameF.ToiLuotChoi();
                        Hearts.notice(NoiDung);
                        myHearts.EnableClick(true);
                        break;
                    }
                    case 6: {
                        //GameF.LabMeg.setText(NoiDung);
                        Hearts.notice(NoiDung);
                        myHearts.showPlayCard(NoiDung);
                        break;
                    }
                    case 7: {
                        //GameF.LabMeg.setText(NoiDung);
                        Hearts.notice(NoiDung);
                        myHearts.CoQuyenDiCo(true);
                        break;
                    }
                    case 8: {
                        //GameF.LabMeg.setText(NoiDung);
                        Hearts.notice(NoiDung);
                        myHearts.thongBaoVanChoiKetThuc(NoiDung);
                        break;
                    }
                    case 9: {
                        //GameF.LabMeg.setText(NoiDung);
                        Hearts.notice(NoiDung);
                        break;
                    }
                    case 10: {
                        //GameF.LabMeg.setText(NoiDung);
                        Hearts.notice(NoiDung);
                        break;
                    }
                    case 11: {
                        //GameF.LabMeg.setText(NoiDung);
                        Hearts.notice(NoiDung);
                        myHearts.receiveChangeCard(NoiDung);

                        //myHearts.continuePassCard();
                        myHearts.continueCheckStartNewRound2();
                        myHearts.continueNewRound();

                        break;
                    }
                    case 12: {
                        Hearts.notice(NoiDung);
                        break;
                    }
                    case 13: {
                        myHearts.receiveMessage(NoiDung);
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

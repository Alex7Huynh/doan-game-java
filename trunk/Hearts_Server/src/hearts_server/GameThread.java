/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hearts_server;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Czytelnik
 */
public class GameThread extends Thread {

    public ListenThread ListenT;
    //public boolean Running;
    public String MessageClient[];

    GameThread(ListenThread lt) {
        ListenT = lt;
        MessageClient = new String[4];
        for (int i = 0; i < 4; i++) {
            MessageClient[i] = "";
        }
        this.start();
    }

    public void run() {
        //lần lượt gửi yêu cầu username đến 4 client
        try {
            for (int i = 0; i < 4; i++) {
                ListenT.ListClient[i].BuffWriter.write("1%YeuCauUserName\n");
                ListenT.ListClient[i].BuffWriter.flush();
                boolean TrungTen = true;
                while (TrungTen) {
                    TrungTen = false;
                    while (MessageClient[i].equals("")) {
                        try {
                            this.sleep(2000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    for (int j = 0; j < i; j++) {
                        if (MessageClient[i].equals(ListenT.ListClient[j].Name)) {
                            TrungTen = true;
                            break;
                        }
                    }
                    if (TrungTen) {
                        ListenT.ListClient[i].BuffWriter.write("1%UserNameDaCo\n");
                        ListenT.ListClient[i].BuffWriter.flush();
                    } else {
                        ListenT.ListClient[i].Name = MessageClient[i];
                    }
                    MessageClient[i] = "";
                }
            }
            //lần lượt gửi yêu cầu bắt đầu chơi;
            for (int i = 0; i < 4; i++) {
                ListenT.ListClient[i].BuffWriter.write("1%BatDauChoi%" + ListenT.ListClient[0].Name + "%" + ListenT.ListClient[1].Name + "%" + ListenT.ListClient[2].Name + "%" + ListenT.ListClient[3].Name + "\n");
                ListenT.ListClient[i].BuffWriter.flush();
            }
            // String ListBai[] = new String[4];
            int VanBai = 1;
            int LuotDi = 0;
            int DiemVanHienTai[]=new int[4];
           

            while (ListenT.ListClient[0].TongDiem < 100 && ListenT.ListClient[1].TongDiem < 100 && ListenT.ListClient[2].TongDiem < 100 && ListenT.ListClient[3].TongDiem < 100) {
                for(int i=0;i<4;i++){
                    DiemVanHienTai[i]=0;
                }
                //Chia Bài Cho 4 Client
                String ListBaiClient[][] = new String[4][13];// Danh Sách Bài Của 4 Client
                String ListBai[] = new String[52];
                ListBai = this.XepBai();
                int Dem = 0;
                int STT = 0;
                while (Dem < 52) {
                    for (int i = 0; i < 4; i++) {
                        if (ListBai[Dem].equals("2_Chuon")) {
                            LuotDi = i;
                        }
                        ListBaiClient[i][STT] = ListBai[Dem];
                        Dem++;
                    }
                    STT++;
                }
                String ChuoiBaiGuiClient[] = new String[4];
                for (int i = 0; i < 4; i++) {
                    ChuoiBaiGuiClient[i] = "3";
                    for (int j = 0; j < 13; j++) {
                        ChuoiBaiGuiClient[i] += "%" + ListBaiClient[i][j];
                    }
                }

                //Gui Chuoi Bai Cho 4 Client
                for (int i = 0; i < 4; i++) {
                    ListenT.ListClient[i].BuffWriter.write(ChuoiBaiGuiClient[i]+"\n");
                    ListenT.ListClient[i].BuffWriter.flush();
                }

                //Xem Co Trao Doi Bai Khong
                if (VanBai % 4 != 0) {
                    for (int i = 0; i < 4; i++) {
                        ListenT.ListClient[i].BuffWriter.write("4%TraoDoiBai\n");
                        ListenT.ListClient[i].BuffWriter.flush();
                    }
                    while (MessageClient[0].equals("") || MessageClient[1].equals("") || MessageClient[2].equals("") || MessageClient[3].equals("")) {
                        Thread.sleep(5000);
                    }
                    // kiểm tra xem co client nào lấy 2 chuồn đem trao đổi không.
                    // gán biến Lượt Đi cho client nào giữ 2 chuồn sau khi trao đổi bài
                    if (MessageClient[0].indexOf("%2_Chuon") != -1) {
                        int th = VanBai % 4;
                        switch (th) {
                            case 1:{
                                LuotDi = 1;
                                break;
                            }
                            case 2:{
                                LuotDi = 3;
                                break;
                            }
                            case 3:{
                                LuotDi = 2;
                                break;
                            }
                        }
                    }
                    if (MessageClient[1].indexOf("%2_Chuon") != -1) {
                        int th = VanBai % 4;
                        switch (th) {
                            case 1:{
                                LuotDi = 2;
                                break;
                            }
                            case 2:{
                                LuotDi = 0;
                                break;
                            }
                            case 3:{
                                LuotDi = 3;
                                break;
                            }
                        }
                    }
                    if (MessageClient[2].indexOf("%2_Chuon") != -1) {
                        int th = VanBai % 4;
                        switch (th) {
                            case 1:{
                                LuotDi = 3;
                                break;
                            }
                            case 2:{
                                LuotDi = 1;
                                break;
                            }
                            case 3:{
                                LuotDi = 0;
                                break;
                            }
                        }
                    }
                    if (MessageClient[3].indexOf("%2_Chuon") != -1) {
                        int th = VanBai % 4;
                        switch (th) {
                            case 1:{
                                LuotDi = 0;
                                break;
                            }
                            case 2:{
                                LuotDi = 2;
                                break;
                            }
                            case 3:{
                                LuotDi = 1;
                                break;
                            }
                        }
                    }
                    //Gui bài lại cho client
                    if (VanBai % 4 == 1) {
                        ListenT.ListClient[0].BuffWriter.write("11%" + MessageClient[3] + "\n");
                        ListenT.ListClient[0].BuffWriter.flush();
                        ListenT.ListClient[1].BuffWriter.write("11%" + MessageClient[0] + "\n");
                        ListenT.ListClient[1].BuffWriter.flush();
                        ListenT.ListClient[2].BuffWriter.write("11%" + MessageClient[1] + "\n");
                        ListenT.ListClient[2].BuffWriter.flush();
                        ListenT.ListClient[3].BuffWriter.write("11%" + MessageClient[2] + "\n");
                        ListenT.ListClient[3].BuffWriter.flush();
                    }
                    if (VanBai % 4 == 2) {
                        ListenT.ListClient[0].BuffWriter.write("11%" + MessageClient[1] + "\n");
                        ListenT.ListClient[0].BuffWriter.flush();
                        ListenT.ListClient[1].BuffWriter.write("11%" + MessageClient[2] + "\n");
                        ListenT.ListClient[1].BuffWriter.flush();
                        ListenT.ListClient[2].BuffWriter.write("11%" + MessageClient[3] + "\n");
                        ListenT.ListClient[2].BuffWriter.flush();
                        ListenT.ListClient[3].BuffWriter.write("11%" + MessageClient[0] + "\n");
                        ListenT.ListClient[3].BuffWriter.flush();
                    }
                    if (VanBai % 4 == 3) {
                        ListenT.ListClient[0].BuffWriter.write("11%" + MessageClient[2] + "\n");
                        ListenT.ListClient[0].BuffWriter.flush();
                        ListenT.ListClient[1].BuffWriter.write("11%" + MessageClient[3] + "\n");
                        ListenT.ListClient[1].BuffWriter.flush();
                        ListenT.ListClient[2].BuffWriter.write("11%" + MessageClient[0] + "\n");
                        ListenT.ListClient[2].BuffWriter.flush();
                        ListenT.ListClient[3].BuffWriter.write("11%" + MessageClient[1] + "\n");
                        ListenT.ListClient[3].BuffWriter.flush();
                    }

                    for (int i = 0; i < 4; i++) {
                        MessageClient[i] = "";
                    }
                }
               String LoaiBai = "";
               String BaiClient[] = new String[4];
               boolean DiCo = false;
                for (int i = 0; i < 13; i++) {
                    for (int j = 0; j < 4; j++) {
                        ListenT.ListClient[LuotDi].BuffWriter.write("5%DenLuotDi\n");
                        ListenT.ListClient[LuotDi].BuffWriter.flush();
                        while (MessageClient[LuotDi].equals("")) {
                            Thread.sleep(5000);
                        }
                        BaiClient[LuotDi] = MessageClient[LuotDi];
                        MessageClient[LuotDi] = "";
                        for (int k = 1; k < 4; k++) {
                            ListenT.ListClient[(LuotDi + k) % 4].BuffWriter.write("6%" + ListenT.ListClient[LuotDi].Name + "%" + BaiClient[LuotDi] + "\n");
                            ListenT.ListClient[(LuotDi + k) % 4].BuffWriter.flush();
                        }
                        // nếu là lượt đầu tiên thì lưu lại loại bài để tính điểm
                        if (j == 0) {
                            LoaiBai = BaiClient[LuotDi].substring(BaiClient[LuotDi].indexOf("_") + 1);
                        }
                        if (DiCo == false) {
                            if (BaiClient[LuotDi].substring(BaiClient[LuotDi].indexOf("_") + 1).equals("Co")) {
                                DiCo = true;
                                for (int l = 0; l < 4; l++) {
                                    ListenT.ListClient[l].BuffWriter.write("7%CoQuyenDiCo\n");
                                    ListenT.ListClient[l].BuffWriter.flush();
                                }
                            }
                        }
                        LuotDi = (LuotDi + 1) % 4;
                    }
                   //Kết thúc 1 lượt chơi
                    int BaiMax = 0;
                    int DiemThua = 0;
                    int ClientThua = 0;
                    for (int j = 0; j < 4; j++) {
                        if (BaiClient[j].substring(BaiClient[j].indexOf("_") + 1).equals(LoaiBai)) {
                            int temp = Integer.parseInt(BaiClient[j].substring(0, BaiClient[j].indexOf("_")));
                            if (temp > BaiMax) {
                                BaiMax = temp;
                                ClientThua = j;
                            }
                        }
                        //Tính điểm thua
                        if (BaiClient[j].indexOf("Co") != -1) {
                            DiemThua++;
                        }
                        if (BaiClient[j].equals("12_Bich")) {
                            DiemThua += 13;
                        }
                    }
                    DiemVanHienTai[ClientThua]+=DiemThua;
                    //ListenT.ListClient[ClientThua].Diem += DiemThua;
                    LuotDi = ClientThua;
                    for (int j = 0; j < 4; j++) {
                        ListenT.ListClient[j].BuffWriter.write("2%" + ListenT.ListClient[ClientThua].Name + "%" + DiemVanHienTai[ClientThua] + "\n");
                        ListenT.ListClient[j].BuffWriter.flush();
                    }

                }
               //Kết thúc 1 ván chơi
               // kiểm tra có trường hợp 1 client gom 26 điểm không
               int Client26=-1;
               for(int i=0;i<4;i++)
                   if(DiemVanHienTai[i]==26){
                       Client26=i;
                       break;
                   }

                // Nếu có trường hợp Client26 thì tính diểm lại cho các client ván chơi này.
               if(Client26!=-1){
                   for(int i=0; i<4; i++){
                       if(i!=Client26)
                           DiemVanHienTai[i]=26;
                       else
                           DiemVanHienTai[i]=0;
                   }
               }
                //gui ket quan van choi cho 4 client
                for (int i = 0; i < 4; i++) {
                    ListenT.ListClient[i].BuffWriter.write("10%" + ListenT.ListClient[0].Name + "%" + DiemVanHienTai[0] + "%" + ListenT.ListClient[1].Name + "%" + DiemVanHienTai[1] + "%" + ListenT.ListClient[2].Name + "%" + DiemVanHienTai[2] + "%" + ListenT.ListClient[3].Name + "%" + DiemVanHienTai[3] + "\n");
                    ListenT.ListClient[i].BuffWriter.flush();
                    ListenT.ListClient[i].TongDiem+=DiemVanHienTai[i];
                }

            }// Kết thúc trò chơi
            for (int i = 0; i < 4; i++) {
                    ListenT.ListClient[i].BuffWriter.write("8%" + ListenT.ListClient[0].Name + "%" +  ListenT.ListClient[0].TongDiem + "%" + ListenT.ListClient[1].Name + "%" +  ListenT.ListClient[1].TongDiem + "%" + ListenT.ListClient[2].Name + "%" +  ListenT.ListClient[2].TongDiem + "%" + ListenT.ListClient[3].Name + "%" +  ListenT.ListClient[3].TongDiem + "\n");
                    ListenT.ListClient[i].BuffWriter.flush();
                    ListenT.ListListenClientT[i].stop();
                }

            
        } catch (InterruptedException ex) {
            Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    String[] XepBai() {
        String bai[] = new String[]{"2_Co", "3_Co", "4_Co", "5_Co", "6_Co", "7_Co", "8_Co", "9_Co", "10_Co", "11_Co", "12_Co", "13_Co", "14_Co", "2_Chuon", "3_Chuon", "4_Chuon", "5_Chuon", "6_Chuon", "7_Chuon", "8_Chuon", "9_Chuon", "10_Chuon", "11_Chuon", "12_Chuon", "13_Chuon", "14_Chuon", "2_Ro", "3_Ro", "4_Ro", "5_Ro", "6_Ro", "7_Ro", "8_Ro", "9_Ro", "10_Ro", "11_Ro", "12_Ro", "13_Ro", "14_Ro", "2_Bich", "3_Bich", "4_Bich", "5_Bich", "6_Bich", "7_Bich", "8_Bich", "9_Bich", "10_Bich", "11_Bich", "12_Bich", "13_Bich", "14_Bich"};
        Random random = new Random();
        for (int i = 0; i < 52; i++) {
            int j = random.nextInt(51);
            if (j != i) {
                String temp = bai[i];
                bai[i] = bai[j];
                bai[j] = temp;
            }
        }
        return bai;
    }
}

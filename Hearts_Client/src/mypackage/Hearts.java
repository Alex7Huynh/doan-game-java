package mypackage;

import hearts_client.ConnectJFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class Hearts implements MouseListener, ItemListener {
    //ConnectJFrame để gửi thông điệp cho server
    public static ConnectJFrame ConnectF;
    //Danh sách lá bài
    private ArrayList<Card> myListCard;
    private Sound mySound;
    private JFrame myFrame;    
    private JMenuBar menuBar;
    private JMenu menuGame, menuOption, menuHelp;
    private JMenuItem menuItem;
    //Các JRadioButtonMenuItem để chọn loại bài sấp
    JRadioButtonMenuItem[] rbmBackCard;
    //Danh sách các label chứa quân bài của từng người chơi
    private JLabel[] lblListCardBottom;
    private JLabel[] lblListCardLeft;
    private JLabel[] lblListCardTop;
    private JLabel[] lblListCardRight;
    //Danh sách các label chứa quân bài của người đánh ra
    private JLabel[] lblCenterCardBottom;
    private JLabel[] lblCenterCardLeft;
    private JLabel[] lblCenterCardTop;
    private JLabel[] lblCenterCardRight;
    //Danh sách các label chứa tên người
    private JLabel[] lblPlayerName;
    private Player mainPlayer;
    public ArrayList<String> PlayerName; //D A B C
    public int[] Scores;
    private String txtScore;
    //Vị trí hiện tại lá bài được chọn
    private static int labelIndex = -1;
    private static String chatBaiKiemTra;
    private static boolean chatCo;
    private static boolean luotDau;
    private static boolean CLICK_ENABLE;
    private int CenterCardIndex;
    private int MoveSpeed = 1;
    //xac dinh bat dau mot vong choi moi
    private static boolean startNewRound = false;
    private static JButton newRoundButton;
    //Thành phần nội dung chat
    private static JTextArea txtDisplayMessage, txtSendMessage;
    private static JButton btnSendMessage;
    //Thông báo cho người chơi
    private static JLabel noteLabel;
    //Cờ hiệu đánh dấu lá bài
    static boolean[] flag = new boolean[Player.SOQUANBAI];
    //Vị trí 3 lá đổi
    static ArrayList<Integer> pos = new ArrayList<Integer>(3);
    static boolean get3card = false;
    //Lượt đổi bài
    private int PASS_NUMBER = -1;

    public Hearts() {
        //Khởi tạo điểm số
        Scores = new int[4];
        for (int i = 0; i < 4; ++i) {
            Scores[i] = 0;
        }
        //Cấp phát danh sách 13 lá bài
        myListCard = new ArrayList<Card>();
        
        initPlayer();
        mySound = new Sound();
        //Khởi tạo giao diện
        initFrame();
        initMenu();
        initPlayerNameLabel();
        initCenterCardLabel();
        initPlayerCardLabel();
        initChatField();
        //Init main button
        newRoundButton = new JButton("Start Round");
        newRoundButton.setSize(101, 25);
        newRoundButton.setLocation(340, 425);
        newRoundButton.setBackground(Color.WHITE);
        newRoundButton.setForeground(Color.RED);
        newRoundButton.setVisible(false);
        newRoundButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {                
                startNewRound = true;
            }
        });
        myFrame.add(newRoundButton);
        //Init instuction panel & label
        JPanel myPanel = new JPanel();
        myPanel.setLayout(null);
        myPanel.setBackground(new Color(242, 243, 248));
        myPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        myPanel.setBounds(0, 650, 500, 25);
        myFrame.add(myPanel);

        //noteLabel = new JLabel("*------------------------------------------------------------------------------------------------------------------------------------------------*");
        noteLabel = new JLabel("");
        noteLabel.setSize(myPanel.getSize().width, myPanel.getSize().height);
        noteLabel.setLocation(5, 0);
        myPanel.add(noteLabel);
        //Show frame        
        myFrame.setVisible(true);
    }
    
    /*
     * Xử lý kết quả một lượt chơi - gom bài về người thắng
     * Mã 2
     */
    public void xuLyKetQuaLuotChuoi(String message) {
        //Lưu lại điểm số người thắng lượt chơi
        CenterCardIndex = - 1;
        String Name = message.substring(0, message.indexOf("%"));
        int Diem = Integer.parseInt(message.substring(message.indexOf("%") + 1));
        for (int i = 0; i < 4; ++i) {
            if (Name.equals(PlayerName.get(i))) {
                Scores[i] = Diem;
                break;
            }
        }
        //Di chuyển 4 quân bài về phía người thắng
        move(Name);
        chatBaiKiemTra = "";
    }

    /*
     * Nhận danh sách bài
     * Mã 3
     */
    public void createListCard(String message) {
        String[] lstBai = new String[Card.NUM_OF_FACE];
        lstBai = message.split("%");

        for (int i = 0; i < Card.NUM_OF_FACE; ++i) {
            String[] FaceSuit = lstBai[i].split("_");
            int face = Integer.parseInt(FaceSuit[0]) - 2;
            for (int j = 0; j < Card.NUM_OF_SUIT; ++j) {
                if (Card.Suit[j].equals(FaceSuit[1])) {
                    Card c = new Card(face, j);
                    myListCard.add(c);
                    break;
                }
            }
        }
    }

    /*
     * Thông báo có trao đổi bài - bắt đầu cho trao đổi bài
     * Mã 4
     */
    public void passCard() {
        String txt = "Chọn 3 lá bài để đổi cho ";
        PASS_NUMBER = (++PASS_NUMBER) % 4;

        switch (PASS_NUMBER) {
            case 0:
                txt += PlayerName.get(1);
                newRoundButton.setText("Đổi qua trái");
                break;

            case 1:
                txt += PlayerName.get(3);
                newRoundButton.setText("Đổi qua phải");
                break;

            case 2:
                txt += PlayerName.get(2);
                newRoundButton.setText("Đổi cho đối diện");
                break;
        }
        notice(txt);
        newRoundButton.setVisible(true);

        //Thus hien pass card
        if (PASS_NUMBER >= 0 && PASS_NUMBER <= 2) {
            mainPlayer.passCard(PASS_NUMBER);
        }
    }

    /*
     * Đến lượt đi - được quyền chọn bài
     * Mã 5
     */
    public void playCard() {
        CLICK_ENABLE = true;
        mainPlayer.play(chatCo, luotDau, chatBaiKiemTra);
        //Hiển thị lá bài vừa đánh
        Card c = mainPlayer.getPLayCard();
        CenterCardIndex++;
        mainPlayer.showPlayCardLabel(CenterCardIndex, c);
        //Nếu là người đi đầu tiên thì gán lại chất bài kiểm tra
        if ("".equals(chatBaiKiemTra)) {
            chatBaiKiemTra = Card.Suit[c.getSuit()];
        }
        //Không được click nữa
        CLICK_ENABLE = false;
        //Đánh dấu hết lượt đầu tiên
        if (luotDau) {
            luotDau = false;
        }
        mainPlayer.setIsFirst(false);        
        //Gửi lá bài vừa đánh cho server
        String Message = "1%" + PlayerName.get(0) + "%"
                + mainPlayer.getPLayCard().toString();
        Hearts.notice(Message);
        ConnectF.sendMessage(Message);
        
    }

    /*
     * Hiển thị lá bài một client khác vừa gửi tới
     * Mã 6
     */
    public void showPlayCard(String message) {
        String Name = message.substring(0, message.indexOf("%"));
        String LaBai = message.substring(message.indexOf("%") + 1);
        String path = Card.PICTURES_FOLDER + LaBai + Card.PICTURES_EXTEND;
        //Lá bài đầu tiên trong lượt đi
        if ("".equals(chatBaiKiemTra)) {
            chatBaiKiemTra = LaBai.substring(LaBai.indexOf("_"));
        }
        //Hiển thị lá bài đánh ra - bỏ 1 lá trong danh sách
        CenterCardIndex++;
        if (Name.equals(PlayerName.get(1))) {
            //Hiển thị lá bài nhận được

            lblCenterCardLeft[CenterCardIndex].setIcon(new ImageIcon(path));
            lblCenterCardLeft[CenterCardIndex].setVisible(true);
            //Bỏ đi một lá trong danh sách bài
            Random a = new Random();
            while (true) {
                int b = a.nextInt(13);
                if (lblListCardLeft[b].isVisible()) {
                    lblListCardLeft[b].setVisible(false);
                    break;
                }
            }
        }
        if (Name.equals(PlayerName.get(2))) {
            //Hiển thị lá bài nhận được            
            lblCenterCardTop[CenterCardIndex].setIcon(new ImageIcon(path));
            lblCenterCardTop[CenterCardIndex].setVisible(true);
            //Bỏ đi một lá trong danh sách bài
            Random a = new Random();
            while (true) {
                int b = a.nextInt(13);
                if (lblListCardTop[b].isVisible()) {
                    lblListCardTop[b].setVisible(false);
                    break;
                }
            }
        }
        if (Name.equals(PlayerName.get(3))) {
            //Hiển thị lá bài nhận được            
            lblCenterCardRight[CenterCardIndex].setIcon(new ImageIcon(path));
            lblCenterCardRight[CenterCardIndex].setVisible(true);
            //Bỏ đi một lá trong danh sách bài
            Random a = new Random();
            while (true) {
                int b = a.nextInt(13);
                if (lblListCardRight[b].isVisible()) {
                    lblListCardRight[b].setVisible(false);
                    break;
                }
            }
        }
    }

    /*
     * Cho biết có quyền đi quân cơ
     * Mã 7
     */
    public void CoQuyenDiCo(boolean enable) {
        chatCo = enable;
    }

    /*
     * Thông báo ván chơi kết thúc (sau 13 lượt)
     * Mã 8
     */
    public void thongBaoVanChoiKetThuc(String message) {
        String[] NguoiChoi = new String[4];
        NguoiChoi = message.split("%");
        for (int i = 0; i < 4; ++i) {
            String Name = NguoiChoi[i].substring(0, NguoiChoi[i].indexOf("$"));
            int Diem = Integer.parseInt(NguoiChoi[i].substring(NguoiChoi[i].indexOf("$") + 1));

            for (int j = 0; j < 4; ++j) {
                if (Name.equals(PlayerName.get(j))) {
                    Scores[j] = Diem;
                    break;
                }
            }
        }
        showGameScore("Van choi ket thuc", true);
    }
    
    /*
     * Thông báo khi có client ngừng chơi
     * Mã 9
     */
    public void thongBaoNgungChoi(String message) {
        JOptionPane.showMessageDialog(null, 
                message + " đã ngừng chơi. Trò chơi kết thúc!", 
                "Thong bao", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    /*
     * Xử lý khi trò chơi kết thúc
     * Mã 10
     */
    public void thongBaoTroChoiKetThuc(String message) {
        String[] NguoiChoi = new String[4];
        NguoiChoi = message.split("%");
        for (int i = 0; i < 4; ++i) {
            String Name = NguoiChoi[i].substring(0, NguoiChoi[i].indexOf("$"));
            int Diem = Integer.parseInt(NguoiChoi[i].substring(NguoiChoi[i].indexOf("$") + 1));

            for (int j = 0; j < 4; ++j) {
                if (Name.equals(PlayerName.get(j))) {
                    Scores[j] = Diem;
                    break;
                }
            }
        }
        showGameScore("Tro choi ket thuc", true);
        mySound.playFileSound(Card.SOUND_PLAY_GAMEOVER);
        myFrame.dispose();
        //System.exit(0);        
    }    

    /*
     * Nhận 3 lá bài sau khi đổi
     * Mã 11
     */
    public void receiveChangeCard(String message) {
        //3 lá nhận được
        String[] lstBai = new String[3];
        lstBai = message.split("%");
        //Nhận 3 lá bài và đổi hình label hiển thị
        for (int i = 0; i < 3; ++i) {
            int j = mainPlayer.get3CardPos().get(i).intValue();
            String path = Card.PICTURES_FOLDER + lstBai[i] + Card.PICTURES_EXTEND;
            mainPlayer.getListCardLabel()[j].setIcon(new ImageIcon(path));
            Card c = Card.toCard(lstBai[i]);
            mainPlayer.getListCard().set(j, c);
        }
        //Thông báo
        notice("Nhan OK de bat dau choi.");
        newRoundButton.setText("OK");
        startNewRound = false;
        while (!startNewRound) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Hearts.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Sắp xếp lại danh sách
        mainPlayer.repaintChangeCard();
        newRoundButton.setVisible(false);
        mainPlayer.sortCard();
        //Hiển thị lá bài
        mainPlayer.showListCard(true, 1);
        
        chatCo = false;
        luotDau = true;
        //CLICK_ENABLE = true;
    }

    /*
     * Nhận loại bài kiểm tra của người đi đầu tiên trong lượt
     * Mã 12
     */
    public void nhanChatBaiLuotChoi(String message) {
        chatBaiKiemTra = message;
        /*String tmp = txtDisplayMessage.getText();
        tmp += "Chat bai kiem tra: " + chatBaiKiemTra + "\n";
        tmp += "Đi đầu tiên = " + (mainPlayer.getIsFirst() == true ? "1" : "0" + "\n");
        txtDisplayMessage.setText(tmp);*/
    }

    /*
     * Nhận nội dung chat và hiển thị lên
     * Mã 13
     */
    public void receiveMessage(String message) {
        String Message = txtDisplayMessage.getText() + "\n";
        Message += message;
        txtDisplayMessage.setText(Message);
    }

    public void makeConnection(ConnectJFrame connectJF) {
        ConnectF = new ConnectJFrame();
        ConnectF = connectJF;
    }

    private void setBackCardVisible(JLabel[] label) {
        for (int i = 0; i < label.length; ++i) {
            label[i].setVisible(true);
        }
    }
    
    private void changeBackCardType(int ID, int n) {
                setBackCardLabel(ID+1);
                for(int i = 0; i < n; ++i)
                    if(rbmBackCard[i].isSelected()) {
                        rbmBackCard[i].setSelected(false);
                        break;
                    }
                rbmBackCard[ID].setSelected(true);
            }
    
    public void setBackCardLabel(int type) {
        Card.BACK_PICTURE = "resources/pictures_back/back_" + type + ".png";
        
        for (int i = 0; i < Card.NUM_OF_FACE; ++i) {
            lblListCardLeft[i].setIcon(new ImageIcon(Card.BACK_PICTURE));
            lblListCardTop[i].setIcon(new ImageIcon(Card.BACK_PICTURE));
            lblListCardRight[i].setIcon(new ImageIcon(Card.BACK_PICTURE));
        }
    }

    public void setTitle(String title) {
        myFrame.setTitle(title);
    }

    public void setPlayerName(int ID, String name) {
        //Nếu là người đầu tiên thì hiển thị tên lên tiêu đề
        if (ID == 0) {
            myFrame.setTitle(name);
        }
        //Lưu lại tên và hiển thị lên label
        PlayerName.set(ID, name);
        lblPlayerName[ID].setText(name);
    }    

    private void initPlayerCardLabel() {
        Dimension size = new Dimension(114, 154);
        int dx = 22;
        int dy = 20;
        int MAX_CARD = Player.SOQUANBAI;
        //Bottom's cards
        lblListCardBottom = new JLabel[MAX_CARD];
        for (int i = 0; i < MAX_CARD; i++) {
            lblListCardBottom[i] = new JLabel();
            lblListCardBottom[i].setSize(size);
            lblListCardBottom[i].setLocation(i * dx + 225, 475);
            lblListCardBottom[i].addMouseListener(this);
        }
        for (int i = MAX_CARD - 1; i >= 0; i--) {
            myFrame.add(lblListCardBottom[i]);
        }
        mainPlayer.setListCardLabel(lblListCardBottom);
        //Left's cards
        lblListCardLeft = new JLabel[MAX_CARD];
        for (int i = 0; i < MAX_CARD; i++) {
            lblListCardLeft[i] = new JLabel();
            lblListCardLeft[i].setSize(size);
            lblListCardLeft[i].setLocation(10, i * dy + 135);
            lblListCardLeft[i].setIcon(new ImageIcon(Card.BACK_PICTURE));
        }
        for (int i = MAX_CARD - 1; i >= 0; i--) {
            myFrame.add(lblListCardLeft[i]);
        }
        //Top's cards
        lblListCardTop = new JLabel[MAX_CARD];
        for (int i = 0; i < MAX_CARD; i++) {
            lblListCardTop[i] = new JLabel();
            lblListCardTop[i].setSize(size);
            lblListCardTop[i].setLocation(i * dx + 225, 15);
            lblListCardTop[i].setIcon(new ImageIcon(Card.BACK_PICTURE));
        }
        for (int i = 0; i < MAX_CARD; i++) {
            myFrame.add(lblListCardTop[i]);
        }
        //Right's cards
        lblListCardRight = new JLabel[MAX_CARD];
        for (int i = 0; i < MAX_CARD; i++) {
            lblListCardRight[i] = new JLabel();
            lblListCardRight[i].setSize(size);
            lblListCardRight[i].setLocation(665, i * dy + 135);
            lblListCardRight[i].setIcon(new ImageIcon(Card.BACK_PICTURE));
        }
        for (int i = 0; i < MAX_CARD; i++) {
            myFrame.add(lblListCardRight[i]);
        }
    }

    private void initCenterCardLabel() {
        //Bottom of center card
        lblCenterCardBottom = new JLabel[4];
        for (int i = 0; i < lblCenterCardBottom.length; i++) {
            lblCenterCardBottom[i] = new JLabel();
            lblCenterCardBottom[i].setSize(114, 154);
            lblCenterCardBottom[i].setLocation(360, 280);
            lblCenterCardBottom[i].setVisible(false);
        }
        mainPlayer.setPlayCardLabel(lblCenterCardBottom);
        //Left of center card
        lblCenterCardLeft = new JLabel[4];
        for (int i = 0; i < lblCenterCardLeft.length; i++) {
            lblCenterCardLeft[i] = new JLabel();
            lblCenterCardLeft[i].setSize(114, 154);
            lblCenterCardLeft[i].setLocation(335, 250);
            lblCenterCardLeft[i].setVisible(false);
        }
        //Top of center card
        lblCenterCardTop = new JLabel[4];
        for (int i = 0; i < lblCenterCardTop.length; i++) {
            lblCenterCardTop[i] = new JLabel();
            lblCenterCardTop[i].setSize(114, 154);
            lblCenterCardTop[i].setLocation(360, 215);
            lblCenterCardTop[i].setVisible(false);
        }
        //Right of center card
        lblCenterCardRight = new JLabel[4];
        for (int i = 0; i < lblCenterCardTop.length; i++) {
            lblCenterCardRight[i] = new JLabel();
            lblCenterCardRight[i].setSize(114, 154);
            lblCenterCardRight[i].setLocation(395, 250);
            lblCenterCardRight[i].setVisible(false);
        }

        myFrame.add(lblCenterCardRight[3]);
        myFrame.add(lblCenterCardTop[2]);
        myFrame.add(lblCenterCardLeft[1]);
        myFrame.add(lblCenterCardBottom[0]);

        myFrame.add(lblCenterCardBottom[3]);
        myFrame.add(lblCenterCardRight[2]);
        myFrame.add(lblCenterCardTop[1]);
        myFrame.add(lblCenterCardLeft[0]);

        myFrame.add(lblCenterCardLeft[3]);
        myFrame.add(lblCenterCardBottom[2]);
        myFrame.add(lblCenterCardRight[1]);
        myFrame.add(lblCenterCardTop[0]);

        myFrame.add(lblCenterCardTop[3]);
        myFrame.add(lblCenterCardLeft[2]);
        myFrame.add(lblCenterCardBottom[1]);
        myFrame.add(lblCenterCardRight[0]);
    }

    private void initPlayerNameLabel() {
        lblPlayerName = new JLabel[4];
        //Bottom player's name label
        lblPlayerName[0] = new JLabel(mainPlayer.getName());
        lblPlayerName[0].setFont(new Font("Arial", Font.BOLD, 14));
        lblPlayerName[0].setSize(100, 20);
        lblPlayerName[0].setLocation(150, 600);
        lblPlayerName[0].setForeground(Color.red);
        myFrame.add(lblPlayerName[0]);
        //Left player's name label
        lblPlayerName[1] = new JLabel("B");
        lblPlayerName[1].setFont(new Font("Arial", Font.BOLD, 14));
        lblPlayerName[1].setSize(100, 20);
        lblPlayerName[1].setLocation(40, 100);
        lblPlayerName[1].setForeground(Color.red);
        myFrame.add(lblPlayerName[1]);
        //Top player's name label
        lblPlayerName[2] = new JLabel("C");
        lblPlayerName[2].setFont(new Font("Arial", Font.BOLD, 14));
        lblPlayerName[2].setSize(100, 20);
        lblPlayerName[2].setLocation(640, 40);
        lblPlayerName[2].setForeground(Color.red);
        myFrame.add(lblPlayerName[2]);
        //Right player's name label
        lblPlayerName[3] = new JLabel("D");
        lblPlayerName[3].setFont(new Font("Arial", Font.BOLD, 14));
        lblPlayerName[3].setSize(100, 40);
        lblPlayerName[3].setLocation(680, 540);
        lblPlayerName[3].setForeground(Color.red);
        myFrame.add(lblPlayerName[3]);
    }

    private void initPlayer() {
        PlayerName = new ArrayList<String>(4);
        PlayerName.add("A");
        PlayerName.add("B");
        PlayerName.add("C");
        PlayerName.add("D");

        mainPlayer = new Player(PlayerName.get(0), Player.IS_HUMAN, Player.BOTTOM);
    }

    private void initFrame() {
        myFrame = new JFrame("Hearts Game Online v1.0");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);
        myFrame.setResizable(false);
        myFrame.setSize(1024, 740);
        myFrame.setLocation(0, 0);
        JPanel panelFrame = (JPanel) myFrame.getContentPane();
        panelFrame.setBackground(new Color(102, 153, 0));
    }

    private void initMenu() {
        menuBar = new JMenuBar();
        myFrame.setJMenuBar(menuBar);
        //Menu Game
        menuGame = new JMenu("Game");
        menuGame.setMnemonic('G');
        menuBar.add(menuGame);
        //Menu Item Statistics
        menuItem = new JMenuItem("Statistics");
        menuItem.setMnemonic('S');
        menuGame.add(menuItem);
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                showGameScore(null, false);
            }
        });
        menuGame.addSeparator();
        //Menu Item Exit
        menuItem = new JMenuItem("Exit");
        menuItem.setMnemonic('x');
        menuGame.add(menuItem);
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //Menu Option
        initMenuOption();
        //Menu Help
        menuHelp = new JMenu("Help");
        menuHelp.setMnemonic('H');
        menuBar.add(menuHelp);
        //Menu Item About
        menuItem = new JMenuItem("About...");
        menuItem.setMnemonic('A');
        menuHelp.add(menuItem);
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Sinh Viên:\nTrần Hưng Thuận  0812508.\nPhan Nhật Tiến   0812515.\nHuỳnh Công Toàn   0812527.\n\nGVHD: Nguyễn Văn Khiết.", "Hearts Game", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void initMenuOption() {
        menuOption = new JMenu("Option");
        menuOption.setMnemonic('O');
        menuBar.add(menuOption);
        /*
         * Change card type
         */
        menuItem = new JMenu("Change card type");
        final JRadioButtonMenuItem Type1 = new JRadioButtonMenuItem("Type 1");
        final JRadioButtonMenuItem Type2 = new JRadioButtonMenuItem("Type 2");
        final JRadioButtonMenuItem Type3 = new JRadioButtonMenuItem("Type 3");
        final JRadioButtonMenuItem Type4 = new JRadioButtonMenuItem("Type 4");
        //Type 1
        menuItem.add(Type1);
        menuOption.add(menuItem);
        Type1.setSelected(true);
        Type1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainPlayer.showListCard(true, 1);
                Type1.setSelected(true);
                Type2.setSelected(false);
                Type3.setSelected(false);
                Type4.setSelected(false);
            }
        });
        //Type 2
        menuItem.add(Type2);
        menuOption.add(menuItem);
        Type2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainPlayer.showListCard(true, 2);
                Type1.setSelected(false);
                Type2.setSelected(true);
                Type3.setSelected(false);
                Type4.setSelected(false);
            }
        });
        //Type 3
        menuItem.add(Type3);
        menuOption.add(menuItem);
        Type3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainPlayer.showListCard(true, 3);
                Type1.setSelected(false);
                Type2.setSelected(false);
                Type3.setSelected(true);
                Type4.setSelected(false);
            }
        });
        //Type 4
        menuItem.add(Type4);
        menuOption.add(menuItem);
        Type4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainPlayer.showListCard(true, 4);
                Type1.setSelected(false);
                Type2.setSelected(false);
                Type3.setSelected(false);
                Type4.setSelected(true);
            }
        });
        /*
         * Thay đổi lá sấp         * 
         */
        menuItem = new JMenu("Change back card");
        final int nBackCard = 8;        
        /*final JRadioButtonMenuItem[] */        rbmBackCard = new JRadioButtonMenuItem[nBackCard];
        for (int i = 0; i < nBackCard; ++i) {
            rbmBackCard[i] = new JRadioButtonMenuItem("Type " + (i + 1));
            rbmBackCard[i].addItemListener(this);
            menuItem.add(rbmBackCard[i]);
        }
        
        //Type 1
        rbmBackCard[0].setSelected(true);
        menuOption.add(menuItem);
        rbmBackCard[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
               changeBackCardType(0, nBackCard);
                
            }
        });        
        //Type 2        
        menuOption.add(menuItem);
        rbmBackCard[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                changeBackCardType(1, nBackCard);
            }            
        });
        //Type 3        
        menuOption.add(menuItem);
        rbmBackCard[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                changeBackCardType(2, nBackCard);
            }            
        });
        //Type 4        
        menuOption.add(menuItem);
        rbmBackCard[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                changeBackCardType(3, nBackCard);
            }            
        });
        //Type 5        
        menuOption.add(menuItem);
        rbmBackCard[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                changeBackCardType(4, nBackCard);
            }            
        });        
        //Type 6        
        menuOption.add(menuItem);
        rbmBackCard[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                changeBackCardType(5, nBackCard);
            }            
        });
        //Type 7        
        menuOption.add(menuItem);
        rbmBackCard[6].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                changeBackCardType(6, nBackCard);
            }            
        });
        //Type 8        
        menuOption.add(menuItem);
        rbmBackCard[7].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                changeBackCardType(7, nBackCard);
            }            
        });
        /*
         * Change speed
         */
        menuItem = new JMenu("Change Speed");
        final JRadioButtonMenuItem rbmVeryFast = new JRadioButtonMenuItem("Very fast", true);
        final JRadioButtonMenuItem rbmFast = new JRadioButtonMenuItem("Fast");
        final JRadioButtonMenuItem rbmNormal = new JRadioButtonMenuItem("Normal");
        //Very fast
        menuItem.add(rbmVeryFast);
        menuOption.add(menuItem);
        rbmVeryFast.setSelected(true);
        rbmVeryFast.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MoveSpeed = 1;
                rbmVeryFast.setSelected(true);
                rbmFast.setSelected(false);
                rbmNormal.setSelected(false);
            }
        });
        //Fast
        menuItem.add(rbmFast);
        rbmFast.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MoveSpeed = 10;
                rbmVeryFast.setSelected(false);
                rbmFast.setSelected(true);
                rbmNormal.setSelected(false);
            }
        });
        //Normal
        menuItem.add(rbmNormal);
        menuOption.add(menuItem);
        rbmNormal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MoveSpeed = 16;
                rbmVeryFast.setSelected(false);
                rbmFast.setSelected(false);
                rbmNormal.setSelected(true);
            }
        });
         /*
         * Change theme
         */
        menuItem = new JMenu("Change Theme");
        final JRadioButtonMenuItem rbmTheme1 = new JRadioButtonMenuItem("Green", true);
        final JRadioButtonMenuItem rbmTheme2 = new JRadioButtonMenuItem("Blue");
        final JRadioButtonMenuItem rbmTheme3 = new JRadioButtonMenuItem("Yellow");
        final JRadioButtonMenuItem rbmTheme4 = new JRadioButtonMenuItem("Random");
        //Theme 1
        menuItem.add(rbmTheme1);
        menuOption.add(menuItem);
        rbmTheme1.setSelected(true);
        rbmTheme1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setTheme(1);
                rbmTheme1.setSelected(true);
                rbmTheme2.setSelected(false);
                rbmTheme3.setSelected(false);
                rbmTheme4.setSelected(false);
            }
        });
        //Theme 2
        menuItem.add(rbmTheme2);
        rbmTheme2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setTheme(2);
                rbmTheme1.setSelected(false);
                rbmTheme2.setSelected(true);
                rbmTheme3.setSelected(false);
                rbmTheme4.setSelected(false);
            }
        });
        //Theme 3
        menuItem.add(rbmTheme3);
        menuOption.add(menuItem);
        rbmTheme3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setTheme(3);
                rbmTheme1.setSelected(false);
                rbmTheme2.setSelected(false);
                rbmTheme3.setSelected(true);
                rbmTheme4.setSelected(false);
            }
        });
        //Theme 4
        menuItem.add(rbmTheme4);
        menuOption.add(menuItem);
        rbmTheme4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setTheme(4);
                rbmTheme1.setSelected(false);
                rbmTheme2.setSelected(false);
                rbmTheme3.setSelected(false);
                rbmTheme4.setSelected(true);
            }
        });
    }    
    
    private void initChatField() {
        //Hien thi hang noi dung chat
        txtDisplayMessage = new JTextArea("Nội dung chat\n");
        txtDisplayMessage.setSize(200, 550);
        txtDisplayMessage.setLocation(800, 10);
        txtDisplayMessage.setEditable(false);
        //Text box chat
        txtSendMessage = new JTextArea("Nhập nội dung");
        txtSendMessage.setSize(122, 100);
        txtSendMessage.setLocation(800, 570);
        //Button send noi dung chat
        btnSendMessage = new JButton("CHAT");
        btnSendMessage.setFont(new Font("Arial", Font.BOLD, 12));
        btnSendMessage.setSize(65, 100);
        btnSendMessage.setLocation(930, 570);
        btnSendMessage.setBackground(Color.GRAY);
        btnSendMessage.setForeground(Color.RED);
        btnSendMessage.setVisible(true);
        btnSendMessage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //txtDisplayMessage.setText(pPlayerOne.getName() + ": " + txtSendMessage.getText().trim());
                /*String tmp = txtDisplayMessage.getText() + "Luot dau = " + (mainPlayer.getIsFirst() == true ? "1" : "0");
                txtDisplayMessage.setText(tmp);*/
                Hearts.notice("2%" + txtSendMessage.getText().trim());
                ConnectF.sendMessage("2%" + txtSendMessage.getText().trim());
            }
        });

        myFrame.add(txtDisplayMessage);
        myFrame.add(txtSendMessage);
        myFrame.add(btnSendMessage);
    }

    public void dealCard() {
        for (int i = 0; i < myListCard.size(); ++i) {
            mainPlayer.addACard(myListCard.get(i));
        }

        mainPlayer.showListCard(true, 1);
        mySound.playFileSound(Card.SOUND_PLAY_DEALGAME);
    }
    
    private void setTheme(int ColorID) {
        JPanel panelFrame = (JPanel) myFrame.getContentPane();        
        if(ColorID == 1)
            panelFrame.setBackground(new Color(102, 153, 0));
        else if(ColorID == 2)
            panelFrame.setBackground(new Color(153, 153, 255));
        else if(ColorID == 3)
            panelFrame.setBackground(new Color(255, 204, 0));
        else if(ColorID == 4)
        {
            Random rand = new Random();
            int red = rand.nextInt(255);
            int green = rand.nextInt(255);
            int blue = rand.nextInt(255);
            panelFrame.setBackground(new Color(red, green, blue));
        }
    }

    /*
     * Ghi nhận các thông báo
     */
    public static void notice(String s) {
        if (noteLabel != null && s != null) {
            noteLabel.setText(s);
        }
    }

    /*
     * Choose a card to play
     */
    public static Card chooseACard(Player p) {
        Card c;
        while (true) {
            if (labelIndex != -1) {
                c = p.getListCard().get(labelIndex);
                if (p.isValid(c, chatCo, luotDau, chatBaiKiemTra)) {
                    p.getListCard().set(labelIndex, null);
                    break;
                } else {
                    labelIndex = -1;
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Hearts.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        p.getListCardLabel()[labelIndex].setVisible(false);
        labelIndex = -1;
        return c;
    }

    /*
     * Move card with dx, dy
     */
    public void move(JLabel[] listLabel, int dx, int dy) {
        for (int index = 0; index < listLabel.length; index++) {
            JLabel lbl = listLabel[index];
            for (int i = 0; i < 10; i++) {
                lbl.setLocation(lbl.getLocation().x + dx, lbl.getLocation().y + dy);

                try {
                    Thread.sleep(MoveSpeed);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * Move card to winner
     */
    public void move(String winner) {
        JLabel[] lbl = new JLabel[4];
        for (int i = 0; i < 4; ++i) {
            if (lblCenterCardBottom[i].isVisible()) {
                lbl[0] = lblCenterCardBottom[i];
            }
            if (lblCenterCardLeft[i].isVisible()) {
                lbl[1] = lblCenterCardLeft[i];
            }
            if (lblCenterCardTop[i].isVisible()) {
                lbl[2] = lblCenterCardTop[i];
            }
            if (lblCenterCardRight[i].isVisible()) {
                lbl[3] = lblCenterCardRight[i];
            }
        }
        //Toa do ban dau cua cac label
        Point[] startingPoint = new Point[4];
        for (int i = 0; i < lbl.length; i++) {
            lbl[i].setVisible(true);
            startingPoint[i] = lbl[i].getLocation();
        }
        //Move label
        if (winner.equals(PlayerName.get(0))) {
            move(lbl, 0, 15);
        }
        if (winner.equals(PlayerName.get(1))) {
            move(lbl, -20, 0);
        }
        if (winner.equals(PlayerName.get(2))) {
            move(lbl, 0, -15);
        }
        if (winner.equals(PlayerName.get(3))) {
            move(lbl, 20, 0);
        }
        //Go back
        for (int i = 0; i < lbl.length; i++) {
            lbl[i].setVisible(false);
            lbl[i].setLocation(startingPoint[i]);
        }
    }

    /*
     * Choose three cards to pass to another player
     */
    public static ArrayList<Integer> chooseThreeCards() {
        labelIndex = -1;
        CLICK_ENABLE = true;
        get3card = true;

        for (int i = 0; i < flag.length; i++) {
            flag[i] = false;
        }
        pos.clear();

        newRoundButton.setVisible(true);
        newRoundButton.setEnabled(false);
        startNewRound = false;
        while (!startNewRound) {
            if (pos.size() != 3) {
                newRoundButton.setEnabled(false);
            } else {
                newRoundButton.setEnabled(true);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Hearts.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        labelIndex = -1;
        CLICK_ENABLE = false;
        get3card = false;
        return pos;
    }
    
    public void newRound() {
        CenterCardIndex = -1;
        chatBaiKiemTra = "";
        chatCo = false;
        luotDau = false;

        setBackCardVisible(lblListCardLeft);
        setBackCardVisible(lblListCardTop);
        setBackCardVisible(lblListCardRight);
        mainPlayer.newRound();

        this.dealCard();
    }

    /*
     * Show game score
     */
    public void showGameScore(String s, boolean saved) {
        //notice(s);
        String temp = txtScore;
        temp += Scores[0] + "           ";
        if (Scores[0] < 100) {
            temp += "  ";
        }
        if (Scores[0] < 10) {
            temp += "  ";
        }
        for (int i = 0; i < PlayerName.get(0).length(); ++i) {
            temp += " ";
        }
        temp += Scores[1] + "           ";
        if (Scores[1] < 100) {
            temp += "  ";
        }
        if (Scores[1] < 10) {
            temp += "  ";
        }
        for (int i = 0; i < PlayerName.get(1).length(); ++i) {
            temp += " ";
        }
        temp += Scores[2] + "           ";
        if (Scores[2] < 100) {
            temp += "  ";
        }
        if (Scores[2] < 10) {
            temp += "  ";
        }
        for (int i = 0; i < PlayerName.get(2).length(); ++i) {
            temp += " ";
        }
        temp += Scores[3];
        for (int i = 0; i < PlayerName.get(3).length(); ++i) {
            temp += " ";
        }
        if (saved) {
            txtScore = temp;
        }
        JOptionPane.showMessageDialog(null, temp, s, JOptionPane.INFORMATION_MESSAGE);
    }

    /*
     * New game
     */
    public void newGame() {
        txtScore = PlayerName.get(0) + "                "
                + PlayerName.get(1) + "                "
                + PlayerName.get(2) + "                "
                + PlayerName.get(3) + "\n";
        mainPlayer.newGame();
        this.newRound();
    }
    
    @Override
    public void mouseClicked(MouseEvent arg0) {
        if (!CLICK_ENABLE) {
            Hearts.notice("Chưa đến lượt đi");
        }
        //Đánh dấu label vừa được click
        if (arg0.getSource() == lblListCardBottom[0] && CLICK_ENABLE) {
            labelIndex = 0;
        }
        if (arg0.getSource() == lblListCardBottom[1] && CLICK_ENABLE) {
            labelIndex = 1;
        }
        if (arg0.getSource() == lblListCardBottom[2] && CLICK_ENABLE) {
            labelIndex = 2;
        }
        if (arg0.getSource() == lblListCardBottom[3] && CLICK_ENABLE) {
            labelIndex = 3;
        }
        if (arg0.getSource() == lblListCardBottom[4] && CLICK_ENABLE) {
            labelIndex = 4;
        }
        if (arg0.getSource() == lblListCardBottom[5] && CLICK_ENABLE) {
            labelIndex = 5;
        }
        if (arg0.getSource() == lblListCardBottom[6] && CLICK_ENABLE) {
            labelIndex = 6;
        }
        if (arg0.getSource() == lblListCardBottom[7] && CLICK_ENABLE) {
            labelIndex = 7;
        }
        if (arg0.getSource() == lblListCardBottom[8] && CLICK_ENABLE) {
            labelIndex = 8;
        }
        if (arg0.getSource() == lblListCardBottom[9] && CLICK_ENABLE) {
            labelIndex = 9;
        }
        if (arg0.getSource() == lblListCardBottom[10] && CLICK_ENABLE) {
            labelIndex = 10;
        }
        if (arg0.getSource() == lblListCardBottom[11] && CLICK_ENABLE) {
            labelIndex = 11;
        }
        if (arg0.getSource() == lblListCardBottom[12] && CLICK_ENABLE) {
            labelIndex = 12;
        }
        //Play sound when a card is clicked
        if (0 <= labelIndex && labelIndex <= 12) {            
            mySound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);
        }
        //Choose 3 cards
        if (get3card) {
            if (labelIndex != -1) {
                if (flag[labelIndex] == false) {
                    if (pos.size() < 3) {
                        //Đẩy label lên để phân biệt
                        JLabel lbl = mainPlayer.getListCardLabel()[labelIndex];
                        lbl.setLocation(lbl.getLocation().x, lbl.getLocation().y - 20);
                        //Đánh dấu lá bài được chọn để xét bỏ chọn hay không
                        flag[labelIndex] = true;
                        //Thêm vào danh sách 3 lá
                        pos.add(new Integer(labelIndex));
                    }
                } else {
                    //Đẩy label lên để phân biệt
                    JLabel lbl = mainPlayer.getListCardLabel()[labelIndex];
                    lbl.setLocation(lbl.getLocation().x, lbl.getLocation().y + 20);
                    //Đánh dấu lá bài được chọn để xét bỏ chọn hay không
                    flag[labelIndex] = false;
                    //Bỏ lá này đi trong danh sách 3 lá
                    pos.remove(new Integer(labelIndex));
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        // TODO Auto-generated method stub
        /*if (this.rbmBackCard[0].isSelected()) {
            for(int i = 0; i < 8; ++i) {
                this.rbmBackCard[i].setSelected(false);
            }
            this.rbmBackCard[0].setSelected(true);
        }
        else if (this.rbmBackCard[1].isSelected()) {
            for(int i = 0; i < 8; ++i) {
                this.rbmBackCard[i].setSelected(false);
            }
            this.rbmBackCard[1].setSelected(true);
        }
        else if (this.rbmBackCard[2].isSelected()) {
            for(int i = 0; i < 8; ++i) {
                this.rbmBackCard[i].setSelected(false);
            }
            this.rbmBackCard[2].setSelected(true);
        }*/
    }

    /*
     * Main method
     * (For testing only)
     */
    /*public static void main(String[] args) {
        // TODO Auto-generated method stub
        Hearts myGame = new Hearts();
        myGame.newGame();
        
        myGame.showGameScore("Game Over", false);
        String mes = "A$3%B$3%C$16%D$4";
        myGame.thongBaoVanChoiKetThuc(mes);
        
        myGame.thongBaoTroChoiKetThuc(mes);
    }*/
}
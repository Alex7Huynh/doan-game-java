package mypackage;

import hearts_client.ConnectJFrame;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class Hearts implements MouseListener, ItemListener {

    public static ConnectJFrame ConnectF;
    private static final long serialVersionUID = 1L;
    private ArrayList<Card> myCardList;
    private Sound mySound;
    private JFrame myFrame;
    private JMenuBar menuBar;
    private JMenu menuGame, menuHelp, menuOption;
    private JMenuItem menuItem;
    //private JMenuItem showcard;
    private Checkbox slow, normal, fast;
    //Danh sách các label chứa quân bài của từng người chơi
    private JLabel[] lblListCardBottom;	//cac vi tri quan bai cua HUMAN	  
    private JLabel[] lblListCardLeft;
    private JLabel[] lblListCardTop;
    private JLabel[] lblListCardRight;
    //Danh sách các label chứa quân bài của người đánh ra
    private JLabel[] lblCenterCardBottom;
    private JLabel[] lblCenterCardLeft;
    private JLabel[] lblCenterCardTop;
    private JLabel[] lblCenterCardRight;
    //Danh sách các label chứa tên người
    private JLabel[] lblPlayerName; //Vi tri ten nguoi choi
    private Player pPlayerOne, pPlayerTwo, pPlayerThree, pPlayerFour;	    //4 nguoi choi trong game
    private Player p1 = null, p2 = null, p3 = null, p4 = null;
    public ArrayList<String> PlayerName; //D A B C
    public ArrayList<String> RealPlayerName;    //A B C D
    public int[] Scores;
    private Player firstPlayer;							//nguoi choi dau tien trong moi luot
    private String txtScore;	//String thong bao hien thi diem
    private static int labelIndex = -1;	//vi tri Label la bai duoc chon cua nguoi choi human
    //private static Card kiemtra;//la bai danh ra cua nguoi choi dau tien trong luot
    private static String chatBaiKiemTra = "";
    private Card Card2, Card3, Card4;	//la bai danh ra cua cac nguoi choi tiep theo
    private static boolean chatCo;
    private static boolean luotDau;
    private static boolean CLICK_ENABLE;		//cho phep click len Label la bai cua nguoi choi human
    private final int SHOOT_MOON_SCORE = 26;	//diem so trong truong hop Shoot The Moon
    private final int MAX_SCORE = 100;			//diem so de xac dinh nguoi chien thang 
    //Animation Speed (timePlay, timeMove)
    //fast: (5, 1) normal: (30, 5) slow: (70, 7)
    private int PlaySpeed = 5;
    private int MoveSpeed = 1;
    //xac dinh bat dau mot vong choi moi
    private static boolean startNewRound = false;
    private static JButton newRoundButton;
    //Thành phần nội dung chat
    private static JTextArea txtDisplayMessage, txtSendMessage;
    private static JButton btnSendMessage;
    //Thông báo cho người chơi
    private static JLabel noteLabel;
    static boolean[] flag = new boolean[Player.SOQUANBAI];
    static ArrayList<Integer> pos = new ArrayList<Integer>(3);
    static boolean get3card = false;
    //Luot pass card cua human voi doi thu
    private int PASS_NUMBER = -1;
    //Danh dau thu cac truong hop dac biet
    //0: ko co j
    //1: Human co 12 quan Co va Q bich
    //2: Human la nguoi danh dau tien trong vong choi
    //3: Human la nguoi Shoot The Moon
    private int CASE_FLAG = 0;

    public void nhanChatBaiLuotChoi(String message) {
        chatBaiKiemTra = message;
    }

    public void playCard() {
        pPlayerOne.play(chatCo, luotDau, chatBaiKiemTra);
        //Hiển thị lá bài vừa đánh
        Card c = pPlayerOne.getPLayCard();
        pPlayerOne.showPlayCardLabel(0, c);
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
        //Gửi lá bài vừa đánh cho server
        String Message = "1%" + PlayerName.get(0) + "%"
                + pPlayerOne.getPLayCard().toString();
        Hearts.notice(Message);
        ConnectF.sendMessage(Message);

        /*try {
        Thread.sleep(PlaySpeed);
        } catch (InterruptedException e) {
        e.printStackTrace();
        }*/
    }

    public void xuLyKetQuaMotVanChoi(String message) {
    }

    public void xuLyKetQuaLuotChuoi(String message) {
        //Lưu lại điểm số người thắng lượt chơi
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
    }

    public void thongBaoVanChoiKetThuc(String message) {
        String[] NguoiChoi = new String[4];
        NguoiChoi = message.split("%");
        for (int i = 0; i < 4; ++i) {
            String Name = message.substring(0, NguoiChoi[i].indexOf("$"));
            int Diem = Integer.parseInt(NguoiChoi[i].substring(NguoiChoi[i].indexOf("$") + 1));

            for (int j = 0; j < 4; ++j) {
                if (Name.equals(PlayerName.get(i))) {
                    Scores[i] = Diem;
                    break;
                }
            }
        }
        showGameScore("Van choi ket thuc");
    }
    /*
     * Hiển thị lá bài một client khác vừa gửi tới (mã 6)
     */

    public void showPlayCard(String message) {
        String Name = message.substring(0, message.indexOf("%"));
        String LaBai = message.substring(message.indexOf("%") + 1);
        String path = Card.PICTURES_FOLDER + LaBai + Card.PICTURES_EXTEND;
        //Lá bài đầu tiên trong lượt đi
        if ("".equals(chatBaiKiemTra)) {
            chatBaiKiemTra = LaBai.substring(LaBai.indexOf("_"));
        }

        /*if (Name.equals(PlayerName.get(0))) {
        for (int i = 0; i < 4; ++i) {
        //Hiển thị lá bài nhận được
        lblCardPlay1[i].setIcon(new ImageIcon(path));
        lblCardPlay1[i].setVisible(true);
        //Bỏ đi một lá trong danh sách bài
        Random a = new Random();
        while (true) {
        int b = a.nextInt(13);
        if (lblPlayCardLeft[b].isVisible()) {
        lblPlayCardLeft[b].setVisible(false);
        break;
        }
        }
        }
        }*/
        if (Name.equals(PlayerName.get(1))) {
            //Hiển thị lá bài nhận được
            lblCenterCardLeft[0].setIcon(new ImageIcon(path));
            lblCenterCardLeft[0].setVisible(true);
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
            lblCenterCardTop[0].setIcon(new ImageIcon(path));
            lblCenterCardTop[0].setVisible(true);
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
            lblCenterCardRight[0].setIcon(new ImageIcon(path));
            lblCenterCardRight[0].setVisible(true);
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
     * Nhận 3 lá bài sau khi đổi (mã 11)
     */
    public void receiveChangeCard(String message) {
        //3 lá nhận được
        String[] lstBai = new String[3];
        lstBai = message.split("%");

        //pPlayerOne.clearChangeCard();
        for (int i = 0; i < 3; ++i) {
            int j = pPlayerOne.get3CardPos().get(i).intValue();
            String path = Card.PICTURES_FOLDER + lstBai[i] + Card.PICTURES_EXTEND;
            pPlayerOne.getListCardLabel()[j].setIcon(new ImageIcon(path));
            Card c = Card.toCard(lstBai[i]);
            pPlayerOne.getListCard().set(j, c);
        }
    }

    public void makeConnection(ConnectJFrame connectJF) {
        ConnectF = new ConnectJFrame();
        ConnectF = connectJF;
    }

    public void setTitle(String title) {
        myFrame.setTitle(title);
    }

    public void setPlayerName(int ID, String name) {
        if (ID == 0) {
            myFrame.setTitle(name);
            //pPlayerOne.setName(name);
        } /*else if (ID == 1) {
        //pPlayerTwo.setName(name);
        } else if (ID == 2) {
        //pPlayerThree.setName(name);
        } else if (ID == 3) {
        //pPlayerFour.setName(name);
        }*/

        PlayerName.set(ID, name);
        lblPlayerName[ID].setText(name);
    }

    public String getPlayerName(int ID) {
        if (ID == 0) {
            return pPlayerOne.getName();
        } else if (ID == 1) {
            return pPlayerTwo.getName();
        } else if (ID == 2) {
            return pPlayerThree.getName();
        } else if (ID == 3) {
            return pPlayerFour.getName();
        }

        return "";
    }
    //Constructor

    public Hearts() {
        //Khởi tạo điểm số
        Scores = new int[4];
        for (int i = 0; i < 4; ++i) {
            Scores[i] = 0;
        }        
        
        myCardList = new ArrayList<Card>();
        RealPlayerName = new ArrayList<String>();
        initPlayer();
        initMenu();
        initPlayerNameLabel();
        initCenterCardLabel();
        initPlayerCardLabel();
        initChatField();
        //===============================================================
        //					Start new Round
        //===============================================================
        newRoundButton = new JButton("Start Round");

        newRoundButton.setSize(101, 25);
        //newRoundButton.setToolTipText("Click To Start New Round");
        newRoundButton.setLocation(340, 425);
        newRoundButton.setBackground(Color.WHITE);
        newRoundButton.setForeground(Color.RED);
        newRoundButton.setVisible(false);
        newRoundButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                startNewRound = true;
            }
        });
        myFrame.add(newRoundButton);

        //===============================================================
        //							Note
        //===============================================================
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(242, 243, 248));
        panel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        panel.setBounds(0, 650, 250, 25);
        myFrame.add(panel);

        //noteLabel = new JLabel("*------------------------------------------------------------------------------------------------------------------------------------------------*");
        noteLabel = new JLabel("");
        noteLabel.setSize(panel.getSize().width, panel.getSize().height);
        noteLabel.setLocation(5, 0);
        panel.add(noteLabel);


        //====================================================================
        //							Show Frame
        //====================================================================
        myFrame.setVisible(true);

    }

    private void initPlayerCardLabel() {
        //====================================================================
        //				LABEL OF PLAYER'S CARD
        //====================================================================
        Dimension size = new Dimension(114, 154);
        int dx = 22;
        int dy = 20;
        int MAX_CARD = Player.SOQUANBAI;

        //====================================================================
        //					Bottom's cards
        //====================================================================
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
        pPlayerOne.setListCardLabel(lblListCardBottom);


        //====================================================================
        //					Left's cards
        //====================================================================
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
        //pPlayerTwo.setListCardLabel(lblComputer1);


        //===============================================================
        //					Top's cards
        //===============================================================
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
        //pPlayerThree.setListCardLabel(lblComputer2);


        //===============================================================
        //					Right's cards
        //===============================================================
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
        //pPlayerFour.setListCardLabel(lblComputer3);
    }

    private void initCenterCardLabel() {
        //====================================================================
        //				CARDPLAY'S LABEL
        //====================================================================
        //Bottom of center card
        lblCenterCardBottom = new JLabel[4];
        for (int i = 0; i < lblCenterCardBottom.length; i++) {
            lblCenterCardBottom[i] = new JLabel();
            lblCenterCardBottom[i].setSize(114, 154);
            lblCenterCardBottom[i].setLocation(360, 280);
            lblCenterCardBottom[i].setVisible(false);
        }
        pPlayerOne.setPlayCardLabel(lblCenterCardBottom);

        //Left of center card
        lblCenterCardLeft = new JLabel[4];
        for (int i = 0; i < lblCenterCardLeft.length; i++) {
            lblCenterCardLeft[i] = new JLabel();
            lblCenterCardLeft[i].setSize(114, 154);
            lblCenterCardLeft[i].setLocation(335, 250);
            lblCenterCardLeft[i].setVisible(false);
        }
        //pPlayerTwo.setPlayCardLabel(lbl2);

        //Top of center card
        lblCenterCardTop = new JLabel[4];
        for (int i = 0; i < lblCenterCardTop.length; i++) {
            lblCenterCardTop[i] = new JLabel();
            lblCenterCardTop[i].setSize(114, 154);
            lblCenterCardTop[i].setLocation(360, 215);
            lblCenterCardTop[i].setVisible(false);
        }
        //pPlayerThree.setPlayCardLabel(lbl3);

        //Right of center card
        lblCenterCardRight = new JLabel[4];
        for (int i = 0; i < lblCenterCardTop.length; i++) {
            lblCenterCardRight[i] = new JLabel();
            lblCenterCardRight[i].setSize(114, 154);
            lblCenterCardRight[i].setLocation(395, 250);
            lblCenterCardRight[i].setVisible(false);
        }
        //pPlayerFour.setPlayCardLabel(lbl4);

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
        //====================================================================
        //					 	PLAYER'S NAME LABEL 
        //====================================================================
        lblPlayerName = new JLabel[4];
        //pJara
        lblPlayerName[0] = new JLabel(pPlayerOne.getName());
        lblPlayerName[0].setFont(new Font("Arial", Font.BOLD, 14));
        lblPlayerName[0].setSize(100, 20);
        lblPlayerName[0].setLocation(150, 600);
        lblPlayerName[0].setForeground(Color.red);
        myFrame.add(lblPlayerName[0]);
        //pPauline
        lblPlayerName[1] = new JLabel("B");
        lblPlayerName[1].setFont(new Font("Arial", Font.BOLD, 14));
        lblPlayerName[1].setSize(100, 20);
        lblPlayerName[1].setLocation(40, 100);
        lblPlayerName[1].setForeground(Color.red);
        myFrame.add(lblPlayerName[1]);
        //pMichele
        lblPlayerName[2] = new JLabel("C");
        lblPlayerName[2].setFont(new Font("Arial", Font.BOLD, 14));
        lblPlayerName[2].setSize(100, 20);
        lblPlayerName[2].setLocation(640, 40);
        lblPlayerName[2].setForeground(Color.red);
        myFrame.add(lblPlayerName[2]);
        //pBen
        lblPlayerName[3] = new JLabel("D");
        lblPlayerName[3].setFont(new Font("Arial", Font.BOLD, 14));
        lblPlayerName[3].setSize(100, 40);
        lblPlayerName[3].setLocation(680, 540);
        lblPlayerName[3].setForeground(Color.red);
        myFrame.add(lblPlayerName[3]);
    }

    private void initPlayer() {
        //====================================================================
        //					THIET LAP NGUOI CHOI
        //====================================================================
        PlayerName = new ArrayList<String>(4);
        PlayerName.add("Alice");
        PlayerName.add("Bill");
        PlayerName.add("Cain");
        PlayerName.add("David");

        pPlayerOne = new Player(PlayerName.get(0), Player.IS_HUMAN, Player.BOTTOM);
//        pPlayerTwo = new Player(PlayerName.get(1), Player.IS_HUMAN, Player.LEFT);
//        pPlayerThree = new Player(PlayerName.get(2), Player.IS_HUMAN, Player.TOP);
//        pPlayerFour = new Player(PlayerName.get(3), Player.IS_HUMAN, Player.RIGHT);

//        pPlayerOne.setNextPlayer(pPlayerTwo);
//        pPlayerTwo.setNextPlayer(pPlayerThree);
//        pPlayerThree.setNextPlayer(pPlayerFour);
//        pPlayerFour.setNextPlayer(pPlayerOne);
    }

    private void initMenu() {
        //====================================================================
        //					THIET LAP FRAME
        //====================================================================
        myFrame = new JFrame("Hearts Game Online v1.0");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);
        myFrame.setResizable(false);
        myFrame.setSize(1024, 740);
        myFrame.setLocation(0, 0);
        JPanel panelFrame = (JPanel) myFrame.getContentPane();
        panelFrame.setBackground(new Color(102, 153, 0));


        //====================================================================
        //					 THIET LAP MENU
        //====================================================================
        menuBar = new JMenuBar();
        myFrame.setJMenuBar(menuBar);

        //--------------------------------------------------------------------
        menuGame = new JMenu("Game");
        menuGame.setMnemonic('G');
        menuBar.add(menuGame);

        menuItem = new JMenuItem("Statistics");
        menuItem.setMnemonic('S');
        menuGame.add(menuItem);
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                showGameScore(null);
                //JOptionPane.showMessageDialog(null, txtScore, "Score Sheet", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuGame.addSeparator();	//them dau nhom item

        /*final JMenuItem play = new JMenuItem("Automatic Play");
        play.setMnemonic('A');
        menuGame.add(play);
        play.addActionListener(new ActionListener() {
        
        public void actionPerformed(ActionEvent e) {
        if (pPlayerOne.isHuman()) {
        pPlayerOne.setType(Player.IS_COMPUTER);
        play.setText("Manually");
        play.setMnemonic('M');
        } else {
        pPlayerOne.setType(Player.IS_HUMAN);
        play.setText("Automatic");
        play.setMnemonic('A');
        }
        }
        });*/
        /*
        showcard = new JMenuItem("Show Card");
        showcard.setMnemonic('S');
        menuGame.add(showcard);
        showcard.addActionListener(new ActionListener() {
        
        public void actionPerformed(ActionEvent e) {
        if (showcard.getText().equals("Show Cards")) {
        showcard.setText("Hide Cards");
        showcard.setMnemonic('H');
        pPlayerTwo.showListCard(true);
        pPlayerThree.showListCard(true);
        pPlayerFour.showListCard(true);
        } else {
        showcard.setText("Show Cards");
        showcard.setMnemonic('S');
        pPlayerTwo.showListCard(false);
        pPlayerThree.showListCard(false);
        pPlayerFour.showListCard(false);
        }
        }
        });         
         */
        menuGame.addSeparator();	//them dau nhom item

        menuItem = new JMenuItem("Exit");
        menuItem.setMnemonic('x');
        menuGame.add(menuItem);
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //--------------------------------------------------------------------
        initMenuOption();
        //--------------------------------------------------------------------
        menuHelp = new JMenu("Help");
        menuHelp.setMnemonic('H');
        menuBar.add(menuHelp);

        menuItem = new JMenuItem("About...");
        menuItem.setMnemonic('A');
        menuHelp.add(menuItem);
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Sinh Viên:\nTrần Hưng Thuận  0812508.\nPhan Nhật Tiến   0812515.\nHuỳnh Công Toàn   0812527.\n\nGVHD: Nguyễn Văn Khiết.", "Hearts Game", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //====================================================================
        //					 	OPTIONS
        //====================================================================  
        CheckboxGroup checkGroup;
        //add the checkboxes to the frame 
        checkGroup = new CheckboxGroup();
        fast = new Checkbox("Fast", checkGroup, true);
        fast.addItemListener(this);

        normal = new Checkbox("Normal", checkGroup, false);
        normal.addItemListener(this);

        slow = new Checkbox("Slow", checkGroup, false);
        slow.addItemListener(this);

        Panel north = new Panel();
        north.add(fast);
        north.add(normal);
        north.add(slow);
        menuBar.add(north, BorderLayout.WEST);
    }

    public void initMenuOption() {
        menuOption = new JMenu("Option");
        menuOption.setMnemonic('O');
        menuBar.add(menuOption);
        //--------------------Change Appearance---------------------
        menuItem = new JMenu("Change Appearance");
        JRadioButtonMenuItem Size16 = new JRadioButtonMenuItem("Size 16");
        menuItem.add(Size16);
        //menuItem.addSeparator();
        menuOption.add(menuItem);
        Size16.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                /*_sizeChangeCard = 1;
                _nameDownPlayer.showListLabelCardPlayer(true, _sizeChangeCard);
                _nameTopPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
                _nameRightPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
                _nameLeftPlayer.showListLabelCardPlayer(false, _sizeChangeCard);*/
            }
        });
        JRadioButtonMenuItem Size14 = new JRadioButtonMenuItem("Size 14");
        menuItem.add(Size14);
        //menuItem.addSeparator();
        menuOption.add(menuItem);
        Size14.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                /*_sizeChangeCard = 2;
                _nameDownPlayer.showListLabelCardPlayer(true, _sizeChangeCard);
                _nameTopPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
                _nameRightPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
                _nameLeftPlayer.showListLabelCardPlayer(false, _sizeChangeCard);*/
            }
        });
        JRadioButtonMenuItem Size12 = new JRadioButtonMenuItem("Size 12");
        menuItem.add(Size12);
        //menuItem.addSeparator();
        menuOption.add(menuItem);
        Size12.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                /*_sizeChangeCard = 3;
                _nameDownPlayer.showListLabelCardPlayer(true, _sizeChangeCard);
                _nameTopPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
                _nameRightPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
                _nameLeftPlayer.showListLabelCardPlayer(false, _sizeChangeCard);*/
            }
        });

        JRadioButtonMenuItem Size10 = new JRadioButtonMenuItem("Size 10");
        menuItem.add(Size10);
        //menuItem.addSeparator();
        menuOption.add(menuItem);
        Size10.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                /*_sizeChangeCard = 4;
                _nameDownPlayer.showListLabelCardPlayer(true, _sizeChangeCard);
                _nameTopPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
                _nameRightPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
                _nameLeftPlayer.showListLabelCardPlayer(false, _sizeChangeCard);*/
            }
        });

        //---------------------------------Change Speed--------------
        menuItem = new JMenu("Change Speed");
        JRadioButtonMenuItem menuItem_Quick = new JRadioButtonMenuItem("Quick");
        menuItem.add(menuItem_Quick);
        //menuItem.addSeparator();
        menuOption.add(menuItem);
        menuItem_Quick.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySpeed = 5;
                MoveSpeed = 1;
                /*_speedPlayCard = 10;
                _speedMoveCard = 2;*/
            }
        });
        JRadioButtonMenuItem menuItem_Normal = new JRadioButtonMenuItem("Normal");
        menuItem.add(menuItem_Normal);
        //menuItem.addSeparator();
        menuOption.add(menuItem);
        menuItem_Normal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySpeed = 60;
                MoveSpeed = 10;
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        JRadioButtonMenuItem menuItem_Slow = new JRadioButtonMenuItem("Slow");
        menuItem.add(menuItem_Slow);
        //menuItem.addSeparator();
        menuOption.add(menuItem);
        menuItem_Slow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
                PlaySpeed = 150;
                MoveSpeed = 16;
            }
        });
    }

    //====================================================================
    // 							Chia bai
    //====================================================================
    public void createListCard(String message) {
        String[] lstBai = new String[Card.NUM_OF_FACE];
        lstBai = message.split("%");

        for (int i = 0; i < Card.NUM_OF_FACE; ++i) {
            String[] FaceSuit = lstBai[i].split("_");
            int face = Integer.parseInt(FaceSuit[0]) - 2;
            for (int j = 0; j < Card.NUM_OF_SUIT; ++j) {
                if (Card.Suit[j].equals(FaceSuit[1])) {
                    Card c = new Card(face, j);
                    myCardList.add(c);
                    break;
                }
            }
        }
    }

    public void dealCard() {

        //Neu co xet cac truong hop dac biet
        if (this.CASE_FLAG != 0) {
            special_Deal_Card();
            return;
        }
        //Neu khong xet cac truong hop dac biet
        Card c;
        for (int i = 0; i < myCardList.size(); ++i) {
            pPlayerOne.addACard(myCardList.get(i));
        }

        //========== SHOW CARD ==========		
        pPlayerOne.showListCard(true);
//        pPlayerTwo.showListCard(false);
//        pPlayerThree.showListCard(false);
//        pPlayerFour.showListCard(false);
    }

    //====================================================================
    // 							Chia bai 2
    //====================================================================
    public void special_Deal_Card() {

        ArrayList<Card> CardList = Card.creatListCard();

        //Chia bai cho cac nguoi theo chat
        for (int i = 0; i < CardList.size(); i++) {
            Card c = CardList.get(i);
            //Chia ca quan bai chat Co cho pJara
            if (c.isHeart()) {
                pPlayerOne.addACard(c);
            }

            //Chia ca quan bai chat Ro cho pPauline
            if (c.getSuit() == Card.SUIT_DIAMOND) {
                pPlayerTwo.addACard(c);
            }

            //Chia ca quan bai chat Chuon cho pMichele
            if (c.getSuit() == Card.SUIT_CLUB) {
                pPlayerThree.addACard(c);
            }

            //Chia ca quan bai chat Bich cho pBen
            if (c.getSuit() == Card.SUIT_SPADE) {
                pPlayerFour.addACard(c);
            }
        }

        //========== XEP BAI ==========		
        pPlayerOne.sortCard();
        pPlayerTwo.sortCard();
        pPlayerThree.sortCard();
        pPlayerFour.sortCard();

        switch (this.CASE_FLAG) {
            //Truong hop 1: Human co 12 quan Co va Q bich
            case 1:
                int i1 = 0;
                int i2 = 10;
                Card c1 = pPlayerOne.getListCard().get(i1);
                Card c2 = pPlayerFour.getListCard().get(i2);
                pPlayerOne.getListCard().set(i1, c2);
                pPlayerFour.getListCard().set(i2, c1);
                break;
            //Truong hop 2:	Human la nguoi danh dau tien trong vong choi	
            case 2:
                i1 = 0;
                i2 = 0;
                c1 = pPlayerOne.getListCard().get(i1);
                c2 = pPlayerThree.getListCard().get(i2);
                pPlayerOne.getListCard().set(i1, c2);
                pPlayerThree.getListCard().set(i2, c1);
                break;

            //Truong hop 3: Human la nguoi Shoot The Moon
            case 3:
                for (int i = 0; i < Player.SOQUANBAI; i++) {
                    c1 = pPlayerOne.getListCard().get(i);
                    c2 = pPlayerThree.getListCard().get(i);
                    pPlayerOne.getListCard().set(i, c2);
                    pPlayerThree.getListCard().set(i, c1);
                }
                break;
        }
    }

    //====================================================================
    // 							notice
    //====================================================================
    public static void notice(String s) {
        if (noteLabel != null && s != null) {
            noteLabel.setText(s);
        }
    }

    //====================================================================
    // 							Human Play
    //====================================================================
    public static Card humanPlay(Player p) {
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

    //====================================================================
    // 							findFisrtPlayer
    //====================================================================
    /*public Player findFisrtPlayer() {
    //Neu la luot danh dau tien cua vong, tim nguoi co quan 2 chuon
    if (luotDau) {
    if (pPlayerOne.hasTwoClub()) {
    return pPlayerOne;
    }
    if (pPlayerFour.hasTwoClub()) {
    return pPlayerFour;
    }
    if (pPlayerTwo.hasTwoClub()) {
    return pPlayerTwo;
    }
    if (pPlayerThree.hasTwoClub()) {
    return pPlayerThree;
    }
    } //Neu khong, tim nguoi thang trong luot hien tai 
    else {
    Card maxCard = kiemtra;
    if (Card2.isGreatThan(maxCard)) {
    maxCard = Card2;
    }
    if (Card3.isGreatThan(maxCard)) {
    maxCard = Card3;
    }
    if (Card4.isGreatThan(maxCard)) {
    maxCard = Card4;
    }
    
    if (pPlayerOne.getPLayCard().equals(maxCard)) {
    return pPlayerOne;
    }
    if (pPlayerFour.getPLayCard().equals(maxCard)) {
    return pPlayerFour;
    }
    if (pPlayerTwo.getPLayCard().equals(maxCard)) {
    return pPlayerTwo;
    }
    if (pPlayerThree.getPLayCard().equals(maxCard)) {
    return pPlayerThree;
    }
    }
    return null;
    }*/
    //====================================================================
    //				Di chuyen cac label theo cac do dich dx, dy
    //====================================================================	
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

    //====================================================================
    //					Thao tac di move tong quat
    //====================================================================
    public void move(String winner) {
        JLabel[] lbl = new JLabel[4];
        lbl[0] = lblCenterCardBottom[0];
        lbl[1] = lblCenterCardLeft[0];
        lbl[2] = lblCenterCardTop[0];
        lbl[3] = lblCenterCardRight[0];

        //Toa do ban dau cua cac label
        Point[] point = new Point[4];
        for (int i = 0; i < lbl.length; i++) {
            lbl[i].setVisible(true);
            point[i] = lbl[i].getLocation();
        }

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
        for (int i = 0; i < lbl.length; i++) {
            lbl[i].setVisible(false);
            lbl[i].setLocation(point[i]);
        }
    }

    //====================================================================
    //						whoShootTheMoon
    //====================================================================
    /*public Player whoShootTheMoon() {
    if (pPlayerOne.isShootTheMoon()) {
    return pPlayerOne;
    }
    if (pPlayerFour.isShootTheMoon()) {
    return pPlayerFour;
    }
    if (pPlayerTwo.isShootTheMoon()) {
    return pPlayerTwo;
    }
    if (pPlayerThree.isShootTheMoon()) {
    return pPlayerThree;
    }
    return null;
    }*/
    //====================================================================
    //						Check Shoot The Moon
    //====================================================================
    /*public void checkShootThenMoon() {
    Player p = this.whoShootTheMoon();
    if (p != null) {
    p.addScore(-SHOOT_MOON_SCORE);
    p.getNextPlayer().addScore(SHOOT_MOON_SCORE);
    p.getNextPlayer().getNextPlayer().addScore(SHOOT_MOON_SCORE);
    p.getNextPlayer().getNextPlayer().getNextPlayer().addScore(SHOOT_MOON_SCORE);
    }
    }*/
    //====================================================================
    //						whoIsMinScore
    //====================================================================
    /*public Player whoIsMinScore() {
    Player p = pPlayerOne;
    if (p.getScore() > pPlayerTwo.getScore()) {
    p = pPlayerTwo;
    }
    if (p.getScore() > pPlayerThree.getScore()) {
    p = pPlayerThree;
    }
    if (p.getScore() > pPlayerFour.getScore()) {
    p = pPlayerFour;
    }
    return p;
    }*/
    //====================================================================
    //						whoIs100Score
    //====================================================================
    /*public Player whoIs100Score() {
    if (pPlayerOne.getScore() >= MAX_SCORE) {
    return pPlayerOne;
    }
    if (pPlayerTwo.getScore() >= MAX_SCORE) {
    return pPlayerTwo;
    }
    if (pPlayerThree.getScore() >= MAX_SCORE) {
    return pPlayerThree;
    }
    if (pPlayerFour.getScore() >= MAX_SCORE) {
    return pPlayerFour;
    }
    return null;
    }*/
    //====================================================================
    //						Check 100 Score
    //====================================================================
    /*public boolean check100Score() {
    Player p = this.whoIs100Score();
    if (p != null) {
    Player winner = this.whoIsMinScore();
    txtScore += winner.getName() + " is the winner!";
    //JOptionPane.showMessageDialog(null, txtScore, "Score Sheet", JOptionPane.INFORMATION_MESSAGE);
    this.showGameScore("Score");
    this.newGame();
    return true;
    }
    return false;
    }*/
    //====================================================================
    //						Print The Score
    //====================================================================
    /*public void printTheScore() {
    Player p = pPlayerOne;
    System.out.println(p.getName() + "'s score is " + p.getScore());
    
    p = p.getNextPlayer();
    System.out.println(p.getName() + "'s score is " + p.getScore());
    
    p = p.getNextPlayer();
    System.out.println(p.getName() + "'s score is " + p.getScore());
    
    p = p.getNextPlayer();
    System.out.println(p.getName() + "'s score is " + p.getScore());
    
    System.out.println("=====================================");
    }*/
    //====================================================================
    //							setTxtScore
    //====================================================================
    /*public void setTxtScore() {
    txtScore += "  " + ((pPlayerOne.getScore() < 10) ? ("  " + pPlayerOne.getScore()) : (pPlayerOne.getScore())) + "                ";
    txtScore += (pPlayerTwo.getScore() < 10 ? ("  " + pPlayerTwo.getScore()) : (pPlayerTwo.getScore())) + "                 ";
    txtScore += (pPlayerThree.getScore() < 10 ? ("  " + pPlayerThree.getScore()) : (pPlayerThree.getScore())) + "                ";//
    txtScore += (pPlayerFour.getScore() < 10 ? ("  " + pPlayerFour.getScore()) : (pPlayerFour.getScore())) + "\n";
    }*/
    //====================================================================
    //							Show Game Score
    //====================================================================
    public void showGameScore(String s) {
        notice(s);
        txtScore += "  " + ((Scores[0] < 10) ? ("  " + Scores[0]) : (Scores[0])) + "                ";
        txtScore += (Scores[1] < 10 ? ("  " + Scores[1]) : (Scores[1])) + "                 ";
        txtScore += (Scores[2] < 10 ? ("  " + Scores[2]) : (Scores[2])) + "                ";
        txtScore += (Scores[3] < 10 ? ("  " + Scores[3]) : (Scores[3])) + "\n";
        JOptionPane.showMessageDialog(null, txtScore, "Score Sheet", JOptionPane.INFORMATION_MESSAGE);
    }

    //====================================================================
    //							SCORING
    //====================================================================
    /*public void scoring() {
    //Hien thi danh sach quan bai quan bai an duoc cua 4 nguoi choi
    pPlayerOne.showListScoreCard();
    pPlayerFour.showListScoreCard();
    pPlayerThree.showListScoreCard();
    pPlayerTwo.showListScoreCard();
    
    System.out.printf("\n============= NEW ROUND ============\n");
    
    this.checkShootThenMoon();
    this.setTxtScore();
    if (!this.check100Score()) //JOptionPane.showMessageDialog(null, txtScore, "Score Sheet", JOptionPane.INFORMATION_MESSAGE);
    {
    this.showGameScore("Score");
    }
    }*/
    //
    public static ArrayList<Integer> humanChose3Card() {
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

    public void continueCheckStartNewRound() {
        //Neu khong xet cac truong hop dac biet
        if (this.CASE_FLAG == 0) {

            String txt = "Chon 3 la bai de doi cho ";
            PASS_NUMBER = (++PASS_NUMBER) % 4;

            switch (PASS_NUMBER) {
                case 0:
                    txt += PlayerName.get(1);
                    newRoundButton.setText("Pass Left");
                    break;

                case 1:
                    txt += PlayerName.get(3);
                    newRoundButton.setText("Pass Right");
                    break;

                case 2:
                    txt += PlayerName.get(2);
                    newRoundButton.setText("Pass Cross");
                    break;
            }
            notice(txt);
            newRoundButton.setVisible(true);

            //Thus hien pass card
            if (PASS_NUMBER >= 0 && PASS_NUMBER <= 2) {
                pPlayerOne.passCard(PASS_NUMBER);
            }
        }
    }
    //====================================================================
    // 						Check Start New Round
    //====================================================================

    public void checkStartNewRound() {

        //chia bai
        this.dealCard();

        /*        //Neu khong xet cac truong hop dac biet
        if (this.CASE_FLAG == 0) {
        
        String txt = "Chon 3 la bai de doi cho ";
        PASS_NUMBER = (++PASS_NUMBER) % 4;
        
        switch (PASS_NUMBER) {
        case 0:
        txt += pPlayerTwo.getName();
        newRoundButton.setText("Pass Left");
        break;
        
        case 1:
        txt += pPlayerFour.getName();
        newRoundButton.setText("Pass Right");
        break;
        
        case 2:
        txt += pPlayerThree.getName();
        newRoundButton.setText("Pass Cross");
        break;
        }
        notice(txt);
        newRoundButton.setVisible(true);
        
        //Thus hien pass card
        if (PASS_NUMBER >= 0 && PASS_NUMBER <= 2) {
        pPlayerOne.passCard(PASS_NUMBER);
        
        //                notice("Nhan OK de bat dau choi.");
        //                newRoundButton.setText("OK");
        //
        //                startNewRound = false;
        //                while (!startNewRound) {
        //                }
        //
        //                pPlayerOne.repaintChangeCard();
        //                pPlayerTwo.repaintChangeCard();
        //                pPlayerThree.repaintChangeCard();
        //                pPlayerFour.repaintChangeCard();
        }
        
        //newRoundButton.setVisible(false);
        
        }//if*/

        //Sap xep cac la bai cua 4 nguoi choi
//        pPlayerOne.sortCard();
//        pPlayerTwo.sortCard();
//        pPlayerThree.sortCard();
//        pPlayerFour.sortCard();
    }

    /*public void continuePassCard() {
    pPlayerOne.continuepassCard(PASS_NUMBER);
    }*/
    public void continueCheckStartNewRound2() {
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

        pPlayerOne.repaintChangeCard();
        //pPlayerTwo.repaintChangeCard();
        //pPlayerThree.repaintChangeCard();
        //pPlayerFour.repaintChangeCard();

        newRoundButton.setVisible(false);

        //if

        //Sap xep cac la bai cua 4 nguoi choi
        pPlayerOne.sortCard();
//        pPlayerTwo.sortCard();
//        pPlayerThree.sortCard();
//        pPlayerFour.sortCard();
    }

    public void EnableClick(boolean enable) {
        CLICK_ENABLE = enable;
    }

    public void CoQuyenDiCo(boolean enable) {
        chatCo = enable;
    }

    public void continueNewRound() {
        pPlayerOne.showListCard(true);
        //pPlayerTwo.showListCard(false);
        //pPlayerThree.showListCard(false);
        //pPlayerFour.showListCard(false);

        //showcard.setEnabled(true);

        chatCo = false;
        luotDau = true;
        CLICK_ENABLE = true;

        //nguoi choi dau tien trong vong
        /*firstPlayer = findFisrtPlayer();
        
        for (int i = 0; i < Player.SOQUANBAI; i++) {
        
        p1 = firstPlayer;
        System.out.println(p1.getName() + " is fisrt");
        p2 = p1.getNextPlayer();
        p3 = p2.getNextPlayer();
        p4 = p3.getNextPlayer();
        
        p1.setIsFirst(true);
        p2.setIsFirst(false);
        p3.setIsFirst(false);
        p4.setIsFirst(false);
        
        //firstPlayer danh dau tien
        p1.play(chatCo, luotDau, kiemtra);
        kiemtra = p1.getPLayCard();
        p1.showPlayCardLabel(0, kiemtra);
        if (p1.isHuman()) {
        CLICK_ENABLE = false;
        }
        System.out.println(p1.getName() + kiemtra.toString());
        
        try {
        Thread.sleep(PlaySpeed);
        } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
        
        //luot danh cua nguoi thu 2
        p2.play(chatCo, luotDau, kiemtra);
        Card2 = p2.getPLayCard();
        p2.showPlayCardLabel(1, Card2);
        if (p2.isHuman()) {
        CLICK_ENABLE = false;
        }
        System.out.println(p2.getName() + Card2.toString());
        
        try {
        Thread.sleep(PlaySpeed);
        } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
        
        //luot danh cua nguoi thu 3
        p3.play(chatCo, luotDau, kiemtra);
        Card3 = p3.getPLayCard();
        p3.showPlayCardLabel(2, Card3);
        if (p3.isHuman()) {
        CLICK_ENABLE = false;
        }
        System.out.println(p3.getName() + Card3.toString());
        
        try {
        Thread.sleep(PlaySpeed);
        } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
        
        //luot danh cua nguoi thu 4
        p4.play(chatCo, luotDau, kiemtra);
        Card4 = p4.getPLayCard();
        p4.showPlayCardLabel(3, Card4);
        if (p4.isHuman()) {
        CLICK_ENABLE = false;
        }
        System.out.println(p4.getName() + Card4.toString());*/

        //Danh dau da xong luot dau
//        if (luotDau) {
//            luotDau = false;
//        }
        //Lay chatCo
//            if (!chatCo) {
//                chatCo = kiemtra.isHeart() || Card2.isHeart() || Card3.isHeart() || Card4.isHeart();
//            }

        //Nguoi choi dau tien trong luot tiep theo
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        //Di chuyển 4 lá bài về phía người thắng - người đi đầu tiên lượt kế ti
        //firstPlayer = findFisrtPlayer();
        //move(p1, firstPlayer);

//            firstPlayer.addAScoreCard(kiemtra);
//            firstPlayer.addAScoreCard(Card2);
//            firstPlayer.addAScoreCard(Card3);
//            firstPlayer.addAScoreCard(Card4);

        //Hien thi diem qua tung luot cua nguoi choi
        //printTheScore();

        //kiem tra khoi tao luot moi
        //CLICK_ENABLE = true;
        //}

        //Tinh diem, kiem tra de bat dau vong moi
        //this.scoring();
    }
    //====================================================================
    // 							New Round
    //====================================================================

    public void newRound() {

        //Player p1 = null, p2 = null, p3 = null, p4 = null;

        //showcard.setEnabled(false);
        //showcard.setText("Show Cards");

        pPlayerOne.newRound();
        //pPlayerTwo.newRound();
        //pPlayerThree.newRound();
        //pPlayerFour.newRound();

        this.checkStartNewRound();

//
//        pPlayerOne.showListCard(true);
//        pPlayerTwo.showListCard(false);
//        pPlayerThree.showListCard(false);
//        pPlayerFour.showListCard(false);
//
//        //showcard.setEnabled(true);
//
//        chatCo = false;
//        luotDau = true;
//        CLICK_ENABLE = true;
//
//        //nguoi choi dau tien trong vong
//        firstPlayer = findFisrtPlayer();
//
//        for (int i = 0; i < Player.SOQUANBAI; i++) {
//
//            p1 = firstPlayer;
//            System.out.println(p1.getName() + " is fisrt");
//            p2 = p1.getNextPlayer();
//            p3 = p2.getNextPlayer();
//            p4 = p3.getNextPlayer();
//
//            p1.setIsFirst(true);
//            p2.setIsFirst(false);
//            p3.setIsFirst(false);
//            p4.setIsFirst(false);
//
//            //firstPlayer danh dau tien
//            p1.play(chatCo, luotDau, kiemtra);
//            kiemtra = p1.getPLayCard();
//            p1.showPlayCardLabel(0, kiemtra);
//            if (p1.isHuman()) {
//                CLICK_ENABLE = false;
//            }
//            System.out.println(p1.getName() + kiemtra.toString());
//
//            try {
//                Thread.sleep(PlaySpeed);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            //luot danh cua nguoi thu 2
//            p2.play(chatCo, luotDau, kiemtra);
//            Card2 = p2.getPLayCard();
//            p2.showPlayCardLabel(1, Card2);
//            if (p2.isHuman()) {
//                CLICK_ENABLE = false;
//            }
//            System.out.println(p2.getName() + Card2.toString());
//
//            try {
//                Thread.sleep(PlaySpeed);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            //luot danh cua nguoi thu 3
//            p3.play(chatCo, luotDau, kiemtra);
//            Card3 = p3.getPLayCard();
//            p3.showPlayCardLabel(2, Card3);
//            if (p3.isHuman()) {
//                CLICK_ENABLE = false;
//            }
//            System.out.println(p3.getName() + Card3.toString());
//
//            try {
//                Thread.sleep(PlaySpeed);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            //luot danh cua nguoi thu 4
//            p4.play(chatCo, luotDau, kiemtra);
//            Card4 = p4.getPLayCard();
//            p4.showPlayCardLabel(3, Card4);
//            if (p4.isHuman()) {
//                CLICK_ENABLE = false;
//            }
//            System.out.println(p4.getName() + Card4.toString());
//
//            //Danh dau da xong luot dau
//            if (luotDau) {
//                luotDau = false;
//            }
//            //Lay chatCo
//            if (!chatCo) {
//                chatCo = kiemtra.isHeart() || Card2.isHeart() || Card3.isHeart() || Card4.isHeart();
//            }
//
//            //Nguoi choi dau tien trong luot tiep theo
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            firstPlayer = findFisrtPlayer();
//            move(p1, firstPlayer);
//
//            firstPlayer.addAScoreCard(kiemtra);
//            firstPlayer.addAScoreCard(Card2);
//            firstPlayer.addAScoreCard(Card3);
//            firstPlayer.addAScoreCard(Card4);
//
//            //Hien thi diem qua tung luot cua nguoi choi
//            printTheScore();
//
//            //kiem tra khoi tao luot moi
//            CLICK_ENABLE = true;
//        }
//
//        //Tinh diem, kiem tra de bat dau vong moi
//        this.scoring();
    }

    //====================================================================
    // 							New Game
    //====================================================================
    public void newGame() {
        txtScore = PlayerName.get(0) + "             "
                + PlayerName.get(1) + "             "
                + PlayerName.get(2) + "             "
                + PlayerName.get(3) + "\n";
        pPlayerOne.newGame();
        //pPlayerTwo.newGame();
        //pPlayerThree.newGame();
        //pPlayerFour.newGame();
        //while (true) {
        this.newRound();
        //}
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
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
        if (0 <= labelIndex && labelIndex <= 12) {
            mySound = new Sound();
            mySound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);

            /*if (!get3card) {
            String Message = "1%" + PlayerName.get(0) + "%"
            + pPlayerOne.getListCard().get(labelIndex).toString();
            Hearts.notice(Message);
            ConnectF.sendMessage(Message);
            }*/
        }
        if (get3card) {
            if (labelIndex != -1) {
                if (flag[labelIndex] == false) {
                    if (pos.size() < 3) {
                        //Đẩy label lên để phân biệt
                        JLabel lbl = pPlayerOne.getListCardLabel()[labelIndex];
                        lbl.setLocation(lbl.getLocation().x, lbl.getLocation().y - 20);
                        //Đánh dấu lá bài được chọn để xét bỏ chọn hay không
                        flag[labelIndex] = true;
                        //Thêm vào danh sách 3 lá
                        pos.add(new Integer(labelIndex));
                    }
                } else {
                    //Đẩy label lên để phân biệt
                    JLabel lbl = pPlayerOne.getListCardLabel()[labelIndex];
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
        if (this.fast.getState()) {
            //Fast speed
            this.PlaySpeed = 5;
            this.MoveSpeed = 1;
        }

        if (this.normal.getState()) {
            //Normal speed
            this.PlaySpeed = 30;
            this.MoveSpeed = 5;
        }

        if (this.slow.getState()) {
            //Slow speed
            this.PlaySpeed = 70;
            this.MoveSpeed = 7;
        }
    }

    private void initChatField() {
        //Hien thi hang noi dung chat
        txtDisplayMessage = new JTextArea("Nội dung chat");
        txtDisplayMessage.setSize(200, 550);
        txtDisplayMessage.setLocation(800, 10);

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
                Hearts.notice("2%" + txtSendMessage.getText().trim());
                ConnectF.sendMessage("2%" + txtSendMessage.getText().trim());
            }
        });

        myFrame.add(txtDisplayMessage);
        myFrame.add(txtSendMessage);
        myFrame.add(btnSendMessage);
    }

    public void receiveMessage(String message) {
        String Message = txtDisplayMessage.getText() + "\n";
        Message += message;
        txtDisplayMessage.setText(Message);
    }
    
    //====================================================================
    // 							 Main method
    //====================================================================
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Hearts myGame = new Hearts();
        myGame.newGame();
        
        myGame.showGameScore("Game Over");
    }
}
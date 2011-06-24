package mypackage;

import hearts_client.ConnectJFrame;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
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
    private JLabel[] lblHuman;	//cac vi tri quan bai cua HUMAN	  
    private JLabel[] lblPlayerName; //Vi tri ten nguoi choi
    private Player pPlayerOne, pPlayerTwo, pPlayerThree, pPlayerFour;	    //4 nguoi choi trong game
    private Player p1 = null, p2 = null, p3 = null, p4 = null;
    public ArrayList<String> PlayerName; //D A B C
    public ArrayList<String> RealPlayerName;    //A B C D
    public int[] Scores;
    private Player firstPlayer;							//nguoi choi dau tien trong moi luot
    private String txtScore;	//String thong bao hien thi diem
    private static int labelIndex = -1;	//vi tri Label la bai duoc chon cua nguoi choi human
    private static Card kiemtra;//la bai danh ra cua nguoi choi dau tien trong luot
    private Card Card2, Card3, Card4;	//la bai danh ra cua cac nguoi choi tiep theo
    private static boolean chatCo;
    private static boolean luotDau;
    private static boolean CLICK_ENABLE;		//cho phep click len Label la bai cua nguoi choi human
    private final int SHOOT_MOON_SCORE = 26;	//diem so trong truong hop Shoot The Moon
    private final int MAX_SCORE = 100;			//diem so de xac dinh nguoi chien thang 
    //Animation Speed (timePlay, timeMove)
    //fast: (5, 1) normal: (30, 5) slow: (70, 7)
    private int PlaySpeed = 150;
    private int MoveSpeed = 16;
    //xac dinh bat dau mot vong choi moi
    private static boolean startNewRound = false;
    private static JButton newRoundButton;
    //Thành phần nội dung chat
    private static JTextArea txtDisplayMessage;
    private static JTextField txtSendMessage;
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

    public void xuLyKetQuaMotVanChoi(String message) {
    }

    public void xuLyKetQuaLuotChuoi(String message) {
        String Name = message.substring(0, message.indexOf("%"));
        int Diem = Integer.parseInt(message.substring(message.indexOf("%") + 1));
        for (int i = 0; i < 4; ++i) {
            if (Name.equals(PlayerName.get(i))) {
                Scores[i] = Diem;
                break;
            }
        }
    }

    public void thongBaoVanChoiKetThuc(String message) {
        String[] NguoiChoi = new String[4];
        NguoiChoi = message.split("%");
        for (int i = 0; i < 4; ++i) {
            String Name = message.substring(0, NguoiChoi[i].indexOf("$"));
            int Diem = Integer.parseInt(message.substring(NguoiChoi[i].indexOf("$") + 1));

            for (int j = 0; j < 4; ++j) {
                if (Name.equals(PlayerName.get(i))) {
                    Scores[i] = Diem;
                    break;
                }
            }
        }
        showGameScore("Game Over");
    }

    public void showPlayCard(String message) {
        String Name = message.substring(0, message.indexOf("%"));
        String NoiDung = message.substring(message.indexOf("%") + 1);
        String path = Card.PICTURES_FOLDER + NoiDung + Card.PICTURES_EXTEND;

        if (Name.equals(PlayerName.get(0))) {
            for (int i = 0; i < 4; ++i) {
                lblCardPlay1[i].setIcon(new ImageIcon(path));
                lblCardPlay1[i].setVisible(true);
            }
        }
        if (Name.equals(PlayerName.get(1))) {
            for (int i = 0; i < 4; ++i) {
                lblCardPlay2[i].setIcon(new ImageIcon(path));
                lblCardPlay2[i].setVisible(true);
            }
        }
        if (Name.equals(PlayerName.get(2))) {
            for (int i = 0; i < 4; ++i) {
                lblCardPlay3[i].setIcon(new ImageIcon(path));
                lblCardPlay3[i].setVisible(true);
            }
        }
        if (Name.equals(PlayerName.get(3))) {
            for (int i = 0; i < 4; ++i) {
                lblCardPlay4[i].setIcon(new ImageIcon(path));
                lblCardPlay4[i].setVisible(true);
            }
        }
    }

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
        initCardPlayLabel();
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
        int MAX_CARD = Player.SOQUANBAI + 1;

        //====================================================================
        //					Bottom's cards
        //====================================================================
        lblHuman = new JLabel[MAX_CARD];
        for (int i = 0; i < MAX_CARD; i++) {
            lblHuman[i] = new JLabel();
            lblHuman[i].setSize(size);
            lblHuman[i].setLocation(i * dx + 225, 475);
            lblHuman[i].addMouseListener(this);
        }
        for (int i = MAX_CARD - 1; i >= 0; i--) {
            myFrame.add(lblHuman[i]);
        }
        pPlayerOne.setListCardLabel(lblHuman);


        //====================================================================
        //					Left's cards
        //====================================================================
        JLabel[] lblComputer1 = new JLabel[MAX_CARD];
        for (int i = 0; i < MAX_CARD; i++) {
            lblComputer1[i] = new JLabel();
            lblComputer1[i].setSize(size);
            lblComputer1[i].setLocation(10, i * dy + 135);
            lblComputer1[i].setIcon(new ImageIcon(Card.BACK_PICTURE));
        }
        for (int i = MAX_CARD - 1; i >= 0; i--) {
            myFrame.add(lblComputer1[i]);
        }
        //pPlayerTwo.setListCardLabel(lblComputer1);


        //===============================================================
        //					Top's cards
        //===============================================================
        JLabel[] lblComputer2 = new JLabel[MAX_CARD];
        for (int i = 0; i < MAX_CARD; i++) {
            lblComputer2[i] = new JLabel();
            lblComputer2[i].setSize(size);
            lblComputer2[i].setLocation(i * dx + 225, 15);
            lblComputer2[i].setIcon(new ImageIcon(Card.BACK_PICTURE));
        }
        for (int i = 0; i < MAX_CARD; i++) {
            myFrame.add(lblComputer2[i]);
        }
        //pPlayerThree.setListCardLabel(lblComputer2);


        //===============================================================
        //					Right's cards
        //===============================================================
        JLabel[] lblComputer3 = new JLabel[MAX_CARD];
        for (int i = 0; i < MAX_CARD; i++) {
            lblComputer3[i] = new JLabel();
            lblComputer3[i].setSize(size);
            lblComputer3[i].setLocation(665, i * dy + 135);
            lblComputer3[i].setIcon(new ImageIcon(Card.BACK_PICTURE));
        }
        for (int i = 0; i < MAX_CARD; i++) {
            myFrame.add(lblComputer3[i]);
        }
        //pPlayerFour.setListCardLabel(lblComputer3);
    }
    private JLabel[] lblCardPlay1;
    private JLabel[] lblCardPlay2;
    private JLabel[] lblCardPlay3;
    private JLabel[] lblCardPlay4;

    private void initCardPlayLabel() {
        //====================================================================
        //				CARDPLAY'S LABEL
        //====================================================================
        //QUAN DANH RA CUA pJara
        lblCardPlay1 = new JLabel[4];
        for (int i = 0; i < lblCardPlay1.length; i++) {
            lblCardPlay1[i] = new JLabel();
            lblCardPlay1[i].setSize(114, 154);
            lblCardPlay1[i].setLocation(360, 280);
            lblCardPlay1[i].setVisible(false);
        }
        pPlayerOne.setPlayCardLabel(lblCardPlay1);

        //QUAN DANH RA CUA pPauline
        lblCardPlay2 = new JLabel[4];
        for (int i = 0; i < lblCardPlay2.length; i++) {
            lblCardPlay2[i] = new JLabel();
            lblCardPlay2[i].setSize(114, 154);
            lblCardPlay2[i].setLocation(335, 250);
            lblCardPlay2[i].setVisible(false);
        }
        //pPlayerTwo.setPlayCardLabel(lbl2);

        //QUAN DANH RA CUA pMichele
        lblCardPlay3 = new JLabel[4];
        for (int i = 0; i < lblCardPlay3.length; i++) {
            lblCardPlay3[i] = new JLabel();
            lblCardPlay3[i].setSize(114, 154);
            lblCardPlay3[i].setLocation(360, 215);
            lblCardPlay3[i].setVisible(false);
        }
        //pPlayerThree.setPlayCardLabel(lbl3);

        //QUAN DANH RA CUA pBen   	
        lblCardPlay4 = new JLabel[4];
        for (int i = 0; i < lblCardPlay3.length; i++) {
            lblCardPlay4[i] = new JLabel();
            lblCardPlay4[i].setSize(114, 154);
            lblCardPlay4[i].setLocation(395, 250);
            lblCardPlay4[i].setVisible(false);
        }
        //pPlayerFour.setPlayCardLabel(lbl4);

        myFrame.add(lblCardPlay4[3]);
        myFrame.add(lblCardPlay3[2]);
        myFrame.add(lblCardPlay2[1]);
        myFrame.add(lblCardPlay1[0]);

        myFrame.add(lblCardPlay1[3]);
        myFrame.add(lblCardPlay4[2]);
        myFrame.add(lblCardPlay3[1]);
        myFrame.add(lblCardPlay2[0]);

        myFrame.add(lblCardPlay2[3]);
        myFrame.add(lblCardPlay1[2]);
        myFrame.add(lblCardPlay4[1]);
        myFrame.add(lblCardPlay3[0]);

        myFrame.add(lblCardPlay3[3]);
        myFrame.add(lblCardPlay2[2]);
        myFrame.add(lblCardPlay1[1]);
        myFrame.add(lblCardPlay4[0]);
    }

    private void initPlayerNameLabel() {
        //====================================================================
        //					 	PLAYER'S NAME LABEL 
        //====================================================================
        lblPlayerName = new JLabel[4];
        //pJara
        lblPlayerName[0] = new JLabel(pPlayerOne.getName());
        lblPlayerName[0].setSize(100, 20);
        lblPlayerName[0].setLocation(180, 550);
        lblPlayerName[0].setForeground(Color.red);
        myFrame.add(lblPlayerName[0]);
        //pPauline
        lblPlayerName[1] = new JLabel("B");
        lblPlayerName[1].setSize(100, 20);
        lblPlayerName[1].setLocation(40, 100);
        lblPlayerName[1].setForeground(Color.red);
        myFrame.add(lblPlayerName[1]);
        //pMichele
        lblPlayerName[2] = new JLabel("C");
        lblPlayerName[2].setSize(100, 20);
        lblPlayerName[2].setLocation(620, 40);
        lblPlayerName[2].setForeground(Color.red);
        myFrame.add(lblPlayerName[2]);
        //pBen
        lblPlayerName[3] = new JLabel("D");
        lblPlayerName[3].setSize(100, 20);
        lblPlayerName[3].setLocation(700, 540);
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
        myFrame.setSize(800, 750);
        myFrame.setLocation(150, 0);
        JPanel panelFrame = (JPanel) myFrame.getContentPane();
        panelFrame.setBackground(new Color(80, 120, 60));


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

        final JMenuItem play = new JMenuItem("Automatic Play");
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
        });
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
                if (p.isValid(c, chatCo, luotDau, kiemtra)) {
                    p.getListCard().set(labelIndex, null);
                    break;
                }
            }
        }
        p.getListCardLabel()[labelIndex].setVisible(false);
        labelIndex = -1;
        return c;
    }

    //====================================================================
    // 							findFisrtPlayer
    //====================================================================
    public Player findFisrtPlayer() {
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
    }

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
    public void move(Player p, Player winner) {

        JLabel[] lbl = new JLabel[4];
        lbl[0] = p.getPlayCardLabel()[0];
        lbl[1] = p.getNextPlayer().getPlayCardLabel()[1];
        lbl[2] = p.getNextPlayer().getNextPlayer().getPlayCardLabel()[2];
        lbl[3] = p.getNextPlayer().getNextPlayer().getNextPlayer().getPlayCardLabel()[3];

        //Toa do ban dau cua cac label
        Point[] point = new Point[4];
        for (int i = 0; i < lbl.length; i++) {
            lbl[i].setVisible(true);
            point[i] = lbl[i].getLocation();
        }

        if (winner == pPlayerOne) {
            move(lbl, 0, 15);  	//pJara
        }
        if (winner == pPlayerTwo) {
            move(lbl, -20, 0);  //pPauline
        }
        if (winner == pPlayerThree) {
            move(lbl, 0, -15);  //pMichele
        }
        if (winner == pPlayerFour) {
            move(lbl, 20, 0); 	//pBen
        }
        for (int i = 0; i < lbl.length; i++) {
            lbl[i].setVisible(false);
            lbl[i].setLocation(point[i]);
        }
    }

    //====================================================================
    //						whoShootTheMoon
    //====================================================================
    public Player whoShootTheMoon() {
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
    }

    //====================================================================
    //						Check Shoot The Moon
    //====================================================================
    public void checkShootThenMoon() {
        Player p = this.whoShootTheMoon();
        if (p != null) {
            p.addScore(-SHOOT_MOON_SCORE);
            p.getNextPlayer().addScore(SHOOT_MOON_SCORE);
            p.getNextPlayer().getNextPlayer().addScore(SHOOT_MOON_SCORE);
            p.getNextPlayer().getNextPlayer().getNextPlayer().addScore(SHOOT_MOON_SCORE);
        }
    }

    //====================================================================
    //						whoIsMinScore
    //====================================================================
    public Player whoIsMinScore() {
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
    }

    //====================================================================
    //						whoIs100Score
    //====================================================================
    public Player whoIs100Score() {
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
    }

    //====================================================================
    //						Check 100 Score
    //====================================================================
    public boolean check100Score() {
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
    }

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
    public void setTxtScore() {
        txtScore += "  " + ((pPlayerOne.getScore() < 10) ? ("  " + pPlayerOne.getScore()) : (pPlayerOne.getScore())) + "                ";
        txtScore += (pPlayerTwo.getScore() < 10 ? ("  " + pPlayerTwo.getScore()) : (pPlayerTwo.getScore())) + "                 ";
        txtScore += (pPlayerThree.getScore() < 10 ? ("  " + pPlayerThree.getScore()) : (pPlayerThree.getScore())) + "                ";//
        txtScore += (pPlayerFour.getScore() < 10 ? ("  " + pPlayerFour.getScore()) : (pPlayerFour.getScore())) + "\n";
    }

    //====================================================================
    //							Show Game Score
    //====================================================================
    public void showGameScore(String s) {
        notice(s);
        JOptionPane.showMessageDialog(null, txtScore, "Score Sheet", JOptionPane.INFORMATION_MESSAGE);
    }

    //====================================================================
    //							SCORING
    //====================================================================
    public void scoring() {
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
    }

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
        //pPlayerOne.showListCard(true);
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
        if (luotDau) {
            luotDau = false;
        }
        //Lay chatCo
//            if (!chatCo) {
//                chatCo = kiemtra.isHeart() || Card2.isHeart() || Card3.isHeart() || Card4.isHeart();
//            }

        //Nguoi choi dau tien trong luot tiep theo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        CLICK_ENABLE = true;
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
        txtScore = PlayerName.get(0) + "         "
                + PlayerName.get(1) + "        "
                + PlayerName.get(2) + "         "
                + PlayerName.get(3) + "\n";
        pPlayerOne.newGame();
        //pPlayerTwo.newGame();
        //pPlayerThree.newGame();
        //pPlayerFour.newGame();
        //while (true) {
        this.newRound();
        //}
    }

    //====================================================================
    // 							 Main method
    //====================================================================
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		Hearts myGame = new Hearts();
//		myGame.newGame();
//	}
    @Override
    public void mouseClicked(MouseEvent arg0) {
        //Đánh dấu label vừa được click
        if (arg0.getSource() == lblHuman[0] && CLICK_ENABLE) {
            labelIndex = 0;
        }
        if (arg0.getSource() == lblHuman[1] && CLICK_ENABLE) {
            labelIndex = 1;
        }
        if (arg0.getSource() == lblHuman[2] && CLICK_ENABLE) {
            labelIndex = 2;
        }
        if (arg0.getSource() == lblHuman[3] && CLICK_ENABLE) {
            labelIndex = 3;
        }
        if (arg0.getSource() == lblHuman[4] && CLICK_ENABLE) {
            labelIndex = 4;
        }
        if (arg0.getSource() == lblHuman[5] && CLICK_ENABLE) {
            labelIndex = 5;
        }
        if (arg0.getSource() == lblHuman[6] && CLICK_ENABLE) {
            labelIndex = 6;
        }
        if (arg0.getSource() == lblHuman[7] && CLICK_ENABLE) {
            labelIndex = 7;
        }
        if (arg0.getSource() == lblHuman[8] && CLICK_ENABLE) {
            labelIndex = 8;
        }
        if (arg0.getSource() == lblHuman[9] && CLICK_ENABLE) {
            labelIndex = 9;
        }
        if (arg0.getSource() == lblHuman[10] && CLICK_ENABLE) {
            labelIndex = 10;
        }
        if (arg0.getSource() == lblHuman[11] && CLICK_ENABLE) {
            labelIndex = 11;
        }
        if (arg0.getSource() == lblHuman[12] && CLICK_ENABLE) {
            labelIndex = 12;
        }
        if (0 <= labelIndex && labelIndex <= 12) {
            mySound = new Sound();
            mySound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);
            if (!get3card) {
                String Message = "1%" + PlayerName.get(0) + "%"
                        + pPlayerOne.getListCard().get(labelIndex).toString();
                Hearts.notice(Message);
                ConnectF.sendMessage(Message);
            }
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
        txtDisplayMessage = new JTextArea("Chưa gửi thông tin");
        txtDisplayMessage.setSize(185, 65);
        txtDisplayMessage.setLocation(610, 560);

        //Text box chat
        txtSendMessage = new JTextField("Nhập nội dung");
        txtSendMessage.setSize(122, 25);
        txtSendMessage.setLocation(610, 625);

        //Button send noi dung chat
        btnSendMessage = new JButton("Send");
        btnSendMessage.setSize(63, 25);
        btnSendMessage.setLocation(732, 625);
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
}
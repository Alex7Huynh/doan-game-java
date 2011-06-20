/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import BUS.Card;
import BUS.Heart;
import BUS.Player;
import BUS.Sound;
import DTO.PicturesCard;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author TienPhan
 */
public class PresentationGame implements MouseListener, ItemListener, MouseMotionListener {

   public int dxPictureCard;//Do co theo chieu ngang cua la bai
   public int dyPictureCard;//Do co theo chieu doc cua la bai
   public Sound _playSound = new Sound();// dùng để play một file âm thanh cho người chơi, khi click lên lá bài
   public Card _cardIndexPlaySound;// Xác định con bài nào được phát âm thanh
   public JLabel[] _labelListCardHuman;// Vị trí lá bài của người
   public Player _nameDownPlayer;// Người chơi ở vị trí down
   public Player _nameLeftPlayer;//Người chơi ở vị trí left
   public Player _nameTopPlayer;//Người chơi ở vị trí top
   public Player _nameRightPlayer;//Người chơi ở vị trí right
   public boolean _isRunningGame;// Kiểm tra game đang chạy
   public int FLAG_HUMMAN = 0;// cờ xác định người
   public int _sizeChangeCard = 5;//Kít thướt mặc định của bộ bài là(_sizeChangeCard = 5 có nghĩa là size = 16)
   public static JLabel _noticeLabel;//Thông báo người chơi
   public int _speedPlayCard = 10;// Tốc độ đánh ra quân bài
   public int _speedMoveCard = 2;// Tốc độ di chuyển con bài
   public String _txtNoticeScore;// Chuổi thông báo điểm
   public int _xMouseCurrent, _yMouseCurrent;// Tọa độ di chuyển chuột
   public static int _indexLabelCardHuman = -1;// Vị trí lá bài được chọn của người chơi humman
   public static boolean _isClickLabelHuman;//Cho phép click lên _labelPoisitionNamePlayer của người chơi là humman
   public static boolean _is3Card = false;// có đủ 3 lá bài chưa
   public static boolean[] _flagPlay = new boolean[Player.CARD_PLAYER_QUANTITY];// Cờ hiển thị cho người chơi
   public static ArrayList<Integer> _indexPositon = new ArrayList<Integer>(3);// Vị trí 3 lá bài được chọn cho người chơi
   public static JButton _bntNewRound;// button dùng để chuyển bài
   public JFrame _frameMainGame;// Thiết lập frame chính cho người chơi
   public JPanel _panelFrameMainGame;// Thiết lập panel frame chính chứa frame
   public JPanel _panelNoteMainGame;//Thiết lập panel note để thông báo cho người chơi
   public JMenuBar _menuBarMainGame;// Thiết lập menu bả chứa các menu
   // Game
   public JMenu _menuGame;//Menu "Game"
   public JMenuItem _menuItemGame_NewGame;//MenuItem "NewGame"
   public JMenuItem _menuItemGame_Statistics;// MenuItem "Statistics"
   public JMenuItem _menuItemGame_AutomaticPlay;//MenuItem "Automatic Play"
   public JMenuItem _menuItemGame_ShowCard;// MenuItem "ShowCard"
   public JMenuItem _menuItemGame_Exit; // MenuItem "Exit"
   // Option
   public JMenu _menuOption;// Menu "Option"
   public JMenu _menuItemOption_ChangeAppearance;// Menu "Change Appearacne"
   public JMenuItem _menuItemOption_ChangeAppearance_Size16;// MenuItem "Size 16"
   public JMenuItem _menuItemOption_ChangeAppearance_Size14;// MenuItem "Size 14"
   public JMenuItem _menuItemOption_ChangeAppearance_Size12;//MenuItem "Size 12"
   public JMenuItem _menuItemOption_ChangeAppearance_Size10;// MenuItem "Size 14"
   public JMenu _menuItemOption_ChangeSpeed;//Menu "Change Speed"
   public JMenuItem _menuItemOption_ChangeSpeed_Quick;//MenuItem "Quick"
   public JMenuItem _menuItemOption_ChangeSpeed_Normal;//MenuItem "Normal"
   public JMenuItem _menuItemOption_ChangeSpeed_Slow;//MenuItem "Slow"
   //Help
   public JMenu _menuHelp;// Menu "Help"
   public JMenuItem _menuItemHelp_About;//MenuItem "About"
   //
   public JMenu _menuConnectServer;// Menu "ConnectServer"
   public JMenuItem _menuItemConnectServer_CreatServer;//Tao server
   public JMenuItem _menuItemConnectServer_Connect;// Dùng để connnect server
   public JMenuItem _menuItemConnectServer_DisConnect;//Dùng để disconnect server
   //
   public JLabel[] _labelPoisitionNamePlayer;//The hien ten cua nguoi choi tren mang hinh
   public JLabel[] _labelPositionPictureCardDown;//The hien vi tri la bai duoc danh ra cua nguoi choi down
   public JLabel[] _labelPositionPictureCardLeft;//The hien vi tri la bai duoc danh ra cua nguoi choi left
   public JLabel[] _labelPositionPictureCardTop;//The hien vi tri la bai duoc danh ra cua nguoi choi top
   public JLabel[] _labelPositionPictureCardRight;//The hien vi tri la bai duoc danh ra cua nguoi choi right
   public Dimension _sizePictureCard;// Kít thướt là bài hiển thị lên frame
   public JLabel[] _labelListCardComputerLeft;// Vị trí lá bài của người chơi ở vị trí Left
   public JLabel[] _labelListCardComputerTop;//Vị trí lá bài của người chơi ở vị trí Top
   public JLabel[] _labelListCardComputerRight;//Vị trí lá bài của người chơi ở vị trí Right
   //
   public JButton _bntSendMessage;
   public JTextArea _txtDisplayMessage;
   public JTextField _txtSendMessage;

   /**
    * PresentationGame()
    */
   public PresentationGame() {
   }

   /**
    * Hien thi diem so cho nguoi choi len message
    * showGameScore(String s)
    * @param s
    */
   public void showGameScore(String s) {
      PresentationGame.noticeMessage(s);
      JOptionPane.showMessageDialog(null, _txtNoticeScore, "Curent Game Score", JOptionPane.INFORMATION_MESSAGE);
   }

   /**
    *  noticeMessage(String s)
    * @param s
    */
   public static void // <editor-fold defaultstate="collapsed" desc="comment">
           noticeMessage// </editor-fold>
           (String s) {
      if (_noticeLabel != null && s != null) {
         _noticeLabel.setText(s);
         _noticeLabel.getBaseline(5, 15);
         _noticeLabel.createToolTip();
         _noticeLabel.setForeground(Color.WHITE);
      }
   }

   /**
    *special_Deal_Card()
    */
   public void special_Deal_Card() {

      ArrayList<Card> list52Card = Card.createList52Card();

      //Dau tien chia bai theo type
      for (int indexCardCurrent = 0; indexCardCurrent < list52Card.size(); indexCardCurrent++) {
         Card cardTemp = list52Card.get(indexCardCurrent);
         if (cardTemp.isHeartCard()) {
            _nameDownPlayer.addACardPlayer(cardTemp);//Chua toan quan type co
         }
         if (cardTemp.getType() == Card.getTYPE_CARD_DIAMOND()) {
            _nameLeftPlayer.addACardPlayer(cardTemp);//Chua toan quan type ro
         }
         if (cardTemp.getType() == Card.getTYPE_CARD_CLUB()) {
            _nameTopPlayer.addACardPlayer(cardTemp);//Chua toan quan type chuon
         }
         if (cardTemp.getType() == Card.getTYPE_CARD_SPADE()) {
            _nameRightPlayer.addACardPlayer(cardTemp);//Chua toan quan type bich
         }
      }
      _nameDownPlayer.sortCardPlayer();
      _nameLeftPlayer.sortCardPlayer();
      _nameTopPlayer.sortCardPlayer();
      _nameRightPlayer.sortCardPlayer();

      switch (this.FLAG_HUMMAN) {
         case 1://Nguoi co quan dam bich va 12 quan co
            int indexCardFirst = 0;
            int indexCardEnd = 10;
            Card cardDown = _nameDownPlayer.getListCardPlayer().get(indexCardFirst);
            Card cardRight = _nameRightPlayer.getListCardPlayer().get(indexCardEnd);
            _nameDownPlayer.getListCardPlayer().set(indexCardFirst, cardRight);
            _nameRightPlayer.getListCardPlayer().set(indexCardEnd, cardDown);
            break;
         case 2://La nguoi danh dau tien
            indexCardFirst = 0;
            indexCardEnd = 0;
            cardDown = _nameDownPlayer.getListCardPlayer().get(indexCardFirst);
            cardRight = _nameTopPlayer.getListCardPlayer().get(indexCardEnd);
            _nameDownPlayer.getListCardPlayer().set(indexCardFirst, cardRight);
            _nameTopPlayer.getListCardPlayer().set(indexCardEnd, cardDown);
            break;
         case 3://la nguoi Shoot The Moon
            for (int indexCard = 0; indexCard < Player.CARD_PLAYER_QUANTITY; indexCard++) {
               cardDown = _nameDownPlayer.getListCardPlayer().get(indexCard);
               cardRight = _nameTopPlayer.getListCardPlayer().get(indexCard);
               _nameDownPlayer.getListCardPlayer().set(indexCard, cardRight);
               _nameTopPlayer.getListCardPlayer().set(indexCard, cardDown);
            }
            break;
      }
      _nameDownPlayer.sortCardPlayer();
      _nameLeftPlayer.sortCardPlayer();
      _nameTopPlayer.sortCardPlayer();
      _nameRightPlayer.sortCardPlayer();
   }

   /**
    * Chia lại các lá bài
    *resetDealCard()
    */
   public void resetDealCard() {

      if (this.FLAG_HUMMAN != 0) {
         special_Deal_Card();
         return;
      }
      ArrayList<Card> list52Card = Card.createList52Card();
      Card c;
      for (int i = 0; i < 13; i++) {
         c = list52Card.get(i);
         _nameDownPlayer.removeACardPlayer(i);
         _nameLeftPlayer.removeACardPlayer(i);
         _nameTopPlayer.removeACardPlayer(i);
         _nameRightPlayer.removeACardPlayer(i);
      }
      for (int i = 0; i < 52; i += 4) {
         c = list52Card.get(i);
         _nameDownPlayer.addACardPlayer(c);
         c = list52Card.get(i + 1);
         _nameLeftPlayer.addACardPlayer(c);
         c = list52Card.get(i + 2);
         _nameTopPlayer.addACardPlayer(c);
         c = list52Card.get(i + 3);
         _nameRightPlayer.addACardPlayer(c);
      }
      //Xap xep lai cac la bai, sau khi new game
      _nameDownPlayer.sortCardPlayer();
      _nameLeftPlayer.sortCardPlayer();
      _nameRightPlayer.sortCardPlayer();
      _nameTopPlayer.sortCardPlayer();
      //Hien thi cac la bai cho nguoi choi
      _nameDownPlayer.showListLabelCardPlayer(true, _sizeChangeCard);
      _nameLeftPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
      _nameTopPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
      _nameRightPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
      noticeMessage("Xin chúc mừng. Đã tạo game mới.");
      _playSound.playFileSound(Card.SOUND_PLAY_DEALGAME);
   }

   /**
    *isRunning()
    *Kiểm tra xem một lá bài có đánh ra chưa
    */
   public boolean isRunning() {
      return _isRunningGame;
   }

   /**
    *initNamePlayGame()
    */
   public void initNamePlayGame() {
      //Tạo ra 4 người chơi;
      _nameDownPlayer = new Player("Tien", Player.IS_HUMAN, Player.DOWN_PLAYER);
      _nameLeftPlayer = new Player("Toan", Player.IS_COMPUTER, Player.LEFT_PLAYER);
      _nameTopPlayer = new Player("Thuan", Player.IS_COMPUTER, Player.TOP_PLAYER);
      _nameRightPlayer = new Player("Client 4", Player.IS_COMPUTER, Player.RIGHT_PLAYER);
      //Xét các người chơi tiếp theo
      _nameDownPlayer.setNextPlayer(_nameLeftPlayer);
      _nameLeftPlayer.setNextPlayer(_nameTopPlayer);
      _nameTopPlayer.setNextPlayer(_nameRightPlayer);
      _nameRightPlayer.setNextPlayer(_nameDownPlayer);
   }

   /**
    * Xác định tạo độ của lá bài: chiều dài, chiều rộng của lá bài
    * De hien thi la bai len label theo mot size qui dinh truoc
    *setDimensionLabelCard()
    */
   public void setDimensionLabelCard() {
      _sizePictureCard = new Dimension(114, 154);//kít thướt lớn nhất của lá bài
      dxPictureCard = 22;
      dyPictureCard = 20;
      // Nguời chơi ở vị trí: Down
      _labelListCardHuman = new JLabel[14];
      for (int i = 0; i < 14; i++) {
         _labelListCardHuman[i] = new JLabel();
         _labelListCardHuman[i].setSize(_sizePictureCard);
         _labelListCardHuman[i].setLocation(i * dxPictureCard + 225, 475);
         _labelListCardHuman[i].setVisible(false);
         _labelListCardHuman[i].addMouseListener(this);
      }
      for (int i = 14 - 1; i >= 0; i--) {
         _frameMainGame.add(_labelListCardHuman[i]);
      }
      _nameDownPlayer.setListLabelCardPlayer(_labelListCardHuman);
      // Nguời chơi ở vị trí: Left
      _labelListCardComputerLeft = new JLabel[14];
      for (int i = 0; i < 14; i++) {
         _labelListCardComputerLeft[i] = new JLabel();
         _labelListCardComputerLeft[i].setSize(_sizePictureCard);
         _labelListCardComputerLeft[i].setLocation(10, i * dyPictureCard + 135);
         _labelListCardComputerLeft[i].setVisible(false);
      }
      for (int i = 14 - 1; i >= 0; i--) {
         _frameMainGame.add(_labelListCardComputerLeft[i]);
      }
      _nameLeftPlayer.setListLabelCardPlayer(_labelListCardComputerLeft);
      // Nguời chơi ở vị trí: Top
      _labelListCardComputerTop = new JLabel[14];
      for (int i = 0; i < 14; i++) {
         _labelListCardComputerTop[i] = new JLabel();
         _labelListCardComputerTop[i].setSize(_sizePictureCard);
         _labelListCardComputerTop[i].setLocation(i * dxPictureCard + 225, 15);
         _labelListCardComputerTop[i].setVisible(false);
      }
      for (int i = 0; i < 14; i++) {
         _frameMainGame.add(_labelListCardComputerTop[i]);
      }
      _nameTopPlayer.setListLabelCardPlayer(_labelListCardComputerTop);
      // Nguời chơi ở vị trí: Right
      _labelListCardComputerRight = new JLabel[14];
      for (int i = 0; i < 14; i++) {
         _labelListCardComputerRight[i] = new JLabel();
         _labelListCardComputerRight[i].setSize(_sizePictureCard);
         _labelListCardComputerRight[i].setLocation(665, i * dyPictureCard + 135);
         _labelListCardComputerRight[i].setVisible(false);
      }
      for (int i = 0; i < 14; i++) {
         _frameMainGame.add(_labelListCardComputerRight[i]);
      }
      _nameRightPlayer.setListLabelCardPlayer(_labelListCardComputerRight);
   }

   /**
    * Thể hiện Picture card ở vị trí của người chơi đánh bài
    *setLabelCardPlay()
    */
   public void setLabelCardPlay() {
      // Nguời chơi ở vị trí: down
      _labelPositionPictureCardDown = new JLabel[4];
      for (int i = 0; i < _labelPositionPictureCardDown.length; i++) {
         _labelPositionPictureCardDown[i] = new JLabel();
         _labelPositionPictureCardDown[i].setSize(114, 154);
         _labelPositionPictureCardDown[i].setLocation(360, 280);
         _labelPositionPictureCardDown[i].setVisible(false);
      }
      _nameDownPlayer.setListLabelCardPlayedPlayer(_labelPositionPictureCardDown);
      // Nguời chơi ở vị trí: Left
      _labelPositionPictureCardLeft = new JLabel[4];
      for (int i = 0; i < _labelPositionPictureCardLeft.length; i++) {
         _labelPositionPictureCardLeft[i] = new JLabel();
         _labelPositionPictureCardLeft[i].setSize(114, 154);
         _labelPositionPictureCardLeft[i].setLocation(335, 250);
         _labelPositionPictureCardLeft[i].setVisible(false);
      }
      _nameLeftPlayer.setListLabelCardPlayedPlayer(_labelPositionPictureCardLeft);
      // Nguời chơi ở vị trí: Top
      _labelPositionPictureCardTop = new JLabel[4];
      for (int i = 0; i < _labelPositionPictureCardTop.length; i++) {
         _labelPositionPictureCardTop[i] = new JLabel();
         _labelPositionPictureCardTop[i].setSize(114, 154);
         _labelPositionPictureCardTop[i].setLocation(360, 215);
         _labelPositionPictureCardTop[i].setVisible(false);
      }
      _nameTopPlayer.setListLabelCardPlayedPlayer(_labelPositionPictureCardTop);
      // Nguời chơi ở vị trí: Right
      _labelPositionPictureCardRight = new JLabel[4];
      for (int i = 0; i < _labelPositionPictureCardTop.length; i++) {
         _labelPositionPictureCardRight[i] = new JLabel();
         _labelPositionPictureCardRight[i].setSize(114, 154);
         _labelPositionPictureCardRight[i].setLocation(395, 250);
         _labelPositionPictureCardRight[i].setVisible(false);
      }
      _nameRightPlayer.setListLabelCardPlayedPlayer(_labelPositionPictureCardRight);

      /*Cui bap
      _frameMainGame add tat ca cac vi tri ma nguoi choi danh ra
      Tong cong co 4 * 4 = 16 vi tri duoc add vao
       */
      _frameMainGame.add(_labelPositionPictureCardRight[3]);
      _frameMainGame.add(_labelPositionPictureCardTop[2]);
      _frameMainGame.add(_labelPositionPictureCardLeft[1]);
      _frameMainGame.add(_labelPositionPictureCardDown[0]);
      _frameMainGame.add(_labelPositionPictureCardDown[3]);
      _frameMainGame.add(_labelPositionPictureCardRight[2]);
      _frameMainGame.add(_labelPositionPictureCardTop[1]);
      _frameMainGame.add(_labelPositionPictureCardLeft[0]);
      _frameMainGame.add(_labelPositionPictureCardLeft[3]);
      _frameMainGame.add(_labelPositionPictureCardDown[2]);
      _frameMainGame.add(_labelPositionPictureCardRight[1]);
      _frameMainGame.add(_labelPositionPictureCardTop[0]);
      _frameMainGame.add(_labelPositionPictureCardTop[3]);
      _frameMainGame.add(_labelPositionPictureCardLeft[2]);
      _frameMainGame.add(_labelPositionPictureCardDown[1]);
      _frameMainGame.add(_labelPositionPictureCardRight[0]);


   }

   /**
    * Tên của người chơi ở đâu
    *setLabelNamePlayer()
    */
   public void setLabelNamePlayer() {
      _labelPoisitionNamePlayer = new JLabel[4];
      // Nguời chơi ở vị trí: down
      _labelPoisitionNamePlayer[0] = new JLabel(_nameDownPlayer.getNamePlayer());
      _labelPoisitionNamePlayer[0].setSize(100, 20);
      _labelPoisitionNamePlayer[0].setLocation(180, 550);
      _labelPoisitionNamePlayer[0].setForeground(Color.RED);
      _frameMainGame.add(_labelPoisitionNamePlayer[0]);
      // Nguời chơi ở vị trí: Left
      _labelPoisitionNamePlayer[1] = new JLabel(_nameLeftPlayer.getNamePlayer());
      _labelPoisitionNamePlayer[1].setSize(100, 20);
      _labelPoisitionNamePlayer[1].setLocation(40, 100);
      _labelPoisitionNamePlayer[1].setForeground(Color.RED);
      _frameMainGame.add(_labelPoisitionNamePlayer[1]);
      // Nguời chơi ở vị trí: Top
      _labelPoisitionNamePlayer[2] = new JLabel(_nameTopPlayer.getNamePlayer());
      _labelPoisitionNamePlayer[2].setSize(100, 20);
      _labelPoisitionNamePlayer[2].setLocation(600, 90);
      _labelPoisitionNamePlayer[2].setForeground(Color.RED);
      _frameMainGame.add(_labelPoisitionNamePlayer[2]);
      // Nguời chơi ở vị trí: Right
      _labelPoisitionNamePlayer[3] = new JLabel(_nameRightPlayer.getNamePlayer());
      _labelPoisitionNamePlayer[3].setSize(100, 20);
      _labelPoisitionNamePlayer[3].setLocation(700, 540);
      _labelPoisitionNamePlayer[3].setForeground(Color.RED);
      _frameMainGame.add(_labelPoisitionNamePlayer[3]);
   }

   /**
    * Thiết lập Frame chính cho game
    *setMainFrame()
    */
   public void setMainFrame() throws HeadlessException {
      _frameMainGame = new JFrame();
      _frameMainGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      _frameMainGame.setLayout(null);
      _frameMainGame.setResizable(false);
      _frameMainGame.setSize(800, 700);
      _frameMainGame.setLocation(225, 50);
      _frameMainGame.setTitle("Java Game");
      _panelFrameMainGame = (JPanel) _frameMainGame.getContentPane();
      _panelFrameMainGame.setBackground(new Color(0, 114, 54));
   }

   /**
    * Xét menu bar cho game
    *setMenuBar()
    */
   public void setMenuBar() {
      _menuBarMainGame = new JMenuBar();
      _frameMainGame.setJMenuBar(_menuBarMainGame);
   }

   /**
    *setMenuGame()
    */
   public void setMenuGame() {
      _menuGame = new JMenu("Game");
      _menuGame.setMnemonic('G');
      _menuBarMainGame.add(_menuGame);
      //---------------New Game-------------------------------------
      _menuItemGame_NewGame = new JMenuItem("New Game");
      _menuItemGame_NewGame.setMnemonic('N');
      _menuGame.add(_menuItemGame_NewGame);
      _menuItemGame_NewGame.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            if (isRunning()) {
               Object[] optionsChose = {"OK", "CANCEL"};
               JOptionPane.showOptionDialog(null, "Bạn đang chơi game, không thể tạo game mới, nhấn Ok để tiếp tục chơi.", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, optionsChose, optionsChose[0]);
            } else {
               JOptionPane.showMessageDialog(null, "Bạn có muốn tạo game mới hay không?");
               resetDealCard();
            }
         }
      });
      //---------------Statistics------------------------------------
      _menuItemGame_Statistics = new JMenuItem("Statistics");
      _menuItemGame_Statistics.setMnemonic('S');
      _menuGame.addSeparator();
      _menuGame.add(_menuItemGame_Statistics);
      _menuItemGame_Statistics.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            showGameScore(null);
         }
      });
      //---------------Automatic Play------------------------------
      _menuItemGame_AutomaticPlay = new JMenuItem("Automatic Play");
      _menuItemGame_AutomaticPlay.setMnemonic('A');
      _menuGame.add(_menuItemGame_AutomaticPlay);
      _menuItemGame_AutomaticPlay.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            if (_nameDownPlayer.isHumanPlayer()) {
               _nameDownPlayer.setTypePlayer(Player.IS_COMPUTER);
               _menuItemGame_AutomaticPlay.setText("Manually Play");
               _menuItemGame_AutomaticPlay.setMnemonic('M');
            } else {
               _nameDownPlayer.setTypePlayer(Player.IS_HUMAN);
               _menuItemGame_AutomaticPlay.setText("Automatic Play");
               _menuItemGame_AutomaticPlay.setMnemonic('A');
            }
         }
      });
      //---------------Show Card------------------------------------
      _menuItemGame_ShowCard = new JMenuItem("Show Card");
      _menuItemGame_ShowCard.setMnemonic('S');
      _menuGame.add(_menuItemGame_ShowCard);
      _menuItemGame_ShowCard.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            if (_menuItemGame_ShowCard.getText().equals("Show Card")) {
               _menuItemGame_ShowCard.setText("Hide Card");
               _menuItemGame_ShowCard.setMnemonic('H');
               _nameLeftPlayer.showListLabelCardPlayer(true, _sizeChangeCard);
               _nameTopPlayer.showListLabelCardPlayer(true, _sizeChangeCard);
               _nameRightPlayer.showListLabelCardPlayer(true, _sizeChangeCard);
            } else {
               _menuItemGame_ShowCard.setText("Show Card");
               _menuItemGame_ShowCard.setMnemonic('S');
               _nameLeftPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
               _nameTopPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
               _nameRightPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
            }
         }
      });
      //---------------Exit-----------------------------------------
      _menuItemGame_Exit = new JMenuItem("Exit");
      _menuGame.addSeparator();
      _menuItemGame_Exit.setMnemonic('E');
      _menuGame.add(_menuItemGame_Exit);
      _menuItemGame_Exit.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            ExitGame();
         }
      });
   }

   public void ExitGame() {

      if (JOptionPane.showConfirmDialog(null, "Bạn chắc là muốn thoát game?") == JOptionPane.YES_OPTION) {
         _playSound.playFileSound(Card.SOUND_PLAY_GAMEOVER);
         System.exit(0);
      }
   }

   /**
    * setMenuConnectServer()
    */
   public void setMenuConnectServer() {
      _menuConnectServer = new JMenu("Network");
      _menuConnectServer.setMnemonic('N');
      _menuBarMainGame.add(_menuConnectServer);
      //-------------------------------Create server------------------
      _menuItemConnectServer_CreatServer = new JMenuItem("Create Server");
      _menuItemConnectServer_CreatServer.setEnabled(false);
      _menuItemConnectServer_CreatServer.setMnemonic('R');
      _menuConnectServer.add(_menuItemConnectServer_CreatServer);
      _menuItemConnectServer_CreatServer.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            //  throw new UnsupportedOperationException("Not supported yet.");
            //Su ly connet o day
            JOptionPane.showMessageDialog(null, "Chua su ly?");
         }
      });
      //--------------------------------------Connect------------------
      _menuItemConnectServer_Connect = new JMenuItem("Connect");
      //_menuItemConnectServer_Connect.setEnabled(false);
      _menuItemConnectServer_Connect.setMnemonic('C');
      _menuConnectServer.add(_menuItemConnectServer_Connect);
      _menuItemConnectServer_Connect.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            //  throw new UnsupportedOperationException("Not supported yet.");
            //Su ly connet o day
            JOptionPane.showMessageDialog(null, "Chua su ly?");
         }
      });
      //-----------------------------------Disconnect----------------
      _menuItemConnectServer_DisConnect = new JMenuItem("Disconnect");
      // _menuItemConnectServer_DisConnect.setEnabled(false);
      _menuItemConnectServer_DisConnect.setMnemonic('D');
      _menuConnectServer.add(_menuItemConnectServer_DisConnect);
      _menuItemConnectServer_DisConnect.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            // throw new UnsupportedOperationException("Not supported yet.");
            //Su ly disconnect server
            JOptionPane.showMessageDialog(null, "Chua su ly?");
         }
      });

   }

   /**
    *setMenuHelp()
    */
   public void setMenuHelp() {
      //------------------------------Help-------------------------
      _menuHelp = new JMenu("Help");
      _menuHelp.setMnemonic('H');
      _menuBarMainGame.add(_menuHelp);
      _menuItemHelp_About = new JMenuItem("About");
      _menuItemHelp_About.setMnemonic('A');
      _menuHelp.add(_menuItemHelp_About);
      _menuItemHelp_About.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Sinh Viên:\nTrần Hưng Thuận  0812508.\nPhan Nhật Tiến   0812515.\nHuỳnh Công Toàn   0812527.\nGVHD:\nNguyễn Văn Khiết.", "Hearts Game", JOptionPane.INFORMATION_MESSAGE);
         }
      });
   }

   /**
    *setMenuOption()
    */
   public void setMenuOption() {
      _menuOption = new JMenu("Option");
      _menuOption.setMnemonic('O');
      _menuBarMainGame.add(_menuOption);
      //--------------------Change Appearance---------------------
      _menuItemOption_ChangeAppearance = new JMenu("Change Appearance");
      _menuItemOption_ChangeAppearance_Size16 = new JRadioButtonMenuItem("Size 16");
      _menuItemOption_ChangeAppearance.add(_menuItemOption_ChangeAppearance_Size16);
      _menuItemOption_ChangeAppearance.addSeparator();
      _menuOption.add(_menuItemOption_ChangeAppearance);
      _menuItemOption_ChangeAppearance_Size16.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            _sizeChangeCard = 1;
            _nameDownPlayer.showListLabelCardPlayer(true, _sizeChangeCard);
            _nameTopPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
            _nameRightPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
            _nameLeftPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
         }
      });
      _menuItemOption_ChangeAppearance_Size14 = new JRadioButtonMenuItem("Size 14");
      _menuItemOption_ChangeAppearance.add(_menuItemOption_ChangeAppearance_Size14);
      _menuItemOption_ChangeAppearance.addSeparator();
      _menuOption.add(_menuItemOption_ChangeAppearance);
      _menuItemOption_ChangeAppearance_Size14.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            _sizeChangeCard = 2;
            _nameDownPlayer.showListLabelCardPlayer(true, _sizeChangeCard);
            _nameTopPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
            _nameRightPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
            _nameLeftPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
         }
      });
      _menuItemOption_ChangeAppearance_Size12 = new JRadioButtonMenuItem("Size 12");
      _menuItemOption_ChangeAppearance.add(_menuItemOption_ChangeAppearance_Size12);
      _menuItemOption_ChangeAppearance.addSeparator();
      _menuOption.add(_menuItemOption_ChangeAppearance);
      _menuItemOption_ChangeAppearance_Size12.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            _sizeChangeCard = 3;
            _nameDownPlayer.showListLabelCardPlayer(true, _sizeChangeCard);
            _nameTopPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
            _nameRightPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
            _nameLeftPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
         }
      });

      _menuItemOption_ChangeAppearance_Size10 = new JRadioButtonMenuItem("Size 10");
      _menuItemOption_ChangeAppearance.add(_menuItemOption_ChangeAppearance_Size10);
      _menuItemOption_ChangeAppearance.addSeparator();
      _menuOption.add(_menuItemOption_ChangeAppearance);
      _menuItemOption_ChangeAppearance_Size10.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            _sizeChangeCard = 4;
            _nameDownPlayer.showListLabelCardPlayer(true, _sizeChangeCard);
            _nameTopPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
            _nameRightPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
            _nameLeftPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
         }
      });

      //---------------------------------Change Speed--------------
      _menuItemOption_ChangeSpeed = new JMenu("Change Speed");
      _menuItemOption_ChangeSpeed_Quick = new JRadioButtonMenuItem("Quick");
      _menuItemOption_ChangeSpeed.add(_menuItemOption_ChangeSpeed_Quick);
      _menuItemOption_ChangeSpeed.addSeparator();
      _menuOption.add(_menuItemOption_ChangeSpeed);
      _menuItemOption_ChangeSpeed_Quick.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            _speedPlayCard = 10;
            _speedMoveCard = 2;
         }
      });
      _menuItemOption_ChangeSpeed_Normal = new JRadioButtonMenuItem("Normal");
      _menuItemOption_ChangeSpeed.add(_menuItemOption_ChangeSpeed_Normal);
      _menuItemOption_ChangeSpeed.addSeparator();
      _menuOption.add(_menuItemOption_ChangeSpeed);
      _menuItemOption_ChangeSpeed_Normal.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            _speedPlayCard = 60;
            _speedPlayCard = 10;
            //throw new UnsupportedOperationException("Not supported yet.");
         }
      });
      _menuItemOption_ChangeSpeed_Slow = new JRadioButtonMenuItem("Slow");
      _menuItemOption_ChangeSpeed.add(_menuItemOption_ChangeSpeed_Slow);
      _menuItemOption_ChangeSpeed.addSeparator();
      _menuOption.add(_menuItemOption_ChangeSpeed);
      _menuItemOption_ChangeSpeed_Slow.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
            _speedPlayCard = 150;
            _speedMoveCard = 16;
         }
      });
   }

   /**
    *setPanelNote()
    */
   public void setPanelNote() {
      _panelNoteMainGame = new JPanel();
      _panelNoteMainGame.setLayout(null);
      _panelNoteMainGame.setBackground(Color.GRAY);
      _panelNoteMainGame.setBorder(new EtchedBorder(EtchedBorder.RAISED));
      _panelNoteMainGame.setBounds(0, 590, 200, 60);
      _frameMainGame.add(_panelNoteMainGame);
      Heart._noticeLabel = new JLabel("");
      Heart._noticeLabel.setSize(_panelNoteMainGame.getSize().width, _panelNoteMainGame.getSize().height);
      Heart._noticeLabel.setLocation(5, 0);
      _panelNoteMainGame.add(Heart._noticeLabel);
   }

   /**
    *setVisibleGame()
    */
   public void setVisibleGame() {
      _frameMainGame.setVisible(true);
   }

   /**
    * setbntRound()
    */
   public void setbntRound() {
      Heart._bntNewRound = new JButton("Start");
      Heart._bntNewRound.setSize(101, 25);
      Heart._bntNewRound.setLocation(340, 425);
      Heart._bntNewRound.setBackground(Color.WHITE);
      Heart._bntNewRound.setForeground(Color.RED);
      Heart._bntNewRound.setVisible(false);
      Heart._bntNewRound.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            Heart._isStartNewRound = true;
         }
      });
      _frameMainGame.add(Heart._bntNewRound);
   }

   /**
    * mouseClicked(MouseEvent arg0)
    * @param arg0
    */
   @Override
   public void mouseClicked(MouseEvent arg0) {
      _playSound = new Sound();
      // TODO Auto-generated method stub
      if (arg0.getSource() == _labelListCardHuman[0] && _isClickLabelHuman) {
         _indexLabelCardHuman = 0;
         _playSound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);
      }
      if (arg0.getSource() == _labelListCardHuman[1] && _isClickLabelHuman) {
         _indexLabelCardHuman = 1;
         _playSound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);
      }
      if (arg0.getSource() == _labelListCardHuman[2] && _isClickLabelHuman) {
         _indexLabelCardHuman = 2;
         _playSound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);
      }
      if (arg0.getSource() == _labelListCardHuman[3] && _isClickLabelHuman) {
         _indexLabelCardHuman = 3;
         _playSound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);
      }
      if (arg0.getSource() == _labelListCardHuman[4] && _isClickLabelHuman) {
         _indexLabelCardHuman = 4;
         _playSound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);
      }
      if (arg0.getSource() == _labelListCardHuman[5] && _isClickLabelHuman) {
         _indexLabelCardHuman = 5;
         _playSound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);
      }
      if (arg0.getSource() == _labelListCardHuman[6] && _isClickLabelHuman) {
         _indexLabelCardHuman = 6;
         _playSound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);
      }
      if (arg0.getSource() == _labelListCardHuman[7] && _isClickLabelHuman) {
         _indexLabelCardHuman = 7;
         _playSound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);
      }
      if (arg0.getSource() == _labelListCardHuman[8] && _isClickLabelHuman) {
         _indexLabelCardHuman = 8;
         _playSound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);
      }
      if (arg0.getSource() == _labelListCardHuman[9] && _isClickLabelHuman) {
         _indexLabelCardHuman = 9;
         _playSound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);
      }
      if (arg0.getSource() == _labelListCardHuman[10] && _isClickLabelHuman) {
         _indexLabelCardHuman = 10;
         _playSound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);
      }
      if (arg0.getSource() == _labelListCardHuman[11] && _isClickLabelHuman) {
         _indexLabelCardHuman = 11;
         _playSound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);
      }
      if (arg0.getSource() == _labelListCardHuman[12] && _isClickLabelHuman) {
         _indexLabelCardHuman = 12;
         _playSound.playFileSound(Card.SOUND_PLAY_CLICK_CARD);
      }

      if (_is3Card) {
         if (_indexLabelCardHuman != -1) {
            if (_flagPlay[_indexLabelCardHuman] == false) {
               if (_indexPositon.size() < 3) {
                  JLabel lbl = _nameDownPlayer.getListLabelCardPlayer()[_indexLabelCardHuman];
                  lbl.setLocation(lbl.getLocation().x, lbl.getLocation().y - 20);
                  _flagPlay[_indexLabelCardHuman] = true;
                  _indexPositon.add(new Integer(_indexLabelCardHuman));
               }
            } else {
               JLabel lbl = _nameDownPlayer.getListLabelCardPlayer()[_indexLabelCardHuman];
               lbl.setLocation(lbl.getLocation().x, lbl.getLocation().y + 20);
               _flagPlay[_indexLabelCardHuman] = false;
               _indexPositon.remove(new Integer(_indexLabelCardHuman));
            }
         }
      }
   }

   public void setTextMessage() {

      //Hien thi hang noi dung chat
      _txtDisplayMessage = new JTextArea("chao");
      _txtDisplayMessage.setSize(185, 65);
      _txtDisplayMessage.setLocation(610, 560);

      //Text box chat
      _txtSendMessage = new JTextField("chat");
      _txtSendMessage.setSize(122, 25);
      _txtSendMessage.setLocation(610,625 );

      //Button send noi dung chat
      _bntSendMessage = new JButton("Send");
      _bntSendMessage.setSize(63, 25);
      _bntSendMessage.setLocation(732, 625);
      _bntSendMessage.setBackground(Color.GRAY);
      _bntSendMessage.setForeground(Color.RED);
      _bntSendMessage.setVisible(true);
      _bntSendMessage.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {

            _txtDisplayMessage.setText("Me:"+_txtSendMessage.getText().toString().trim());
         }
      });

      _frameMainGame.add(_txtDisplayMessage);
      _frameMainGame.add(_txtSendMessage);
      _frameMainGame.add(_bntSendMessage);

   }

   /**
    *
    */
   public void enableGame() {
      //Khoi tao ten nguoi choi
      initNamePlayGame();
      //Xet frame chinh
      setMainFrame();
      //Xet menubar chinh
      setMenuBar();
      //Xet menu game
      setMenuGame();
      //Xet menu Option
      setMenuOption();
      //xet menu Connect server
      setMenuConnectServer();
      //Xet menu help
      setMenuHelp();
      //Xet label name
      setLabelNamePlayer();
      //Xet label card
      setLabelCardPlay();
      //Hien thi thong tin de chat
      setTextMessage();
      //Xet kit thuot card
      setDimensionLabelCard();
      //Xet button Start game
      setbntRound();
      //Xet panel hien thi thong bao
      setPanelNote();
      setVisibleGame();
   }

   /**
    * mouseEntered(MouseEvent arg0)
    * @param arg0
    */
   @Override
   public void mouseEntered(MouseEvent arg0) {
      // TODO Auto-generated method stub
   }

   /**
    *mouseExited(MouseEvent arg0)
    * @param arg0
    */
   @Override
   public void mouseExited(MouseEvent arg0) {
      // TODO Auto-generated method stub
   }

   /**
    * mousePressed(MouseEvent arg0)
    * @param arg0
    */
   @Override
   public void mousePressed(MouseEvent arg0) {
      // TODO Auto-generated method stub
   }

   /**
    * mouseReleased(MouseEvent arg0)
    * @param arg0
    */
   @Override
   public void mouseReleased(MouseEvent arg0) {
      // TODO Auto-generated method stub
   }

   /**
    * itemStateChanged(ItemEvent ie)
    * @param ie
    */
   @Override
   public void itemStateChanged(ItemEvent ie) {
      // TODO Auto-generated method stub
   }

   /**
    * mouseDragged(MouseEvent e)
    * @param e
    */
   @Override
   public void mouseDragged(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
   }

   /**
    * mouseMoved(MouseEvent e)
    * @param e
    */
   @Override
   public void mouseMoved(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
      _xMouseCurrent = e.getX();
      _yMouseCurrent = e.getY();
      _nameDownPlayer.repaintList3CardChangePlayer();
   }
}

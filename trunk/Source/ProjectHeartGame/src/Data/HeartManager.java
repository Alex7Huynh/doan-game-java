/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author TienPhan
 */
public class HeartManager implements MouseListener, ItemListener {

   private JFrame myFrame; // Frame chinh
   private JMenuBar menuBar; // Menu game chính
   private JMenu game, help;
   private JMenuItem menuItem;
   private JMenuItem showcard;
   private Checkbox slow, normal, fast;
   private JLabel[] lblHuman;	//cac vi tri quan bai cua HUMAN
   private JLabel[] lblHumanpPauline;
   private JLabel[] lblHumanpMichele;
   private JLabel[] lblHumanpBen;
   private Player pJara, pPauline, pMichele, pBen;	    //4 nguoi choi trong game
   //
   private Player firstPlayer;	//nguoi choi dau tien trong moi luot
   private String txtScore;	//String thong bao hien thi diem
   private static int labelIndex = -1;	//vi tri Label la bai duoc chon cua nguoi choi human
   private static boolean CLICK_ENABLE;//cho phep click len Label la bai cua nguoi choi human
   //
   private static Card kiemtra;		//la bai danh ra cua nguoi choi dau tien trong luot
   //
   private Card Card2, Card3, Card4;	//la bai danh ra cua cac nguoi choi tiep theo
   private static boolean chatCo;
   private static boolean luotDau;
   private final int SHOOT_MOON_SCORE = 26;	//diem so trong truong hop Shoot The Moon
   private final int MAX_SCORE = 100;			//diem so de xac dinh nguoi chien thang
   //Animation Speed (timePlay, timeMove)
   //fast: (5, 1) normal: (30, 5) slow: (70, 7)
   private int timePlay = 5;
   private int timeMove = 1;
   //xac dinh bat dau mot vong choi moi
   private static boolean startNewRound = false;
   private static JButton newRoundButton;
   //Thong bao cho nguoi choi
   private static JLabel noteLabel;
   static boolean[] flag = new boolean[Player.SOQUANBAI];
   //Ba la bai duoc chon
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

   public HeartManager() {

      //====================================================================
      //					THIET LAP NGUOI CHOI
      //====================================================================
      pJara = new Player("Jara", Player.IS_HUMAN, Player.LEFT);
      /**/
      pPauline = new Player("Pauline", Player.IS_HUMAN, Player.BOTTOM);/////////////////////////////////////////////////////////////////////
      //pMichele = new Player("Michele", Player.IS_HUMAN, Player.TOP);
      //pBen = new Player("Ben", Player.IS_HUMAN, Player.RIGHT);
        /**/
      // pPauline = new Player("Pauline", Player.IS_COMPUTER, Player.LEFT);
      pMichele = new Player("Michele", Player.IS_COMPUTER, Player.TOP);
      pBen = new Player("Ben", Player.IS_COMPUTER, Player.RIGHT);

      pJara.setNextPlayer(pPauline);
      pPauline.setNextPlayer(pMichele);
      pMichele.setNextPlayer(pBen);
      pBen.setNextPlayer(pJara);

      //====================================================================
      //					THIET LAP FRAME
      //====================================================================
      myFrame = new JFrame("Hearts Game Java v1.0");
      myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      myFrame.setLayout(null);
      myFrame.setResizable(false);
      myFrame.setSize(620, 585);
      myFrame.setLocation(150, 50);
      JPanel panelFrame = (JPanel) myFrame.getContentPane();
      panelFrame.setBackground(Color.BLUE);

      //====================================================================
      //					 THIET LAP MENU
      //====================================================================
      menuBar = new JMenuBar();
      myFrame.setJMenuBar(menuBar);

      //-------------------------------Game-------------------------------------
      game = new JMenu("Game");
      game.setMnemonic('G');
      menuBar.add(game);

      menuItem = new JMenuItem("Score...");
      menuItem.setMnemonic('c');
      game.add(menuItem);
      menuItem.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            //showGameScore(null);
            // JOptionPane.showMessageDialog(null, txtScore, "Score Sheet", JOptionPane.INFORMATION_MESSAGE);
         }
      });
      game.addSeparator();	//them dau nhom item

      final JMenuItem play = new JMenuItem("Automatic");
      play.setMnemonic('t');
      game.add(play);
      play.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            if (pJara.isHuman()) {
               pJara.setType(Player.IS_COMPUTER);
               play.setText("Manually");
               play.setMnemonic('M');
            } else {
               pJara.setType(Player.IS_HUMAN);
               play.setText("Automatic");
               play.setMnemonic('A');
            }
         }
      });

      showcard = new JMenuItem("Show Computers Card");
      showcard.setMnemonic('S');
      game.add(showcard);
      showcard.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            if (showcard.getText().equals("Show Computers Card")) {
               showcard.setText("Hide Computers Card");
               showcard.setMnemonic('H');
               pPauline.showListCard(true);//hien thi toan bo la bai ra cho nguoi choi
               pMichele.showListCard(true);
               pBen.showListCard(true);
            } else {
               showcard.setText("Show Computers Card");
               showcard.setMnemonic('S');
               pPauline.showListCard(false);
               pMichele.showListCard(false);
               pBen.showListCard(false);
            }
         }
      });
      game.addSeparator();	//them dau nhom item

      menuItem = new JMenuItem("Exit");
      menuItem.setMnemonic('x');
      game.add(menuItem);
      menuItem.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      });

      //--------------------------------Help------------------------------------
      help = new JMenu("Help");
      help.setMnemonic('e');
      menuBar.add(help);

      menuItem = new JMenuItem("About...");
      menuItem.setMnemonic('A');
      help.add(menuItem);

      menuItem.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Design by: Tien,Toan,Thuan", "JAVA", JOptionPane.INFORMATION_MESSAGE);
         }
      });

      //====================================================================
      //					 	OPTIONS
      //====================================================================
      CheckboxGroup checkGroup;
      //add the checkboxes to the frame
      checkGroup = new CheckboxGroup();
      fast = new Checkbox("Fast", checkGroup, true);
      fast.addItemListener((ItemListener) this);

      normal = new Checkbox("Normal", checkGroup, false);
      normal.addItemListener((ItemListener) this);

      slow = new Checkbox("Slow", checkGroup, false);
      slow.addItemListener((ItemListener) this);

      Panel north = new Panel();
      north.add(fast);
      north.add(normal);
      north.add(slow);
      menuBar.add(north, BorderLayout.WEST);


      //====================================================================
      //					 	PLAYER'S NAME LABEL
      //====================================================================
      JLabel[] label = new JLabel[4];
      //pJara
      label[0] = new JLabel(pJara.getName());
      label[0].setSize(100, 20);
      label[0].setLocation(100, 465);
      myFrame.add(label[0]);
      //pPauline
      label[1] = new JLabel(pPauline.getName());
      label[1].setSize(100, 20);
      label[1].setLocation(10, 31);
      myFrame.add(label[1]);
      //pMichele
      label[2] = new JLabel(pMichele.getName());
      label[2].setSize(100, 20);
      label[2].setLocation(455, 10);
      myFrame.add(label[2]);
      //pBen
      label[3] = new JLabel(pBen.getName());
      label[3].setSize(100, 20);
      label[3].setLocation(560, 420);
      myFrame.add(label[3]);

      //====================================================================
      //				CARDPLAY'S LABEL: xet la bai danh ra nam o vi tri nao
      // o giua mang hinh do, moi nguoi choi se xet rieng
      //====================================================================
      //QUAN DANH RA CUA pJara
      JLabel[] lbl1 = new JLabel[4];
      for (int i = 0; i < lbl1.length; i++) {
         lbl1[i] = new JLabel();
         lbl1[i].setSize(73, 97);
         lbl1[i].setLocation(245, 230);
         lbl1[i].setVisible(false);
      }
      pJara.setPlayCardLabel(lbl1);

      //QUAN DANH RA CUA pPauline
      JLabel[] lbl2 = new JLabel[4];
      for (int i = 0; i < lbl2.length; i++) {
         lbl2[i] = new JLabel();
         lbl2[i].setSize(73, 97);
         lbl2[i].setLocation(225, 190);
         lbl2[i].setVisible(false);
      }
      pPauline.setPlayCardLabel(lbl2);

      //QUAN DANH RA CUA pMichele
      JLabel[] lbl3 = new JLabel[4];
      for (int i = 0; i < lbl3.length; i++) {
         lbl3[i] = new JLabel();
         lbl3[i].setSize(73, 97);
         lbl3[i].setLocation(250, 165);
         lbl3[i].setVisible(false);
      }
      pMichele.setPlayCardLabel(lbl3);

      //QUAN DANH RA CUA pBen
      JLabel[] lbl4 = new JLabel[4];
      for (int i = 0; i < lbl3.length; i++) {
         lbl4[i] = new JLabel();
         lbl4[i].setSize(73, 97);
         lbl4[i].setLocation(285, 200);
         lbl4[i].setVisible(false);
      }
      pBen.setPlayCardLabel(lbl4);

      // add theo thu tu nay co nghia la gi day ta????????
      //Thu tu khong quan trong mien sao la add du vao la duoc
      myFrame.add(lbl4[3]);
      myFrame.add(lbl3[2]);
      myFrame.add(lbl2[1]);
      myFrame.add(lbl1[0]);

      myFrame.add(lbl1[3]);
      myFrame.add(lbl4[2]);
      myFrame.add(lbl3[1]);
      myFrame.add(lbl2[0]);

      myFrame.add(lbl2[3]);
      myFrame.add(lbl1[2]);
      myFrame.add(lbl4[1]);
      myFrame.add(lbl3[0]);

      myFrame.add(lbl3[3]);
      myFrame.add(lbl2[2]);
      myFrame.add(lbl1[1]);
      myFrame.add(lbl4[0]);

      //====================================================================
      //				LABEL OF PLAYER'S CARD
      //Dan kit thuot cua cac la bai chong len nhau, neu tang kit thuot thi cac
      // la bai dan ra nhieu, neu ha thi cac la bai thu lai.
      //====================================================================
      Dimension size = new Dimension(73, 97);
      int dx = 19;
      int dy = 23;
//      int dx = 18;
//        int dy = 20;
      int MAX_CARD = Player.SOQUANBAI + 1;

      //====================================================================
      //					Jara's card
      //====================================================================
      lblHuman = new JLabel[MAX_CARD];
      for (int i = 0; i < MAX_CARD; i++) {
         lblHuman[i] = new JLabel();
         lblHuman[i].setSize(size);
         //lblHuman[i].setLocation(i * dx + 145, 385);
         lblHuman[i].setLocation(10, i * dy + 65);
         lblHuman[i].setVisible(false);
         lblHuman[i].addMouseListener(this);
      }
      for (int i = MAX_CARD - 1; i >= 0; i--) {
         myFrame.add(lblHuman[i]);
      }
      pJara.setListCardLabel(lblHuman);

      //====================================================================
      //					Pauline's card
      //====================================================================
      JLabel[] lblComputer1 = new JLabel[MAX_CARD];
      for (int i = 0; i < MAX_CARD; i++) {
         lblComputer1[i] = new JLabel();
         lblComputer1[i].setSize(size);
         //lblComputer1[i].setLocation(10, i * dy + 65);
         lblComputer1[i].setLocation(i * dx + 145, 385);
         lblComputer1[i].setVisible(false);
         // lblComputer1[i].addMouseListener(this);
///////////////////////////////////////////////////////////////////////////////////////Bo dau ///////////////////////////////////////////
      }
      for (int i = MAX_CARD - 1; i >= 0; i--) {
         myFrame.add(lblComputer1[i]);
      }
      pPauline.setListCardLabel(lblComputer1);
      /*
      //Them moi het tron bo
      lblHuman11 = new JLabel[MAX_CARD];
      for (int i = 0; i < MAX_CARD; i++) {
      lblHuman11[i] = new JLabel();
      lblHuman11[i].setSize(size);
      lblHuman11[i].setLocation(10, i * dy + 65);
      lblHuman11[i].setVisible(false);
      lblHuman11[i].addMouseListener(this);
      }
      for (int i = MAX_CARD - 1; i >= 0; i--) {
      myFrame.add(lblHuman11[i]);
      }
      pPauline.setListCardLabel(lblHuman11);
       */
      //===============================================================
      //					Michele's card
      //===============================================================
      JLabel[] lblComputer2 = new JLabel[MAX_CARD];
      for (int i = 0; i < MAX_CARD; i++) {
         lblComputer2[i] = new JLabel();
         lblComputer2[i].setSize(size);
         lblComputer2[i].setLocation(i * dx + 145, 15);
         lblComputer2[i].setVisible(false);
      }
      for (int i = 0; i < MAX_CARD; i++) {
         myFrame.add(lblComputer2[i]);
      }
      pMichele.setListCardLabel(lblComputer2);


      //===============================================================
      //					Ben's card
      //===============================================================
      JLabel[] lblComputer3 = new JLabel[MAX_CARD];
      for (int i = 0; i < MAX_CARD; i++) {
         lblComputer3[i] = new JLabel();
         lblComputer3[i].setSize(size);
         lblComputer3[i].setLocation(510, i * dy + 65);
         lblComputer3[i].setVisible(false);
      }
      for (int i = 0; i < MAX_CARD; i++) {
         myFrame.add(lblComputer3[i]);
      }
      pBen.setListCardLabel(lblComputer3);

      //===============================================================
      //					Start new Round
      //===============================================================
      newRoundButton = new JButton("Start Round");
      newRoundButton.setSize(101, 25);
      //newRoundButton.setToolTipText("Click To Start New Round");
      newRoundButton.setLocation(240, 320);
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
      panel.setBounds(0, 495, 595, 25);
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

   public void mouseClicked(MouseEvent e) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void mousePressed(MouseEvent e) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void mouseReleased(MouseEvent e) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void mouseEntered(MouseEvent e) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void mouseExited(MouseEvent e) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void itemStateChanged(ItemEvent e) {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}

package mypackage;

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

	private static final long serialVersionUID = 1L;
	private JFrame myFrame;
	private JMenuBar menuBar;
	private JMenu game, help;
	private JMenuItem menuItem;
	private JMenuItem showcard;
	private Checkbox slow, normal, fast;
    
    private JLabel[] lblHuman;	//cac vi tri quan bai cua HUMAN	  
    
    private Player pPlayerOne, pPlayerTwo, pPlayerThree, pPlayerFour;	    //4 nguoi choi trong game
    private String[] PlayerName = { "Alice", "Bill", "Cain", "David" };
	private Player firstPlayer;							//nguoi choi dau tien trong moi luot
    
	private String txtScore;	//String thong bao hien thi diem
    
    private static int labelIndex = -1;	//vi tri Label la bai duoc chon cua nguoi choi human
    private static Card kiemtra;		//la bai danh ra cua nguoi choi dau tien trong luot
	private Card Card2, Card3, Card4;	//la bai danh ra cua cac nguoi choi tiep theo
	private static boolean chatCo;
	private static boolean luotDau;
    
    private static boolean CLICK_ENABLE;		//cho phep click len Label la bai cua nguoi choi human
	
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
    
    
	//Constructor
        
    public Hearts() {
    	
    	//====================================================================
    	//					THIET LAP NGUOI CHOI
    	//====================================================================
	
        pPlayerOne = new Player(getPlayerName()[0], Player.IS_HUMAN, Player.BOTTOM);
        pPlayerTwo = new Player(getPlayerName()[1], Player.IS_COMPUTER, Player.LEFT);
        pPlayerThree = new Player(getPlayerName()[2], Player.IS_COMPUTER, Player.TOP);
        pPlayerFour = new Player(getPlayerName()[3], Player.IS_COMPUTER, Player.RIGHT);

		pPlayerOne.setNextPlayer(pPlayerTwo);
		pPlayerTwo.setNextPlayer(pPlayerThree);
		pPlayerThree.setNextPlayer(pPlayerFour);
		pPlayerFour.setNextPlayer(pPlayerOne);
		
		//====================================================================
		//					THIET LAP FRAME
		//====================================================================
		myFrame = new JFrame("Hearts Game Java v1.0");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setLayout(null);
		myFrame.setResizable(false);
		myFrame.setSize(600, 585);
        myFrame.setLocation(150, 50);
        JPanel panelFrame = (JPanel) myFrame.getContentPane();
        panelFrame.setBackground(new Color(85, 171, 105));
        
        //====================================================================
        //					 THIET LAP MENU
        //====================================================================
    	menuBar = new JMenuBar();
    	myFrame.setJMenuBar(menuBar);
    	
    	//--------------------------------------------------------------------
    	game = new JMenu("Game");
    	game.setMnemonic('G');
        menuBar.add(game);
    	
    	menuItem = new JMenuItem("Score...");
    	menuItem.setMnemonic('c');
    	game.add(menuItem);
    	menuItem.addActionListener(new ActionListener()
        {
    		public void actionPerformed(ActionEvent e) {
    			showGameScore(null);
    			//JOptionPane.showMessageDialog(null, txtScore, "Score Sheet", JOptionPane.INFORMATION_MESSAGE);
    		}
        });
    	game.addSeparator();	//them dau nhom item
    	
    	final JMenuItem play = new JMenuItem("Automatic");
    	play.setMnemonic('t');
    	game.add(play);
    	play.addActionListener(new ActionListener()
        {
    		public void actionPerformed(ActionEvent e) {
    			if(pPlayerOne.isHuman()) {
    				pPlayerOne.setType(Player.IS_COMPUTER);
    				play.setText("Manually");
    				play.setMnemonic('M');
    			}
    			else {
    				pPlayerOne.setType(Player.IS_HUMAN);
    				play.setText("Automatic");
    				play.setMnemonic('A');
    			}
    		}
        });
    	
    	showcard = new JMenuItem("Show Computers Card");
    	showcard.setMnemonic('S');
    	game.add(showcard);
    	showcard.addActionListener(new ActionListener()
        {
    		public void actionPerformed(ActionEvent e) {
    			if(showcard.getText().equals("Show Computers Card")) {
    				showcard.setText("Hide Computers Card");
    				showcard.setMnemonic('H');
    				pPlayerTwo.showListCard(true);
    				pPlayerThree.showListCard(true);
    				pPlayerFour.showListCard(true);
    			}
    			else {
    				showcard.setText("Show Computers Card");
    				showcard.setMnemonic('S');
       				pPlayerTwo.showListCard(false);
    				pPlayerThree.showListCard(false);
    				pPlayerFour.showListCard(false);
    			}
    		}
        });
        game.addSeparator();	//them dau nhom item
    	
    	menuItem = new JMenuItem("Exit");
    	menuItem.setMnemonic('x');
    	game.add(menuItem);
    	menuItem.addActionListener(new ActionListener()
        {
    		public void actionPerformed(ActionEvent e) {
    			System.exit(0);
    		}
        });
           
    	//--------------------------------------------------------------------
    	help = new JMenu("Help");
    	help.setMnemonic('e');
    	menuBar.add(help);
    	
    	menuItem = new JMenuItem("About...");
    	menuItem.setMnemonic('A');
    	help.add(menuItem);
    	menuItem.addActionListener(new ActionListener()
        {
    		public void actionPerformed(ActionEvent e) {
    			JOptionPane.showMessageDialog(null, "Design by: Luu Binh\nEmail: luubinh273@yahoo.com", "Hearts Game Java v1.0", JOptionPane.INFORMATION_MESSAGE);
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
    	
    	
        //====================================================================
        //					 	PLAYER'S NAME LABEL 
        //====================================================================
    	JLabel[] label = new JLabel[4];
    	//pJara
    	label[0] = new JLabel(pPlayerOne.getName());
    	label[0].setSize(100, 20);
    	label[0].setLocation(100, 465);
    	myFrame.add(label[0]);
    	//pPauline
    	label[1] = new JLabel(pPlayerTwo.getName());
    	label[1].setSize(100, 20);
    	label[1].setLocation(10, 31);
    	myFrame.add(label[1]);
    	//pMichele
    	label[2] = new JLabel(pPlayerThree.getName());
    	label[2].setSize(100, 20);
    	label[2].setLocation(455, 10);
    	myFrame.add(label[2]);
    	//pBen
    	label[3] = new JLabel(pPlayerFour.getName());
    	label[3].setSize(100, 20);
    	label[3].setLocation(560, 420);
    	myFrame.add(label[3]);
        
        //====================================================================
        //				CARDPLAY'S LABEL
        //====================================================================
    	//QUAN DANH RA CUA pJara
        JLabel[] lbl1 = new JLabel[4];
    	for(int i=0; i<lbl1.length; i++) {
    		lbl1[i] = new JLabel();
    		lbl1[i].setSize(73, 97);
    		lbl1[i].setLocation(245, 230);
    		lbl1[i].setVisible(false);
    	}
    	pPlayerOne.setPlayCardLabel(lbl1);
    	
    	//QUAN DANH RA CUA pPauline
    	JLabel[] lbl2 = new JLabel[4];
    	for(int i=0; i<lbl2.length; i++) {
    		lbl2[i] = new JLabel();
    		lbl2[i].setSize(73, 97);
    		lbl2[i].setLocation(225, 190);
    		lbl2[i].setVisible(false);
    	}
    	pPlayerTwo.setPlayCardLabel(lbl2);
    	
		//QUAN DANH RA CUA pMichele
    	JLabel[] lbl3 = new JLabel[4];
    	for(int i=0; i<lbl3.length; i++) {
    		lbl3[i] = new JLabel();
    		lbl3[i].setSize(73, 97);
    		lbl3[i].setLocation(250, 165);
    		lbl3[i].setVisible(false);
    	}
    	pPlayerThree.setPlayCardLabel(lbl3);
    	
    	//QUAN DANH RA CUA pBen   	
    	JLabel[] lbl4 = new JLabel[4];
    	for(int i=0; i<lbl3.length; i++) {
    		lbl4[i] = new JLabel();
    		lbl4[i].setSize(73, 97);
    		lbl4[i].setLocation(285, 200);
    		lbl4[i].setVisible(false);
    	}
    	pPlayerFour.setPlayCardLabel(lbl4);    	
    	
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
       	//====================================================================
    	Dimension size = new Dimension(73, 97);
    	int dx = 18;
    	int dy = 20;
    	int MAX_CARD = Player.SOQUANBAI + 1;
		
		//====================================================================
    	//					Jara's card
		//====================================================================
		lblHuman = new JLabel[MAX_CARD];
    	for(int i=0; i<MAX_CARD; i++) {
    		lblHuman[i] = new JLabel();
    		lblHuman[i].setSize(size);
    		lblHuman[i].setLocation(i*dx + 145, 385);
    		lblHuman[i].setVisible(false);
    		lblHuman[i].addMouseListener(this);
    	}
    	for(int i=MAX_CARD - 1; i>=0; i--)
    		myFrame.add(lblHuman[i]);
    	pPlayerOne.setListCardLabel(lblHuman);
    	
    	
    	//====================================================================
    	//					Pauline's card
    	//====================================================================
    	JLabel[] lblComputer1 = new JLabel[MAX_CARD];
    	for(int i=0; i<MAX_CARD; i++) {
    		lblComputer1[i] = new JLabel();
    		lblComputer1[i].setSize(size);
    		lblComputer1[i].setLocation(10, i*dy + 65);
    		lblComputer1[i].setVisible(false);
    	}
    	for(int i=MAX_CARD - 1; i>=0; i--)
    		myFrame.add(lblComputer1[i]);
    	pPlayerTwo.setListCardLabel(lblComputer1);
    	
    	
    	//===============================================================
    	//					Michele's card
    	//===============================================================
    	JLabel[] lblComputer2 = new JLabel[MAX_CARD];
    	for(int i=0; i<MAX_CARD; i++) {
    		lblComputer2[i] = new JLabel();
    		lblComputer2[i].setSize(size);
    		lblComputer2[i].setLocation(i*dx + 145, 15);
    		lblComputer2[i].setVisible(false);
    	}
    	for(int i=0; i<MAX_CARD; i++)
    		myFrame.add(lblComputer2[i]);
    	pPlayerThree.setListCardLabel(lblComputer2);
    	
    	
    	//===============================================================
    	//					Ben's card
		//===============================================================
    	JLabel[] lblComputer3 = new JLabel[MAX_CARD];
    	for(int i=0; i<MAX_CARD; i++) {
    		lblComputer3[i] = new JLabel();
    		lblComputer3[i].setSize(size);
    		lblComputer3[i].setLocation(510, i*dy + 65);
    		lblComputer3[i].setVisible(false);
    	}
     	for(int i=0; i<MAX_CARD; i++)
    		myFrame.add(lblComputer3[i]);
     	pPlayerFour.setListCardLabel(lblComputer3);
     	
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
    
    //====================================================================
    // 							Chia bai
    //====================================================================
	public void dealCard() {
				
		//Neu co xet cac truong hop dac biet
		if(this.CASE_FLAG != 0) {
			special_Deal_Card();
			return;
		}
		
		//Neu khong xet cac truong hop dac biet
		ArrayList<Card> cardList = Card.creatListCard();
		
		//chia tung nhom 4 la bai co nguoi choi theo chieu kim dong ho, tinh tu human
		Card c;
		for(int i=0; i<52; i+=4) {		
			
			//pJara
			c = cardList.get(i);
			pPlayerOne.addACard(c);
			
			//pPauline
			c = cardList.get(i+1);
			pPlayerTwo.addACard(c);
			
			//pMichele
			c = cardList.get(i+2);
			pPlayerThree.addACard(c);
			
			//pBen
			c = cardList.get(i+3);
			pPlayerFour.addACard(c);
		}
		
		//========== SHOW CARD ==========		
		pPlayerOne.showListCard(true);
		pPlayerTwo.showListCard(false);
		pPlayerThree.showListCard(false);
		pPlayerFour.showListCard(false);		
	}
	
	//====================================================================
    // 							Chia bai 2
    //====================================================================
	public void special_Deal_Card() {
		
		ArrayList<Card> cardList = Card.creatListCard();
		
		//Chia bai cho cac nguoi theo chat
		for(int i=0; i<cardList.size(); i++) {	
			Card c = cardList.get(i);
			//Chia ca quan bai chat Co cho pJara
			if(c.isHeart())							pPlayerOne.addACard(c);
			
			//Chia ca quan bai chat Ro cho pPauline
			if(c.getSuit() == Card.SUIT_DIAMOND)	pPlayerTwo.addACard(c);
			
			//Chia ca quan bai chat Chuon cho pMichele
			if(c.getSuit() == Card.SUIT_CLUB)		pPlayerThree.addACard(c);
			
			//Chia ca quan bai chat Bich cho pBen
			if(c.getSuit() == Card.SUIT_SPADE)		pPlayerFour.addACard(c);
		}				
		
		//========== XEP BAI ==========		
		pPlayerOne.sortCard();
		pPlayerTwo.sortCard();
		pPlayerThree.sortCard();
		pPlayerFour.sortCard();
		
		switch(this.CASE_FLAG) {
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
			for(int i=0; i<Player.SOQUANBAI; i++) {
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
		if(noteLabel != null && s != null)
			noteLabel.setText(s);
	}
	
    //====================================================================
    // 							Human Play
    //====================================================================
	public static Card humanPlay(Player p) {
		Card c;
		while(true) {
			if(labelIndex != -1) {
				c = p.getListCard().get(labelIndex);
				if(p.isValid(c, chatCo, luotDau, kiemtra)) {
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
		if(luotDau) {
			if(pPlayerOne.hasTwoClub())		return pPlayerOne;
			if(pPlayerFour.hasTwoClub())		return pPlayerFour;
			if(pPlayerTwo.hasTwoClub())	return pPlayerTwo;
			if(pPlayerThree.hasTwoClub())	return pPlayerThree;
		}
		//Neu khong, tim nguoi thang trong luot hien tai 
		else {			
			Card maxCard = kiemtra;
			if(Card2.isGreatThan(maxCard)) maxCard = Card2;
			if(Card3.isGreatThan(maxCard)) maxCard = Card3;
			if(Card4.isGreatThan(maxCard)) maxCard = Card4;
			
			if(pPlayerOne.getPLayCard().equals(maxCard))		return pPlayerOne;
			if(pPlayerFour.getPLayCard().equals(maxCard))		return pPlayerFour;
			if(pPlayerTwo.getPLayCard().equals(maxCard))	return pPlayerTwo;
			if(pPlayerThree.getPLayCard().equals(maxCard))	return pPlayerThree;
		}
		return null;
	}
	    
    //====================================================================
	//				Di chuyen cac label theo cac do dich dx, dy
    //====================================================================	
	public void move(JLabel[] listLabel, int dx, int dy) {	
		for(int index=0; index<listLabel.length; index++) {
			JLabel lbl = listLabel[index];
			for(int i=0; i<10; i++) {
				lbl.setLocation(lbl.getLocation().x + dx, lbl.getLocation().y + dy);
				
				try {
					Thread.sleep(timeMove);
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
		for(int i=0; i<lbl.length; i++) {
			lbl[i].setVisible(true);
			point[i] = lbl[i].getLocation();
		}
		
		if(winner == pPlayerOne)
			move(lbl, 0, 15);  	//pJara
		
		if(winner == pPlayerTwo)
			move(lbl, -20, 0);  //pPauline
		
		if(winner == pPlayerThree)
			move(lbl, 0, -15);  //pMichele
		
		if(winner == pPlayerFour)
			move(lbl, 20, 0); 	//pBen
		
		for(int i=0; i<lbl.length; i++) {
			lbl[i].setVisible(false);
			lbl[i].setLocation(point[i]);
		}
	}
	
    //====================================================================
	//						whoShootTheMoon
    //====================================================================
	public Player whoShootTheMoon() {
		if(pPlayerOne.isShootTheMoon())		return pPlayerOne;
		if(pPlayerFour.isShootTheMoon())		return pPlayerFour;
		if(pPlayerTwo.isShootTheMoon())	return pPlayerTwo;
		if(pPlayerThree.isShootTheMoon())	return pPlayerThree;
		return null;
	}
	
    //====================================================================
	//						Check Shoot The Moon
    //====================================================================
	public void checkShootThenMoon() {
		Player p = this.whoShootTheMoon();
		if(p != null) {
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
		if(p.getScore() > pPlayerTwo.getScore()) p = pPlayerTwo;
		if(p.getScore() > pPlayerThree.getScore()) p = pPlayerThree;
		if(p.getScore() > pPlayerFour.getScore()) p = pPlayerFour;
		return p;
	}
	
    //====================================================================
	//						whoIs100Score
    //====================================================================
	public Player whoIs100Score() {
		if(pPlayerOne.getScore() >= MAX_SCORE) return pPlayerOne;
		if(pPlayerTwo.getScore() >= MAX_SCORE) return pPlayerTwo;
		if(pPlayerThree.getScore() >= MAX_SCORE) return pPlayerThree;
		if(pPlayerFour.getScore() >= MAX_SCORE) return pPlayerFour;
		return null;
	}
	
    //====================================================================
	//						Check 100 Score
    //====================================================================
	public boolean check100Score() {
		Player p = this.whoIs100Score();
		if(p != null) {
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
	public void printTheScore() {
		Player p = pPlayerOne;
		System.out.println(p.getName() + "'s score is " + p.getScore());
		
		p = p.getNextPlayer();
		System.out.println(p.getName() + "'s score is " + p.getScore());
		
		p = p.getNextPlayer();
		System.out.println(p.getName() + "'s score is " + p.getScore());
		
		p = p.getNextPlayer();
		System.out.println(p.getName() + "'s score is " + p.getScore());
		
		System.out.println("=====================================");
	}
	
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
		if(!this.check100Score())
			//JOptionPane.showMessageDialog(null, txtScore, "Score Sheet", JOptionPane.INFORMATION_MESSAGE);
			this.showGameScore("Score");
	}
	
	//
	public static ArrayList<Integer> humanChose3Card() {
		labelIndex = -1;
		CLICK_ENABLE = true;
		get3card = true;
		
		for(int i=0; i<flag.length; i++)
			flag[i] = false;
		pos.clear();
	
		newRoundButton.setVisible(true);
		newRoundButton.setEnabled(false);
		startNewRound = false;
		while(!startNewRound) {
			if(pos.size() != 3)
				newRoundButton.setEnabled(false);
			else newRoundButton.setEnabled(true);
		}
		
		labelIndex = -1;
		CLICK_ENABLE = false;
		get3card = false;
		return pos;
	}
	
	//====================================================================
    // 						Check Start New Round
    //====================================================================
	public void checkStartNewRound() {
		
		//chia bai
		this.dealCard();
		
		//Neu khong xet cac truong hop dac biet
		if(this.CASE_FLAG ==0 ) {
			
		String txt = "Chon 3 la bai de doi cho ";
		PASS_NUMBER = (++PASS_NUMBER) % 4;
		
		switch(PASS_NUMBER) {
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
		if(PASS_NUMBER>=0 && PASS_NUMBER<=2) {
			pPlayerOne.passCard(PASS_NUMBER);
			
			notice("Nhan OK de bat dau choi.");
			newRoundButton.setText("OK");
		
			startNewRound = false;
			while(!startNewRound) {
			}	
			
			pPlayerOne.repaintChangeCard();
			pPlayerTwo.repaintChangeCard();
			pPlayerThree.repaintChangeCard();
			pPlayerFour.repaintChangeCard();
		}		
							
		newRoundButton.setVisible(false);
		
		}//if
		
		//Sap xep cac la bai cua 4 nguoi choi
		pPlayerOne.sortCard();
		pPlayerTwo.sortCard();
		pPlayerThree.sortCard();
		pPlayerFour.sortCard();
	}
		
	//====================================================================
    // 							New Round
    //====================================================================
	public void newRound() {
			
		Player p1, p2 = null, p3 = null, p4 = null;

		//showcard.setEnabled(false);
		showcard.setText("Show Computers Card");
		
		pPlayerOne.newRound();
		pPlayerTwo.newRound();
		pPlayerThree.newRound();
		pPlayerFour.newRound();
		
		this.checkStartNewRound();
		
		pPlayerOne.showListCard(true);
		pPlayerTwo.showListCard(false);
		pPlayerThree.showListCard(false);
		pPlayerFour.showListCard(false);
		
		//showcard.setEnabled(true);
		
		chatCo = false;
		luotDau = true;
		CLICK_ENABLE = true;
		
		//nguoi choi dau tien trong vong
		firstPlayer = findFisrtPlayer();		
		
		for(int i=0; i<Player.SOQUANBAI; i++) {	
			
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
			if(p1.isHuman()) CLICK_ENABLE = false;
			System.out.println(p1.getName() + kiemtra.toString());
			
			try {
				Thread.sleep(timePlay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			//luot danh cua nguoi thu 2
			p2.play(chatCo, luotDau, kiemtra);
			Card2 = p2.getPLayCard();
			p2.showPlayCardLabel(1, Card2);
			if(p2.isHuman()) CLICK_ENABLE = false;
			System.out.println(p2.getName() + Card2.toString());
			
			try {
				Thread.sleep(timePlay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//luot danh cua nguoi thu 3
			p3.play(chatCo, luotDau, kiemtra);
			Card3 = p3.getPLayCard();
			p3.showPlayCardLabel(2, Card3);
			if(p3.isHuman()) CLICK_ENABLE = false;
			System.out.println(p3.getName() + Card3.toString());
			
			try {
				Thread.sleep(timePlay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//luot danh cua nguoi thu 4
			p4.play(chatCo, luotDau, kiemtra);
			Card4 = p4.getPLayCard();
			p4.showPlayCardLabel(3, Card4);
			if(p4.isHuman()) CLICK_ENABLE = false;
			System.out.println(p4.getName() + Card4.toString());
			
			//Danh dau da xong luot dau
			if(luotDau) luotDau = false;
			//Lay chatCo
			if(!chatCo) 
				chatCo = kiemtra.isHeart() || Card2.isHeart() || Card3.isHeart() || Card4.isHeart();
			
			//Nguoi choi dau tien trong luot tiep theo
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			firstPlayer = findFisrtPlayer();
			move(p1, firstPlayer);
			
			firstPlayer.addAScoreCard(kiemtra);
			firstPlayer.addAScoreCard(Card2);
			firstPlayer.addAScoreCard(Card3);
			firstPlayer.addAScoreCard(Card4);
		
			//Hien thi diem qua tung luot cua nguoi choi
			printTheScore();
			
			//kiem tra khoi tao luot moi
			CLICK_ENABLE = true;
		}
		
		//Tinh diem, kiem tra de bat dau vong moi
		this.scoring();
	}
	
	//====================================================================
    // 							New Game
    //====================================================================
	public void newGame() {
		txtScore = pPlayerOne.getName() + "         " + pPlayerTwo.getName() + "        " + pPlayerThree.getName() + "         " + pPlayerFour.getName() + "\n";
		pPlayerOne.newGame();
		pPlayerTwo.newGame();
		pPlayerThree.newGame();
		pPlayerFour.newGame();
		while(true) {
			this.newRound();
		}
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
		// TODO Auto-generated method stub	
		if(arg0.getSource() == lblHuman[0]  && CLICK_ENABLE) labelIndex = 0;
		if(arg0.getSource() == lblHuman[1]  && CLICK_ENABLE) labelIndex = 1;
		if(arg0.getSource() == lblHuman[2]  && CLICK_ENABLE) labelIndex = 2;
		if(arg0.getSource() == lblHuman[3]  && CLICK_ENABLE) labelIndex = 3;
		if(arg0.getSource() == lblHuman[4]  && CLICK_ENABLE) labelIndex = 4;
		if(arg0.getSource() == lblHuman[5]  && CLICK_ENABLE) labelIndex = 5;
		if(arg0.getSource() == lblHuman[6]  && CLICK_ENABLE) labelIndex = 6;
		if(arg0.getSource() == lblHuman[7]  && CLICK_ENABLE) labelIndex = 7;
		if(arg0.getSource() == lblHuman[8]  && CLICK_ENABLE) labelIndex = 8;
		if(arg0.getSource() == lblHuman[9]  && CLICK_ENABLE) labelIndex = 9;
		if(arg0.getSource() == lblHuman[10] && CLICK_ENABLE) labelIndex = 10;
		if(arg0.getSource() == lblHuman[11] && CLICK_ENABLE) labelIndex = 11;
		if(arg0.getSource() == lblHuman[12] && CLICK_ENABLE) labelIndex = 12;
		
		if(get3card) {
			if(labelIndex != -1) {
				if(flag[labelIndex] == false) {
					if(pos.size() < 3) {
						JLabel lbl = pPlayerOne.getListCardLabel()[labelIndex];
						lbl.setLocation(lbl.getLocation().x, lbl.getLocation().y - 20);
						flag[labelIndex] = true;
						pos.add(new Integer(labelIndex));
					}
				}
				else {
					JLabel lbl = pPlayerOne.getListCardLabel()[labelIndex];
					lbl.setLocation(lbl.getLocation().x, lbl.getLocation().y  + 20);
					flag[labelIndex] = false;
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
		if(this.fast.getState()) {
			//Fast speed
			this.timePlay = 5;
			this.timeMove = 1;
		}
		
		if(this.normal.getState()) {
			//Normal speed
			this.timePlay = 30;
			this.timeMove = 5;
		}
		
		if(this.slow.getState()) {
			//Slow speed
			this.timePlay = 70;
			this.timeMove = 7;
		}
	}

    /**
     * @return the PlayerName
     */
    public String[] getPlayerName() {
        return PlayerName;
    }

    /**
     * @param PlayerName the PlayerName to set
     */
    public void setPlayerName(String[] PlayerName) {
        this.PlayerName = PlayerName;
    }
}


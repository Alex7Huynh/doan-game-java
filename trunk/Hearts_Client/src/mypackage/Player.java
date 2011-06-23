package mypackage;

import com.sun.org.apache.bcel.internal.generic.LSTORE;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Player {

    public static final int SOQUANBAI = 13;
    public static final int IS_HUMAN = 11;
    public static final int IS_COMPUTER = 21;
    public static final int BOTTOM = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int TOP = 4;
    private String name;				//ten nguoi choi
    private ArrayList<Card> listCard;
    private ArrayList<Card> scoreCard;
    private int score = 0;				//diem so
    private Player nextPlayer;//nguoi choi tiep theo
    private boolean isFirst;//xac dinh xem co phai la nguoi danh dau tien trong luot hien tai khong
    private Card playCard;//la bai danh ra
    private int type;//kieu nguoi choi: IS_HUMAN / IS_COMPUTER
    private JLabel[] listCardLabel;//cac nut chua cac quan bai co trong tay nguoi choi
    private JLabel[] playCardLabel;
    int position;	//vi tri nguoi choi
    private ArrayList<Integer> list3Card;
    //Vi tri quan bai duoc chon cua computer
    private int[] randomIndex;

    //Constructor
    public Player(String name, int type, int position) {
        this.name = name;
        this.type = type;
        this.position = position;
        this.listCard = new ArrayList<Card>(SOQUANBAI);
        this.scoreCard = new ArrayList<Card>();

        this.randomIndex = new int[SOQUANBAI];
        for (int i = 0; i < SOQUANBAI; i++) {
            randomIndex[i] = i;
        }

        Random random = new Random();
        for (int i = 0; i < SOQUANBAI; i++) {
            int j = random.nextInt(SOQUANBAI);
            int tmp = randomIndex[i];
            randomIndex[i] = randomIndex[j];
            randomIndex[j] = tmp;
        }

        /*System.out.println("");
        for(int i=0; i<SOQUANBAI; i++)
        System.out.print(randomIndex[i] + " ");
         */
    }

    //setName
    public void setName(String name) {
        this.name = name;
    }

    //getName
    public String getName() {
        return name;
    }

    //getListCard
    public ArrayList<Card> getListCard() {
        return listCard;
    }

    //addScore
    public void addScore(int score) {
        this.score += score;
    }

    //getScore
    public int getScore() {
        return score;
    }

    //setNextPlayer
    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    //getNextPlayer
    public Player getNextPlayer() {
        return nextPlayer;
    }

    //setType
    public void setType(int type) {
        this.type = type;
    }

    //getType
    public int getType() {
        return type;
    }

    //setIsFirst
    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    //getIsFirst
    public boolean getIsFirst() {
        return isFirst;
    }

    //getCardPlay
    public Card getPLayCard() {
        return playCard;
    }

    //setListCardLabel
    public void setListCardLabel(JLabel[] listCardLabel) {
        this.listCardLabel = listCardLabel;
    }

    //getListCardLabel
    public JLabel[] getListCardLabel() {
        return listCardLabel;
    }

    //setPlayCardLabel
    public void setPlayCardLabel(JLabel[] playCardLabel) {
        this.playCardLabel = playCardLabel;
    }

    //getPlayCardLabel
    public JLabel[] getPlayCardLabel() {
        return playCardLabel;
    }

    //getAPlayCardLabel
    public JLabel getAPlayCardLabel(int i) {
        return listCardLabel[i];
    }

    //isHuman
    public boolean isHuman() {
        return (this.type == IS_HUMAN);
    }

    //========================================================================
    //					hasTwoClub
    //========================================================================
    public boolean hasTwoClub() {
        for (int i = 0; i < listCard.size(); i++) {
            if (listCard.get(i).isTwoClub()) {
                return true;
            }
        }
        return false;
    }

    //========================================================================
    //		Kiem tra mot la bai c co hop le khong
    //		chatCo = true: o luot truoc co quan bai chat co
    //		luotDau = true: luot danh dau tien cua vong hien tai
    //		kiemtra: la bai cua nguoi danh dau tien trong luot do
    //========================================================================
    public boolean isValid(Card c, boolean chatCo, boolean luotDau, Card kiemtra) {

        if (c == null) {
            return false;
        }

        //Neu la nguoi danh dau tien trong luot hien tai
        if (isFirst) {
            //Neu la luot dau tien cua vong ma khong phai la quan 2 chuon thi ko hop le
            if (luotDau) {
                if (c.isTwoClub()) {
                    return true;
                } else {
                    if (this.isHuman()) {
                        Hearts.notice("Ban phai chon la 2 Chuon.");
                    }
                    return false;
                }
            }

            //Neu khong phai la chat co
            if (c.getSuit() != Card.SUIT_HEART) {
                return true;
            } //Neu la chat co
            else {
                //Neu o luot truoc co quan chat co
                if (chatCo) {
                    return true;
                } ////Neu o luot truoc khong co quan chat co
                else {
                    //Neu tren tay co it nhat mot quan khong phai chat co
                    for (int i = 0; i < listCard.size(); i++) {
                        if (listCard.get(i) != null) {
                            if (!listCard.get(i).isHeart()) {
                                if (this.isHuman()) {
                                    Hearts.notice("Chua duoc chon la bai chat Co. Hay chon la bai chat khac.");
                                }
                                return false;
                            }
                        }
                    }
                    //Neu tren tay toan quan bai chat co
                    return true;
                }
            }
        } //Neu la nguoi danh sau
        else {
            //Neu c co cung chat voi kiemtra
            if (c.getSuit() == kiemtra.getSuit()) {
                return true;
            } //Neu c khong cung chat voi kiemtra
            else {
                //Kiem tra xem tren tay co quan nao cung chat voi kiemtra khong
                for (int i = 0; i < listCard.size(); i++) {
                    if (listCard.get(i) != null) {
                        if (listCard.get(i).getSuit() == kiemtra.getSuit()) {
                            if (this.isHuman()) {
                                Hearts.notice("Ban phai chon 1 la bai chat " + Card.suitText[kiemtra.getSuit()] + ".");
                            }
                            return false;
                        }
                    }
                }
                //Neu tren tay khong co quan nao cung chat voi kiemtra	
                //	Neu c khong phai la quan chat co hoac khong phai la Q bich: hop le
                if (!c.isHeart() && !c.isQueenSpade()) {
                    return true;
                }

                //	Neu la quan chat co
                if (c.isHeart()) {
                    //Neu tren tay co quan khac chat co
                    for (int i = 0; i < listCard.size(); i++) {
                        if (listCard.get(i) != null) {
                            if (!listCard.get(i).isHeart()) {
                                //Neu o luot dau
                                if (luotDau) {
                                    if (this.isHuman()) {
                                        Hearts.notice("Ban khong duoc chon chat Co o luot dau tien.");
                                    }
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        }
                    }
                    //Neu tren tay cac quan toan chat co
                    return true;
                }

                //Neu la quan Q bich
                if (c.isQueenSpade()) {
                    if (luotDau) {
                        //Neu tren tay co quan khac chat co
                        for (int i = 0; i < listCard.size(); i++) {
                            if (listCard.get(i) != null) {
                                if (!listCard.get(i).isHeart()) {
                                    return true;
                                }
                            }
                        }
                        //Neu toan chat co 
                        if (this.isHuman()) {
                            Hearts.notice("Ban khong duoc chon la Q Bich o luot dau tien.");
                        }
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    //========================================================================
    //							play	
    //========================================================================
    public void play(boolean chatCo, boolean luotDau, Card kiemtra) {
        switch (type) {
            case IS_COMPUTER:
                Hearts.notice("Den luot choi cua " + this.name + " ...");
                for (int i = 0; i < SOQUANBAI; i++) {
                    int index = this.randomIndex[i];
                    Card comCard = listCard.get(index);
                    if (isValid(comCard, chatCo, luotDau, kiemtra)) {
                        this.playCard = comCard;
                        this.listCardLabel[index].setVisible(false);
                        this.listCard.set(index, null);
                        return;
                    }
                }
                break;
            case IS_HUMAN:
                Hearts.notice("Chon 1 la bai de choi.");
                this.playCard = Hearts.humanPlay(this);
                break;
        }
    }

    //========================================================================
    //								xepBai
    //========================================================================
    public void sortCard() {
        //Sap xep theo chat
        for (int i = 0; i < listCard.size() - 1; i++) {
            for (int j = i + 1; j < listCard.size(); j++) {
                if (listCard.get(i).getSuit() > listCard.get(j).getSuit()) {
                    Card t = listCard.get(i);
                    listCard.set(i, listCard.get(j));
                    listCard.set(j, t);
                }
            }
        }
        //Sap sep theo nuoc cua tung chat
        for (int i = 0; i < listCard.size() - 1; i++) {
            for (int j = i + 1; j < listCard.size(); j++) {
                if ((listCard.get(i).getSuit() == listCard.get(j).getSuit()) && (listCard.get(i).getFace() > listCard.get(j).getFace())) {
                    Card t = listCard.get(i);
                    listCard.set(i, listCard.get(j));
                    listCard.set(j, t);
                }
            }
        }
    }

    //========================================================================
    //addACard: Them 1 la bai vao bo bai cua nguoi choi
    //========================================================================
    public void addACard(Card c) {
        listCard.add(c);
    }

    //========================================================================
    //						them 1 quan bai thang duoc
    //========================================================================
    public void addAScoreCard(Card arg0) {//, Card arg1, Card arg2, Card arg3) {
        if (arg0.isHeart() || arg0.isQueenSpade()) {
            scoreCard.add(arg0);
            if (arg0.isHeart()) {
                score += 1;
            }
            if (arg0.isQueenSpade()) {
                score += 13;
            }
        }
    }

    //========================================================================
    //					shoot the moon
    //========================================================================
    public boolean isShootTheMoon() {
        if (scoreCard.size() == SOQUANBAI + 1) {
            return true;
        }
        return false;
    }

    //========================================================================
    //					hien thi label quan bai danh ra
    //========================================================================
    public void showPlayCardLabel(int i, Card c) {
        if (i < 0 || i > playCardLabel.length) {
            return;
        }
        this.playCardLabel[i].setVisible(true);
        this.playCardLabel[i].setIcon(new ImageIcon(c.creatIconFile()));
    }

    //========================================================================
    //					hien thi cac quan bai tren tay nguoi choi
    //========================================================================
    public void showListCard(boolean show) {
        int i;
        for (i = 0; i < listCard.size(); i++) {
            Card c = listCard.get(i);
            if (c != null) {
                if (show) {
                    this.listCardLabel[i].setIcon(new ImageIcon(c.creatIconFile()));
                } else {
                    this.listCardLabel[i].setIcon(new ImageIcon(Card.BACK_PICTURE));
                }
                this.listCardLabel[i].setVisible(true);
            }
        }

        //this.listCardLabel[Player.SOQUANBAI].setIcon(new ImageIcon(Card.BACK_PICTURE));
        this.listCardLabel[Player.SOQUANBAI].setVisible(false);
    }

    //========================================================================
    //					hien thi cac la bai thang duoc cua nguoi choi
    //========================================================================
    public void showListScoreCard() {
        if (scoreCard == null || scoreCard.size() == 0) {
            return;
        }

        for (int i = 0; i < scoreCard.size(); i++) {
            Card c = scoreCard.get(i);
            this.listCardLabel[i].setIcon(new ImageIcon(c.creatIconFile()));
            this.listCardLabel[i].setVisible(true);
        }

        for (int i = scoreCard.size(); i < listCardLabel.length; i++) {
            this.listCardLabel[i].setVisible(false);
        }

        if (scoreCard.size() > listCardLabel.length) {
            this.listCardLabel[SOQUANBAI].setVisible(false);
        }
    }

    //getList3Card
    public ArrayList<Integer> get3CardPos() {
        return list3Card;
    }

    //========================================================================
    //					Chose 3 random Card
    //========================================================================
    public void chose3Card() {

        switch (type) {
            //IS_COMPUTER
            case IS_COMPUTER:
                //Lay ngau nhien 3 vi tri cac quan bai tren tay nguoi choi
                Random random = new Random();

                ArrayList<Integer> listPos = new ArrayList<Integer>(SOQUANBAI);
                for (int i = 0; i < SOQUANBAI; i++) {
                    listPos.add(new Integer(i));
                }

                for (int i = 0; i < SOQUANBAI; i++) {
                    int j = random.nextInt(SOQUANBAI);
                    Integer t = listPos.get(i);
                    listPos.set(i, listPos.get(j));
                    listPos.set(j, t);
                }

                //Hien thi lai vi tri cua cac la bai duoc chon
                int dx = 0;
                int dy = 0;
                switch (this.position) {

                    case BOTTOM:
                        dy = -20;
                        break;

                    case LEFT:
                        dx = 20;
                        break;

                    case TOP:
                        dy = 20;
                        break;

                    case RIGHT:
                        dx = -20;
                        break;
                }

                ArrayList<Integer> tmp = new ArrayList<Integer>(3);
                for (int i = 0; i < 3; i++) {
                    int pos = listPos.get(i).intValue();
                    tmp.add(listPos.get(i));
                    JLabel lbl = this.listCardLabel[pos];
                    lbl.setLocation(lbl.getLocation().x + dx, lbl.getLocation().y + dy);
                }

                this.list3Card = tmp;
                break;

            //IS_HUMAN
            case IS_HUMAN:
                this.list3Card = Hearts.humanChose3Card();
                break;
        }
    }

    //
    public void change3CardToOther(Player p) {

        ArrayList<Integer> myList = this.get3CardPos();
        ArrayList<Integer> yourList = p.get3CardPos();
        
        Card myCard, yourCard;
        int j, k;

        for (int i = 0; i < 3; i++) {
            //my card
            j = myList.get(i).intValue();
            myCard = this.getListCard().get(j);

            //your card
            k = yourList.get(i).intValue();
            yourCard = p.getListCard().get(k);

            //change 2 card
            this.getListCard().set(j, yourCard);
            p.getListCard().set(k, myCard);

            if (this.isHuman()) {
                this.getListCardLabel()[j].setIcon(new ImageIcon(yourCard.creatIconFile()));
            } else {
                this.getListCardLabel()[j].setIcon(new ImageIcon(Card.BACK_PICTURE));
            }

            if (p.isHuman()) {
                p.getListCardLabel()[k].setIcon(new ImageIcon(myCard.creatIconFile()));
            } else {
                p.getListCardLabel()[k].setIcon(new ImageIcon(Card.BACK_PICTURE));
            }
        }
    }

    //
    public void passCard(int turn) {
        /*	SO DO DOI
         * 					cross
         *          left				right
         *             		this
         */
//        Player left, cross, right;
//        left = this.getNextPlayer();
//        cross = left.getNextPlayer();
//        right = cross.getNextPlayer();

        //left.chose3Card();
        //cross.chose3Card();
        //right.chose3Card();
        this.chose3Card();
        String Message = "1%" + listCard.get(list3Card.get(0)).toString()
                + "%" + listCard.get(list3Card.get(1)).toString()
                + "%" + listCard.get(list3Card.get(2)).toString();
                Hearts.ConnectF.sendMessage(Message);
                
        /*switch (turn) {
            //Pass Left
            case 0:
                this.change3CardToOther(left);
                cross.change3CardToOther(right);
                break;

            //Pass Right
            case 1:
                this.change3CardToOther(right);
                cross.change3CardToOther(left);
                break;

            //Pass Cross	
            case 2:
                this.change3CardToOther(cross);
                left.change3CardToOther(right);
                break;
        }*/
    }
    public void continuepassCard(int turn)
    {
        Player left, cross, right;
        left = this.getNextPlayer();
        cross = left.getNextPlayer();
        right = cross.getNextPlayer();
        
        switch (turn) {
            //Pass Left
            case 0:
                this.change3CardToOther(left);
                cross.change3CardToOther(right);
                break;

            //Pass Right
            case 1:
                this.change3CardToOther(right);
                cross.change3CardToOther(left);
                break;

            //Pass Cross	
            case 2:
                this.change3CardToOther(cross);
                left.change3CardToOther(right);
                break;
        }
    }

    //
    public void repaintChangeCard() {

        //Hien thi lai vi tri cua cac la bai duoc chon
        int dx = 0;
        int dy = 0;
        switch (this.position) {

            case BOTTOM:
                dy = 20;
                break;

            case LEFT:
                dx = -20;
                break;

            case TOP:
                dy = -20;
                break;

            case RIGHT:
                dx = 20;
                break;
        }

        ArrayList<Integer> myList = this.get3CardPos();
        for (int i = 0; i < 3; i++) {
            int pos = myList.get(i).intValue();
            JLabel lbl = this.listCardLabel[pos];
            lbl.setLocation(lbl.getLocation().x + dx, lbl.getLocation().y + dy);
        }
    }

    //========================================================================
    //							newRound
    //========================================================================
    public void newRound() {
        this.listCard = new ArrayList<Card>(SOQUANBAI);
        this.scoreCard = new ArrayList<Card>();
    }

    //========================================================================
    //					newGame
    //========================================================================
    public void newGame() {
        this.score = 0;
        this.newRound();
    }
    
    public void clearChangeCard()
    {
        if(list3Card != null)
            list3Card.clear();
        else
            list3Card = new ArrayList<Integer>();
    }
    public void setChangeCard(int ID)
    {        
        list3Card.add(new Integer(ID));     
    }
}

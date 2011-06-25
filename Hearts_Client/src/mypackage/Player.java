package mypackage;

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

    /*
     * Player has two-club card
     */
    public boolean hasTwoClub() {
        for (int i = 0; i < listCard.size(); i++) {
            if (listCard.get(i).isTwoClub()) {
                return true;
            }
        }
        return false;
    }
    
    /*
     * Check valid chosen card
     */
    public boolean isValid(Card c, boolean chatCo, boolean luotDau, String chatBaiKiemTra) {

        if (c == null) {
            return false;
        }
        if("".equals(chatBaiKiemTra))
            isFirst = true;
        
        if (isFirst) {            
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
            if (Card.Suit[c.getSuit()].equals(chatBaiKiemTra)) {
                return true;
            } //Neu c khong cung chat voi kiemtra
            else {
                //Kiem tra xem tren tay co quan nao cung chat voi kiemtra khong
                for (int i = 0; i < listCard.size(); i++) {
                    if (listCard.get(i) != null) {
                        if (Card.Suit[listCard.get(i).getSuit()].equals(chatBaiKiemTra)) {
                            if (this.isHuman()) {
                                Hearts.notice("Ban phai chon 1 la bai chat " + chatBaiKiemTra + ".");
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

    /*
     * Choose a card
     */
    public void play(boolean chatCo, boolean luotDau, String chatBaiKiemTra) {
        switch (type) {
            case IS_COMPUTER:
                Hearts.notice("Den luot choi cua " + this.name + " ...");
                for (int i = 0; i < SOQUANBAI; i++) {
                    int index = this.randomIndex[i];
                    Card comCard = listCard.get(index);
                    if (isValid(comCard, chatCo, luotDau, chatBaiKiemTra)) {
                        this.playCard = comCard;
                        this.listCardLabel[index].setVisible(false);
                        this.listCard.set(index, null);
                        return;
                    }
                }
                break;
            case IS_HUMAN:
                Hearts.notice("Chon 1 la bai de choi.");
                this.playCard = Hearts.chooseACard(this);
                break;
        }
    }

    /*
     * Sort cards
     */
    public void sortCard() {
        //Sắp xếp theo chất
        for (int i = 0; i < listCard.size() - 1; i++) {
            for (int j = i + 1; j < listCard.size(); j++) {
                if (listCard.get(i).getSuit() > listCard.get(j).getSuit()) {
                    Card t = listCard.get(i);
                    listCard.set(i, listCard.get(j));
                    listCard.set(j, t);
                }
            }
        }
        //Sắp xếp theo nước của từng chất
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

    /*
     * Add a card to list of cards
     */
    public void addACard(Card c) {
        if(listCard.size() == 13)
            listCard.clear();
        listCard.add(c);
    }

    /*
     * Show play card's label
     */
    public void showPlayCardLabel(int i, Card c) {
        if (i < 0 || i > playCardLabel.length) {
            return;
        }
        this.playCardLabel[i].setVisible(true);
        this.playCardLabel[i].setIcon(new ImageIcon(c.creatIconFile()));
    }
    
    /*
     * Show list of cards
     */
    public void showListCard(boolean show, int type) {
        if(type == 1)
            Card.PICTURES_FOLDER = "resources/pictures_1/";
        else if(type == 2)
            Card.PICTURES_FOLDER = "resources/pictures_2/";
        else if(type == 3)
            Card.PICTURES_FOLDER = "resources/pictures_3/";
        else if(type == 4)
            Card.PICTURES_FOLDER = "resources/pictures_4/";
        
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
    }


    /*
     * get list3Card
     */
    public ArrayList<Integer> get3CardPos() {
        return list3Card;
    }

    /*
     * Choose 3 cards to exchange
     */
    public void chose3Card() {
        switch (type) {            
            case IS_HUMAN:
                this.list3Card = Hearts.chooseThreeCards();
                break;
        }
    }
     
    /*
     * Pass card
     */
    public void passCard(int turn) {        
        this.chose3Card();
        String Message = "1%" + listCard.get(list3Card.get(0)).toString()
                + "%" + listCard.get(list3Card.get(1)).toString()
                + "%" + listCard.get(list3Card.get(2)).toString();
                Hearts.ConnectF.sendMessage(Message);        
    }
    
    /*
     * Repaint change card after exchanged
     */
    public void repaintChangeCard() {        
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

    /*
     * Start new round
     */
    public void newRound() {
        this.listCard = new ArrayList<Card>(SOQUANBAI);
        this.scoreCard = new ArrayList<Card>();
    }
    
    /*
     * Start new game
     */
    public void newGame() {
        this.score = 0;
        this.newRound();
    }   
}
package mypackage;

public class Card {	
        //Âm thanh
	public static final String SOUND_PLAY_CLICK_CARD = "resources/sound/clickcard.wav";//File name dùng để click lên lá bài
        public static final String SOUND_PLAY_GAMEOVER = "resources/sound/gameOver.wav";//File name dùng để thong bao game over
        public static final String SOUND_PLAY_DEALGAME = "resources/sound/delaCard.wav";//Sound "Chia bai"
        //Số lá bài tổng cộng, chất bài, lá bài trong 1 chất
        public static final int NUM_OF_SUIT = 4;
	public static final int NUM_OF_FACE = 13;
	public static final int NUM_OF_CARD = 52;
	//Nước bài
	public static final int FACE_2 = 0;
	public static final int FACE_3 = 1;
	public static final int FACE_4 = 2;
	public static final int FACE_5 = 3;
	public static final int FACE_6 = 4;
	public static final int FACE_7 = 5;
	public static final int FACE_8 = 6;
	public static final int FACE_9 = 7;
	public static final int FACE_10 = 8;
	public static final int FACE_JACK = 9;
	public static final int FACE_QUEEN = 10;
	public static final int FACE_KING = 11;
	public static final int FACE_ACE = 12;
	//Chất bài
	public static final int SUIT_SPADE = 0;
	public static final int SUIT_CLUB = 1;
	public static final int SUIT_DIAMOND = 2;
	public static final int SUIT_HEART = 3;
	//Vị trí resource	
        public static String PICTURES_FOLDER = "resources/pictures_1/";	
        public static String PICTURES_EXTEND = ".png";	
        public static String BACK_PICTURE = "resources/pictures_back/back_1.png";	
	
	public static final String[] Face = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"};
	public static final String[] Suit = {"Bich", "Chuon", "Ro", "Co"};	
	
	private int face;
	private int suit;
	
	public Card(int face, int suit) {
		this.face = face;
		this.suit = suit;
	}
	
	//setFace
	public void setFace(int face) {
		this.face = face;
	}
	
	//getFace
	public int getFace() {
		return face;
	}

	//setSuit
	public void setSuit(int suit) {
		this.suit = suit;
	}

	//getSuit
	public int getSuit() {
		return suit;
	}

	public String toString() {
		return ( Face[face] + "_" + Suit[suit]);
	}
	public static Card toCard(String message) {
            int myFace = Integer.parseInt(message.substring(0, message.indexOf("_"))) - 2;
            String mySuit = message.substring(message.indexOf("_") + 1);
            for (int i = 0; i < Card.NUM_OF_SUIT; ++i) {
                if (Card.Suit[i].equals(mySuit)) {
                    Card c = new Card(myFace, i);
                    return c;
                }
            }
            return null;
        }
	//equals
	public boolean equals(Card arg0) {
		if(face == arg0.getFace() && suit == arg0.getSuit())
			return true;
		return false;
	}
	
	//isTwoClub
	public boolean isTwoClub() {
		if(equals(new Card(FACE_2, SUIT_CLUB)))
			return true;
		return false;
	}
	
	//isHeart
	public boolean isHeart() {
		if(suit == SUIT_HEART)
			return true;
		return false;		
	}
	
	//isQueenSpade
	public boolean isQueenSpade() {
		if(equals(new Card(FACE_QUEEN, SUIT_SPADE)))
			return true;
		return false;
	}	
	
	//creatIconFile
	public String creatIconFile() {	
            if(face < FACE_2 || face > FACE_ACE || suit < SUIT_SPADE || suit > SUIT_HEART)
			return "";
		return (PICTURES_FOLDER + Face[face] + "_" + Suit[suit] + PICTURES_EXTEND);	
	}
}

package mypackage;

import java.util.ArrayList;
import java.util.Random;


public class Card {
	
	//private static final long serialVersionUID = 1L;
        //Âm thanh
	public static final String SOUND_PLAY_CLICK_CARD = "resources/sound/clickcard.wav";//File name dùng để click lên lá bài
        public static final String SOUND_PLAY_GAMEOVER = "resources/sound/gameOver.wav";//File name dùng để thong bao game over
        public static final String SOUND_PLAY_DEALGAME = "resources/sound/delaCard.wav";//Sound "Chia bai"
        //Số lá bài tổng cộng, chất bài, lá bài trong 1 chất
        public static final int NUM_OF_SUIT = 4;
	public static final int NUM_OF_FACE = 13;
	public static final int NUM_OF_CARD = 52;
	
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
	
	public static final int SUIT_SPADE = 0;
	public static final int SUIT_CLUB = 1;
	public static final int SUIT_DIAMOND = 2;
	public static final int SUIT_HEART = 3;
	
	//public static final String PICTURES_FOLDER = "pictures/";
        public static final String PICTURES_FOLDER = "resources/pictures_1/";
	//public static final String PICTURES_EXTEND = ".gif";
        public static final String PICTURES_EXTEND = ".png";
	//public static final String BACK_PICTURE = "pictures/b.gif";
        public static final String BACK_PICTURE = "resources/pictures_back/back_1.png";
	
	//nuoc cua la bai: 2..10, j, q, k, a
	public static final String[] Face = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"};  
	//public static final String[] faceText = {"TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEEN", "JACK", "QUEEN", "KING", "ACE"};
	
	//chat cua la bai(bich, chuon, ro, co): s(spade), c(club), d(diamond), h(heart)
	public static final String[] Suit = {"Bich", "Chuon", "Ro", "Co"};
	public static final String[] suitText = {"Bich", "Chuon", "Ro", "Co"};
	
	private int face; 	//nuoc: 0..12
	private int suit; 	//chat: 0..3 
	
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
	
	//isGreatThan: Kiem tra mot la bai lon hon mot la bai cung chat
	public boolean isGreatThan(Card c) {
		//Neu c cung chat 
		if(c.getSuit() == suit) {
			//face lon hon thi dung
			if(face > c.getFace()) return true;
			return false;
		}
		return false;
	}
	
	//creatIconFile
	public String creatIconFile() {
		if(face < FACE_2 || face > FACE_ACE || suit < SUIT_SPADE || suit > SUIT_HEART)
			return "";
		return (PICTURES_FOLDER + Face[face] + "_" + Suit[suit] + PICTURES_EXTEND);	
	}
	
	//Thiet lap mot bo bai
	public static ArrayList<Card> creatListCard() {
		
		ArrayList<Card> list = new ArrayList<Card>(NUM_OF_CARD);
		
		//i = [0,52)
		//Gan nuoc cho la bai thu i: i%13 = [0,12]
		//Chat se duoc gan giong nhau cho tung bo 13 la bai nho cau lenh: i/13 = [0,4)
		//Vi 13 khong chia het cho 13 nen co the gan chat bang lenh i%4
		for(int i=0; i<NUM_OF_CARD; i++) {
			int face = i%NUM_OF_FACE;
			int suit = i/NUM_OF_FACE;
			Card I = new Card(face, suit);
			list.add(I);
		}
		
		/*int dem=0;
		for(int i=0; i<NUM_OF_CARD; i++) {
			System.out.print("[ " + list.get(i).getFace() + " , " + list.get(i).getSuit() + "]");
			if(++dem == 13) {
				System.out.println("");
				dem=0;
			}
		}*/
		
		//Hoan doi tung la bai can tron voi mot la bai ngau nhien trong 52 la bai
		Random random = new Random();
		for(int i=0; i<NUM_OF_CARD; i++) {
			int j = random.nextInt(NUM_OF_CARD);
			if(j!=i) {
				Card t = list.get(i);
				list.set(i, list.get(j));
				list.set(j, t);
			}
		}
		
		/*System.out.println("");
		dem=0;
		for(int i=0; i<NUM_OF_CARD; i++) {
			System.out.print("[ " + list.get(i).getFace() + " , " + list.get(i).getSuit() + "]");
			if(++dem == 13) {
				System.out.println("");
				dem=0;
			}
		}*/
		
		return list;
	}
}

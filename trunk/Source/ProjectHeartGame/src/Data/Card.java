/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author TienPhan
 */
public class Card extends DTOConstantCard {
   
   private int faceCard; // Con bài thứ mấy trong bộ bài
   private int suitCard; // Loại bài gì: Spade, club, diamond, heart

   /*
    * Khởi tạo bộ bài
    * faceCard: con thứ mấy trong bộ bài
    * suitCard: nước nào
    */
   public Card(int face, int suit) {
      this.faceCard = face;
      this.suitCard = suit;
   }

   /*
    * Lấy ra thứ tự trong bộ bài.
    */
   public int getFace() {
      return this.faceCard;
   }

   /*
    * Lấy ra nước của bộ bài
    */
   public int getSuit() {
      return this.suitCard;
   }

   /*
    * Gán giá trị của một con bài
    */
   public void setFace(int face) {
      this.faceCard = face;
   }

   /*
    * gán giá trị: là nước
    */
   public void setSuit(int suit) {
      this.suitCard = suit;
   }

   /*
    * Lấy ra tên một lá bài bất kì// toString
    */
   public String getNameCard() {
      return (" " + Face[this.faceCard] + Suit[this.suitCard]);
   }

   /*
    * Kiểm tra coi hai lá bài có giống nhau không// equals
    */
   public boolean equalsCard(Card c) {
      if (this.faceCard == c.getFace() && this.suitCard == c.getSuit()) {
         return true;
      }
      return false;
   }
   /*
    * kiểm tra 1 con bài bất kỳ có phải là 2 chuồn hay không
    */

   public boolean isTwoClub() {
      if (this.equalsCard(new Card(FACE_2, SUIT_CLUB))) {
         return true;
      }
      return false;
   }

   /*
    * Kiểm tra coi một lá bài có phải là nước Cơ hay không
    */
   public boolean isHeart() {
      if ((this.suitCard == SUIT_HEART)) {
         return false;
      }
      return false;
   }

   /*
    * Kiểm tra xem một con có phải là con Đầm Bích không: để sử lý -13 điểm
    */
   public boolean isQueenSpade() {
      if (this.equalsCard(new Card(FACE_QUEEN, SUIT_SPADE))) {
         return true;
      }
      return false;
   }

   /*
    * Kiểm tra xem 1 lá bài (this) có lớn hơn một lá bài c cùng nước hay không
    */
   public boolean isGreatThan(Card c) {
      if (c.getSuit() == suitCard) {
         if (faceCard > c.getFace()) {
            return true;
         }
         return false;
      }
      return false;
   }

   /*
    * Kiểm tra xem nó có nằm trong phạm vi 13 là bài không.
    * Nếu đúng: ta trả về một kiểu String: là chuổi một con bài và đường dẫn
    * Nếu sai: ta trả về "Invalid"
    */
   public String creatIconFile() {
      if (faceCard > FACE_ACE || faceCard < FACE_2 || suitCard > SUIT_HEART || suitCard < SUIT_SPADE) {
         return ("Invalid");
      }
      return (PICTURES_FOLDER + Face[faceCard] + Suit[suitCard] + PICTURES_EXTEND);
   }

   /*
    * Tạo ra danh sách các con bài:
    *    Thiết lập một bộ bài. Xong rồi trộn các con bài lại với nhau.
    */
   public static ArrayList<Card> creatListCard() {
      //Tạo ra các bộ bài
      ArrayList<Card> listNumberCard = new ArrayList<Card>(NUM_OF_CARD); //listNumberCard = 52 lá
      for (int indexCard = 0; indexCard < NUM_OF_CARD; indexCard++) {
         int face = indexCard % NUM_OF_FACE;//faceCard = [0-12]
         int suit = indexCard / NUM_OF_FACE;//suitCard = [0-3]
         Card cardTempl = new Card(face, suit);
         listNumberCard.add(cardTempl);
      }

      //sau đó random chúng đi
      Random randomCard = new Random();
      for (int indexCardCurrent = 0; indexCardCurrent < NUM_OF_CARD; indexCardCurrent++) {//[0-52]
         int indexCardTemp = randomCard.nextInt(NUM_OF_CARD);
         if (indexCardTemp != indexCardCurrent) {
            Card cardTemp = listNumberCard.get(indexCardCurrent);
            listNumberCard.set(indexCardCurrent, listNumberCard.get(indexCardTemp));
            listNumberCard.set(indexCardCurrent, cardTemp);
         }
      }
      return listNumberCard;
   }
}
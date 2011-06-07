/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import BUS.Card;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author TienPhan
 */
public class PicturesCard extends Type {

   //Hình nền 0: default
   public static final String PICTURES_EXTEND_DEFAULT = ".png";
   public static final String PICTURES_FOLDER_DEFAULT = "resources/pictures_1/";
   public static final String THEM_DEFAULT = "resources/pictures_theme/them_1.png";
   public static final String BACK_PICTURE_DEFAULT = "resources/pictures_back/back_1.png";
   //Hình nền 1: win 7_1: size = 16
   public static final String PICTURES_EXTEND_CHANGE_WIN7_1 = ".png";
   public static final String PICTURES_FOLDER_CHANGE_WIN7_1 = "resources/pictures_2/";
   public static final String THEM_WIN7_1 = "resources/pictures_theme/them_1.png";
   public static final String BACK_PICTURE_CHANGE_WIN7_1 = "resources/pictures_back/back_2.png";
   //Hình nền 1: win 7_2: size =14
   public static final String PICTURES_EXTEND_CHANGE_WIN7_2 = ".png";
   public static final String PICTURES_FOLDER_CHANGE_WIN7_2 = "resources/pictures_3/";
   public static final String THEM_WIN7_2 = "resources/pictures_theme/them_1.png";
   public static final String BACK_PICTURE_CHANGE_WIN7_2 = "resources/pictures_back/back_3.png";
   //Hình nền 3: dan gian: size = 12
   public static final String PICTURES_EXTEND_CHANGE_XP = ".png";
   public static final String PICTURES_FOLDER_CHANGE_XP = "resources/pictures_4/";
   public static final String THEM_XP = "resources/pictures_theme/them_1.png";
   public static final String BACK_PICTURE_CHANGE_XP = "resources/pictures_back/back_4.png";
   //Hình nền 4: dan gian: size = 10
   public static final String PICTURES_EXTEND_CHANGE_FOLK = ".png";
   public static final String PICTURES_FOLDER_CHANGE_FOLK = "resources/pictures_5/";
   public static final String THEM_FOLK = "resources/pictures_theme/them_1.png";
   public static final String BACK_PICTURE_CHANGE_FOLK = "resources/pictures_back/back_5.png";
   //Qui định bộ bài
   public static int CARD_QUANTITY = 52;// Qui định một bộ bài gồm 52 lá
   public static int CARD_PLAYER_QUANTITY = 13;// Qui định một nước bài gồm 13 lá
   public static final String[] TABLE_TYPE_CARD_TEXT = {"Bích", "Chuồn", "Rô", "Cơ"};//Mảng qui định các chuổi các
   // con bài để thông báo cho người chơi biết là nên chọn con nào
   //Sound
   public static final String SOUND_PLAY_CLICK_CARD = "resources/sound/clickcard.wav";//File name dùng để click lên lá bài
   public static final String SOUND_PLAY_GAMEOVER = "resources/sound/gameOver.wav";//File name dùng để thong bao game over
   public static final String SOUND_PLAY_DEALGAME = "resources/sound/delaCard.wav";//Sound "Chia bai"

   /**
    * setCARD_PLAYER_QUANTITY(int CARD_PLAYER_QUANTITY)
    * @param CARD_PLAYER_QUANTITY
    */
   public static void setCARD_PLAYER_QUANTITY(int CARD_PLAYER_QUANTITY) {
      PicturesCard.CARD_PLAYER_QUANTITY = CARD_PLAYER_QUANTITY;
   }

   /**
    * setCARD_QUANTITY(int CARD_QUANTITY)
    * @param CARD_QUANTITY
    */
   public static void setCARD_QUANTITY(int CARD_QUANTITY) {
      PicturesCard.CARD_QUANTITY = CARD_QUANTITY;
   }

   /**
    *getCARD_PLAYER_QUANTITY()
    */
   public static int getCARD_PLAYER_QUANTITY() {
      return CARD_PLAYER_QUANTITY;
   }

   /**
    *getCARD_QUANTITY()
    */
   public static int getCARD_QUANTITY() {
      return CARD_QUANTITY;
   }

   /**
    * Tạo ra một bộ bài mặc định là 52 lá.
    * createList52Card()
    * @return
    */
   public static ArrayList<Card> createList52Card() {
      ArrayList<Card> listCardQuantity = new ArrayList<Card>(PicturesCard.CARD_QUANTITY);
      for (int indexCard = 0; indexCard < PicturesCard.CARD_QUANTITY; indexCard++) {
         int rankIndex = indexCard % PicturesCard.CARD_PLAYER_QUANTITY;//rankIndex = [0,12]
         int typeIndex = indexCard / PicturesCard.CARD_PLAYER_QUANTITY;//typeIndex = [0-3]
         Card cardTemp = new Card(rankIndex, typeIndex);
         listCardQuantity.add(cardTemp);
      }
      //Trộn các lá baid lại với nhau
      Random randomCard = new Random();
      for (int indexCard = 0; indexCard < PicturesCard.CARD_QUANTITY; indexCard++) {
         int indexCardTemp = randomCard.nextInt(PicturesCard.CARD_QUANTITY);
         if (indexCardTemp != indexCard) {//Hoán đổi vị trí các lá bài với nhau
            Card cardTemp = listCardQuantity.get(indexCard);
            listCardQuantity.set(indexCard, listCardQuantity.get(indexCardTemp));
            listCardQuantity.set(indexCardTemp, cardTemp);
         }
      }
      return listCardQuantity;// Trả về danh sách 52 lá bài
   }

   /**
    *PicturesCard()
    */
   public PicturesCard() {
   }
}

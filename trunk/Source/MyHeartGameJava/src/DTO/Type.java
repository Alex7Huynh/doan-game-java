/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author TienPhan
 */
public class Type extends Rank {

   private static int TYPE_CARD_SPADE = 0;//Loại bài bích
   private static int TYPE_CARD_CLUB = 1;// Loại bài chuồn
   private static int TYPE_CARD_DIAMOND = 2;// Laọi bài rô
   private static int TYPE_CARD_HEART = 3;// Laọi bài cơ
   private static String[] TABLE_TYPE_CARD = {//Chuổi các ký hiệu dùng để nhận biết loại bài
      "s",
      "c",
      "d",
      "h"
   };

   /**
    * setTABLE_TYPE_CARD(String[] TABLE_TYPE_CARD)
    * @param TABLE_TYPE_CARD
    */
   public static void setTABLE_TYPE_CARD(String[] TABLE_TYPE_CARD) {
      Type.TABLE_TYPE_CARD = TABLE_TYPE_CARD;
   }

   /**
    * setTYPE_CARD_CLUB(int TYPE_CARD_CLUB)
    * @param TYPE_CARD_CLUB
    */
   public static void setTYPE_CARD_CLUB(int TYPE_CARD_CLUB) {
      Type.TYPE_CARD_CLUB = TYPE_CARD_CLUB;
   }

   /**
    * setTYPE_CARD_DIAMOND(int TYPE_CARD_DIAMOND)
    * @param TYPE_CARD_DIAMOND
    */
   public static void setTYPE_CARD_DIAMOND(int TYPE_CARD_DIAMOND) {
      Type.TYPE_CARD_DIAMOND = TYPE_CARD_DIAMOND;
   }

   /**
    * setTYPE_CARD_HEART(int TYPE_CARD_HEART)
    * @param TYPE_CARD_HEART
    */
   public static void setTYPE_CARD_HEART(int TYPE_CARD_HEART) {
      Type.TYPE_CARD_HEART = TYPE_CARD_HEART;
   }

   /**
    * setTYPE_CARD_SPADE(int TYPE_CARD_SPADE)
    * @param TYPE_CARD_SPADE
    */
   public static void setTYPE_CARD_SPADE(int TYPE_CARD_SPADE) {
      Type.TYPE_CARD_SPADE = TYPE_CARD_SPADE;
   }

   /**
    *getTABLE_TYPE_CARD()
    */
   public static String[] getTABLE_TYPE_CARD() {
      return TABLE_TYPE_CARD;
   }

   /**
    * getTYPE_CARD_CLUB()
    */
   public static int getTYPE_CARD_CLUB() {
      return TYPE_CARD_CLUB;
   }

   /**
    *getTYPE_CARD_DIAMOND()
    */
   public static int getTYPE_CARD_DIAMOND() {
      return TYPE_CARD_DIAMOND;
   }

   /**
    * getTYPE_CARD_HEART()
    */
   public static int getTYPE_CARD_HEART() {
      return TYPE_CARD_HEART;
   }

   /**
    *getTYPE_CARD_SPADE()
    */
   public static int getTYPE_CARD_SPADE() {
      return TYPE_CARD_SPADE;
   }

   /**
    *Type()
    */
   public Type() {
   }
}

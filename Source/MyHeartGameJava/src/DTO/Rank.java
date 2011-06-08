/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author TienPhan
 */
public class Rank {

   private static String[] TABLE_RANK_CARD = {// Chuoi các hạng của con bài(2-14)
      "2",//2
      "3",
      "4",
      "5",
      "6",
      "7",
      "8",
      "9",
      "10",
      "11",//J
      "12",//Q
      "13",//K
      "14"};//Ace
   private static int PICTURE_RANK_2 = 0;// hạng của con 2
   private static int PICTURE_RANK_ACE = 12;// hạng của con ace
   private static int PICTURE_RANK_QUEEN = 10;// hạng của con Q

   /**
    * String[] getTABLE_RANK_CARD()
    * @return the TABLE_RANK_CARD
    */
   public static String[] getTABLE_RANK_CARD() {
      return TABLE_RANK_CARD;
   }

   /**
    * setTABLE_RANK_CARD(String[] aTABLE_RANK_CARD)
    * @param aTABLE_RANK_CARD the TABLE_RANK_CARD to set
    */
   public static void setTABLE_RANK_CARD(String[] aTABLE_RANK_CARD) {
      TABLE_RANK_CARD = aTABLE_RANK_CARD;
   }

   /**
    * getPICTURE_RANK_2()
    * @return the PICTURE_RANK_2
    */
   public static int getPICTURE_RANK_2() {
      return PICTURE_RANK_2;
   }

   /**
    * setPICTURE_RANK_2(int aPICTURE_RANK_2)
    * @param aPICTURE_RANK_2 the PICTURE_RANK_2 to set
    */
   public static void setPICTURE_RANK_2(int aPICTURE_RANK_2) {
      PICTURE_RANK_2 = aPICTURE_RANK_2;
   }

   /**
    * getPICTURE_RANK_ACE()
    * @return the PICTURE_RANK_ACE
    */
   public static int getPICTURE_RANK_ACE() {
      return PICTURE_RANK_ACE;
   }

   /**
    * setPICTURE_RANK_ACE(int aPICTURE_RANK_ACE)
    * @param aPICTURE_RANK_ACE the PICTURE_RANK_ACE to set
    */
   public static void setPICTURE_RANK_ACE(int aPICTURE_RANK_ACE) {
      PICTURE_RANK_ACE = aPICTURE_RANK_ACE;
   }

   /**
    * getPICTURE_RANK_QUEEN()
    * @return the PICTURE_RANK_QUEEN
    */
   public static int getPICTURE_RANK_QUEEN() {
      return PICTURE_RANK_QUEEN;
   }

   /**
    * setPICTURE_RANK_QUEEN(int aPICTURE_RANK_QUEEN)
    * @param aPICTURE_RANK_QUEEN the PICTURE_RANK_QUEEN to set
    */
   public static void setPICTURE_RANK_QUEEN(int aPICTURE_RANK_QUEEN) {
      PICTURE_RANK_QUEEN = aPICTURE_RANK_QUEEN;
   }

   /**
    * Rank()
    */
   public Rank() {
   }
}

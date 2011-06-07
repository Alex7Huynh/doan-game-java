/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.*;
import DTO.PicturesCard;

/**
 *
 * @author TienPhan
 */
public class Card extends PicturesCard {

   private int _rankIndex; 	//hạng: 0..12
   private int _typeIndex; 	//type: 0..3

   /**
    * Card(int rankIndex, int typeIndex)
    * @param rankIndex
    * @param typeIndex
    */
   public Card(int rankIndex, int typeIndex) {
      this._rankIndex = rankIndex;
      this._typeIndex = typeIndex;
   }

   /**
    * setRank(int rankIndex)
    * @param rankIndex
    */
   public void setRank(int rankIndex) {
      this._rankIndex = rankIndex;
   }

   /**
    * getRank()
    * @return
    */
   public int getRank() {
      return _rankIndex;
   }

   /**
    * setType(int typeIndex)
    * @param typeIndex
    */
   public void setType(int typeIndex) {
      this._typeIndex = typeIndex;
   }

   /**
    * getType()
    * @return
    */
   public int getType() {
      return _typeIndex;
   }

   /**
    * In ra tên một lá bài bất kỳ
    * toString()
    * @return
    */
   @Override
   public String toString() {
      return (" " + getTABLE_RANK_CARD()[_rankIndex] + getTABLE_TYPE_CARD()[_typeIndex]);
   }

   /**
    * Kiểm tra 2 lá bài có giống nhau không: giống nhau cả về hạng và loại
    * equalCard(Card card)
    * @param card
    * @return
    */
   public boolean equalCard(Card card) {
      if (_rankIndex == card.getRank() && _typeIndex == card.getType()) {
         return true;
      }
      return false;
   }

   /**
    * Kiểm tra một lá bài có phải là con 2 Chuồn hay không: Để sữ lý con 2 chuồn
    * isTwoClubCard()
    * @return
    */
   public boolean isTwoClubCard() {
      if (equalCard(new Card(getPICTURE_RANK_2(), getTYPE_CARD_CLUB()))) {
         return true;
      }
      return false;
   }

   /**
    * Kiểm tra loại có phải là nước cơ hay không
    * isHeartCard()
    * @return
    */
   public boolean isHeartCard() {
      if (_typeIndex == getTYPE_CARD_HEART()) {
         return true;
      }
      return false;
   }

   /**
    * Kiểm tra xe có phải là con đầm bích hay không
    * isQueenSpadeCard()
    * @return
    */
   public boolean isQueenSpadeCard() {
      if (equalCard(new Card(getPICTURE_RANK_QUEEN(), getTYPE_CARD_SPADE()))) {
         return true;
      }
      return false;
   }

   /**
    * So sánh 2 lá bài cùng một Type, con nào lớn hơn
    * isCompareCard(Card card)
    * @param card
    * @return
    */
   public boolean isCompareCard(Card card) {
      if (card.getType() == _typeIndex) {
         if (_rankIndex > card.getRank()) {
            return true;
         }
         return false;
      }
      return false;
   }

   /**
    * Tạo một ảnh các lá bài, trong một fordel qui định sẳn kít thướt của các con bài với nhau
    * Size =1: folder mac dinh
    * Size =2: folder win 7_1
    * Size =2: folder win 7_2
    * Size != : folder folk
    * createImageIconCard(int sizeCard)
    * @param sizeCard
    * @return
    */
   public String createImageIconCard(int sizeCard) {

      switch (sizeCard) {
         case 1: {
            if (_rankIndex < getPICTURE_RANK_2() || _rankIndex > getPICTURE_RANK_ACE() || _typeIndex < getTYPE_CARD_SPADE() || _typeIndex > getTYPE_CARD_HEART()) {
               return "";
            }
            return (PICTURES_FOLDER_CHANGE_WIN7_1 + getTABLE_RANK_CARD()[_rankIndex] + getTABLE_TYPE_CARD()[_typeIndex] + PICTURES_EXTEND_CHANGE_WIN7_1);
         }
         case 2: {
            if (_rankIndex < getPICTURE_RANK_2() || _rankIndex > getPICTURE_RANK_ACE() || _typeIndex < getTYPE_CARD_SPADE() || _typeIndex > getTYPE_CARD_HEART()) {
               return "";
            }
            return (PICTURES_FOLDER_CHANGE_WIN7_2 + getTABLE_RANK_CARD()[_rankIndex] + getTABLE_TYPE_CARD()[_typeIndex] + PICTURES_EXTEND_CHANGE_WIN7_2);
         }
         case 3: {
            if (_rankIndex < getPICTURE_RANK_2() || _rankIndex > getPICTURE_RANK_ACE() || _typeIndex < getTYPE_CARD_SPADE() || _typeIndex > getTYPE_CARD_HEART()) {
               return "";
            }
            return (PICTURES_FOLDER_CHANGE_FOLK + getTABLE_RANK_CARD()[_rankIndex] + getTABLE_TYPE_CARD()[_typeIndex] + PICTURES_EXTEND_CHANGE_FOLK);
         }
         default:
            return (PICTURES_FOLDER_DEFAULT + getTABLE_RANK_CARD()[_rankIndex] + getTABLE_TYPE_CARD()[_typeIndex] + PICTURES_EXTEND_DEFAULT);
      }
   }
}

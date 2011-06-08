/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.PlayerConstant;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author TienPhan
 */
public class Player extends PlayerConstant {

   private String _namePlayer;//tên người chơi
   private int _typePlayer;//loại người chơi: humman hay computer
   int _positionPlayer; //vị trí người chơi
   private Player _nextPlayer;//nguời chơi tiếp theo
   private ArrayList<Card> _listCardPlayer; //danh sách các lá bài của người chơi (13 lá)
   private ArrayList<Card> _listSocreCardPlayer;//Danh sách điểm của người chơi
   private int _scorePlayer = 0;//điểm số được tính trong 1 cuộc chơi
   private boolean _isFirstPlayer;//Kiểm tra coi ai là người đánh bài đầu tiên
   private Card _playCardPlayer;//lá bài của người chơi sẽ được đánh ra
   private JLabel[] _listLabelCardPlayer;//label chứa quân bài trong tay người chơi
   private JLabel[] _listLabelCardPlayedPlayer;//label chứa các quân bài mà người chơi đã đánh ra
   private ArrayList<Integer> _list3CardChangePlayer;// Danh sách 3 lá bài được chọn của humman
   private int[] _randomIndexPlayerComputer;// vị trí các lá bài mà người chơi máy tính chọn
   private int _sizeCard; //Kít thướt các lá bài mà ta muốn thay đổi: 16, 14, 12, 10
   private int dxCard = 0;//la bai lui ra theo toa do x
   private int dyCard = 0;//la bai lui ra theo toa do y

   /**
    * Khoi tao nguoi choi voi: ten, loai, vi tri
    * Player(String namePlayer, int typePlayer, int postionPlayer)
    * @param namePlayer
    * @param typePlayer
    * @param postionPlayer
    */
   public Player(String namePlayer, int typePlayer, int postionPlayer) {
      this._namePlayer = namePlayer;
      this._typePlayer = typePlayer;
      this._positionPlayer = postionPlayer;
      //Moi nguoi choi co 13 la bai
      this._listCardPlayer = new ArrayList<Card>(CARD_PLAYER_QUANTITY);
      //Diem hien tai
      this._listSocreCardPlayer = new ArrayList<Card>();

      this._randomIndexPlayerComputer = new int[CARD_PLAYER_QUANTITY];
      for (int indexCard = 0; indexCard < CARD_PLAYER_QUANTITY; indexCard++) {
         _randomIndexPlayerComputer[indexCard] = indexCard;
      }
      Random random = new Random();
      for (int indexCard = 0; indexCard < CARD_PLAYER_QUANTITY; indexCard++) {
         int indexCardNext = random.nextInt(CARD_PLAYER_QUANTITY);
         int indexCardTemp = _randomIndexPlayerComputer[indexCard];
         _randomIndexPlayerComputer[indexCard] = _randomIndexPlayerComputer[indexCardNext];
         _randomIndexPlayerComputer[indexCardNext] = indexCardTemp;
      }
   }

   /**
    * setNamePlayer(String namePlayer)
    * @param namePlayer
    */
   public void setNamePlayer(String namePlayer) {

      this._namePlayer = namePlayer;
   }

   /**
    * getNamePlayer()
    * @return
    */
   public String getNamePlayer() {
      return _namePlayer;
   }

   /**
    * getListCardPlayer()
    * @return
    */
   public ArrayList<Card> getListCardPlayer() {
      return _listCardPlayer;
   }

   /**
    * Cộng điểm cho người chơi, khi người chơi ăn quân bài
    * addScorePlayer(int scorePlayer)
    * @param scorePlayer
    */
   public void addScorePlayer(int scorePlayer) {
      this._scorePlayer += scorePlayer;
   }

   /**
    * getScorePlayer()
    * @return
    */
   public int getScorePlayer() {
      return _scorePlayer;
   }

   /**
    * setNextPlayer(Player nextPlayer)
    * @param nextPlayer
    */
   public void setNextPlayer(Player nextPlayer) {
      this._nextPlayer = nextPlayer;
   }

   /**
    * getNextPlayer()
    * @return
    */
   public Player getNextPlayer() {
      return _nextPlayer;
   }

   /**
    * setTypePlayer(int typePlayer)
    * @param typePlayer
    */
   public void setTypePlayer(int typePlayer) {
      this._typePlayer = typePlayer;
   }

   /**
    * getTypePlayer()
    * @return
    */
   public int getTypePlayer() {
      return _typePlayer;
   }

   /**
    *  setIsFirstPlayer(boolean isFirstPlayer)
    * @param isFirstPlayer
    */
   public void setIsFirstPlayer(boolean isFirstPlayer) {
      this._isFirstPlayer = isFirstPlayer;
   }

   /**
    * getIsFirstPlayer()
    * @return
    */
   public boolean getIsFirstPlayer() {
      return _isFirstPlayer;
   }

   /**
    * getPLayCardPlayer()
    * @return
    */
   public Card getPLayCardPlayer() {
      return _playCardPlayer;
   }

   /**
    * setListLabelCardPlayer(JLabel[] listLabelCardPlayer)
    * @param listLabelCardPlayer
    */
   public void setListLabelCardPlayer(JLabel[] listLabelCardPlayer) {
      this._listLabelCardPlayer = listLabelCardPlayer;
   }

   /**
    * tra ve toan bo danh sach
    * getListLabelCardPlayer()
    * @return
    */
   public JLabel[] getListLabelCardPlayer() {
      return _listLabelCardPlayer;
   }

   /**
    * setListLabelCardPlayedPlayer(JLabel[] listLabelCardPlayedPlayer)
    * @param listLabelCardPlayedPlayer
    */
   public void setListLabelCardPlayedPlayer(JLabel[] listLabelCardPlayedPlayer) {
      this._listLabelCardPlayedPlayer = listLabelCardPlayedPlayer;
   }

   /**
    * getListLabelCardPlayedPlayer()
    * @return
    */
   public JLabel[] getListLabelCardPlayedPlayer() {
      return _listLabelCardPlayedPlayer;
   }

   /**
    * Tra ve tai vi tri biet truoc
    * getListLabelCardPlayer(int indexLabelCardPlayer)
    * @param indexLabelCardPlayer
    * @return
    */
   public JLabel getListLabelCardPlayer(int indexLabelCardPlayer) {
      return _listLabelCardPlayer[indexLabelCardPlayer];
   }

   /**
    * isHumanPlayer()
    * @return
    */
   public boolean isHumanPlayer() {
      return (this._typePlayer == IS_HUMAN);
   }

   /**
    * kiểm tra trong 13 lá quân bài của người chơi có con 2 chuồn hay không
    * isTwoClubCardPlayer()
    * @return
    * true: có con 2 chuồn
    * false: không có con 2 chuồn
    */
   public boolean isTwoClubCardPlayer() {
      for (int indexCardPlayer = 0; indexCardPlayer < _listCardPlayer.size(); indexCardPlayer++) {
         if (_listCardPlayer.get(indexCardPlayer).isTwoClubCard()) {
            return true;
         }
      }
      return false;
   }

   /**
    * Kiểm tra một lá bài được đánh ra có hợp lệ hay không
    * isValidCardPlayer(Card card, boolean queenPlayed, boolean turnPlayer, Card validCardFirst)
    * @param card: lá bài
    * @param queenPlayed: Cơ có đánh ra chưa
    * @param turnPlayer: Lượt đi
    * @param validCardFirst:Lá mà người chơi đầu tiên đánh ra, bắt buộc người đi sau phải đi theo lá bài này
    * @return
    */
   public boolean isValidCardPlayer(Card card, boolean queenPlayed, boolean turnPlayer, Card validCardFirst) {

      if (card == null) {
         return false;
      }
      //Người đánh đầu tiên: kiểm tra có 2 chuồn hay không
      if (_isFirstPlayer) {
         if (turnPlayer) {
            if (card.isTwoClubCard())//Kiem tra coi co 2 chuon khong
            {
               return true;
            } else {
               if (this.isHumanPlayer()) {
                  Heart.noticeMessage("Bạn đang giữ lá 2 chuồn.\nHãy chọn lá \"2 Chuồn\".");
               }
               return false;
            }
         }
         if (card.getType() != Card.getTYPE_CARD_HEART())//Kiem tra co quan co khong
         {
            return true;
         } else {
            if (queenPlayed) {//Nếu quân cơ được đánh ra rồi
               return true;
            } else {
               for (int indexCard = 0; indexCard < _listCardPlayer.size(); indexCard++) {
                  if (_listCardPlayer.get(indexCard) != null) {
                     if (!_listCardPlayer.get(indexCard).isHeartCard()) {
                        if (this.isHumanPlayer()) {
                           Heart.noticeMessage("Bạn chưa được chọn lá bài \"Cơ\"");
                        }
                        return false;
                     }
                  }
               }
               return true;
            }
         }
      } //Nếu là người đánh sau
      else {// Nếu card của người đánh có type cùng với type của validCardFirst
         if (card.getType() == validCardFirst.getType()) {
            return true;
         } // Nếu card của người đánh không cùng type với type của validCardFirst
         else {
            for (int indexCard = 0; indexCard < _listCardPlayer.size(); indexCard++) {
               if (_listCardPlayer.get(indexCard) != null) {
                  if (_listCardPlayer.get(indexCard).getType() == validCardFirst.getType()) {
                     if (this.isHumanPlayer()) {
                        Heart.noticeMessage("Bạn phải chọn lá " + Card.TABLE_TYPE_CARD_TEXT[validCardFirst.getType()]);
                     }
                     return false;
                  }
               }
            }
            //Nếu trong tay ngừoi chơi không có quân nào cùng với quân validCardFirst
            if (!card.isHeartCard() && !card.isQueenSpadeCard()) {
               return true;
            }
            if (card.isHeartCard()) {
               for (int indexCard = 0; indexCard < _listCardPlayer.size(); indexCard++) {
                  if (_listCardPlayer.get(indexCard) != null) {
                     if (!_listCardPlayer.get(indexCard).isHeartCard()) {
                        if (turnPlayer) {
                           if (this.isHumanPlayer()) {
                              Heart.noticeMessage("Bạn không được chọn con \"Cơ\".");
                           }
                           return false;
                        } else {
                           return true;
                        }
                     }
                  }
               }
               return true;
            }
            if (card.isQueenSpadeCard()) { //Nếu quân đầm bích
               if (turnPlayer) {
                  for (int indexCard = 0; indexCard < _listCardPlayer.size(); indexCard++) {
                     if (_listCardPlayer.get(indexCard) != null) {
                        if (!_listCardPlayer.get(indexCard).isHeartCard()) {
                           return true;
                        }
                     }
                  }
                  if (this.isHumanPlayer()) {
                     Heart.noticeMessage("Không được chọn con \"Q bích\".");
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

   /**
    * xác đinh người chơi đánh ra quân bài gì
    * playCardPlayer(boolean queenPlayer, boolean turnPlayer, Card validCardFirst)
    * @param queenPlayer
    * @param turnPlayer
    * @param validCardFirst
    */
   public void playCardPlayer(boolean queenPlayer, boolean turnPlayer, Card validCardFirst) {
      switch (_typePlayer) {
         case IS_COMPUTER:
            Heart.noticeMessage("Đến lượt của:\'" + this._namePlayer + "\'");
            for (int indexCard = 0; indexCard < CARD_PLAYER_QUANTITY; indexCard++) {
               int indexCardNext = this._randomIndexPlayerComputer[indexCard];
               Card cardPlayed = _listCardPlayer.get(indexCardNext);
               if (isValidCardPlayer(cardPlayed, queenPlayer, turnPlayer, validCardFirst)) {
                  this._playCardPlayer = cardPlayed;
                  this._listLabelCardPlayer[indexCardNext].setVisible(false);
                  this._listCardPlayer.set(indexCardNext, null);
                  return;
               }
            }
            break;
         case IS_HUMAN:
            Heart.noticeMessage("Xin bạn vui lòng chọn 1 lá bài để đánh.");
            this._playCardPlayer = Heart.humanPlay(this);
            break;
      }
   }

   /**
    * Sắp xếp một bộ bài:
    * sortCardPlayer()
    */
   public void sortCardPlayer() {
      for (int indexCardCurrent = 0; indexCardCurrent < _listCardPlayer.size() - 1; indexCardCurrent++) {
         for (int indexCardNext = indexCardCurrent + 1; indexCardNext < _listCardPlayer.size(); indexCardNext++) {
            if (_listCardPlayer.get(indexCardCurrent).getType() > _listCardPlayer.get(indexCardNext).getType()) {
               Card cardTemp = _listCardPlayer.get(indexCardCurrent);
               _listCardPlayer.set(indexCardCurrent, _listCardPlayer.get(indexCardNext));
               _listCardPlayer.set(indexCardNext, cardTemp);
            }
         }
      }
      for (int indexCardCurrent = 0; indexCardCurrent < _listCardPlayer.size() - 1; indexCardCurrent++) {
         for (int indexCardNext = indexCardCurrent + 1; indexCardNext < _listCardPlayer.size(); indexCardNext++) {
            if ((_listCardPlayer.get(indexCardCurrent).getType() == _listCardPlayer.get(indexCardNext).getType()) && (_listCardPlayer.get(indexCardCurrent).getRank() > _listCardPlayer.get(indexCardNext).getRank())) {
               Card cardTemp = _listCardPlayer.get(indexCardCurrent);
               _listCardPlayer.set(indexCardCurrent, _listCardPlayer.get(indexCardNext));
               _listCardPlayer.set(indexCardNext, cardTemp);
            }
         }
      }
   }

   /**
    * addACardPlayer(Card card)
    * @param card
    */
   public void addACardPlayer(Card card) {
      _listCardPlayer.add(card);

   }

   /**
    * removeACardPlayer(int indexCard)
    * @param indexCard
    */
   public void removeACardPlayer(int indexCard) {
      _listCardPlayer.removeAll(_listCardPlayer);
   }

   /**
    * Neu la quan co + 1 diem
    * Neu la quan dam bich + 13 d
    * addAScoreCardPlayer(Card card)
    * @param card
    */
   public void addAScoreCardPlayer(Card card) {
      if (card.isHeartCard() || card.isQueenSpadeCard()) {
         _listSocreCardPlayer.add(card);
         if (card.isHeartCard()) {
            _scorePlayer += 1;
         }
         if (card.isQueenSpadeCard()) {
            _scorePlayer += 13;
         }
      }
   }

   /**
    * Neu danh sach diem cua nguoi choi luu 14 la bai
    * Tim neu nguoi choi thang duoc 14 la bai
    * thi se shoot the moon
    * isShootTheMoonPlayer()
    * @return
    */
   public boolean isShootTheMoonPlayer() {
      if (_listSocreCardPlayer.size() == 14) {//Xem dnh sach diem co 14 bai thang duoc hay khong
         return true;
      }
      return false;
   }

   /**
    * Hiển thị label quân bài đánh ra
    * showListLabelCardPlayedPlayer(int indexLabelCard, Card card, int sizeCard)
    * @param indexLabelCard
    * @param card
    * @param sizeCard
    */
   public void showListLabelCardPlayedPlayer(int indexLabelCard, Card card, int sizeCard) {
      if (indexLabelCard < 0 || indexLabelCard > _listLabelCardPlayedPlayer.length) {
         return;
      }
      this._listLabelCardPlayedPlayer[indexLabelCard].setVisible(true);
      this._listLabelCardPlayedPlayer[indexLabelCard].setIcon(new ImageIcon(card.createImageIconCard(sizeCard)));
   }

   /**
    * hien thi danh sach cac la bai cua nguoi choi
    * showListLabelCardPlayer(boolean isShowListCard, int sizeCard)
    *  Hiển thị các quân bài trên tay người chơi
    * @param isShowListCard
    * @param sizeCard
    */
   public void showListLabelCardPlayer(boolean isShowListCard, int sizeCard) {
      int indexCardCurrent;
      for (indexCardCurrent = 0; indexCardCurrent < _listCardPlayer.size(); indexCardCurrent++) {
         Card c = _listCardPlayer.get(indexCardCurrent);
         if (c != null) {
            if (isShowListCard) {
               this._listLabelCardPlayer[indexCardCurrent].setIcon(new ImageIcon(c.createImageIconCard(sizeCard)));
            } else {
               if (sizeCard == 1) {
                  this._listLabelCardPlayer[indexCardCurrent].setIcon(new ImageIcon(Card.BACK_PICTURE_CHANGE_WIN7_1));
               }
               if (sizeCard == 2) {
                  this._listLabelCardPlayer[indexCardCurrent].setIcon(new ImageIcon(Card.BACK_PICTURE_CHANGE_WIN7_2));
               }
               if (sizeCard == 3) {
                  this._listLabelCardPlayer[indexCardCurrent].setIcon(new ImageIcon(Card.BACK_PICTURE_CHANGE_XP));
               }
               if (sizeCard == 4) {
                  this._listLabelCardPlayer[indexCardCurrent].setIcon(new ImageIcon(Card.BACK_PICTURE_CHANGE_FOLK));
               }
               if (sizeCard == 5) {
                  this._listLabelCardPlayer[indexCardCurrent].setIcon(new ImageIcon(Card.BACK_PICTURE_DEFAULT));
               }
            }
            this._listLabelCardPlayer[indexCardCurrent].setVisible(true);
         }
      }
      this._listLabelCardPlayer[Player.CARD_PLAYER_QUANTITY].setVisible(false);
   }

   /**
    * Hiển thị các là bài thắng được của người chơi
    * showListScoreCardPlayer(int sizeCard)
    * @param sizeCard
    */
   public void showListScoreCardPlayer(int sizeCard) {
      if (_listSocreCardPlayer == null || _listSocreCardPlayer.isEmpty()) {
         return;
      }
      for (int i = 0; i < _listSocreCardPlayer.size(); i++) {
         Card c = _listSocreCardPlayer.get(i);
         this._listLabelCardPlayer[i].setIcon(new ImageIcon(c.createImageIconCard(sizeCard)));
         this._listLabelCardPlayer[i].setVisible(true);
      }
      for (int i = _listSocreCardPlayer.size(); i < _listLabelCardPlayer.length; i++) {
         this._listLabelCardPlayer[i].setVisible(false);
      }
      if (_listSocreCardPlayer.size() > _listLabelCardPlayer.length) {
         this._listLabelCardPlayer[CARD_PLAYER_QUANTITY].setVisible(false);
      }
   }

   /**
    * get3CardPos()
    * @return
    */
   public ArrayList<Integer> get3CardPos() {
      return _list3CardChangePlayer;
   }

   /**
    * Chọn ra ngẫu nhiên 3 lá bài
    * choseList3CardChangePlayer()
    */
   public void choseList3CardChangePlayer() {

      switch (_typePlayer) {
         case IS_COMPUTER:
            Random randomCard = new Random();
            ArrayList<Integer> listPosition = new ArrayList<Integer>(CARD_PLAYER_QUANTITY);
            for (int indexCard = 0; indexCard < CARD_PLAYER_QUANTITY; indexCard++) {
               listPosition.add(new Integer(indexCard));
            }
            for (int indexCardCurrent = 0; indexCardCurrent < CARD_PLAYER_QUANTITY; indexCardCurrent++) {
               int indexCardNext = randomCard.nextInt(CARD_PLAYER_QUANTITY);
               Integer positionCard = listPosition.get(indexCardCurrent);
               listPosition.set(indexCardCurrent, listPosition.get(indexCardNext));
               listPosition.set(indexCardNext, positionCard);
            }
            switch (this._positionPlayer) {

               case DOWN_PLAYER:
                  dyCard = -20;
                  break;

               case LEFT_PLAYER:
                  dxCard = 20;
                  break;

               case TOP_PLAYER:
                  dyCard = 20;
                  break;

               case RIGHT_PLAYER:
                  dxCard = -20;
                  break;
            }

            ArrayList<Integer> index3Card = new ArrayList<Integer>(3);
            for (int indexCard = 0; indexCard < 3; indexCard++) {
               int positionCard = listPosition.get(indexCard).intValue();
               index3Card.add(listPosition.get(indexCard));
               JLabel lblCardTemp = this._listLabelCardPlayer[positionCard];
               lblCardTemp.setLocation(lblCardTemp.getLocation().x + dxCard, lblCardTemp.getLocation().y + dyCard);
            }
            this._list3CardChangePlayer = index3Card;
            dxCard = 0;
            dyCard = 0;
            break;
         case IS_HUMAN:
            this._list3CardChangePlayer = Heart.humanChose3Card();
            break;
      }
   }

   /**
    * Thay đổi 3 lá bài được chọn
    * changeList3CardChangePlayer(Player player, int sizeCard)
    * @param player
    * @param sizeCard
    */
   public void changeList3CardChangePlayer(Player player, int sizeCard) {

      ArrayList<Integer> list3CardCurrent = this.get3CardPos();
      ArrayList<Integer> list3CardChange = player.get3CardPos();

      Card card3PlayerCurrent, card3PlayerChange;
      int indexCurrent, indexChange;

      for (int indexCard = 0; indexCard < 3; indexCard++) {
         indexCurrent = list3CardCurrent.get(indexCard).intValue();
         card3PlayerCurrent = this.getListCardPlayer().get(indexCurrent);
         indexChange = list3CardChange.get(indexCard).intValue();
         card3PlayerChange = player.getListCardPlayer().get(indexChange);
         this.getListCardPlayer().set(indexCurrent, card3PlayerChange);
         player.getListCardPlayer().set(indexChange, card3PlayerCurrent);
         if (this.isHumanPlayer()) {
            this.getListLabelCardPlayer()[indexCurrent].setIcon(new ImageIcon(card3PlayerChange.createImageIconCard(sizeCard)));
         } else {
            if (sizeCard == 1) {
               this.getListLabelCardPlayer()[indexCurrent].setIcon(new ImageIcon(Card.BACK_PICTURE_CHANGE_WIN7_1));
            }
            if (sizeCard == 2) {
               this.getListLabelCardPlayer()[indexCurrent].setIcon(new ImageIcon(Card.BACK_PICTURE_CHANGE_WIN7_2));
            }
            if (sizeCard == 3) {
               this.getListLabelCardPlayer()[indexCurrent].setIcon(new ImageIcon(Card.BACK_PICTURE_CHANGE_FOLK));
            }
            if (sizeCard == 4) {
               this.getListLabelCardPlayer()[indexCurrent].setIcon(new ImageIcon(Card.BACK_PICTURE_DEFAULT));
            }
         }

         if (player.isHumanPlayer()) {
            player.getListLabelCardPlayer()[indexChange].setIcon(new ImageIcon(card3PlayerCurrent.createImageIconCard(sizeCard)));
         } else {
            if (sizeCard == 1) {
               player.getListLabelCardPlayer()[indexChange].setIcon(new ImageIcon(Card.BACK_PICTURE_CHANGE_WIN7_1));
            }
            if (sizeCard == 2) {
               player.getListLabelCardPlayer()[indexChange].setIcon(new ImageIcon(Card.BACK_PICTURE_CHANGE_WIN7_2));
            }
            if (sizeCard == 3) {
               player.getListLabelCardPlayer()[indexChange].setIcon(new ImageIcon(Card.BACK_PICTURE_CHANGE_FOLK));
            }
            if (sizeCard == 4) {
               player.getListLabelCardPlayer()[indexChange].setIcon(new ImageIcon(Card.BACK_PICTURE_DEFAULT));
            }
         }
      }
   }

   /**
    * Đổi lá bài cho người chơi ở các vị trí:
    * Left, top, right
    * passCardPlayer(int turnPassCard)
    * @param turnPassCard
    */
   public void passCardPlayer(int turnPassCard) {
      Player leftPlayer, topPlayer, rightPlayer;
      leftPlayer = this.getNextPlayer();
      topPlayer = leftPlayer.getNextPlayer();
      rightPlayer = topPlayer.getNextPlayer();

      leftPlayer.choseList3CardChangePlayer();
      topPlayer.choseList3CardChangePlayer();
      rightPlayer.choseList3CardChangePlayer();
      this.choseList3CardChangePlayer();

      switch (turnPassCard) {
         case 0:
            this.changeList3CardChangePlayer(leftPlayer, _sizeCard);
            topPlayer.changeList3CardChangePlayer(rightPlayer, _sizeCard);
            break;
         case 1:
            this.changeList3CardChangePlayer(rightPlayer, _sizeCard);
            topPlayer.changeList3CardChangePlayer(leftPlayer, _sizeCard);
            break;
         case 2:
            this.changeList3CardChangePlayer(topPlayer, _sizeCard);
            leftPlayer.changeList3CardChangePlayer(rightPlayer, _sizeCard);
            break;
      }
   }

   /**
    *repaintList3CardChangePlayer()
    */
   public void repaintList3CardChangePlayer() {
      switch (this._positionPlayer) {

         case DOWN_PLAYER:
            dyCard = 20;
            break;

         case LEFT_PLAYER:
            dxCard = -20;
            break;

         case TOP_PLAYER:
            dyCard = -20;
            break;

         case RIGHT_PLAYER:
            dxCard = 20;
            break;
      }

      ArrayList<Integer> index3Card = this.get3CardPos();
      for (int indexCard = 0; indexCard < 3; indexCard++) {
         int positionCard = index3Card.get(indexCard).intValue();
         JLabel lblCardTemp = this._listLabelCardPlayer[positionCard];
         lblCardTemp.setLocation(lblCardTemp.getLocation().x + dxCard, lblCardTemp.getLocation().y + dyCard);
      }
      dxCard = 0;
      dyCard = 0;
   }

   /**
    *newRoundPlayer()
    */
   public void newRoundPlayer() {
      this._listCardPlayer = new ArrayList<Card>(CARD_PLAYER_QUANTITY);
      this._listSocreCardPlayer = new ArrayList<Card>();
   }

   /**
    *newGamePlayer()
    */
   public void newGamePlayer() {
      this._scorePlayer = 0;
      this.newRoundPlayer();
   }
}

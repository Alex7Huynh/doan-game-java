/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import Presentation.PresentationGame;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author TienPhan
 */
public final class Heart extends PresentationGame {

   public Player _firstGamePlayer;//Người chơi đầu tiên
   public static Card _validCard;//Lá bài của người chơi trong lượt đấu(Humman)
   public Card _nextCardPlayer1;// Các lá bài tiếp theo của tiếp theo
   public Card _nextCardPlayer2;// Các lá bài tiếp theo của tiếp theo
   public Card _nextCardPlayer3;// Các lá bài tiếp theo của tiếp theo
   public static boolean _queenPlayer;// Có đánh ra con Cơ chưa
   public static boolean _turnPlayer;//Lượt đánh của người chơi
   public final int SCORE_SHOOT_MOON = 26;// Điểm đê shoot the moon: 26
   public final int SCORE_MAX = 100;// Điểm để thắng: 100
   public static boolean _isStartNewRound = false;// Xác định vòng đấu kế tiếp
   public int PASS_NUMBER;//Lượt pass bài của humman vói các người chơi còn lại

   public Heart() {
      initNamePlayGame();
      setMainFrame();
      setMenuBar();
      setMenuGame();
      setMenuOption();
      setMenuConnectServer();
      setMenuHelp();
      setLabelNamePlayer();
      setLabelCardPlay();
      setDimensionLabelCard();
      setbntRound();
      setPanelNote();
      setVisibleGame();
   }

   /**
    *dealCard()
    */
   public void dealCard() {
      if (this.FLAG_HUMMAN != 0) {
         special_Deal_Card();
         return;
      }
      ArrayList<Card> cardList = Card.createList52Card();
      Card c;
      for (int i = 0; i < 52; i += 4) {

         //_nameBottomPlayer
         c = cardList.get(i);
         _nameBottomPlayer.addACardPlayer(c);

         //_nameLeftPlayer
         c = cardList.get(i + 1);
         _nameLeftPlayer.addACardPlayer(c);

         //_nameTopPlayer
         c = cardList.get(i + 2);
         _nameTopPlayer.addACardPlayer(c);

         //_nameRightPlayer
         c = cardList.get(i + 3);
         _nameRightPlayer.addACardPlayer(c);
      }
      _nameBottomPlayer.showListLabelCardPlayer(true, _sizeChangeCard);
      _nameLeftPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
      _nameTopPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
      _nameRightPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
   }

   /**
    * humanPlay(Player p)
    * @param p
    * @return
    */
   public static Card humanPlay(Player p) {
      Card c;
      while (true) {
         if (_indexLabelCardHuman != -1) {
            c = p.getListCardPlayer().get(_indexLabelCardHuman);
            if (p.isValidCardPlayer(c, _queenPlayer, _turnPlayer, _validCard)) {
               p.getListCardPlayer().set(_indexLabelCardHuman, null);
               break;
            }
         }
      }
      p.getListLabelCardPlayer()[_indexLabelCardHuman].setVisible(false);
      _indexLabelCardHuman = -1;
      return c;
   }

   /**
    * findFisrtPlayer()
    * @return
    */
   public Player findFisrtPlayer() {

      this._isRunningGame = true;
      if (_turnPlayer) {
         if (_nameBottomPlayer.isTwoClubCardPlayer()) {
            return _nameBottomPlayer;
         }
         if (_nameRightPlayer.isTwoClubCardPlayer()) {
            return _nameRightPlayer;
         }
         if (_nameLeftPlayer.isTwoClubCardPlayer()) {
            return _nameLeftPlayer;
         }
         if (_nameTopPlayer.isTwoClubCardPlayer()) {
            return _nameTopPlayer;
         }
      } else {
         Card maxCard = _validCard;
         if (_nextCardPlayer1.isCompareCard(maxCard)) {
            maxCard = _nextCardPlayer1;
         }
         if (_nextCardPlayer2.isCompareCard(maxCard)) {
            maxCard = _nextCardPlayer2;
         }
         if (_nextCardPlayer3.isCompareCard(maxCard)) {
            maxCard = _nextCardPlayer3;
         }

         if (_nameBottomPlayer.getPLayCardPlayer().equalCard(maxCard)) {
            return _nameBottomPlayer;
         }
         if (_nameRightPlayer.getPLayCardPlayer().equalCard(maxCard)) {
            return _nameRightPlayer;
         }
         if (_nameLeftPlayer.getPLayCardPlayer().equalCard(maxCard)) {
            return _nameLeftPlayer;
         }
         if (_nameTopPlayer.getPLayCardPlayer().equalCard(maxCard)) {
            return _nameTopPlayer;
         }
      }
      return null;
   }

   /**
    * move(JLabel[] listLabel, int dx, int dy)
    * @param listLabel
    * @param dx
    * @param dy
    */
   public void move(JLabel[] listLabel, int dx, int dy) {
      for (int index = 0; index < listLabel.length; index++) {
         JLabel lbl = listLabel[index];
         for (int i = 0; i < 10; i++) {
            lbl.setLocation(lbl.getLocation().x + dx, lbl.getLocation().y + dy);

            try {
               Thread.sleep(_speedMoveCard);
            } catch (InterruptedException e) {
            }
         }
      }
   }

   /**
    * Di chuyển tổng quát
    * move(Player p, Player winner)
    * @param p
    * @param winner
    */
   public void move(Player p, Player winner) {

      JLabel[] lbl = new JLabel[4];
      lbl[0] = p.getListLabelCardPlayedPlayer()[0];
      lbl[1] = p.getNextPlayer().getListLabelCardPlayedPlayer()[1];
      lbl[2] = p.getNextPlayer().getNextPlayer().getListLabelCardPlayedPlayer()[2];
      lbl[3] = p.getNextPlayer().getNextPlayer().getNextPlayer().getListLabelCardPlayedPlayer()[3];

      Point[] point = new Point[4];
      for (int i = 0; i < lbl.length; i++) {
         lbl[i].setVisible(true);
         point[i] = lbl[i].getLocation();
      }

      if (winner == _nameBottomPlayer) {
         move(lbl, 0, 20);  	//_nameBottomPlayer
      }
      if (winner == _nameLeftPlayer) {
         move(lbl, -20, 0);  //_nameLeftPlayer
      }
      if (winner == _nameTopPlayer) {
         move(lbl, 0, -20);  //_nameTopPlayer
      }
      if (winner == _nameRightPlayer) {
         move(lbl, 20, 0); 	//_nameRightPlayer
      }
      for (int i = 0; i < lbl.length; i++) {
         lbl[i].setVisible(false);
         lbl[i].setLocation(point[i]);
      }
   }

   /**
    * whoShootTheMoon()
    * @return
    */
   public Player whoShootTheMoon() {
      if (_nameBottomPlayer.isShootTheMoonPlayer()) {
         return _nameBottomPlayer;
      }
      if (_nameRightPlayer.isShootTheMoonPlayer()) {
         return _nameRightPlayer;
      }
      if (_nameLeftPlayer.isShootTheMoonPlayer()) {
         return _nameLeftPlayer;
      }
      if (_nameTopPlayer.isShootTheMoonPlayer()) {
         return _nameTopPlayer;
      }
      return null;
   }

   /**
    *checkShootThenMoon()
    */
   public void checkShootThenMoon() {
      Player p = this.whoShootTheMoon();
      if (p != null) {
         p.addScorePlayer(-SCORE_SHOOT_MOON);
         p.getNextPlayer().addScorePlayer(SCORE_SHOOT_MOON);
         p.getNextPlayer().getNextPlayer().addScorePlayer(SCORE_SHOOT_MOON);
         p.getNextPlayer().getNextPlayer().getNextPlayer().addScorePlayer(SCORE_SHOOT_MOON);
      }
   }

   /**
    * whoIsMinScore()
    * @return
    */
   public Player whoIsMinScore() {
      Player p = _nameBottomPlayer;
      if (p.getScorePlayer() > _nameLeftPlayer.getScorePlayer()) {
         p = _nameLeftPlayer;
      }
      if (p.getScorePlayer() > _nameTopPlayer.getScorePlayer()) {
         p = _nameTopPlayer;
      }
      if (p.getScorePlayer() > _nameRightPlayer.getScorePlayer()) {
         p = _nameRightPlayer;
      }
      return p;
   }

   /**
    * whoIs100Score()
    * @return
    */
   public Player whoIs100Score() {
      if (_nameBottomPlayer.getScorePlayer() >= SCORE_MAX) {
         return _nameBottomPlayer;
      }
      if (_nameLeftPlayer.getScorePlayer() >= SCORE_MAX) {
         return _nameLeftPlayer;
      }
      if (_nameTopPlayer.getScorePlayer() >= SCORE_MAX) {
         return _nameTopPlayer;
      }
      if (_nameRightPlayer.getScorePlayer() >= SCORE_MAX) {
         return _nameRightPlayer;
      }
      return null;
   }

   /**
    * check100Score()
    * @return
    */
   public boolean check100Score() {
      Player p = this.whoIs100Score();
      if (p != null) {
         Player winner = this.whoIsMinScore();
         _txtNoticeScore += "Xin chúc mừng \'"+ winner.getNamePlayer() + "\' bạn là người thắng cuộc!";
         this.showGameScore("Score");
         this.newGame();
         return true;
      }
      return false;
   }

   /**
    *setTxtScore()
    */
   public void setTxtScore() {
      _txtNoticeScore += "  " + ((_nameBottomPlayer.getScorePlayer() < 10) ? ("  " + _nameBottomPlayer.getScorePlayer()) : (_nameBottomPlayer.getScorePlayer())) + "                ";
      _txtNoticeScore += (_nameLeftPlayer.getScorePlayer() < 10 ? ("  " + _nameLeftPlayer.getScorePlayer()) : (_nameLeftPlayer.getScorePlayer())) + "                 ";
      _txtNoticeScore += (_nameTopPlayer.getScorePlayer() < 10 ? ("  " + _nameTopPlayer.getScorePlayer()) : (_nameTopPlayer.getScorePlayer())) + "                ";//
      _txtNoticeScore += (_nameRightPlayer.getScorePlayer() < 10 ? ("  " + _nameRightPlayer.getScorePlayer()) : (_nameRightPlayer.getScorePlayer())) + "\n";
   }

   /**
    *scoring()
    */
   public void scoring() {
      //Hien thi danh sach quan bai quan bai an duoc cua 4 nguoi choi
      _nameBottomPlayer.showListScoreCardPlayer(_sizeChangeCard);
      _nameRightPlayer.showListScoreCardPlayer(_sizeChangeCard);
      _nameTopPlayer.showListScoreCardPlayer(_sizeChangeCard);
      _nameLeftPlayer.showListScoreCardPlayer(_sizeChangeCard);
      this.checkShootThenMoon();
      this.setTxtScore();
      if (!this.check100Score()) {
         this.showGameScore("Score");
      }
   }

   /**
    * humanChose3Card()
    * @return
    */
   public static ArrayList<Integer> humanChose3Card() {
      _indexLabelCardHuman = -1;
      _isClickLabelHuman = true;
      _is3Card = true;

      for (int i = 0; i < _flagPlay.length; i++) {
         _flagPlay[i] = false;
      }
      _indexPositon.clear();

      _bntNewRound.setVisible(true);
      _bntNewRound.setEnabled(false);
      _isStartNewRound = false;
      while (!_isStartNewRound) {
         if (_indexPositon.size() != 3) {
            _bntNewRound.setEnabled(false);
         } else {
            _bntNewRound.setEnabled(true);
         }
      }

      _indexLabelCardHuman = -1;
      _isClickLabelHuman = false;
      _is3Card = false;
      return _indexPositon;
   }

   /**
    *checkStartNewRound()
    */
   public void checkStartNewRound() {

      //chia bai
      this.dealCard();
      PASS_NUMBER = -1;
      //Neu khong xet cac truong hop dac biet
      if (this.FLAG_HUMMAN == 0) {

         String txt = "Bạn hãy chọn 3 lá bài tùy ý,\nđể đổi cho đối thủ.";
         PASS_NUMBER = (++PASS_NUMBER) % 4;

         switch (PASS_NUMBER) {
            case 0:
               txt += _nameLeftPlayer.getNamePlayer();
               _bntNewRound.setText("Pass Left");
               break;

            case 1:
               txt += _nameRightPlayer.getNamePlayer();
               _bntNewRound.setText("Pass Right");
               break;

            case 2:
               txt += _nameTopPlayer.getNamePlayer();
               _bntNewRound.setText("Pass Cross");
               break;
         }
         notice(txt);
         _bntNewRound.setVisible(true);

         //Thus hien pass card
         if (PASS_NUMBER >= 0 && PASS_NUMBER <= 2) {
            _nameBottomPlayer.passCardPlayer(PASS_NUMBER);

            notice("Nhấn OK để chơi game.");
            _bntNewRound.setText("OK");

            _isStartNewRound = false;
            while (!_isStartNewRound) {
            }

            _nameBottomPlayer.repaintList3CardChangePlayer();
            _nameLeftPlayer.repaintList3CardChangePlayer();
            _nameTopPlayer.repaintList3CardChangePlayer();
            _nameRightPlayer.repaintList3CardChangePlayer();
         }

         _bntNewRound.setVisible(false);

      }
      //Sap xep cac la bai cua 4 nguoi choi
      _nameBottomPlayer.sortCardPlayer();
      _nameLeftPlayer.sortCardPlayer();
      _nameTopPlayer.sortCardPlayer();
      _nameRightPlayer.sortCardPlayer();
   }

   /**
    *newRound()
    */
   public void newRound() {

      Player p1, p2 = null, p3 = null, p4 = null;

      _menuItemGame_ShowCard.setText("Show Card");

      _nameBottomPlayer.newRoundPlayer();
      _nameLeftPlayer.newRoundPlayer();
      _nameTopPlayer.newRoundPlayer();
      _nameRightPlayer.newRoundPlayer();

      this.checkStartNewRound();

      _nameBottomPlayer.showListLabelCardPlayer(true, _sizeChangeCard);
      _nameLeftPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
      _nameTopPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
      _nameRightPlayer.showListLabelCardPlayer(false, _sizeChangeCard);
      _queenPlayer = false;
      _turnPlayer = true;
      _isClickLabelHuman = true;

      //nguoi choi dau tien trong vong
      _firstGamePlayer = findFisrtPlayer();

      for (int i = 0; i < Player.CARD_PLAYER_QUANTITY; i++) {

         p1 = _firstGamePlayer;
         //System.out.println(p1.getNamePlayer() + " is fisrt");
         p2 = p1.getNextPlayer();
         p3 = p2.getNextPlayer();
         p4 = p3.getNextPlayer();

         p1.setIsFirstPlayer(true);
         p2.setIsFirstPlayer(false);
         p3.setIsFirstPlayer(false);
         p4.setIsFirstPlayer(false);

         //_firstGamePlayer danh dau tien
         p1.playCardPlayer(_queenPlayer, _turnPlayer, _validCard);
         _validCard = p1.getPLayCardPlayer();
         p1.showListLabelCardPlayedPlayer(0, _validCard, _sizeChangeCard);
         if (p1.isHumanPlayer()) {
            _isClickLabelHuman = false;
         }
         // System.out.println(p1.getNamePlayer() + _validCard.toString());

         try {
            Thread.sleep(_speedPlayCard);
         } catch (InterruptedException e) {
            // TODO Auto-generated catch block
         }

         //luot danh cua nguoi thu 2
         p2.playCardPlayer(_queenPlayer, _turnPlayer, _validCard);
         _nextCardPlayer1 = p2.getPLayCardPlayer();
         p2.showListLabelCardPlayedPlayer(1, _nextCardPlayer1, _sizeChangeCard);
         if (p2.isHumanPlayer()) {
            _isClickLabelHuman = false;
         }
         //System.out.println(p2.getNamePlayer() + _nextCardPlayer1.toString());

         try {
            Thread.sleep(_speedPlayCard);
         } catch (InterruptedException e) {
            // TODO Auto-generated catch block
         }

         //luot danh cua nguoi thu 3
         p3.playCardPlayer(_queenPlayer, _turnPlayer, _validCard);
         _nextCardPlayer2 = p3.getPLayCardPlayer();
         p3.showListLabelCardPlayedPlayer(2, _nextCardPlayer2, _sizeChangeCard);
         if (p3.isHumanPlayer()) {
            _isClickLabelHuman = false;
         }
         // System.out.println(p3.getNamePlayer() + _nextCardPlayer2.toString());

         try {
            Thread.sleep(_speedPlayCard);
         } catch (InterruptedException e) {
            // TODO Auto-generated catch block
         }

         //luot danh cua nguoi thu 4
         p4.playCardPlayer(_queenPlayer, _turnPlayer, _validCard);
         _nextCardPlayer3 = p4.getPLayCardPlayer();
         p4.showListLabelCardPlayedPlayer(3, _nextCardPlayer3, _sizeChangeCard);
         if (p4.isHumanPlayer()) {
            _isClickLabelHuman = false;
         }
         //System.out.println(p4.getNamePlayer() + _nextCardPlayer3.toString());

         //Danh dau da xong luot dau
         if (_turnPlayer) {
            _turnPlayer = false;
         }
         //Lay _queenPlayer
         if (!_queenPlayer) {
            _queenPlayer = _validCard.isHeartCard() || _nextCardPlayer1.isHeartCard() || _nextCardPlayer2.isHeartCard() || _nextCardPlayer3.isHeartCard();
         }

         //Nguoi choi dau tien trong luot tiep theo
         try {
            Thread.sleep(1000);
         } catch (InterruptedException e) {
            // TODO Auto-generated catch block
         }

         _firstGamePlayer = findFisrtPlayer();
         move(p1, _firstGamePlayer);

         _firstGamePlayer.addAScoreCardPlayer(_validCard);
         _firstGamePlayer.addAScoreCardPlayer(_nextCardPlayer1);
         _firstGamePlayer.addAScoreCardPlayer(_nextCardPlayer2);
         _firstGamePlayer.addAScoreCardPlayer(_nextCardPlayer3);

         //Hien thi diem qua tung luot cua nguoi choi
         // printTheScore();

         //kiem tra khoi tao luot moi
         _isClickLabelHuman = true;
      }

      //Tinh diem, kiem tra de bat dau vong moi
      this.scoring();
   }

   /**
    *newGame()
    */
   public void newGame() {
      _txtNoticeScore = _nameBottomPlayer.getNamePlayer() + "         " + _nameLeftPlayer.getNamePlayer() + "        " + _nameTopPlayer.getNamePlayer() + "         " + _nameRightPlayer.getNamePlayer() + "\n";
      _nameBottomPlayer.newGamePlayer();
      _nameLeftPlayer.newGamePlayer();
      _nameTopPlayer.newGamePlayer();
      _nameRightPlayer.newGamePlayer();
      while (true) {
         this.newRound();
      }
   }
}

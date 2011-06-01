/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author TienPhan
 */
public class Player extends DTOConstantPlayer {

   private String name; // Tên người chơi
   private int type;    //Kiểu người chơi: là IS_HUMAN, IS_COMPUTER
   private ArrayList<Card> listCard; // Danh sách các lá bài mà người chơi có được.
   private ArrayList<Card> scoreCard; // Điểm của mỗi là bài sẽ được lưu vào trong danh sách.
   private int score = 0; // Điểm này dùng trong quá trình chơi sẽ được tăng lên.
   private Player nextPlayer; // Xác định người chơi tiếp theo.
   private boolean isFirst; // Xác định ai là người chơi đầu tiên.
   private Card playCard; // Xác định lá bài nào được đánh ra.
   private JLabel[] listCardLabel; //Các nút label chứa quân bài trong tay người choi: chua duoc danh ra
   private JLabel[] playCardLabel;// Cac nut quan bai da duoc danh ra
   private ArrayList<Integer> list3Card; //3 lá bài được chọn để hoán đổi vị trí.
   private int[] randomIndex; //Vị trí quân bài được chọn của người chơi là IS_COMPUTER

   /*
    * Ham khoi tao mot nguoi choi
    * namePlayer: Tên người chơi
    * typePlayer: Loại người chơi
    * positionPlayer: Vị trí người chơi
    */
   public Player(String namePlayer, int typePlayer, int positionPlayer) {
      //Xác định người chơi.
      this.name = namePlayer;
      this.type = typePlayer;
      position = positionPlayer;
      //Xác định bộ bài
      listCard = new ArrayList<Card>(SOQUANBAI);
      scoreCard = new ArrayList<Card>();

      //Chia bài cho ngừoi chơi
      this.randomIndex = new int[SOQUANBAI];
      for (int i = 0; i < SOQUANBAI; i++) {
         randomIndex[i] = i;
      }
      //Trộn bài cho người chơi.
      Random random = new Random();
      for (int i = 0; i < SOQUANBAI; i++) {
         int j = random.nextInt(SOQUANBAI);
         int tmp = randomIndex[i];
         randomIndex[i] = randomIndex[j];
         randomIndex[j] = tmp;
      }
   }

   /*
    * Lấy tên người chơi
    */
   public String getName() {
      return name;
   }

   /*
    * Gán nhãn lá bài mà người chơi đã chơi
    */
   public void setPlayCardLabel(JLabel[] playCardLabel) {
      this.playCardLabel = playCardLabel;
   }

   /*
    * Gán danh sách lá bài trong tay người chơi
    */
   public void setListCardLabel(JLabel[] listCardLabel) {
      this.listCardLabel = listCardLabel;
   }

   /*
    *setNextPlayer:set nguoi choi tiep theo
    * Người chơi 1 -> ng2 -> ng3->ng4
    */
   public void setNextPlayer(Player nextPlayer) {
      this.nextPlayer = nextPlayer;
   }

   /*
    * Kiểm tra con có phải người chơi hay không
    */
   public boolean isHuman() {
      return (this.type == IS_HUMAN);
   }
   /*
    * Xétloại người chơi
    */

   public void setType(int type) {
      this.type = type;
   }

   /*
    * Hiển thị lá bài lên JLabel của người chơi
    * show = true: hiển thị thấy hình
    * show = false: hiển thị thấy mặt sắp 
    */
   public void showListCard(boolean show) {
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
      // this.listCardLabel[Player.SOQUANBAI].setIcon(new ImageIcon(Card.BACK_PICTURE));
      this.listCardLabel[Player.SOQUANBAI].setVisible(false);
   }
}

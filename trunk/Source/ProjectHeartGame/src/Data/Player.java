/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JLabel;

/**
 *
 * @author TienPhan: sữ lý người choi
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
    */
   public Player(String namePlayer, int typePlayer, int positionPlayer) {
      //Xác định người chơi.
      this.name = namePlayer;
      this.type = typePlayer;
      this.position = positionPlayer;
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
}

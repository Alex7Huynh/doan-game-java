/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author TienPhan
 */
public class DTOConstantCard {

   public static final int NUM_OF_CARD = 52; // Qui định số lá bài của một bộ là 52 lá
   public static final int NUM_OF_FACE = 13; // Qui định 1 nước gồm có 13 lá.
   public static final int FACE_2 = 0; // Qui định con bài 2 = trọng số là 0: để sử lý con 2 chuồn(con thấp nhất)
   public static final int FACE_ACE = 12; // Qui định con bài Ace = trọng số là 12 để sử lý (con cao nhất)
   public static final int FACE_QUEEN = 10; //Qui đinh con bài Q = trọng số là 10 để sử lý con Đầm bích: trừ 13
   public static final String[] Face = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
   public static final String PICTURES_EXTEND = ".png";
   public static final String PICTURES_FOLDER = "resources/";
   public static final String BACK_PICTURE = "resources/MatSap.png";
   public static final int SUIT_SPADE = 0; //Bích
   public static final int SUIT_CLUB = 1; //Chuồn
   public static final int SUIT_DIAMOND = 2; //Rô
   public static final int SUIT_HEART = 3; //Cơ
   public static final String[] Suit = {"SPADE", "CLUB", "DIAMOND", "HEART"};// Cho biết 4 nước trong lá bài
}

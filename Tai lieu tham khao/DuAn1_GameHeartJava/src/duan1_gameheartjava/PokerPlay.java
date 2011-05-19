/*
 * PokerPlay.java
 *
 * Created on 2006��12��23��, ����9:14
 */

package duan1_gameheartjava;
import javax.swing.JOptionPane;
/**
 *
 * @author  1��
 */
public class PokerPlay {
    private Poker cards[];
    Bonus bonusList;
    boolean findBonus[];
    
    
    /** Creates a new instance of PokerPlay */
    public PokerPlay() {
        cards = new Poker[5];
        bonusList = new Bonus();
        findBonus = new boolean[10];
        for( int i = 0 ; i < 10 ; i++ ){
            findBonus[i] = false;
        }
    }
    public void zeroBonus(){
        for( int i = 0 ; i < 10 ; i++ ){
            findBonus[i] = false;
        }
    }
        
    public void addPoker( int ptr , PokerDeck deck ){
        Poker card = new Poker( deck.dealPoker() );
        cards[ptr] = card;        
    }
    public void subPoker( int ptr , PokerDeck deck ){
        deck.returnPoker( cards[ptr].getNumber() );
    }
    
    public void sortCards(){
        int i,j;
        for( i = 1 ; i < 5 ; i++ )
            for( j = 0 ; j < 4 ; j++ ){
                if( cards[j].getNumber() > cards[j+1].getNumber() ){
                    Poker hold = new Poker( cards[j] );
                    cards[j] = cards[j+1];
                    cards[j+1] = hold;
                }
            }
    }
    
    private void findPair(){
        int pipCount[] = new int[13];
        int i;
        
        for( i = 0 ; i < 13 ; i++ )
            pipCount[i] = 0;
        for( i = 0 ; i < 5 ; i++ ){
            pipCount[ cards[i].getPip() ]++;
        }
   /*     String output = new String("");
        for( i=0;i<13;i++)
            output += (" " +pipCount[i] +"  ");
        JOptionPane.showMessageDialog(null, output,"aaa",JOptionPane.PLAIN_MESSAGE);*/
        for( i = 0 ; i < 13 ; i++ ){
            switch( pipCount[i] ){
                case 2 :
                    if( findBonus[1] == true )
                        findBonus[2] = true;
                    else
                        findBonus[1] = true;
                    
                    break;
                case 3:
                    findBonus[3] = true;
                    break;
                case 4:
                    findBonus[7] = true;
                    break;
                default:
                    break;
            }
        }
    }
    private void findFullHouse(){
        if(findBonus[1] == true && findBonus[3] == true)
            findBonus[6] = true;
    }
    private void findStraight(){
        boolean result = true;
        int i;
        for( i = 1 ; i < 5 ; i++ ){
            if( cards[i].getPip() != ( cards[i-1].getPip() + 1 ) ){
               if( i == 4 && cards[i].getPip() == 0 && cards[i-1].getPip() ==12 )
                   continue;
               else{
                    result = false;
                    break;
               }
            }
        }
        findBonus[4] = result;
    }
    private void findFlush(){
        boolean result = true;
        int i;
        for( i=1 ; i < 5 ; i++ ){
            if( cards[i].getDesign() != cards[i-1].getDesign()){
                result = false;
                break;
            }
        }
        findBonus[5] = result;
    }
    private void findStraightFlush(){
        if( findBonus[4] == true && findBonus[5] == true)
            findBonus[8] = true;                
    }
    private void findRoyalFlush(){
        if(findBonus[8] == true && cards[0].getPip() == 9)
            findBonus[9] = true;
    }
    private void findNoPair(){
        int i;
        boolean result = true;
        for( i = 1 ; i < 10 ; i++ ){
            if(findBonus[i] == true){
                result = false;
                break;
            }
        }
        findBonus[0] = result;
    }
    public void checkCards(){
        sortCards();
        findPair();
        findFullHouse();
        findFlush();
        findStraight();
        findStraightFlush();
        findRoyalFlush();
        findNoPair();
    }
    private int getBonusNumber(){

        int i;
        for(i = 9 ; i >= 0 ; i--){
            if(findBonus[i] == true)
                return i;
        }
        return 0;
    }
    public int getBonusRate(){
        return bonusList.getRate(getBonusNumber());
    }
    public String getBonusName(){
        return bonusList.getName(getBonusNumber());
    }
    public int getCardNumber(int num){
        return cards[num].getNumber();
    }
    
                
    
}
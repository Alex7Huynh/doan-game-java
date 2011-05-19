/*
 * Poker.java
 *
 * Created on 2006��12��23��, ����8:17
 */

package duan1_gameheartjava;

/**
 *
 * @author  1��
 */
public class Poker {
    private int cardNumber;
    private int design;
    private int pip;
    
        
    /** Creates a new instance of Poker */
    public Poker() {
        setPoker(0);
    }
    public Poker( int num ){
        setPoker(num);
    }
    
    public Poker( Poker other ){
        setPoker( other.cardNumber );
    }
    
    public void setPoker( int num ) {
        if( num < 0 || num > 53 )
            num = 0;
        cardNumber = num;
        design = cardNumber % 4;
        pip = cardNumber / 4;
    }
    
    public int getNumber(){
        return cardNumber;
    }
    public int getDesign(){
        return design;
    }
    public int getPip(){
        return pip;
    }    
    public String getDesignName(){
        String output = new String("");
        switch( design ){
            case 0:
                output += "CLUB";
                break;
            case 1:
                output += "DIAMOND";
                break;
            case 2:
                output += "HEART";
                break;
            case 3:
                output += "SPADE";
                break;
            default:
                break;
        }
        return output;
    }
    
    public String getPipName(){
        String output = new String("");
        switch( pip ){
            case 0:
                output += "A";
                break;
            case 12:
                output += "K";
                break;
            case 11:
                output += "Q";
                break;
            case 10:
                output += "J";
                break;
            default:
                output += ( pip + 1 );
                break;
        }
        return output;
    }
    
    public String toString(){
        String output = new String("");
        output += getDesignName() + "\n" + getPipName();
        return output;        
    }
}

/*
 * PokerDeck.java
 *
 * Created on 2006��12��23��, ����8:41
 */


package duan1_gameheartjava;

import javax.swing.JOptionPane;

/**
 *
 * @author  1��
 */
public class PokerDeck {
    String pictures[];
    boolean isDealed[];
    
    /** Creates a new instance of PokerDeck */
    public PokerDeck() {
        pictures = new String[54];
        isDealed = new boolean[54];
        for( int i = 0 ; i < 54 ; i++ ){
            isDealed[i] = false;
            pictures[i] = "";
        }            
    }
    public int dealPoker(){        
        int deal = ( int )( Math.random() * 52 );
        while( isDealed[deal] == true ){
            deal = ( int )( Math.random() * 52 );
        }
        isDealed[deal] = true;
        return deal;
    }
    public void returnPoker( int num ){
        isDealed[num] = false;
    }
    public String getPic( int num ){
        return pictures[num];
    }
}

/*
 * Bonus.java
 *
 * Created on 2006��12��23��, ����9:02
 */

package duan1_gameheartjava;

/**
 *
 * @author  1��
 */
public class Bonus {
    private String bonusName[];
  
    private int rate[];
    /** Creates a new instance of Bonus */
    public Bonus() {
        bonusName = new String[]{ "NO PAIR" , "1 PAIR" , "2 PAIR" ,
            "3 OF A KIND" , "STRAIGHT" , "FLUSH" , "FULL HOUSE" , 
            "4 OF A KIND" , "STRAIGHT FLUSH" , "ROYAL FLUSH" };
        rate = new int[]{ 0, 2, 3, 4, 8, 10, 15, 50, 100, 500 };            

    }
    public String getName(int num){
        if( num < 0 || num > 9)
            num = 0;
        return bonusName[num];
    }
    public int getRate( int num ){
        if( num < 0 || num > 9 )
            num = 0;
        return rate[num];
    }    
}

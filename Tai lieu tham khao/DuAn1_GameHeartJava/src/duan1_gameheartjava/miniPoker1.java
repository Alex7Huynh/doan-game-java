/*
 * miniPoker1.java
 *
 * Created on 2006��12��23��, ����10:50
 */

package duan1_gameheartjava;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;
/**
 *
 * @author  1��
 */
public class miniPoker1 extends JFrame{
    private final int MAX = 5;

    private Container container;
    private GridBagLayout layout;
    private GridBagConstraints constraints;
    
    private JTextArea medalText, betNameText, multiText, rateNameText, 
             equText,betText, rateText, resultText;
    private JLabel pokerLabels[];
    private JCheckBox pokerCBoxes[];
    private JButton changeButton, allHoldButton;
    
    private JTextArea pokerText[];
    
    private int bet;
    private int rate;
    private int medal;
    private PokerDeck playerDeck;
    private PokerPlay player;
    private boolean pokerChose[];
    
    private int chosePtr;
   
    /** Creates a new instance of miniPoker1 */
    public miniPoker1() {
        super( "miniPoker V1.0.0" ); 
        
        int i;
        pokerChose = new boolean[MAX];
        playerDeck = new PokerDeck();
        player = new PokerPlay();
                
        container = getContentPane();
        layout = new GridBagLayout();
        container.setLayout(layout);        
        constraints = new GridBagConstraints();
        
        //create wallpaper
        ImageIcon wallpaper = new ImageIcon(getClass().getResource("wall.jpg"));
        ((JPanel)getContentPane()).setOpaque(false);
        JLabel background = new JLabel(wallpaper);
        getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setBounds(0, 0,wallpaper.getIconWidth(),wallpaper.getIconHeight());
  
        //create Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.setMnemonic('N');
        newGameItem.addActionListener(
            new ActionListener(){
                public void actionPerformed( ActionEvent event ){
                    medal = 100;
                    rate = 1;
                    bet = 0;
                            
                    enter();
                    brush();

                    playGame();
                                  
                }
            }
        );
        fileMenu.add( newGameItem );
        
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('x');
                exitItem.addActionListener(
            new ActionListener(){
                public void actionPerformed( ActionEvent event ){
                    System.exit(0);
                }
            }
        );
        fileMenu.add( exitItem );
        
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        
        JMenuItem gameGuideItem = new JMenuItem("Game Guide");
        gameGuideItem.setMnemonic('G');
        gameGuideItem.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    JTextArea copyRight = new JTextArea(10,20);
                    copyRight.setEnabled(false);
                    copyRight.append("GAME GUIDE"+
                                      "\n\nSee Readme.txt, please.");
                    JOptionPane.showMessageDialog( miniPoker1.this, copyRight,"About",JOptionPane.PLAIN_MESSAGE );
                }
            }
        ); 
        helpMenu.add( gameGuideItem );
        
        JMenuItem bonusListItem = new JMenuItem("Bonus List");
        bonusListItem.setMnemonic('B');
        bonusListItem.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    JTextArea copyRight = new JTextArea(13,10);
                    copyRight.setEnabled(false);
                    copyRight.append("BONUS LIST\n\n"+
                                      "NO PAIR\t\tRATE X 000\n"+
                                      "1 PAIR\t\tRATE X 002\n"+
                                      "2 PAIR\t\tRATE X 003\n"+
                                      "3 OF A KIND\t\tRATE X 004\n"+
                                      "STRAIGHT\t\tRATE X 008\n"+
                                      "FLUSH\t\tRATE X 010\n"+
                                      "FULL HOUSE\t\tRATE X 015\n"+
                                      "4 OF A KIND\t\tRATE X 050\n"+
                                      "STRAIGHT FLUSH\tRATE X 100\n"+
                                      "ROYAL FLUSH\t\tRATE X 500");
                    JOptionPane.showMessageDialog( miniPoker1.this, copyRight,"About",JOptionPane.PLAIN_MESSAGE );
                }
            }
        );
        helpMenu.add( bonusListItem );
        
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.setMnemonic('A');
        aboutItem.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    JTextArea copyRight = new JTextArea(8,10);
                    copyRight.setEnabled(false);
                    copyRight.append("(C) Copyright 2006 by Zhu Yu."+
                                      "\nAll Rights Reserved.\nSJTU\n24 , Dec , 2006 ");
                    JOptionPane.showMessageDialog( miniPoker1.this, copyRight,"About",JOptionPane.PLAIN_MESSAGE );
                }
            }
        );        
        helpMenu.add( aboutItem );
        
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        bar.add(fileMenu);
        bar.add(helpMenu);
        
        //set GUI
        medalText = new JTextArea(3,5);
        medalText.setText("    Medal\n--------------\n  "+medal);
        medalText.setFont(new Font("TimesRoman",Font.BOLD+Font.ITALIC,13));
        medalText.setEnabled(false);
        
        betNameText = new JTextArea(2,5);
        betNameText.setText(" BET");
        betNameText.setFont(new Font("TimesRoman",Font.BOLD+Font.ITALIC,13));
        betNameText.setEnabled(false);

        multiText = new JTextArea(2,5);
        multiText.setText(" *");
        multiText.setFont(new Font("TimesRoman",Font.BOLD+Font.ITALIC,13));
        multiText.setEnabled(false);
        
        rateNameText = new JTextArea(2,5);
        rateNameText.setText(" RATE");
        rateNameText.setFont(new Font("TimesRoman",Font.BOLD+Font.ITALIC,13));
        rateNameText.setEnabled(false);
        
        equText = new JTextArea(2,5);
        equText.setText(" =");
        equText.setFont(new Font("TimesRoman",Font.BOLD+Font.ITALIC,13));
        equText.setEnabled(false);
        
        betText = new JTextArea(2,5);
        betText.setText(" "+bet);
        betText.setFont(new Font("TimesRoman",Font.BOLD+Font.ITALIC,13));
        betText.setEnabled(false);
        
        rateText = new JTextArea(2,5);
        rateText.setText(" "+rate);
        rateText.setFont(new Font("TimesRoman",Font.BOLD+Font.ITALIC,13));
        rateText.setEnabled(false);
        
        resultText = new JTextArea(2,5);
        resultText.setText(" "+bet*rate);
        resultText.setFont(new Font("TimesRoman",Font.BOLD+Font.ITALIC,13));
        resultText.setEnabled(false);
        
        pokerLabels = new JLabel[MAX];
        pokerCBoxes = new JCheckBox[MAX];
       
        CheckBoxHandler handler = new CheckBoxHandler();
        
        for( i = 0 ; i < MAX ; i++){
            pokerLabels[i] = new JLabel("");
            
            pokerCBoxes[i] = new JCheckBox("");
            pokerCBoxes[i].setEnabled(false); 
            pokerCBoxes[i].addItemListener(handler);
         }
        
        changeButton = new JButton("CHANGE");
        changeButton.setEnabled(false);
        changeButton.addActionListener(
            new ActionListener(){
                public void actionPerformed( ActionEvent event ){
                    int i;
                    for( i = 0 ; i < MAX ; i++ ){
                        if( pokerChose[i] != true ){
                             player.subPoker(i,playerDeck);
                            player.addPoker(i,playerDeck);
                        }
                    }
                     showPoker();
                     calculor();
                     if(rate == 0){
                         if(medal == 0){
                            JOptionPane.showMessageDialog(null, "Game Over! ","Game Over",JOptionPane.PLAIN_MESSAGE);
                            medal = 100;
                            rate = 1;
                            bet =0 ;
                            brush();
                            changeButton.setEnabled(false);
                            allHoldButton.setEnabled(false);
                            for(int j=0;j<MAX;j++)
                                pokerCBoxes[j].setEnabled(false);
                         }                        
                        else{    
                            bet = 0;
                            changeButton.setEnabled(false);
                            allHoldButton.setEnabled(false);
                            for(int j=0;j<MAX;j++)
                                 pokerCBoxes[j].setEnabled(false);
                            brush();
                            enter();
                            brush();
                            playGame();
                        }
                     }
                     else {
                      //   JOptionPane.showConfirmDialog(null,"Wrong?");
                         playGame();                       
                     }

                     
                            
                }
        }
        );
        
        allHoldButton = new JButton("SAVE MEDAL");
        allHoldButton.setEnabled(false);
        allHoldButton.addActionListener(
            new ActionListener(){
                public void actionPerformed( ActionEvent event ){
                  medal += (bet*rate);
                  bet =0;
                  rate =1;
                  brush();
                  enter();
                  brush();
                  playGame();
                  
                }
        }
        );
   
        //set GridBagLayout
        constraints.weightx = 50;
        constraints.weighty = 50;   
        constraints.fill = GridBagConstraints.NONE;
        addComponent( medalText, 0, 5, 1, 1 );
        
        constraints.weightx = 60;
        constraints.weighty = 60;   
        constraints.fill = GridBagConstraints.NONE;
        addComponent( betNameText, 4, 0, 1, 1 );
        
        constraints.fill = GridBagConstraints.NONE;
        addComponent( multiText, 4, 1, 1, 1 );
        
        constraints.fill = GridBagConstraints.NONE;
        addComponent( rateNameText, 4, 2, 1, 1 );
        
        constraints.fill = GridBagConstraints.NONE;
        addComponent( equText, 4, 3, 1, 1 );
        
        constraints.weightx = 70;
        constraints.weighty = 70;   
        constraints.fill = GridBagConstraints.NONE;
        addComponent( betText, 5, 0, 1, 1 );
        
        constraints.fill = GridBagConstraints.NONE;
        addComponent( rateText, 5, 2, 1, 1 );
        
        constraints.fill = GridBagConstraints.NONE;
        addComponent( resultText, 5, 4, 1, 1 );
        
        for( i = 0 ; i < MAX ; i++ ){
            constraints.weightx = 200;
            constraints.weighty = 200;
            constraints.fill = GridBagConstraints.NONE;
            constraints.anchor = GridBagConstraints.WEST;
            addComponent( pokerLabels[i], 1, i, 2, 1 );
            
            constraints.weightx = 100;
            constraints.weighty = 100;
            constraints.fill = GridBagConstraints.NONE;
            constraints.anchor = GridBagConstraints.CENTER;
            addComponent( pokerCBoxes[i], 3, i, 1, 1 );
        }
        
        constraints.weightx = 70;
        constraints.weighty = 70;
        constraints.fill = GridBagConstraints.NONE;
        addComponent( changeButton, 4, 5, 1, 1 );
        
        constraints.weightx = 70;
        constraints.weighty = 70;
        constraints.fill = GridBagConstraints.NONE;
        addComponent( allHoldButton, 5, 5, 1, 1 );
        
        
        
    
        setSize(1100, 700);
        setVisible(true);     
    }
    
    private void addComponent( Component component, int row, int column, int width, int height ){
        constraints.gridx = column;
        constraints.gridy = row;
        
        constraints.gridwidth = width;
        constraints.gridheight = height;
        
        layout.setConstraints( component , constraints );
        container.add( component );
    }
    
    public void playGame(){
       
        int i;

            player.zeroBonus();
            for( i =0 ; i < MAX ; i++)
                player.addPoker(i,playerDeck);
            showPoker();                    
            for( i = 0 ; i < MAX ; i++ ){
                pokerChose[i] = false;   
                pokerCBoxes[i].setSelected(false);
            }
            for( i = 0 ; i < MAX ; i++ )
            pokerCBoxes[i].setEnabled(true);            
            changeButton.setEnabled(true);
            allHoldButton.setEnabled(true); 

        

        
    }
    public void enter(){
        String enterBet = JOptionPane.showInputDialog("Enter your bet ( equal or less than " + medal + ")");
        bet = Integer.parseInt(enterBet);
        if(bet > medal)
            bet = medal;
        medal -=bet;
    }
    public void brush(){
        medalText.setText("    Medal\n--------------\n  "+medal);
        betText.setText(""+bet);
        rateText.setText(""+rate);
        resultText.setText(""+bet*rate);
    }
    public void showPoker(){
        int i;
        for( i = 0 ; i < MAX ; i++){
                int hold;
                hold = player.getCardNumber(i);
                ImageIcon pokPic = new ImageIcon(getClass().getResource(hold+".gif"));
                pokerLabels[i].setIcon(pokPic);
        }
    }
    public void text(){
      JOptionPane.showMessageDialog(null,"hello","text",JOptionPane.PLAIN_MESSAGE);
    }
    public void calculor(){
        player.checkCards();
        rate *= player.getBonusRate();
        JOptionPane.showMessageDialog(null,""+player.getBonusName() + "\tX\t" +player.getBonusRate(),"Your Bonus!",JOptionPane.PLAIN_MESSAGE);
        brush();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        miniPoker1 application = new miniPoker1();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private class CheckBoxHandler implements ItemListener{
        
        public void itemStateChanged(java.awt.event.ItemEvent event) {
            int i;
            
            for(i=0;i<MAX;i++){
                if( event.getSource()  == pokerCBoxes[i]){
                   
                    if( event.getStateChange() == ItemEvent.SELECTED ){
                        pokerChose[i] = true;
                       // text();
                    }
                    else
                        pokerChose[i] = false;
                                           
                }
            }
        }
        
    }
    
}


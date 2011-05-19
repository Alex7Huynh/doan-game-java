/**************************************************************************
 * (C) Copyright 10/06/2005 by Jokeyu  ������:������;ѧ��:2003131014;03��A�� *
 * All Rights Reserved.                                                   *
 *************************************************************************/
/**************************************************************************
 1�� ��Ӯ�Ĺ���Ϊ�� ����<һ��<����<ͬ��<˳��<ͬ��<Full-house<ͬ��<ͬ��˳��

 2.  ����ʱÿ��ÿ�η�һ�ţ�����Ϊ�˷���ϴ��ʱ��Щ���ŵ���ûϴ��������ɷ���ʱ���ŷ���ͬһ���˵������

 3.  �����ƺ���עǰ�����Ի��Լ����ϵ��ƣ�ÿ�ſ�100��Ҳ����͵�����ֵ��ƣ�Ҳ��ÿ�ſ�100��

 4.  ��ʤ���ʵļ������������������ϵ��ƣ�������б����С���Ƶ����֮��ռһ����������ϵİٷ��ʣ���Ϊ��ʤ���ʡ�
 *************************************************************************/
package mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class DeckOfCards
    extends JFrame {
  private int card_name[][] = new int[5][2]; //��ʾ��ʱ�õ�ͼƬ����
  private long player_score;
  private int wager; //��ע
  private int EMULANT = 0, PLAYER = 1;
  private int lightbutton; //����Ķ�ע��ť
  private boolean GameIsStart = false, BreakConnect = false; //��Ϸ�Ƿ������,�Ƿ�ȡ�����
  private Socket socket;
  private DataInputStream input;
  private DataOutputStream output;
  private int UpdateStatus=0,GetCard=1,ChangeCard=2,PeekCard=3,Wager=4;
  Client client;

  JButton button_100, button_200, button_300, button_400, button_500,
      button_begin, emulant_image, player_image,
      card_image[][] = new JButton[5][2], button_client, button_break;
  JLabel emulant_status, player_status, result_label, wager_label, copyright_label,
      warn_label, score_label, level_label, winchance_label, blank_label,
      address_label, emulant_level_label, emulant_score_label;
  JTextField address_textf, port_textf;
  GridBagLayout gbLayout;
  GridBagConstraints gbConstraints;
  JPanel p;
  Icon image;
  Image icon;

  public DeckOfCards() {
    super("�˿���С��Ϸ");
    player_score = 1000;
    gbLayout = new GridBagLayout();
    Container c = getContentPane();
    JPanel p = new JPanel();
    p.setLayout(gbLayout);
    gbConstraints = new GridBagConstraints();
    JScrollPane sp = new JScrollPane(p);

    icon = Toolkit.getDefaultToolkit().getImage("smile.gif");
    this.setIconImage(icon);

    copyright_label = new JLabel(
        "\u00A9 Copyright 10/06/2005 by Jokeyu @C4-702. All Rights Reserved.");
    warn_label = new JLabel("����Ա�Ҹ����: �Ĳ��������ˣ��������׳��ԣ������������Ϸ������;��");
    warn_label.setFont(Font.getFont("������"));
    blank_label = new JLabel("^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^");

    image = new ImageIcon("player.gif");
    player_image = new JButton();
    player_image.setIcon(image);

    image = new ImageIcon("opponent.gif");
    emulant_image = new JButton();
    emulant_image.setIcon(image);
    image = new ImageIcon("waiting.gif");
    for (int i = 0; i < 5; i++) {
      card_image[i][EMULANT] = new JButton();
      card_image[i][EMULANT].setIcon(image);
      card_image[i][PLAYER] = new JButton();
      card_image[i][PLAYER].setIcon(image);
    }

    image = new ImageIcon("smile.gif");
    result_label = new JLabel("��δ���ӷ�����");
    result_label.setFont(Font.getFont("������"));
    result_label.setIcon(image);
    level_label = new JLabel("��ĵȼ� : δ֪");
    level_label.setFont(Font.getFont("������"));
    score_label = new JLabel("��ķ��� : δ֪");
    score_label.setFont(Font.getFont("������"));
    emulant_level_label = new JLabel("���ֵȼ� : δ֪");
    emulant_level_label.setFont(Font.getFont("������"));
    emulant_score_label = new JLabel("���ַ��� : δ֪");
    emulant_score_label.setFont(Font.getFont("������"));
    winchance_label = new JLabel("��ʤ���ʣ� δ֪");
    winchance_label.setFont(Font.getFont("������"));
    button_begin = new JButton("�����￪ʼ��Ϸ");
    button_begin.setFont(Font.getFont("������"));
    image = new ImageIcon("money.gif");
    wager_label = new JLabel("�� �� ע");
    wager_label.setFont(Font.getFont("������"));
    wager_label.setIcon(image);
    button_100 = new JButton("100");
    button_200 = new JButton("200");
    button_300 = new JButton("300");
    button_400 = new JButton("400");
    button_500 = new JButton("500");
    player_status = new JLabel("����� : �������ӷ�����");
    player_status.setFont(Font.getFont("������"));
    emulant_status = new JLabel("���ֵ��� : �������ӷ�����");
    emulant_status.setFont(Font.getFont("������"));
    button_break = new JButton("�Ͽ�����");
    button_break.setFont(Font.getFont("������"));
    button_break.setBackground(Color.white);
    button_client = new JButton("��������");
    button_client.setFont(Font.getFont("������"));
    button_client.setBackground(Color.white);
    address_label = new JLabel("---��ַ�Ͷ˿�--->");
    address_label.setFont(Font.getFont("������"));
    address_textf = new JTextField("127.0.0.1");
    port_textf = new JTextField("1617");

    gbConstraints.weightx = 1;
    gbConstraints.weighty = 1;
    gbConstraints.fill = GridBagConstraints.BOTH;

    addComponent(copyright_label, gbLayout, gbConstraints, p, 0, 0, 6, 1);
    addComponent(warn_label, gbLayout, gbConstraints, p, 1, 0, 6, 1);
    addComponent(blank_label, gbLayout, gbConstraints, p, 2, 0, 6, 1);
    addComponent(emulant_level_label, gbLayout, gbConstraints, p, 3, 0, 2, 1);
    addComponent(emulant_score_label, gbLayout, gbConstraints, p, 3, 2, 4, 1);
    addComponent(emulant_image, gbLayout, gbConstraints, p, 4, 0, 1, 5);
    for (int i = 1; i <= 5; i++) {
      addComponent(card_image[i - 1][EMULANT], gbLayout, gbConstraints, p, 4,
                   i, 1, 5);
    }
    addComponent(emulant_status, gbLayout, gbConstraints, p, 9, 0, 6, 1);
    addComponent(player_image, gbLayout, gbConstraints, p, 10, 0, 1, 5);
    for (int i = 1; i <= 5; i++) {
      addComponent(card_image[i - 1][PLAYER], gbLayout, gbConstraints, p, 10, i,
                   1, 5);
    }
    addComponent(player_status, gbLayout, gbConstraints, p, 15, 0, 6, 1);
    addComponent(wager_label, gbLayout, gbConstraints, p, 16, 0, 1, 1);
    addComponent(button_100, gbLayout, gbConstraints, p, 16, 5, 1, 1);
    addComponent(button_200, gbLayout, gbConstraints, p, 16, 4, 1, 1);
    addComponent(button_300, gbLayout, gbConstraints, p, 16, 3, 1, 1);
    addComponent(button_400, gbLayout, gbConstraints, p, 16, 2, 1, 1);
    addComponent(button_500, gbLayout, gbConstraints, p, 16, 1, 1, 1);
    addComponent(level_label, gbLayout, gbConstraints, p, 17, 0, 2, 1);
    addComponent(score_label, gbLayout, gbConstraints, p, 17, 2, 2, 1);
    addComponent(winchance_label, gbLayout, gbConstraints, p, 17, 4, 2, 1);
    addComponent(button_begin, gbLayout, gbConstraints, p, 18, 0, 2, 1);
    addComponent(result_label, gbLayout, gbConstraints, p, 18, 2, 4, 1);
    addComponent(button_break, gbLayout, gbConstraints, p, 19, 0, 1, 1);
    addComponent(button_client, gbLayout, gbConstraints, p, 19, 1, 1, 1);
    addComponent(address_label, gbLayout, gbConstraints, p, 19, 2, 1, 1);
    addComponent(address_textf, gbLayout, gbConstraints, p, 19, 3, 2, 1);
    addComponent(port_textf, gbLayout, gbConstraints, p, 19, 5, 1, 1);

    c.add(sp);
    setSize(550, 400);
    this.setLocation(250, 150);

    button_100.setEnabled(false);
    button_100.setBackground(Color.lightGray);
    button_200.setEnabled(false);
    button_200.setBackground(Color.lightGray);
    button_300.setEnabled(false);
    button_300.setBackground(Color.lightGray);
    button_400.setEnabled(false);
    button_400.setBackground(Color.lightGray);
    button_500.setEnabled(false);
    button_500.setBackground(Color.lightGray);
    button_begin.setEnabled(false);
    button_begin.setBackground(Color.lightGray);
    button_break.setEnabled(false);
    button_break.setBackground(Color.lightGray);

    show(); // show the window

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
//��ʼ��Ϸ��ť������Ĵ����¼�
    button_begin.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switch (lightbutton) {
          case 1:
            button_100.setBackground(Color.lightGray);
            break;
          case 2:
            button_200.setBackground(Color.lightGray);
            break;
          case 3:
            button_300.setBackground(Color.lightGray);
            break;
          case 4:
            button_400.setBackground(Color.lightGray);
            break;
          case 5:
            button_500.setBackground(Color.lightGray);
            break;
        }
        for (int i = 0; i < 5; i++) {
          image = new ImageIcon("waiting.gif");
          card_image[i][EMULANT].setIcon(image);
        }
        try {
          output.writeInt(GetCard);
          button_begin.setText("��Ϸ�Ѿ���ʼ");
          button_begin.setEnabled(false);
          button_begin.setBackground(Color.lightGray);
          result_label.setText("����ȡ��......");
        }
        catch (IOException E) {}
      }
    }
    );
//100�ֶ�ע��ť������Ĵ����¼�
    button_100.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(GameIsStart==true){
          try {
            output.writeInt(Wager);
            output.writeInt(100);
            lightbutton = 1;
            wager = 100;
            wager();
            button_100.setBackground(Color.yellow);
            result_label.setText("���ڵȴ�ȽϽ��......");
          }
          catch (IOException E) {}
        }
        GameIsStart=false;
      }
    }
    );
//200�ֶ�ע��ť������Ĵ����¼�
    button_200.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(GameIsStart==true){
          try {
            output.writeInt(Wager);
            output.writeInt(200);
            lightbutton = 2;
            wager = 200;
            wager();
            button_200.setBackground(Color.yellow);
            result_label.setText("���ڵȴ�ȽϽ��......");
          }
          catch (IOException E) {}
        }
        GameIsStart=false;
      }
    }
    );
//300�ֶ�ע��ť������Ĵ����¼�
    button_300.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(GameIsStart==true){
          try {
            output.writeInt(Wager);
            output.writeInt(300);
            lightbutton = 3;
            wager = 300;
            wager();
            button_300.setBackground(Color.yellow);
            result_label.setText("���ڵȴ�ȽϽ��......");
          }
          catch (IOException E) {}
        }
        GameIsStart=false;
      }
    }
    );
//400�ֶ�ע��ť������Ĵ����¼�
    button_400.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(GameIsStart==true){
          try {
            output.writeInt(Wager);
            output.writeInt(400);
            lightbutton = 4;
            wager = 400;
            wager();
            button_400.setBackground(Color.yellow);
            result_label.setText("���ڵȴ�ȽϽ��......");
          }
          catch (IOException E) {}
        }
        GameIsStart=false;
      }
    }
    );
//500�ֶ�ע��ť������Ĵ����¼�
    button_500.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(GameIsStart==true){
          try {
            output.writeInt(Wager);
            output.writeInt(500);
            lightbutton = 5;
            wager = 500;
            wager();
            button_500.setBackground(Color.yellow);
            result_label.setText("���ڵȴ�ȽϽ��......");
          }
          catch (IOException E) {}
        }
        GameIsStart=false;
      }
    }
    );
//��ҵĵ�һ���Ʊ���������Ļ����¼�
    card_image[0][PLAYER].addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (GameIsStart == true) {
          if (player_score >= 200) {
            try {
              output.writeInt(ChangeCard);
              output.writeInt(0);
              result_label.setText("���ڻ���......");
            }
            catch (IOException E) {}
          }
          else {
            result_label.setText("��ķ���������ٻ����ˣ�");
          }
        }
      }
    }
    );
//��ҵĵڶ����Ʊ���������Ļ����¼�
    card_image[1][PLAYER].addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (GameIsStart == true) {
          if (player_score >= 200) {
            try {
              output.writeInt(ChangeCard);
              output.writeInt(1);
              result_label.setText("���ڻ���......");
            }
            catch (IOException E) {}
          }
          else {
            result_label.setText("��ķ���������ٻ����ˣ�");
          }
        }
      }
    }
    );
//��ҵĵ������Ʊ���������Ļ����¼�
    card_image[2][PLAYER].addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (GameIsStart == true) {
          if (player_score >= 200) {
            try {
              output.writeInt(ChangeCard);
              output.writeInt(2);
              result_label.setText("���ڻ���......");
            }
            catch (IOException E) {}
          }
          else {
            result_label.setText("��ķ���������ٻ����ˣ�");
          }
        }
      }
    }
    );
//��ҵĵ������Ʊ���������Ļ����¼�
    card_image[3][PLAYER].addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (GameIsStart == true) {
          if (player_score >= 200) {
            try {
              output.writeInt(ChangeCard);
              output.writeInt(3);
              result_label.setText("���ڻ���......");
            }
            catch (IOException E) {}
          }
          else {
            result_label.setText("��ķ���������ٻ����ˣ�");
          }
        }
      }
    }
    );
//��ҵĵ������Ʊ���������Ļ����¼�
    card_image[4][PLAYER].addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (GameIsStart == true) {
          if (player_score >= 200) {
            try {
              output.writeInt(ChangeCard);
              output.writeInt(4);
              result_label.setText("���ڻ���......");
            }
            catch (IOException E) {}
          }
          else {
            result_label.setText("��ķ���������ٻ����ˣ�");
          }
        }
      }
    }
    );
//���ֵĵ�һ���Ʊ����������͵���¼�
    card_image[0][EMULANT].addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (GameIsStart == true) {
          if (player_score >= 200) {
            try {
              output.writeInt(PeekCard);
              output.writeInt(0);
              result_label.setText("͵����......");
            }
            catch (IOException E) {}
          }
          else {
            result_label.setText("��ķ����������͵�����˵����ˣ�");
          }
        }
      }
    }
    );
//���ֵĵڶ����Ʊ����������͵���¼�
    card_image[1][EMULANT].addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (GameIsStart == true) {
          if (player_score >= 200) {
            try {
              output.writeInt(PeekCard);
              output.writeInt(1);
              result_label.setText("͵����......");
            }
            catch (IOException E) {}
          }
          else {
            result_label.setText("��ķ����������͵�����˵����ˣ�");
          }
        }
      }
    }
    );
//���ֵĵ������Ʊ����������͵���¼�
    card_image[2][EMULANT].addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (GameIsStart == true) {
          if (player_score >= 200) {
            try {
              output.writeInt(PeekCard);
              output.writeInt(2);
              result_label.setText("͵����......");
            }
            catch (IOException E) {}
          }
          else {
            result_label.setText("��ķ����������͵�����˵����ˣ�");
          }
        }
      }
    }
    );
//���ֵĵ������Ʊ����������͵���¼�
    card_image[3][EMULANT].addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (GameIsStart == true) {
          if (player_score >= 200) {
            try {
              output.writeInt(PeekCard);
              output.writeInt(3);
              result_label.setText("͵����......");
            }
            catch (IOException E) {}
          }
          else {
            result_label.setText("��ķ����������͵�����˵����ˣ�");
          }
        }
      }
    }
    );
//���ֵĵ������Ʊ����������͵���¼�
    card_image[4][EMULANT].addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (GameIsStart == true) {
          if (player_score >= 200) {
            try {
              output.writeInt(PeekCard);
              output.writeInt(4);
              result_label.setText("͵����......");
            }
            catch (IOException E) {}
          }
          else {
            result_label.setText("��ķ����������͵�����˵����ˣ�");
          }
        }
      }
    }
    );
//������ӷ�������ť�Ĵ����¼�
    button_client.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (address_textf.getText().compareTo("") == 0) {
          result_label.setText("�����������<��ַ>���ٵ�����Ӱ�ť");
        }
        else if (port_textf.getText().compareTo("") == 0) {
          result_label.setText("�����������<�˿�>���ٵ�����Ӱ�ť");
        }
        else {
          client = new Client();
          client.start();
        }
      }
    }
    );
//����Ͽ����Ӱ�ť�Ĵ����¼�
    /*
    button_break.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        BreakConnect = true;
        result_label.setText("�������Ѿ��Ͽ�������������");
        button_begin.setEnabled(false);
        button_begin.setBackground(Color.lightGray);
        button_break.setEnabled(false);
        button_break.setBackground(Color.lightGray);
        button_client.setEnabled(true);
        button_client.setBackground(Color.white);
        button_100.setEnabled(false);
        button_100.setBackground(Color.lightGray);
        button_200.setEnabled(false);
        button_200.setBackground(Color.lightGray);
        button_300.setEnabled(false);
        button_300.setBackground(Color.lightGray);
        button_400.setEnabled(false);
        button_400.setBackground(Color.lightGray);
        button_500.setEnabled(false);
        button_500.setBackground(Color.lightGray);
        button_begin.setEnabled(false);
        button_begin.setBackground(Color.lightGray);
        button_break.setEnabled(false);
        button_break.setBackground(Color.lightGray);

      }
    }
    );
*/
  }

//��ӿؼ�����addComponent()
  public void addComponent(Component c, GridBagLayout g, GridBagConstraints gc,
                           JPanel p, int row, int column, int width, int height) {
    gc.gridx = column;
    gc.gridy = row;
    gc.gridwidth = width;
    gc.gridheight = height;
    g.setConstraints(c, gc);
    p.add(c);
  }

//�������������ע��ť�ɲ����õĺ���checkscore()
  public void checkscore() {
    image = new ImageIcon("money.gif");
    if (player_score >= 100) {
      button_100.setEnabled(true);
      button_100.setBackground(Color.white);
      button_100.setIcon(image);
    }
    if (player_score >= 200) {
      button_200.setEnabled(true);
      button_200.setBackground(Color.white);
      button_200.setIcon(image);
    }
    if (player_score >= 300) {
      button_300.setEnabled(true);
      button_300.setBackground(Color.white);
      button_300.setIcon(image);
    }
    if (player_score >= 400) {
      button_400.setEnabled(true);
      button_400.setBackground(Color.white);
      button_400.setIcon(image);
    }
    if (player_score >= 500) {
      button_500.setEnabled(true);
      button_500.setBackground(Color.white);
      button_500.setIcon(image);
    }
    if (player_score < 200) {
      button_200.setEnabled(false);
      button_200.setBackground(Color.lightGray);
      button_200.setIcon(null);
    }
    if (player_score < 300) {
      button_300.setEnabled(false);
      button_300.setBackground(Color.lightGray);
      button_300.setIcon(null);
    }
    if (player_score < 400) {
      button_400.setEnabled(false);
      button_400.setBackground(Color.lightGray);
      button_400.setIcon(null);
    }
    if (player_score < 500) {
      button_500.setEnabled(false);
      button_500.setBackground(Color.lightGray);
      button_500.setIcon(null);
    }
  }

//��ע����wager()
  public void wager() {
    button_100.setEnabled(false);
    button_100.setBackground(Color.lightGray);
    button_100.setIcon(null);
    button_200.setEnabled(false);
    button_200.setBackground(Color.lightGray);
    button_200.setIcon(null);
    button_300.setEnabled(false);
    button_300.setBackground(Color.lightGray);
    button_300.setIcon(null);
    button_400.setEnabled(false);
    button_400.setBackground(Color.lightGray);
    button_400.setIcon(null);
    button_500.setEnabled(false);
    button_500.setBackground(Color.lightGray);
    button_500.setIcon(null);
    player_score = player_score - wager;
    score_label.setText("��ķ��� :" + player_score);
  }

//��ʾ���ϵ��Ƶĺ���showCard()
  public void showCard(int name, int order) {
    image = new ImageIcon(card_name[order][name] + ".gif");
    card_image[order][name].setIcon(image);
  }


  public static void main(String args[]) {
    DeckOfCards app = new DeckOfCards();
    app.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  private void jbInit() throws Exception {
    this.setFont(new java.awt.Font("������", 0, 12));
    this.setResizable(false);
  }

//�ͻ���ͨ�źʹ������
  class Client
      extends Thread {
    public Client() {
      try {
        socket = new Socket(address_textf.getText(),
                            Integer.parseInt(port_textf.getText()));
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        button_break.setEnabled(true);
        button_break.setBackground(Color.white);
        button_client.setEnabled(false);
        button_client.setBackground(Color.lightGray);
        result_label.setText("�������Ѿ����ӣ���ȴ���һ���������");
      }
      catch (IOException E) {
        E.printStackTrace();
        result_label.setText("���ӷ�����ʧ�ܣ�����������");
        button_client.setEnabled(true);
        button_client.setBackground(Color.white);
      }
    }

    public void run() {
      while (true) {
        try {
          int operate=input.readInt();
          if ( operate==UpdateStatus ) {
            result_label.setText("����״̬��......");
            level_label.setText("��ĵȼ���" + input.readUTF());
            emulant_level_label.setText("���ֵȼ� : " + input.readUTF());
            player_score = input.readInt();
            score_label.setText("��ķ��� : " + player_score);
            emulant_score_label.setText("���ַ��� : " + input.readInt());
            result_label.setText("����״̬��ϣ��뿪ʼ��Ϸ");
            button_begin.setEnabled(true);
            button_begin.setBackground(Color.white);
          }
          else if ( operate==GetCard ) {
            for (int i = 0; i < 5; i++) {
              card_name[i][PLAYER] = input.readInt();
              showCard(PLAYER, i);
            }
            level_label.setText("��ĵȼ���" + input.readUTF());
            player_score = input.readInt();
            score_label.setText("��ķ��� :" + player_score);
            player_status.setText("����� : " + input.readUTF());
            winchance_label.setText("��ʤ���ʣ�" + input.readFloat() + "%");
            result_label.setText("ȡ�����");
            emulant_status.setText("���ֵ��� : ����ע���Ҿ����㿴");
            result_label.setText("�뿴��Ȼ����ע");
            checkscore();
            GameIsStart = true;
          }
          else if ( operate==ChangeCard ) {
            int index = input.readInt();
            card_name[index][1] = input.readInt();
            showCard(PLAYER, index);
            player_score = input.readInt();
            score_label.setText("��ķ��� :" + player_score);
            player_status.setText("��ҵ��� : " + input.readUTF());
            winchance_label.setText("��ʤ���ʣ�" + input.readFloat() + "%");
            checkscore();
            result_label.setText("������͵͵�ػ���һ���ƣ���Ҫ�����۳�100�ֵĴ�ۣ�");
          }
          else if ( operate==PeekCard ) {
            int index = input.readInt();
            card_name[index][0] = input.readInt();
            showCard(EMULANT, index);
            result_label.setText("͵�����");
            player_score = input.readInt();
            score_label.setText("��ķ��� :" + player_score);
            checkscore();
            result_label.setText("������͵���˶���һ���ƣ���Ҫ�����۳�100�ֵĴ�ۣ�");
          }
          else if ( operate==Wager ) {
            for (int i = 0; i < 5; i++) {
              card_name[i][EMULANT] = input.readInt();
              showCard(EMULANT, i);
            }
            emulant_status.setText("���ֵ��� : "+input.readUTF());
            result_label.setText(input.readUTF());
            level_label.setText("��ĵȼ���" + input.readUTF());
            emulant_level_label.setText("���ֵȼ���" + input.readUTF());
            player_score = input.readInt();
            score_label.setText("��ķ��� :" + player_score);
            emulant_score_label.setText("���ַ��� :" + input.readInt());
            if (player_score >= 100) {
              button_begin.setText("����������һ��");
              button_begin.setBackground(Color.white);
              button_begin.setEnabled(true);
            }
            else {
              result_label.setText("���ź�����ķ���Ϊ�㣡��Ϸ�����ˣ�");
              button_begin.setText("��Ϸ�ѽ���");
              break;
            }
          }
        }
        catch (IOException ec) {}
      }
    } //end of run
  }

}

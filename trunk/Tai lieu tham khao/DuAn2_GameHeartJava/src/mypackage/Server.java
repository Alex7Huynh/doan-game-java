package mypackage;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;

public class Server
    extends JFrame {
  private Player Players[] = new Player[2];
  private boolean player_connect_status[] = new boolean[2],
      player_getcard[] = new boolean[2], player_wagered[] = new boolean[2];
  private ServerSocket server;
  private Card deck[] = new Card[52];
  private int currentCard;
  private int Player1 = 0, Player2 = 1;
  private StringBuffer player_level[] = new StringBuffer[2],
      player_compare_result[] = new StringBuffer[2];
  private int player_score[] = new int[2], player_wager[] = new int[2];
  private float player_winchance[] = new float[2];
  private int card_name[][] = new int[5][2];
  private int UpdateStatus = 0, GetCard = 1, ChangeCard = 2, PeekCard = 3,
      Wager = 4;
  private String faces[] = {
      "Deuce", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
      "Jack", "Queen", "King", "Ace"};
  private String suits[] = {
      "Diamonds", "Clubs", "Hearts", "Spades"};
  private int face13[][] = {
      {
      0, 0}
      , {
      0, 0}
      , {
      0, 0}
      , {
      0, 0}
      , {
      0, 0}
      , {
      0, 0}
      , {
      0, 0}
      , {
      0, 0}
      , {
      0, 0}
      , {
      0, 0}
      , {
      0, 0}
      , {
      0, 0}
      , {
      0, 0}
  };
  private int suit4[][] = {
      {
      0, 0}
      , {
      0, 0}
      , {
      0, 0}
      , {
      0, 0}
  };
  private int cardsum[] = {
      0, 0}
      , cardcomp[][] = {
      {
      -1, -1}
      , {
      -1, -1}
      , {
      -1, -1}
  };
  private double winchance[] = {
      1303566, 1098130, 123586, 54862, 9230, 5190, 3736, 624, 36}; //52���Ƶ�����������������ֵĴ���

  JButton button_server, button_player1_out, button_player2_out,
      button_shutdown;
  JTextField port_textf;
  JLabel port_label;
  JTextArea message_area;
  GridBagLayout gbLayout;
  GridBagConstraints gbConstraints;
  JPanel p;

  public Server() {
    super("��Ϸ������");
    for (int i = 0; i < deck.length; i++) {
      deck[i] = new Card(faces[i % 13], suits[i / 13]);
    }
    currentCard = -1;
    for (int i = 0; i < Players.length; i++) {
      player_level[i] = new StringBuffer();
      player_level[i].append("����");
      player_compare_result[i] = new StringBuffer();
      player_compare_result[i].append("");
      player_score[i] = 1000;
      player_winchance[i] = 100;
      player_wager[i] = 0; //��ע
      for (int j = 0; j < 5; j++) {
        card_name[j][i] = -1;
      }
    }
    player_connect_status[0] = false;
    player_connect_status[1] = false;
    player_getcard[0] = false;
    player_getcard[1] = false;
    player_wagered[0] = false;
    player_wagered[1] = false;
    button_server = new JButton("����������");
    button_server.setBackground(Color.white);
    button_server.setFont(Font.getFont("������"));
    button_player1_out = new JButton("�߳����1");
    button_player1_out.setEnabled(false);
    button_player1_out.setBackground(Color.lightGray);
    button_player1_out.setFont(Font.getFont("������"));
    button_player2_out = new JButton("�߳����2");
    button_player2_out.setEnabled(false);
    button_player2_out.setBackground(Color.lightGray);
    button_player2_out.setFont(Font.getFont("������"));
    button_shutdown = new JButton("�Ͽ�����");
    button_shutdown.setEnabled(false);
    button_shutdown.setBackground(Color.lightGray);
    button_shutdown.setFont(Font.getFont("������"));
    port_textf = new JTextField("1617");
    message_area = new JTextArea(10, 30);
    port_label = new JLabel("<�˿�>: ");
    port_label.setFont(Font.getFont("������"));

    gbLayout = new GridBagLayout();
    Container c = getContentPane();
    JPanel p = new JPanel();
    p.setLayout(gbLayout);
    gbConstraints = new GridBagConstraints();
    JScrollPane sp = new JScrollPane(p);
    gbConstraints.weightx = 0;
    gbConstraints.weighty = 0;
    gbConstraints.fill = GridBagConstraints.HORIZONTAL;

    addComponent(port_textf, gbLayout, gbConstraints, p, 0, 1, 1, 1);
    addComponent(port_label, gbLayout, gbConstraints, p, 0, 0, 1, 1);
    addComponent(button_server, gbLayout, gbConstraints, p, 0, 2, 1, 1);
    addComponent(button_shutdown, gbLayout, gbConstraints, p, 0, 3, 1, 1);
    addComponent(button_player1_out, gbLayout, gbConstraints, p, 0, 4, 1, 1);
    addComponent(button_player2_out, gbLayout, gbConstraints, p, 0, 5, 1, 1);
    addComponent(message_area, gbLayout, gbConstraints, p, 1, 0, 6, 1);

    c.add(sp);
    setSize(462, 240);
    this.setLocation(250, 150);
    port_textf.setColumns(4);
    message_area.setFocusable(false);
    show();

    button_server.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (port_textf.getText().compareTo("") != 0) {
          try {
            server = new ServerSocket(Integer.parseInt(port_textf.getText()), 2);
            message_area.append("Server is opening\n");
            for (int i = 0; i < Players.length; i++) {
              Players[i] = new Player(server.accept(), i);
              Players[i].start();
            }
            button_server.setEnabled(false);
            button_server.setBackground(Color.lightGray);
            button_player1_out.setEnabled(true);
            button_player1_out.setBackground(Color.white);
            button_player2_out.setEnabled(true);
            button_player2_out.setBackground(Color.white);
            button_shutdown.setEnabled(true);
            button_shutdown.setBackground(Color.white);
          }
          catch (IOException E) {}
        }
      }
    }
    );
    /*
        button_shutdown.addActionListener(
            new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              for (int i = 0; i < Players.length; i++) {
                Players[i].output.writeUTF("ServerShutdown");
                Players[i].input.close();
                Players[i].output.close();
              }
              server.close();
              message_area.append("Server is shut down\n");
              button_server.setEnabled(true);
              button_server.setBackground(Color.white);
              button_player1_out.setEnabled(false);
              button_player1_out.setBackground(Color.lightGray);
              button_player2_out.setEnabled(false);
              button_player2_out.setBackground(Color.lightGray);
              button_shutdown.setEnabled(false);
              button_shutdown.setBackground(Color.lightGray);
            }
            catch (IOException E) {}
          }
        }
        );

        button_player1_out.addActionListener(
            new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              Players[0].output.writeUTF("ConnectOut");
            }
            catch (IOException E) {}
            player_connect_status[0] = false;
            message_area.append("���1ʧȥ����\n");
          }
        }
        );

        button_player2_out.addActionListener(
            new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              Players[0].output.writeUTF("ConnectOut");
            }
            catch (IOException E) {}
            player_connect_status[0] = false;
            message_area.append("���1ʧȥ����\n");
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

  //ϴ�ƺ���shuffle()
  public void shuffle() {
    currentCard = -1;
    for (int i = 0; i < deck.length; i++) {
      int j = (int) (Math.random() * 52);
      Card temp = deck[i]; // swap
      deck[i] = deck[j]; // the
      deck[j] = temp; // cards
    }
  }

  //�Ƚ��ƵĴ�С�ĺ���cardCompare()
  public void cardCompare() {
    int comp_result;
    player_compare_result[0] = new StringBuffer();
    player_compare_result[1] = new StringBuffer();
    if (cardcomp[0][Player1] > cardcomp[0][Player2]) {
      comp_result = -1;
    }
    else if (cardcomp[0][Player1] == cardcomp[0][Player2]) {
      if (cardcomp[1][Player1] > cardcomp[1][Player2]) {
        comp_result = -1;
      }
      else if (cardcomp[1][Player1] == cardcomp[1][Player2]) {
        if (cardcomp[2][Player1] > cardcomp[2][Player2]) {
          comp_result = -1;
        }
        else if (cardcomp[2][Player1] == cardcomp[2][Player2]) {
          comp_result = 0;
        }
        else {
          comp_result = 1;
        }
      }
      else {
        comp_result = 1;
      }
    }
    else {
      comp_result = 1;
    }
    switch (comp_result) {
      case -1:
        player_compare_result[0].append("�ϵ۱��ӣ���Ӯ�ˣ�����һ����");
        player_score[0] = player_score[0] + player_wager[0] + player_wager[1];
        player_compare_result[1].append("��?�����ˣ�����һ����");
        break;
      case 0:
        player_compare_result[0].append("ʲô����Ȼ��ƽ�֣�����һ����");
        player_score[0] = player_score[0] + player_wager[0];
        player_compare_result[1].append("ʲô����Ȼ��ƽ�֣�����һ����");
        player_score[1] = player_score[1] + player_wager[1];
        break;
      case 1:
        player_compare_result[1].append("�ϵ۱��ӣ���Ӯ�ˣ�����һ����");
        player_score[1] = player_score[1] + player_wager[0] + player_wager[1];
        player_compare_result[0].append("��?�����ˣ�����һ����");
        break;
      default:
        message_area.append("�������д���(compare)����Ͽ������Ա��ϵ��\n");
        break;
    }
  }

  //����Ƿ�Ϊ˳�ӵĺ���shunORnot()
  public int shunORnot(int number) {
    int num = 0;
    for (int i = 0; i < 13; i++) {
      if (face13[i][number] != 1) {
        i = i + num;
        num = 0;
      }
      else {
        num = num + 1;
        if (num == 5) {
          cardcomp[0][number] = 8;
          cardcomp[1][number] = i;
          return 1;
        }
      }
    }
    return 0;
  }

//����õ���ʲô�Ƶĺ���count()
  public String count(int number) {
    cardsum[number] = 0;
    cardcomp[0][number] = 0;
    cardcomp[1][number] = 0;
    for (int i = 0; i < 13; i++) {
      if (face13[i][number] == 1) {
        cardsum[number] = cardsum[number] + 0;
        if (cardcomp[0][number] <= 1) {
          cardcomp[0][number] = 1;
          cardcomp[2] = cardcomp[1];
          cardcomp[1][number] = i;
        }
      }
      else if (face13[i][number] == 2) {
        cardsum[number] = cardsum[number] + 3;
        if (cardcomp[0][number] < 3) {
          cardcomp[0][number] = 3;
          cardcomp[1][number] = i;
        }
        else if (cardcomp[0][number] == 3) {
          cardcomp[0][number] = 6;
          cardcomp[2][number] = cardcomp[1][number];
          cardcomp[1][number] = i;
        }
      }
      else if (face13[i][number] == 3) {
        cardsum[number] = cardsum[number] + 7;
        cardcomp[0][number] = cardsum[number];
        cardcomp[1][number] = i;
      }
      else if (face13[i][number] == 4) {
        cardsum[number] = cardsum[number] + 11;
        cardcomp[0][number] = cardsum[number];
        cardcomp[1][number] = i;
      }
      else {
        cardsum[number] = cardsum[number] + 0;
      }
    }
    if (shunORnot(number) == 1) {
      cardsum[number] = cardsum[number] + 8;
    }
    for (int j = 0; j < 4; j++) {
      if (suit4[j][number] == 5) {
        cardsum[number] = cardsum[number] + 9;
        cardcomp[0][number] = cardsum[number];
        cardcomp[2][number] = cardcomp[1][number];
        cardcomp[1][number] = j;
      }
      else {
        cardsum[number] = cardsum[number] + 0;
      }
    }
    switch (cardsum[number]) {
      case 0:
        return ("���ˣ��õ���һ�����ƣ�");
      case 3:
        return ("С�ģ����ֻ��һ��ม�");
      case 6:
        return ("���ã��õ������ԡ�");
      case 7:
        return ("���?������ͬ������ơ�");
      case 8:
        return ("�ã��õ���˳�ӣ�");
      case 9:
        return ("�ܺã��õ���ͬ����");
      case 10:
        return ("̫���ˣ��õ���Full house��");
      case 11:
        return ("�ü��ˣ�������ͬ������ƣ�");
      case 17:
        return ("���ܵ��񰡣���Ȼ�õ���ͬ��˳��");
      default:
        break;
    }
    return ("�������д���(count)����Ͽ������Ա��ϵ��");
  }

  //�鿴�ȼ��ĺ���checkLevel()
  public void checkLevel(int number) {
    player_level[number] = new StringBuffer();
    if (player_score[number] == 0) {
      player_level[number].append("�ܼ���");
    }
    if (player_score[number] >= 100 && player_score[number] <= 500) {
      player_level[number].append("��ù��");
    }
    if (player_score[number] > 500 && player_score[number] <= 2000) {
      player_level[number].append("����");
    }
    if (player_score[number] > 2000 && player_score[number] <= 5000) {
      player_level[number].append("����");
    }
    if (player_score[number] > 5000 && player_score[number] <= 10000) {
      player_level[number].append("����");
    }
    if (player_score[number] > 10000) {
      player_level[number].append("����");
    }
  }

  //���һ�����Ƿ���ĺ���checkCurrentCard()
  public int checkCurrentCard() {
    int tempCard = currentCard;
    if (++tempCard <= 51) {
      return++currentCard;
    }
    else {
      return currentCard;
    }
  }

  //����������Ƽ�¼�ĺ���addrecord()
  public void addrecord(int number, Card deck, int order) {
    int j, k;
    for (j = 0; j < 13; j++) {
      if (deck.checkface() == faces[j]) {
        face13[j][number] = face13[j][number] + 1;
        break;
      }
    }
    for (k = 0; k < 4; k++) {
      if (deck.checksuit() == suits[k]) {
        suit4[k][number] = suit4[k][number] + 1;
        break;
      }
    }
    card_name[order][number] = j * 4 + k;
  }

  //���Ƽ�¼����������ɾ��ĺ���deleterecord()
  public void deletecard(int order, int number) {
    face13[card_name[order][number] /
        4][number] = face13[card_name[order][number] / 4][number] - 1;
    suit4[card_name[order][number] %
        4][number] = suit4[card_name[order][number] %
        4][number] - 1;
  }

  //���ƺ���dealCard()
  public void dealCard() {
    for (int i = 0; i < 5; i++) {
      addrecord(Player1, deck[++currentCard], i);
      addrecord(Player2, deck[++currentCard], i);
    }
  }

  //������Ϊ�´���Ϸ���г�ʼ���ĺ���checkNext()
  public void checkNext() {
    for (int i = 0; i < 13; i++) {
      face13[i][Player1] = 0;
      face13[i][Player2] = 0;
    }
    for (int j = 0; j < 4; j++) {
      suit4[j][Player1] = 0;
      suit4[j][Player2] = 0;
    }
    cardsum[Player1] = 0;
    cardsum[Player2] = 0;
    for (int k = 0; k < 3; k++) {
      cardcomp[k][Player1] = -1;
      cardcomp[k][Player2] = -1;
    }
    for (int i = 0; i < 5; i++) {
      card_name[i][Player1] = -1;
      card_name[i][Player2] = -1;
    }
    currentCard = -1;
    for (int i = 0; i < Players.length; i++) {
      player_wager[i] = 0; //��ע
      for (int j = 0; j < 5; j++) {
        card_name[j][i] = -1;
      }
    }
    player_getcard[0] = false;
    player_getcard[1] = false;
    player_wagered[0] = false;
    player_wagered[1] = false;
  }

//����ʤ��ĺ���winChance()
  public float winChance(int number) {
    switch (cardcomp[0][number]) {
      case 1:
        return ( (float) winchance[0] * (cardcomp[1][number] - 4) * 100 / 8 /
                2598960);
      case 3:
        return ( (float) (winchance[0] +
                          winchance[1] * (cardcomp[1][number] + 1) / 13) * 100 /
                2598960);
      case 6:
        return ( (float) (winchance[0] + winchance[1] +
                          winchance[2] * ( (cardcomp[1][number]) / 12) *
                          ( (cardcomp[2][number] + 1) / 13)) * 100 / 2598960);
      case 7:
        return ( (float) (winchance[0] + winchance[1] + winchance[2] +
                          winchance[3] * (cardcomp[1][number] + 1) / 13) * 100 /
                2598960);
      case 8:
        return ( (float) (winchance[0] + winchance[1] + winchance[2] +
                          winchance[3] +
                          winchance[4] * (cardcomp[1][number] + 1) / 13) * 100 /
                2598960);
      case 9:
        return ( (float) (winchance[0] + winchance[1] + winchance[2] +
                          winchance[3] + winchance[4] +
                          winchance[5] * ( (cardcomp[1][number] + 1) / 4) *
                          ( (cardcomp[2][number] - 4) / 8)) * 100 / 2598960);
      case 10:
        return ( (float) (winchance[0] + winchance[1] + winchance[2] +
                          winchance[3] + winchance[4] + winchance[5] +
                          winchance[6] * (cardcomp[1][number] + 1) / 13) * 100 /
                2598960);
      case 11:
        return ( (float) (winchance[0] + winchance[1] + winchance[2] +
                          winchance[3] + winchance[4] + winchance[5] +
                          winchance[6] +
                          winchance[7] * (cardcomp[1][number] + 1) / 13) * 100 /
                2598960);
      case 17:
        return ( (float) (winchance[0] + winchance[1] + winchance[2] +
                          winchance[3] + winchance[4] + winchance[5] +
                          winchance[6] + winchance[7] +
                          winchance[8] * ( (cardcomp[1][number] + 1) / 4) *
                          ( (cardcomp[2][number] - 3) / 9)) * 100 / 2598960);
      default:
        break;
    }
    return 100;
  }

  public static void main(String args[]) {
    Server app = new Server();
    app.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  class Player
      extends Thread {
    private Socket connection;
    private DataInputStream input;
    private DataOutputStream output;
    private int player_number, other_player_number;
    public Player(Socket socket, int number) {
      player_number = number;
      other_player_number = (player_number + 1) % 2;
      connection = socket;
      try {
        input = new DataInputStream(connection.getInputStream());
        output = new DataOutputStream(connection.getOutputStream());
        message_area.append("���" + (player_number + 1) + "������Ϸ\n");
        player_connect_status[number] = true;
        player_getcard[number] = false;
      }
      catch (IOException e) {}
    }

    public void run() {
      if (player_connect_status[other_player_number] == true) {
        message_area.append("����״̬\n");
        for (int i = 0; i < Players.length; i++) {
          try {
            Players[i].output.writeInt(UpdateStatus);
            Players[i].output.writeUTF(player_level[i].toString());
            Players[i].output.writeUTF(player_level[Players[i].
                                       other_player_number].toString());
            Players[i].output.writeInt(player_score[i]);
            Players[i].output.writeInt(player_score[Players[i].
                                       other_player_number]);
          }
          catch (IOException E) {}
        }
      }
      while (true) {
        try {
          int operate = input.readInt();
          if (operate == GetCard) {
            player_getcard[player_number] = true;
            message_area.append("���"+ (player_number+1)+"Ҫ��\n");
            if (player_getcard[Players[player_number].other_player_number] == true) {
              message_area.append("����\n");
              shuffle();
              dealCard();
              for (int i = 0; i < 2; i++) {
                Players[i].output.writeInt(GetCard);
                for (int j = 0; j < 5; j++) {
                  Players[i].output.writeInt(card_name[j][i]);
                }
                checkLevel(i);
                Players[i].output.writeUTF(player_level[i].toString());
                Players[i].output.writeInt(player_score[i]);
                Players[i].output.writeUTF(count(i));
                player_winchance[i] = winChance(i);
                Players[i].output.writeFloat(player_winchance[i]);
              }
            }
          }
          else if (operate == ChangeCard) {
            int card_number = input.readInt();
            synchronized (card_name) {
              deletecard(card_number, player_number);
              addrecord(player_number, deck[checkCurrentCard()], card_number);
              output.writeInt(ChangeCard);
              output.writeInt(card_number);
              output.writeInt(card_name[card_number][player_number]);
              player_score[player_number] = player_score[player_number] - 100;
              output.writeInt(player_score[player_number]);
              output.writeUTF(count(player_number));
              player_winchance[player_number] = winChance(player_number);
              output.writeFloat(player_winchance[player_number]);
            }
            message_area.append("���" + (player_number + 1) + "����\n");
          }
          else if (operate == PeekCard) {
            int card_number = input.readInt();
            synchronized (card_name) {
              output.writeInt(PeekCard);
              output.writeInt(card_number);
              output.writeInt(card_name[card_number][other_player_number]);
              player_score[player_number] = player_score[player_number] - 100;
              output.writeInt(player_score[player_number]);
            }
            message_area.append("���" + (player_number + 1) + "͵����\n");
          }
          else if (operate == Wager) {
            player_wagered[player_number] = true;
            player_wager[player_number] = input.readInt();
            player_score[player_number] = player_score[player_number] -
                player_wager[player_number];
            message_area.append("���" + (player_number + 1) + "��ע" +
                                player_wager[player_number] + "\n");
            if (player_wagered[other_player_number] == true) {
              cardCompare();
              for (int i = 0; i < Players.length; i++) {
                Players[i].output.writeInt(Wager);
                for (int j = 0; j < 5; j++) {
                  Players[i].output.writeInt(card_name[j][Players[i].
                                             other_player_number]);
                }
                Players[i].output.writeUTF(count(i));
                Players[i].output.writeUTF(player_compare_result[i].toString());
                checkLevel(Players[i].player_number);
                Players[i].output.writeUTF(player_level[i].toString());
                Players[i].output.writeUTF(player_level[Players[i].
                                           other_player_number].toString());
                Players[i].output.writeInt(player_score[i]);
                Players[i].output.writeInt(player_score[Players[i].
                                           other_player_number]);
              }
              checkNext();
            }
          }
        }
        catch (IOException E) {}
      }
    }
  }
}

class Card {
  private String face;
  private String suit;
  public Card(String f, String s) {
    face = f;
    suit = s;
  }

  public String checkface() {
    return face;
  }

  public String checksuit() {
    return suit;
  }
}

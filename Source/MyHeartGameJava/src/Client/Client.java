/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author TienPhan
 */
public class Client {

   //Tạo ra 3 port để conect server
   private int _iPort1 = 3200;
   private int _iPort2 = 3300;
   private int _iPort3 = 3400;
   private Socket _talkingSocket = null;
   private OutputStream os;
   private BufferedWriter bw;
   private Thread thread = null;
   private ClientThread client = null;
}

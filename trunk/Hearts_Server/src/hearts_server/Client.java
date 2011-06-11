/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hearts_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Czytelnik
 */
public class Client {

    public String Name;
    public int Id;
    public Socket Soc;
    public int TongDiem;
    //public List<Integer> DiemVan;
    public BufferedWriter BuffWriter;
    Client(Socket s, BufferedWriter bw, int i){
       Soc =s;
       BuffWriter =bw;
       TongDiem=0;
       Id=i;
       Name="";
    }
    public void Huy(){
        try {
            BuffWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viduUDP;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author Aragon
 */
public class UDPServer {

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(2010);
        DatagramPacket packet = new DatagramPacket(new byte[250], 250);
        socket.receive(packet);
        BufferedReader br= new BufferedReader(new InputStreamReader(new ByteArrayInputStream(packet.getData())));
//        String s=new String(packet.getData(),0,packet.getLength());
                String s=br.readLine();
        System.out.println(s);
    }
}

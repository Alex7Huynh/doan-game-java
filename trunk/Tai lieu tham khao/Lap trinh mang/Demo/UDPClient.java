/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package viduUDP;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author Aragon
 */
public class UDPClient {
    public static void main(String[] args) throws Exception {
        String s="Phan Đình Long";
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(new byte[250], 250);
        byte[]data;//=s.getBytes();
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        PrintWriter pw= new PrintWriter(baos);
        pw.println(s);
        pw.flush();
        data=baos.toByteArray();
        packet.setData(data);
        packet.setLength(data.length);
        packet.setPort(2010);
        packet.setAddress(InetAddress.getLocalHost());
        socket.send(packet);
    }
}

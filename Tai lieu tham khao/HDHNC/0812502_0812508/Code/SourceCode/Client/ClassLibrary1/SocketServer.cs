using System;
using System.Collections.Generic;
using System.Text;
using System.Net;
using System.Net.Sockets;
namespace ClassLibrary
{
    public class SocketServer
    {
        
        public IPEndPoint ipeServer;
        public Socket sServer = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        public void taoKetNoiDenServer(string ipServer, int portServer)
        {
            ipeServer = new IPEndPoint(IPAddress.Parse(ipServer), portServer);
            sServer.Connect(ipeServer);
            
        }
        public string dangNhapUser(string User, string Pass,string port)
        {
            string send, receive;
            send="1-"+User+"-"+Pass+"-"+port;
            byte[] data = new byte[1024];
            data = Encoding.ASCII.GetBytes(send);
            sServer.Send(data);
            data = new byte[1024];
            int recv= sServer.Receive(data);
            receive = Encoding.ASCII.GetString(data,0,recv);
            return receive;
        }
        public string taoUser(string User, string Pass1, string Pass2,string port)
        {
            string send, receive;
           
            
            send="3-"+User+"-"+Pass1+"-"+Pass2+"-"+port;
            byte[] data = new byte[1024];
            data = Encoding.ASCII.GetBytes(send);
            sServer.Send(data);
            data = new byte[1024];
            int recv=sServer.Receive(data);
            receive = Encoding.ASCII.GetString(data,0,recv);
            return receive;
        }
        public string themFile(string User, string TenFile)
        {
            string s,r;
            s = "5-" + TenFile + "-" + User;
            byte[] data = new byte[1024];
            data = Encoding.ASCII.GetBytes(s);
            sServer.Send(data);
            data = new byte[1024];
            int recv = sServer.Receive(data);
            r = Encoding.ASCII.GetString(data, 0, recv);
            return r;
        }
        public string xoaFile(string User, string TenFile)
        {
            string s, r;
            s = "6-" + TenFile + "-" + User;
            byte[] data = new byte[1024];
            data = Encoding.ASCII.GetBytes(s);
            sServer.Send(data);
            data = new byte[1024];
            int recv = sServer.Receive(data);
            r = Encoding.ASCII.GetString(data, 0, recv);
            return r;
        }
        public string timFile(string User,string TenFile)
        {
            string s,r;
            s = "2-" + TenFile+"-"+User;
            byte[] data = new byte[1024];
            data=Encoding.ASCII.GetBytes(s);
            sServer.Send(data);
            data = new byte[1024];
            int recv = sServer.Receive(data);
            r = Encoding.ASCII.GetString(data, 0, recv);
            return r;
        }
        public void yeuCauDownload(string User, string UserGiu, string TenFile)
        {
            string s;
            s = "8-" + TenFile+"-"+UserGiu+"-"+User;
            byte[] data = new byte[1024];
            data=Encoding.ASCII.GetBytes(s);
            sServer.Send(data);
           /* data = new byte[1024];
            int recv = sServer.Receive(data);
            r = Encoding.ASCII.GetString(data, 0, recv);*/
            //return r;
        }
        public void DangXuat(string user)
        {
            string s;
            s = "4-" + user;
            byte[] data = new byte[1024];
            data = Encoding.ASCII.GetBytes(s);
            sServer.Send(data);
            sServer.Close();
        }
    }
}

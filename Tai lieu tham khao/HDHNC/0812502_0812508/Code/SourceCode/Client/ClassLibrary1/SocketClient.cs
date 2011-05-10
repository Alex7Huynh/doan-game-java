using System;
using System.Collections.Generic;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.IO;

namespace ClassLibrary1
{
    class SocketClient
    {
        public Socket m_socLangNghe;
        public Socket m_socWork;
        public class MyUser
        {
            public string m_sTenUser;
            public IPAddress m_IPAddress;
            public int m_iPort;
            public string m_sFilename;
            public void LuuThongTin(string s)
            {
               // string username;
                string IP;
                string port;
                //int i;
                string[] arr;
                arr = s.Split(new char[] { '-' });
                m_sFilename = arr[0];
                m_sTenUser = arr[1];
                IP = arr[2];
                port = arr[3];
                m_IPAddress = IPAddress.Parse(IP);
                m_iPort = int.Parse(port);

            }
        }
        public void LangNghe(int port, IPAddress IP)
        {
            //create the listening socket...
            m_socLangNghe = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            IPEndPoint ipLocal = new IPEndPoint(IP, port);
            //bind to local IP Address...
            m_socLangNghe.Bind(ipLocal);
            //start listening...
            m_socLangNghe.Listen(10);
            // create the call back for any client connections...
            //  m_socListener.BeginAccept(new AsyncCallback(OnClientConnect), null);
            //cmdListen.Enabled = false;
            m_socWork = m_socLangNghe.Accept();
            byte[] data = new byte[1024];
            int recv = m_socWork.Receive(data);
            string s = Encoding.ASCII.GetString(data, 0, recv);
            string kq = PhanTichGoiTin(s);
           /* string s = "Ket Noi Thanh Cong";
            byte[] data = new byte[1024];
            data = Encoding.ASCII.GetBytes(s);
            m_socWork.Send(data, data.Length, SocketFlags.None);*/

        }
        public string PhanTichGoiTin(string s)
        {
            int flag;
            string kq = "";
            // string tam = s;
            string temp = s.Substring(2);

            flag = int.Parse(s[0].ToString());

            switch (flag)
            {
                case 1: kq = temp; break;
                case 2:
                    {
                        MyUser user = new MyUser();
                        user.LuuThongTin(temp);
                        Socket socTam = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                        IPEndPoint ipEP = new IPEndPoint(user.m_IPAddress, user.m_iPort);
                        socTam.Connect(ipEP);
                        byte[] data = new byte[1024];
                        data = Encoding.ASCII.GetBytes(user.m_sFilename);
                        socTam.Send(data, data.Length, SocketFlags.None);
                        socTam.Close();
                        kq= " Da luu thong tin user và gui yeu cau down load";

                    } break;
                   
            }
            return kq;

        }
        public void DownLoadFile()
        {
            byte[] clientData = new byte[1024 * 5000];

            int receivedBytesLen = m_socWork.Receive(clientData);
            //curMsg = "Receiving data...";

            int fileNameLen = BitConverter.ToInt32(clientData, 0);
            string fileName = Encoding.ASCII.GetString(clientData, 4, fileNameLen);

            BinaryWriter bWrite = new BinaryWriter(File.Open("C:/" + fileName, FileMode.Append)); ;
            bWrite.Write(clientData, 4 + fileNameLen, receivedBytesLen - 4 - fileNameLen);

            //curMsg = "Saving file...";

            bWrite.Close();
            //clientSock.Close();
            //curMsg = "Reeived & Saved file; Server Stopped.";
        }
    }
}

using System;
using System.Collections.Generic;
using System.Text;
using System.Net.Sockets;
using System.Net;

namespace MyUser
{
    public class User
    {
        public string m_sTenUser;
        public string m_sPassword;
        public System.Net.IPAddress m_IpAddress;
        public int m_iPort;
        public User()
        {
            m_sPassword = "";
            m_sTenUser = "";
            m_iPort = 0;
   
        }
        public void LayThongTin(string name, string pass,string ip, int port)
        {
            m_IpAddress = IPAddress.Parse(ip);
            m_sPassword = pass;
            m_sTenUser = name;
            m_iPort = port;
        }
    }
    
}

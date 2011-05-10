using System;
using System.Collections.Generic;
using System.Text;
using System.Net.Sockets;
//using System.Collections.Generi;

namespace MyThuVien
{
    class File
    {
       public string m_sTenFile;
       public string m_sUserGiuFile;
       public bool m_bTinhTrangDownLoad;
       public bool m_bTinhTrangUser;
       //public queue<string> HangDoiDownLoad;

       public File()
       {
           m_sTenFile = "";
           m_sUserGiuFile = "";
           m_bTinhTrangDownLoad=false;
           m_bTinhTrangUser=false;
       }
        public void Gan (File s)
        {
	        m_bTinhTrangDownLoad=s.m_bTinhTrangDownLoad;
	        m_bTinhTrangUser=s.m_bTinhTrangUser;
	        m_sTenFile=s.m_sTenFile;
	        m_sUserGiuFile=s.m_sUserGiuFile;
        }
    }
}

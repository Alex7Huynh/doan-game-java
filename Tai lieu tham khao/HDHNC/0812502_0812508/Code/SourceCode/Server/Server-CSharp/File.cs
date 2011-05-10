using System;
using System.Collections.Generic;
using System.Text;

namespace ConsoleApplication1
{
    class File
    {
       public string m_sTenFile;
       public string m_sUserGiuFile;
       public bool m_bTinhTrangDownLoad;
       public bool m_bTinhTrangUser;
       public queue<string> HangDoiDownLoad;

       public File()
       {
            m_bTinhTrangDownLoad=0;
           m_bTinhTrangUser=0;
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

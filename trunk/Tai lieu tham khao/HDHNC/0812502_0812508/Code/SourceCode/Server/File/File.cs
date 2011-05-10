using System;
using System.Collections.Generic;
using System.Text;
//using System.Collections.ObjectModel;

namespace MyFile
{
    
     public class File
    {
       public string m_sTenFile;
       public string m_sUserGiuFile;
       public string m_bTinhTrangDownLoad;
       public string m_bTinhTrangUser;
       public Queue<string> HangDoiDownLoad;

       public File()
       {
           m_sTenFile = "";
           m_sUserGiuFile = "";
           m_bTinhTrangDownLoad="khong";
           m_bTinhTrangUser="offline";
           HangDoiDownLoad = new Queue<string>();
       }
    }
}

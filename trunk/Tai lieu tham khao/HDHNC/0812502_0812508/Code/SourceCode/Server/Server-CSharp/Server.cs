using System;
using System.Collections.Generic;
using System.Text;
using File;
using User;
using System.Net.Sockets;
using System.Collections.Generics;

namespace ConsoleApplication1
{
    class Server
    {
        List <File*> m_arrDanhSachFile;
        List <User*> m_arrDanhSachUser;
        Socket m_socServer;
        Socket m_socTam;

        public void LangNghe()
        {
            //create the listening socket...
            m_socServer = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            IPEndPoint ipLocal = new IPEndPoint(IPAddress.Any, 8221);
            //bind to local IP Address...
            m_socServer.Bind(ipLocal);
            //start listening...
            m_socServer.Listen(4);
            // create the call back for any client connections...
          //  m_socListener.BeginAccept(new AsyncCallback(OnClientConnect), null);
            //cmdListen.Enabled = false;
        }

        public string DangNhap (string s)
        {
	        string username;
	        string password;
	        int tam=0,i,j=0;
	        for ( i=0;i<s.size();i++)
	        {
		        if(s[i] != '-')
			        username[i]= s[i];
		        else
                {
                    tam=i;
                    break;
                }
	        }
	        for( i=0; i < m_arrDanhSachUser.size();i++)
	        {
		        if(m_arrDanhSachUser[i]->m_sTenUser == username)
		        {
			        for (i=tam+1;i<s.size();i++)
			        {
				        password[j] = s[i];
				        j++;
			        }
			        if(password == m_arrDanhSachUser[i]->m_sPassword)
				        return "dang nhap thanh cong";
			        else
				        return "nhap sai password";
		        }
	        }
	        return "yeu cau dang ky";

        }

        public File* TimKiemFile(string s)
        {
	        MyFile* kq;
	        string tenfile;
	        string username;
	        int tam=0,i,j=0;
	        for ( i=0;i<s.size();i++)
	        {
		        if(s[i] != '-')
			        tenfile[i]= s[i];
		        else
                {
                    tam=i;
                    break;
                }
	        }
	        for (i=tam+1;i<s.size();i++)
	        {
		        username[j]=s[i];
	        }

	        for(i =0;i< this->m_arrDanhSachFile.size() ;i++)
	        {
		        if(m_arrDanhSachFile[i]->m_sTenFile == tenfile)
		        {
			        if(m_arrDanhSachFile[i]->m_bTinhTrangUser == 1)
			        {
				        if(this->m_arrDanhSachFile[i]->m_bTinhTrangDownLoad == 1)
					        this->m_arrDanhSachFile[i]->HangDoiDownLoad.push(username);
				        kq = m_arrDanhSachFile[i];
			        }
			        return kq;
		        }

	        }
	        return NULL;
        }

        public string DangKy(string s)
        {
	        User *kq;
	        int i,tam=0,j=0;
	        for(i=0;i<s.size();i++)
	        {
		        if(s[i] != '-')
			        kq->m_sTenUser[i] = s[i];
		        else
                {
                    tam=i;
                    break;
                }
	        }
	        for(i=tam+1;i<s.size();i++)
	        {
		        if(s[i] != '-')
			        kq->m_sPassword[j] = s[i];
		        else
		        {
			        tam=i;
			        j=0;
                    break;
		        }
	        }
	        for(i=tam+1;i<s.size();i++)
	        {
		        if(s[i] != '-')
			        kq->m_IpAddress[j] = s[i];	
	        }
	       // this->m_arrDanhSachUser.push_back(kq);
            m_arrDanhSachFile.Add(kq);
	        return "Dang Ky Thanh Cong,Moi dang nhap lai";

        }

        void DangXuat(string s, System.Net.Sockets.Socket a)
        {
	        int i;
	        for(i=0;i<this->m_arrDanhSachFile.size();i++)
	        {
		        if(this->m_arrDanhSachFile[i]->m_sUserGiuFile == s)
			        this->m_arrDanhSachFile[i]->m_bTinhTrangUser = 0;
	        }
	        a.Close();
        }

        public string GuiTraLoiChoUser (MyFile *s)
        {
            if(s==null)
                return "khong tim thay file";
            else
            {
            string kq= s->m_sTenFile + "-"+s->m_sUserGiuFile+"-"+s->m_bTinhTrangDownLoad+"-"+s->m_bTinhTrangUser;
	        return kq;
            }
        }

        public string GuiYeuCauChoHost(MyFile *s, string userYeuCau)
        {
	        string kq;
	        kq = s->m_sTenFile+'-'+userYeuCau;
	        return kq;
        }

        public string NhanFile (string s)
        {
            string kq,filename,username;
            File file;
            int i,j=0,tam;
            for(i=0;i<s.Length;i++)
            {
                if(s[i]!= '-')
                    filename[i]=s[i];
                else
                {
                    tam=i;
                    break;
                }
            }
            for(int e=0;e<m_arrDanhSachFile.Count;e++)
                if(m_arrDanhSachFile[i]->m_sTenFile == filename)
                    return "file da co tren server";
            for(i=tam+1;i<s.Length;i++)
            {
                username[j]=s[i];
            }
            
            file.m_sTenFile=filename;
            file.m_sUserGiuFile = username;
            m_arrDanhSachFile.Add(*file);
            return "Nhan file thanh cong";
        }

      public string XoaFile (string s)
      {
        int i,j=0;
          string filename;
          for(i=0;i<s.Length;i++)
            {
                if(s[i]!= '-')
                    filename[i]=s[i];
                else
                {
                    tam=i;
                    break;
                }
            }
        for(i=0;i<m_arrDanhSachFile.Count;i++)
        {
            if(m_arrDanhSachFile[i]->m_sTenFile == filename)
                m_arrDanhSachFile.Remove(m_arrDanhSachFile[i]);
            return "Da Xoa";
        }

      }
        public string NhanXong (string s)
        {
            int i,j=0;
              string filename;
            string username;
              for(i=0;i<s.Length;i++)
                {
                    if(s[i]!= '-')
                        filename[i]=s[i];
                    else
                    {
                        tam=i;
                        break;
                    }
                }
            for(i=tam+1;i<s.Length;i++)
            {
                username[j]=s[i];
                j++;
            }
            for(int e=0;e<m_arrDanhSachFile.Count;e++)
            { 
                if(m_arrDanhSachFile[e]->m_sTenFile == filename && m_arrDanhSachFile[e]->m_sUserGiuFile == username)
                {
                    m_arrDanhSachFile.Remove(m_arrDanhSachFile[e]);
                    return "Da Xoa";
                }
            }
       
        }
        public string PhanTichGoiTin(char* s)
        {
            int i, flag, j = 0;
            string kq;
            string tam = s;
            string temp;

            flag = int.Parse(tam[0]);

            for (i = 0; i < tam.Length; i++)
                temp[j] = tam[i];

            switch (flag)
            {
                case 1: kq = DangNhap(temp); break;
                case 2:
                    {
                        File* tam;
                        tam = TimKiemFile(temp);
                        kq = GuiTraLoiChoUser(tam);
                    }
                case 3: kq = DangKy(temp); break;
                case 4: kq = DangXuat(temp); break;
                case 5: kq = NhanFile(temp); break;
                case 6: kq = XoaFile(temp); break;
                case 7: kq = NhanXong(temp); break;

            }

            /* if(tam[0] == 1)
             {
                 string DangNhap;
                 int j=0;
                 for(i=2;i<tam.length();i++)
                     DangNhap[j]=tam[i];
                 kq = this->DangNhap(DangNhap);
                 return kq;
             }
             else
                 if(tam[0]==3)
                 {
                     string dangky;
                     int j=0;
                     for(i=2;i<tam.length();i++)
                         dangky[j]=tam[i];
                     kq = this->DangKy(dangky);
                     return kq;
                 }
                 else
                 {
                     string TenFile;
                     int j=0;
                     for(i=2;i<tam.length();i++)
                         TenFile[j]=tam[i];
                     //kq = this->DangKy(dangky);
                     //File
                     return kq;
                 }*/
        }
    }
}

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using ClassLibrary;
using System.Net;
using System.Net.Sockets;
using System.IO;
using System.Threading;
using System.Data.OleDb;

namespace presentation
{
    public partial class FormChiaSeFile : Form
    {
        //Khai bao them 2 class moi là MyUser va SocketClient
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
       
        public class SocketClient
        {
            //tao them 1 class de luu thong tin file user nay chia se
            public class FileChiaSe
            {
                public string filePath;
                public string filename;
                public FileChiaSe()
                {
                    filename = "";
                    filePath = "";
                }
            }
            public static OleDbConnection KetNoi()
            {
                String chuoiKetNoi = "Provider=Microsoft.Jet.OLEDB.4.0;Data Source=CSDL.mdb";
                OleDbConnection ketNoi = new OleDbConnection(chuoiKetNoi);
                ketNoi.Open();
                return ketNoi;
            }
            public static List<FileChiaSe> layDanhSachFile()
            {
                OleDbConnection ketNoi = null;
                List<FileChiaSe> ds_File = new List<FileChiaSe>();
                try
                {
                    ketNoi = KetNoi();
                    string chuoiLenh = "SELECT TenFile, DuongDan "
                                        + "FROM TableFile  ";
                    OleDbCommand lenh = new OleDbCommand(chuoiLenh, ketNoi);
                    OleDbDataReader boDoc = lenh.ExecuteReader();
                    while (boDoc.Read())
                    {
                        FileChiaSe tam = new FileChiaSe();
                        if (!boDoc.IsDBNull(0))
                            tam.filename = boDoc.GetString(0);
                        if (!boDoc.IsDBNull(1))
                            tam.filePath = boDoc.GetString(1);
                        ds_File.Add(tam);
                    }
                }
                catch
                {
                    ds_File = new List<FileChiaSe>();
                }
                finally
                {
                    if (ketNoi != null && ketNoi.State == System.Data.ConnectionState.Open)
                        ketNoi.Close();
                }
                return ds_File;
            }

            public static bool themFileChiaSe(FileChiaSe FileNew)
            {
                bool ketQua = false;
                OleDbConnection ketNoi = null;
                try
                {
                    ketNoi = KetNoi();
                    string chuoiLenh = "INSERT INTO TableFile( TenFile, DuongDan) VALUES( @TenFile, @DuongDan)";
                    OleDbCommand lenh = new OleDbCommand(chuoiLenh, ketNoi);
                    OleDbParameter thamSo = new OleDbParameter("@TenFile", OleDbType.VarChar);
                    thamSo.Value = FileNew.filename;
                    lenh.Parameters.Add(thamSo);
                    thamSo = new OleDbParameter("@DuongDan", OleDbType.VarChar);
                    thamSo.Value = FileNew.filePath;
                    lenh.Parameters.Add(thamSo);
                    lenh.ExecuteNonQuery();
                }
                catch (Exception ex)
                {
                    ketQua = false;
                }
                finally
                {
                    if (ketNoi != null && ketNoi.State == System.Data.ConnectionState.Open)
                        ketNoi.Close();
                }
                return ketQua;
            }

            public static bool xoaFileChiaSe(string FileName)
            {
                bool ketQua=true;
                OleDbConnection ketNoi = null;
                try
                {
                    ketNoi = KetNoi();
                    string chuoiLenh = "Delete from TableFile where TenFile=@TenFile ";
                    OleDbCommand lenh = new OleDbCommand(chuoiLenh, ketNoi);
                    OleDbParameter thamSo = new OleDbParameter("@TenFile", OleDbType.VarChar);
                    thamSo.Value = FileName;
                    lenh.Parameters.Add(thamSo);
                    lenh.ExecuteNonQuery();
                }
                 catch (Exception ex)
                {
                    ketQua = false;
                }
                finally
                {
                    if (ketNoi != null && ketNoi.State == System.Data.ConnectionState.Open)
                        ketNoi.Close();
                }
                return ketQua;
            }



            public Socket m_socLangNghe;
            public Socket[] m_socLamViec;
            public IPAddress ipServer;
            public int m_iSoClient,portServer,m_iPort;
            public string MyName;
            public List<FileChiaSe> m_DanhSachFileChiaSe=layDanhSachFile();

            public void LayThongTin(IPAddress ipserver,int portsv,int myport,string name)
            {
                ipServer = ipserver;
                portServer = portsv;
                m_iPort = myport;
                MyName = name;
                 
            }
            public void LangNghe(int port)
            {
                try
                {
                    m_socLamViec = new Socket[200];
                    //create the listening socket...
                    m_socLangNghe = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                    IPEndPoint ipLocal = new IPEndPoint(System.Net.IPAddress.Any, port);
                    //bind to local IP Address...
                    m_socLangNghe.Bind(ipLocal);
                    //start listening...
                    m_socLangNghe.Listen(10);
                    // create the call back for any client connections...
                    m_socLangNghe.BeginAccept(new AsyncCallback(ClientKetNoi), null);

                    //send thong bao da bta dau lang nghe cho server
                    Socket soctam = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                    IPEndPoint ipEP = new IPEndPoint(ipServer, portServer);
                    try
                    {                                           
                        soctam.Connect(ipEP);
                        string send = "9-"+MyName+"-da online";
                        byte[] send2 = new byte[1024];
                        send2 = Encoding.ASCII.GetBytes(send);
                        soctam.Send(send2, send2.Length, SocketFlags.None);
                        soctam.Close();
                    }
                    catch (Exception ex)
                    {
                        soctam.Close();
                    }
                }
                catch (Exception ex)
                {
                    m_socLangNghe.Close();
                    
                }
              

            }
            public void ClientKetNoi(IAsyncResult asyn)
            {
                ThreadStart myThreadDelegate = new ThreadStart(LamViec);
                try
                {
                    m_socLamViec[m_iSoClient] = m_socLangNghe.EndAccept(asyn);
                }
                catch (Exception ex)
                {
                    m_socLangNghe.Close();
                }
                Thread myThread = new Thread(myThreadDelegate);
                try
                {
                    myThread.Start();
                    m_socLangNghe.BeginAccept(new AsyncCallback(ClientKetNoi), null);
                }
                catch (Exception ex)
                {
                    myThread.Abort();
                }
            }
            public void LamViec()
            {
                Socket m_socTam = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                try
                {
                    m_socTam = m_socLamViec[m_iSoClient];
                }
                catch (Exception ex)
                {
                    Thread.Sleep(1);
                }
               try
                {
                    while (true)
                    {
                        byte[] data = new byte[1024];
                        int recv = m_socTam.Receive(data);
                        string s = Encoding.ASCII.GetString(data, 0, recv);
                        string kq = PhanTichGoiTin(s, m_socTam);
                        if(kq!="khong show")
                            MessageBox.Show(kq);
                        
                    }
                }
                catch (Exception ex)
                {
                   // MessageBox.Show(ex.ToString());
                    //m_socTam.Close();
                } 
            }
            public string PhanTichGoiTin(string s, Socket soc)
            {
                int flag;
                string kq = "";               
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
                            string send = "3-" + user.m_sFilename;
                            data = Encoding.ASCII.GetBytes(send);
                            socTam.Send(data, data.Length, SocketFlags.None);
                            string kqq=DownLoadFile(socTam, user.m_sFilename);
                            if (kqq == "xong")
                                //socTam.Close();
                                kq = "Da download " + user.m_sFilename;
                            else
                                //MessageBox.Show(kqq);
                                kq = kqq;

                        } break;
                    case 3:
                        {
                            string filename = temp;
                            string kiemTra = UpLoadFile(filename, soc);
                            if (kiemTra == "da upload")
                            {
                                Socket socTam = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                                IPEndPoint ipEP = new IPEndPoint(ipServer, portServer);
                                socTam.Connect(ipEP);
                                try
                                {
                                    //gui thong bao da upload xong 
                                    string data = "7-" + filename + "-" + MyName;
                                    byte[] send = new byte[1024];
                                    send = Encoding.ASCII.GetBytes(data);
                                    socTam.Send(send, send.Length, SocketFlags.None);
                                    //nhan thong bao tu server
                                    byte[] data1 = new byte[1024];
                                    int recv = socTam.Receive(data1);
                                    string nhan = Encoding.ASCII.GetString(data1, 0, recv);
                                    MessageBox.Show(nhan);
                                    kq = "Da upload file " + filename;
                                }
                                catch (Exception ex)
                                {
                                    MessageBox.Show(ex.ToString());
                                    socTam.Close();
                                }
                            }
                            else
                                kq = kiemTra;
                            } break;
                        
                }
                return kq;

            }
            public string DownLoadFile(Socket soc, string filename)
            {

                byte[] kiemtra = new byte[1024];
                int kt = soc.Receive(kiemtra);
                string ktra = Encoding.ASCII.GetString(kiemtra, 0, kt);
                if (ktra == "file khong ton tai hoac da bi xoa")
                    //MessageBox.Show(ktra);
                    return ktra;
                else
                {

                    FileStream writeFile = new FileStream("C:/DownLoad/" + filename, FileMode.Append, FileAccess.Write, FileShare.Write);
                    BinaryWriter bWrite = new BinaryWriter(writeFile);
                    byte[] Data = new byte[1024];

                    //nhan kich thuong file
                    byte[] datasize = new byte[10 * 1024];
                    int totalDataSize = soc.Receive(datasize);
                    string nhan = Encoding.ASCII.GetString(datasize, 0, totalDataSize);
                    totalDataSize = int.Parse(nhan);

                    //Tao chuoi de thong bao bat dau nhan du lieu
                    string s = "send";
                    datasize = Encoding.ASCII.GetBytes(s);
                    soc.Send(datasize, datasize.Length, SocketFlags.None);
                    int byterecv = 0;
                    while (byterecv < totalDataSize)
                    {
                        try
                        {
                            int receivedBytesLen = soc.Receive(Data);
                            bWrite.Write(Data, 0, receivedBytesLen);
                            byterecv += receivedBytesLen;
                        }
                        catch (Exception ex)
                        {
                            bWrite.Close();
                            soc.Close();
                            Thread.Sleep(1);
                            MessageBox.Show(ex.ToString());
                            return "loi"; ;
                        }
                    }
                    bWrite.Close();
                    soc.Close();
                    Thread.Sleep(1);
                    return "xong";
                }
            }
            public string UpLoadFile(string filename, Socket soc)
            {
                string ketQua;
         
                int sentDataSize=0, bufferSize=1024;
               
                byte[] data = new byte[bufferSize];

                //tim file mà client mún down
                FileChiaSe tam = new FileChiaSe();
                for (int i = 0; i < m_DanhSachFileChiaSe.Count; i++)
                {
                    if (m_DanhSachFileChiaSe[i].filename == filename)
                    {
                        tam = m_DanhSachFileChiaSe[i];
                        break;
                    }
                }
                if (File.Exists(tam.filePath + filename) == false)
                {
                    ketQua = "file khong ton tai hoac da bi xoa";
                    byte[] data3 = new byte[1024];
                    data3 = Encoding.ASCII.GetBytes(ketQua);
                    soc.Send(data3, data3.Length, SocketFlags.None);
                    return ketQua;

                }
                else
                {
                    ketQua = "co ton tai";
                    byte[] data3 = new byte[1024];
                    data3 = Encoding.ASCII.GetBytes(ketQua);
                    soc.Send(data3, data3.Length, SocketFlags.None);
                    
                    //Tien hanh upload
                    FileStream readFile = new FileStream(tam.filePath + filename, FileMode.Open, FileAccess.Read);
                    BinaryReader bReader = new BinaryReader(readFile);

                    //Gui dung luong file
                    int totalDataSize = (int)bReader.BaseStream.Length;
                    byte[] data2 = new byte[10 * 1024];
                    data2 = Encoding.ASCII.GetBytes(totalDataSize.ToString());
                    soc.Send(data2, data2.Length, SocketFlags.None);
                    //nhan lai thong bao bat dau send
                    data2 = new byte[1024];
                    int nhanlai = soc.Receive(data2);
                    string mess = Encoding.ASCII.GetString(data2, 0, nhanlai);
                    if (mess == "send")
                    {

                        try
                        {
                            while (sentDataSize < bReader.BaseStream.Length)
                            {
                                if (sentDataSize + bufferSize <= bReader.BaseStream.Length)
                                {
                                    bReader.Read(data, 0, bufferSize);
                                    sentDataSize += bufferSize;
                                    soc.Send(data, data.Length, SocketFlags.None);


                                }
                                else
                                {
                                    //SEND LAST PACKET
                                    byte[] endData = new byte[(int)bReader.BaseStream.Length - sentDataSize];
                                    bReader.Read(endData, 0, (int)bReader.BaseStream.Length - sentDataSize);
                                    sentDataSize = (int)bReader.BaseStream.Length;
                                    soc.Send(endData, endData.Length, SocketFlags.None);

                                }
                                
                            }
                            return "da upload";
                        }
                        catch (Exception ex)
                        {
                            MessageBox.Show(ex.ToString());
                            bReader.Close();
                            soc.Close();
                            Thread.Sleep(1);
                            return "loi";
                        }
                    }

                    else
                        return "ket noi voi client gap van de";
                }
            }
            public string LuuFile(string s)
            {
                FileChiaSe filetam = new FileChiaSe();
                filetam.filename = s;

                filetam.filename = filetam.filename.Replace("\\", "/");
                while (filetam.filename.IndexOf("/") > -1)
                {
                    filetam.filePath += filetam.filename.Substring(0, filetam.filename.IndexOf("/") + 1);
                    filetam.filename = filetam.filename.Substring(filetam.filename.IndexOf("/") + 1);
                }
                m_DanhSachFileChiaSe.Add(filetam);
                themFileChiaSe(filetam);
                return filetam.filename;
            }
            public bool XoaFile(string s)
            {
                return xoaFileChiaSe(s);
            }
        }
        
        //Cac thuoc tinh cua client(can luu tru)
        public string User;
        public int PortLangNghe;
        public SocketServer socketS; 
        public SocketClient socketL;
        
    
        public FormChiaSeFile(string u,string p,SocketServer s)
        {
            InitializeComponent();
            User = u;
            string tam = p;
            PortLangNghe = int.Parse(tam);
            socketS = new SocketServer();
            socketS = s;
            socketL = new SocketClient();
            socketL.LayThongTin(socketS.ipeServer.Address,socketS.ipeServer.Port,PortLangNghe,User);
            socketL.LangNghe(PortLangNghe);
            
        }       

        private void btnTim_Click(object sender, EventArgs e)
        {
            string s = socketS.timFile(User,txtTenFile.Text);
            if (s == "khong tim thay file")
                MessageBox.Show(s);
            else
            {
                
                List<string> TenFile = new List<string>();
                List<string>UserGiu = new List<string>();
                List<string> TTUser = new List<string>();
                List<string> TTDownload=new List<string>();
                string[] arr;
                arr = s.Split(new char[] { '-' });
                for (int i = 0; i < arr.Length-1; i=i+4)
                {
                    TenFile.Add(arr[i]);
                    UserGiu.Add(arr[i+1]);
                    TTDownload.Add(arr[i+2]);
                    TTUser.Add(arr[i + 3]);
                }
                BangFile.Rows.Clear();
                List<MyFile> dsFile=new List<MyFile>();
                for(int j=0;j<TenFile.Count ;j++)
                {
                    MyFile temp=new MyFile();
                    temp.m_sTenFile=TenFile[j];
                    temp.m_sUserGiuFile=UserGiu[j];
                    temp.m_bTinhTrangUser=TTUser[j];
                    temp.m_bTinhTrangDownLoad=TTDownload[j];
                    dsFile.Add(temp);
                    BangFile.Rows.Add(TenFile[j], UserGiu[j], TTUser[j], TTDownload[j]);
                 
                    BangFile.Rows[BangFile.RowCount-1].Tag=dsFile[j];
                 }
            }
        }
        
        private void btnThem_Click(object sender, EventArgs e)
        {
          
            FileDialog fDg = new OpenFileDialog();
            string filename = "";
            if (fDg.ShowDialog() == DialogResult.OK)
            {
                filename = socketL.LuuFile(fDg.FileName);
                string s = socketS.themFile(User, filename);
                MessageBox.Show(s);

            }
            else
            {
                MessageBox.Show("Ban chua chon file nen viec them file that bai");
                return;
            }
            
            

        }

        private void btnDongKetNoi_Click(object sender, EventArgs e)
        {            
            socketS.DangXuat(User);            
            socketL.m_socLangNghe.Close();
            Thread.Sleep(1);
            Close();
            
        }

        private void btnXoa_Click(object sender, EventArgs e)
        {
            string s = socketS.xoaFile(User, txtTenFile.Text);
            if (s == "Da Xoa")
                socketL.XoaFile(txtTenFile.Text);
                
            MessageBox.Show(s);

        }

        private void btnDownload_Click(object sender, EventArgs e)
        {
            try
            {
                MyFile chon = new MyFile();
                chon = (MyFile)BangFile.CurrentRow.Tag;
                BangFile.Rows.Clear();
                BangFile.Rows.Add(chon.m_sTenFile, chon.m_sUserGiuFile);
                socketS.yeuCauDownload(User, chon.m_sUserGiuFile, chon.m_sTenFile);
            }
            catch (Exception ex)
            {
                MessageBox.Show("vui long chon file download");
                return;
            }
        }

        private void notifyIcon1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            this.Visible = true;          
        }

        private void btnTrayIcon_Click(object sender, EventArgs e)
        {          
            this.Visible = false;
        }

        private void restoreToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.Visible = true;
        }

        private void closeAppToolStripMenuItem_Click(object sender, EventArgs e)
        {
            socketS.DangXuat(User);        
            socketL.m_socLangNghe.Close();
            Thread.Sleep(1);
            Close();
        }

        
    }
}
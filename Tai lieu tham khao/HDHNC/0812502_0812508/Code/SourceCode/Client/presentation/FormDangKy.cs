using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using ClassLibrary;
using presentation.Properties;
namespace presentation
{
    public partial class FormDangKy : Form
    {
        public SocketServer serverS = new SocketServer();
        public FormDangKy(SocketServer s)
        {
            InitializeComponent();
            serverS = s;

            
        }

        private void btnOk_Click(object sender, EventArgs e)
        {

            //string p=serverS.taoKetNoiDenServer("127.0.0.1", 8888);
            //MessageBox.Show(p);
            string s=serverS.taoUser(txtUser.Text, txtPassword.Text, txtNhapLai.Text,txtPort.Text);
            MessageBox.Show(s);
            if (s == "Dang Ky Thanh Cong,Moi dang nhap lai")
            {
                Close();
                FormDangNhap fDangNhap = new FormDangNhap(serverS);
                fDangNhap.Show();
            }
        }
    }
}
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using ClassLibrary;

namespace presentation
{
    public partial class FormDangNhap : Form
    {
        public SocketServer socketS;
        public FormDangNhap(SocketServer  s)
        {
            InitializeComponent();
            socketS = new SocketServer();
            socketS = s;    

        }
        
        

        private void bttDangKy_Click(object sender, EventArgs e)
        {
            FormDangKy fDangKy = new FormDangKy(socketS);
            Close();
            fDangKy.Show();            //Close();
        }

        private void btnDangNhap_Click(object sender, EventArgs e)
        {
            if(txtPortLangNghe.Text=="")
            {
                MessageBox.Show("Vui long nhap port da dang ky");
                return;
            }
            else
            {
            
                string kq = socketS.dangNhapUser(txtUser.Text, txtPassword.Text, txtPortLangNghe.Text);

                if (kq.Contains("dang nhap thanh cong"))
                {


                    FormChiaSeFile fChiaSeFile = new FormChiaSeFile(txtUser.Text, txtPortLangNghe.Text, socketS);
                    //fChiaSeFile.Show();
                    Close();
                    fChiaSeFile.Show();
                }
                else
                    MessageBox.Show(kq);
            }
        
        }

        private void txtPortLangNghe_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar < '0' || e.KeyChar > '9')
                e.Handled = true;
            if (e.KeyChar == 8)
                e.Handled = false;
        }
    }
}
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using MyFile;
using MyUser;
using MyThuVien;
using System.Net.Sockets;
using System.Threading;


namespace Server_CSharp
{
    public partial class Form1 : Form
    {
        private Server server;
       
        public Form1()
        {
            InitializeComponent();
            server = new Server();       
        }

        private void btnBatDau_Click(object sender, EventArgs e)
        {
           
            if (txtPort.Text == "")
            {
                MessageBox.Show("Chua nhap gia tri port");
                return;
            }
            String szPort = txtPort.Text;
            int alPort = System.Convert.ToInt16(szPort, 10);           
            server.m_iPort = alPort;
            server.LangNghe();         
            btnBatDau.Enabled = false;
        }

        private void btnKetThuc_Click(object sender, EventArgs e)
        {
            if (server.m_socServer.IsBound == true)
            {
                server.m_socServer.Close();
                Thread.Sleep(1);
                Close();
            }
            else
                Close();
        }
       
        private void btnTrayIcon_Click(object sender, EventArgs e)
        {
           
            this.Visible = false;

        }

        private void notifyIcon1_MouseDoubleClick_1(object sender, MouseEventArgs e)
        {
            this.Visible = true;
           
        }

       

        private void restoreToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.Visible = true;
        }

        private void closeAppToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (server.m_socServer.IsBound==true)
            {
                server.m_socServer.Close();
                Thread.Sleep(1);
                Close();
            }
            else
                Close();
        }
        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            notifyIcon1.Visible = false;
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void txtPort_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar < '0' || e.KeyChar > '9')
                e.Handled = true;
            if (e.KeyChar == 8)
                e.Handled = false;
        }
      
    }
}
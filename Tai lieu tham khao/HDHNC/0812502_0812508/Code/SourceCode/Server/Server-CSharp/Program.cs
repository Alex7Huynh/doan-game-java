using System;
using System.Collections.Generic;
using System.Windows.Forms;
using MyFile;
using MyUser;
using MyThuVien;

namespace Server_CSharp
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }
    }
}
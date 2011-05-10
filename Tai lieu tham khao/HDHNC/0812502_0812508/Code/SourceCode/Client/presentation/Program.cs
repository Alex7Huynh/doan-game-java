using System;
using System.Collections.Generic;
using System.Windows.Forms;
using ClassLibrary;
namespace presentation
{
   
    static class Program
    {
        static SocketServer s = new SocketServer();
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        //public SocketServer serverS = new SocketServer();
        static void Main()
        {
            
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new FormMain());
        }
    }
}
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
    
    public partial class FormMain : Form
    {
        public FormMain()
        {
            InitializeComponent();
        }

        private void btnKetNoi_Click(object sender, EventArgs e)
        {
            FormKetNoi fKetNoi = new FormKetNoi();
            fKetNoi.Show();
            
        }

      
    }
}
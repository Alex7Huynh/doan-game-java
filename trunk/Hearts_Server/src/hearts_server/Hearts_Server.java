/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Hearts_Server.java
 *
 * Created on Jun 6, 2011, 11:20:25 AM
 */

package hearts_server;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Czytelnik
 */
public class Hearts_Server extends javax.swing.JFrame {

    /** Creates new form Hearts_Server */
    public Hearts_Server() {
        PortLangNghe=0;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextPortLangNghe = new javax.swing.JTextField();
        jButtonBatDau = new javax.swing.JButton();
        jButtonDong = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(hearts_server.Hearts_ServerApp.class).getContext().getResourceMap(Hearts_Server.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setBackground(resourceMap.getColor("Form.background")); // NOI18N
        setName("Form"); // NOI18N

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setForeground(resourceMap.getColor("jLabel1.foreground")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextPortLangNghe.setFont(resourceMap.getFont("jtxtPortLangNghe.font")); // NOI18N
        jTextPortLangNghe.setText(resourceMap.getString("jtxtPortLangNghe.text")); // NOI18N
        jTextPortLangNghe.setName("jtxtPortLangNghe"); // NOI18N

        jButtonBatDau.setText(resourceMap.getString("jButtonBatDau.text")); // NOI18N
        jButtonBatDau.setName("jButtonBatDau"); // NOI18N
        jButtonBatDau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonBatDauMouseClicked(evt);
            }
        });

        jButtonDong.setText(resourceMap.getString("jButtonDong.text")); // NOI18N
        jButtonDong.setEnabled(false);
        jButtonDong.setName("jButtonDong"); // NOI18N
        jButtonDong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonDongMouseClicked(evt);
            }
        });

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setForeground(resourceMap.getColor("jLabel2.foreground")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonDong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextPortLangNghe, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextPortLangNghe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBatDau)
                    .addComponent(jButtonDong))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBatDauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBatDauMouseClicked
        // TODO add your handling code here:
        try {
            PortLangNghe = Integer.parseInt(jTextPortLangNghe.getText().trim());
            //khởi tạo socket với port mà người dùng nhập
            ServerS = new ServerSocket(PortLangNghe);
            jTextPortLangNghe.setEnabled(false);
            jButtonBatDau.setEnabled(false);
             jButtonDong.setEnabled(true);
            //khởi tạo ListenT với tham số truyền vô ServerS
            ListenT = new ListenThread(ServerS);

        } catch (IOException ex) {
            Logger.getLogger(Hearts_Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Bạn nhập port có giá trị không hợp lệ");
        }


    }//GEN-LAST:event_jButtonBatDauMouseClicked

    private void jButtonDongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDongMouseClicked
        try {
            // TODO add your handling code here:
            ServerS.close();
            ListenT.ListClient=null;
            ListenT.ListListenClientT=null;
            jTextPortLangNghe.setEnabled(true);
            jButtonBatDau.setEnabled(true);
            jButtonDong.setEnabled(false);
        } catch (IOException ex) {
            Logger.getLogger(Hearts_Server.class.getName()).log(Level.SEVERE, null, ex);
        }



    }//GEN-LAST:event_jButtonDongMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Hearts_Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBatDau;
    private javax.swing.JButton jButtonDong;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextPortLangNghe;
    // End of variables declaration//GEN-END:variables
    private Integer PortLangNghe; // Giá trị port mà server lắng nghe kết nối tại port này
    ServerSocket ServerS;
    ListenThread ListenT; // Thread để lắng nghe kết nối từ các client

}

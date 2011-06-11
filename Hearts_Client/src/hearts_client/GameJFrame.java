/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GameJFrame.java
 *
 * Created on Jun 9, 2011, 3:44:50 PM
 */

package hearts_client;

/**
 *
 * @author Czytelnik
 */
public class GameJFrame extends javax.swing.JFrame {

    /** Creates new form GameJFrame */
    public GameJFrame() {
        initComponents();
    }

    GameJFrame(ConnectJFrame cf) {
        initComponents();
        ConnectF=cf;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LabMy = new javax.swing.JLabel();
        LabYou1 = new javax.swing.JLabel();
        LabYou2 = new javax.swing.JLabel();
        LabYou3 = new javax.swing.JLabel();
        LabBai = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextMegToServer = new javax.swing.JTextField();
        jButtonSend = new javax.swing.JButton();
        LabMeg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(hearts_client.Hearts_ClientApp.class).getContext().getResourceMap(GameJFrame.class);
        LabMy.setText(resourceMap.getString("LabMy.text")); // NOI18N
        LabMy.setName("LabMy"); // NOI18N

        LabYou1.setText(resourceMap.getString("LabYou1.text")); // NOI18N
        LabYou1.setName("LabYou1"); // NOI18N

        LabYou2.setText(resourceMap.getString("LabYou2.text")); // NOI18N
        LabYou2.setName("LabYou2"); // NOI18N

        LabYou3.setText(resourceMap.getString("LabYou3.text")); // NOI18N
        LabYou3.setName("LabYou3"); // NOI18N

        LabBai.setText(resourceMap.getString("LabBai.text")); // NOI18N
        LabBai.setName("LabBai"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextMegToServer.setText(resourceMap.getString("jTextMegToServer.text")); // NOI18N
        jTextMegToServer.setName("jTextMegToServer"); // NOI18N

        jButtonSend.setText(resourceMap.getString("jButtonSend.text")); // NOI18N
        jButtonSend.setName("jButtonSend"); // NOI18N
        jButtonSend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSendMouseClicked(evt);
            }
        });

        LabMeg.setText(resourceMap.getString("LabMeg.text")); // NOI18N
        LabMeg.setName("LabMeg"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextMegToServer, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSend))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(LabYou1)
                        .addGap(146, 146, 146)
                        .addComponent(LabYou3))
                    .addComponent(LabBai, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LabYou2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addComponent(LabMy)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 199, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LabMeg, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(187, 187, 187))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabYou2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(LabMeg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabYou3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabYou1))
                .addGap(26, 26, 26)
                .addComponent(LabMy)
                .addGap(40, 40, 40)
                .addComponent(LabBai)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextMegToServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSend))
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSendMouseClicked
        // TODO add your handling code here:
        ConnectF.sendMessage(jTextMegToServer.getText().trim() );
    }//GEN-LAST:event_jButtonSendMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameJFrame().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LabBai;
    public javax.swing.JLabel LabMeg;
    public javax.swing.JLabel LabMy;
    public javax.swing.JLabel LabYou1;
    public javax.swing.JLabel LabYou2;
    public javax.swing.JLabel LabYou3;
    private javax.swing.JButton jButtonSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextMegToServer;
    // End of variables declaration//GEN-END:variables
    public String MyName;
    public String YourName[]= new String[3];
    public ConnectJFrame ConnectF;
}

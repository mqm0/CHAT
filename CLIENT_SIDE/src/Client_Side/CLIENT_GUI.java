/*The MIT License (MIT)

Copyright (c) 2015 Muhammad Alhadi B.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.*/

package Client_Side;


import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author muhammad
 */
public class CLIENT_GUI extends javax.swing.JFrame {
    

    public static PRIVATE_MESSAGE CALL= new PRIVATE_MESSAGE();
            
    private static CLIENT ChatClient;
    public static final String UserName= LOG_IN.UserName;
    public static String messageTo;
    
    
   
    /**
     * Creates new form CLIENT_GUI
     */
    public CLIENT_GUI() {
        initComponents();
       
        pseudo.setText(UserName);
        Connect(LOG_IN.Host, LOG_IN.Port);
        
        
    }
    
        public static void Connect(String host, int port){
        try {
            
            Socket SOCK= new Socket(host, port);
            System.out.println("You connected to: "+host);
            
            ChatClient= new CLIENT(SOCK);
            
            PrintWriter OUT= new PrintWriter(SOCK.getOutputStream());
            OUT.println("/join "+UserName);
            OUT.flush();
            
            Thread X= new Thread(ChatClient);
            X.start();
            
            
        } catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Server not responding.");
            System.exit(0);
        }
    }
    

        
    public static void ACTION_B_SEND_PRIVATE(String HEAD){
        if(!PRIVATE_MESSAGE.msg.getText().equals("")){
            ChatClient.SEND_PRIVATE(HEAD+" "+PRIVATE_MESSAGE.msg.getText());
            PRIVATE_MESSAGE.msg.requestFocus();
        }
        
    }
    
    public static void ACTION_B_SEND(){
        if(!message.getText().equals("")){
            ChatClient.SEND(message.getText());
            message.requestFocus();
        }
    }
    
    public static void ACTION_B_DISCONNECT(){
        try {
            ChatClient.DISCONNECT();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public static void ACTION_B_SERV(){
        try {
            ChatClient.SERV("GET_TIME");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        conversation = new javax.swing.JTextArea();
        message = new javax.swing.JTextField();
        send = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        online = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        disconnect = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        pseudo = new javax.swing.JLabel();
        Time = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(10, 8, 8));
        jPanel1.setForeground(new java.awt.Color(24, 108, 243));

        conversation.setBackground(new java.awt.Color(197, 197, 197));
        conversation.setColumns(20);
        conversation.setForeground(new java.awt.Color(9, 31, 230));
        conversation.setRows(5);
        jScrollPane1.setViewportView(conversation);

        message.setBackground(new java.awt.Color(197, 197, 197));
        message.setForeground(new java.awt.Color(9, 31, 230));
        message.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageActionPerformed(evt);
            }
        });
        message.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageKeyPressed(evt);
            }
        });

        send.setBackground(new java.awt.Color(31, 28, 28));
        send.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        send.setForeground(new java.awt.Color(247, 44, 44));
        send.setText("SEND");
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });
        send.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sendKeyPressed(evt);
            }
        });

        online.setBackground(new java.awt.Color(197, 197, 197));
        online.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        online.setForeground(new java.awt.Color(255, 0, 6));
        online.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        online.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                onlineMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onlineMouseClicked(evt);
            }
        });
        online.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                onlineValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(online);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 126, 255));
        jLabel1.setText("Conversation");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 126, 255));
        jLabel2.setText("Currently Online");

        disconnect.setBackground(new java.awt.Color(31, 28, 28));
        disconnect.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        disconnect.setForeground(new java.awt.Color(247, 44, 44));
        disconnect.setText("DISCONNECT");
        disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 126, 255));
        jLabel3.setText("Logged In As");

        pseudo.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        pseudo.setForeground(new java.awt.Color(21, 255, 0));
        pseudo.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(13, 17, 203)));

        Time.setBackground(new java.awt.Color(31, 28, 28));
        Time.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        Time.setForeground(new java.awt.Color(247, 44, 44));
        Time.setText("Date/Time");
        Time.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TimeMouseClicked(evt);
            }
        });
        Time.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(message)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(disconnect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Time)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(send, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(disconnect)
                            .addComponent(Time))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(message)
                    .addComponent(send, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void messageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_messageActionPerformed

    private void sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendActionPerformed
        // TODO add your handling code here:
        ACTION_B_SEND();
    }//GEN-LAST:event_sendActionPerformed

    private void disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectActionPerformed
        ACTION_B_DISCONNECT();
        
    }//GEN-LAST:event_disconnectActionPerformed

    private void onlineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onlineMouseClicked
        messageTo= (String) online.getSelectedValue();
        CALL.setTitle((String)online.getSelectedValue());
        CALL.setVisible(true);
    }//GEN-LAST:event_onlineMouseClicked

    private void onlineMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onlineMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_onlineMousePressed

    private void onlineValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_onlineValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_onlineValueChanged

    private void TimeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TimeMouseClicked

        ACTION_B_SERV();
    }//GEN-LAST:event_TimeMouseClicked

    

    private void sendKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sendKeyPressed
        if(evt.getKeyCode()== java.awt.event.KeyEvent.VK_ENTER)
            ACTION_B_SEND();
    }//GEN-LAST:event_sendKeyPressed

    private void messageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageKeyPressed
        if(evt.getKeyCode()== java.awt.event.KeyEvent.VK_ENTER)
            ACTION_B_SEND();
    }//GEN-LAST:event_messageKeyPressed

    private void TimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TimeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CLIENT_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CLIENT_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CLIENT_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CLIENT_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new CLIENT_GUI().setVisible(true);
                
                
            }
        });
        
    }


    


   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Time;
    public static javax.swing.JTextArea conversation;
    private javax.swing.JButton disconnect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTextField message;
    public static javax.swing.JList online;
    public static javax.swing.JLabel pseudo;
    private javax.swing.JButton send;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package game;

import go_client.Client;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author osman
 */
public class Game extends javax.swing.JFrame {
    public int turn = -1;
    public static Game ThisGame;
    public ImageIcon board = new ImageIcon("go_board.png");
    public ImageIcon piece_white = new ImageIcon("piece_white.png");
    //public ImageIcon piece_white = createImageIcon("images/piece_white.png", "piece_white");
    public JLabel lbl_board = new JLabel(board);
    public JLayeredPane lp_board = new JLayeredPane();
    public JButton btn_connect = new JButton("Connect");
    public JTextField txt_name = new JTextField("Name");
    public JTextField txt_rival_name = new JTextField("Rival");
    public JTextArea txt_send = new JTextArea();
    public JButton btn_send_message = new JButton("Send");
    public JTextArea txt_receive = new JTextArea();
    public JLabel lbl_info = new JLabel();
    
    JLabel[][] pieces = new JLabel[6][6];
    JLabel piece1 = new JLabel(piece_white);
    
    public Game() {
        initComponents();
        
        ThisGame = this;
        
        txt_name.setBounds(20, 20, 80, 25);
        ThisGame.add(txt_name);
        
        btn_connect.setBounds(130, 20, 80, 25);
        btn_connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.Start("localhost", 2000);
                //başlangıç durumları

                btn_connect.setEnabled(false);
                txt_name.setEnabled(false);
               
                btn_send_message.setEnabled(false);
            }
            
        });
        ThisGame.add(btn_connect);
        
        txt_rival_name.setBounds(240, 20, 80, 25);
        txt_rival_name.setBackground(new Color(221, 221, 221));
        txt_rival_name.setEnabled(false);
        ThisGame.add(txt_rival_name);
        
        lbl_info.setBounds(100, 50, 150, 25);
        lbl_info.setHorizontalAlignment(SwingConstants.CENTER);
        Font font = new Font("Courier",Font.BOLD,20);
        lbl_info.setFont(font);
        lbl_info.setText("Sen Beyazsın");
        lbl_info.setVisible(false);
        ThisGame.add(lbl_info);
        
        txt_send.setBounds(20, 390, 100, 60);
        txt_send.setEnabled(false);
        btn_send_message.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message msg = new Message(Message.Message_Type.Text);
                String x = txt_send.getText();
                msg.content = txt_send.getText();
                Client.Send(msg);
                txt_send.setText("");
            }
        });
        ThisGame.add(txt_send);
        
        btn_send_message.setBounds(140, 390, 60, 25);
        btn_send_message.setEnabled(false);
        ThisGame.add(btn_send_message);
        
        txt_receive.setBounds(220, 390, 100, 60);
        txt_receive.setEnabled(false);
        ThisGame.add(txt_receive);
        
        lbl_board.setBounds(0, 0, 300, 300);
        
        lp_board.add(lbl_board, Integer.valueOf(0));
        lp_board.setBounds(20, 80, 300, 300);
        ThisGame.add(lp_board);
        
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                pieces[i][j] = new JLabel(piece_white);
                pieces[i][j].setBounds(24 + (43 * j), 24 + (43 * i), 36, 36);
                pieces[i][j].setVisible(false);
                pieces[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e){
                        //BURADSIN!!
                    }
                });
                
                lp_board.add(pieces[i][j], Integer.valueOf(1));
            }
        }
        
        ThisGame.setBounds(0, 0, 350, 500);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Game().setVisible(true);
            }
        });
    }
    
    public void makeVissible(JLabel[][] array, int i, int j){
        array[i][j].setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

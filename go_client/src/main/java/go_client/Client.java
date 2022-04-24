/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package go_client;

import game.Message;
import game.Game;
import static go_client.Client.sInput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author osman
 */
// serverdan gelecek mesajları dinleyen thread
class Listen extends Thread {

    @Override
    public void run() {
        //soket bağlı olduğu sürece dön

        while (Client.socket.isConnected()) {
            try {
                //mesaj gelmesini bloking olarak dinyelen komut
                Message received = (Message) (sInput.readObject());
                //mesaj gelirse bu satıra geçer
                //mesaj tipine göre yapılacak işlemi ayır.
                switch (received.type) {
                    case Name -> {
                    }
                    case RivalConnected -> {
                        Object[] conn = (Object[]) received.content;
                        Game.ThisGame.turn = (Boolean) conn[1];
                        Game.ThisGame.txt_rival_name.setText((String) conn[0]);
                        Game.ThisGame.btn_send_message.setEnabled(true);
                        Game.ThisGame.txt_send.setEnabled(true);
                        Game.ThisGame.txt_receive.setEnabled(true);
                        if (Game.ThisGame.turn) {
                            Game.ThisGame.lbl_info.setText("You are Black");
                            Game.ThisGame.lbl_info.setVisible(true);
                            Game.ThisGame.my_piece_color = new ImageIcon("piece_black.png");
                            Game.ThisGame.rival_piece_color = new ImageIcon("piece_white.png");
                            Game.ThisGame.MY_COLOR = 1;
                            Game.ThisGame.ENEMY_COLOR = 2;
                            

                        } else {
                            Game.ThisGame.lbl_info.setText("You are White");
                            Game.ThisGame.lbl_info.setVisible(true);
                            Game.ThisGame.my_piece_color = new ImageIcon("piece_white.png");
                            Game.ThisGame.rival_piece_color = new ImageIcon("piece_black.png");
                            Game.ThisGame.MY_COLOR = 2;
                            Game.ThisGame.ENEMY_COLOR = 1;
                        }
                    }
                    case Disconnect -> {
                    }
                    case Text ->
                        Game.ThisGame.txt_receive.setText(received.content.toString());
                    case Selected -> {

                        Game.ThisGame.rival_selected_location = (int[]) received.content;
                        System.out.println("rival_selection: " + Game.ThisGame.rival_selected_location[0] + "," + Game.ThisGame.rival_selected_location[1]);
                        Game.ThisGame.pieces[Game.ThisGame.rival_selected_location[0]][Game.ThisGame.rival_selected_location[1]].setIcon(Game.ThisGame.rival_piece_color);
                        Game.ThisGame.board_int[Game.ThisGame.rival_selected_location[0]][Game.ThisGame.rival_selected_location[1]] = Game.ThisGame.ENEMY_COLOR;
                        Game.ThisGame.turn = true;
                        if (Game.ThisGame.isFull()) {
                            Game.ThisGame.turn = false;
                            int score = Game.ThisGame.score();
                            Game.ThisGame.lbl_info.setText("Score: "+score);
                            //Message msg = new Message(Message.Message_Type.Bitis);
                            //msg.content = kazandın veya kaybettin mesajı gönder ve labela yazdır
                            //Client.Send(msg);
                        }
                       
                    }

                    case Bitis -> {
                        //kazandın veya kaybettin mesajını labela yazdır
                    }

                }

            } catch (IOException | ClassNotFoundException ex) {

                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                //Client.Stop();
                break;
            }
            //Client.Stop();

        }

    }
}

public class Client {

    //her clientın bir soketi olmalı
    public static Socket socket;

    //verileri almak için gerekli nesne
    public static ObjectInputStream sInput;
    //verileri göndermek için gerekli nesne
    public static ObjectOutputStream sOutput;
    //serverı dinleme thredi 
    public static Listen listenMe;

    public static void Start(String ip, int port) {
        try {
            // Client Soket nesnesi
            Client.socket = new Socket(ip, port);
            Client.Display("Servera bağlandı");
            // input stream
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            // output stream
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
            Client.listenMe = new Listen();
            Client.listenMe.start();

            //ilk mesaj olarak isim gönderiyorum
            Message msg = new Message(Message.Message_Type.Name);
            msg.content = Game.ThisGame.txt_name.getText();
            Client.Send(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //client durdurma fonksiyonu
    public static void Stop() {
        try {
            if (Client.socket != null) {
                Client.listenMe.stop();
                Client.socket.close();
                Client.sOutput.flush();
                Client.sOutput.close();

                Client.sInput.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void Display(String msg) {

        System.out.println(msg);

    }

    //mesaj gönderme fonksiyonu
    public static void Send(Message msg) {
        try {
            Client.sOutput.writeObject(msg);
            Client.sOutput.reset();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

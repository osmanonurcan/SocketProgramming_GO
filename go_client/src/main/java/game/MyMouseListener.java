/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import go_client.Client;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 *
 * @author osman
 */
public class MyMouseListener extends MouseAdapter {

    JLabel piece;

    MyMouseListener(JLabel piece) {
        this.piece = piece;

    }

    @Override
    public void mouseClicked(MouseEvent clicked) {
        if (Client.socket.isConnected() && piece.getIcon() == null && Game.ThisGame.turn) {
            
            piece.setIcon(Game.ThisGame.my_piece_color);
            int x = (int) piece.getName().charAt(5) - '0';
            int y = (int) piece.getName().charAt(6) - '0';
            Game.ThisGame.board_int[x][y] = Game.ThisGame.MY_COLOR;
            System.out.println("My_selection: "+x+","+y);
            Game.ThisGame.my_selected_location[0] = x;
            Game.ThisGame.my_selected_location[1] = y;
            Message msg = new Message(Message.Message_Type.Selected);
            msg.content = Game.ThisGame.my_selected_location;
            Client.Send(msg);
            Game.ThisGame.turn = false;

            //Game.ThisGame.visible(Game.ThisGame.pieces, Game.ThisGame.turn);
        }

    }
}

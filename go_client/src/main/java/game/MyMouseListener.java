/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 *
 * @author osman
 */
public class MyMouseListener extends MouseAdapter{
    JLabel label;
    String text;
    MyMouseListener(JLabel label) {
        this.label = label;
       
    }
    // ...
    // use this.label instead of item[i], like this
    public void mouseEntered(MouseEvent entered) {

        
        

    }
}

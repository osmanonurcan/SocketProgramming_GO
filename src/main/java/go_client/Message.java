/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package go_client;

/**
 *
 * @author osman
 */
public class Message implements java.io.Serializable {
    public static enum Message_Type {None, Name, Disconnect,RivalConnected, Text, Selected, Bitis,Start,}
    
    public Message_Type type;
    public Object content;
    public Message(Message_Type t)
    {
        this.type=t;
    }
 

    
    
}

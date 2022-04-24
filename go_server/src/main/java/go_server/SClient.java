/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package go_server;

import game.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author osman
 */
public class SClient {

    int[] received_content = new int[2];
    int id;
    public String name = "NoName";
    Socket soket;
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;
    //clientten gelenleri dinleme threadi
    Listen listenThread;
    //cilent eşleştirme thredi
    PairingThread pairThread;
    //rakip client
    SClient rival;
    //eşleşme durumu
    public boolean paired = false;

    public SClient(Socket gelenSoket, int id) {
        this.soket = gelenSoket;
        this.id = id;
        try {
            this.sOutput = new ObjectOutputStream(this.soket.getOutputStream());
            this.sInput = new ObjectInputStream(this.soket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //thread nesneleri
        this.listenThread = new Listen(this);
        this.pairThread = new PairingThread(this);

    }

    //client mesaj gönderme
    public void Send(Message message) {
        try {
            this.sOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //client dinleme threadi
    //her clientin ayrı bir dinleme thredi var
    class Listen extends Thread {

        SClient TheClient;

        //thread nesne alması için yapıcı metod
        Listen(SClient TheClient) {
            this.TheClient = TheClient;
        }

        @Override
        public void run() {
            //client bağlı olduğu sürece dönsün

            while (TheClient.soket.isConnected()) {
                try {
                    Message received;
                    //mesajı bekleyen kod satırı

                    received = (Message) TheClient.sInput.readObject();

                    //mesaj gelirse bu satıra geçer
                    //mesaj tipine göre işlemlere ayır
                    switch (received.type) {
                        case Name -> {
                            TheClient.name = received.content.toString();
                            // isim verisini gönderdikten sonra eşleştirme işlemine başla
                            TheClient.pairThread.start();
                        }
                        case Disconnect -> {
                        }
                        case Text -> //gelen metni direkt rakibe gönder
                            Server.Send(TheClient.rival, received);
                        case Selected -> {//gelen seçim yapıldı mesajını rakibe gönder
                            received_content = (int[]) received.content;
                            System.out.println("received: " + received_content[0] + "," + received_content[1]);
                            Server.Send(TheClient.rival, received);

                        }
                        case Bitis -> {
                        }

                    }

                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    //client bağlantısı koparsa listeden sil
                    Server.Clients.remove(TheClient);

                }
                //client bağlantısı koparsa listeden sil

            }

        }
    }

    //eşleştirme threadi
    //her clientin ayrı bir eşleştirme thredi var
    class PairingThread extends Thread {

        SClient TheClient;

        PairingThread(SClient TheClient) {
            this.TheClient = TheClient;
        }

        @Override
        public void run() {
            //client bağlı ve eşleşmemiş olduğu durumda dön
            while (TheClient.soket.isConnected() && TheClient.paired == false) {
                try {
                    //lock mekanizması
                    //sadece bir client içeri grebilir
                    //diğerleri release olana kadar bekler
                    Server.pairTwo.acquire(1);

                    //client eğer eşleşmemişse gir
                    if (!TheClient.paired) {
                        SClient crival = null;
                        //eşleşme sağlanana kadar dön
                        while (crival == null && TheClient.soket.isConnected()) {
                            //liste içerisinde eş arıyor
                            for (SClient clnt : Server.Clients) {
                                if (TheClient != clnt && clnt.rival == null) {
                                    //eşleşme sağlandı ve gerekli işaretlemeler yapıldı
                                    crival = clnt;
                                    crival.paired = true;
                                    crival.rival = TheClient;
                                    TheClient.rival = crival;
                                    TheClient.paired = true;
                                    break;
                                }
                            }
                            //sürekli dönmesin 1 saniyede bir dönsün
                            //thredi uyutuyoruz
                            sleep(1000);
                        }
                        //eşleşme oldu
                        //her iki tarafada eşleşme mesajı gönder 
                        //oyunu başlat
                        Object[] client_conn = {TheClient.name, true};
                        Message msg_client_conn = new Message(Message.Message_Type.RivalConnected);
                        msg_client_conn.content = client_conn;
                        Server.Send(TheClient, msg_client_conn);

                        Object[] rival_conn = {TheClient.rival.name, false};
                        Message client_rival_conn = new Message(Message.Message_Type.RivalConnected);
                        client_rival_conn.content = rival_conn;
                        Server.Send(TheClient.rival, client_rival_conn);

                    }
                    //lock mekanizmasını servest bırak
                    //bırakılmazsa deadlock olur.
                    Server.pairTwo.release(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PairingThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}

package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import client.input.KeyboardHandler;
import client.input.MouseHandler;
import client.net.ClientReceiver;
import client.net.ClientSender;
import client.render.GameRenderer;
import shared.view.GameView;

public class Client extends Thread {
    MulticastSocket listenSocket;
    // Socket to send requests to the server
    MulticastSocket sendSocket;
    InetAddress listenAddress;
    InetAddress senderAddress;
    static final int LISTENPORT = 4444;
    static final int SENDPORT = 4445;
    GameView view;
    GameRenderer renderer;
    String playerName;
    int playerID;
    Boolean running;
    ClientSender sender;
    ClientReceiver receiver;
    private KeyboardHandler kbHandler;
    private MouseHandler mHandler;


    public Client(GameRenderer renderer, String playerName, int playerID){
        this.renderer = renderer;
        this.playerName = playerName;
        this.playerID = playerID;
        this.running = true;
        this.view = renderer.getView();
    }

    public void run(){
        try{
            listenSocket = new MulticastSocket(LISTENPORT);
            sendSocket = new MulticastSocket();
            listenAddress = InetAddress.getByName("230.0.1.1");
            senderAddress = InetAddress.getByName("230.0.0.1");


            // Start the sender and receiver threads for the client
            sender = new ClientSender(senderAddress, sendSocket, SENDPORT);
            receiver = new ClientReceiver(renderer, listenAddress, listenSocket, this);
            renderer.updateGameView(view);
            renderer.run();

            while(running){
                if(view != null) {
                	renderer.updateGameView(view);
                    //Thread.sleep(50); TODO: see if this is necessary, can be replaced with something more sophisticated
                }
            }

            // TODO: How will these threads close if the client is constantly rendering
            // Waits for the sender to join as that will be the first thread to close
            sender.join();
            // Waits for the receiver thread to end as this will be the second thread to close
            receiver.join();
            // Closes the socket as communication has finished
            sendSocket.close();
            listenSocket.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGameView(GameView view){
        this.view = view;
    }
    
    public ClientSender getClientSender() {
    	return this.sender;
    }

    public void close() {
    	this.running = false;
        sender.stopRunning();
        receiver.stopRunning();
    }

}
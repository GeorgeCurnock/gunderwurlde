package server.net;

import server.Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Thread to manage players trying to join the game
 */
public class JoinGameManager extends Thread {

    /**
     *  integer holding the next assignable ID
     */
    private static int lowestAvailableID;

    /**
     * boolean to tell if the thread should keep running
     */
    private boolean running;

    /**
     * reference to server so the player can be added to the game
     */
    private Server server;

    /**
     *  socket used to accept incoming join requests from clients
     */
    ServerSocket joinSocket;

    /**
     * Constructor
     * @param server reference to server so the player can be added to the game
     */
    public JoinGameManager(Server server){
        lowestAvailableID = 0;
        this.running = true;
        this.server = server;
    }

    /**
     * run method that when a client connects to the socket starts a thread to handle the player joining
     */
    public void run(){
        try {
            joinSocket = new ServerSocket(8081);
            while (running) {
                // For each connection spin off a new protocol instance.
                Socket connection = joinSocket.accept();
                // increase assignable ID so no 2 players have the same ID
                increaseAvailableID();
                Thread instance = new Thread(new JoinGameThread(connection, lowestAvailableID, server));
                instance.setName("joinGame-" + lowestAvailableID);
                instance.start();
            }
        } catch (SocketException e) {
            System.out.println("TCPManager ended");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to increase the assignable ID
     */
    public static void increaseAvailableID(){
        lowestAvailableID++;
    }

    /**
     * Method to close the thread when all players have joined
     * @throws IOException
     */
    public void close(){
        try {
            running = false;
            joinSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package server.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Enumeration;

import client.net.Addressing;
import server.Server;
import shared.lists.Teams;

// Gets messages from client and puts them in a queue, for another
// thread to forward to the appropriate client. Also controls server behaviour

public class ServerReceiver extends Thread {
    MulticastSocket listenSocket;
    InetAddress listenAddress;
    ServerSender sender;
    Boolean running;
    DatagramPacket packet;
    byte[] buffer;
    int numOfPlayers;
    Server handler;


    public ServerReceiver(InetAddress address, MulticastSocket listenSocket, ServerSender sender, Server handler) {
        this.listenSocket = listenSocket;
        this.listenAddress = address;
        this.sender = sender;
        this.handler = handler;
        buffer = new byte[255];
        running = true;
        Addressing.setInterfaces(listenSocket);
        this.start();
    }

    public boolean getRunning() {
        return running;
    }

    public void stopRunning() {
        this.running = false;
    }


    public void run() {
        try {
            listenSocket.joinGroup(listenAddress);

            while (running) {
                // packet to receive incoming messages
                packet = new DatagramPacket(buffer, buffer.length);
                // blocking method that waits until a packet is received
                listenSocket.receive(packet);

                // System.out.println("Packet recieved by ServerReceiver");

                // Creates a bytearrayinputstream from the received packets data
                byte[] receivedBytes = Arrays.copyOfRange(packet.getData(), 0, packet.getData().length-4);
                byte[] clientIDBytes = Arrays.copyOfRange(packet.getData(), packet.getData().length-4, packet.getData().length);

                ByteBuffer wrapped = ByteBuffer.wrap(clientIDBytes);
                int playerID = wrapped.getInt();
                System.out.println("ServerReceiver: client ID is " + playerID);
                ByteArrayInputStream bis = new ByteArrayInputStream(receivedBytes);
                //ObjectinputStream to turn the bytes back into an object.
                ObjectInputStream in = null;
                try {
                    in = new ObjectInputStream(bis);

                    Integer[] received =  (Integer[]) in.readObject();

                    //Request request = new Request();
                    switch(received[0]) {
                        case 99 :
                            joinGame(packet);
                    	case 0 : // ATTACK
                    		//request.requestShoot();
                    		handler.getClientRequests().playerRequestShoot(playerID);
                    		break;
                    	case 1 : // DROPITEM
                    		//request.requestDrop();
                    		handler.getClientRequests().playerRequestDrop(playerID);
                    		break;
                    	case 2 : // RELOAD
                    		//request.requestReload();
                    		handler.getClientRequests().playerRequestReload(playerID);
                    		break;
                    	case 3 : // CHANGEITEM
                    		//request.setSelectItem(received[1]);
                    		handler.getClientRequests().playerRequestSelectItem(playerID, received[1]);
                    		break;
                    	case 4 : // MOVEMENT
                    		//request.setMovementDirection(received[1]);
                    		handler.getClientRequests().playerRequestMovement(playerID, received[1]);
                    		break;
                    	case 5 : // TURN
                    		//request.setFacing(received[1]);
                    		handler.getClientRequests().playerRequestFacing(playerID, received[1]);
                    }

                    /*
                    // Send the request to the Engine
                    handler.setClientRequests(requests);

                    handler.setClientRequests(null);
                    */

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            // Waits for the sender to finish its processes before ending itself
            sender.join();
            // Running = false so the Thread ends gracefully
            running = false;
            System.out.println("Ending server receiver");
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void joinGame(DatagramPacket packet){
        try {
            // check that it isnt a player joining the game
            String data = new String(packet.getData());
            String[] seperateData = data.split(" ");
            String playerName = seperateData[0];
            Teams team = null;
            switch(seperateData[1]){
                // RED, BLUE, GREEN, YELLOW, ENEMY, NONE
                case "RED" :
                    team = Teams.RED;
                case "BLUE" :
                    team = Teams.BLUE;
                case "GREEN" :
                    team = Teams.GREEN;
                case "YELLOW" :
                    team = Teams.YELLOW;
            }
            handler.addPlayer(playerName, team);
        }
        catch(Exception e){

        }
    }
}

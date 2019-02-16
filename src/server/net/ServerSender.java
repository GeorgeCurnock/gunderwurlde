package server.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Enumeration;

import client.net.Addressing;
import shared.view.GameView;


public class ServerSender extends Thread {
    MulticastSocket senderSocket;
    InetAddress senderAddress;
    Boolean running;
    DatagramPacket packet;
    int port;
    byte[] buffer;
    int maxBufferSize;

    public ServerSender(InetAddress address, MulticastSocket socket, int port) throws SocketException {
        this.senderAddress = address;
        this.senderSocket = socket;
        this.port = port;
        running = true;
        senderSocket.setInterface(Addressing.findInetAddress());
        this.start();
    }

    public boolean getRunning() {
        return running;
    }

    public void stopRunning() {
        this.running = false;
    }

    public void run() {
        while (running) {
            Thread.yield();

        }
        System.out.println("Ending server sender");
    }

    // sends a confirmation back to the client that the message has been received
    // in future will be used to send the continuous game state to the user/users
    public void send(GameView view) {


        // System.out.println("Server received new GameView");
        try {
            // Turn the received GameView into a byte array
            // Output Stream for the byteArray. Will grow as data is added
            // Allows the object to be written to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // Output stream that will hold the object
            ObjectOutputStream out = null;
            try {
                // OOutputStream to read the GameView into the byteArray
                out = new ObjectOutputStream(bos);
                // Writes the view object into the BAOutputStream
                out.writeObject(view);
                //flushes anything in the OOutputStream
                out.flush();
                // Writes the info in the BOutputStream to a byte array to be transmitted

                int buffersize = bos.toByteArray().length;
                if(buffersize > maxBufferSize){
                    buffer = ByteBuffer.allocate(4).putInt(buffersize).array();
                    maxBufferSize = buffersize;
                    packet = new DatagramPacket(buffer, buffer.length, senderAddress, port);
                    System.out.println("ServerSender packet data size is:" + packet.getData().length);
                    senderSocket.send(packet);
                }


                buffer = bos.toByteArray();
                // System.out.println("Size of packet to be sent " + buffer.length);
                packet = new DatagramPacket(buffer, buffer.length, senderAddress, port);
                senderSocket.send(packet);
                // System.out.println("Packet sent from serversender");

            } finally {
                try {
                    bos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            // TODO Will be set on a loop to send every ______ seconds

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


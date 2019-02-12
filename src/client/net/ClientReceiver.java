package client.net;


import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import client.Client;
import client.render.GameRenderer;
import shared.view.GameView;

// Gets messages from other clients via the server (by the
// serverclientthreads.ServerSender thread).

public class ClientReceiver extends Thread {
    MulticastSocket listenSocket;
    InetAddress listenAddress;
    Boolean running;
    DatagramPacket packet;
    byte[] buffer;
    Client client;
    GameRenderer renderer;

    public ClientReceiver(GameRenderer renderer, InetAddress listenAddress, MulticastSocket listenSocket, Client client) {
        this.listenSocket = listenSocket;
        this.listenAddress = listenAddress;
        this.client = client;
        this.renderer = renderer;
        buffer = new byte[25000];
        running = true;
        setInterfaces(listenSocket);
        this.start();
    }

    public boolean getRunning() {
        return running;
    }

    public void stopRunning() {
        this.running = false;
    }

    public void setInterfaces(MulticastSocket listenSocket) {
        Enumeration<NetworkInterface> interfaces = null;
        // attempt to set the sockets interface to all the addresses of the machine
        try {
            // for all interfaces that are not loopback or up get the addresses associated with thos
            // interfaces and set the sockets interface to that address
//			}
            interfaces = NetworkInterface.getNetworkInterfaces();
            //while (interfaces.hasMoreElements()) {
            NetworkInterface iface = null;
            if (interfaces.hasMoreElements()) {
                iface = interfaces.nextElement();
            }

            if (!iface.isLoopback() || iface.isUp()) {
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                if (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    listenSocket.setInterface(addr);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        try {
            listenSocket.joinGroup(listenAddress);
            while (running) {

                // creates a packet and waits to receive a message from the server
                packet = new DatagramPacket(buffer, buffer.length);
                // blocking method waiting to receive a message from the server
                listenSocket.receive(packet);
                // System.out.println("Size of received packet" + packet.getData().length);
                // Creates a bytearrayinputstream from the received packets data
                ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
                //ObjectinputStream to turn the bytes back into an object.
                ObjectInputStream in = null;
                GameView view = null;
                try {
                    // System.out.println("Client received new gameview");
                    in = new ObjectInputStream(bis);
                    view = (GameView)in.readObject();
                    client.setGameView(view);
                    renderer.updateGameView(view);
                    renderer.getKeyboardHandler().setGameView(view);
                    renderer.getMouseHandler().setGameView(view);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (EOFException ex) {
                    ex.printStackTrace();
                    }
                    finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                // TODO how do threads exit?
            }
            System.out.println("Ending client receiver");
        }
          catch (SocketException e) {
            System.out.println("Socket closed unexpectedly");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
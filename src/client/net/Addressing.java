package client.net;

import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Addressing {

    // Sets the correct network interface to be communicated with
    public static void setInterfaces(MulticastSocket listenSocket) {
        Enumeration<NetworkInterface> interfaces;
        try {
            //check for interfaces that arent loopbacks
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback())
                    continue;
                // if that interface has an address that is for the ethernet port then add it to the socket
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if(iface.getDisplayName().equals("Realtek PCIe GBE Family Controller") ){
                        System.out.println(iface.getDisplayName());
                        listenSocket.setInterface(addr);
                    }
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
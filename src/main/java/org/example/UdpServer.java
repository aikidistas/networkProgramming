package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

/**
 * Hello world!
 */
public class UdpServer {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello UDP Server!");
        // InetAddress.class - dnsLookup

        // To create a server with UDP, do the following:

        // 1. Create a DatagramSocket attached to a port.
        int port = 1234;
        DatagramSocket socket = new DatagramSocket(port);

        // 2. Allocate space to hold the incoming packet,
        byte[] buffer = new byte[1024];
        // and create an instance of DatagramPacket to hold the incoming data.
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            // 3. Block until a packet is received, then extract the information you need from the packet.
            // Block on receive()
            socket.receive(packet);
//            System.out.println("Packet Received. Packet length: " + (packet.getLength()));
            // Find out where packet came from
            // so we can reply to the same host/port
//            InetAddress remoteHost = packet.getAddress();
//            int remotePort = packet.getPort();
            // Extract the packet data
            byte[] data = packet.getData();
            // The server can now process the data it has received from the client, and issue an
            // appropriate reply in response to the client 's request.


            System.out.println(new String(Arrays.copyOfRange(data, 0, packet.getLength())));
        }
    }

    /*
    Bash gives us a very simple way of sending a UDP packet to an IP address and port:

echo "This is my data" > /dev/udp/127.0.0.1/1234
    This example will send a UDP packet containing 'This is my data' to localhost on port 3000.
     */

}

package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClientCounting {
    public static void main(String[] args) throws IOException {

        // Creating UDP Clients

        // Writing code for a UDP client is similar to what we did for a server. Again, we
        // need a DatagramSocket and a DatagramPacket . The only real difference is that
        // we must specify the destination address with each packet, so the form of the
        // DatagramPacket constructor used here specifies the destination host and port
        // number. Then, of course, we initially send packets instead of receiving.

        // 1.First allocate space to hold the data we are sending and create an instance of
        // DatagramPacket to hold the data.
        int port = 1234;
        InetAddress host = InetAddress.getByName("localhost");

        // 2. Create a DatagramSocket and send the packet using this socket.
        DatagramSocket socket = new DatagramSocket();
        for (int i = 0; i < Integer.MAX_VALUE; ++i) {
            byte[] buffer = ("" + i).getBytes();
            DatagramPacket packet = new DatagramPacket(
                    buffer,
                    buffer.length,
                    host,
                    port
            );

            socket.send(packet);
        }

        // The DatagramSocket constructor that takes no arguments will allocate a free local
        // port to use. You can find out what local port number has been allocated for your
        // socket, along with other information about your socket if needed.
        // Find out where we are sending from
//        InetAddress localHostname = socket.getLocalAddress();
//        int localPort = socket.getLocalPort();

        // The client then waits for a reply from the server. Many protocols require the
        // server to reply to the host and port number that the client used, so the client can
        // now invoke socket.receive() to wait for information from the server.

        //socket.receive(packet);
    }
}

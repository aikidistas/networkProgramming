# Network Programming
## Topics in this section include:
- What a socket is
- What you can do with a socket
- The difference between TCP/IP, UDP/IP and Multicast sockets
- How servers and clients communicate over sockets
- How to create a simple server
- How to create a simple client
- How to create a multithreaded server

## Introduction
The Internet is all about connecting machines together. One of the most exciting
aspects of Java is that it incorporates an easy-to-use, cross-platform model for
network communications that makes it possible to learn network programming
without years of study. This opens up a whole new class of applications to
programmers.

## What is a Socket?
Java's socket model is derived from BSD (UNIX) sockets, introduced in the early
1980s for interprocess communication using IP, the Internet Protocol.

The Internet Protocol breaks all communications into packets, finite-sized chunks
of data which are separately and individually routed from source to destination. IP
allows routers, bridges, etc. to drop packets--there is no delivery guarantee. Packet
size is limited by the IP protocol to 65535 bytes. Of this, a minimum of 20 bytes
is needed for the IP packet header, so there is a maximum of 65515 bytes available
for user data in each packet.

Sockets are a means of using IP to communicate between machines, so sockets are
one major feature that allows Java to interoperate with legacy systems by simply
talking to existing servers using their pre-defined protocol.

Other common protocols are layered on top of the Internet protocol. The ones we
discuss in this chapter are the User Datagram Protocol (UDP) and the
Transmission Control Protocol (TCP). Applications can make use of these two
protocols to communicate over the network, commonly by building additional
protocols on top of the TCP or UDP base.

## Internet Addresses
Internet addresses are manipulated in Java by the use of the InetAddress class.
InetAddress takes care of the Domain Name System (DNS) look-up and reverse
look-up; IP addresses can be specified by either the host name or the raw IP
address. InetAddress provides methods to getByName() , getAllByName() ,
getLocalHost() , getAddress() , etc.

## IP Addresses
IP addresses are a 32-bit number, often represented as a "quad" of four 8-bit
numbers separated by periods. They are organized into classes (A, B, C, D, and
E) which are used to group varying numbers of hosts in a hierarchical numbering
scheme.
- Class A
1.0.0.0 to 126.255.255.255, inclusive. About 16 million IP addresses in a class A
domain.
- Class B
128.1.0.0 to 191.254.255.255, inclusive. About 64 thousand IP addresses in a
class B domain.
- Class C
192.0.1.0 to 223.255.254.255, inclusive. 256 IP addresses in a class C domain.
- Class D
224.0.0.1 to 239.255.255.255, inclusive, denote multicast groups.
- Class E
240.0.0.0 to 254.255.255.255, inclusive. Reserved for future use.

The IP address 127.0.0.1 is special, and is reserved to represent the loopback or
"localhost" address.

## Port number
The port number field of an IP packet is specified as a 16-bit unsigned integer.
This means that valid port numbers range from 1 through 65535. (Port number 0
is reserved and can't be used).

Java does not have any unsigned data types; Java's short data type is 16 bits, but
its range is -32768 to 32767 since it is a signed type. Thus, short is not large
enough to hold a port number, so all classes which use or return a port number
must represent the port number as an int . In the JDK 1.1, using an int with a
value greater than 65535 will generate an IllegalArgumentException . In the
JDK 1.0.2 and earlier, values greater than 65535 are truncated and only the low-
order 16 bits are used.

Port numbers 1 through 255 are reserved by IP for well-known services. A well-
known service is a service that is widely implemented which resides at a
published, "well-known", port. If you connect to port 80 of a host, for instance,
you may expect to find an HTTP server. On UNIX machines, ports less than
1024 are privileged and can only be bound by the root user. This is so an arbitrary
user on a multi-user system can't impersonate well-known services like TELNET
(port 23), creating a security problem. Windows has no such restrictions, but you
should program as if it did so that your applications will work cross-platform.

## Client/Server Computing
You can use the Java language to communicate with remote file systems using a
client/server model. A server listens for connection requests from clients across
the network or even from the same machine. Clients know how to connect to the
server via an IP address and port number. Upon connection, the server reads the
request sent by the client and responds appropriately. In this way, applications
can be broken down into specific tasks that are accomplished in separate
locations.

The data that is sent back and forth over a socket can be anything you like.
Normally, the client sends a request for information or processing to the server,
which performs a task or sends data back. You could, for example, place an SQL
shell on the server and let people talk to it via a simple client "chat" program.

The IP and port number of the server is generally well-known and advertised so
the client knows where to find the service. In contrast, the port number on client
the side is generally allocated automatically by the kernel.

Many protocols used on the Internet (HTTP for example) are designed to be
driven from the command line. They send their requests and responses across the
net in plain text. One of the easiest ways to become familiar with network
programming and/or specific protocols is to use the TELNET application to "talk"
directly to a server from the command line.

## User Datagram Protocol (UDP)
UDP provides an unreliable packet delivery system built on top of the IP
protocol. As with IP, each packet is an individual, and is handled separately.
Because of this, the amount of data that can be sent in a UDP packet is limited to
the amount that can be contained in a single IP packet. Thus, a UDP packet can
contain at most 65507 bytes (this is the 65535-byte IP packet size minus the
minimum IP header of 20 bytes and minus the 8-byte UDP header).

UDP packets can arrive out of order or not at all. No packet has any knowledge of
the preceding or following packet. The recipient does not acknowledge packets, so
the sender does not know that the transmission was successful. UDP has no
provisions for flow control--packets can be received faster than they can be used.
We call this type of communication connectionless because the packets have no
relationship to each other and because there is no state maintained.

The destination IP address and port number is encapsulated in each UDP packet.
These two numbers together uniquely identify the recipient and are used by the
underlying operating system to deliver the packet to a specific process
(application).

One way to think of UDP is by analogy to communications via a letter. You write
the letter (this is the data you are sending); put the letter inside an envelope (the
UDP packet); address the envelope (using an IP address and a port number); put
your return address on the envelope (your local IP address and port number); and
then you send the letter.

Like a real letter, you have no way of knowing whether a UDP packet was
received. If you send a second letter one day after the first, the second one may be
received before the first. Or, the second one may never be received.

So why use UDP if it unreliable? Two reasons: speed and overhead. UDP packets
have almost no overhead--you simply send them then forget about them. And
they are fast, since there is no acknowledgement required for each packet. Keep in
mind the degree of unreliability we are talking about. For all practical purposes, an
Ethernet breaks down if more than about 2 percent of all packets are lost. So,
when we say unreliable, the worst-case loss is very small.

UDP is appropriate for the many network services that do not require guaranteed
delivery. An example of this is a network time service. Consider a time daemon
that issues a UDP packet every second so computers on the LAN can
synchronize their clocks. If a packet is lost, it's no big deal--the next one will be
by in another second and will contain all necessary information to accomplish the
task.

Another common use of UDP is in networked, multi-user games, where a player's
position is sent periodically. Again, if one position update is lost, the next one
will contain all the required information.

A broad class of applications is built on top of UDP using streaming protocols.
With streaming protocols, receiving data in real-time is far more important than
guaranteeing delivery. Examples of real-time streaming protocols are RealAudio
and RealVideo which respectively deliver real-time streaming audio and video over
the Internet. The reason a streaming protocol is desired in these cases is because if
an audio or video packet is lost, it is much better for the client to see this as noise
or "drop-out" in the sound or picture rather than having a long pause while the
client software stops the playback, requests the missing data from the server.
That would result in a very choppy, bursty playback which most people find
unacceptable, and which would place a heavy demand on the server.
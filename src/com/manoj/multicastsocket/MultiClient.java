package com.manoj.multicastsocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Scanner;

public class MultiClient {
    private static final String TERMINATE = "Exit";
    static String name;
    static volatile boolean finished = false;
    private static String inet_addr="224.0.0.3";
    private static  int port = 8888;

    public static void main(String args[]){
        try {
            InetAddress group= InetAddress.getByName(inet_addr);
            MulticastSocket socket = new MulticastSocket(port);
            socket.setBroadcast(true);
            BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please Enter your Name:");
            name=buff.readLine();

            socket.setTimeToLive(1);
            socket.joinGroup(group);
            Thread t =new Thread(new ReadThread(socket,group,port));
            t.start();

            System.out.println("Start typing your message..........\n");
            while(true){
                String message;
                message = buff.readLine();
                if(message.equalsIgnoreCase(MultiClient.TERMINATE)){
                    finished = true;
                    socket.leaveGroup(group);
                    socket.close();
                    break;
                }
                message = name +": "+message;
                byte[] buffer = message.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length,group,port);
                socket.send(datagramPacket);

            }


        } catch (SocketException e) {
            System.out.println("Error in creating Socket");
        }
        catch (IOException e) {
            System.out.println("Error in IO operations");
        }
    }
}
class ReadThread implements Runnable{
    private MulticastSocket socket;
    private InetAddress group;
    private int port;
    private static final int Max_Len = 1000;

    public ReadThread(MulticastSocket socket, InetAddress group, int port) {
        this.socket = socket;
        this.group = group;
        this.port = port;
    }


    @Override
    public void run() {
        while(!MultiClient.finished){
            byte[] buffer = new byte[ReadThread.Max_Len];
            DatagramPacket datagram = new DatagramPacket(buffer,buffer.length,group,port);
            String message;

            try {
                socket.receive(datagram);
                message = new String(buffer,0,datagram.getLength(),"UTF-8");
                if(!message.startsWith(MultiClient.name))
                    System.out.println( message);

            } catch (IOException e) {
                System.out.println("Socket Closed");
            }
        }

    }
}

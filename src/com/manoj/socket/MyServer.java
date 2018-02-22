package com.manoj.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String args[]){
        try {
            ServerSocket ss = new   ServerSocket(6666);
            Socket s = ss.accept();
            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String str="",str2="";
            while(!str.equalsIgnoreCase("stop")) {
                str = (String) inputStream.readUTF();
                System.out.println("Client Says :" + str);
                str2=reader.readLine();
                outputStream.writeUTF(str2);
                outputStream.flush();
            }
            inputStream.close();
            outputStream.close();
            s.close();
            ss.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

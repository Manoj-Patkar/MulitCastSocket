package com.manoj.socket;

import java.io.*;
import java.net.Socket;

public class MyClient {
    public static void main(String args[]){
        try {
            Socket s = new Socket("bg4ws1068",6666);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            DataInputStream din = new DataInputStream(s.getInputStream());
            BufferedReader buff =new BufferedReader(new InputStreamReader(System.in));

            String str="",str2="";
            while(!str.equalsIgnoreCase("stop")){
                str = buff.readLine();
                dout.writeUTF(str);
                dout.flush();
                str2=din.readUTF();
                System.out.println("Server says: "+str2);


            }
            dout.close();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

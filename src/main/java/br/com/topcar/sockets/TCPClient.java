package br.com.topcar.sockets;

import br.com.topcar.io.replyResponseSerializable;
import br.com.topcar.io.requestResponseSerializable;

import java.net.*;
import java.io.*;

public class TCPClient {
    public static void main(String[] args) {
        if (args != null && args.length > 0 && "obj".equalsIgnoreCase(args[0])) {
            ObjetoSerializableStreams(args);
        } else {
            TextoStreams(args);
        }
    }

    public static void TextoStreams(String args[]) {
        // arguments supply message and hostname
        Socket s = null;
        try {
            int serverPort = 7897;
            s = new Socket("localhost", serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            String envio = "sistemas_distribuidos";
            System.out.println("Sent: "+envio);
            out.writeUTF(envio); // UTF is a string encoding see Sn. 4.4
            String data = in.readUTF(); // read a line of data from the stream
            System.out.println("Received: " + data);
        }
        catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        }  
        finally {
            if (s != null)
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
        }
    }

    public static void ObjetoSerializableStreams(String args[]) {
        Socket s = null;
        try {
            int serverPort = 7900;
            s = new Socket("localhost", serverPort);

            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());

            requestResponseSerializable request = new requestResponseSerializable(
                    "CONSULTAR",
                    "Farol Corolla",
                    "cliente-serializable-1");

            out.writeObject(request);
            out.flush();

            replyResponseSerializable reply = (replyResponseSerializable) in.readObject();
            System.out.println("Resposta serializada recebida: " + reply);
        }
        catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class not found:" + e.getMessage());
        }
        
        finally {
            if (s != null)
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
        }
        
    }
}
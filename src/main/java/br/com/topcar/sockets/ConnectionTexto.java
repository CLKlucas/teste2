package br.com.topcar.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ConnectionTexto extends Thread {
    private final DataInputStream in;
    private final DataOutputStream out;
    private final Socket clientSocket;

    public ConnectionTexto(Socket aClientSocket) throws IOException {
        clientSocket = aClientSocket;
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());
        this.start();
    }

    public void run() {
        try {
            String data = in.readUTF();
            System.out.println(data);
            out.writeUTF(data.toUpperCase());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                /* close failed */
            }
        }
    }
}

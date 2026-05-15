package test.java;

import br.com.topcar.io.PecaInputStream;
import br.com.topcar.io.PecaOutputStream;
import br.com.topcar.model.Peca;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class test_TCPServer {
    public static void main(String args[]) {
        try {
            System.out.println("Servidor iniciado");
            int serverPort = 7897; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);

            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println(clientSocket.getInetAddress());
                System.out.println("conexão estabelecida");
                test_Connection c = new test_Connection(clientSocket);
//                c.run();
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }
}

class test_Connection extends Thread {
    Socket clientSocket;

    public test_Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            this.start();
        } catch (Exception e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {
            // Lê as peças enviadas pelo cliente
            PecaInputStream pis = new PecaInputStream(clientSocket.getInputStream());
            Peca[] recebidas = pis.readSystem();

            for (Peca p : recebidas) {
                System.out.println("Recebida: " + p);
            }

            // Responde ao cliente com as mesmas peças
            PecaOutputStream pos = new PecaOutputStream(clientSocket.getOutputStream(), recebidas, recebidas.length);
            pos.writeSystem();

            pis.close();
            pos.close();

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            try { clientSocket.close(); } catch (IOException e) {}
        }
    }
}

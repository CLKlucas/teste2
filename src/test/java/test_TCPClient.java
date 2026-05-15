package test.java;

import br.com.topcar.io.PecaInputStream;
import br.com.topcar.io.PecaOutputStream;
import br.com.topcar.model.Peca;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class test_TCPClient {
    public static void main(String args[]) {
        Socket s = null;
        try {
            int serverPort = 7897;
            s = new Socket("localhost", serverPort);

            Peca[] pecas = { CargaDeDadosStorage.amortecedor1 };

            // Envia as peças pelo socket usando PecaOutputStream
            PecaOutputStream pos = new PecaOutputStream(s.getOutputStream(), pecas, pecas.length);
            pos.writeSystem();

            // Lê as peças de volta do servidor usando PecaInputStream
            PecaInputStream pis = new PecaInputStream(s.getInputStream());
            Peca[] recebidas = pis.readTCP();

            for (Peca p : recebidas) {
                System.out.println("Recebida: " + p);
            }

        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            if (s != null)
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
        }
    }
}
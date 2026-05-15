package br.com.topcar.sockets;

import br.com.topcar.io.replyResponseSerializable;
import br.com.topcar.io.requestResponseSerializable;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;

public class ConnectionObjeto extends Thread {
    private final Socket clientSocket;

    public ConnectionObjeto(Socket aClientSocket) {
        clientSocket = aClientSocket;
        this.start();
    }

    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            Object objRecebido = in.readObject();
            replyResponseSerializable resposta;

            if (objRecebido instanceof requestResponseSerializable) {
                requestResponseSerializable request = (requestResponseSerializable) objRecebido;
                String mensagem = "Operacao " + request.getOperation() + " processada para " + request.getClientId();
                resposta = new replyResponseSerializable(true, mensagem, LocalDate.now());
            } else {
                resposta = new replyResponseSerializable(false, "Objeto invalido recebido", LocalDate.now());
            }

            out.writeObject(resposta);
            out.flush();
        } catch (EOFException e) {
            System.out.println("EOF obj:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO obj:" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Classe nao encontrada:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                /* close failed */
            }
        }
    }
}

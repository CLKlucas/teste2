package br.com.topcar.rmi.client;

import br.com.topcar.rmi.protocol.RetornoCliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RetornoClienteConsole extends UnicastRemoteObject implements RetornoCliente {
    public RetornoClienteConsole() throws RemoteException {
    }

    @Override
    public void receiveReplyNotice(String requestId, byte[] reply) {
        System.out.println("Callback RMI recebido para " + requestId + " (" + reply.length + " bytes)");
    }
}

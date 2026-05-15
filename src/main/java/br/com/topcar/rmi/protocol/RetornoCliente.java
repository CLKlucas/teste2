package br.com.topcar.rmi.protocol;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RetornoCliente extends Remote {
    void receiveReplyNotice(String requestId, byte[] reply) throws RemoteException;
}

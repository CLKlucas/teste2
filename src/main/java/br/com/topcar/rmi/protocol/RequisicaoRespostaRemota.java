package br.com.topcar.rmi.protocol;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RequisicaoRespostaRemota extends Remote {
    byte[] getRequest(byte[] request, RetornoCliente callback) throws RemoteException;
}

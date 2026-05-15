package br.com.topcar.rmi.server;

import br.com.topcar.rmi.protocol.ReferenciaObjetoRemoto;

import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

public class ServidorRmiTopcar {
    public static void main(String[] args) throws Exception {
        int port = ReferenciaObjetoRemoto.DEFAULT_REGISTRY_PORT;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        String bindingName = ReferenciaObjetoRemoto.DEFAULT_BINDING_NAME;
        if (args.length > 1) {
            bindingName = args[1];
        }

        Registry registry = registry(port);
        TratadorRequisicaoTopcar handler = new TratadorRequisicaoTopcar(new ServicoCatalogoTopcar());

        try {
            registry.bind(bindingName, handler);
        } catch (AlreadyBoundException e) {
            registry.rebind(bindingName, handler);
        }

        System.out.println("Servidor RMI Topcar pronto");
        System.out.println("Registry: localhost:" + port);
        System.out.println("Objeto remoto: " + bindingName);
        System.out.println("Referencia do servico: " + ReferenciaObjetoRemoto.DEFAULT_OBJECT_NAME);
        Thread.currentThread().join();
    }

    private static Registry registry(int port) throws Exception {
        try {
            return LocateRegistry.createRegistry(port);
        } catch (ExportException e) {
            return LocateRegistry.getRegistry(port);
        }
    }
}

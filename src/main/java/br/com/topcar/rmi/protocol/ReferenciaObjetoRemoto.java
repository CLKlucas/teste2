package br.com.topcar.rmi.protocol;

import java.io.Serializable;

public class ReferenciaObjetoRemoto implements Serializable {
    public static final String DEFAULT_BINDING_NAME = "TopcarRequestReply";
    public static final String DEFAULT_OBJECT_NAME = "topcar.catalogo";
    public static final int DEFAULT_REGISTRY_PORT = 1099;

    private static final long serialVersionUID = 1L;

    private final String objectName;
    private final String registryHost;
    private final int registryPort;
    private final String bindingName;

    public ReferenciaObjetoRemoto(String objectName, String registryHost, int registryPort, String bindingName) {
        this.objectName = objectName;
        this.registryHost = registryHost;
        this.registryPort = registryPort;
        this.bindingName = bindingName;
    }

    public static ReferenciaObjetoRemoto localhost() {
        return new ReferenciaObjetoRemoto(
                DEFAULT_OBJECT_NAME,
                "localhost",
                DEFAULT_REGISTRY_PORT,
                DEFAULT_BINDING_NAME);
    }

    public String getObjectName() {
        return objectName;
    }

    public String getRegistryHost() {
        return registryHost;
    }

    public int getRegistryPort() {
        return registryPort;
    }

    public String getBindingName() {
        return bindingName;
    }

    @Override
    public String toString() {
        return "ReferenciaObjetoRemoto{" +
                "objectName='" + objectName + '\'' +
                ", registryHost='" + registryHost + '\'' +
                ", registryPort=" + registryPort +
                ", bindingName='" + bindingName + '\'' +
                '}';
    }
}

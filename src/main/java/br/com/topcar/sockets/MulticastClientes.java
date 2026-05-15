package br.com.topcar.sockets;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;

public class MulticastClientes {
    public static void main(String[] args) {
        String groupIp = "230.0.0.10";
        int port = 7899;

        try (MulticastSocket socket = new MulticastSocket(port)) {
            socket.setReuseAddress(true);
            NetworkInterface interfaceNET = NetworkInterface.getByInetAddress(InetAddress.getByName("192.168.0.8"));
            InetSocketAddress MulticastAddress = new InetSocketAddress(groupIp, port);
            socket.joinGroup(MulticastAddress, interfaceNET);

            System.out.println("Cliente inscrito no multicast " + groupIp + ":" + port);
            System.out.println("Aguardando avisos da loja...");

            while (true) {
                byte[] buffer = new byte[4096];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String mensagem = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                System.out.println("Recebido de " + packet.getAddress() + ":" + packet.getPort() + " -> " + mensagem);
            }
        } catch (Exception e) {
            System.out.println("Erro no assinante multicast: " + e.getMessage());
        }
    }
}

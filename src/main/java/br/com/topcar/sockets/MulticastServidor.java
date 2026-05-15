package br.com.topcar.sockets;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class MulticastServidor {
    public static void main(String[] args) {
        String groupIp = "230.0.0.10";
        int port = 7899;

        List<String> mensagensPadrao = Arrays.asList(
                "Oferta Topcar: 10% OFF em amortecedores hoje.",
                "Aviso Topcar: novo lote de baterias chegou no estoque.",
                "Promocao Topcar: frete gratis em pedidos acima de R$ 500.");

        try (MulticastSocket socket = new MulticastSocket()) {
            InetAddress group = InetAddress.getByName(groupIp);
            int indice = 0;

            System.out.println("Publicador multicast iniciado em " + groupIp + ":" + port);
            System.out.println("Pressione Ctrl+C para encerrar.");

            while (true) {
                String mensagem;
                if (args.length > 0) {
                    mensagem = String.join(" ", args);
                } else {
                    mensagem = mensagensPadrao.get(indice % mensagensPadrao.size());
                    indice++;
                }

                String payload = "[TOPCAR " + LocalDateTime.now() + "] " + mensagem;
                byte[] data = payload.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(data, data.length, group, port);
                socket.send(packet);

                System.out.println("Mensagem enviada: " + payload);
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            System.out.println("Erro no publicador multicast: " + e.getMessage());
        }
    }
}

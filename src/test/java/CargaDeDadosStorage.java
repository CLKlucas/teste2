package test.java;

import br.com.topcar.model.ItemPedido;
import br.com.topcar.model.Pedido;
import br.com.topcar.model.storage.*;
import br.com.topcar.model.user.Cliente;
import br.com.topcar.model.utils.Car;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CargaDeDadosStorage {

        // =========================================================
        // CARROS
        // =========================================================
        public static final Car carFox = new Car("CrossFox", LocalDate.now().getYear(), "Sport");
        public static final Car carGol = new Car("Gol", LocalDate.now().getYear() - 3, "Hatch");
        public static final Car carCorolla = new Car("Corolla", LocalDate.now().getYear() - 1, "Sedan");
        public static final Car carHilux = new Car("Hilux", LocalDate.now().getYear() - 5, "Pickup");
        public static final Car carCivic = new Car("Civic", LocalDate.now().getYear() - 2, "Sedan");
        public static final Car carMobi = new Car("Mobi", LocalDate.now().getYear(), "Hatch");
        public static final Car carRanger = new Car("Ranger", LocalDate.now().getYear() - 4, "Pickup");
        public static final Car carSandero = new Car("Sandero", LocalDate.now().getYear() - 6, "Hatch");

        // =========================================================
        // AMORTECEDORES
        // =========================================================
        public static final Amortecedor amortecedor1 = new Amortecedor(
                        1, "Amortecedor Dianteiro Hidráulico CrossFox",
                        BigDecimal.valueOf(320.00), LocalDate.now(), carFox,
                        "hidráulico", "dianteiro");

        public static final Amortecedor amortecedor2 = new Amortecedor(
                        2, "Amortecedor Traseiro a Gás Gol",
                        BigDecimal.valueOf(275.50), LocalDate.now(), carGol,
                        "a gás", "traseiro");

        public static final Amortecedor amortecedor3 = new Amortecedor(
                        3, "Amortecedor Dianteiro Pressurizado Corolla",
                        BigDecimal.valueOf(480.00), LocalDate.now(), carCorolla,
                        "pressurizado", "dianteiro");

        public static final Amortecedor amortecedor4 = new Amortecedor(
                        4, "Amortecedor Traseiro a Gás Hilux",
                        BigDecimal.valueOf(560.00), LocalDate.now(), carHilux,
                        "a gás", "traseiro");

        public static final Amortecedor amortecedor5 = new Amortecedor(
                4, "Amortecedor Traseiro a Gás Corolla",
                BigDecimal.valueOf(560.00), LocalDate.now(), carHilux,
                "a gás", "traseiro");

        // =========================================================
        // BATERIAS
        // =========================================================
        public static final Bateria bateria1 = new Bateria(
                        6, "Bateria Moura 60Ah CrossFox",
                        BigDecimal.valueOf(450.00), LocalDate.now(), carFox,
                        420, "chumbo-ácido");

        public static final Bateria bateria2 = new Bateria(
                        7, "Bateria Heliar 45Ah Gol",
                        BigDecimal.valueOf(380.00), LocalDate.now(), carGol,
                        380, "chumbo-ácido");

        public static final Bateria bateria3 = new Bateria(
                        8, "Bateria Bosch 70Ah Hilux",
                        BigDecimal.valueOf(620.00), LocalDate.now(), carHilux,
                        600, "AGM");

        public static final Bateria bateria4 = new Bateria(
                        9, "Bateria Varta 55Ah Corolla",
                        BigDecimal.valueOf(510.00), LocalDate.now(), carCorolla,
                        500, "EFB");

        public static final Bateria bateria5 = new Bateria(
                        10, "Bateria Heliar 40Ah Mobi",
                        BigDecimal.valueOf(320.00), LocalDate.now(), carMobi,
                        330, "chumbo-ácido");

        // =========================================================
        // FARÓIS
        // =========================================================
        public static final Farol farol1 = new Farol(
                        11, "Farol Alto LED Corolla",
                        BigDecimal.valueOf(780.00), LocalDate.now(), carCorolla,
                        "alto", true);

        public static final Farol farol2 = new Farol(
                        12, "Farol Baixo Halógeno CrossFox",
                        BigDecimal.valueOf(310.00), LocalDate.now(), carFox,
                        "baixo", false);

        public static final Farol farol3 = new Farol(
                        13, "Farol de Milha LED Hilux",
                        BigDecimal.valueOf(540.00), LocalDate.now(), carHilux,
                        "milha", true);

        public static final Farol farol4 = new Farol(
                        14, "Farol Baixo LED Civic",
                        BigDecimal.valueOf(870.00), LocalDate.now(), carCivic,
                        "baixo", true);

        public static final Farol farol5 = new Farol(
                        15, "Farol Alto Halógeno Sandero",
                        BigDecimal.valueOf(260.00), LocalDate.now(), carSandero,
                        "alto", false);

        // =========================================================
        // MOTORES
        // =========================================================
        public static final Motor motor1 = new Motor(
                        16, "Motor 1.6 VHT CrossFox",
                        BigDecimal.valueOf(4500.00), LocalDate.now(), carFox,
                        117, 15.9f);

        public static final Motor motor2 = new Motor(
                        17, "Motor 1.0 Flex Gol",
                        BigDecimal.valueOf(3200.00), LocalDate.now(), carGol,
                        82, 10.2f);

        public static final Motor motor3 = new Motor(
                        18, "Motor 2.0 Dual VVT-i Corolla",
                        BigDecimal.valueOf(6800.00), LocalDate.now(), carCorolla,
                        171, 21.4f);

        public static final Motor motor4 = new Motor(
                        19, "Motor 2.8 Turbo Diesel Hilux",
                        BigDecimal.valueOf(9500.00), LocalDate.now(), carHilux,
                        204, 51.0f);

        public static final Motor motor5 = new Motor(
                        20, "Motor 2.0 VTEC Turbo Civic",
                        BigDecimal.valueOf(7200.00), LocalDate.now(), carCivic,
                        173, 22.4f);

        // =========================================================
        // PNEUS
        // =========================================================
        public static final Pneu pneu1 = new Pneu(
                        21, "Pneu Bridgestone 195/65R15 CrossFox",
                        BigDecimal.valueOf(420.00), LocalDate.now(), carFox,
                        "195/65R15", "A");

        public static final Pneu pneu2 = new Pneu(
                        22, "Pneu Pirelli 175/70R14 Gol",
                        BigDecimal.valueOf(350.00), LocalDate.now(), carGol,
                        "175/70R14", "B");

        public static final Pneu pneu3 = new Pneu(
                        23, "Pneu Michelin 205/55R16 Corolla",
                        BigDecimal.valueOf(590.00), LocalDate.now(), carCorolla,
                        "205/55R16", "A");

        public static final Pneu pneu4 = new Pneu(
                        24, "Pneu Goodyear 265/65R17 Hilux",
                        BigDecimal.valueOf(780.00), LocalDate.now(), carHilux,
                        "265/65R17", "C");

        public static final Pneu pneu5 = new Pneu(
                        25, "Pneu Continental 215/45R17 Civic",
                        BigDecimal.valueOf(640.00), LocalDate.now(), carCivic,
                        "215/45R17", "A");

        // =========================================================
        // ITENS DE PEDIDO
        // =========================================================
        public static final ItemPedido item1 = new ItemPedido(amortecedor1, 2, BigDecimal.valueOf(10.00));
        public static final ItemPedido item2 = new ItemPedido(bateria1, 1, BigDecimal.valueOf(20.00));
        public static final ItemPedido item3 = new ItemPedido(farol1, 2, BigDecimal.valueOf(15.00));
        public static final ItemPedido item4 = new ItemPedido(motor3, 1, BigDecimal.valueOf(5.00));
        public static final ItemPedido item5 = new ItemPedido(pneu3, 4, BigDecimal.valueOf(8.00));
        public static final ItemPedido item6 = new ItemPedido(amortecedor3, 2, BigDecimal.valueOf(12.00));
        public static final ItemPedido item7 = new ItemPedido(bateria3, 1, BigDecimal.valueOf(25.00));
        public static final ItemPedido item8 = new ItemPedido(farol3, 1, BigDecimal.valueOf(18.00));
        public static final ItemPedido item9 = new ItemPedido(pneu4, 4, BigDecimal.valueOf(6.00));
        public static final ItemPedido item10 = new ItemPedido(motor5, 1, BigDecimal.valueOf(5.00));
        public static final ItemPedido item11 = new ItemPedido(farol4, 2, BigDecimal.valueOf(10.00));
        public static final ItemPedido item12 = new ItemPedido(pneu1, 4, BigDecimal.valueOf(7.00));
        public static final ItemPedido item13 = new ItemPedido(bateria2, 1, BigDecimal.valueOf(15.00));
        public static final ItemPedido item14 = new ItemPedido(amortecedor5, 2, BigDecimal.valueOf(9.00));
        public static final ItemPedido item15 = new ItemPedido(motor2, 1, BigDecimal.valueOf(5.00));

        // =========================================================
        // PEDIDOS
        // =========================================================
        public static final Pedido pedido1;
        public static final Pedido pedido2;
        public static final Pedido pedido3;
        public static final Pedido pedido4;
        public static final Pedido pedido5;

        static {
                List<ItemPedido> itensPedido1 = new ArrayList<>();
                itensPedido1.add(item1);
                itensPedido1.add(item2);
                itensPedido1.add(item3);
                pedido1 = new Pedido(1, itensPedido1);

                List<ItemPedido> itensPedido2 = new ArrayList<>();
                itensPedido2.add(item4);
                itensPedido2.add(item5);
                itensPedido2.add(item6);
                pedido2 = new Pedido(2, itensPedido2);

                List<ItemPedido> itensPedido3 = new ArrayList<>();
                itensPedido3.add(item7);
                itensPedido3.add(item8);
                itensPedido3.add(item9);
                pedido3 = new Pedido(3, itensPedido3);

                List<ItemPedido> itensPedido4 = new ArrayList<>();
                itensPedido4.add(item10);
                itensPedido4.add(item11);
                itensPedido4.add(item12);
                pedido4 = new Pedido(4, itensPedido4);

                List<ItemPedido> itensPedido5 = new ArrayList<>();
                itensPedido5.add(item13);
                itensPedido5.add(item14);
                itensPedido5.add(item15);
                pedido5 = new Pedido(5, itensPedido5);
        }

        // =========================================================
        // CLIENTES
        // =========================================================
        public static final Cliente cliente1;
        public static final Cliente cliente2;
        public static final Cliente cliente3;
        public static final Cliente cliente4;
        public static final Cliente cliente5;

        static {
                List<Pedido> pedidosCliente1 = new ArrayList<>();
                pedidosCliente1.add(pedido1);
                cliente1 = new Cliente("11122233344", "Matias Souza", 28, pedidosCliente1);

                List<Pedido> pedidosCliente2 = new ArrayList<>();
                pedidosCliente2.add(pedido2);
                cliente2 = new Cliente("22233344455", "Fernanda Lima", 35, pedidosCliente2);

                List<Pedido> pedidosCliente3 = new ArrayList<>();
                pedidosCliente3.add(pedido3);
                cliente3 = new Cliente("33344455566", "Ricardo Alves", 42, pedidosCliente3);

                List<Pedido> pedidosCliente4 = new ArrayList<>();
                pedidosCliente4.add(pedido4);
                cliente4 = new Cliente("44455566677", "Ana Paula Costa", 31, pedidosCliente4);

                List<Pedido> pedidosCliente5 = new ArrayList<>();
                pedidosCliente5.add(pedido5);
                cliente5 = new Cliente("55566677788", "Bruno Mendes", 25, pedidosCliente5);
        }
}
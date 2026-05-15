package br.com.topcar.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private List<ItemPedido> itens = new ArrayList<>();
    // private BigDecimal valorPedido = BigDecimal.ZERO;

    void addItemPedido(ItemPedido item) {
        this.itens.add(item);
    }

    public Pedido() {
    }

    public Pedido(int id, List<ItemPedido> itens) {
        this.id = id;
        this.itens = itens;
        // this.valorPedido = itens.stream()
        // .map(ItemPedido::getValorTotal) // assumindo getter de valor como BigDecimal
        // .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", itens=" + itens +
                // ", valorPedido=" + valorPedido +
                '}';
    }
}

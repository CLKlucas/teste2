package br.com.topcar.model;

import br.com.topcar.service.interfaces.strategy.iPromoStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ItemPedido implements iPromoStrategy {
    private Peca peca;
    private int quantidade;
    private BigDecimal valorTotal;
    private BigDecimal desconto;

    public ItemPedido() {
    }

    public ItemPedido(Peca peca, int quantidade) {
        this.peca = peca;
        this.quantidade = quantidade;
        this.valorTotal = peca.getValor().multiply(BigDecimal.valueOf(quantidade));
        this.desconto = BigDecimal.ZERO;
    }

    public ItemPedido(Peca peca, int quantidade, BigDecimal desconto) {
        this.peca = peca;
        this.quantidade = quantidade;
        this.valorTotal = BigDecimal.valueOf((double)this.quantidade).multiply(aplicarDesconto(desconto));
        this.desconto = aplicarDesconto(desconto);
    }

    @Override
    public BigDecimal aplicarDesconto(BigDecimal percentual) {
        percentual = percentual.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal valorDesconto = this.peca.getValor().multiply(percentual);
//        System.out.println("\n percentual de desconto: " + percentual);
//        System.out.println("\n valor desconto: " + valorDesconto);
//        System.out.println("\n valor com desconto: " + this.peca.getValor().subtract(valorDesconto));
        return this.peca.getValor().subtract(valorDesconto);
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorPedido) {
        this.valorTotal = valorPedido;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "peca=" + peca +
                ", quantidade=" + quantidade +
                ", valorTotal=" + valorTotal +
                ", desconto=" + desconto +
                '}';
    }
}

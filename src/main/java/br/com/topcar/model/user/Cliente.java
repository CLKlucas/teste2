package br.com.topcar.model.user;

import br.com.topcar.model.Pedido;
import br.com.topcar.service.interfaces.buy.iCompra;

import java.util.ArrayList;
import java.util.List;

public class Cliente implements iCompra {
    private String cpf;
    private String nome;
    private int idade;
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String cpf, String nome, int idade, List<Pedido> pedidos) {
        this.cpf = cpf;
        this.nome = nome;
        this.idade = idade;
        this.pedidos = pedidos;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", pedidos=" + pedidos +
                '}';
    }

    @Override
    public void efetuarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
    }
}

package br.com.topcar.model.utils;

public class Car {
    private String nome;
    private int ano;
    private String modelo;

    public Car() {
    }

    public Car(String nome, int ano, String modelo) {
        this.nome = nome;
        this.ano = ano;
        this.modelo = modelo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return "Car{" +
                "nome='" + nome + '\'' +
                ", ano='" + ano + '\'' +
                ", modelo='" + modelo + '\'' +
                '}';
    }
}

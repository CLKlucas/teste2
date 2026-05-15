package br.com.topcar.model.storage;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.topcar.model.Peca;
import br.com.topcar.model.utils.Car;

public class Motor extends Peca {
    private int potenciaCv; // Potência em cavalos-vapor
    private float torqueKgf; // Torque em kgf.m

    public Motor() {
    }

    public Motor(int potenciaCv, float torqueKgf) {
        this.potenciaCv = potenciaCv;
        this.torqueKgf = torqueKgf;
    }

    public Motor(int id, String nome, BigDecimal valor, LocalDate data_fabricacao, Car carro, int potenciaCv,
            float torqueKgf) {
        super(id, nome, valor, data_fabricacao, carro);
        this.potenciaCv = potenciaCv;
        this.torqueKgf = torqueKgf;
    }

    // Getters e Setters
    public int getPotenciaCv() {
        return potenciaCv;
    }

    public void setPotenciaCv(int potenciaCv) {
        this.potenciaCv = potenciaCv;
    }

    public float getTorqueKgf() {
        return torqueKgf;
    }

    public void setTorqueKgf(float torqueKgf) {
        this.torqueKgf = torqueKgf;
    }

    @Override
    public String toString() {
        return "Motor{" +
                "potenciaCv=" + potenciaCv +
                ", torqueKgf=" + torqueKgf +
                '}';
    }
}

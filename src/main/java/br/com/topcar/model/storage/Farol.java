package br.com.topcar.model.storage;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.topcar.model.Peca;
import br.com.topcar.model.utils.Car;

public class Farol extends Peca {
    private String tipo; // ex: alto, baixo, milha
    private boolean led; // Indicador se é LED

    // Getters e Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isLed() {
        return led;
    }

    public void setLed(boolean led) {
        this.led = led;
    }

    public Farol(int id, String nome, BigDecimal valor, LocalDate data_fabricacao, Car carro, String tipo,
            boolean led) {
        super(id, nome, valor, data_fabricacao, carro);
        this.led = led;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Farol{" +
                "tipo='" + tipo + '\'' +
                ", led=" + led +
                '}';
    }
}

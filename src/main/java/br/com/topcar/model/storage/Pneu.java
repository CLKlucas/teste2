package br.com.topcar.model.storage;

import br.com.topcar.model.Peca;
import java.math.BigDecimal;
import java.time.LocalDate;
import br.com.topcar.model.utils.Car;

public class Pneu extends Peca {
    private String dimensoes; // ex: 195/65R15
    private String indiceTraction; // ex: AA, A (aderência)

    public Pneu() {
    }

    public Pneu(String dimensoes, String indiceTraction) {
        this.dimensoes = dimensoes;
        this.indiceTraction = indiceTraction;
    }

    public Pneu(int id, String nome, BigDecimal valor,
            LocalDate data_fabricacao,
            Car carro, String dimensoes, String indiceTraction) {
        super(id, nome, valor, data_fabricacao, carro);
        this.dimensoes = dimensoes;
        this.indiceTraction = indiceTraction;
    }

    // Getters e Setters
    public String getDimensoes() {
        return dimensoes;
    }

    public void setDimensoes(String dimensoes) {
        this.dimensoes = dimensoes;
    }

    public String getIndiceTraction() {
        return indiceTraction;
    }

    public void setIndiceTraction(String indiceTraction) {
        this.indiceTraction = indiceTraction;
    }

    @Override
    public String toString() {
        return "Pneu{" +
                "dimensoes='" + dimensoes + '\'' +
                ", indiceTraction='" + indiceTraction + '\'' +
                '}';
    }
}

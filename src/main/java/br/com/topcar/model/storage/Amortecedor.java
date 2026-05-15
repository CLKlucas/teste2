package br.com.topcar.model.storage;

import br.com.topcar.model.utils.Car;
import br.com.topcar.model.Peca;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Amortecedor extends Peca {
    private String tipo; // ex: hidráulico, a gás, pressurizado
    private String posicao; // ex: dianteiro, traseiro

    public Amortecedor() {
    }

    public Amortecedor(String tipo, String posicao) {
        this.tipo = tipo;
        this.posicao = posicao;
    }

    public Amortecedor(int id, String nome, BigDecimal valor, LocalDate data_fabricacao, Car carro, String tipo,
            String posicao) {

        super(id, nome, valor, data_fabricacao, carro);
        this.tipo = tipo;
        this.posicao = posicao;
    }

    // Getters e Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    @Override
    public String toString() {
        return "Amortecedor{" +
                "tipo='" + tipo + '\'' +
                ", posicao='" + posicao + '\'' +
                '}';
    }
}

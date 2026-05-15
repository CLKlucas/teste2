package br.com.topcar.model.storage;

import br.com.topcar.model.Peca;
import br.com.topcar.model.utils.Car;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Bateria extends Peca {
    private int cca; // Corrente de arranque a frio
    private String tipoQuimica; // ex: chumbo-ácido, íon de lítio

    public Bateria() {
    }

    public Bateria(int cca, String tipoQuimica) {
        this.cca = cca;
        this.tipoQuimica = tipoQuimica;
    }

    public Bateria(int id, String nome, BigDecimal valor, LocalDate data_fabricacao,
            Car carro, int cca, String tipoQuimica) {
        super(id, nome, valor, data_fabricacao, carro);
        this.cca = cca;
        this.tipoQuimica = tipoQuimica;
    }

    // Getters e Setters
    public int getCca() {
        return cca;
    }

    public void setCca(int cca) {
        this.cca = cca;
    }

    public String getTipoQuimica() {
        return tipoQuimica;
    }

    public void setTipoQuimica(String tipoQuimica) {
        this.tipoQuimica = tipoQuimica;
    }

    @Override
    public String toString() {
        return "Bateria{" +
                "cca=" + cca +
                ", tipoQuimica='" + tipoQuimica + '\'' +
                '}';
    }
}

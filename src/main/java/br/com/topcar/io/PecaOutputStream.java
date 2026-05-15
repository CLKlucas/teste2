package br.com.topcar.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import br.com.topcar.model.Peca;
import br.com.topcar.model.utils.Car;

public class PecaOutputStream extends OutputStream {

    private OutputStream op;
    private Peca[] pecas;
    private int numPecas;
    private final ByteArrayOutputStream buffer;

    public PecaOutputStream() {
        this.buffer = new ByteArrayOutputStream();
    }

    public PecaOutputStream(OutputStream op, Peca[] pecas, int numObjetos){
        this.op = op;
        this.pecas = pecas;
        this.numPecas = numObjetos;
        this.buffer = new ByteArrayOutputStream();
    }

    public PecaOutputStream(OutputStream op, Peca[] pecas) {
        this.op = op;
        this.pecas = pecas;
        this.buffer = new ByteArrayOutputStream();
    }

    public void writeSystem() {

        PrintStream opLocal = new PrintStream(op);

        // envia quantidade de peças;


        opLocal.println(this.numPecas);

        // envia os dados de um conjunto (array) de Pessoas
        for (Peca peca : this.pecas) {
            if (peca != null) {

                int id = peca.getId();

                int tamanhoNomePeca = peca.getNome().getBytes().length;
                String nome = peca.getNome();

                BigDecimal valor = peca.getValor();
                String textovalor = valor.toString();
                int bytesvalor = textovalor.getBytes().length;

                LocalDate data_fabricacao = peca.getData_fabricacao();
                String texto_data = data_fabricacao.toString();
                int bytesdata = texto_data.getBytes().length;

                Car carro = peca.getCarro();

                opLocal.println(id);
                opLocal.println(tamanhoNomePeca);
                opLocal.println(nome);
                opLocal.println(bytesvalor);
                opLocal.println(valor);
                opLocal.println(bytesdata);
                opLocal.println(data_fabricacao);
                opLocal.println(carro.getNome());
                opLocal.println(carro.getAno());
                opLocal.println(carro.getModelo());

            }
        }
    }

    @Override
    public void write(int b) throws IOException {
        // TODO Auto-generated method stub
        buffer.write(b);
        op.write(b);
        // super.write(b);
    }

    @Override
    public String toString() {
        return "Ola, mundo! Estamos sobrescrevendo o método toString()!\n"
                + " PecasOutputStream [ \n"
                + " getClass()=" + getClass() + ",\n"
                + " hashCode()=" + hashCode() + ",\n"
                + " toString()=" + super.toString() + "]";
    }

}

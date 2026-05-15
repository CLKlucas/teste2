package br.com.topcar.io;

import java.io.BufferedReader;
import java.io.IOException;
import br.com.topcar.model.utils.Car;
import br.com.topcar.model.Peca;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class PecaInputStream extends InputStream {

    private Peca[] pecas;
    private InputStream ins;
    private BufferedReader bufferedReader;
    public PecaInputStream(InputStream ins) {
        this.ins = ins;
        this.bufferedReader = new BufferedReader(new InputStreamReader(ins));
    }

    public Peca[] readSystem() throws IOException {
        String linhaQtd = bufferedReader.readLine();
        System.out.println("[DEBUG] qtd_pecas raw: '" + linhaQtd + "'");
        int qtd_pecas = Integer.parseInt(linhaQtd);

        Peca[] pecas = new Peca[qtd_pecas];

        for (int i = 0; i < qtd_pecas; i++) {
            System.out.println("[DEBUG] ---- Peça " + i + " ----");

            String linhaId = bufferedReader.readLine();
            System.out.println("[DEBUG] id raw: '" + linhaId + "'");
            int id = Integer.parseInt(linhaId);

            String linhaTamanhoNome = bufferedReader.readLine();
            System.out.println("[DEBUG] tamanhoNome raw: '" + linhaTamanhoNome + "'");
            int tamanhoNomePeca = Integer.parseInt(linhaTamanhoNome);

            String nome = bufferedReader.readLine();
            System.out.println("[DEBUG] nome: '" + nome + "'");

            String linhaTamanhoValor = bufferedReader.readLine();
            System.out.println("[DEBUG] tamanhoValor raw: '" + linhaTamanhoValor + "'");
            int tamanhoValor = Integer.parseInt(linhaTamanhoValor);

            String linhaValor = bufferedReader.readLine();
            System.out.println("[DEBUG] valor raw: '" + linhaValor + "'");
            BigDecimal valor = new BigDecimal(linhaValor);

            String linhaTamanhoData = bufferedReader.readLine();
            System.out.println("[DEBUG] tamanhoData raw: '" + linhaTamanhoData + "'");
            int tamanhoData = Integer.parseInt(linhaTamanhoData);

            String linhaData = bufferedReader.readLine();
            System.out.println("[DEBUG] data_fabricacao raw: '" + linhaData + "'");
            LocalDate data_fabricacao = LocalDate.parse(linhaData);

            String nomecar = bufferedReader.readLine();
            System.out.println("[DEBUG] nomecar: '" + nomecar + "'");

            String linhaAno = bufferedReader.readLine();
            System.out.println("[DEBUG] anocar raw: '" + linhaAno + "'");
            int anocar = Integer.parseInt(linhaAno);

            String modelocar = bufferedReader.readLine();
            System.out.println("[DEBUG] modelocar: '" + modelocar + "'");

            Car carro = new Car(nomecar, anocar, modelocar);
            pecas[i] = new Peca(id, nome, valor, data_fabricacao, carro);
            System.out.println("[DEBUG] Peça construída: " + pecas[i]);
        }

        return pecas;
    }

    
    
    
    
    public Peca[] readFromTerminal() {
    Scanner sc = new Scanner(this.ins);

    System.out.print("Quantidade de peças: ");
    int qtd = Integer.parseInt(sc.nextLine());

    Peca[] pecas = new Peca[qtd];

    for (int i = 0; i < qtd; i++) {
        System.out.println("\nPeça " + (i + 1));

        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Valor: ");
        BigDecimal valor = new BigDecimal(sc.nextLine());

        System.out.print("Data de fabricação: ");
        String data = sc.nextLine();
        LocalDate data_formatada = LocalDate.parse(data);

        System.out.print("Nome do carro: ");
        String nomeCarro = sc.nextLine();

        System.out.print("Ano do carro: ");
        int anoCarro = Integer.parseInt(sc.nextLine());

        System.out.print("Modelo do carro: ");
        String modeloCarro = sc.nextLine();

        Car carro = new Car(nomeCarro, anoCarro, modeloCarro);  
        pecas[i] = new Peca(id, nome, valor, data_formatada, carro);
    }

    sc.close();
    return pecas;
}



    public Peca[] ReadFile() throws IOException{ return readSystem(); }

    public Peca[] readTCP() throws IOException{ return readSystem(); }

    @Override
    public int read() throws IOException {
        return ins.read(); // delegando para o stream original
    }
}

package test.java;

import br.com.topcar.model.Peca;
import br.com.topcar.io.*;
import test.java.CargaDeDadosStorage;

import java.io.*;

public class testeSOUT {
    public static void main(String[] args) {
        Peca[] pecas = { CargaDeDadosStorage.amortecedor1 };

        try {
            // Teste 1: saída padrão
            System.out.println("=== Saída padrão ===");
            PecaOutputStream posSOUT = new PecaOutputStream(System.out, pecas, pecas.length);
            posSOUT.writeSystem();
            posSOUT.close();

            // Teste 2: arquivo
            System.out.println("=== Escrevendo arquivo ===");
            FileOutputStream fos = new FileOutputStream("pecas.txt");
            PecaOutputStream posFILE = new PecaOutputStream(fos, pecas, pecas.length);
            posFILE.writeSystem();
            posFILE.close();
            fos.close();

            // Teste 3: lendo arquivo
            System.out.println("=== Lendo arquivo ===");
            FileInputStream fis = new FileInputStream("pecas.txt");
            br.com.topcar.io.PecaInputStream pis = new br.com.topcar.io.PecaInputStream(fis);
            Peca[] lidas = pis.readSystem();
            pis.close();
            fis.close();

            for (Peca p : lidas) {
                System.out.println("Lida: " + p);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

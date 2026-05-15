package test.java;

import br.com.topcar.io.PecaInputStream;
import br.com.topcar.model.Peca;

public class testeSIN {
    public static void main(String[] args) {
        try {
            System.out.println("Digite os dados exatamente no formato esperado pelo stream:");
            System.out.println("qtd_pecas");
            System.out.println("id");
            System.out.println("nome");
            System.out.println("valor");
            System.out.println("data");
            System.out.println("nomeCarro");
            System.out.println("anoCarro");
            System.out.println("modeloCarro");

            PecaInputStream entrada = new PecaInputStream(System.in);
            Peca[] pecas = entrada.readFromTerminal();

            System.out.println("\nPeças lidas com sucesso:");
            for (Peca p : pecas) {
                System.out.println(p);
            }

        } catch (Exception e) {
            System.out.println("Erro ao ler do System.in: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

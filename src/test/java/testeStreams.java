package test.java;

import br.com.topcar.model.Peca;
import br.com.topcar.io.*;
import test.java.CargaDeDadosStorage;

import java.io.*;

public class testeStreams {
    public static void main(String[] args) {
        Peca[] pecas = { CargaDeDadosStorage.amortecedor1 };

        try 
        {
            FileOutputStream fos = new FileOutputStream("pecas.txt");
            // FileOutputStream fos = new FileOutputStream("pecas.ser");
            PecaOutputStream pos = new PecaOutputStream(fos, pecas, pecas.length);
            pos.writeSystem();
            pos.close();
            fos.close();

            FileInputStream fis = new FileInputStream("pecas.txt");
            br.com.topcar.io.PecaInputStream pis = new br.com.topcar.io.PecaInputStream(fis);
            // Peca deserializedPerson = (Peca) PecaInputStream.readObject();
            Peca[] lidas = pis.readSystem();
            pis.close();
            fis.close();

            // System.out.println("Nome: " + deserializedPerson.getName());
            // System.out.println(": " + deserializedPerson.getAge());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

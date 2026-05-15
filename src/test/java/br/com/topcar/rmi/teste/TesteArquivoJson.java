package test.br.com.topcar.rmi.teste;

import br.com.topcar.rmi.codec.ArquivoJson;

import java.math.BigDecimal;
import java.util.List;

public class TesteArquivoJson {
    public static void main(String[] args) {
        testarCamposSimples();
        testarListaDeItens();
        System.out.println("TesteArquivoJson finalizado com sucesso.");
    }

    private static void testarCamposSimples() {
        String json = ArquivoJson.objeto(
                ArquivoJson.campoTexto("cpf", "123.456.789-00"),
                ArquivoJson.campoNumero("idade", 22),
                ArquivoJson.campoTexto("valor", "150.75"));

        verificar("123.456.789-00".equals(ArquivoJson.valorTexto(json, "cpf")), "CPF lido errado");
        verificar(ArquivoJson.valorInteiro(json, "idade") == 22, "Idade lida errada");
        verificar(
                new BigDecimal("150.75").equals(ArquivoJson.valorDecimal(json, "valor", BigDecimal.ZERO)),
                "Valor decimal lido errado");
    }

    private static void testarListaDeItens() {
        String item1 = ArquivoJson.objeto(
                ArquivoJson.campoNumero("pecaId", 1),
                ArquivoJson.campoNumero("quantidade", 4),
                ArquivoJson.campoNumero("descontoPercentual", 5));
        String item2 = ArquivoJson.objeto(
                ArquivoJson.campoNumero("pecaId", 3),
                ArquivoJson.campoNumero("quantidade", 1));

        String json = ArquivoJson.objeto(
                ArquivoJson.campoTexto("cpf", "123.456.789-00"),
                ArquivoJson.campoJson("itens", ArquivoJson.lista(List.of(item1, item2))));

        String itensJson = ArquivoJson.valorBruto(json, "itens");
        List<String> itens = ArquivoJson.separarItens(itensJson);

        verificar(itens.size() == 2, "Quantidade de itens lida errada");
        verificar(ArquivoJson.valorInteiro(itens.get(0), "pecaId") == 1, "ID da primeira peca lido errado");
        verificar(ArquivoJson.valorInteiro(itens.get(0), "quantidade") == 4, "Quantidade da primeira peca lida errada");
        verificar(ArquivoJson.valorInteiro(itens.get(1), "pecaId") == 3, "ID da segunda peca lido errado");
    }

    private static void verificar(boolean condicao, String mensagemErro) {
        if (!condicao) {
            throw new IllegalStateException(mensagemErro);
        }
    }
}

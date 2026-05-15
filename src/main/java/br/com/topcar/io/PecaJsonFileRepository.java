package br.com.topcar.io;

import br.com.topcar.model.Peca;
import br.com.topcar.model.utils.Car;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PecaJsonFileRepository {
    public void salvar(Path arquivo, Peca[] pecas) throws IOException {
        Files.writeString(arquivo, toJson(pecas), StandardCharsets.UTF_8);
    }

    public Peca[] carregar(Path arquivo) throws IOException {
        if (!Files.exists(arquivo)) return new Peca[0];
        String json = Files.readString(arquivo, StandardCharsets.UTF_8).trim();
        if (json.isEmpty() || "[]".equals(json)) return new Peca[0];

        List<Peca> lista = new ArrayList<>();
        for (String obj : splitObjetos(json)) {
            int id = Integer.parseInt(valor(obj, "id"));
            String nome = valor(obj, "nome");
            BigDecimal valor = new BigDecimal(valor(obj, "valor"));
            LocalDate data = LocalDate.parse(valor(obj, "data"));
            String carroNome = valor(obj, "carro_nome");
            int carroAno = Integer.parseInt(valor(obj, "carro_ano"));
            String carroModelo = valor(obj, "carro_modelo");
            lista.add(new Peca(id, nome, valor, data, new Car(carroNome, carroAno, carroModelo)));
        }
        return lista.toArray(new Peca[0]);
    }

    private String toJson(Peca[] pecas) {
        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < pecas.length; i++) {
            Peca p = pecas[i];
            sb.append("  {\"id\":").append(p.getId())
              .append(",\"nome\":\"").append(p.getNome())
              .append("\",\"valor\":\"").append(p.getValor())
              .append("\",\"data\":\"").append(p.getData_fabricacao())
              .append("\",\"carro_nome\":\"").append(p.getCarro().getNome())
              .append("\",\"carro_ano\":").append(p.getCarro().getAno())
              .append(",\"carro_modelo\":\"").append(p.getCarro().getModelo())
              .append("\"}");
            if (i < pecas.length - 1) sb.append(",");
            sb.append("\n");
        }
        return sb.append("]").toString();
    }

    private List<String> splitObjetos(String json) {
        String corpo = json.substring(1, json.length() - 1);
        List<String> out = new ArrayList<>();
        int depth = 0, ini = -1;
        for (int i = 0; i < corpo.length(); i++) {
            char c = corpo.charAt(i);
            if (c == '{') {
                if (depth == 0) ini = i;
                depth++;
            } else if (c == '}') {
                depth--;
                if (depth == 0) out.add(corpo.substring(ini, i + 1));
            }
        }
        return out;
    }

    private String valor(String obj, String campo) {
        String chave = "\"" + campo + "\":";
        int i = obj.indexOf(chave);
        if (i < 0) throw new IllegalStateException("Campo nao encontrado: " + campo);
        i += chave.length();

        if (obj.charAt(i) == '"') {
            int j = obj.indexOf('"', i + 1);
            return obj.substring(i + 1, j);
        }

        int j = obj.indexOf(',', i);
        if (j < 0) j = obj.indexOf('}', i);
        return obj.substring(i, j).trim();
    }
}

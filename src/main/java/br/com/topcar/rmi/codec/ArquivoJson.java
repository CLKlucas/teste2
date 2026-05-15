package br.com.topcar.rmi.codec;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ArquivoJson {
    private ArquivoJson() {
    }

    public static byte[] paraBytes(String texto) {
        return texto.getBytes(StandardCharsets.UTF_8);
    }

    public static String deBytes(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String campoTexto(String nome, String valor) {
        return textoJson(nome) + ":" + textoJson(valor);
    }

    public static String campoNumero(String nome, Number valor) {
        return textoJson(nome) + ":" + valor;
    }

    public static String campoBooleano(String nome, boolean valor) {
        return textoJson(nome) + ":" + valor;
    }

    public static String campoJson(String nome, String json) {
        String valor = json;
        if (valor == null || valor.isBlank()) {
            valor = "{}";
        }
        return textoJson(nome) + ":" + valor;
    }

    public static String objeto(String... campos) {
        return "{" + String.join(",", campos) + "}";
    }

    public static String lista(Collection<String> itens) {
        return "[" + String.join(",", itens) + "]";
    }

    public static String textoJson(String valor) {
        if (valor == null) {
            return "null";
        }

        String texto = valor.replace("\\", "\\\\");
        texto = texto.replace("\"", "\\\"");
        texto = texto.replace("\n", "\\n");
        texto = texto.replace("\r", "\\r");
        texto = texto.replace("\t", "\\t");
        return "\"" + texto + "\"";
    }

    public static String valorTexto(String json, String campo) {
        String bruto = valorBruto(json, campo);
        if (bruto == null) {
            throw new IllegalArgumentException("Campo obrigatorio ausente: " + campo);
        }
        return removerAspas(bruto);
    }

    public static int valorInteiro(String json, String campo) {
        String bruto = valorBrutoObrigatorio(json, campo);
        return Integer.parseInt(removerAspas(bruto));
    }

    public static BigDecimal valorDecimal(String json, String campo, BigDecimal valorPadrao) {
        String bruto = valorBruto(json, campo);
        if (bruto == null || bruto.isBlank()) {
            return valorPadrao;
        }
        return new BigDecimal(removerAspas(bruto));
    }

    public static String valorBruto(String json, String campo) {
        String chave = textoJson(campo);
        int posicaoChave = json.indexOf(chave);
        if (posicaoChave < 0) {
            return null;
        }

        int doisPontos = json.indexOf(':', posicaoChave + chave.length());
        if (doisPontos < 0) {
            return null;
        }

        int inicio = pularEspacos(json, doisPontos + 1);
        if (inicio >= json.length()) {
            return null;
        }

        char primeiro = json.charAt(inicio);
        if (primeiro == '"') {
            int fimTexto = acharFimTexto(json, inicio);
            return json.substring(inicio, fimTexto + 1);
        }
        if (primeiro == '{') {
            int fimObjeto = acharFechamento(json, inicio, '{', '}');
            return json.substring(inicio, fimObjeto + 1);
        }
        if (primeiro == '[') {
            int fimLista = acharFechamento(json, inicio, '[', ']');
            return json.substring(inicio, fimLista + 1);
        }

        int fim = inicio;
        while (fim < json.length()) {
            char atual = json.charAt(fim);
            if (atual == ',' || atual == '}' || atual == ']') {
                break;
            }
            fim++;
        }
        return json.substring(inicio, fim).trim();
    }

    public static List<String> separarItens(String jsonLista) {
        String texto = "[]";
        if (jsonLista != null) {
            texto = jsonLista.trim();
        }

        if (texto.length() < 2 || texto.charAt(0) != '[' || texto.charAt(texto.length() - 1) != ']') {
            throw new IllegalArgumentException("Valor nao e uma lista JSON: " + jsonLista);
        }

        List<String> itens = new ArrayList<>();
        int posicao = 1;
        int fimLista = texto.length() - 1;
        while (posicao < fimLista) {
            posicao = pularEspacos(texto, posicao);
            if (posicao >= fimLista) {
                break;
            }

            int fimItem = acharFimItem(texto, posicao);
            itens.add(texto.substring(posicao, fimItem + 1));

            posicao = pularEspacos(texto, fimItem + 1);
            if (posicao < fimLista && texto.charAt(posicao) == ',') {
                posicao++;
            }
        }
        return itens;
    }

    private static String valorBrutoObrigatorio(String json, String campo) {
        String bruto = valorBruto(json, campo);
        if (bruto == null) {
            throw new IllegalArgumentException("Campo obrigatorio ausente: " + campo);
        }
        return bruto;
    }

    private static String removerAspas(String valor) {
        String texto = valor.trim();
        if (texto.length() >= 2 && texto.charAt(0) == '"' && texto.charAt(texto.length() - 1) == '"') {
            texto = texto.substring(1, texto.length() - 1);
        }

        texto = texto.replace("\\\"", "\"");
        texto = texto.replace("\\\\", "\\");
        texto = texto.replace("\\n", "\n");
        texto = texto.replace("\\r", "\r");
        texto = texto.replace("\\t", "\t");
        return texto;
    }

    private static int acharFimItem(String texto, int inicio) {
        char primeiro = texto.charAt(inicio);
        if (primeiro == '"') {
            return acharFimTexto(texto, inicio);
        }
        if (primeiro == '{') {
            return acharFechamento(texto, inicio, '{', '}');
        }
        if (primeiro == '[') {
            return acharFechamento(texto, inicio, '[', ']');
        }

        int fim = inicio;
        while (fim < texto.length() && texto.charAt(fim) != ',') {
            fim++;
        }
        return fim - 1;
    }

    private static int pularEspacos(String texto, int inicio) {
        int posicao = inicio;
        while (posicao < texto.length() && Character.isWhitespace(texto.charAt(posicao))) {
            posicao++;
        }
        return posicao;
    }

    private static int acharFimTexto(String texto, int inicio) {
        boolean barra = false;
        for (int i = inicio + 1; i < texto.length(); i++) {
            char atual = texto.charAt(i);
            if (barra) {
                barra = false;
            } else if (atual == '\\') {
                barra = true;
            } else if (atual == '"') {
                return i;
            }
        }
        throw new IllegalArgumentException("Texto JSON sem fechamento");
    }

    private static int acharFechamento(String texto, int inicio, char abre, char fecha) {
        int profundidade = 0;
        boolean dentroDeTexto = false;
        boolean barra = false;

        for (int i = inicio; i < texto.length(); i++) {
            char atual = texto.charAt(i);

            if (dentroDeTexto) {
                if (barra) {
                    barra = false;
                } else if (atual == '\\') {
                    barra = true;
                } else if (atual == '"') {
                    dentroDeTexto = false;
                }
                continue;
            }

            if (atual == '"') {
                dentroDeTexto = true;
            } else if (atual == abre) {
                profundidade++;
            } else if (atual == fecha) {
                profundidade--;
                if (profundidade == 0) {
                    return i;
                }
            }
        }

        throw new IllegalArgumentException("JSON sem fechamento para " + abre);
    }
}

# Trabalho 2 - Remote Method Invocation (RMI)

## Servico remoto implementado

O projeto Topcar foi reimplementado em uma copia isolada para usar Java RMI no pacote `br.com.topcar.rmi`.
O servico remoto simula um catalogo de pecas automotivas e pedidos de clientes.

Objeto remoto registrado no RMI Registry:

- Binding: `TopcarRequestReply`
- Referencia logica do objeto: `topcar.catalogo`
- Classe servidora: `br.com.topcar.rmi.server.ServidorRmiTopcar`
- Classe cliente: `br.com.topcar.rmi.client.ClienteRmiTopcar`

## Protocolo requisicao-resposta

A classe `Requisitante` implementa:

- `doOperation(ReferenciaObjetoRemoto o, int methodId, byte[] arguments)`
- `doOperation(ReferenciaObjetoRemoto o, String methodId, byte[] arguments)`

No servidor, `TratadorRequisicaoTopcar` implementa:

- `getRequest(byte[] request, RetornoCliente callback)`
- `sendReply(byte[] reply, String clientHost, int clientPort)`

As assinaturas foram adaptadas para Java RMI. O transporte fica por conta do RMI, sem criacao manual de sockets.

## Representacao externa de dados

As mensagens trafegam como `byte[]`, mas seu conteudo e JSON em UTF-8.
Cada requisicao contem:

- `messageType`
- `requestId`
- `objectReference`
- `methodId`
- `arguments`

Cada resposta contem:

- `messageType`
- `requestId`
- `success`
- `message`
- `result`

## Metodos remotos disponiveis

- `LISTAR_PECAS`
- `BUSCAR_PECA`
- `CADASTRAR_CLIENTE`
- `CRIAR_PEDIDO`
- `CALCULAR_TOTAL_PEDIDO`
- `CONSULTAR_CLIENTE`

Tambem ha uma tabela numerica no `Requisitante`, entao o metodo `1` corresponde a `LISTAR_PECAS`, `2` a `BUSCAR_PECA`, e assim por diante.

## Requisitos de OO atendidos

Entidades:

- `Peca`
- `Cliente`
- `Pedido`
- `ItemPedido`
- `Car`

Agregacoes:

- `Cliente` tem uma lista de `Pedido`
- `Pedido` tem uma lista de `ItemPedido`
- `ItemPedido` tem uma `Peca`
- `Peca` tem um `Car`

Extensoes:

- `Pneu extends Peca`
- `Motor extends Peca`
- `Bateria extends Peca`
- `Farol extends Peca`

## Passagem por referencia e por valor

- Passagem por referencia: o cliente obtem o stub remoto `RequisicaoRespostaRemota` pelo RMI Registry e tambem passa um `RetornoCliente` remoto para o servidor.
- Passagem por valor: argumentos e resultados de negocio sao JSON em `byte[]`, que representam objetos locais do servidor como `Cliente`, `Pedido`, `ItemPedido` e `Peca`.

## Como executar

Compilar sem Maven:

```bash
mkdir -p out
javac -d out $(find src/main/java -name "*.java")
```

Executar o servidor:

```bash
java -cp out br.com.topcar.rmi.server.ServidorRmiTopcar
```

Em outro terminal, executar o cliente:

```bash
java -cp out br.com.topcar.rmi.client.ClienteRmiTopcar
```

## Testes simples

Tambem foram criados testes simples com `main`, sem depender de JUnit:

- `br.com.topcar.rmi.teste.TesteArquivoJson`
- `br.com.topcar.rmi.teste.TesteServicoCatalogoTopcar`

Para compilar o projeto com os testes:

```bash
mkdir -p out
javac -d out $(find src/main/java src/test/java -name "*.java")
```

Para executar:

```bash
java -cp out br.com.topcar.rmi.teste.TesteArquivoJson
java -cp out br.com.topcar.rmi.teste.TesteServicoCatalogoTopcar
```

Se o ambiente tiver Maven instalado, tambem e possivel compilar com:

```bash
mvn test
```

Neste ambiente de validacao o Maven nao estava instalado, entao a verificacao foi feita com `javac` e execucao local do servidor/cliente RMI.

# Diagrama de Classes e Padrões de Projeto

Este documento apresenta o diagrama de classes da solução proposta para o projeto "Loja Sapato", juntamente com uma explicação detalhada dos padrões de projeto utilizados e as justificativas para suas escolhas.

## Diagrama de Classes

Abaixo está o diagrama de classes que ilustra a estrutura e as relações entre as principais classes do sistema:

![Diagrama de Classes](https://private-us-east-1.manuscdn.com/sessionFile/Hy3kUbIydt0lbmJv0njkkA/sandbox/hUBlg3wJFQ5hkDbligNtFF-images_1749924737383_na1fn_L2hvbWUvdWJ1bnR1L2xvamFzYXBhdG8vbG9qYXNhcGF0by9EaWFncmFtYWRlQ2Fsc3NlLUxvamFTYXBhdG8wMQ.png?Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiaHR0cHM6Ly9wcml2YXRlLXVzLWVhc3QtMS5tYW51c2Nkbi5jb20vc2Vzc2lvbkZpbGUvSHkza1ViSXlkdDBsYm1KdjBuamtrQS9zYW5kYm94L2hVQmxnM3dKRlE1aGtEYmxpZ050RkYtaW1hZ2VzXzE3NDk5MjQ3MzczODNfbmExZm5fTDJodmJXVXZkV0oxYm5SMUwyeHZhbUZ6WVhCaGRHOHZiRzlxWVhOaGNHRjBieTlFYVdGbmNtRnRZV1JsUTJGc2MzTmxMVXh2YW1GVFlYQmhkRzh3TVEucG5nIiwiQ29uZGl0aW9uIjp7IkRhdGVMZXNzVGhhbiI6eyJBV1M6RXBvY2hUaW1lIjoxNzY3MjI1NjAwfX19XX0_&Key-Pair-Id=K2HSFNDJXOU9YS&Signature=lTNMSQg9gk03F77A0n2xtTlnj5OpDDYz0kzIIXcjyWRMhHu3NWHMYDh8cmIjYixt5zk3OHAwsb-vPlJUGTpkiMl4ERbh7~8NbfoQvVzZEq5u7CoVr6Dzydmeg9ngdIh1zLNQVTm-Fn5opF3vAwz7QXNi~ogWmKgv-OML5H12tP2c6aCYDM4TGQwksb3bUfm8bwNkH9Xydrzqbb6sXMaZflXyHxl10r7YQj2gFu4VI5hwcPtmgxcB6EpA~rENU0w2fCOb7KrVsb-9L-zJ-gky0J09BlRT6Ibr~sXTEPsvlTloKZlKlO16wqeAwiRjHmTepGQVpOcpIc7zb06R7ukrVg__)




## Padrões de Projeto Utilizados

Neste projeto, foram identificados e aplicados os seguintes padrões de projeto:

### 1. Strategy Pattern (Padrão Strategy)

**Onde foi utilizado:**

O padrão Strategy foi implementado para lidar com o cálculo do frete de diferentes modalidades (aéreo e terrestre). As classes `FreteAereo` e `FreteTerrestre` implementam a interface `FreteStrategy`, que define o método `calcular` e `getFrete`. A classe `Pedido` possui uma referência a `FreteStrategy`, permitindo que o cálculo do frete seja delegado à estratégia específica selecionada.

- `FreteStrategy.java`: Interface que define o contrato para as estratégias de cálculo de frete. Ela declara os métodos `calcular(BigDecimal valorPedido)` para calcular o valor do frete com base no valor do pedido e `getFrete()` para retornar o tipo de frete (ex: "Aereo", "Terrestre").
- `FreteAereo.java`: Implementação concreta da estratégia de frete aéreo. Calcula o frete como 10% do valor do pedido.
- `FreteTerrestre.java`: Implementação concreta da estratégia de frete terrestre. Calcula o frete como 5% do valor do pedido.
- `Pedido.java`: A classe `Pedido` utiliza a interface `FreteStrategy` para calcular o frete, sem precisar conhecer os detalhes de cada implementação específica. Isso é feito através do método `getFrete()` que, dependendo do `modoFrete` configurado, instancia a estratégia correta (Aéreo ou Terrestre) e a utiliza para calcular o valor do frete.

**Por que foi utilizado:**

O padrão Strategy foi escolhido para:

- **Flexibilidade:** Permite adicionar novas modalidades de frete (ex: frete marítimo, frete expresso) sem modificar o código existente da classe `Pedido`. Basta criar uma nova classe que implemente `FreteStrategy`.
- **Manutenibilidade:** O código relacionado ao cálculo de frete é encapsulado em classes separadas, tornando-o mais fácil de entender, testar e manter.
- **Reusabilidade:** As estratégias de frete podem ser reutilizadas em diferentes partes da aplicação ou em outros projetos.
- **Remoção de condicionais:** Evita o uso de múltiplas estruturas `if-else` ou `switch-case` para determinar o tipo de frete e seu cálculo, tornando o código mais limpo e extensível.

### 2. State Pattern (Padrão State)

**Onde foi utilizado:**

O padrão State foi aplicado para gerenciar os diferentes estados de um `Pedido` (Aguardando Pagamento, Pago, Enviado, Cancelado). Cada estado é representado por uma classe separada que implementa a interface `PedidoState`. A classe `Pedido` mantém uma referência ao seu estado atual e delega as operações relacionadas ao estado para o objeto de estado.

- `PedidoState.java`: Interface que define o comportamento comum para todos os estados do pedido. Declara métodos como `sucessoAoPagar()`, `enviaPedido()`, `cancelarPedido()` e `getState()`.
- `AguardandoPagamentoState.java`: Implementação do estado "Aguardando Pagamento". Define como o pedido se comporta quando está neste estado, permitindo transições para "Pago" ou "Cancelado", mas impedindo o envio.
- `PagoState.java`: Implementação do estado "Pago". Permite a transição para "Enviado" ou "Cancelado".
- `EnviadoState.java`: Implementação do estado "Enviado". Não permite mais transições de estado, pois o pedido já foi despachado.
- `CanceladoState.java`: Implementação do estado "Cancelado". Não permite mais transições de estado.
- `Pedido.java`: A classe `Pedido` contém uma instância de `PedidoState` e delega as chamadas de método (`sucessoAoPagar`, `cancelarPedido`, `enviaPedido`) para o objeto de estado atual. A transição entre os estados é controlada pelas próprias classes de estado através da atualização da referência `state`.

**Por que foi utilizado:**

O padrão State foi escolhido para:

- **Organização do código:** Centraliza o comportamento específico de cada estado em classes separadas, evitando que a classe `Pedido` se torne um "monolito" com muitas condicionais para gerenciar o fluxo de estados.
- **Facilidade de adição de novos estados:** Permite adicionar novos estados ao pedido sem modificar as classes de estado existentes ou a classe `Pedido`.
- **Clareza e legibilidade:** Torna o fluxo de transição de estados mais explícito e fácil de entender, pois cada estado sabe para quais outros estados ele pode transitar e como.
- **Evitar condicionais complexas:** Elimina a necessidade de grandes blocos `if-else` ou `switch-case` na classe `Pedido` para lidar com a lógica de estado, resultando em um código mais limpo e menos propenso a erros.



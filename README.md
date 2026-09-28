# Sistema de Estoque de Produtos (Java)

Exercício da Primeira Avaliação — classes abstratas, herança, interfaces, polimorfismo, composição e exceções.

## Estrutura (`src/`)

| Arquivo | Papel |
|---|---|
| `EstoqueException` | Exceção base (checked) |
| `QuantidadeInvalidaException` | Preço/quantidade negativos no cadastro |
| `ProdutoIndisponivelException` | Venda acima do estoque disponível |
| `Vendavel` | Interface com `vender(int)` |
| `Product` | Classe abstrata: atributos privados, `calcularValorTotal()` abstrato, `getDescricao()`, `vender()`, sobrecarga de `aplicarDesconto()` |
| `ProdutoComum` | `preco × quantidade` |
| `ProdutoPerecivel` | `diasParaVencer`; 20% de desconto quando ≤ 3 dias; `getDescricao()` sobrescrito |
| `Estoque` | Composição: tem uma `List<Product>`; soma polimórfica do valor total |
| `EstoqueApp` | `main()` com os cenários pedidos |

## Como compilar e executar

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out EstoqueApp
```

No Windows (PowerShell), para acentos corretos: `chcp 65001` antes de executar.

A saída de referência está em `saida.txt`.

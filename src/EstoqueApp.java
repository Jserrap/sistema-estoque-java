import java.util.Locale;

public class EstoqueApp {
    private static final Locale BR = Locale.forLanguageTag("pt-BR");

    // Operação combinada: pode lançar qualquer EstoqueException (cadastro ou venda)
    private static void cadastrarEVender(Estoque estoque, String nome, double preco, int qtd,
                                         int diasParaVencer, int qtdVenda) throws EstoqueException {
        Product p = new ProdutoPerecivel(nome, preco, qtd, diasParaVencer);
        estoque.adicionarProduto(p);
        p.vender(qtdVenda);
    }

    public static void main(String[] args) {
        Estoque estoque = new Estoque();

        System.out.println("=== 1. Cadastro de produtos ===");
        try {
            estoque.adicionarProduto(new ProdutoComum("Arroz 5kg", 25.90, 10));
            estoque.adicionarProduto(new ProdutoComum("Detergente", 2.50, 40));
            estoque.adicionarProduto(new ProdutoPerecivel("Leite 1L", 5.00, 20, 2));   // vence em <= 3 dias
            estoque.adicionarProduto(new ProdutoPerecivel("Queijo Minas", 30.00, 5, 10));
            System.out.println("Produtos cadastrados com sucesso.");
        } catch (QuantidadeInvalidaException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
        estoque.listarProdutos();

        System.out.println("\n=== 2. Cadastro inválido (quantidade negativa) ===");
        try {
            estoque.adicionarProduto(new ProdutoComum("Feijão 1kg", 8.00, -3));
            System.out.println("Produto cadastrado (não deveria acontecer).");
        } catch (QuantidadeInvalidaException e) {
            System.out.println("QuantidadeInvalidaException capturada: " + e.getMessage());
        }

        System.out.println("\n=== 3. Venda válida ===");
        try {
            estoque.venderProduto(0, 4);
            System.out.println("Venda de 4 unidades de '" + estoque.getProduto(0).getNome()
                    + "' realizada. Restam " + estoque.getProduto(0).getQuantidade() + ".");
        } catch (ProdutoIndisponivelException e) {
            System.out.println("Erro na venda: " + e.getMessage());
        }

        System.out.println("\n=== 4. Venda acima do disponível ===");
        try {
            cadastrarEVender(estoque, "Iogurte", 3.20, 8, 1, 50); // só há 8 unidades
            System.out.println("Venda realizada (não deveria acontecer).");
        } catch (QuantidadeInvalidaException e) {
            System.out.println("QuantidadeInvalidaException capturada: " + e.getMessage());
        } catch (ProdutoIndisponivelException e) {
            System.out.println("ProdutoIndisponivelException capturada: " + e.getMessage());
        } catch (EstoqueException e) { // mais genérico por último
            System.out.println("EstoqueException capturada: " + e.getMessage());
        }

        System.out.println("\n=== 5. Sobrecarga de aplicarDesconto() ===");
        Product detergente = estoque.getProduto(1);
        Product queijo = estoque.getProduto(3);
        detergente.aplicarDesconto(10);          // 10% simples
        queijo.aplicarDesconto(50, 5.00);        // 50%, limitado a R$ 5,00
        System.out.printf(BR, "Detergente com 10%% de desconto: R$ %.2f%n", detergente.getPreco());
        System.out.printf(BR, "Queijo com 50%% (máx. R$ 5,00): R$ %.2f%n", queijo.getPreco());

        System.out.println("\n=== 6. Estoque final e valor total ===");
        estoque.listarProdutos();
        System.out.printf(BR, "VALOR TOTAL DO ESTOQUE: R$ %.2f%n", estoque.calcularValorTotalEstoque());
    }
}

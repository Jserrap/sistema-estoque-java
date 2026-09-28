import java.util.ArrayList;
import java.util.List;

public class Estoque {
    // Composição: Estoque TEM UMA lista de Product
    private final List<Product> produtos = new ArrayList<>();

    public void adicionarProduto(Product p) {
        if (p == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo.");
        }
        produtos.add(p);
    }

    public void venderProduto(int indice, int quantidade) throws ProdutoIndisponivelException {
        if (indice < 0 || indice >= produtos.size()) {
            throw new ProdutoIndisponivelException("Produto de índice " + indice + " não encontrado.");
        }
        produtos.get(indice).vender(quantidade); // propaga ProdutoIndisponivelException
    }

    public double calcularValorTotalEstoque() {
        double total = 0;
        for (Product p : produtos) {
            total += p.calcularValorTotal(); // polimorfismo dinâmico
        }
        return total;
    }

    public Product getProduto(int indice) {
        return produtos.get(indice);
    }

    public void listarProdutos() {
        for (int i = 0; i < produtos.size(); i++) {
            Product p = produtos.get(i);
            System.out.printf(Product.BR, "  [%d] %s | Valor total: R$ %.2f%n",
                    i, p.getDescricao(), p.calcularValorTotal());
        }
    }
}

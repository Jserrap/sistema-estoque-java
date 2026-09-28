import java.util.Locale;

public abstract class Product implements Vendavel {
    protected static final Locale BR = Locale.forLanguageTag("pt-BR");

    private String nome;
    private double preco;
    private int quantidade;

    public Product(String nome, double preco, int quantidade) throws QuantidadeInvalidaException {
        if (preco < 0) {
            throw new QuantidadeInvalidaException("Preço inválido para '" + nome + "': " + preco);
        }
        if (quantidade < 0) {
            throw new QuantidadeInvalidaException("Quantidade inválida para '" + nome + "': " + quantidade);
        }
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    // Cada subclasse decide como calcular
    public abstract double calcularValorTotal();

    public String getDescricao() {
        return String.format(BR, "%s | Preço: R$ %.2f | Quantidade: %d", nome, preco, quantidade);
    }

    @Override
    public void vender(int quantidadeDesejada) throws ProdutoIndisponivelException {
        if (quantidadeDesejada <= 0) {
            throw new IllegalArgumentException("A quantidade vendida deve ser positiva.");
        }
        if (quantidadeDesejada > quantidade) {
            throw new ProdutoIndisponivelException(String.format(
                    "Estoque insuficiente de '%s': solicitado %d, disponível %d.",
                    nome, quantidadeDesejada, quantidade));
        }
        quantidade -= quantidadeDesejada;
    }

    // Sobrecarga (polimorfismo estático) — versão 1: desconto percentual simples
    public void aplicarDesconto(double percentual) {
        validarPercentual(percentual);
        preco -= preco * percentual / 100.0;
    }

    // Sobrecarga — versão 2: desconto percentual limitado a um valor máximo (em R$)
    public void aplicarDesconto(double percentual, double descontoMaximo) {
        validarPercentual(percentual);
        if (descontoMaximo < 0) {
            throw new IllegalArgumentException("Desconto máximo não pode ser negativo.");
        }
        double desconto = Math.min(preco * percentual / 100.0, descontoMaximo);
        preco -= desconto;
    }

    private void validarPercentual(double percentual) {
        if (percentual < 0 || percentual > 100) {
            throw new IllegalArgumentException("Percentual deve estar entre 0 e 100.");
        }
    }

    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getQuantidade() { return quantidade; }
}

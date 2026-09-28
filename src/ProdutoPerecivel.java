public class ProdutoPerecivel extends Product {
    private static final int DIAS_LIMITE_DESCONTO = 3;
    private static final double DESCONTO_VENCIMENTO = 0.20;

    private int diasParaVencer;

    public ProdutoPerecivel(String nome, double preco, int quantidade, int diasParaVencer)
            throws QuantidadeInvalidaException {
        super(nome, preco, quantidade);
        if (diasParaVencer < 0) {
            throw new QuantidadeInvalidaException("Dias para vencer inválido para '" + nome + "': " + diasParaVencer);
        }
        this.diasParaVencer = diasParaVencer;
    }

    @Override
    public double calcularValorTotal() {
        double total = getPreco() * getQuantidade();
        if (diasParaVencer <= DIAS_LIMITE_DESCONTO) {
            total *= (1 - DESCONTO_VENCIMENTO); // 20% de desconto automático
        }
        return total;
    }

    @Override
    public String getDescricao() {
        String desc = super.getDescricao() + " | Vence em: " + diasParaVencer + " dia(s)";
        if (diasParaVencer <= DIAS_LIMITE_DESCONTO) {
            desc += " (20% de desconto aplicado)";
        }
        return desc;
    }

    public int getDiasParaVencer() { return diasParaVencer; }
}

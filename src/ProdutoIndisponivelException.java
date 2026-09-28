public class ProdutoIndisponivelException extends EstoqueException {
    private static final long serialVersionUID = 1L;

    public ProdutoIndisponivelException(String mensagem) {
        super(mensagem);
    }
}

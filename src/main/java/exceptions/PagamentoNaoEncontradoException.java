package exceptions;

public class PagamentoNaoEncontradoException extends Exception{

    public PagamentoNaoEncontradoException() {
        super("Pagamento não encontrado");
    }
}

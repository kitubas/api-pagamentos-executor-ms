package exceptions;

public class PagamentoNaoEncontradoException extends Throwable{

    public PagamentoNaoEncontradoException() {
        super("Pagamento não encontrado");
    }
}

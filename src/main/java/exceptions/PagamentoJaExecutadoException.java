package exceptions;

public class PagamentoJaExecutadoException extends Throwable {

    public PagamentoJaExecutadoException() {
        super("Pagamento já executado anteriormente");
    }
}

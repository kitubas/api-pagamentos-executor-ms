package exceptions;

public class PagamentoJaExecutadoException extends Exception {

    public PagamentoJaExecutadoException() {
        super("Pagamento já executado anteriormente");
    }
}

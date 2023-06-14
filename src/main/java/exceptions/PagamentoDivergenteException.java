package exceptions;

public class PagamentoDivergenteException extends Throwable {

    public PagamentoDivergenteException() {
        super("Error!!! Pagamento divergente!");
    }
}

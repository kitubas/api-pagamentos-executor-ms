package exceptions;

public class PagamentoDivergenteException extends Exception {

    public PagamentoDivergenteException() {
        super("Error!!! Pagamento divergente!");
    }
}

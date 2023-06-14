package executor;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import model.Pagamento;

@ApplicationScoped
@Slf4j
public class PagamentoExecutor {

    public Pagamento executarPagamento(Pagamento pagamento){
        log.info("pagamento " + pagamento.getId() + " está sendo executado");
        pagamento.setExecutado(true);
        return pagamento;
    }
}

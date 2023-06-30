package strategy;

import jakarta.inject.Singleton;
import model.Pagamento;
import strategy.impl.EstrategiaPagamentoAgendado;
import strategy.impl.EstrategiaPagamentoImediato;

@Singleton
public class FabricaEstrategiaPagamento {

    public static EstrategiaPagamento criarEstrategiaPagamento(Pagamento pagamento) {
        switch (pagamento.getTipoPagamento()) {
            case AGENDADO:
                return new EstrategiaPagamentoAgendado(pagamento);

            default:
                return new EstrategiaPagamentoImediato(pagamento);
        }
    }
}

package strategy.impl;

import com.fasterxml.jackson.core.JsonProcessingException;

import exceptions.PagamentoDivergenteException;
import exceptions.PagamentoJaExecutadoException;
import exceptions.PagamentoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Pagamento;
import service.PagamentoExecutorService;
import strategy.EstrategiaPagamento;

@RequiredArgsConstructor
@Slf4j
public class EstrategiaPagamentoImediato implements EstrategiaPagamento {

    private final Pagamento pagamento;

    @Override
    public void executar(PagamentoExecutorService service) throws JsonProcessingException,
            PagamentoJaExecutadoException, PagamentoNaoEncontradoException, PagamentoDivergenteException {
        log.info("Usando estratégia de pagamento imediato...");
        service.executePagamento(pagamento);
    }

}

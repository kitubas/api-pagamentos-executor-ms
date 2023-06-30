package strategy;

import com.fasterxml.jackson.core.JsonProcessingException;

import exceptions.PagamentoDivergenteException;
import exceptions.PagamentoJaExecutadoException;
import exceptions.PagamentoNaoEncontradoException;
import service.PagamentoExecutorService;

public interface EstrategiaPagamento {
    void executar(PagamentoExecutorService service) throws JsonProcessingException,
            PagamentoJaExecutadoException, PagamentoNaoEncontradoException, PagamentoDivergenteException;
}

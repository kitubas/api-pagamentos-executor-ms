package service;

import com.fasterxml.jackson.core.JsonProcessingException;

import exceptions.PagamentoDivergenteException;
import exceptions.PagamentoJaExecutadoException;
import exceptions.PagamentoNaoEncontradoException;
import model.Pagamento;


public interface PagamentoExecutorService {

    void executePagamento(Pagamento pagamento) throws JsonProcessingException, PagamentoJaExecutadoException, PagamentoNaoEncontradoException, 
    PagamentoDivergenteException;
    
    
}

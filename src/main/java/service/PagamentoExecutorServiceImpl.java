package service;

import java.util.UUID;
import java.util.function.Supplier;

import com.fasterxml.jackson.core.JsonProcessingException;

import exceptions.PagamentoDivergenteException;
import exceptions.PagamentoJaExecutadoException;
import exceptions.PagamentoNaoEncontradoException;
import executor.PagamentoExecutor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Pagamento;
import repository.PagamentoRepository;

@RequiredArgsConstructor
@ApplicationScoped
@Slf4j
public class PagamentoExecutorServiceImpl implements PagamentoExecutorService {
    
   

    @Inject
    private  PagamentoRepository pagamentoRepository;

    @Inject
    private  PagamentoExecutor pagamentoExecutor;

    private final Supplier<PagamentoNaoEncontradoException> pagamentoNaoEncontradoExceptionSupplier = PagamentoNaoEncontradoException::new;

    @Transactional
    public void executePagamento(Pagamento pagamento) throws JsonProcessingException, PagamentoJaExecutadoException, PagamentoNaoEncontradoException, PagamentoDivergenteException {
        
        if (pagamento.getExecutado()) {
            log.error("Pagamento já executado");
            throw new PagamentoJaExecutadoException();
        }
        Pagamento pagamentoById = getPagamentoById(pagamento.getId());
        if(!pagamento.equals(pagamentoById)) {
            log.error("Pagamento divergente");
            throw new PagamentoDivergenteException();
        }
        Pagamento pagamentoExecutado = pagamentoExecutor.executarPagamento(pagamento);
        log.info("Persistindo pagamento...");
        savePagamento(pagamentoExecutado);
    }

    public void savePagamento(Pagamento pagamento){
        pagamentoRepository.getEntityManager().merge(pagamento);
    }

    public Pagamento getPagamentoById(UUID id) throws PagamentoNaoEncontradoException {
        return pagamentoRepository.findById(id).orElseThrow(pagamentoNaoEncontradoExceptionSupplier);
    }
}

package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.PagamentoDivergenteException;
import exceptions.PagamentoJaExecutadoException;
import exceptions.PagamentoNaoEncontradoException;
import executor.PagamentoExecutor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mapper.PagamentoMapper;
import model.Pagamento;
import repository.PagamentoRepository;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.UUID;
import java.util.function.Supplier;

@RequiredArgsConstructor
@ApplicationScoped
public class PagamentoExecutorService {

    @Inject
    private  PagamentoMapper pagamentoMapper;

    @Inject
    private  PagamentoRepository pagamentoRepository;

    @Inject
    private  PagamentoExecutor pagamentoExecutor;

    private final Supplier<PagamentoNaoEncontradoException> pagamentoNaoEncontradoExceptionSupplier = PagamentoNaoEncontradoException::new;

    @Transactional
    public void executePagamento(Message message) throws JsonProcessingException, PagamentoJaExecutadoException, PagamentoNaoEncontradoException, PagamentoDivergenteException {
        Pagamento pagamento = pagamentoMapper.pagamentoFromMessage(message);
        if (pagamento.getExecutado()) {
            throw new PagamentoJaExecutadoException();
        }
        Pagamento pagamentoById = getPagamentoById(pagamento.getId());
        if(!pagamento.equals(pagamentoById)) {
            throw new PagamentoDivergenteException();
        }
        Pagamento pagamentoExecutado = pagamentoExecutor.executarPagamento(pagamento);
        savePagamento(pagamentoExecutado);
    }

    public void savePagamento(Pagamento pagamento){
        pagamentoRepository.getEntityManager().merge(pagamento);
    }

    public Pagamento getPagamentoById(UUID id) throws PagamentoNaoEncontradoException {
        return pagamentoRepository.findById(id).orElseThrow(pagamentoNaoEncontradoExceptionSupplier);
    }
}

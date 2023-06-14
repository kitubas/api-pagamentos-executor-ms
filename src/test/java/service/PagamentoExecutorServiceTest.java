package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import config.TestsConfiguration;
import exceptions.PagamentoDivergenteException;
import exceptions.PagamentoJaExecutadoException;
import exceptions.PagamentoNaoEncontradoException;
import executor.PagamentoExecutor;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import mapper.PagamentoMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.PagamentoRepository;
import software.amazon.awssdk.services.sqs.model.Message;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@QuarkusTestResource(H2DatabaseTestResource.class)
@QuarkusTest
class PagamentoExecutorServiceTest extends TestsConfiguration {

    @Inject
    private  PagamentoMapper pagamentoMapper;

    @Inject
    private  PagamentoRepository pagamentoRepository;

    @Inject
    private  PagamentoExecutor pagamentoExecutor;

    @Inject
    PagamentoExecutorService pagamentoExecutorService; //= new PagamentoExecutorService(pagamentoMapper,pagamentoRepository,pagamentoExecutor);

    @Test
    void whenMessageReceivedShouldValidateAndExecute() throws JsonProcessingException, PagamentoJaExecutadoException, PagamentoNaoEncontradoException, PagamentoDivergenteException {
        Message pagamentoMessage = givenAMessage();
//        whenVeryfingAValidMessageAtTheRepository();

//        when(pagamentoMapper.pagamentoFromMessage(Mockito.mock())).thenReturn(givenAPagamento());
//        pagamentoExecutorService.executePagamento(pagamentoMessage);

//
//        verify(pagamentoExecutorService).executePagamento(Mockito.any());
    }

}
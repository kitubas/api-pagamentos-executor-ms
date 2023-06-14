package mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import config.TestsConfiguration;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import model.Pagamento;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.sqs.model.Message;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class PagamentoMapperTest extends TestsConfiguration {


    @Inject
    PagamentoMapper pagamentoMapper;


    @Test
    void pagamentoFromJson() throws JsonProcessingException {
        Message pagamentoMessage = givenAMessage();
        Pagamento pagamento = pagamentoMapper.pagamentoFromMessage(pagamentoMessage);
        Pagamento expectedPagamento = givenAPagamento();
        assertEquals(expectedPagamento,pagamento);
    }


}
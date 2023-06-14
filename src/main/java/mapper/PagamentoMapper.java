package mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import model.Pagamento;
import software.amazon.awssdk.services.sqs.model.Message;

@RequiredArgsConstructor
@ApplicationScoped
public class PagamentoMapper {


    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    public Pagamento pagamentoFromMessage(Message msg) throws JsonProcessingException {

            return objectMapper.readValue(objectMapper.readTree(msg.body()).get("Message").asText(),
                    Pagamento.class);

    }
}

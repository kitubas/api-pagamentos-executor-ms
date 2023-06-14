import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import exceptions.PagamentoDivergenteException;
import exceptions.PagamentoJaExecutadoException;
import exceptions.PagamentoNaoEncontradoException;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import model.Pagamento;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import service.PagamentoExecutorService;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@ApplicationScoped
@Startup
@Slf4j
public class SQSListener {

    @Inject
    private  SqsClient sqsClient;

    @Inject
    @ConfigProperty(name = "quarkus.sqs.aws.queue.url")
    private String queueUrl;

    @Inject
    private  PagamentoExecutorService pagamentoExecutorService;

    static ObjectReader PAGAMENTO_READER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
            .readerFor(Pagamento.class);
    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final int MAX_MESSAGES = 3; // Define o número máximo de mensagens a serem processadas por vez
    private static final ExecutorService executorService = Executors.newFixedThreadPool(MAX_MESSAGES); // Cria um pool de threads com tamanho máximo de MAX_MESSAGES

    @PostConstruct
    public void startListening() throws IOException {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .waitTimeSeconds(20)
                .maxNumberOfMessages(10)
                .build();
        ////////////////
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
                log.info("qtd msgs recebidas: " + messages.size());
                int count = 0; // Variável para contar o número de mensagens processadas

                for (Message message : messages) {
                    if (count >= MAX_MESSAGES) {
                        break; // Sai do loop se o número máximo de mensagens for atingido
                    }
                    try {
                        executorService.submit(processMessage(message));
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    count++; // Incrementa o contador de mensagens processadas
                }
            }
        };
        // Agendar a tarefa para ser executada a cada 3 segundos
        timer.scheduleAtFixedRate(task, 0, 3000);
    }

    @Transactional
    public Runnable processMessage(Message message) throws IOException {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    pagamentoExecutorService.executePagamento(message);
                    sqsClient.deleteMessage(DeleteMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .receiptHandle(message.receiptHandle())
                            .build());
                } catch (Exception | PagamentoJaExecutadoException | PagamentoNaoEncontradoException |
                         PagamentoDivergenteException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @PreDestroy
    public void shutdownExecutorService() {
        executorService.shutdown();
    }
}

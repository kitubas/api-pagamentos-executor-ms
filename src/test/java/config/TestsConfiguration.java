package config;

import model.Pagamento;
import software.amazon.awssdk.services.sqs.model.Message;

import java.math.BigDecimal;
import java.util.UUID;

public class TestsConfiguration {
    protected Message givenAMessage() {
        return Message.builder()
                .messageId("messageId")
                .receiptHandle("reciptHandle")
                .md5OfBody("5ad9c2f5d1990d45d5073675ef83a427")
                .body("{\"Type\": \"Notification\", \"MessageId\": \"04bc9d01-0980-410c-873d-2e31b27d1fc2\", \"TopicArn\": \"arn:aws:sns:us-east-1:000000000000:sample-topic\", \"Message\": \"{\\\"id\\\":\\\"13f53f32-bd5d-47de-9781-b09f91e86588\\\",\\\"dataHora\\\":\\\"2023-06-06T12:36:19.437667446\\\",\\\"valor\\\":59,\\\"contaDestino\\\":\\\"da baea\\\",\\\"contaOrigem\\\":\\\"santana\\\",\\\"executado\\\":false}\", \"Timestamp\": \"2023-06-06T12:36:20.316Z\", \"SignatureVersion\": \"1\", \"Signature\": \"EXAMPLEpH+..\", \"SigningCertURL\": \"https://sns.us-east-1.amazonaws.com/SimpleNotificationService-0000000000000000000000.pem\", \"UnsubscribeURL\": \"http://localhost:4566/?Action=Unsubscribe&SubscriptionArn=arn:aws:sns:us-east-1:000000000000:sample-topic:d181dc2a-cb85-4401-9ab4-b6014e42b719\", \"MessageAttributes\": {\"id\": {\"Type\": \"String\", \"Value\": \"52932657-5be3-b66d-1f06-6b2737b52626\"}, \"contentType\": {\"Type\": \"String\", \"Value\": \"text/plain;charset=UTF-8\"}, \"timestamp\": {\"Type\": \"Number.java.lang.Long\", \"Value\": \"1686054979991\"}}}")
                .build();
    }

    protected Pagamento givenAPagamento(){
        return Pagamento.builder()
                .id(UUID.fromString("13f53f32-bd5d-47de-9781-b09f91e86588"))
                .dataHora("2023-06-06T12:36:19.437667446")
                .valor(new BigDecimal(59))
                .contaDestino("da baea")
                .contaOrigem("santana")
                .executado(false)
                .build();
    }

}

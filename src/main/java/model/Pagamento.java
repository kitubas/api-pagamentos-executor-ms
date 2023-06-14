package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Data
public class Pagamento {

    @Id
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("dataHora")
    @Column(name = "data_hora")
    private String dataHora;
    @NonNull
    @JsonProperty("valor")
    private BigDecimal valor;
    @NonNull
    @JsonProperty("contaDestino")
    @Column(name = "conta_destino")
    private String contaDestino;

    @NonNull
    @JsonProperty("contaOrigem")
    @Column(name = "conta_origem")
    private String contaOrigem;

    @JsonProperty("executado")
    private Boolean executado;


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pagamento pagamento) {
            return pagamento.getId().equals(this.id)
                    && pagamento.getContaOrigem().equals(this.contaOrigem)
                    && pagamento.getDataHora().equals(this.dataHora)
                    && pagamento.getContaDestino().equals(this.contaDestino)
                    && pagamento.getValor().compareTo(this.valor)==0
                    && pagamento.getExecutado().equals(this.executado);
        }
        return false;
    }


//    return switch (obj){
//        case model.Pagamento pagamento when pagamento.getId().equals(this.id)
//                && pagamento.getContaOrigem().equals(this.contaOrigem)
//                && pagamento.getDataHora().equals(this.dataHora)
//                && pagamento.getContaDestino().equals(this.contaDestino)
//                && pagamento.getValor().compareTo(this.valor)==0
//                && pagamento.getExecutado().equals(this.executado) -> true;
//        default -> false;
//    };
}

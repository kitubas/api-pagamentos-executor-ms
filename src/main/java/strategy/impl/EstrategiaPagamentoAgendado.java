package strategy.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.fasterxml.jackson.core.JsonProcessingException;

import exceptions.PagamentoDivergenteException;
import exceptions.PagamentoJaExecutadoException;
import exceptions.PagamentoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Pagamento;
import service.PagamentoExecutorService;
import strategy.EstrategiaPagamento;

@RequiredArgsConstructor
@Slf4j
public class EstrategiaPagamentoAgendado implements EstrategiaPagamento {

    private final Pagamento pagamento;

    @Override
    public void executar(PagamentoExecutorService service) throws JsonProcessingException,
            PagamentoJaExecutadoException, PagamentoNaoEncontradoException, PagamentoDivergenteException {
        log.info("Usando estratégia de pagamento agendado...");
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    service.executePagamento(pagamento);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = formatter.parse(pagamento.getDataHoraExecucao());
            timer.schedule(task, date);
            log.info("Pagamento agendado para {}", date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

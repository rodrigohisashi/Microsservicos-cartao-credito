package io.github.rodrigotakeuti.mscartoes.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rodrigotakeuti.mscartoes.model.Cartao;
import io.github.rodrigotakeuti.mscartoes.model.CartaoCliente;
import io.github.rodrigotakeuti.mscartoes.model.DadosSolicitacaoEmissaoCartao;
import io.github.rodrigotakeuti.mscartoes.repository.CartaoClienteRepository;
import io.github.rodrigotakeuti.mscartoes.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;

    private final CartaoClienteRepository cartaoClienteRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload) {
        var mapper = new ObjectMapper();
        try {
            DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
            Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
            CartaoCliente cartaoCliente = new CartaoCliente();
            cartaoCliente.setCartao(cartao);
            cartaoCliente.setCpf(dados.getCpf());
            cartaoCliente.setLimite(dados.getLimiteLiberado());
            cartaoClienteRepository.save(cartaoCliente);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}

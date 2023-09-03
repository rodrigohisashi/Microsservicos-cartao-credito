package io.github.rodrigotakeuti.msavaliadorcredito.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import io.github.rodrigotakeuti.msavaliadorcredito.clients.CartoesResourceClient;
import io.github.rodrigotakeuti.msavaliadorcredito.clients.ClienteResourceClient;
import io.github.rodrigotakeuti.msavaliadorcredito.exceptions.DadosClienteNotFoundException;
import io.github.rodrigotakeuti.msavaliadorcredito.exceptions.ErroComunicacaoMicrosservicesException;
import io.github.rodrigotakeuti.msavaliadorcredito.exceptions.ErroSolicitacaoCartaoException;
import io.github.rodrigotakeuti.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import io.github.rodrigotakeuti.msavaliadorcredito.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;

    private final CartoesResourceClient cartoesResourceClient;

    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicrosservicesException {
        try {
            var dadosClienteResponse = clientesClient.getDadosClientesPorCPF(cpf);
            var dadosCartoesResponse = cartoesResourceClient.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(dadosCartoesResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();

            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicrosservicesException(e.getMessage(), status);
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException, ErroComunicacaoMicrosservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.getDadosClientesPorCPF(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesResourceClient.getCartoesRendaAte(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();
            var listaCartoesAprovados = cartoes.stream().map(cartao -> {
                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);
                return aprovado;
            }).toList();

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);
        } catch (FeignException.FeignClientException e) {
            int status = e.status();

            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicrosservicesException(e.getMessage(), status);
        }
    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao) {
        try {
            emissaoCartaoPublisher.solicitarCartao(dadosSolicitacaoEmissaoCartao);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);

        } catch (Exception e) {
            throw new ErroSolicitacaoCartaoException(e.getMessage());

        }
    }
}

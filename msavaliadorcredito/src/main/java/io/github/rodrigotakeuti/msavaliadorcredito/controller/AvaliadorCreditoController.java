package io.github.rodrigotakeuti.msavaliadorcredito.controller;

import io.github.rodrigotakeuti.msavaliadorcredito.exceptions.DadosClienteNotFoundException;
import io.github.rodrigotakeuti.msavaliadorcredito.exceptions.ErroComunicacaoMicrosservicesException;
import io.github.rodrigotakeuti.msavaliadorcredito.exceptions.ErroSolicitacaoCartaoException;
import io.github.rodrigotakeuti.msavaliadorcredito.model.*;
import io.github.rodrigotakeuti.msavaliadorcredito.services.AvaliadorCreditoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/avaliacoes-credito")
@RequiredArgsConstructor
@Slf4j
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @GetMapping(value ="/situacao-cliente", params = "cpf")
    public ResponseEntity<SituacaoCliente> consultaSituacaoCliente(@RequestParam("cpf") String cpf) {

        try {
            SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicrosservicesException e) {
            return ResponseEntity.status(Objects.requireNonNull(HttpStatus.resolve(e.getStatus()))).build();
        }
    }

    @PostMapping
    public ResponseEntity<RetornoAvaliacaoCliente> realizarAvaliacao(@RequestBody DadosAvaliacao dados) {

        try {
            RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicrosservicesException e) {
            return ResponseEntity.status(Objects.requireNonNull(HttpStatus.resolve(e.getStatus()))).build();
        }
    }


    @PostMapping(value = "/solicitacoes-cartao")
    public ResponseEntity solicitaCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados) {
        try {
            ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = avaliadorCreditoService.solicitarEmissaoCartao(dados);

            return ResponseEntity.ok(protocoloSolicitacaoCartao);
        } catch (ErroSolicitacaoCartaoException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}

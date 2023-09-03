package io.github.rodrigotakeuti.mscartoes.controller;

import io.github.rodrigotakeuti.mscartoes.dto.CartaoClienteDTO;
import io.github.rodrigotakeuti.mscartoes.dto.CartaoDTO;
import io.github.rodrigotakeuti.mscartoes.model.Cartao;
import io.github.rodrigotakeuti.mscartoes.services.CartaoClienteService;
import io.github.rodrigotakeuti.mscartoes.services.CartaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
@Slf4j
public class CartaoController {

    private final CartaoService cartaoService;

    private final CartaoClienteService cartaoClienteService;

    @GetMapping
    public String status() {
        log.info("Obtendo o status do microsservice de cliente");
        return "ok";
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody CartaoDTO cartaoDTO) {
        var cartaoSalvo = cartaoService.save(cartaoDTO);
        URI gatewayLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath(null)
                .host("localhost")
                .port(8080)
                .path("/cartoes")
                .build()
                .toUri();

        return ResponseEntity.created(gatewayLocation).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartaoClienteDTO>> getCartoesByCliente(@RequestParam("cpf") String cpf) {
        var listCartoesCliente = cartaoClienteService.listaCartoesByCpf(cpf);
        var listCartaoClienteDTO = listCartoesCliente.stream().map(CartaoClienteDTO::fromModel).toList();
        return ResponseEntity.ok(listCartaoClienteDTO);
    }
}

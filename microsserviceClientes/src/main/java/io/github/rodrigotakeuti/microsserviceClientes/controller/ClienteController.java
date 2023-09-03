package io.github.rodrigotakeuti.microsserviceClientes.controller;

import io.github.rodrigotakeuti.microsserviceClientes.dto.ClienteDTO;
import io.github.rodrigotakeuti.microsserviceClientes.model.Cliente;
import io.github.rodrigotakeuti.microsserviceClientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public String status() {
        log.info("Obtendo o status do microsservice de cliente");
        return "ok";
    }


    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody ClienteDTO clienteDTO) {
        var cliente = clienteService.save(clienteDTO);
        URI gatewayLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath(null)
                .host("localhost")
                .port(8080)
                .path("/clientes")
                .build()
                .toUri();

        return ResponseEntity.created(gatewayLocation).build();
    }


    @GetMapping(params = "cpf")
    public ResponseEntity<Cliente> getDadosClientesPorCPF(String cpf) {
        var cliente = clienteService.getByCPF(cpf);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
}

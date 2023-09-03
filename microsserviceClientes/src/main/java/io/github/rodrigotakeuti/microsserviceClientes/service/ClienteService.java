package io.github.rodrigotakeuti.microsserviceClientes.service;

import io.github.rodrigotakeuti.microsserviceClientes.dto.ClienteDTO;
import io.github.rodrigotakeuti.microsserviceClientes.model.Cliente;
import io.github.rodrigotakeuti.microsserviceClientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente save(ClienteDTO clienteDTO) {
        return clienteRepository.save(new Cliente(clienteDTO));
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> getByCPF (String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

}

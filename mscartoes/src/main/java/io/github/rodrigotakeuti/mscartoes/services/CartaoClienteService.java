package io.github.rodrigotakeuti.mscartoes.services;

import io.github.rodrigotakeuti.mscartoes.model.CartaoCliente;
import io.github.rodrigotakeuti.mscartoes.repository.CartaoClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoClienteService {

    private final CartaoClienteRepository repository;

    public List<CartaoCliente> listaCartoesByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

}

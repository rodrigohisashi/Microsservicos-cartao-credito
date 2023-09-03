package io.github.rodrigotakeuti.mscartoes.repository;

import io.github.rodrigotakeuti.mscartoes.model.Cartao;
import io.github.rodrigotakeuti.mscartoes.model.CartaoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaoClienteRepository extends JpaRepository<CartaoCliente, Long> {

    List<CartaoCliente> findByCpf(String cpf);
}

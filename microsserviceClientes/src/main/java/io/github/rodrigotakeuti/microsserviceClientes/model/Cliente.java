package io.github.rodrigotakeuti.microsserviceClientes.model;

import io.github.rodrigotakeuti.microsserviceClientes.dto.ClienteDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String cpf;

    @Column
    private String nome;

    @Column
    private Integer idade;

    public Cliente(String cpf, String nome, Integer idade) {
        this.cpf = cpf;
        this.nome = nome;
        this.idade = idade;
    }

    public Cliente(ClienteDTO clienteDTO) {
        this.cpf = clienteDTO.getCpf();
        this.nome = clienteDTO.getNome();
        this.idade = clienteDTO.getIdade();
    }
}

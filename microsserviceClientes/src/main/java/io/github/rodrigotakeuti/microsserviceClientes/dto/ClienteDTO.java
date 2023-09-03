package io.github.rodrigotakeuti.microsserviceClientes.dto;

import lombok.Data;

@Data
public class ClienteDTO {

    private String cpf;

    private String nome;

    private Integer idade;
}

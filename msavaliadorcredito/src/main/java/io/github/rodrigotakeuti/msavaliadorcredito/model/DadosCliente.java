package io.github.rodrigotakeuti.msavaliadorcredito.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DadosCliente {

    private Long id;

    private String cpf;

    private String nome;

    private Integer idade;
}

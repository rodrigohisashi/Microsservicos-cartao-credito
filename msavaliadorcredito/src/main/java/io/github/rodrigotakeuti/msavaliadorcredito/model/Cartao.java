package io.github.rodrigotakeuti.msavaliadorcredito.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Cartao {

    private Long id;

    private String nome;

    private String bandeira;

    private BigDecimal renda;

    private BigDecimal limiteBasico;
}

package io.github.rodrigotakeuti.mscartoes.dto;

import io.github.rodrigotakeuti.mscartoes.model.BandeiraCartao;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoDTO {

    private String nome;

    private BandeiraCartao bandeira;

    private BigDecimal renda;

    private BigDecimal limiteBasico;

}

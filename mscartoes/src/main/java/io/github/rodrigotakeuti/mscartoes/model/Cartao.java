package io.github.rodrigotakeuti.mscartoes.model;

import io.github.rodrigotakeuti.mscartoes.dto.CartaoDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private BandeiraCartao bandeira;

    private BigDecimal renda;

    private BigDecimal limiteBasico;

    public Cartao(String nome, BandeiraCartao bandeira, BigDecimal renda, BigDecimal limiteBasico) {
        this.nome = nome;
        this.bandeira = bandeira;
        this.renda = renda;
        this.limiteBasico = limiteBasico;
    }

    public Cartao(CartaoDTO cartaoDTO) {
        this.nome = cartaoDTO.getNome();
        this.bandeira = cartaoDTO.getBandeira();
        this.renda = cartaoDTO.getRenda();
        this.limiteBasico = cartaoDTO.getLimiteBasico();
    }
}

package io.github.rodrigotakeuti.mscartoes.dto;

import io.github.rodrigotakeuti.mscartoes.model.Cartao;
import io.github.rodrigotakeuti.mscartoes.model.CartaoCliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoClienteDTO {

    private String nome;

    private String bandeira;

    private BigDecimal limiteLiberado;

    public static CartaoClienteDTO fromModel (CartaoCliente cartaoCliente) {
        return new CartaoClienteDTO(cartaoCliente.getCartao().getNome(), cartaoCliente.getCartao().getBandeira().name(), cartaoCliente.getLimite());
    }
}

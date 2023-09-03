package io.github.rodrigotakeuti.mscartoes.services;

import io.github.rodrigotakeuti.mscartoes.dto.CartaoDTO;
import io.github.rodrigotakeuti.mscartoes.model.Cartao;
import io.github.rodrigotakeuti.mscartoes.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    @Transactional
    public Cartao save (CartaoDTO cartaoDTO) {
        return cartaoRepository.save(new Cartao(cartaoDTO));
    }

    @Transactional(readOnly = true)
    public List<Cartao> getCartoesRendaMenorIgual(Long renda) {
        var rendaBigDecimal = BigDecimal.valueOf(renda);
        return cartaoRepository.findByRendaLessThanEqual(rendaBigDecimal);
    }

}

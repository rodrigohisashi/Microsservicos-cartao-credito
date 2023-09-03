package io.github.rodrigotakeuti.mscartoes.repository;

import io.github.rodrigotakeuti.mscartoes.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {


    List<Cartao> findByRendaLessThanEqual(BigDecimal rendaBigDecimal);
}

package br.com.rldcarvalho.controlefinanceiroapi.repository;

import br.com.rldcarvalho.controlefinanceiroapi.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    public Optional<Receita> findByDescricaoAndDataBetween(String descricao, LocalDate startDate, LocalDate endDate);
}

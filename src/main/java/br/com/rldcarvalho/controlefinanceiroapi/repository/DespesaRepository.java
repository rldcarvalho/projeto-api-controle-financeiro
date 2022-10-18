package br.com.rldcarvalho.controlefinanceiroapi.repository;

import br.com.rldcarvalho.controlefinanceiroapi.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;


public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    Optional<Despesa> findByDescricaoAndDataBetween(String descricao, LocalDate startDate, LocalDate endDate);
}

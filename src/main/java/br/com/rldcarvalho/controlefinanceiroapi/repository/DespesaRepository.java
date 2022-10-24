package br.com.rldcarvalho.controlefinanceiroapi.repository;

import br.com.rldcarvalho.controlefinanceiroapi.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    Optional<Despesa> findByDescricaoAndDataBetween(String descricao, LocalDate startDate, LocalDate endDate);
    List<Despesa> findByDescricaoContaining(String descricao);
    @Query("SELECT d FROM Despesa d WHERE year(d.data) = :year AND month(d.data) = :month")
    List<Despesa> findAllByMonth(@Param("year") Integer year, @Param("month") Integer month);
}

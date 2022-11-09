package br.com.rldcarvalho.controlefinanceiroapi.repository;

import br.com.rldcarvalho.controlefinanceiroapi.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    Optional<Receita> findByDescricaoAndDataBetween(String descricao, LocalDate startDate, LocalDate endDate);
    Optional<Receita> findById(Long id);
    List<Receita> findByDescricaoContaining(String descricao);
    @Query("SELECT r FROM Receita r WHERE year(r.data) = :year AND month(r.data) = :month")
    List<Receita> findAllByMonth(@Param("year") Integer year, @Param("month") Integer month);
    @Query("SELECT SUM(r.valor) FROM Receita r WHERE year(r.data) = :year AND month(r.data) = :month")
    Optional<BigDecimal> sumByMonth(@Param("year") Integer year, @Param("month") Integer month);
}

package br.com.rldcarvalho.controlefinanceiroapi.repository;

import br.com.rldcarvalho.controlefinanceiroapi.model.Receitas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitasRepository extends JpaRepository<Receitas, Long> {
}

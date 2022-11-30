package br.com.rldcarvalho.controlefinanceiroapi.controller.dto;

import br.com.rldcarvalho.controlefinanceiroapi.repository.DespesaRepository;
import br.com.rldcarvalho.controlefinanceiroapi.repository.ReceitaRepository;

import java.math.BigDecimal;
import java.util.List;

public record ResumoDto(BigDecimal totalReceitas, BigDecimal totalDespesas, BigDecimal saldoFinal, List<CategoriaDto> totalPorCategoria) {

    public ResumoDto(Integer ano, Integer mes, ReceitaRepository receitaRepository, DespesaRepository despesaRepository) {
        this(receitaRepository.sumByMonth(ano, mes).orElse(BigDecimal.ZERO),
                despesaRepository.sumByMonth(ano, mes).orElse(BigDecimal.ZERO),
                subtrairReceitaDespesa(ano, mes, receitaRepository, despesaRepository),
                despesaRepository.sumCategoriaByMonth(ano, mes)
        );
    }
    public static BigDecimal subtrairReceitaDespesa(Integer ano, Integer mes, ReceitaRepository receitaRepository, DespesaRepository despesaRepository){
        BigDecimal totalReceitas = receitaRepository.sumByMonth(ano, mes).orElse(BigDecimal.ZERO);
        BigDecimal totalDespesas = despesaRepository.sumByMonth(ano, mes).orElse(BigDecimal.ZERO);
        return totalReceitas.subtract(totalDespesas);
    }
}

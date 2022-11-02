package br.com.rldcarvalho.controlefinanceiroapi.controller.dto;

import br.com.rldcarvalho.controlefinanceiroapi.repository.DespesaRepository;
import br.com.rldcarvalho.controlefinanceiroapi.repository.ReceitaRepository;

import java.math.BigDecimal;
import java.util.List;

public class ResumoDto {

    private BigDecimal totalReceitas;
    private BigDecimal totalDespesas;
    private BigDecimal saldoFinal;
    private List<CategoriaDto> totalPorCategoria;

    public ResumoDto() {}

    public ResumoDto(Integer ano, Integer mes, ReceitaRepository receitaRepository, DespesaRepository despesaRepository) {
        this.totalReceitas = new BigDecimal(receitaRepository.sumByMonth(ano, mes));
        this.totalDespesas = new BigDecimal(despesaRepository.sumByMonth(ano, mes));
        this.saldoFinal = this.totalReceitas.subtract(this.totalDespesas);
        this.totalPorCategoria = despesaRepository.sumCategoriaByMonth(ano, mes);
    }

    public BigDecimal getTotalReceitas() {
        return totalReceitas;
    }

    public void setTotalReceitas(BigDecimal totalReceitas) {
        this.totalReceitas = totalReceitas;
    }

    public BigDecimal getTotalDespesas() {
        return totalDespesas;
    }

    public void setTotalDespesas(BigDecimal totalDespesas) {
        this.totalDespesas = totalDespesas;
    }

    public BigDecimal getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(BigDecimal saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public List<CategoriaDto> getTotalPorCategoria() {
        return totalPorCategoria;
    }

    public void setTotalPorCategoria(List<CategoriaDto> totalPorCategoria) {
        this.totalPorCategoria = totalPorCategoria;
    }
}

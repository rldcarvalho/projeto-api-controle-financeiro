package br.com.rldcarvalho.controlefinanceiroapi.controller.dto;

import br.com.rldcarvalho.controlefinanceiroapi.model.Categoria;
import br.com.rldcarvalho.controlefinanceiroapi.model.Despesa;
import br.com.rldcarvalho.controlefinanceiroapi.repository.DespesaRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;


public record DespesaDto(
        Long id,
        String descricao,
        BigDecimal valor,
        @JsonFormat(pattern="dd/MM/yyyy")
        LocalDate data,
        @Enumerated(EnumType.STRING)
        Categoria categoria) {

        public DespesaDto(Despesa despesa) {
                this(despesa.getId(), despesa.getDescricao(), despesa.getValor(), despesa.getData(), despesa.getCategoria());
        }

        public static boolean verificaSeDespesaDuplicada(Despesa despesa, DespesaRepository despesaRepository){
                LocalDate dataInicial = despesa.getData().with(TemporalAdjusters.firstDayOfMonth());
                LocalDate dataFinal = despesa.getData().with(TemporalAdjusters.lastDayOfMonth());

                return despesaRepository.findByDescricaoAndDataBetween(despesa.getDescricao(), dataInicial, dataFinal).isPresent();
        }

        public static List<DespesaDto> buscaTodasDespesas(DespesaRepository despesaRepository) {
                List<Despesa> todasDespesas = despesaRepository.findAll();
                return converteParaDto(todasDespesas);
        }

        public static List<DespesaDto> converteParaDto(List<Despesa> todasDespesas) {
                return todasDespesas.stream().map(DespesaDto::new).collect(Collectors.toList());
        }

        public static DespesaDto converteParaDto(Despesa despesa) {
                return new DespesaDto(despesa);
        }

}

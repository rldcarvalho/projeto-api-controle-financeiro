package br.com.rldcarvalho.controlefinanceiroapi.controller.dto;

import br.com.rldcarvalho.controlefinanceiroapi.model.Categoria;
import br.com.rldcarvalho.controlefinanceiroapi.model.Despesa;
import br.com.rldcarvalho.controlefinanceiroapi.repository.DespesaRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
}

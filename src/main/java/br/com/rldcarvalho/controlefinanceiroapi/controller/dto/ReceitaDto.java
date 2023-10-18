package br.com.rldcarvalho.controlefinanceiroapi.controller.dto;

import br.com.rldcarvalho.controlefinanceiroapi.model.Receita;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReceitaDto(
        Long id,
        String descricao,
        BigDecimal valor,
        @JsonFormat(pattern="dd/MM/yyyy")
        LocalDate data) {

    public ReceitaDto(Receita receita) {
        this(receita.getId(), receita.getDescricao(), receita.getValor(), receita.getData());
    }
}



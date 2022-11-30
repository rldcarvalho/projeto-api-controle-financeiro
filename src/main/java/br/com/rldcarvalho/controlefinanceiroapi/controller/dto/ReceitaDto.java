package br.com.rldcarvalho.controlefinanceiroapi.controller.dto;

import br.com.rldcarvalho.controlefinanceiroapi.model.Receita;
import br.com.rldcarvalho.controlefinanceiroapi.repository.ReceitaRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

public record ReceitaDto(
        Long id,
        String descricao,
        BigDecimal valor,
        @JsonFormat(pattern="dd/MM/yyyy")
        LocalDate data) {

    public ReceitaDto(Receita receita) {
        this(receita.getId(), receita.getDescricao(), receita.getValor(), receita.getData());
    }

    public static List<ReceitaDto> converter(List<Receita> receitas) {
        return receitas.stream().map(ReceitaDto::new).collect(Collectors.toList());
    }

    public static List<ReceitaDto> buscaTodasReceitas(ReceitaRepository receitaRepository){

        List<Receita> receitas = receitaRepository.findAll();

        return ReceitaDto.converter(receitas);
    }

    public static boolean verificaSeReceitaDuplicada(Receita receitaNova, ReceitaRepository receitaRepository){

        LocalDate dataInicial = receitaNova.getData().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dataFinal = receitaNova.getData().with(TemporalAdjusters.lastDayOfMonth());

        return receitaRepository
                .findByDescricaoAndDataBetween(receitaNova.getDescricao(), dataInicial, dataFinal)
                .isPresent();
    }
}



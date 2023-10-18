package br.com.rldcarvalho.controlefinanceiroapi.services;

import br.com.rldcarvalho.controlefinanceiroapi.controller.dto.ReceitaDto;
import br.com.rldcarvalho.controlefinanceiroapi.model.Receita;
import br.com.rldcarvalho.controlefinanceiroapi.repository.ReceitaRepository;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

public class ReceitaService {

    public static boolean verificaSeReceitaDuplicada(Receita receitaNova, ReceitaRepository receitaRepository) {

        LocalDate dataInicial = receitaNova.getData().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dataFinal = receitaNova.getData().with(TemporalAdjusters.lastDayOfMonth());

        return receitaRepository
                .findByDescricaoAndDataBetween(receitaNova.getDescricao(), dataInicial, dataFinal)
                .isPresent();
    }

    public static List<ReceitaDto> converterParaDtoList(List<Receita> receitas){
        return receitas.stream().map(ReceitaDto::new).collect(Collectors.toList());
    }
}

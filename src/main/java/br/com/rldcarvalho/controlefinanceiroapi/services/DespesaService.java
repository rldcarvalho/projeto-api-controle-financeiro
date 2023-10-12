package br.com.rldcarvalho.controlefinanceiroapi.services;

import br.com.rldcarvalho.controlefinanceiroapi.controller.dto.DespesaDto;
import br.com.rldcarvalho.controlefinanceiroapi.model.Despesa;
import br.com.rldcarvalho.controlefinanceiroapi.repository.DespesaRepository;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

public class DespesaService {

    public static boolean verificaSeDespesaDuplicada(Despesa despesa, DespesaRepository despesaRepository){
        LocalDate dataInicial = despesa.getData().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dataFinal = despesa.getData().with(TemporalAdjusters.lastDayOfMonth());

        return despesaRepository.findByDescricaoAndDataBetween(despesa.getDescricao(), dataInicial, dataFinal).isPresent();
    }

    public static List<DespesaDto> converteParaDto(List<Despesa> todasDespesas) {
        return todasDespesas.stream().map(DespesaDto::new).collect(Collectors.toList());
    }
}

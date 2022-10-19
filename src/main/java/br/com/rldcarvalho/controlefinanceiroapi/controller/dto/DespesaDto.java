package br.com.rldcarvalho.controlefinanceiroapi.controller.dto;

import br.com.rldcarvalho.controlefinanceiroapi.model.Despesa;
import br.com.rldcarvalho.controlefinanceiroapi.repository.DespesaRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

public class DespesaDto {

    @JsonIgnore
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DespesaDto() {}

    public DespesaDto(Despesa despesa) {
        this.id = despesa.getId();
        this.descricao = despesa.getDescricao();
        this.valor = despesa.getValor();
        this.data = despesa.getData();
    }

    public DespesaDto(String descricao, BigDecimal valor, LocalDate data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public static boolean verificaSeDespesaDuplicada(Despesa despesa, DespesaRepository despesaRepository){
        LocalDate dataInicial = despesa.getData().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dataFinal = despesa.getData().with(TemporalAdjusters.lastDayOfMonth());

        return despesaRepository.findByDescricaoAndDataBetween(despesa.getDescricao(), dataInicial, dataFinal).isPresent();
    }

    public static List<DespesaDto> buscaTodasDespesas(DespesaRepository despesaRepository) {
        List<Despesa> todasDespesas = despesaRepository.findAll();
        List<DespesaDto> todasDespesasDto = converteParaDto(todasDespesas);
        return todasDespesasDto;
    }

    private static List<DespesaDto> converteParaDto(List<Despesa> todasDespesas) {
        return todasDespesas.stream().map(DespesaDto::new).collect(Collectors.toList());
    }


}

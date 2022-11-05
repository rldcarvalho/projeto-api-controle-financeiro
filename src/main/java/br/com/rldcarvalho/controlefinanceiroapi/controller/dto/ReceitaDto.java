package br.com.rldcarvalho.controlefinanceiroapi.controller.dto;

import br.com.rldcarvalho.controlefinanceiroapi.model.Receita;
import br.com.rldcarvalho.controlefinanceiroapi.repository.ReceitaRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

public class ReceitaDto {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate data;

    public ReceitaDto(Receita receita) {
        this.id = receita.getId();
        this.descricao = receita.getDescricao();
        this.valor = receita.getValor();
        this.data = receita.getData();
    }

    public ReceitaDto(String descricao, BigDecimal valor, LocalDate data) {
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

    public static List<ReceitaDto> converter(List<Receita> receitas) {
        return receitas.stream().map(ReceitaDto::new).collect(Collectors.toList());
    }

    public Receita converterParaModel(){
        return new Receita(this.id, this.descricao, this.valor, this.data);
    }

    public static ReceitaDto converterParaDto(Receita receita){
        return new ReceitaDto(receita);
    }

    public static List<ReceitaDto> buscaTodasReceitas(ReceitaRepository receitaRepository){

        List<Receita> receitas = receitaRepository.findAll();

        return ReceitaDto.converter(receitas);
    }

    public static boolean verificaSeReceitaDuplicada(ReceitaDto receitaNova, ReceitaRepository receitaRepository){

        LocalDate dataInicial = receitaNova.getData().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dataFinal = receitaNova.getData().with(TemporalAdjusters.lastDayOfMonth());

        return receitaRepository
                .findByDescricaoAndDataBetween(receitaNova.getDescricao(), dataInicial, dataFinal)
                .isPresent();
    }

    public static boolean verificaSeReceitaDuplicada(Receita receitaNova, ReceitaRepository receitaRepository){

        LocalDate dataInicial = receitaNova.getData().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dataFinal = receitaNova.getData().with(TemporalAdjusters.lastDayOfMonth());

        return receitaRepository
                .findByDescricaoAndDataBetween(receitaNova.getDescricao(), dataInicial, dataFinal)
                .isPresent();
    }

    @Override
    public String toString() {
        return "ReceitasDto{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", data=" + data +
                '}';
    }
}

package br.com.rldcarvalho.controlefinanceiroapi.controller.dto;

import br.com.rldcarvalho.controlefinanceiroapi.model.Receita;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReceitaDto {

    @JsonIgnore
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;

    public ReceitaDto(Receita receita) {
        this.id = receita.getId();
        this.descricao = receita.getDescricao();
        this.valor = receita.getValor();
        this.data = receita.getData();
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

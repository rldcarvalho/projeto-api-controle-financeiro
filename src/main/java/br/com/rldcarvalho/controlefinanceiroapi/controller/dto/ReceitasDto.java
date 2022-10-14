package br.com.rldcarvalho.controlefinanceiroapi.controller.dto;

import br.com.rldcarvalho.controlefinanceiroapi.model.Receitas;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReceitasDto {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;

    public ReceitasDto(Receitas receita) {
        this.id = receita.getId();
        this.descricao = receita.getDescricao();
        this.valor = receita.getValor();
        this.data = receita.getData();
    }

    public List<ReceitasDto> convert(List<Receitas> receitas) {
        return receitas.stream().map(ReceitasDto::new).collect(Collectors.toList());
    }
}

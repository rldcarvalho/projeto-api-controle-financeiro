package br.com.rldcarvalho.controlefinanceiroapi.controller.dto;

import br.com.rldcarvalho.controlefinanceiroapi.model.Receita;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReceitaDto {

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

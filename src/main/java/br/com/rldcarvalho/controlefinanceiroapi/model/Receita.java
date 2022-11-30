package br.com.rldcarvalho.controlefinanceiroapi.model;

import br.com.rldcarvalho.controlefinanceiroapi.controller.dto.ReceitaDto;
import br.com.rldcarvalho.controlefinanceiroapi.repository.ReceitaRepository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Receita {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;

    public Receita(String descricao, BigDecimal valor, LocalDate data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public Receita(Long id, String descricao, BigDecimal valor, LocalDate data) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public Receita() {
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void atualizar(Receita receitaNova) {
        this.descricao = receitaNova.getDescricao();
        this.valor = receitaNova.getValor();
        this.data = receitaNova.getData();
    }
}

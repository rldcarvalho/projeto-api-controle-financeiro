package br.com.rldcarvalho.controlefinanceiroapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
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

    public void atualizar(Receita receitaNova) {
        this.descricao = receitaNova.getDescricao();
        this.valor = receitaNova.getValor();
        this.data = receitaNova.getData();
    }
}

package br.com.rldcarvalho.controlefinanceiroapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Despesa {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Despesa(String descricao, BigDecimal valor, LocalDate data, Categoria categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }

    public void atualizar(Despesa despesaNova) {
        this.descricao = despesaNova.getDescricao();
        this.valor = despesaNova.getValor();
        this.data = despesaNova.getData();
        this.categoria = despesaNova.getCategoria();
    }
}

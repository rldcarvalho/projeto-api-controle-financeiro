package br.com.rldcarvalho.controlefinanceiroapi.model;

import br.com.rldcarvalho.controlefinanceiroapi.controller.form.ReceitaForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public Receita(ReceitaForm receitaForm){
        BigDecimal valorFormatado = new BigDecimal(receitaForm.getValor());
        LocalDate dataFormatada = LocalDate.parse(receitaForm.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        this.descricao = receitaForm.getDescricao();
        this.valor = valorFormatado;
        this.data = dataFormatada;
    }

    public void atualizar(Receita receitaNova) {
        this.descricao = receitaNova.getDescricao();
        this.valor = receitaNova.getValor();
        this.data = receitaNova.getData();
    }
}

package br.com.rldcarvalho.controlefinanceiroapi.model;

import br.com.rldcarvalho.controlefinanceiroapi.controller.form.DespesaForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public Despesa(DespesaForm despesaForm){
        BigDecimal valorFormatado = new BigDecimal(despesaForm.getValor());
        LocalDate dataFormatada = LocalDate.parse(despesaForm.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        this.descricao = despesaForm.getDescricao();
        this.valor = valorFormatado;
        this.data = dataFormatada;
        this.categoria = despesaForm.getCategoria();
    }

    public void atualizar(Despesa despesaNova) {
        this.descricao = despesaNova.descricao;
        this.valor = despesaNova.valor;
        this.data = despesaNova.data;
        if (!despesaNova.categoria.getDescricao().equalsIgnoreCase("Outras")){
            this.categoria = despesaNova.categoria;
        }
    }
}

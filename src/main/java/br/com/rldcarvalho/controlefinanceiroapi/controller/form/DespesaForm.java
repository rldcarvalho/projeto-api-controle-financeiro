package br.com.rldcarvalho.controlefinanceiroapi.controller.form;

import br.com.rldcarvalho.controlefinanceiroapi.model.Categoria;
import br.com.rldcarvalho.controlefinanceiroapi.model.Despesa;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class DespesaForm {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @NotEmpty(message = "Uma descrição da despesa deve ser informada")
    private String descricao;
    @Pattern(regexp = "\\d+\\.\\d{2}")
    @NotEmpty(message = "Um valor para despesa deve ser informado no formato 0.00")
    private String valor;
    @Pattern(regexp = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/[0-9][0-9][0-9][0-9]$")
    @NotEmpty(message = "Uma data da despesa deve ser informada no formato dd/mm/yyyy")
    private String data;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public DespesaForm(String descricao, String valor, String data, Categoria categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
        if (categoria == null) {
            this.categoria = Categoria.OUTRAS;
        }
    }

    public Despesa converterParaModel(){
        BigDecimal valorFormatado = new BigDecimal(this.valor);
        LocalDate dataFormatada = LocalDate.parse(this.data, formatter);

        return new Despesa(this.descricao, valorFormatado, dataFormatada, this.categoria);
    }
}

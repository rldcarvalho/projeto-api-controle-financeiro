package br.com.rldcarvalho.controlefinanceiroapi.controller.form;

import br.com.rldcarvalho.controlefinanceiroapi.controller.dto.ReceitaDto;
import br.com.rldcarvalho.controlefinanceiroapi.model.Receita;
import com.sun.istack.NotNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReceitaForm {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @NotEmpty(message = "Uma descrição da receita deve ser informada")
    private String descricao;
    @Pattern(regexp = "\\d+\\.\\d{2}")
    @NotEmpty(message = "Um valor para receita deve ser informado no formato 0.00")
    private String valor;
    @Pattern(regexp = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$")
    @NotEmpty(message = "Uma data da receita deve ser informada no formato dd/mm/yyyy")
    private String data;

    public ReceitaForm(String descricao, String valor, String data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Receita converterParaModel(){
        BigDecimal valorFormatado = new BigDecimal(this.valor);
        LocalDate dataFormatada = LocalDate.parse(this.data, formatter);

        return new Receita(this.descricao, valorFormatado, dataFormatada);
    }

    public ReceitaDto converterParaDto(){
        BigDecimal valorFormatado = new BigDecimal(this.valor);
        LocalDate dataFormatada = LocalDate.parse(this.data, formatter);

        return new ReceitaDto(this.descricao, valorFormatado, dataFormatada);
    }
}

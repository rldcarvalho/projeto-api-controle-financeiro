package br.com.rldcarvalho.controlefinanceiroapi.controller.form;

import br.com.rldcarvalho.controlefinanceiroapi.model.Receitas;
import com.sun.istack.NotNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReceitasForm {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @NotNull @NotEmpty
    private String descricao;
    @Pattern(regexp = "\\d+\\.\\d{2}")
    @NotNull @NotEmpty
    private String valor;
    @Pattern(regexp = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$")
    @NotNull @NotEmpty
    private String data;

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

    public Receitas converter(){
        BigDecimal valorFormatado = new BigDecimal(this.valor);
        LocalDate dataFormatada = LocalDate.parse(this.data, formatter);

        return new Receitas(this.descricao, valorFormatado, dataFormatada);
    }
}

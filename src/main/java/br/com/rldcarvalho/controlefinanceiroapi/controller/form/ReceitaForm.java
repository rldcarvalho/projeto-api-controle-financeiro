package br.com.rldcarvalho.controlefinanceiroapi.controller.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReceitaForm {

    @NotEmpty(message = "Uma descrição da receita deve ser informada")
    private String descricao;
    @Pattern(regexp = "\\d+\\.\\d{2}")
    @NotEmpty(message = "Um valor para receita deve ser informado no formato 0.00")
    private String valor;
    @Pattern(regexp = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9][0-9][0-9])$")
    @NotEmpty(message = "Uma data da receita deve ser informada no formato dd/mm/yyyy")
    private String data;

}

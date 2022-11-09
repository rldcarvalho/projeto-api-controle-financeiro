package br.com.rldcarvalho.controlefinanceiroapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

@JsonPropertyOrder(alphabetic = true)
public interface CategoriaDto {

    @Value("#{target.categoria.descricao}")
    String getCategoria();
    BigDecimal getValor();

}

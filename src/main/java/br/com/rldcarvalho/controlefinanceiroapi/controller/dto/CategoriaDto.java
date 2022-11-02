package br.com.rldcarvalho.controlefinanceiroapi.controller.dto;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface CategoriaDto {

    @Value("#{target.categoria.descricao}")
    String getCategoria();
    BigDecimal getValor();

}

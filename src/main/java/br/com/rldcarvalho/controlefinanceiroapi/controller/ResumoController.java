package br.com.rldcarvalho.controlefinanceiroapi.controller;

import br.com.rldcarvalho.controlefinanceiroapi.controller.dto.ResumoDto;
import br.com.rldcarvalho.controlefinanceiroapi.repository.DespesaRepository;
import br.com.rldcarvalho.controlefinanceiroapi.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resumo")
public class ResumoController {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    @GetMapping("/{ano}/{mes}")
    public ResumoDto mostraResumoMensal(@PathVariable Integer ano, @PathVariable Integer mes){

        return new ResumoDto(ano, mes, receitaRepository, despesaRepository);
    }
}

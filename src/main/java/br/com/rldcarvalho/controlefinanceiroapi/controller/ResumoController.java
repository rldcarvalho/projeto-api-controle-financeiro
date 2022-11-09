package br.com.rldcarvalho.controlefinanceiroapi.controller;

import br.com.rldcarvalho.controlefinanceiroapi.controller.dto.ResumoDto;
import br.com.rldcarvalho.controlefinanceiroapi.repository.DespesaRepository;
import br.com.rldcarvalho.controlefinanceiroapi.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.DateTimeException;
import java.time.LocalDate;

@RestController
@RequestMapping("/resumo")
public class ResumoController {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity<ResumoDto> mostraResumoMensal(@PathVariable Integer ano, @PathVariable Integer mes){

        try {
            LocalDate dataValida = LocalDate.of(ano, mes, 1);
        }catch (DateTimeException e){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new ResumoDto(ano, mes, receitaRepository, despesaRepository));
    }
}

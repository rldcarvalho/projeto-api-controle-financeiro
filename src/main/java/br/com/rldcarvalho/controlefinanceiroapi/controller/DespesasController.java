package br.com.rldcarvalho.controlefinanceiroapi.controller;

import br.com.rldcarvalho.controlefinanceiroapi.controller.dto.DespesaDto;
import br.com.rldcarvalho.controlefinanceiroapi.controller.form.DespesaForm;
import br.com.rldcarvalho.controlefinanceiroapi.model.Despesa;
import br.com.rldcarvalho.controlefinanceiroapi.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/despesas")
public class DespesasController {

    @Autowired
    DespesaRepository despesaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastraDespesa(@RequestBody @Valid DespesaForm despesaForm){
        Despesa despesa = despesaForm.converterParaModel();

        if(DespesaDto.verificaSeDespesaDuplicada(despesa, despesaRepository)){
            return ResponseEntity.badRequest().body("Despesa com descrição idêntica existente no mesmo mês");
        }

        despesaRepository.save(despesa);

        return ResponseEntity.ok("Despesa cadastrada com sucesso");
    }
}

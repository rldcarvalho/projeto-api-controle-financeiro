package br.com.rldcarvalho.controlefinanceiroapi.controller;

import br.com.rldcarvalho.controlefinanceiroapi.controller.dto.ReceitasDto;
import br.com.rldcarvalho.controlefinanceiroapi.controller.form.ReceitasForm;
import br.com.rldcarvalho.controlefinanceiroapi.model.Receitas;
import br.com.rldcarvalho.controlefinanceiroapi.repository.ReceitasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/receitas")
public class ReceitasController {

    @Autowired
    private ReceitasRepository receitasRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid ReceitasForm receitasForm, UriComponentsBuilder uriBuilder){
        Receitas receita = receitasForm.converter();

        //TODO: verifica receita duplicada

        receitasRepository.save(receita);
    }


//    @PostMapping
//    @Transactional
//    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){
//        Topico topico =  form.converter(cursoRepository);
//        topicoRepository.save(topico);
//
//        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
//        return ResponseEntity.created(uri).body(new TopicoDto(topico));
//    }
}

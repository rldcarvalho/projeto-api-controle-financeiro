package br.com.rldcarvalho.controlefinanceiroapi.controller;

import br.com.rldcarvalho.controlefinanceiroapi.controller.dto.ReceitaDto;
import br.com.rldcarvalho.controlefinanceiroapi.controller.form.ReceitaForm;
import br.com.rldcarvalho.controlefinanceiroapi.model.Receita;
import br.com.rldcarvalho.controlefinanceiroapi.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/receitas")
public class ReceitasController {

    @Autowired
    private ReceitaRepository receitaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid ReceitaForm receitaForm){
        Receita receita = receitaForm.converter();

        if(verificaSeReceitaDuplicada(receita)){
            return ResponseEntity.badRequest().body("Receita com descrição idêntica existente no mesmo mês");
        }

        receitaRepository.save(receita);
        return ResponseEntity.ok("Receita cadastrada com sucesso");
    }

    public boolean verificaSeReceitaDuplicada(Receita receitaNova){

        LocalDate dataInicial = receitaNova.getData().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dataFinal = receitaNova.getData().with(TemporalAdjusters.lastDayOfMonth());

        return receitaRepository
                .findByDescricaoAndDataBetween(receitaNova.getDescricao(), dataInicial, dataFinal)
                .isPresent();
    }

    public List<ReceitaDto> buscaTodasReceitas(){

        List<Receita> receitas = receitaRepository.findAll();

        return ReceitaDto.converter(receitas);
    }

    @GetMapping
    public List<ReceitaDto> mostraReceita(){
        return buscaTodasReceitas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaDto> mostraReceitaPorId(@PathVariable Long id){
        Optional<Receita> receita = receitaRepository.findById(id);

        if (!receita.isPresent()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ReceitaDto(receita.get()));
    }

}

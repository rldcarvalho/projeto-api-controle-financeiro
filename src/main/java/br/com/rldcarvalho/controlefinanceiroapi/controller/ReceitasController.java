package br.com.rldcarvalho.controlefinanceiroapi.controller;

import br.com.rldcarvalho.controlefinanceiroapi.controller.dto.ReceitaDto;
import br.com.rldcarvalho.controlefinanceiroapi.controller.form.ReceitaForm;
import br.com.rldcarvalho.controlefinanceiroapi.model.Receita;
import br.com.rldcarvalho.controlefinanceiroapi.repository.ReceitaRepository;
import br.com.rldcarvalho.controlefinanceiroapi.services.ReceitaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/receitas")
public class ReceitasController {

    @Autowired
    private ReceitaRepository receitaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastraReceita(@RequestBody @Valid ReceitaForm receitaForm){
        Receita receita = new Receita(receitaForm);

        if(ReceitaService.verificaSeReceitaDuplicada(receita, receitaRepository)){
            return ResponseEntity.badRequest().body("Receita com descrição idêntica existente no mesmo mês");
        }

        receitaRepository.save(receita);

        return ResponseEntity.ok("Receita cadastrada com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<ReceitaDto>> buscaReceita(@RequestParam(required = false) String descricao){
        Optional<List<Receita>> receitasOptional;

        if (descricao == null || descricao.isEmpty()) {
            receitasOptional = Optional.of(receitaRepository.findAll());
        } else {
            receitasOptional = receitaRepository.findByDescricaoContainingIgnoreCase(descricao);
        }

        List<ReceitaDto> receitasDto = ReceitaService.converterParaDtoList(receitasOptional.orElse(Collections.emptyList()));

        return ResponseEntity.ok(receitasDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaDto> buscaReceitaPorId(@PathVariable Long id){
        Optional<Receita> receita = receitaRepository.findById(id);

        if (receita.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ReceitaDto(receita.get()));
    }

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity<List<ReceitaDto>> buscaReceitaPorMes(@PathVariable Integer ano, @PathVariable Integer mes){
        List<Receita> receitasPorMes = receitaRepository.findAllByMonth(ano, mes);

        if (receitasPorMes.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ReceitaService.converterParaDtoList(receitasPorMes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizaReceita(@RequestBody @Valid ReceitaForm receitaForm, @PathVariable Long id){
        Optional<Receita> receitaAtual = receitaRepository.findById(id);

        if(receitaAtual.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        try {
            Receita receitaNova = new Receita(receitaForm);

            boolean receitasTemNomesDiferentes = !receitaNova.getDescricao().equals(receitaAtual.get().getDescricao());
            if(receitasTemNomesDiferentes && ReceitaService.verificaSeReceitaDuplicada(receitaNova, receitaRepository)){
                return ResponseEntity.badRequest().body("Receita com descrição idêntica existente no mesmo mês");
            }

            receitaAtual.get().atualizar(receitaNova);
            receitaRepository.save(receitaAtual.get());

            return ResponseEntity.ok("Receita atualizada com sucesso.");

        }catch (DateTimeException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletaReceita(@PathVariable Long id){
        Optional<Receita> receita = receitaRepository.findById(id);

        if (receita.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        receitaRepository.deleteById(id);

        return ResponseEntity.ok("Receita deletada com sucesso");
    }

}

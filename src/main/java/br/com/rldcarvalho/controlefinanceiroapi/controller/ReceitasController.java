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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/receitas")
public class ReceitasController {

    @Autowired
    private ReceitaRepository receitaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastraReceita(@RequestBody @Valid ReceitaForm receitaForm){
        Receita receita = receitaForm.converterParaModel();

        if(ReceitaDto.verificaSeReceitaDuplicada(receita, receitaRepository)){
            return ResponseEntity.badRequest().body("Receita com descrição idêntica existente no mesmo mês");
        }

        receitaRepository.save(receita);
        return ResponseEntity.ok("Receita cadastrada com sucesso");
    }

    @GetMapping
    public List<ReceitaDto> mostraReceita(@RequestParam Optional<String> descricao){
        if (descricao.isEmpty()){
            return ReceitaDto.buscaTodasReceitas(receitaRepository);
        }
        List<Receita> receita = receitaRepository.findByDescricaoContaining(descricao.get());

        return ReceitaDto.converter(receita);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaDto> mostraReceitaPorId(@PathVariable Long id){
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

        return ResponseEntity.ok(ReceitaDto.converter(receitasPorMes));
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizaReceita(@RequestBody @Valid ReceitaForm receitaForm, @PathVariable Long id){

        Optional<Receita> receitaAtual = receitaRepository.findById(id);

        if(receitaAtual.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        ReceitaDto receitaNovaDto = receitaForm.converterParaDto();

        boolean receitasTemNomesDiferentes = !receitaNovaDto.getDescricao().equals(receitaAtual.get().getDescricao());
        if(receitasTemNomesDiferentes && ReceitaDto.verificaSeReceitaDuplicada(receitaNovaDto, receitaRepository)){
            return ResponseEntity.badRequest().body("Receita com descrição idêntica existente no mesmo mês");
        }

        receitaNovaDto.setId(id);

        receitaRepository.save(receitaNovaDto.converterParaModel());

        return ResponseEntity.ok("Receita atualizada com sucesso.");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletaReceita(@PathVariable Long id){
        Optional<Receita> receita = receitaRepository.findById(id);

        if (receita.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        receitaRepository.delete(receita.get());

        return ResponseEntity.ok("Receita deletada com sucesso");
    }



}

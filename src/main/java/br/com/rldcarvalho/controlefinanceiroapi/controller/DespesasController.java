package br.com.rldcarvalho.controlefinanceiroapi.controller;

import br.com.rldcarvalho.controlefinanceiroapi.controller.dto.DespesaDto;
import br.com.rldcarvalho.controlefinanceiroapi.controller.form.DespesaForm;
import br.com.rldcarvalho.controlefinanceiroapi.model.Despesa;
import br.com.rldcarvalho.controlefinanceiroapi.repository.DespesaRepository;
import br.com.rldcarvalho.controlefinanceiroapi.services.DespesaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/despesas")
public class DespesasController {

    @Autowired
    DespesaRepository despesaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastraDespesa(@RequestBody @Valid DespesaForm despesaForm){
        Despesa despesa = new Despesa(despesaForm);

        if(DespesaService.verificaSeDespesaDuplicada(despesa, despesaRepository)){
            return ResponseEntity.badRequest().body("Despesa com descrição idêntica existente no mesmo mês");
        }

        despesaRepository.save(despesa);

        return ResponseEntity.ok("Despesa cadastrada com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<DespesaDto>> mostraDespesa(@RequestParam(required = false) String descricao) {
        List<Despesa> despesaList;

        if (descricao == null || descricao.isEmpty()) {
            despesaList = despesaRepository.findAll();
        } else {
            despesaList = despesaRepository.findByDescricaoContaining(descricao);
        }

        List<DespesaDto> despesaDtoList = DespesaService.converteParaDto(despesaList);

        return ResponseEntity.ok(despesaDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaDto> buscaDespesaPorId(@PathVariable Long id){
        Despesa despesa = despesaRepository.findById(id).orElse(null);

        if (despesa == null) {
            return ResponseEntity.notFound().build();
        }

        DespesaDto despesaDto = new DespesaDto(despesa);
        return ResponseEntity.ok(despesaDto);
    }

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity<List<DespesaDto>> buscaDespesaPorMes(@PathVariable Integer ano,@PathVariable Integer mes){
        List<Despesa> despesaPorMes = despesaRepository.findAllByMonth(ano, mes);

        if (despesaPorMes.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(DespesaService.converteParaDto(despesaPorMes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizaReceita(@PathVariable Long id, @RequestBody @Valid DespesaForm despesaForm){
        Optional<Despesa> despesaAtual = despesaRepository.findById(id);

        if(despesaAtual.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Despesa despesaNova;

        try {
            despesaNova = new Despesa(despesaForm);
        }catch (DateTimeException e){
            return ResponseEntity.badRequest().build();
        }

        boolean despesasTemNomesDiferentes = !despesaNova.getDescricao().equals(despesaAtual.get().getDescricao());
        if(despesasTemNomesDiferentes && DespesaService.verificaSeDespesaDuplicada(despesaNova, despesaRepository)){
            return ResponseEntity.badRequest().body("Receita com descrição idêntica existente no mesmo mês.");
        }

        despesaAtual.get().atualizar(despesaNova);
        despesaRepository.save(despesaAtual.get());

        return ResponseEntity.ok("Despesa atualizada com sucesso.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletaDespesa(@PathVariable Long id){
        Optional<Despesa> despesa = despesaRepository.findById(id);

        if(despesa.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        despesaRepository.delete(despesa.get());

        return ResponseEntity.ok("Despesa deletada com sucesso.");
    }

}

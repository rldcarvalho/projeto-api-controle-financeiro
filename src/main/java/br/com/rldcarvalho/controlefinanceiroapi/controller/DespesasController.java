package br.com.rldcarvalho.controlefinanceiroapi.controller;

import br.com.rldcarvalho.controlefinanceiroapi.controller.dto.DespesaDto;
import br.com.rldcarvalho.controlefinanceiroapi.controller.form.DespesaForm;
import br.com.rldcarvalho.controlefinanceiroapi.model.Despesa;
import br.com.rldcarvalho.controlefinanceiroapi.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @GetMapping
    public List<DespesaDto> mostraDespesa(){
        return DespesaDto.buscaTodasDespesas(despesaRepository);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaDto> buscaDespesaPorId(@PathVariable Long id){
        Optional<Despesa> despesa = despesaRepository.findById(id);

        if(despesa.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        DespesaDto despesaDto = DespesaDto.converteParaDto(despesa.get());
        return ResponseEntity.ok(despesaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizaReceita(@PathVariable Long id, @RequestBody DespesaForm despesaForm){
        Optional<Despesa> despesaAntiga = despesaRepository.findById(id);

        if(despesaAntiga.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Despesa despesaNova = despesaForm.converterParaModel();

        boolean despesasTemNomesDiferentes = !despesaNova.getDescricao().equals(despesaAntiga.get().getDescricao());
        if(despesasTemNomesDiferentes && DespesaDto.verificaSeDespesaDuplicada(despesaNova, despesaRepository)){
            return ResponseEntity.badRequest().body("Receita com descrição idêntica existente no mesmo mês.");
        }

        despesaNova.setId(id);

        despesaRepository.save(despesaNova);

        return ResponseEntity.ok("Despesa atualizada com sucesso.");


    }
}

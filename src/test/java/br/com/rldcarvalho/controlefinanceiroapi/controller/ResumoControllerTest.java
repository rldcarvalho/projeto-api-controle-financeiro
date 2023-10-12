package br.com.rldcarvalho.controlefinanceiroapi.controller;

import br.com.rldcarvalho.controlefinanceiroapi.controller.form.DespesaForm;
import br.com.rldcarvalho.controlefinanceiroapi.controller.form.ReceitaForm;
import br.com.rldcarvalho.controlefinanceiroapi.model.Categoria;
import br.com.rldcarvalho.controlefinanceiroapi.model.Despesa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import jakarta.transaction.Transactional;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ResumoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TestEntityManager em;

    @Test
    void deveriaRetornarOkEOsValoresCorretos() throws Exception {

        povoaDatabase();

        URI uri = new URI("/resumo/2022/11");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isOk())
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        String jsonExpectedResult = "{\"totalReceitas\":3200.00,\"totalDespesas\":3217.00,\"saldoFinal\":-17.00,\"totalPorCategoria\":[{\"categoria\":\"Educação\",\"valor\":600.00},{\"categoria\":\"Lazer\",\"valor\":140.00},{\"categoria\":\"Moradia\",\"valor\":1729.00},{\"categoria\":\"Outras\",\"valor\":49.00},{\"categoria\":\"Transporte\",\"valor\":699.00}]}";

        assertEquals(jsonExpectedResult, jsonResult);

    }

    @Test
    void deveriaRetornarOkAoBuscarMesSemResultados() throws Exception {

        URI uri = new URI("/resumo/2022/10");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isOk())
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        String jsonExpectedResult = "{\"totalReceitas\":0,\"totalDespesas\":0,\"saldoFinal\":0,\"totalPorCategoria\":[]}";

        assertEquals(jsonExpectedResult, jsonResult);
    }

    @Test
    void deveriaRetornarBadRequestAoBuscarMesInvalido() throws Exception {

        URI uri = new URI("/resumo/2022/15");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isBadRequest());
    }

    void povoaDatabase(){
        cadastraReceita("Salário", "2800.00", "08/11/2022");
        cadastraReceita("Renda Extra", "400.00", "08/11/2022");
        cadastraDespesa("Aluguel", "1400.00", "08/11/2022", Categoria.MORADIA);
        cadastraDespesa("Prestação Carro", "699.00", "08/11/2022", Categoria.TRANSPORTE);
        cadastraDespesa("Água", "90.00", "08/11/2022", Categoria.MORADIA);
        cadastraDespesa("Luz", "140.00", "08/11/2022", Categoria.MORADIA);
        cadastraDespesa("Internet", "99.00", "08/11/2022", Categoria.MORADIA);
        cadastraDespesa("Celular", "49.00", "08/11/2022", Categoria.OUTRAS);
        cadastraDespesa("Academia", "140.00", "08/11/2022", Categoria.LAZER);
        cadastraDespesa("Escola Particular", "600.00", "08/11/2022", Categoria.EDUCACAO);
    }

    void cadastraReceita(String descricao, String valor, String data){

        ReceitaForm receita = new ReceitaForm(descricao, valor, data);
        em.persist(receita.converterParaModel());
    }

    void cadastraDespesa(String descricao, String valor, String data, Categoria categoria){

        DespesaForm despesa = new DespesaForm(descricao, valor, data, categoria);
        em.persist(new Despesa(despesa));
    }
}
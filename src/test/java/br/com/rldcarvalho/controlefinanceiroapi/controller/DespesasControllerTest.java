package br.com.rldcarvalho.controlefinanceiroapi.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class DespesasControllerTest {

    @Autowired
    MockMvc mockMvc;

    String jsonElement1 = "{\"descricao\":\"DespesaTeste1\",\"valor\":\"1400.00\",\"data\":\"10/10/2022\",\"categoria\":\"MORADIA\"}";
    String jsonExpectedElement1 = "{\"id\":1,\"descricao\":\"DespesaTeste1\",\"valor\":1400.00,\"data\":\"10/10/2022\",\"categoria\":\"MORADIA\"}";

    String jsonElement2 = "{\"descricao\":\"DespesaTeste2\",\"valor\":\"500.00\",\"data\":\"10/10/2022\",\"categoria\":\"TRANSPORTE\"}";
    String jsonExpectedElement2 = "{\"id\":2,\"descricao\":\"DespesaTeste2\",\"valor\":500.00,\"data\":\"10/10/2022\",\"categoria\":\"TRANSPORTE\"}";

    String jsonElement3 = "{\"descricao\":\"DespesaTeste3\",\"valor\":\"100.00\",\"data\":\"10/10/2022\"}";
    String jsonExpectedElement3 = "{\"id\":3,\"descricao\":\"DespesaTeste3\",\"valor\":100.00,\"data\":\"10/10/2022\",\"categoria\":\"OUTRAS\"}";

    String jsonExpectedAllElements = "[" + jsonExpectedElement1 + "," + jsonExpectedElement2 + "," + jsonExpectedElement3 + "]";

    @Test
    @Order(1)
    void deveriaRetornarOkAoCriarDespesas() throws Exception {

        URI uri = new URI("/despesas");

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(jsonElement1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(jsonElement2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(jsonElement3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isOk());
    }

    @Test
    @Order(2)
    void deveriaAtribuirOutrasElementoSemCategoria() throws Exception {

        URI uri = new URI("/despesas/3");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isOk())
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString();

        assertEquals(jsonExpectedElement3, jsonResult);
    }

    @Test
    @Order(3)
    void deveriaRetornarBadRequestAoInserirDespesaDuplicada() throws Exception {

        URI uri = new URI("/despesas");

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(jsonElement1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isBadRequest());
    }

    @Test
    @Order(4)
    void deveriaRetornarBadRequestAoInserirDespesaFaltandoAtributo() throws Exception {

        URI uri = new URI("/despesas");

        String json = "{\"descricao\":\"DespesaTeste4\",\"valor\":\"1400.00\",\"categoria\":\"MORADIA\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isBadRequest());
    }

    @Test
    @Order(5)
    void deveriaRetornarBadRequestAoInserirDespesaComDataNoFormatoErrado() throws Exception {

        URI uri = new URI("/despesas");

        String json1 = "{\"descricao\":\"DespesaTeste4\",\"valor\":\"40.00\",\"data\":\"10/15/2022\",\"categoria\":\"OUTRAS\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isBadRequest());

        String json2 = "{\"descricao\":\"DespesaTeste4\",\"valor\":\"40.00\",\"data\":\"10/10/22\",\"categoria\":\"OUTRAS\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isBadRequest());
    }

    @Test
    @Order(6)
    void deveriaRetornarOkAoBuscarTodasDespesas() throws Exception {

        URI uri = new URI("/despesas");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isOk())
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString();

        assertEquals(jsonExpectedAllElements, jsonResult);
    }

    @Test
    @Order(7)
    void deveriaRetornarOkAoBuscarDespesaPorDescricao() throws Exception {

        URI uri = new URI("/despesas?descricao=DespesaTeste1");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isOk())
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString();

        assertEquals("[" + jsonExpectedElement1 + "]", jsonResult);
    }

    @Test
    @Order(8)
    void deveriaRetornarOkEJsonVazioaoBuscarDespesaPorDescricaoInvalida() throws Exception {

        URI uri = new URI("/despesas?descricao=DespesaInexistente");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isOk())
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString();

        assertEquals("[]", jsonResult);
    }

    @Test
    @Order(9)
    void deveriaRetornarOkAoBuscarDespesaPorId() throws Exception {

        URI uri = new URI("/despesas/1");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isOk())
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString();

        assertEquals(jsonExpectedElement1, jsonResult);
    }

    @Test
    @Order(10)
    void deveriaRetornarNotFoundAoBuscarDespesaComIdInvalido() throws Exception {

        URI uri = new URI("/despesas/4");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isNotFound());
    }

    @Test
    @Order(11)
    void deveriaRetornarNotFoundAoPesquisarPorMesInvalido() throws Exception {

        URI uri = new URI("/despesas/2022/15");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isNotFound());
    }

    @Test
    @Order(12)
    void deveriaRetornarOkAoPesquisarPorMesValido() throws Exception {

        URI uri = new URI("/despesas/2022/10");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isOk())
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString();

        assertEquals(jsonExpectedAllElements, jsonResult);

    }

    @Test
    @Order(13)
    void deveriaRetornarNotFoundAoAtualizarDespesaComIdInexistente() throws Exception {

        URI uri = new URI("/despesas/4");

        String json = "{\"descricao\":\"DespesaNovaTeste1\",\"valor\":\"1400.00\",\"data\":\"10/10/2022\",\"categoria\":\"MORADIA\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .put(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isNotFound());
    }

    @Test
    @Order(14)
    void deveriaRetornarBadRequestAoAtualizarDespesaDuplicada() throws Exception {

        URI uri = new URI("/despesas/1");

        mockMvc.perform(MockMvcRequestBuilders
                        .put(uri)
                        .content(jsonElement2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isBadRequest());
    }

    @Test
    @Order(15)
    void deveriaRetornarOkAoAtualizarDespesaValida() throws Exception {

        URI uri = new URI("/despesas/1");

        String json = "{\"descricao\":\"DespesaNovaTeste1\",\"valor\":\"1400.00\",\"data\":\"10/10/2022\",\"categoria\":\"MORADIA\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .put(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isOk());
    }

    @Test
    @Order(16)
    void deveriaRetornarBadRequestAoAtualizarReceitaComDataInvalida() throws Exception {

        URI uri = new URI("/despesas/1");

        String json = "{\"descricao\":\"DespesaNovaTeste1\",\"valor\":\"1400.00\",\"data\":\"10/15/2022\",\"categoria\":\"MORADIA\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .put(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isBadRequest());
    }

    @Test
    @Order(17)
    void deveriaRetornarNotFoundAoDeletarDespesaComIdInvalido() throws Exception {

        URI uri = new URI("/despesas/4");

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isNotFound());
    }

    @Test
    @Order(18)
    void deveriaRetornarOkAoDeletarDespesaValida() throws Exception {

        URI uri = new URI("/despesas/1");

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isOk());
    }

}
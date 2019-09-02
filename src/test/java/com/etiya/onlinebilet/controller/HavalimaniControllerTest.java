package com.etiya.onlinebilet.controller;

import com.etiya.onlinebilet.domain.Havalimani;
import com.etiya.onlinebilet.domain.Havayolu;
import com.etiya.onlinebilet.domain.Rota;
import com.etiya.onlinebilet.dto.BaseResponse;
import com.etiya.onlinebilet.repository.*;
import com.etiya.onlinebilet.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springfox.documentation.spring.web.json.Json;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HavalimaniController.class)
public class HavalimaniControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    HavalimaniRepository havalimaniRepository;

    @MockBean
    RotaRepository rotaRepository;

    @MockBean
    HavayoluRepository havayoluRepository;

    @MockBean
    RezervasyonRepository rezervasyonRepository;

    @MockBean
    UcusRepository ucusRepository;

    @Autowired
    HavalimaniController havalimaniController;

    @Test
    public void getirTumHavalimani() throws Exception {
        Havalimani havalimani = new Havalimani();
        havalimani.setAdi("Sabiha Gökçen Havalimanı");
        havalimani.setKodu("SAW");
        List<Havalimani> havalimaniListesi = Arrays.asList(havalimani);
        given(havalimaniRepository.findAll()).willReturn(havalimaniListesi);

        mockMvc.perform( MockMvcRequestBuilders
                .get("/api/v1/havalimani")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray());
    }

    @Test
    public void getirHavalimani() throws Exception {
        Havalimani havalimani = new Havalimani();
        havalimani.setAdi("Sabiha Gökçen Havalimanı");
        havalimani.setKodu("SAW");
        Optional<Havalimani> optional = Optional.of(havalimani);
        given(havalimaniRepository.findById(1l)).willReturn(optional);

        mockMvc.perform( MockMvcRequestBuilders
                .get("/api/v1/havalimani/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isMap());
    }

    @Test
    public void getirHavalimaniByNereden() throws Exception {
        Rota rota = new Rota();

        Havalimani havalimani = new Havalimani();
        havalimani.setAdi("Sabiha Gökçen Havalimanı");
        havalimani.setKodu("SAW");

        Havalimani havalimani2 = new Havalimani();
        havalimani2.setAdi("Samsun Çarşamba Havalimanı");
        havalimani2.setKodu("SZF");

        rota.setNereden(havalimani);
        rota.setNereye(havalimani2);
        List<Rota> rotaListesi = Arrays.asList(rota);

        given(havalimaniRepository.findByKodu("SAW")).willReturn(havalimani);
        given(rotaRepository.findRotaByNereden(havalimani)).willReturn(rotaListesi);

        mockMvc.perform( MockMvcRequestBuilders
                .get("/api/v1/havalimaniByNereden/SAW")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray());
    }

    @Test
    public void kaydetHavalimani() throws Exception{
        Havalimani havalimani = new Havalimani();
        havalimani.setAdi("Sabiha Gökçen Havalimanı");
        havalimani.setKodu("SAW");

        given(havalimaniRepository.save(havalimani)).willReturn(havalimani);

        mockMvc.perform( MockMvcRequestBuilders
                .post("/api/v1/havalimani")
                .contentType(APPLICATION_JSON_UTF8)
                .content(Utils.asJsonString(havalimani))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isMap());
    }

}

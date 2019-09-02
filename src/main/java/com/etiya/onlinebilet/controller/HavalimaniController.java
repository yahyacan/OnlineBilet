package com.etiya.onlinebilet.controller;

import com.etiya.onlinebilet.domain.Havalimani;
import com.etiya.onlinebilet.domain.Rota;
import com.etiya.onlinebilet.dto.BaseResponse;
import com.etiya.onlinebilet.enums.HataMesaji;
import com.etiya.onlinebilet.repository.HavalimaniRepository;
import com.etiya.onlinebilet.repository.RotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HavalimaniController {

    @Autowired
    HavalimaniRepository havalimaniRepository;

    @Autowired
    RotaRepository rotaRepository;

    @GetMapping("/havalimani")
    public BaseResponse<List<Havalimani>> getirTumHavalimani(){
        BaseResponse<List<Havalimani>> result = new BaseResponse<>();
        try {
            result.setData(havalimaniRepository.findAll());
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @GetMapping("/havalimani/{id}")
    public BaseResponse<Havalimani> getirHavalimani(@PathVariable Long id){
        BaseResponse<Havalimani> result = new BaseResponse<>();
        try {
            result.setData(havalimaniRepository.findById(id).get());
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @GetMapping("/havalimaniByNereden/{havalimaniKodu}")
    public BaseResponse<List<Havalimani>> getirHavalimaniByNereden(@PathVariable String havalimaniKodu){
        BaseResponse<List<Havalimani>> result = new BaseResponse<>();
        Havalimani tempHavalimani = havalimaniRepository.findByKodu(havalimaniKodu);
        List<Rota> rotaListesi = rotaRepository.findRotaByNereden(tempHavalimani);
        try {
            if (!rotaListesi.isEmpty()){
                List<Havalimani> liste = new ArrayList<>();
                for (Rota rota: rotaListesi) {
                    liste.add(rota.getNereye());
                }
                result.setData(liste);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/havalimani")
    public BaseResponse<Havalimani> kaydetHavalimani(@RequestBody Havalimani havalimani){
        BaseResponse<Havalimani> result = new BaseResponse<>();
        try {
            result.setData(havalimaniRepository.save(havalimani));
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/havalimani/{id}")
    public BaseResponse<Boolean> silHavalimani(@PathVariable Long id){
        BaseResponse<Boolean> result = new BaseResponse<Boolean>();
        try {
            havalimaniRepository.deleteById(id);
            result.setData(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

}

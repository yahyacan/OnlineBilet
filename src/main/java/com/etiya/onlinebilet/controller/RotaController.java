package com.etiya.onlinebilet.controller;

import com.etiya.onlinebilet.domain.Havayolu;
import com.etiya.onlinebilet.domain.Rota;
import com.etiya.onlinebilet.dto.BaseResponse;
import com.etiya.onlinebilet.enums.HataMesaji;
import com.etiya.onlinebilet.repository.HavayoluRepository;
import com.etiya.onlinebilet.repository.RotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RotaController {

    @Autowired
    RotaRepository rotaRepository;

    @GetMapping("/rota")
    public BaseResponse<List<Rota>> getirTumRota(){
        BaseResponse<List<Rota>> result = new BaseResponse<>();
        try {
            result.setData(rotaRepository.findAll());
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @GetMapping("/rota/{id}")
    public BaseResponse<Rota> getirRota(@PathVariable Long id){
        BaseResponse<Rota> result = new BaseResponse<>();
        try {
            result.setData(rotaRepository.findById(id).get());
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/rota")
    public BaseResponse<Rota> kaydetHavayolu(@RequestBody Rota rota){
        BaseResponse<Rota> result = new BaseResponse<>();
        try {
            result.setData(rotaRepository.save(rota));
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/rota/{id}")
    public BaseResponse<Boolean> silHavayolu(@PathVariable Long id){
        BaseResponse<Boolean> result = new BaseResponse<Boolean>();
        try {
            rotaRepository.deleteById(id);
            result.setData(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

}

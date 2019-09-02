package com.etiya.onlinebilet.controller;

import com.etiya.onlinebilet.domain.Havayolu;
import com.etiya.onlinebilet.dto.BaseResponse;
import com.etiya.onlinebilet.enums.HataMesaji;
import com.etiya.onlinebilet.repository.HavayoluRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HavayoluController {

    @Autowired
    HavayoluRepository havayoluRepository;

    @GetMapping("/havayolu")
    public BaseResponse<List<Havayolu>> getirTumHavayolu(){
        BaseResponse<List<Havayolu>> result = new BaseResponse<>();
        try {
            result.setData(havayoluRepository.findAll());
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @GetMapping("/havayolu/{id}")
    public BaseResponse<Havayolu> getirHavayolu(@PathVariable Long id){
        BaseResponse<Havayolu> result = new BaseResponse<>();
        try {
            result.setData(havayoluRepository.findById(id).get());
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/havayolu")
    public BaseResponse<Havayolu> kaydetHavayolu(@RequestBody Havayolu havayolu){
        BaseResponse<Havayolu> result = new BaseResponse<>();
        try {
            result.setData(havayoluRepository.save(havayolu));
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/havayolu/{id}")
    public BaseResponse<Boolean> silHavayolu(@PathVariable Long id){
        BaseResponse<Boolean> result = new BaseResponse<Boolean>();
        try {
            havayoluRepository.deleteById(id);
            result.setData(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

}

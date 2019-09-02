package com.etiya.onlinebilet.controller;

import com.etiya.onlinebilet.domain.Rezervasyon;
import com.etiya.onlinebilet.domain.Rota;
import com.etiya.onlinebilet.domain.Ucus;
import com.etiya.onlinebilet.dto.BaseResponse;
import com.etiya.onlinebilet.dto.UcusAramaDTO;
import com.etiya.onlinebilet.enums.HataMesaji;
import com.etiya.onlinebilet.enums.IslemTipleri;
import com.etiya.onlinebilet.repository.RezervasyonRepository;
import com.etiya.onlinebilet.repository.RotaRepository;
import com.etiya.onlinebilet.repository.UcusRepository;
import com.etiya.onlinebilet.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RezervasyonController {

    @Autowired
    RezervasyonRepository rezervasyonRepository;

    @GetMapping("/rezervasyon")
    public BaseResponse<List<Rezervasyon>> getirTumRezervasyon(){
        BaseResponse<List<Rezervasyon>> result = new BaseResponse<>();
        try {
            result.setData(rezervasyonRepository.findAll());
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @GetMapping("/rezervasyon/{id}")
    public BaseResponse<Rezervasyon> getirRezervasyon(@PathVariable Long id){
        BaseResponse<Rezervasyon> result = new BaseResponse<>();
        try {
            result.setData(rezervasyonRepository.findById(id).get());
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/rezervasyon")
    public BaseResponse<Rezervasyon> kaydetRezervasyon(@RequestBody Rezervasyon rezervasyon){
        BaseResponse<Rezervasyon> result = new BaseResponse<>();
        try {
            if (rezervasyon.getId() == null)
                rezervasyon.setPnrNo(Utils.uretPnrNo(6));
            rezervasyon.setIslemTipi(IslemTipleri.REZERVASYON.name());
            result.setData(rezervasyonRepository.save(rezervasyon));
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/rezervasyon/{id}")
    public BaseResponse<Boolean> silRezervasyon(@PathVariable Long id){
        BaseResponse<Boolean> result = new BaseResponse<Boolean>();
        try {
            rezervasyonRepository.deleteById(id);
            result.setData(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

}

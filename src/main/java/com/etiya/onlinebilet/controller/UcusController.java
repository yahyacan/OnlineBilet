package com.etiya.onlinebilet.controller;

import com.etiya.onlinebilet.domain.Rota;
import com.etiya.onlinebilet.domain.Ucus;
import com.etiya.onlinebilet.dto.BaseResponse;
import com.etiya.onlinebilet.dto.UcusAramaDTO;
import com.etiya.onlinebilet.enums.HataMesaji;
import com.etiya.onlinebilet.repository.RotaRepository;
import com.etiya.onlinebilet.repository.UcusRepository;
import com.etiya.onlinebilet.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UcusController {

    @Autowired
    UcusRepository ucusRepository;

    @Autowired
    RotaRepository rotaRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @GetMapping("/ucus")
    public BaseResponse<List<Ucus>> getirTumUcus(){
        BaseResponse<List<Ucus>> result = new BaseResponse<>();
        try {
            result.setData(ucusRepository.findAll());
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @GetMapping("/ucus/{id}")
    public BaseResponse<Ucus> getirUcus(@PathVariable Long id){
        BaseResponse<Ucus> result = new BaseResponse<>();
        try {
            result.setData(ucusRepository.findById(id).get());
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/getirUcusByNeredenNereyeTarih")
    public BaseResponse<List<Ucus>> getirUcusByNeredenNereyeTarih(@RequestBody UcusAramaDTO ucusArama){
        BaseResponse<List<Ucus>> result = new BaseResponse<>();
        try {
            LocalDateTime baslangicTarihi = LocalDateTime.parse(ucusArama.getUcusTarihi() + " 00:00",formatter );;
            LocalDateTime bitisTarihi = LocalDateTime.parse(ucusArama.getUcusTarihi() + " 23:59",formatter );;

            Rota rota = rotaRepository.findByNeredenAndNereye(ucusArama.getNereden(),ucusArama.getNereye());
            List<Ucus> ucusListesi = ucusRepository.findByRotaAndUcusTarihiBetween(rota,baslangicTarihi,bitisTarihi);
            result.setData(ucusListesi);
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/ucus")
    public BaseResponse<Ucus> kaydetUcus(@RequestBody Ucus ucus){
        BaseResponse<Ucus> result = new BaseResponse<>();
        try {
            if (ucus.getId() == null)
                ucus.setUcusNo(Utils.uretUcusNo(ucus.getRota().getHavayolu().getUcusKodu(),6));
            result.setData(ucusRepository.save(ucus));
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/ucus/{id}")
    public BaseResponse<Boolean> silUcus(@PathVariable Long id){
        BaseResponse<Boolean> result = new BaseResponse<Boolean>();
        try {
            ucusRepository.deleteById(id);
            result.setData(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(HataMesaji.ERROR.getCode());
            result.setMessage(HataMesaji.ERROR.getCode() + " : " + e.getMessage());
        }
        return result;
    }

}

package com.etiya.onlinebilet.dto;

import com.etiya.onlinebilet.domain.Havalimani;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UcusAramaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Havalimani nereden;
    private Havalimani nereye;
    private String ucusTarihi;

}

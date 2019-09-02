package com.etiya.onlinebilet.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Ucus  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(length = 10)
    private String ucusNo;

    @OneToOne
    private Rota rota;

    private LocalDateTime ucusTarihi;

    private BigDecimal fiyat;

    @Column(length = 5)
    private String paraBirimi;

    private Integer koltukSayisi;

}

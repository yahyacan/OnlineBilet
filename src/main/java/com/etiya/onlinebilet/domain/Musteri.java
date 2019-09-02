package com.etiya.onlinebilet.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Musteri implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(length = 11)
    private String tcNo;

    @Column(length = 20)
    private String adi;

    @Column(length = 20)
    private String soyadi;
    private Date dogumTarihi;

    @Column(length = 5)
    private String cinsiyet;

    @Column(length = 5)
    private String uyruk;
}

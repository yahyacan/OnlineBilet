package com.etiya.onlinebilet.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Rezervasyon implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(length = 10)
    private String pnrNo;

    @OneToOne(cascade = CascadeType.ALL)
    private Musteri musteri;

    @OneToOne
    private Ucus ucus;

    @Column(length = 15)
    private String islemTipi;

}

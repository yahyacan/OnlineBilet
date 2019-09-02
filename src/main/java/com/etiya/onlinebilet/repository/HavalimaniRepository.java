package com.etiya.onlinebilet.repository;

import com.etiya.onlinebilet.domain.Havalimani;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HavalimaniRepository extends JpaRepository<Havalimani, Long> {

    public Havalimani findByKodu(String kodu);

}

package com.etiya.onlinebilet.repository;

import com.etiya.onlinebilet.domain.Rota;
import com.etiya.onlinebilet.domain.Ucus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UcusRepository extends JpaRepository<Ucus,Long> {

    public List<Ucus> findByRotaAndUcusTarihiBetween(Rota rota, LocalDateTime startDate, LocalDateTime endDate);

}

package com.etiya.onlinebilet.repository;

import com.etiya.onlinebilet.domain.Havalimani;
import com.etiya.onlinebilet.domain.Rota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RotaRepository extends JpaRepository<Rota, Long> {

    public List<Rota> findRotaByNereden(Havalimani nereden);

    public Rota findByNeredenAndNereye(Havalimani nereden, Havalimani nereye);

}

package com.etiya.onlinebilet.repository;

import com.etiya.onlinebilet.domain.Havayolu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HavayoluRepository extends JpaRepository<Havayolu,Long> {
}

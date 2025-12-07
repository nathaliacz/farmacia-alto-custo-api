package com.farmacia.altocusto.farmacia_api.repository;

import com.farmacia.altocusto.farmacia_api.model.EnderecoFarmacia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoFarmaciaRepository extends JpaRepository<EnderecoFarmacia, Long> {

    Optional<EnderecoFarmacia> findByFarmaciaId(Long farmaciaId);
}

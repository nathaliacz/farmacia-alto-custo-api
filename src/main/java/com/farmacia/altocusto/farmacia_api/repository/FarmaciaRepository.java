package com.farmacia.altocusto.farmacia_api.repository;

import com.farmacia.altocusto.farmacia_api.model.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmaciaRepository extends JpaRepository<Farmacia, Long> {

    Farmacia findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCnpj(String cnpj);
}


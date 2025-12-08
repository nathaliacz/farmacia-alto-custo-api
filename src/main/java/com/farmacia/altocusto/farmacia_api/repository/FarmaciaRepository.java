package com.farmacia.altocusto.farmacia_api.repository;

import com.farmacia.altocusto.farmacia_api.model.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmaciaRepository extends JpaRepository<Farmacia, Long> {

    // usado para login
    Optional<Farmacia> findByEmail(String email);

    // usado para validar duplicidade no cadastro
    boolean existsByEmail(String email);

    boolean existsByCnpj(String cnpj);
}


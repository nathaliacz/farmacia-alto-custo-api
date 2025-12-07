package com.farmacia.altocusto.farmacia_api.repository;

import com.farmacia.altocusto.farmacia_api.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    // query customizada (conta ponto no projeto!)
    List<Medicamento> findByNomeContainingIgnoreCase(String nome);
}

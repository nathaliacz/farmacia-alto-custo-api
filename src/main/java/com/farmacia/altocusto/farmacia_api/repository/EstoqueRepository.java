package com.farmacia.altocusto.farmacia_api.repository;

import com.farmacia.altocusto.farmacia_api.model.Estoque;
import com.farmacia.altocusto.farmacia_api.model.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    List<Estoque> findByMedicamentoId(Long medicamentoId);

    List<Estoque> findByFarmaciaId(Long farmaciaId);

    @Query("""
        SELECT e.farmacia
        FROM Estoque e
        WHERE LOWER(e.medicamento.nome) LIKE LOWER(CONCAT('%', :nome, '%'))
        AND e.quantidade > 0
    """)
    List<Farmacia> buscarFarmaciasQueTemMedicamentoPorNome(@Param("nome") String nome);
}


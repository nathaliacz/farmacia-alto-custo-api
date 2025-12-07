package com.farmacia.altocusto.farmacia_api.repository;

import com.farmacia.altocusto.farmacia_api.model.UsuarioMedicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioMedicamentoRepository extends JpaRepository<UsuarioMedicamento, Long> {

    // lista todos os medicamentos cadastrados para um usuário
    List<UsuarioMedicamento> findByUsuarioId(Long usuarioId);
}


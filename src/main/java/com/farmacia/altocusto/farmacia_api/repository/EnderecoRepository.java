package com.farmacia.altocusto.farmacia_api.repository;

import com.farmacia.altocusto.farmacia_api.model.Endereco;
import com.farmacia.altocusto.farmacia_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByUsuario(Usuario usuario);
}

package com.farmacia.altocusto.farmacia_api.service;

import com.farmacia.altocusto.farmacia_api.model.Endereco;
import com.farmacia.altocusto.farmacia_api.model.Usuario;
import com.farmacia.altocusto.farmacia_api.repository.EnderecoRepository;
import com.farmacia.altocusto.farmacia_api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    public EnderecoService(EnderecoRepository enderecoRepository,
                           UsuarioRepository usuarioRepository) {
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Endereco criar(Long usuarioId, Endereco endereco) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        endereco.setUsuario(usuario);
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listarPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return enderecoRepository.findByUsuario(usuario);
    }

    public void deletar(Long enderecoId) {
        enderecoRepository.deleteById(enderecoId);
    }
}

package com.farmacia.altocusto.farmacia_api.service;

import com.farmacia.altocusto.farmacia_api.model.Usuario;
import com.farmacia.altocusto.farmacia_api.repository.UsuarioRepository;
import com.farmacia.altocusto.farmacia_api.util.HashUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // ----------------------------------
    // CADASTRAR USUÁRIO
    // ----------------------------------
    public Usuario criar(Usuario usuario) {

        // ⚠️ Verifica se CPF já existe
        if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
            throw new RuntimeException("CPF já cadastrado");
        }

        // ⚠️ Verifica se e-mail já existe
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        // A senha enviada no campo senhaHash é a senha pura!
        String senhaPura = usuario.getSenhaHash();
        String senhaHash = HashUtil.sha256(senhaPura);

        usuario.setSenhaHash(senhaHash);
        usuario.setDataCadastro(LocalDateTime.now());

        return usuarioRepository.save(usuario);
    }

    // ----------------------------------
    // LOGIN DO USUÁRIO
    // ----------------------------------
    public Usuario login(String email, String senhaPura) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String senhaHash = HashUtil.sha256(senhaPura);

        if (!usuario.getSenhaHash().equals(senhaHash)) {
            throw new RuntimeException("Senha incorreta");
        }

        return usuario;
    }

    // ----------------------------------
    // LISTAR TODOS
    // ----------------------------------
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    // ----------------------------------
    // BUSCAR POR ID
    // ----------------------------------
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // ----------------------------------
    // DELETAR
    // ----------------------------------
    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}


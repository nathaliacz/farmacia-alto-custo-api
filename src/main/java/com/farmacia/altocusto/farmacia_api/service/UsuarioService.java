package com.farmacia.altocusto.farmacia_api.service;

import com.farmacia.altocusto.farmacia_api.model.Usuario;
import com.farmacia.altocusto.farmacia_api.repository.UsuarioRepository;
import com.farmacia.altocusto.farmacia_api.util.HashUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // CADASTRAR USUÁRIO
    public Usuario criar(Usuario usuario) {

        // A senha enviada no campo senhaHash é a senha pura!
        String senhaPura = usuario.getSenhaHash();
        if (senhaPura == null || senhaPura.isBlank()) {
            throw new RuntimeException("Senha é obrigatória.");
        }

        String senhaHash = HashUtil.sha256(senhaPura);

        usuario.setSenhaHash(senhaHash);
        usuario.setDataCadastro(LocalDateTime.now());

        return usuarioRepository.save(usuario);
    }

    // LOGIN DO USUÁRIO
    // aceita senha em texto puro e com hash

    public Usuario login(String email, String senhaPura) {

        Optional<Usuario> opt = usuarioRepository.findByEmail(email);
        if (opt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = opt.get();

        String senhaBanco = usuario.getSenhaHash();
        String senhaHashCalculada = HashUtil.sha256(senhaPura);

        boolean senhaConfere = false;

        // 1) senha já está em hash no banco
        if (senhaBanco != null && senhaBanco.equals(senhaHashCalculada)) {
            senhaConfere = true;
        }
        // 2) senha no banco ainda é texto puro
        else if (senhaBanco != null && senhaBanco.equals(senhaPura)) {
            senhaConfere = true;

            // Atualiza para hash no banco
            usuario.setSenhaHash(senhaHashCalculada);
            usuarioRepository.save(usuario);
        }

        if (!senhaConfere) {
            throw new RuntimeException("Senha incorreta");
        }

        // monta um objeto de resposta SEM a senha
        Usuario resposta = new Usuario();
        resposta.setId(usuario.getId());
        resposta.setNome(usuario.getNome());
        resposta.setCpf(usuario.getCpf());
        resposta.setEmail(usuario.getEmail());
        resposta.setDataCadastro(usuario.getDataCadastro());
        // não copia senhaHash

        return resposta;
    }

    // LISTAR TODOS
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    // BUSCAR POR ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // DELETAR
    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}

package com.farmacia.altocusto.farmacia_api.controller;

import com.farmacia.altocusto.farmacia_api.model.Usuario;
import com.farmacia.altocusto.farmacia_api.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // -----------------------------
    // CADASTRAR USUÁRIO
    // -----------------------------
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Usuario usuario) {
        try {
            Usuario salvo = usuarioService.criar(usuario);
            return ResponseEntity.ok(salvo);
        } catch (RuntimeException e) {
            // aqui aparecem mensagens como "CPF já cadastrado" ou "Email já cadastrado"
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // -----------------------------
    // LOGIN DO USUÁRIO
    // -----------------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario credenciais) {
        try {
            Usuario usuario = usuarioService.login(
                    credenciais.getEmail(),
                    credenciais.getSenhaHash() // aqui você envia a senha pura no campo senhaHash
            );
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    // -----------------------------
    // LISTAR TODOS
    // -----------------------------
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    // -----------------------------
    // BUSCAR POR ID
    // -----------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    // -----------------------------
    // DELETAR
    // -----------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}


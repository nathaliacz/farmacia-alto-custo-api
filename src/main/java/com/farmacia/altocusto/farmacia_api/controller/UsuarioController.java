package com.farmacia.altocusto.farmacia_api.controller;

import com.farmacia.altocusto.farmacia_api.dto.LoginRequest;
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

    // CADASTRAR USUÁRIO
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Usuario usuario) {
        try {
            Usuario salvo = usuarioService.criar(usuario);
            return ResponseEntity.ok(salvo);
        } catch (RuntimeException e) {
            // por exemplo: CPF já cadastrado, email já cadastrado, etc.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // LOGIN USUÁRIO (usa DTO LoginRequest: { email, senha })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Usuario usuario = usuarioService.login(
                    request.getEmail(),
                    request.getSenha()
            );
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            // se der "Usuário não encontrado" ou "Senha incorreta"
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    // LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // DELETAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

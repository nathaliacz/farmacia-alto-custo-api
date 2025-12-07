package com.farmacia.altocusto.farmacia_api.controller;

import com.farmacia.altocusto.farmacia_api.model.UsuarioMedicamento;
import com.farmacia.altocusto.farmacia_api.service.UsuarioMedicamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios/{usuarioId}/meus-medicamentos")
@CrossOrigin(origins = "*")
public class UsuarioMedicamentoController {

    private final UsuarioMedicamentoService usuarioMedicamentoService;

    public UsuarioMedicamentoController(UsuarioMedicamentoService usuarioMedicamentoService) {
        this.usuarioMedicamentoService = usuarioMedicamentoService;
    }

    // DTO simples só para receber o body do POST
    public static class UsuarioMedicamentoRequest {
        public Long medicamentoId;
        public String observacoes;
    }

    // POST - adicionar medicamento à lista do usuário
    @PostMapping
    public ResponseEntity<UsuarioMedicamento> adicionar(
            @PathVariable Long usuarioId,
            @RequestBody UsuarioMedicamentoRequest body) {

        UsuarioMedicamento salvo = usuarioMedicamentoService.adicionarMedicamento(
                usuarioId,
                body.medicamentoId,
                body.observacoes
        );

        return ResponseEntity.ok(salvo);
    }

    // GET - listar "meus medicamentos" do usuário
    @GetMapping
    public ResponseEntity<List<UsuarioMedicamento>> listar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioMedicamentoService.listarPorUsuario(usuarioId));
    }

    // DELETE - remover medicamento da lista do usuário
    @DeleteMapping("/{usuarioMedicamentoId}")
    public ResponseEntity<Void> remover(
            @PathVariable Long usuarioId,
            @PathVariable Long usuarioMedicamentoId) {

        usuarioMedicamentoService.remover(usuarioId, usuarioMedicamentoId);
        return ResponseEntity.noContent().build();
    }
}

